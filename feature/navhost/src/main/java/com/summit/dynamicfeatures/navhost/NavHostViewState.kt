package com.summit.dynamicfeatures.navhost

import com.summit.commons.ui.base.BaseViewState


sealed class NavHostViewState : BaseViewState {

    object EmptyScreen : NavHostViewState()

    object FullScreen : NavHostViewState()

    object NavigationScreen : NavHostViewState()

    object AppBarScreen : NavHostViewState()

    fun isFullScreen() = this is FullScreen

    fun isEmptyScreen() = this is EmptyScreen

    fun isAppBar() = this is AppBarScreen

    fun isNavigationScreen() = this is NavigationScreen


}
