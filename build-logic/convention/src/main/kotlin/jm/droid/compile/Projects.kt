package jm.droid.compile

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun Project.setupLibraryModule(
    buildConfig: Boolean = false,
    publish: Boolean = false,
    document: Boolean = publish,
    block: LibraryExtension.() -> Unit = {}
) = setupBaseModule<LibraryExtension> {
    libraryVariants.all {
        generateBuildConfigProvider?.configure { enabled = buildConfig }
    }
    buildFeatures {
        viewBinding = true
    }
    if (publish) {
        if (document) apply(plugin = "org.jetbrains.dokka")
        apply(plugin = "com.vanniktech.maven.publish.base")
        publishing {
            singleVariant("release") {
                withJavadocJar()
                withSourcesJar()
            }
        }
        afterEvaluate {
            extensions.configure<PublishingExtension> {
                publications.create<MavenPublication>("release") {
                    from(components["release"])
                    // https://github.com/vanniktech/gradle-maven-publish-plugin/issues/326
                    val id = project.property("POM_ARTIFACT_ID").toString()
                    artifactId = artifactId.replace(project.name, id)
                }
            }
        }
    }
    block()
}

fun Project.setupAppModule(
    block: BaseAppModuleExtension.() -> Unit = {}
) = setupBaseModule<BaseAppModuleExtension> {
    defaultConfig {
        versionCode = project.versionCode
        versionName = project.versionName
        resourceConfigurations += "en"
        vectorDrawables.useSupportLibrary = true
    }
    buildFeatures {
        viewBinding = true
    }
    block()
}

private inline fun <reified T : BaseExtension> Project.setupBaseModule(
    crossinline block: T.() -> Unit = {}
) = extensions.configure<T>("android") {
    group = groupId
    version = versionName
    compileSdkVersion(project.compileSdk)
    defaultConfig {
        minSdk = project.minSdk
        targetSdk = project.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        allWarningsAsErrors = false

        val arguments = mutableListOf(
            // https://kotlinlang.org/docs/compiler-reference.html#progressive
            "-progressive",
            // Generate smaller bytecode by not generating runtime not-null assertions.
            "-Xno-call-assertions",
            "-Xno-param-assertions",
            "-Xno-receiver-assertions",
            // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-requires-opt-in/#requiresoptin
            "-opt-in=kotlin.RequiresOptIn"
        )
        // https://youtrack.jetbrains.com/issue/KT-41985
        freeCompilerArgs += arguments
    }
    packagingOptions {
        resources.pickFirsts += "META-INF/AL2.0"
        resources.pickFirsts += "META-INF/LGPL2.1"
        resources.pickFirsts += "META-INF/*kotlin_module"
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    block()
}

private fun BaseExtension.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

fun Project.spotless() {
    pluginManager.apply("com.diffplug.spotless")
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    extensions.configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint(libs.findVersion("ktlint").get().toString()).userData(mapOf("android" to "true"))
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
        format("kts") {
            target("**/*.kts")
            targetExclude("**/build/**/*.kts")
            // Look for the first line that doesn't have a block comment (assumed to be the license)
            licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
        }
        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
            // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
            licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
        }
    }
}