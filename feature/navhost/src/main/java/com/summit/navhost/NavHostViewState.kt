

package com.summit.navhost

import com.summit.commons.ui.base.BaseViewState


sealed class NavHostViewState : BaseViewState {


    object FullScreen : NavHostViewState()

    object NavigationScreen : NavHostViewState()

    fun isFullScreen() = this is FullScreen


    fun isNavigationScreen() = this is NavigationScreen


}
