package com.nads.dindinnapp.models


import com.google.gson.annotations.SerializedName

data class Datas(
    @SerializedName("Appetizer")
    val appetizer: List<Appetizer>,
    @SerializedName("Bento")
    val bento: List<Bento>,
    @SerializedName("Main")
    val main: List<Main>
)