//pluginManagement {

//    repositories {
//        google()
//        mavenCentral()
//        gradlePluginPortal()
//    }
//}

enableFeaturePreview("VERSION_CATALOGS")
//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        google()
//        mavenCentral()
//    }
//}
includeBuild("build-logic")
rootProject.name = "JmdroidDemo"
include(":app")
