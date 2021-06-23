package com.summit.offert.create

import com.summit.commons.ui.base.BaseViewState

sealed class CreateAddViewState:BaseViewState {
    /** Register info state**/
    object Loading : CreateAddViewState()

    object Complete : CreateAddViewState()

    object Error : CreateAddViewState()


    fun isLoading() = this is Loading

    fun isComplete() = this is Complete

    fun isError() = this is Error
}