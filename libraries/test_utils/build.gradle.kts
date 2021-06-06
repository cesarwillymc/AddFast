
import extension.addTestsDependencies
import extension.implementation
import extension.kapt
import extension.testImplementation

plugins {
    id("commons.android-library")
}

dependencies {
    implementation(dependency.TestDependencies.MOCKK)
    implementation(dependency.TestDependencies.ROOM)
    implementation(dependency.TestDependencies.COROUTINES_TEST)
    implementation(dependency.TestDependencies.EXT)
    implementation(dependency.TestDependencies.CORE)
    implementation(dependency.TestDependencies.RUNNER)
    implementation(dependency.TestDependencies.ROBOELECTRIC)
    implementation(dependency.TestDependencies.JUNIT)
    implementation(dependency.TestDependencies.RULES)

}