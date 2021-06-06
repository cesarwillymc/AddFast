package dependency


object TestDependencies {
    const val JUNIT = "junit:junit:${DependencyVersion.JUNIT}"
    const val EXT = "androidx.test.ext:junit:${DependencyVersion.EXT}"
    const val MOCKK = "io.mockk:mockk:${DependencyVersion.MOCKK}"
    const val ROOM = "androidx.room:room-testing:${DependencyVersion.ROOM}"
    const val RULES = "androidx.test:rules:${DependencyVersion.TEST}"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${DependencyVersion.COROUTINESTEST}"
    const val CORE = "androidx.test:core:${DependencyVersion.TEST}"
    const val ARCH_CORE = "androidx.arch.core:core-testing:${DependencyVersion.ARCH_CORE}"
    const val RUNNER = "androidx.test:runner:${DependencyVersion.TEST}"
    const val ROBOELECTRIC = "org.robolectric:robolectric:${DependencyVersion.ROBOELECTRIC}"
}
