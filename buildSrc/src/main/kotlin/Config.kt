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

object AppNames {
    private const val CORE_NAME = "GeoTrainer"
    const val DEV = "$CORE_NAME Dev"
    const val UAT = "$CORE_NAME UAT"
    const val PROD = CORE_NAME
}

enum class AppProductFlavor(val raw: String, val appName: String) {
    Dev("dev", AppNames.DEV),
    Prod("prod", AppNames.PROD),
    Uat("uat", AppNames.UAT),
    ;
}

enum class Dimension(val raw: String) {
    Environment("environment"),
    ;
}

enum class BuildConfigField(val raw: String) {
    ApiBaseUrl("apiBaseUrl"),
    IsDev("isDev"),
    ;
}
