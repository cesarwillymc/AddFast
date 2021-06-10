package com.summit.home.home.state

import com.summit.commons.ui.base.BaseViewState


sealed class HomeViewState : BaseViewState {


    object Loading : HomeViewState()


    object Error : HomeViewState()


    object Complete : HomeViewState()

    object Empty : HomeViewState()


    fun isLoading() = this is Loading


    fun isError() = this is Error


    fun isComplete() = this is Complete

    fun isEmpty() = this is Empty
}
