package com.example.meowdiary.model.db.Note


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val noteBody: String,
    val created_at: Date,
    val updated_at: Date?
    //TODO добавить дату создания и изменения
) : Parcelable

// TODO убрать parcelize
// TODO поменять названия столбцов
// TODO добавить хранение цвета (?)