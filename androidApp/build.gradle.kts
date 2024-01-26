plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlinx.parcelize)
}

android {
    namespace = "com.geotrainer.android"
    compileSdk = Android.compileSdk
    defaultConfig {
        applicationId = "com.geotrainer.android"
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk

        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = ProjectJavaVersion.gradle
        targetCompatibility = ProjectJavaVersion.gradle
    }

    kotlinOptions {
        jvmTarget = ProjectJavaVersion.string
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
            isMinifyEnabled = false
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = isMinifyEnabled

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions.add(Dimension.Environment.raw)

    productFlavors {
        /* Environment */
        create(AppProductFlavor.Dev.raw) {
            isDefault = true
            dimension = Dimension.Environment.raw
        }

        create(AppProductFlavor.Uat.raw) {
            dimension = Dimension.Environment.raw
        }

        create(AppProductFlavor.Prod.raw) {
            dimension = Dimension.Environment.raw
        }
    }

    kotlin {
        jvmToolchain(ProjectJavaVersion.integer)
    }
}

dependencies {
    /* Shared code */
    implementation(projects.shared)

    /* Android */
    implementation(libs.androidx.core.core)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)

    /* DI */
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    /* Firebase */
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.analytics)
//    implementation(libs.firebase.playintegrity)
//    implementation(libs.firebase.appcheckdebug)

    /* Networking */
    implementation(libs.ktor.okhttp)
    implementation(libs.ktor.serialization)

    /* Material */
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)

    /* Compose */
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.ui.toolingpreview)
    implementation(libs.coil.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)

    /* Lifecycle */
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodelCompose)

    /* Accompanist */
    implementation(libs.google.accompanist.systemuicontroller)

    /* Compose Destinations */
    implementation(libs.composeDestinations.core)
    ksp(libs.composeDestinations.ksp)

    /* Testing */
    testImplementation(kotlin("test"))
    testImplementation(libs.test.junit.junit)
    testImplementation(libs.test.turbine)
    testImplementation(libs.test.parameter.injector)
}