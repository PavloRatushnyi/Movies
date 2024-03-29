plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.pavloratushnyi.movies"

    compileSdk = 33

    defaultConfig {
        applicationId = "com.pavloratushnyi.movies"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kapt {
        correctErrorTypes = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {

    implementation(project(":model"))
    implementation(project(":feature:movie_details"))
    implementation(project(":feature:movies"))

    implementation(libs.compose.preview)
    implementation(libs.compose.material.three)
    implementation(libs.activity.compose)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.navigation.compose)

    debugImplementation(libs.compose.tooling)

    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
}