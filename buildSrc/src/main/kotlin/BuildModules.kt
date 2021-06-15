object BuildModules {
    const val APP = ":app"
    const val CORE = ":core"

    object Features {
        const val HOME = ":feature:home"
        const val NAVHOST = ":feature:navhost"
        const val PROFILE = ":feature:profile"
        const val POSTULATE = ":feature:postulate"
        const val AUTHENTIFICATION = ":feature:authentification"

    }

    object Commons {
        const val UI = ":commons:ui"
        const val VIEW = ":commons:views"
    }

    object Libraries {
        const val TEST_UTILS = ":libraries:test_utils"
    }
}
