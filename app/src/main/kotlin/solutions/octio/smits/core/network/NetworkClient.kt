package solutions.octio.smits.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import solutions.octio.smits.core.AppConfig

fun createNetworkClient(): Retrofit {

    val okHttp = OkHttpClient.Builder()
        .build()

    return Retrofit.Builder()
        .baseUrl(AppConfig.API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttp)
        .build()
}