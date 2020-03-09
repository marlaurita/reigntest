package com.reign.reigntest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class  ResponseHits (
    @SerializedName("hits")
    val hits: ArrayList<Report>?
)

@Parcelize
data class Report (
    @SerializedName("story_title")
    val storyTitle : String?,
    @SerializedName("title")
    val title : String?,
    @SerializedName("author")
    val author : String?,
    @SerializedName("comment_text")
    val commentText : String?,
    @SerializedName("created_at")
    val createdAt : String?,
    @SerializedName("objectID")
    val id: String

) : Parcelable