package extension


import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import dependency.TestDependencies
import org.gradle.api.Project
import java.util.*

private const val LOCAL_PROPERTIES_FILE_NAME = "local.properties"


fun getLocalProperty(propertyName: String, project: Project): String {
    val localProperties = Properties().apply {
        val localPropertiesFile = project.rootProject.file(LOCAL_PROPERTIES_FILE_NAME)
        if (localPropertiesFile.exists()) {
            load(localPropertiesFile.inputStream())
        }
    }

    return localProperties.getProperty(propertyName)?.let {
        it
    } ?: run {
        throw NoSuchFieldException("Not defined property: $propertyName")
    }
}

fun Project.getLocalProperty(propertyName: String): String {
    return getLocalProperty(propertyName, this)
}

fun DependencyHandler.debugImplementation(dependencyNotation: String): Dependency? =
    add("debugImplementation", dependencyNotation)

fun DependencyHandler.implementation(dependencyNotation: String): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.api(dependencyNotation: String): Dependency? =
    add("api", dependencyNotation)


fun DependencyHandler.kapt(dependencyNotation: String): Dependency? =
    add("kapt", dependencyNotation)


fun DependencyHandler.testImplementation(dependencyNotation: String): Dependency? =
    add("testImplementation", dependencyNotation)


fun DependencyHandler.androidTestImplementation(dependencyNotation: String): Dependency? =
    add("androidTestImplementation", dependencyNotation)

fun DependencyHandler.testRuntimeOnly(dependencyNotation: Any): Dependency? =
    add("testRuntimeOnly", dependencyNotation)

fun DependencyHandler.addTestsDependencies() {

    testImplementation(TestDependencies.MOCKK)
    testImplementation(TestDependencies.ROOM)
    testImplementation(TestDependencies.COROUTINES_TEST)
    testImplementation(TestDependencies.EXT)
    testImplementation(TestDependencies.CORE)
    testImplementation(TestDependencies.ARCH_CORE)
    testImplementation(TestDependencies.RUNNER)
    testImplementation(TestDependencies.ROBOELECTRIC)
    // (Required) Writing and executing Unit Tests on the JUnit Platform
    testImplementation(TestDependencies.JUNIT5_API)
    testRuntimeOnly(TestDependencies.JUNIT5_ENGINE)
    testImplementation(TestDependencies.RULES)
    // Truth
    testImplementation(TestDependencies.TRUTH)
    androidTestImplementation(TestDependencies.TRUTH)
}
