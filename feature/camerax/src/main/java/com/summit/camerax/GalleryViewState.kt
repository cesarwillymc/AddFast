package com.summit.camerax

import com.summit.commons.ui.base.BaseViewState

sealed class GalleryViewState : BaseViewState {


    object Loading : GalleryViewState()

    object Complete : GalleryViewState()

    object Error : GalleryViewState()


    fun isLoading() = this is Loading

    fun isComplete() = this is Complete

    fun isError() = this is Error
}