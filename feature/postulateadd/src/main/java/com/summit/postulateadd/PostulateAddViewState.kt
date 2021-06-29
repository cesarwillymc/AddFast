package com.summit.postulateadd

import com.summit.commons.ui.base.BaseViewState

sealed class PostulateAddViewState:BaseViewState {
    /** Register info state add**/
    object Loading : PostulateAddViewState()

    object Complete : PostulateAddViewState()

    object Error : PostulateAddViewState()


    fun isLoading() = this is Loading

    fun isComplete() = this is Complete

    fun isError() = this is Error

    /** Edit Text State Especiality **/
    object EspecialityEmpty : PostulateAddViewState()
    object EspecialityError : PostulateAddViewState()
    object EspecialitySucces : PostulateAddViewState()

    fun isEspecialityEmpty() = this is EspecialityEmpty
    fun isEspecialityError() = this is EspecialityError
    fun isEspecialitySucces() = this is EspecialitySucces

    /** Edit Text State Resume **/
    object ResumeEmpty : PostulateAddViewState()
    object ResumeError : PostulateAddViewState()
    object ResumeSucces : PostulateAddViewState()

    fun isResumeEmpty() = this is ResumeEmpty
    fun isResumeError() = this is ResumeError
    fun isResumeSucces() = this is ResumeSucces

    /** Edit Text State Difference **/
    object DifferenceEmpty : PostulateAddViewState()
    object DifferenceError : PostulateAddViewState()
    object DifferenceSucces : PostulateAddViewState()

    fun isDifferenceEmpty() = this is DifferenceEmpty
    fun isDifferenceError() = this is DifferenceError
    fun isDifferenceSucces() = this is DifferenceSucces

    /** Edit Text State Phone **/
    object PhoneEmpty : PostulateAddViewState()
    object PhoneError : PostulateAddViewState()
    object PhoneSucces : PostulateAddViewState()

    fun isPhoneEmpty() = this is PhoneEmpty
    fun isPhoneError() = this is PhoneError
    fun isPhoneSucces() = this is PhoneSucces
}