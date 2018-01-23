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
import com.android.build.gradle.internal.pipeline.TransformManager
import com.tracy.plugin.java.CostMethodClassVisitor
import com.tracy.plugin.java.SourceMethodClassVisitor
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES

class JianTransform extends Transform {
    Project project

    public JianTransform(Project project) {
        this.project = project
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

        handleSourceFiles(inputs, outputProvider)
//        handleJarFiles(inputs.jarInputs, outputProvider)
        println '//===============JianAOPPluginImp visit end===============//'
    }

    private void handleSourceFiles(Collection<TransformInput> inputs, TransformOutputProvider outputProvider) {
        inputs.each { TransformInput input ->
            //遍历input里边的DirectoryInput
            input.directoryInputs.each {
                DirectoryInput directoryInput ->
                    if (directoryInput.file.isDirectory()) {
                        directoryInput.file.eachFileRecurse {
                            File file ->
                                def name = file.name
                                if (isFileAvailable(name)) {
                                    ClassReader classReader = new ClassReader(file.bytes)
                                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                                    def className = name.split(".class")[0]
                                    ClassVisitor cv = new SourceMethodClassVisitor(className, classWriter)
                                    classReader.accept(cv, EXPAND_FRAMES)
                                    byte[] code = classWriter.toByteArray()
                                    FileOutputStream fos = new FileOutputStream(file.parentFile.absolutePath + File.separator + name)
                                    fos.write(code)
                                    fos.close()

                                    //处理完输入文件之后，要把输出给下一个任务
                                    def dest = outputProvider.getContentLocation(directoryInput.name,
                                            directoryInput.contentTypes, directoryInput.scopes,
                                            Format.DIRECTORY)
                                    FileUtils.copyDirectory(directoryInput.file, dest)
                                }
                        }
                    }


            }
        }
    }

    private void handleJarFiles(Collection<TransformInput> inputs, TransformOutputProvider outputProvider) {
        inputs.each { JarInput jarInput ->
            /**
             * 重名名输出文件,因为可能同名,会覆盖
             */
            def jarName = jarInput.name
            def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length() - 4)
            }

            File tmpFile = null
            if (jarInput.file.getAbsolutePath().endsWith(".jar")) {
                JarFile jarFile = new JarFile(jarInput.file)
                Enumeration enumeration = jarFile.entries()
                tmpFile = new File(jarInput.file.getParent() + File.separator + "classes_meetyoucost.jar")
                //避免上次的缓存被重复插入
                if (tmpFile.exists()) {
                    tmpFile.delete()
                }
                JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tmpFile))
                //用于保存
                ArrayList<String> processorList = new ArrayList<>()
                while (enumeration.hasMoreElements()) {
                    JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                    String entryName = jarEntry.getName()
                    ZipEntry zipEntry = new ZipEntry(entryName)
                    //println "MeetyouCost entryName :" + entryName
                    InputStream inputStream = jarFile.getInputStream(jarEntry)
                    //如果是inject文件就跳过

                    //插桩class
                    if (isFileAvailable(entryName)) {
                        //class文件处理
                        jarOutputStream.putNextEntry(zipEntry)
                        ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream))
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        def className = name.split(".class")[0]
                        ClassVisitor cv = new CostMethodClassVisitor(className, classWriter)
                        classReader.accept(cv, EXPAND_FRAMES)
                        byte[] code = classWriter.toByteArray()
                        jarOutputStream.write(code)

                    } else if (entryName.contains("META-INF/services/javax.annotation.processing.Processor")) {
                        if (!processorList.contains(entryName)) {
                            processorList.add(entryName)
                            jarOutputStream.putNextEntry(zipEntry)
                            jarOutputStream.write(IOUtils.toByteArray(inputStream))
                        } else {
                            println "duplicate entry:" + entryName
                        }
                    } else {

                        jarOutputStream.putNextEntry(zipEntry)
                        jarOutputStream.write(IOUtils.toByteArray(inputStream))
                    }

                    jarOutputStream.closeEntry()
                }
                //写入inject注解

                //写入inject文件
//                    ZipEntry addEntry = new ZipEntry(injectClazz + ".class")
//                    jarOutputStream.putNextEntry(addEntry)
//                    jarOutputStream.write(annaInjectWriter.inject(injectClazz,false))
//                    jarOutputStream.closeEntry()
//
//                    clazzindex++
                //结束
                jarOutputStream.close()
                jarFile.close()
//                    jarInput.file.delete()
//                    tmpFile.renameTo(jarInput.file)
            }
//                println 'Assassin-----> find Jar:' + jarInput.getFile().getAbsolutePath()

            //处理jar进行字节码注入处理 TODO

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

    private boolean isFileAvailable(String entryName) {
        return entryName.endsWith(".class") && !entryName.contains("R\$") && !entryName.contains("R.class") && !entryName.contains("BuildConfig.class")
    }

}
