package com.example.movie.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiService {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org"  // Correctly declare BASE_URL
        private var retrofit: Retrofit? = null

        fun getInstance(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }
}
