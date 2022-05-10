package com.practice.hagekotlin.network

import com.practice.hagekotlin.model.LoginResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountService {

    @POST("driver/login") //TODO: Check if driver and user is separated!
    suspend fun login(
        @Query("fname") firstName: String,
        @Query("lname") lastName: String,
        @Query("p_no") personalNo: String
    ): LoginResponse
}