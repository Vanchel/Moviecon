package com.vanchel.moviecon.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

private const val PARAM_API_KEY = "api_key"

/**
 * @author Иван Тимашов
 *
 * [Interceptor], подставляющий в параметр запроса ключ [apiKey].
 *
 * @property apiKey ключ доступа к API сервиса tmdb.org
 */
class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter(PARAM_API_KEY, apiKey)
            .build()
        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}