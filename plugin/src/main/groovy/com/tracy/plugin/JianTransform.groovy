package com.tracy.plugin

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.tracy.plugin.visitor.SourceMethodClassVisitor

import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES
import static org.objectweb.asm.Opcodes.ACC_PUBLIC
import static org.objectweb.asm.Opcodes.ALOAD
import static org.objectweb.asm.Opcodes.INVOKESPECIAL
import static org.objectweb.asm.Opcodes.INVOKESTATIC
import static org.objectweb.asm.Opcodes.RETURN

class JianTransform extends Transform {
    Project project

    JianTransform(Project project) {
        this.project = project
        SlarkSettings.setProject(project)
    }

    @Override
    String getName() {
        return "JianAOP"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context,
                   Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental)
            throws IOException, TransformException, InterruptedException {
        println '//===============JianAOPPluginImp visit start===============//'
        //删除之前的输出
        if (outputProvider != null)
            outputProvider.deleteAll()
        inputs.each { TransformInput input ->
            handleDirectoryInput(input, outputProvider)
            handleJarInput(input, outputProvider)
        }
        println '//===============JianAOPPluginImp visit end===============//'
    }

    private void handleDirectoryInput(TransformInput input, TransformOutputProvider outputProvider) {
        input.directoryInputs.each {
            DirectoryInput directoryInput ->
                if (SlarkSettings.isEnabled()) {
                    if (directoryInput.file.isDirectory()) {
                        directoryInput.file.eachFileRecurse {
                            File file ->
                                def name = file.name
                                if (isClassAvailable(name)) {
                                    ClassReader classReader = new ClassReader(file.bytes)
                                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                                    def className = name.split(".class")[0]
                                    ClassVisitor cv = new SourceMethodClassVisitor(className, classWriter)
                                    classReader.accept(cv, EXPAND_FRAMES)
                                    checkMethod(cv, classWriter)
                                    byte[] code = classWriter.toByteArray()
                                    FileOutputStream fos = new FileOutputStream(file.parentFile.absolutePath + File.separator + name)
                                    fos.write(code)
                                    fos.close()
                                }
                        }
                    }
                }
                //处理完输入文件之后，copy至原来位置，以便后续Transform处理
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
        }
    }

    private void handleJarInput(TransformInput input, TransformOutputProvider outputProvider) {
        List<String> injectPackages = SlarkSettings.getInjectPackages()
        input.jarInputs.each { JarInput jarInput ->
            //重名名输出文件,因为可能同名,会覆盖
            def jarName = jarInput.name
            def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length() - 4)
            }

            File tmpFile = null
            if (SlarkSettings.isEnabled()) {
                if (jarInput.file.getAbsolutePath().endsWith(".jar")) {
                    JarFile jarFile = new JarFile(jarInput.file)
                    Enumeration enumeration = jarFile.entries()
                    //jar修改时需要重命名暂存
                    tmpFile = new File(jarInput.file.getParent() + File.separator + "classes_slark.jar")
                    if (tmpFile.exists()) {
                        tmpFile.delete()
                    }
                    JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tmpFile))
                    while (enumeration.hasMoreElements()) {
                        JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                        String entryName = jarEntry.getName()
                        ZipEntry zipEntry = new ZipEntry(entryName)
                        InputStream inputStream = jarFile.getInputStream(zipEntry)
                        //插桩class
                        if (isClassAvailable(entryName) && isClassShouldInject(entryName, injectPackages)) {
                            jarOutputStream.putNextEntry(zipEntry)
                            ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream))
                            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                            def className = name.split(".class")[0]
                            ClassVisitor cv = new SourceMethodClassVisitor(className, classWriter)
                            classReader.accept(cv, EXPAND_FRAMES)
                            byte[] code = classWriter.toByteArray()
                            jarOutputStream.write(code)
                        } else {
                            jarOutputStream.putNextEntry(zipEntry)
                            jarOutputStream.write(IOUtils.toByteArray(inputStream))
                        }
                        jarOutputStream.closeEntry()
                    }
                    jarOutputStream.close()
                    jarFile.close()
                }
            }
            def dest = outputProvider.getContentLocation(jarName + md5Name,
                    jarInput.contentTypes, jarInput.scopes, Format.JAR)
            if (tmpFile == null) {
                FileUtils.copyFile(jarInput.file, dest)
            } else {
                FileUtils.copyFile(tmpFile, dest)
                tmpFile.delete()
            }
        }

    }

    private boolean isClassAvailable(String entryName) {
        return entryName.endsWith(".class") && !entryName.contains("R\$") && !entryName.contains("R.class") && !entryName.contains("BuildConfig.class")
    }

    private boolean isClassShouldInject(String entryName, List<String> injectPackages) {
        if (injectPackages.isEmpty())
            return true
        for (String packageName : injectPackages) {
            if (entryName.contains(packageName)) {
                return true
            }
        }
        return false
    }

    private void checkMethod(SourceMethodClassVisitor cv, ClassWriter classWriter) {
        if (cv.addMNames.size() > 0) {
            for (int i = 0; i < cv.addMNames.size(); i++) {
                System.out.println(cv.addMNames.get(i))
                if (cv.addMNames.get(i).equals("onResume") || cv.addMNames.get(i).equals("onPause")) {
                    addNoParamsMethod(classWriter, cv.addMNames.get(i))
                } else {
                    addParamsMethod(classWriter, cv.addMNames.get(i))
                }
            }
        }
    }

    private void addNoParamsMethod(ClassWriter cw, String name) {
        org.objectweb.asm.MethodVisitor mw = cw.visitMethod(
                ACC_PUBLIC, name, "()V", null, null)
        mw.visitVarInsn(ALOAD, 0)
        if (name.equals("onResume")) {
            mw.visitLdcInsn(true)
        } else {
            mw.visitLdcInsn(false)
        }
        mw.visitMethodInsn(INVOKESTATIC, "com/tracy/slark/Slark", "trackPageEvent", "(Ljava/lang/Object;Z)V")
        mw.visitVarInsn(ALOAD, 0)
        mw.visitMethodInsn(INVOKESPECIAL, "android/support/v4/app/Fragment", name, "()V", false)
        mw.visitInsn(RETURN)
        mw.visitMaxs(5, 5)
    }

    private void addParamsMethod(ClassWriter cw, String name) {
        org.objectweb.asm.MethodVisitor mw = cw.visitMethod(
                ACC_PUBLIC, name, "(Ljava/lang/boolean;)V", null, null)
        mw.visitVarInsn(ALOAD, 0)
        mw.visitVarInsn(ALOAD, 1)
        mw.visitMethodInsn(INVOKESTATIC, "com/tracy/slark/Slark", "trackPageEvent", "(Ljava/lang/Object;)V")
        mw.visitVarInsn(ALOAD, 0)
        mw.visitVarInsn(ALOAD, 1)
        mw.visitMethodInsn(INVOKESPECIAL, "android/support/v4/app/Fragment", name, "(Ljava/lang/boolean;)V", false)
        mw.visitInsn(RETURN)
        mw.visitMaxs(5, 5)
    }

}
