
package extension

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven


fun RepositoryHandler.applyDefault() {
    google()
    mavenCentral()
    jcenter()
    maven(url = "https://jitpack.io")
    maven(url = "https://developer.huawei.com/repo/") // HUAWEI Maven r
    maven("https://plugins.gradle.org/m2/")
    maven("https://ci.android.com/builds/submitted/5837096/androidx_snapshot/latest/repository")
}
