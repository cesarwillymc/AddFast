package com.summit.authentification.register

import com.summit.commons.ui.base.BaseViewState

sealed class RegisterViewState:BaseViewState {
    /** Register info state**/
    object Loading : RegisterViewState()

    object Complete : RegisterViewState()

    object Error : RegisterViewState()


    fun isLoading() = this is Loading

    fun isComplete() = this is Complete

    fun isError() = this is Error

    /** Edit Text State Ruc **/
    object RucEmpty : RegisterViewState()
    object RucError : RegisterViewState()
    object RucSucces : RegisterViewState()

    fun isRucEmpty() = this is RucEmpty
    fun isRucError() = this is RucError
    fun isRucSucces() = this is RucSucces
    /** Edit Text State Dni **/
    object DniEmpty : RegisterViewState()
    object DniError : RegisterViewState()
    object DniSucces : RegisterViewState()

    fun isDniEmpty() = this is DniEmpty
    fun isDniError() = this is DniError
    fun isDniSucces() = this is DniSucces

    /** Edit Text State Name **/
    object NameEmpty : RegisterViewState()
    object NameError : RegisterViewState()
    object NameSucces : RegisterViewState()

    fun isNameEmpty() = this is NameEmpty
    fun isNameError() = this is NameError
    fun isNameSucces() = this is NameSucces
    /** Edit Text State Bussiness **/
    object BussinessEmpty : RegisterViewState()
    object BussinessError : RegisterViewState()
    object BussinessSucces : RegisterViewState()

    fun isBussinessEmpty() = this is BussinessEmpty
    fun isBussinessError() = this is BussinessError
    fun isBussinessSucces() = this is BussinessSucces
}