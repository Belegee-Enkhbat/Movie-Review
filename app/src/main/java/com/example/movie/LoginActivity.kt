package com.example.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.model.RequestTokenBody
import com.example.movie.model.RequestTokenResponse
import com.example.movie.model.SessionResponse
import com.example.movie.service.MovieApiInterface
import com.example.movie.service.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRequestToken() // Start token request process
    }

    private fun getRequestToken() {
        val movieApiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        val call: Call<RequestTokenResponse> = movieApiService.getRequestToken()

        call.enqueue(object : Callback<RequestTokenResponse> {
            override fun onResponse(call: Call<RequestTokenResponse>, response: Response<RequestTokenResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val requestToken = response.body()!!.request_token
                    Log.d("LoginActivity", "Request Token: $requestToken")

                    // Redirect user to TMDB for authentication
                    val authUrl = "https://www.themoviedb.org/authenticate/$requestToken?redirect_to=yourapp://approved"
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)))
                } else {
                    Log.e("LoginActivity", "Failed to get request token")
                }
            }

            override fun onFailure(call: Call<RequestTokenResponse>, t: Throwable) {
                Log.e("LoginActivity", "Error: ${t.message}")
            }
        })
    }

    private fun createSessionId(requestToken: String) {
        val movieApiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        val req = RequestTokenBody(requestToken)

        val call: Call<SessionResponse> = movieApiService.createSessionId(req)
        call.enqueue(object : Callback<SessionResponse> {
            override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val sessionId = response.body()!!.session_id
                    Log.d("LoginActivity", "Session ID: $sessionId")

                    // Navigate to the main activity with session ID
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("SESSION_ID", sessionId)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("LoginActivity", "Failed to create session ID")
                }
            }

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                Log.e("LoginActivity", "Error: ${t.message}")
            }
        })
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        val uri: Uri? = intent.data
        if (uri != null && uri.host == "approved") {
            val requestToken = uri.getQueryParameter("request_token")
            if (requestToken != null) {
                createSessionId(requestToken)
            } else {
                Log.e("LoginActivity", "Request token not found")
            }
        } else {
            Log.e("LoginActivity", "Invalid redirect URI or host")
        }
    }
}
