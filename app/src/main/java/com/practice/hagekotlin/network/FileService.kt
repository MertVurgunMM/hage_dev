package com.practice.hagekotlin.network

import com.practice.hagekotlin.model.Category
import com.practice.hagekotlin.model.CategoryContent
import com.practice.hagekotlin.model.VersionLog
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FileService {

    @GET("app/pdf/{filename}")
    suspend fun download(
        @Path(value = "filename") fileName: String,
        @Query(value = "fname") firstName: String,
        @Query(value = "lname") lastName: String,
        @Query(value = "p_no") personalNumber: String
    ): ResponseBody

    @GET("app/categories")
    suspend fun getCategories(
        @Query(value = "fname") firstName: String,
        @Query(value = "lname") lastName: String,
        @Query(value = "p_no") personalNumber: String
    ): List<Category>

    @GET("app/contents")
    suspend fun getContents(
        @Query(value = "fname") firstName: String,
        @Query(value = "lname") lastName: String,
        @Query(value = "p_no") personalNumber: String
    ): List<CategoryContent>

    @GET("app/logs")
    suspend fun getLogs(
        @Query(value = "fname") firstName: String,
        @Query(value = "lname") lastName: String,
        @Query(value = "p_no") personalNumber: String
    ): List<VersionLog>
}
