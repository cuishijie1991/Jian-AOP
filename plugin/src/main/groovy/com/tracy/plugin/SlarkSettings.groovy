package com.tracy.plugin

import org.gradle.api.Project

class SlarkSettings {
    static class SlarkOptions {
        def enabled = true
        def configMode = false
        List<String> injectPackages = []
    }

    static class TrackOptions {
        def click = true
        def page = true
    }

    static Project project

    static void setProject(Project project) {
        this.project = project
        project.extensions.create("slark", SlarkOptions)
        project.slark.extensions.create("track", TrackOptions)
    }

    static boolean isConfigMode() {
        return project.slark.configMode
    }

    static boolean isEnabled() {
        return project.slark.enabled
    }

    static List<String> getInjectPackages() {
        List<String> injectPackages = []
        for (String packageName : project.slark.injectPackages) {
            if (packageName != null && packageName.length() > 0) {
                injectPackages.add(packageName.replace('.', '/'))
            }
        }
        return injectPackages
    }

    static boolean isTrackClick() {
        return project.slark.track.click
    }

    static boolean isTrackPage() {
        return project.slark.track.page
    }

}