package com.arbi.gihubapp.api


import com.arbi.gihubapp.data.model.DetailUserResponse
import com.arbi.gihubapp.data.model.User
import com.arbi.gihubapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val token = "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"
interface Api {

    @GET("search/users")
    @Headers("Authorization: token $token")

    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $token")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $token")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $token")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}