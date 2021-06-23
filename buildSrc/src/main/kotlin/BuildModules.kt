object BuildModules {
    const val APP = ":app"
    const val CORE = ":core"

    object Features {
        const val HOME = ":feature:home"
        const val NAVHOST = ":feature:navhost"
        const val PROFILE = ":feature:profile"
        const val POSTULATE = ":feature:postulate"
        const val OFFERT = ":feature:offert"
        const val AUTHENTIFICATION = ":feature:authentification"
        const val CAMERAX = ":feature:camerax"

    }

    object Commons {
        const val UI = ":commons:ui"
        const val VIEW = ":commons:views"
    }

    object Libraries {
        const val TEST_UTILS = ":libraries:test_utils"
    }
}
