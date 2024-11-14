package com.example.movie.service

import com.example.movie.WatchList
import com.example.movie.model.MovieCast
import com.example.movie.model.MovieResult
import com.example.movie.model.MovieDetail
import com.example.movie.model.MovieReviews
import com.example.movie.model.RequestTokenBody
import com.example.movie.model.RequestTokenRequest
import com.example.movie.model.RequestTokenResponse
import com.example.movie.model.SessionResponse
import com.example.movie.model.WatchListData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiInterface {
   @GET("/3/search/movie")
   fun searchMoviesByTitle(@Query("query") title: String): Call<MovieResult>

   @GET("/3/discover/movie")
   fun searchMoviesByGenre(@Query("with_genres") genreId: Int): Call<MovieResult>

   @GET("/3/search/person")
   fun searchMoviesByActor(@Query("query") actorName: String): Call<MovieResult>

   @GET("/3/movie/top_rated")
   fun getMovieList(@Query("api_key") apiKey: String = API_KEY): Call<MovieResult>

   @GET("/3/movie/now_playing")
   fun getNowPlayingList(@Query("api_key") apiKey: String = API_KEY): Call<MovieResult>

   @GET("/3/movie/popular")
   fun getPopularList(@Query("api_key") apiKey: String = API_KEY): Call<MovieResult>

   @GET("/3/movie/upcoming")
   fun getUpComingList(@Query("api_key") apiKey: String = API_KEY): Call<MovieResult>

   @GET("/3/movie/{id}")
   fun getDetail(@Path("id") id: Int, @Query("api_key") apiKey: String = API_KEY): Call<MovieDetail>

   @GET("/3/movie/{movie_id}/reviews")
   fun getReviews(@Path("movie_id") movie_id: Int, @Query("api_key") api_key:String = API_KEY): Call<MovieReviews>

   @GET("/3/movie/{movie_id}/credits")
   fun getCast(@Path("movie_id") movieId: Int): Call<MovieCast>

   @GET("/3/search/movie")
   fun getSearch(@Query("query") movieName: String, @Query("api_key") api_key:String = API_KEY): Call<MovieResult>


   @Headers("Content-Type: application/json")
   @PUT("/3/movie/{movie_id}/toggle-list-item")
   fun addToWatchlist(
      @Header("Authorization") authToken: String,
      @Path("movie_id") movieId: Int,
      @Body request: WatchListData
   ): Call<MovieResult>

   @GET("/3/account/21567329/watchlist/movies")
   fun getWatchlist(@Query("api_key") api_key:String = API_KEY): Call<MovieResult>

   @Headers("Content-Type: application/json")
   @POST("account/{account_id}/watchlist")
   fun addWatchlist(
      @Header("Authorization") sessionId: String,
      @Path("account_id") accountId: Int,
      @Body request: WatchListData
   ): Call<MovieResult>

   @GET("/3/authentication/token/new")
   fun getRequestToken(
      @Query("api_key") apiKey: String = API_KEY
   ): Call<RequestTokenResponse>

   @POST("/3/authentication/session/new")
   fun createSessionId(@Body requestTokenBody: RequestTokenBody): Call<SessionResponse>


   companion object {
      private const val API_KEY = "e2a28e6d316025c7d7992c1ef7a27a9e"
   }
}
