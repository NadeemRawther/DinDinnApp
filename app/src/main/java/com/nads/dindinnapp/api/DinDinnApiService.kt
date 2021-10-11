package com.nads.dindinnapp.api

import com.nads.dindinnapp.models.CategoryModel
import com.nads.dindinnapp.models.IngredientsModel
import com.nads.dindinnapp.models.OrderModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import java.util.*

interface DinDinnApiService {
    @GET("getorders")
    suspend fun getorders():OrderModel

    @GET("getcategory")
    suspend fun getcategories():CategoryModel

    @GET("getingredients")
    suspend fun getingredients():IngredientsModel
}