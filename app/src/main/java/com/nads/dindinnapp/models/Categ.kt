package com.nads.dindinnapp.models


import com.google.gson.annotations.SerializedName

data class Categ(
    @SerializedName("category")
    val category: String,
    @SerializedName("id")
    val id: Int,
    var isSelected:Boolean=false
)