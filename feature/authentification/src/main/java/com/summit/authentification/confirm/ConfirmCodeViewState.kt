package com.summit.authentification.confirm

import com.summit.commons.ui.base.BaseViewState


sealed class ConfirmCodeViewState : BaseViewState {
    /** Edit Text State send code**/
    object Loading : ConfirmCodeViewState()

    object Complete : ConfirmCodeViewState()
    object InComplete : ConfirmCodeViewState()

    object Error : ConfirmCodeViewState()


    fun isLoading() = this is Loading

    fun isComplete() = this is Complete
    fun isInComplete() = this is InComplete

    fun isError() = this is Error

    /** Edit Text State code sms **/
    object CodeEmpty : ConfirmCodeViewState()
    object CodeError : ConfirmCodeViewState()
    object CodeSucces : ConfirmCodeViewState()

    fun isCodeEmpty() = this is CodeEmpty

    fun isCodeError() = this is CodeError

    fun isCodeSucces() = this is CodeSucces
}