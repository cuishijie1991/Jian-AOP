package com.tracy.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginImp implements Plugin<Project> {

    void apply(Project project) {
        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new JianTransform(project))
    }
}
