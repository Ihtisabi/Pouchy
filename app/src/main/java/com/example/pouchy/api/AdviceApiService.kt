package com.example.pouchy.api


import retrofit2.Call
import retrofit2.http.GET


interface AdviceApiService {
    @GET("advice")
    fun getRandomAdvice(): Call<AdviceResponse>
}
