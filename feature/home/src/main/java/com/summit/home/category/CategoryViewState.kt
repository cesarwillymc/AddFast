package com.summit.home.category

import com.summit.commons.ui.base.BaseViewState


sealed class CategoryViewState : BaseViewState {


    object Loading : CategoryViewState()


    object Error : CategoryViewState()


    object Complete : CategoryViewState()

    object Empty : CategoryViewState()


    fun isLoading() = this is Loading


    fun isError() = this is Error


    fun isComplete() = this is Complete

    fun isEmpty() = this is Empty
}
