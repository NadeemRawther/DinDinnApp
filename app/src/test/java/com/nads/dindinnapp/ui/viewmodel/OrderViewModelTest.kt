package com.nads.dindinnapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.nads.dindinnapp.getOrAwaitValueTest
import com.nads.dindinnapp.repository.FakeRepository
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not


import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class OrderViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var orderViewModel:OrderViewModel



    @Before
    fun setup(){
       orderViewModel = OrderViewModel(FakeRepository())

    }
    @SmallTest
    @Test
    fun getOrder()  {
        orderViewModel.getOrder()
        val datas = orderViewModel.orderlist.getOrAwaitValueTest()
        assertThat(datas.status.success, `is`(true))
    }
    @SmallTest
    @Test
    fun getCategory() {
        orderViewModel.getCategory()
        val datas = orderViewModel.catitems.getOrAwaitValueTest()
        assertThat(datas.status.success , `is`(true))
    }
    @Test
    fun getIngredients() {
        orderViewModel.getIngredients()
        val datas = orderViewModel.ingredients.getOrAwaitValueTest()
        assertThat(datas.status.success, `is`(true))
    }

    @Test
    fun getLoading() {
    }

    @Test
    fun getItems2() {
    }



    @Test
    fun getLastSelectedCategory() {
    }

    @Test
    fun setLastSelectedCategory() {
    }

    @Test
    fun getSelecteditem() {
    }

    @Test
    fun getSnackBarEventFire() {
    }

    @Test
    fun getSnackBarEvent() {
    }

    @Test
    fun getOrderlist() {
    }

    @Test
    fun getTickerRx() {
    }

    @Test
    fun testGetIngredients() {
    }

    @Test
    fun tickerFlow() {
    }

    @Test
    fun tickerRx() {
    }



    @Test
    fun countdownTimer() {
    }

    @Test
    fun getItemBinding2Modified() {
    }
}