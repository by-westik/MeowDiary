package com.example.meowdiary.di.module

import android.app.Application
import com.example.meowdiary.model.db.Note.NoteDao
import com.example.meowdiary.model.db.Note.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Singleton
    @Provides
    fun getNoteDatabase(app: Application): NoteDatabase {
       return NoteDatabase.getDatabase(app)
    }

    @Singleton
    @Provides
    fun getDao(noteDB: NoteDatabase): NoteDao {
        return noteDB.getNoteDao()
    }

}