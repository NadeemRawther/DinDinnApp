package com.nads.dindinnapp.ui.viewmodel


import android.os.CountDownTimer
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.hadilq.liveevent.LiveEvent
import com.nads.dindinnapp.R
import com.nads.dindinnapp.BR
import com.nads.dindinnapp.listeners.ItemSelectListener
import com.nads.dindinnapp.models.*
import com.nads.dindinnapp.repository.IOrderRepository
import com.nads.dindinnapp.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import me.tatarka.bindingcollectionadapter2.OnItemBind
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class OrderViewModel
@Inject
constructor(private val orderRepository: IOrderRepository):BaseViewModel(){
    val loading = MutableLiveData<Boolean>()
    val items2 = ObservableArrayList<Categ>()
    val ingredients = MutableLiveData<IngredientsModel>()
    var lastSelectedCategory = 0
    val selecteditem = LiveEvent<Categ>()
    protected val snackBarEventFire = LiveEvent<SnackBarEvent>()
    val snackBarEvent = snackBarEventFire
    val orderlist = LiveEvent<OrderModel>()

    private val _tickerRx = BehaviorSubject.create<Long>()
    val tickerRx:Observable<Long>
    get() = _tickerRx


    private val parentJob = Job()



init {

}

    private val selectcartype = object : ItemSelectListener<Categ> {
        override fun onItemSelected(item: Categ) {
            val lastCategor = items2[lastSelectedCategory]
            lastCategor.isSelected = false
            items2[lastSelectedCategory] = lastCategor

            val position = items2.indexOf(item)
            item.isSelected = true
            items2[position] = item

            lastSelectedCategory = position
            selecteditem.value = item
        }
    }
    fun getIngredients() {
        mainScope.launch {
            loading.postValue(true)
            val response = orderRepository.getingredients()
            loading.postValue(false)

            response?.let {
                ingredients.value = it
            }

        }
    }


    fun tickerFlow(period: Long, initialDelay:Long = 0) = flow{
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
    fun tickerRx(period: Long,initialDelay: Long) = object :CountDownTimer(period,initialDelay){
        override fun onTick(millisUntilFinished: Long) {
            _tickerRx.subscribeOn(Schedulers.io())

        }

        override fun onFinish() {
            TODO("Not yet implemented")
        }

    }

    fun getOrder() {
        mainScope.launch {
            loading.postValue(true)
            val response = orderRepository.getorders()
            loading.postValue(false)
            response?.let {
                orderlist.value = it
            }

        }
    }

    fun getCategory() {
        mainScope.launch {
            loading.postValue(true)
            val response = orderRepository.getcategories()

            loading.postValue(false)
            items2.clear()
            response!!.categ.let {
                items2.addAll(it)

            }
            selectcartype.onItemSelected(items2.get(0))

        }
    }


    fun countdownTimer(remainingTime: Long, interval: Int): Flowable<Int> {
        return Flowable.range(0, interval + 1)
            .map { interval - it }
            .repeat()
            .skip(interval - remainingTime)
            .concatMap { Flowable.just(it).delay(1, TimeUnit.SECONDS) }
    }



    val itemBinding2Modified: OnItemBind<Categ> =
        OnItemBind { itemBinding, position, item ->
            itemBinding.set(
                BR.ingredienttab,
                 R.layout.tabscategory)

            itemBinding.bindExtra(BR.listener,selectcartype )
        }
}