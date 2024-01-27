import org.gradle.api.JavaVersion

object ProjectJavaVersion {
    const val integer = 17
    const val string = integer.toString()
    val gradle = JavaVersion.VERSION_17
}

object Android {
    const val compileSdk = 34
    const val targetSdk = compileSdk
    const val minSdk = 26
}

enum class AppTarget(val raw: String) {
    Android("android"),
    Ios("ios"),
    ;
}

enum class AppBuildType(val raw: String) {
    Debug("debug"),
    Release("release"),
    ;
}

enum class AppProductFlavor(val raw: String) {
    Dev("dev"),
    Uat("uat"),
    Prod("prod"),
    ;
}

enum class Dimension(val raw: String) {
    Environment("environment"),
    ;
}

enum class BuildConfigField(val raw: String) {
    ApiBaseUrl("apiBaseUrl"),
    IsDev("isDev")
    ;
}