
interface BuildType {

    companion object {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    val isMinifyEnabled: Boolean
    val isTestCoverageEnabled: Boolean
    val isShinkResource:Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
    override val isTestCoverageEnabled = true
    override val isShinkResource=false

    const val applicationIdSuffix = ".debug"
    const val versionNameSuffix = "-DEBUG"
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = true
    override val isTestCoverageEnabled = false
    override val isShinkResource=false
}
