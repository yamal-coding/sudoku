buildscript {
    dependencies {
        classpath(libs.google.services.plugin)
        classpath(libs.crashlytics.plugin)
    }
}

plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.compose.compiler) apply false
}

detekt {
    source.setFrom(rootProject.rootDir)
    config.setFrom("$projectDir/detekt.yml")
    buildUponDefaultConfig = true
}

subprojects {
    val currentModule = name

    if (currentModule == "app") {
        val isReleaseBuildType = gradle.startParameter.taskRequests.toString().contains("Release")
        if (isReleaseBuildType) {
            apply(plugin = "com.google.gms.google-services")
            apply(plugin = "com.google.firebase.crashlytics")
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}

tasks.register("ci") {
    dependsOn("app:test", "app:lint", "app:assembleDebug", "detekt")
}