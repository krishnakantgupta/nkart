package com.kk.nkart.data

data class LoadingStatusDetails<out T>(val status: LoadStatus, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): LoadingStatusDetails<T> {
            return LoadingStatusDetails(LoadStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): LoadingStatusDetails<T> {
            return LoadingStatusDetails(LoadStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): LoadingStatusDetails<T> {
            return LoadingStatusDetails(LoadStatus.LOADING, data, null)
        }

        fun <T> loadingMore(data: T?): LoadingStatusDetails<T> {
            return LoadingStatusDetails(LoadStatus.LOAD_MORE, data, null)
        }

    }

}