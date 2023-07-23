import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.gradle.versions) apply true
    alias(libs.plugins.version.catalog.update) apply true
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    fun isNonStable(version: String): Boolean {
        return setOf("dev", "alpha", "beta", "rc", "m").any { qualifier ->
            version.lowercase().contains(qualifier)
        }
    }

    fun isStable(version: String): Boolean {
        return !isNonStable(version)
    }

    rejectVersionIf {
        isNonStable(candidate.version) && isStable(currentVersion)
    }
}