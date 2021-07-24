package com.team_gdb.pentatonic.network

import android.content.Context
import androidx.core.net.ParseException
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.CustomTypeAdapter
import com.apollographql.apollo.api.CustomTypeValue
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.type.CustomType
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
        .addCustomTypeAdapter(CustomType.JWT, jwtTypeAdapter)
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

val jwtTypeAdapter = object : CustomTypeAdapter<String> {
    override fun decode(value: CustomTypeValue<*>): String {
        return try {
            value.value.toString()
        } catch (e: ParseException) {
            throw RuntimeException(e)
        }
    }

    override fun encode(value: String): CustomTypeValue<*> {
        return CustomTypeValue.GraphQLString(value)
    }
}