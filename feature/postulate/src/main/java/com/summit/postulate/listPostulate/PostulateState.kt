package com.summit.postulate.listPostulate

import com.summit.commons.ui.base.BaseViewState


sealed class PostulateState : BaseViewState {


    object Loading : PostulateState()


    object Error : PostulateState()


    object Complete : PostulateState()

    object Empty : PostulateState()


    fun isLoading() = this is Loading


    fun isError() = this is Error


    fun isComplete() = this is Complete

    fun isEmpty() = this is Empty
}
