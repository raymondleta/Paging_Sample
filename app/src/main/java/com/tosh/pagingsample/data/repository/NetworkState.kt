package com.tosh.pagingsample.data.repository


enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}
class NetworkState(val status: Status, val message: String) {
    companion object{
        val LOADED: NetworkState
        val LOADING: NetworkState
        val FAILED: NetworkState
        val ENDOFLIST: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Running")
            FAILED = NetworkState(Status.SUCCESS, "Something went wrong")
            ENDOFLIST = NetworkState(Status.FAILED, "You have reached end of page")
        }
    }

}