package com.arbi.gihubapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
//    val login: String,
//    val id: Int,
//    val avatar_url: String

    val login : String?,
    val id : Int,
    val avatar_url : String?,
    val followers_url : String?,
    val following_url : String?,
    val name : String?,
    val following : Int,
    val followers : Int,
    val bio: String?,
    val company: String?,
    val location: String?,
    val blog: String?,
    val public_repos: String?,

): Parcelable
