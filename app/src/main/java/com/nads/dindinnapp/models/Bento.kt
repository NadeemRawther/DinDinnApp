package com.nads.dindinnapp.models


import com.google.gson.annotations.SerializedName

data class Bento(
    @SerializedName("image")
    val image: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("title")
    val title: String
)