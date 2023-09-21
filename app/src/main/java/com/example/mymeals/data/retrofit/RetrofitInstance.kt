package com.example.mymeals.data.retrofit

import com.example.mymeals.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private const val BASE_URL = "https://themealdb.p.rapidapi.com/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", BuildConfig.API_KEY)
                .addHeader("X-RapidAPI-Host", "themealdb.p.rapidapi.com")
                .build()
            chain.proceed(request)
        }
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}