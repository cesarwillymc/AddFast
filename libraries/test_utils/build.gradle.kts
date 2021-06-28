
import extension.*
import org.gradle.kotlin.dsl.testRuntimeOnly

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
    // (Required) Writing and executing Unit Tests on the JUnit Platform
    testImplementation(dependency.TestDependencies.JUNIT5_API)
    testRuntimeOnly(dependency.TestDependencies.JUNIT5_ENGINE)
    implementation(dependency.TestDependencies.RULES)

}