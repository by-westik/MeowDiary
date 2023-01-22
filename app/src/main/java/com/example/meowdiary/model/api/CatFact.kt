package com.example.meowdiary.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CatFact (
    @SerializedName("fact")
    @Expose
    var catFact: String,

    @SerializedName("length")
    @Expose
    var factLen: Int
)