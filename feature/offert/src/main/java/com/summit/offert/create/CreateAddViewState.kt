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


    /** Edit Text State Title **/
    object TitleEmpty : CreateAddViewState()
    object TitleError : CreateAddViewState()
    object TitleSucces : CreateAddViewState()

    fun isTitleEmpty() = this is TitleEmpty
    fun isTitleError() = this is TitleError
    fun isTitleSucces() = this is TitleSucces
    /** Edit Text State Desc **/
    object DescEmpty : CreateAddViewState()
    object DescError : CreateAddViewState()
    object DescSucces : CreateAddViewState()

    fun isDescEmpty() = this is DescEmpty
    fun isDescError() = this is DescError
    fun isDescSucces() = this is DescSucces

}