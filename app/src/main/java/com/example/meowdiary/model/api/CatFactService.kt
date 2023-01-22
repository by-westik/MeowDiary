package com.example.meowdiary.model.api

import retrofit2.http.GET

interface CatFactService {
    @GET("fact")
    suspend fun getCatFact(): CatFact
}