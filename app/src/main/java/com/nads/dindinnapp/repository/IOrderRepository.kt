package com.nads.dindinnapp.repository

import com.nads.dindinnapp.models.CategoryModel
import com.nads.dindinnapp.models.IngredientsModel
import com.nads.dindinnapp.models.OrderModel

interface IOrderRepository {
    suspend fun getorders(): OrderModel?
    suspend fun getcategories(): CategoryModel?
    suspend fun getingredients(): IngredientsModel?
}