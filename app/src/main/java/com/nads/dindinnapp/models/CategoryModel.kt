package com.nads.dindinnapp.models


import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("categ")
    val categ: List<Categ>,
    @SerializedName("status")
    val status: Status
)