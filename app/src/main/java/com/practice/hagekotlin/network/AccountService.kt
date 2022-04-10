package com.practice.hagekotlin.network

import com.practice.hagekotlin.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountService {

    @GET("driver/login") //TODO: Check if driver and user is separated!
    fun login(
        @Query("fname") firstName: String,
        @Query("lname") lastName: String,
        @Query("pno") personalNo: String
    ): BaseResponse
}