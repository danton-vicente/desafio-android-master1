package com.picpay.desafio.android.data_remote.service

import com.picpay.desafio.android.data_remote.model.CommentsResponse
import com.picpay.desafio.android.data_remote.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserWebService {

    @GET("getUsers")
    fun getUsers(): Call<List<UserResponse>>

    @GET("getUsers/{id}")
    fun getCompleteUserInfo(
        @Path("id") id: String,
    ): Call<UserResponse>

    @GET("getUsers/{id}/comments")
    fun getUserComments(
        @Path("id") id: String,
    ): Call<List<CommentsResponse>>

    @PUT("getUsers/{id}/comments")
    fun addComments(
        @Path("id") id: String,
        @Body comments: CommentsResponse
    ): Call<CommentsResponse>
}
