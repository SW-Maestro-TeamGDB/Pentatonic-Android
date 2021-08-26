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
    private const val serverBaseUrl = "https://api.penta-tonic.com/api/"

    val apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(serverBaseUrl)
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor())
                .build()
        )
        .addCustomTypeAdapter(CustomType.URL, urlTypeAdapter)
        .addCustomTypeAdapter(CustomType.JWT, jwtTypeAdapter)
        .addCustomTypeAdapter(CustomType.NAME, nameTypeAdapter)
        .addCustomTypeAdapter(CustomType.OBJECTID, objectIdAdapter)
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

/**
 * 로그인 요청 시 반환되는 JWT 커스텀 타입을 String 으로 변환해주는 타입 어댑터
 */
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

/**
 * 파일 업로드 요청 시 반환되는 URL 커스텀 타입을 String 으로 변환해주는 타입 어댑터
 */
val urlTypeAdapter = object : CustomTypeAdapter<String> {
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


/**
 * 커버 업로드 요청 시 반환되는 NAME (커버 명) 커스텀 타입을 String 으로 변환해주는 타입 어댑터
 */
val nameTypeAdapter = object : CustomTypeAdapter<String> {
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

/**
 * 커버 업로드 요청 시 반환되는 OBJECT_ID (커버 명) 커스텀 타입을 String 으로 변환해주는 타입 어댑터
 */
val objectIdAdapter = object : CustomTypeAdapter<String> {
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