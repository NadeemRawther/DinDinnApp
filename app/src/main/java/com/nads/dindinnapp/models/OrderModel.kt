package com.nads.dindinnapp.models


import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("status")
    val status: Status
)