package com.nads.dindinnapp.repository

import com.nads.dindinnapp.api.DinDinnApiService
import com.nads.dindinnapp.models.CategoryModel
import com.nads.dindinnapp.models.IngredientsModel
import com.nads.dindinnapp.models.OrderModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(private val dinDinnApiService: DinDinnApiService)
     {
   suspend fun getorders():OrderModel{
       return dinDinnApiService.getorders()
   }

  suspend fun getcategories():CategoryModel{
         return dinDinnApiService.getcategories()
  }

         suspend fun getingredients():IngredientsModel{
             return dinDinnApiService.getingredients()
         }
}