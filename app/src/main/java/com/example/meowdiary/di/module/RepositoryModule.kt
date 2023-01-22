package com.example.meowdiary.di.module

import com.example.meowdiary.model.CatFactRepository
import com.example.meowdiary.model.api.CatFactService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCatFactRepository(
        catFactService: CatFactService
    ): CatFactRepository =
        CatFactRepository(catFactService)
}