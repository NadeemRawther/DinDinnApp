package com.nads.dindinnapp.repository

import com.nads.dindinnapp.models.CategoryModel
import com.nads.dindinnapp.models.IngredientsModel
import com.nads.dindinnapp.models.OrderModel

class FakeRepository:IOrderRepository {
    override suspend fun getorders(): OrderModel? {
        TODO("Not yet implemented")
    }

    override suspend fun getcategories(): CategoryModel? {
        TODO("Not yet implemented")
    }

    override suspend fun getingredients(): IngredientsModel? {
        TODO("Not yet implemented")
    }
}