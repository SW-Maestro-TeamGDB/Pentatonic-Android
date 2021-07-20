package com.team_gdb.pentatonic.network

import com.apollographql.apollo.ApolloClient


object NetworkHelper {
    private const val serverBaseUrl = "https://api.pukuba.dev/api/"

    val apolloClient = ApolloClient.builder()
        .serverUrl(serverBaseUrl)
        .build()

}
