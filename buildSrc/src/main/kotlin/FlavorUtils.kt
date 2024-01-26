import org.gradle.StartParameter
import org.gradle.api.Project
import org.gradle.api.invocation.Gradle
import java.util.Locale

/**
 * Check iOS build phases to see where and how the arguments are being passed in for iOS builds
 */
private val testTasks = listOf(
    "iosX64Test",
    "iosSimulatorArm64Test",
    "allTests",
    "testDebugUnitTest",
)

private val flavourIndependentTasks = listOf(
    "clean",
    "spotless",
    "help",
    "signingReport",
    "dependencies",
    "buildEnvironment"
)

private val buildTaskPrefixes = listOf(
    "shared",
    "androidApp",
)

private val StartParameter.sanitisedTaskNames
    get() = taskNames.map {
        it.removePrefix(":")
    }.filter { task ->
        buildTaskPrefixes.any { task.startsWith(it) } || testTasks.contains(task)
    }

fun Project.buildConfigFlavor(): AppProductFlavor? = gradle.extractFlavorFromStartTasks()
    ?: rootProject.valueFromProperties<AppProductFlavor>("buildkonfig.flavor") { it.raw }

fun Project.isFlavourIndependentTask() = gradle.startParameter.taskNames.let { tasks ->
    tasks.all { task ->
        flavourIndependentTasks.any { task.contains(it) }
    } || tasks.isEmpty()  // Empty tasks means it's likely an Android Studio sync.
}

fun Gradle.extractFlavorFromStartTasks(): AppProductFlavor? {
    val flavors = startParameter.sanitisedTaskNames.mapNotNull {
        extractFlavor(it)
    }.toSet()

    if (flavors.size > 1) {
        error("Attempting to build multiple flavors at once. This isn't supported, build them individually.")
    }

    return flavors.firstOrNull()
}

private fun extractFlavor(task: String): AppProductFlavor? {
    if (testTasks.filter { !it.startsWith("shared") }.any { task.contains(it) }) {
        return AppProductFlavor.Dev
    }

    return AppProductFlavor.values().firstOrNull { task.lowercase(Locale.ROOT).contains(it.raw) }
}

private inline fun <reified T : Enum<T>> Project.valueFromProperties(
    key: String,
    enumMapper: (T) -> String
): T? = (rootProject.properties[key] as String?)?.takeUnless {
    it.isBlank()
}?.let { propertyString ->
    enumValues<T>().firstOrNull {
        enumMapper(it) == propertyString
    }
}