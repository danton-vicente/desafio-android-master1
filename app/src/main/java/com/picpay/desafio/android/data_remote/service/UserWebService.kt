package com.picpay.desafio.android.data_remote.service

import com.picpay.desafio.android.data_remote.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserWebService {

    @GET("getUsers")
    fun getUsers(): Call<List<UserResponse>>

    @PUT("getUsers/{id}")
    fun test(
        @Path("id") id: String,
        @Body user: UserResponse
    ): Call<UserResponse>
}
