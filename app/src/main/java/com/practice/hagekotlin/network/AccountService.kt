package com.practice.hagekotlin.network

import com.practice.hagekotlin.model.BaseResponse
import okhttp3.ResponseBody
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountService {

    @POST("driver/login") //TODO: Check if driver and user is separated!
    suspend fun login(
        @Query("fname") firstName: String,
        @Query("lname") lastName: String,
        @Query("p_no") personalNo: String
    ): BaseResponse
}