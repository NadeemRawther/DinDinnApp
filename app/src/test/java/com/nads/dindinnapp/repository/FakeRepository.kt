package com.nads.dindinnapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nads.dindinnapp.models.*
import org.junit.Rule

class FakeRepository:IOrderRepository {

    //OrdersList
    val addonList = mutableListOf<Addon>(Addon(id=0,quantity = 2,title = "cabbage"),
        Addon(id=1,quantity = 3,title = "tomatocury"))
    val datas = listOf<Data>(Data(alertedAt = "25",addon =addonList,createdAt = "50"
        ,expiredAt = "0",id=1,quantity = 5,title = "pizza" ),
        Data(alertedAt = "25",addon =addonList,createdAt = "50"
        ,expiredAt = "0",id=1,quantity = 5,title = "mozarella" ))
    val orderModel = OrderModel(data =datas ,status = Status("",200,true) )
    //Categories
    val categ = listOf<Categ>(Categ(category = "Bento",id= 0)
        ,Categ(category = "Main",id= 0)
        ,Categ(category = "Appetizer",id= 0))
    val categories = CategoryModel(categ=categ,status = Status("",200,true))
    //Ingredients
    val appetizer = listOf<Appetizer>(Appetizer(image = "",quantity = 2,title="Appetizerfood"),
        Appetizer(image = "",quantity = 2,title="Appetizerfood"))
    val bento = arrayListOf<Bento>(
        Bento(image = "",quantity = 2,title="Appetizerfood"),
        Bento(image = "",quantity = 2,title="Appetizerfood"))
    val main = listOf<Main>(Main(image = "",quantity = 2,title="Appetizerfood"),
        Main(image = "",quantity = 2,title="Appetizerfood"))
    val ingredModel = listOf<Datas>(Datas(appetizer,bento,main))
    val ingredients = IngredientsModel(datas=ingredModel,Status("",200,true))

    override suspend fun getorders(): OrderModel? {
        return orderModel
    }

    override suspend fun getcategories(): CategoryModel? {
        return categories
    }

    override suspend fun getingredients(): IngredientsModel? {
        return ingredients
    }
}