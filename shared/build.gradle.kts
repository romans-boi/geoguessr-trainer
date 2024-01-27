import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import com.codingfeline.buildkonfig.gradle.TargetConfigDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.moko.resources)
    alias(libs.plugins.moko.swift)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlinx.parcelize)
    alias(libs.plugins.ksp)
}

kotlin {
    targets.all {
        compilations.all {
            kotlinOptions {
                // Ignore warnings for non-stable expect/ actual classes.
                freeCompilerArgs += "-Xexpect-actual-classes"
            }
        }
    }

    applyDefaultHierarchyTemplate()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = ProjectJavaVersion.string
            }
        }
    }

    jvmToolchain(ProjectJavaVersion.integer)
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            /**
             * Make sure any new dependencies are added to license_plist.yml for iOS
             * (https://github.com/mono0926/LicensePlist)
             */

            /* Networking */
            implementation(libs.ktor.core)
            implementation(libs.ktor.contentnegotiation)
            implementation(libs.ktor.resources)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.serialization.kotlixjson)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.auth)

            /* Coroutines */
            implementation(libs.kotlinx.coroutines.core)

            /* Localisation */
            api(libs.moko.resources)
            api(libs.moko.swift)

            /* DI */
            api(libs.koin.core)

            /* Datetime */
            api(libs.kotlinx.datetime)

            /* Logging */
            api(libs.kermit.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            /* Networking */
            implementation(libs.test.ktor.mock)

            /* Mocking */
            implementation(libs.test.mockative)

            /* Coroutines */
            implementation(libs.test.turbine)
            implementation(libs.kotlinx.coroutines.test)

            /* Localisation */
            api(libs.moko.resources.test)
        }
        androidMain.dependencies {
            /* Networking */
            implementation(libs.ktor.okhttp)

            /* Coroutines */
            implementation(libs.kotlinx.coroutines.android)

            /* ViewModel */
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.compose.runtime)

            /* DI */
            api(libs.koin.android)

            /* Localisation */
            api(libs.moko.resources.compose)
        }
        iosMain.dependencies {
            /* Networking */
            implementation(libs.ktor.darwin)
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(ProjectJavaVersion.integer))
    }
}

buildkonfig {
    packageName = "com.geotrainer.shared"
    exposeObjectWithName = "BuildKonfig"

    val flavor = project.buildConfigFlavor()

    if (flavor == null) {
        // Flavor isn't defined, codegen isn't required.
        defaultConfigs { /* No-op */ }
        return@buildkonfig
    }

    logger.warn("Generating Config: Environment: ${flavor.raw}")

    defaultConfigs {
        // Add default configs here
        booleanField(BuildConfigField.IsDev, false)

        register(Type.STRING, BuildConfigField.ApiBaseUrl, nullable = false)
    }

    targetConfigs {
        android {
            // Add android configs here
        }
        ios {
            // Add ios configs here
        }
    }

    targetConfigs(AppProductFlavor.Dev.raw) {
        common {
            // Add dev config here
            booleanField(BuildConfigField.IsDev, true)
            stringField(BuildConfigField.ApiBaseUrl, "http://localhost:8080/")
        }
    }

    targetConfigs(AppProductFlavor.Uat.raw) {
        common {
            // Add uat config here
            // FIXME Actual URL TBD
            stringField(BuildConfigField.ApiBaseUrl, "http://localhost:8080/")
        }
    }

    targetConfigs(AppProductFlavor.Prod.raw) {
        common {
            // Add prod config here
            // FIXME Actual URL TBD
            stringField(BuildConfigField.ApiBaseUrl, "http://localhost:8080/")
        }
    }
}

android {
    namespace = "com.geotrainer.shared"
    compileSdk = Android.compileSdk
    defaultConfig {
        minSdk = Android.minSdk
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // This solves the following issue:
    // "Expected object 'MR' has no actual declaration in module <mpp-library_debug> for JVM"
    // https://github.com/icerockdev/moko-resources/issues/510
    // but should be removed when they end up fixing it
    sourceSets {
        getByName("main").java.srcDirs("build/generated/moko/androidMain/src")
    }
}

fun NamedDomainObjectContainer<TargetConfigDsl>.android(block: TargetConfigDsl.() -> Unit) {
    create(AppTarget.Android.raw) { block(this) }
}

fun NamedDomainObjectContainer<TargetConfigDsl>.ios(block: TargetConfigDsl.() -> Unit) {
    create(AppTarget.Ios.raw) { block(this) }
}

fun NamedDomainObjectContainer<TargetConfigDsl>.common(block: TargetConfigDsl.() -> Unit) {
    android { block(this) }
    ios { block(this) }
}

fun TargetConfigDsl.register(
    type: FieldSpec.Type,
    name: BuildConfigField,
    nullable: Boolean = false,
) {
    buildConfigField(type, name.raw, if (nullable) null else "", nullable = nullable)
}

fun TargetConfigDsl.stringField(
    name: BuildConfigField,
    value: String?,
    nullable: Boolean = false,
) {
    buildConfigField(FieldSpec.Type.STRING, name.raw, value, nullable = nullable)
}

fun TargetConfigDsl.booleanField(
    name: BuildConfigField,
    value: Boolean,
) {
    buildConfigField(FieldSpec.Type.BOOLEAN, name.raw, value.toString())
}

