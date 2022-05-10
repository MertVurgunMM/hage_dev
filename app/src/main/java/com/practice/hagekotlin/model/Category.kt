package com.practice.hagekotlin.model

import com.google.gson.annotations.SerializedName

data class Category(
    val id: String,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "created_at") val createdTime: String? = null,
    @SerializedName(value = "updated_at") val updatedTime: String? = null
)

data class CategoryContent(
    val id: Int? = null,
    @SerializedName(value = "category_id") val categoryId: String? = null,
    @SerializedName(value = "headline") val headline: String,
    @SerializedName(value = "file") val path: String? = null,
    @SerializedName(value = "created_at") val createdTime: String? = null,
    @SerializedName(value = "updated_at") val updatedTime: String? = null
)

data class VersionLog(
    val id: Int,
    @SerializedName(value = "user_id") val userId: Int,
    @SerializedName(value = "text") val text: String,
    @SerializedName(value = "category_id") val categoryId: String? = null,
    @SerializedName(value = "content_id") val contentId: String? = null,
    @SerializedName(value = "created_at") val createdTime: String? = null,
    @SerializedName(value = "updated_at") val updatedTime: String? = null
)