package com.reign.reigntest.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class  ResponseHits (
    @SerializedName("hits")
    val hits: ArrayList<Report>?
)

@Parcelize
@Entity
data class Report (
    @SerializedName("story_title")
    @ColumnInfo(name = "storyTitle") val storyTitle : String?,
    @SerializedName("title")
    @ColumnInfo(name = "title")val title : String?,
    @SerializedName("author")
    @ColumnInfo(name = "author")val author : String?,
    @SerializedName("comment_text")
    @ColumnInfo(name = "commentText")val commentText : String?,
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")val createdAt : String?,
    @SerializedName("objectID")
    @PrimaryKey val id: String

) : Parcelable