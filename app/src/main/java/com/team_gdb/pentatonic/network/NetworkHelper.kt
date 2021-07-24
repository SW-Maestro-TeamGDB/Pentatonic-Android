package com.team_gdb.pentatonic.network

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.team_gdb.pentatonic.base.BaseApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response


object NetworkHelper {
    private const val serverBaseUrl = "https://api.pukuba.dev/api/"

    val apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(serverBaseUrl)
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor())
                .build()
        )
        .build()
}

private class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", BaseApplication.prefs.token ?: "")
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}
