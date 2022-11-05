// Top-level build file where you can add configuration options common to all sub-projects/modules.

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost.DEFAULT
import com.vanniktech.maven.publish.SonatypeHost.S01
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.gradlePlugin.android)
        classpath(libs.gradlePlugin.kotlin)
        classpath(libs.gradlePlugin.mavenPublish)
    }
}

// https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")
plugins {
    alias(libs.plugins.binaryCompatibility)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.spotless)
}

tasks.withType<DokkaMultiModuleTask>().configureEach {
    outputDirectory.set(file("$rootDir/docs/api"))
}

allprojects {
    repositories {
        google()
        mavenCentral()
//        maven {
//            setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
//        }
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    plugins.withId("com.vanniktech.maven.publish.base") {
        extensions.configure<MavenPublishBaseExtension> {
            publishToMavenCentral(S01)
            signAllPublications()
            pomFromGradleProperties()
        }
    }

    // Uninstall test APKs after running instrumentation tests.
    tasks.whenTaskAdded {
        if (name == "connectedDebugAndroidTest") {
            finalizedBy("uninstallDebugAndroidTest")
        }
    }
}