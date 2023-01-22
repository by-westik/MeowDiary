package com.example.meowdiary.presentation.utils

import android.graphics.Color
import java.util.Random

object ColorGenerator {

    fun generateColor(): Int {
        val nextInt = Random().nextInt(0xffffff + 1)
        val colorCode = String.format("#%06x", nextInt)
        return Color.parseColor(colorCode)
    }
}