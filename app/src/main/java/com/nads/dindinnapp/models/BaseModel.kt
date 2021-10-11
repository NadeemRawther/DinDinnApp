package com.nads.dindinnapp.models

import androidx.annotation.DrawableRes
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.nads.dindinnapp.R

data class BaseModel(val name:String,
                     var isSelected:Boolean=false,
                     val type:Int = 1,)
//                   @SerializedName("image")
//                   @Expose
//                   @DrawableRes
//                   val image: Int?= R.drawable.vodafone)
