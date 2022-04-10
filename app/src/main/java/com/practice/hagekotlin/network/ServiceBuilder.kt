package com.practice.hagekotlin.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder {
    companion object {
        fun <T> build(baseUrl: String, service: Class<T>): T {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(service)
        }
    }
}