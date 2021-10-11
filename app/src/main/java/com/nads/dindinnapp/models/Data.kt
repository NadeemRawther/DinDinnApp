package com.nads.dindinnapp.models


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("addon")
    val addon: List<Addon>,
    @SerializedName("alerted_at")
    val alertedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("expired_at")
    val expiredAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("title")
    val title: String
)