//package com.example.movie.service
//
//import com.example.movie.model.User
//import retrofit2.Call
//import retrofit2.http.Body
//import retrofit2.http.POST
//
//interface UserService {
//    @POST("/user/signup")
//    fun signUp(@Body user: User): Call<User>
//
//    @POST("/user/signin")
//    fun signIn(@Body user: User): Call<User>
//
//    @POST("/user/update")
//    fun updateProfile(@Body user: User): Call<User>
//
//    @POST("/user/reset_password")
//    fun resetPassword(@Body email: String): Call<Void>
//}