package com.summit.home.detail

import com.summit.commons.ui.base.BaseViewState


sealed class DetailAdState : BaseViewState {


    object Loading : DetailAdState()


    object Error : DetailAdState()


    object Complete : DetailAdState()

    object Empty : DetailAdState()


    fun isLoading() = this is Loading


    fun isError() = this is Error


    fun isComplete() = this is Complete

    fun isEmpty() = this is Empty
}
