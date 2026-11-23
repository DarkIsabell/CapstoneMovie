package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("results")
    val results: List<MovieReviewItem>? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
) {
    data class MovieReviewItem(
        @SerializedName("author")
        val author: String? = null,
        @SerializedName("author_details")
        val authorDetails: AuthorDetail? = null,
        @SerializedName("content")
        val content: String? = null,
        @SerializedName("created_at")
        val createdAt: String? = null,
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("url")
        val url: String? = null,
        @SerializedName("updated_at")
        val updatedAt: String? = null
    )

    data class AuthorDetail(
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("username")
        val username: String? = null,
        @SerializedName("avatar_path")
        val avatarPath: String? = null,
        @SerializedName("rating")
        val rating: Double? = null
    )
}