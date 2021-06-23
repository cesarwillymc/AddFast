package com.summit.offert.myadds

import com.summit.commons.ui.base.BaseViewState


sealed class MyAddViewState : BaseViewState {


    object Loading : MyAddViewState()


    object Error : MyAddViewState()


    object Complete : MyAddViewState()

    object Empty : MyAddViewState()


    fun isLoading() = this is Loading


    fun isError() = this is Error


    fun isComplete() = this is Complete

    fun isEmpty() = this is Empty
}
