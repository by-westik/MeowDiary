package com.example.meowdiary.model.db.Note

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//TODO разобраться зачем тут version
@Database(entities = [Note::class], version = 1)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = createDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun createDatabase(context: Context) : NoteDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).build()
        }

    }

}