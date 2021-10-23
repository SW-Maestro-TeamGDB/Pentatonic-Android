package com.team_gdb.pentatonic.network

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
    private const val serverBaseUrl = "https://test.pukuba.dev/api/"

    val apolloClient: ApolloClient = ApolloClient.builder()
        .serverUrl(serverBaseUrl)
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor())
                .build()
        )
        .addCustomTypeAdapter(CustomType.URL, urlTypeAdapter)
        .addCustomTypeAdapter(CustomType.JWT, jwtTypeAdapter)
        .addCustomTypeAdapter(CustomType.ID, idTypeAdapter)
        .addCustomTypeAdapter(CustomType.OBJECTID, objectIdTypeAdapter)
        .addCustomTypeAdapter(CustomType.USERNAME, usernameTypeAdapter)
        .addCustomTypeAdapter(CustomType.DATETIME, dateTimeTypeAdapter)
        .addCustomTypeAdapter(CustomType.DATE, dateTypeAdapter)
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
 * - 필요 없어짐
 */
//val nameTypeAdapter = object : CustomTypeAdapter<String> {
//    override fun decode(value: CustomTypeValue<*>): String {
//        return try {
//            value.value.toString()
//        } catch (e: ParseException) {
//            throw RuntimeException(e)
//        }
//    }
//
//    override fun encode(value: String): CustomTypeValue<*> {
//        return CustomTypeValue.GraphQLString(value)
//    }
//}

/**
 * 커버 업로드 요청 시 반환되는 OBJECT_ID (커버 명) 커스텀 타입을 String 으로 변환해주는 타입 어댑터
 */
val objectIdTypeAdapter = object : CustomTypeAdapter<String> {
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
 * User 정보 쿼리 시 인자로 넘기는 Id 커스텀 타입을 String 으로 변환해주는 어댑터
 */
val idTypeAdapter = object : CustomTypeAdapter<String> {
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
 * 커버 정보의 Username 타입을 String 으로 변환해주는 어댑터
 */
val usernameTypeAdapter = object : CustomTypeAdapter<String> {
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
 * 커버 정보의 Username 타입을 String 으로 변환해주는 어댑터
 */
val dateTimeTypeAdapter = object : CustomTypeAdapter<String> {
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

val dateTypeAdapter = object : CustomTypeAdapter<String> {
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