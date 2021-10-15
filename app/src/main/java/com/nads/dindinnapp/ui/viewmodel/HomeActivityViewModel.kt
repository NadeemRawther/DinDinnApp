package com.nads.dindinnapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.impl.Schedulers
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Observer


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function

import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.order_cards.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

class HomeActivityViewModel:BaseViewModel() {

   var fragmentdestroyed = MutableLiveData<Boolean>()
   var arr = arrayListOf<Int?>()

    fun display( j:Long): CompositeDisposable? {
      val timeobserver =Observable.interval( j, TimeUnit.SECONDS)
            .map { it-1 }
            .observeOn(AndroidSchedulers.mainThread())
          .subscribe()
        val compositeDisposable= CompositeDisposable()
        compositeDisposable.addAll(timeobserver)
      return compositeDisposable



    }
    fun tickerFlow(period: Long, initialDelay:Long = 0) = flow {
        delay(initialDelay)
        var count = period
        while (true) {
            emit(count)
            count = count-1
            delay(1000)
            if (count < 0){
                break
            }

        }
    }

    companion object {
        private var instance: HomeActivityViewModel? = null
        fun getInstance() =
            instance ?: synchronized(HomeActivityViewModel::class.java) {
                instance ?: HomeActivityViewModel().also { instance = it }
            }
    }
}