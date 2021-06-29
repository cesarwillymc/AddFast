package dependency


object TestDependencies {
    // (Required) Writing and executing Unit Tests on the JUnit Platform
    const val JUNIT5_API = "org.junit.jupiter:junit-jupiter-api:${DependencyVersion.junit5Version}"
    const val JUNIT5_ENGINE = "org.junit.jupiter:junit-jupiter-engine:${DependencyVersion.junit5Version}"
    const val EXT = "androidx.test.ext:junit:${DependencyVersion.EXT}"
    const val MOCKK = "io.mockk:mockk:${DependencyVersion.MOCKK}"
    const val ROOM = "androidx.room:room-testing:${DependencyVersion.ROOM}"
    const val RULES = "androidx.test:rules:${DependencyVersion.TEST}"
    // Truth
    const val TRUTH = "com.google.truth:truth:${DependencyVersion.truthVersion}"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${DependencyVersion.COROUTINESTEST}"
    const val CORE = "androidx.test:core:${DependencyVersion.TEST}"
    const val ARCH_CORE = "androidx.arch.core:core-testing:${DependencyVersion.ARCH_CORE}"
    const val RUNNER = "androidx.test:runner:${DependencyVersion.TEST}"
    const val ROBOELECTRIC = "org.robolectric:robolectric:${DependencyVersion.ROBOELECTRIC}"
}
