package com.nads.dindinnapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nads.dindinnapp.models.CategoryModel
import com.nads.dindinnapp.models.IngredientsModel
import com.nads.dindinnapp.models.OrderModel
import org.junit.Rule

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