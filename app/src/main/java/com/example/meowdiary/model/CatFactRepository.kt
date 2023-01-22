package com.example.meowdiary.model

import com.example.meowdiary.model.api.CatFact
import com.example.meowdiary.model.api.CatFactService

class CatFactRepository(
    private val catFactService: CatFactService
) {
    suspend fun getCatFact(): CatFact = catFactService.getCatFact()
}