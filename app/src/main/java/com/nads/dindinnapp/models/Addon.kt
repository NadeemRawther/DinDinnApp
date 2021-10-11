package com.nads.dindinnapp.models


import com.google.gson.annotations.SerializedName

data class Addon(
    @SerializedName("id")
    val id: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("title")
    val title: String
)