plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.buildKonfig) apply false
    alias(libs.plugins.kotlinx.parcelize) apply false
    alias(libs.plugins.spotless)
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target("**/*.kt")

        targetExclude()

        diktat(libs.versions.diktat.get()).configFile("diktat-analysis.yml")

        // Bump if tweaking the custom step
        // (required to retain performance:
        // https://javadoc.io/doc/com.diffplug.spotless/spotless-plugin-gradle/latest/com/diffplug/gradle/spotless/FormatExtension.html#bumpThisNumberIfACustomStepChanges-int-)
        bumpThisNumberIfACustomStepChanges(1)
    }
}

setupEnvironmentProperties()

fun setupEnvironmentProperties() {
    val flavor = buildConfigFlavor()

    if (flavor == null) {
        if (!isFlavourIndependentTask()) {
            error(
                """
                Could not detect the product flavour.

                Running tasks : ${gradle.startParameter.taskNames.joinToString()}

                If building Android ensure you are prefixing the task correctly with "androidApp"
                e.g. ./gradlew androidApp:bundleProdExternalRelease
                """
            )
        }
        logger.warn("Flavour independent task being run. Not generating BuildKonfig.")
        return
    }

    logger.warn("Detected BuildKonfig. Flavour: $flavor")
    (project.subprojects + rootProject).forEach { subProject ->
        // Flavor is passed thorough properties on iOS, and detected from the gradle command on Android
        // This ensures that the buildkonfig library picks it up either way
        subProject.setProperty("buildkonfig.flavor", flavor.raw)
    }
}

project.afterEvaluate {
    tasks.getByPath(":shared:preBuild").apply {
        dependsOn(task("installGitHooks", Copy::class) {
            from(
                File(rootProject.rootDir, "scripts/hooks/pre-commit.sh"),
            )
            into { File(rootProject.rootDir, ".git/hooks") }

            fileMode = 0b111101101
        })
    }
}