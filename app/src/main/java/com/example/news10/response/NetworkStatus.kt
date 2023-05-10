package com.example.news10.response

sealed class NetworkStatus {

    object Available: NetworkStatus()
    object Unavailable: NetworkStatus()

}