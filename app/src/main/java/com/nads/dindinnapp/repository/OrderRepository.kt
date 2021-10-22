package com.nads.dindinnapp.repository

import com.nads.dindinnapp.api.DinDinnApiService
import com.nads.dindinnapp.models.CategoryModel
import com.nads.dindinnapp.models.IngredientsModel
import com.nads.dindinnapp.models.OrderModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(private val dinDinnApiService: DinDinnApiService)
    :DinDinnRepository(){
   suspend fun getorders():OrderModel?{
       return safeApiCall(
           {dinDinnApiService.getorders()},
           errorMessage = "Got Order Error")
   }

  suspend fun getcategories():CategoryModel?{
             return safeApiCall(call = {dinDinnApiService.getcategories()}
             ,errorMessage = "Got Category Error")
  }

  suspend fun getingredients():IngredientsModel?{
          return safeApiCall(call = {dinDinnApiService.getingredients()}
          ,errorMessage = "Got Category Error")
  }


}