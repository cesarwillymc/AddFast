

package com.summit.home

import com.summit.commons.ui.base.BaseViewState


sealed class HomeViewState : BaseViewState {


    object Loading : HomeViewState()


    object Error : HomeViewState()


    object AddToFavorite : HomeViewState()


    object AddedToFavorite : HomeViewState()


    object AlreadyAddedToFavorite : HomeViewState()


    object Dismiss : HomeViewState()


    fun isLoading() = this is Loading


    fun isError() = this is Error


    fun isAddToFavorite() = this is AddToFavorite


    fun isAddedToFavorite() = this is AddedToFavorite


    fun isAlreadyAddedToFavorite() = this is AlreadyAddedToFavorite


    fun isDismiss() = this is Dismiss
}
