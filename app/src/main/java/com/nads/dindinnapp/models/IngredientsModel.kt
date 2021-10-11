package com.nads.dindinnapp.models


import com.google.gson.annotations.SerializedName

data class IngredientsModel(
    @SerializedName("datas")
    val datas: List<Datas>,
    @SerializedName("status")
    val status: Status
)