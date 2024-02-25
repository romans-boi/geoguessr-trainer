import com.project.starter.easylauncher.filter.ChromeLikeFilter
import com.project.starter.easylauncher.filter.ColorRibbonFilter
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.ksp)
    alias(libs.plugins.easyLauncher)
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

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions.add(Dimension.Environment.raw)

    productFlavors {
        /* Environment */
        create(AppProductFlavor.Dev.raw) {
            isDefault = true
            dimension = Dimension.Environment.raw
            resValue("string", "app_name", AppProductFlavor.Dev.appName)
        }

        create(AppProductFlavor.Uat.raw) {
            dimension = Dimension.Environment.raw
            resValue("string", "app_name", AppProductFlavor.Uat.raw)
        }

        create(AppProductFlavor.Prod.raw) {
            dimension = Dimension.Environment.raw
            resValue("string", "app_name", AppProductFlavor.Prod.appName)
        }
    }

    kotlin {
        jvmToolchain(ProjectJavaVersion.integer)
    }
}

easylauncher {
    val environments = android.productFlavors
        .filter { it.dimension == Dimension.Environment.raw }
        .map { it.name }
    val buildTypes = android.buildTypes.names
        .map { it.uppercaseFirstChar() }
    val version = with(android.defaultConfig) { "${versionName}.${versionCode}" }
    environments.forEach { env ->
        buildTypes.forEach { buildType ->
            val variantName = "$env$buildType"
            variants.register(variantName) {
                setFilters(
                    listOfNotNull(
                        chromeLike(
                            label = version,
                            ribbonColor = "#BB154734",
                            labelColor = "#FFC6E6EE",
                            gravity = ChromeLikeFilter.Gravity.TOP
                        ),
                        chromeLike(
                            label = env.uppercase(),
                            ribbonColor = "#BB154734",
                            labelColor = "#FFC6E6EE",
                            gravity = ChromeLikeFilter.Gravity.BOTTOM
                        )
                    )
                )
            }
        }
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
    implementation(libs.androidx.compose.lifecycle)
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