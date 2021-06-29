package com.summit.core.status

import com.summit.core.exception.ExceptionGeneral


data class Resource<out T>(val status: Status, val data: T?,val loading:Double?, val exception: ExceptionGeneral?){
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null,null)
        }

        fun <T> error(msg: ExceptionGeneral): Resource<T> {
            return Resource(Status.ERROR, null,null, msg)
        }

        fun <T> loading(data: Double?): Resource<T> {
            return Resource(Status.LOADING, null, data,null)
        }
    }
}
