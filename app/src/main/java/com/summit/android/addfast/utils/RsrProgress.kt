package com.summit.android.addfast.utils.lifeData

data class RsrProgress<out T>(val status: Status, val progreso:Double?,val data: T?, val exception: Exception?) {
    companion object {
        fun <T> success(correcto: T?): RsrProgress<T> {
            return RsrProgress(Status.SUCCESS, null, correcto,null)
        }

        fun <T> error(msg: Exception): RsrProgress<T> {
            return RsrProgress(Status.ERROR, null,null,msg)
        }

        fun <T> loading(progreso: Double): RsrProgress<T> {
            return RsrProgress(Status.LOADING, progreso,null, null)
        }
    }
}