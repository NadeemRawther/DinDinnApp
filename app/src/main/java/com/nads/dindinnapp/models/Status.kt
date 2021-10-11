package com.nads.dindinnapp.models


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)