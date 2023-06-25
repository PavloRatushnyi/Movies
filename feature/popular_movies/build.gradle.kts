plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.pavloratushnyi.movies.feature.popular_movies"

    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {

    implementation(project(":model"))
    implementation(project(":resource"))
    implementation(project(":domain"))
    implementation(project(":shared_composables"))

    implementation(libs.compose.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.material.three)
    implementation(libs.runtime.ktx)
    implementation(libs.runtime.compose)
    implementation(libs.viewmodel.ktx)
    implementation(libs.viewmodel.compose)
    implementation(libs.hilt.core)
    implementation(libs.navigation.compose)
    implementation(libs.navigation.compose.hilt)

    debugImplementation(libs.compose.tooling)

    testImplementation(project(":main_dispatcher_extension"))
    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
}