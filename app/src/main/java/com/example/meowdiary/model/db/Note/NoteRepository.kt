package com.example.meowdiary.model.db.Note

import com.example.meowdiary.model.db.Note.NoteDao
import com.example.meowdiary.model.db.Note.Note
import javax.inject.Inject
/*
class NoteRepository @Inject constructor(private val db: NoteDatabase) {

    // TODO поменять синтаксис функций потом

    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)
    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)

}*/

class NoteRepository @Inject constructor(private val dao: NoteDao) {

    val getAllNotes = dao.getAllNotes()

    fun searchNote(query: String) = dao.searchNote(query)

    suspend fun insertNote(note: Note){
        dao.insertNote(note)
    }

    suspend fun updateNote(note: Note){
        dao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        dao.deleteNote(note)
    }
}