package com.nads.dindinnapp.ui.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import com.nads.dindinnapp.R
import com.nads.dindinnapp.BR
import com.nads.dindinnapp.api.DinDinnApiService
import com.nads.dindinnapp.listeners.ItemSelectListener
import com.nads.dindinnapp.models.*
import com.nads.dindinnapp.repository.DinDinnRepository
import com.nads.dindinnapp.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.plugins.RxJavaPlugins.onError

import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import me.tatarka.bindingcollectionadapter2.OnItemBind
import org.reactivestreams.Subscriber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@HiltViewModel
class OrderViewModel
@Inject
constructor(private val orderRepository: OrderRepository,private val savedStateHandle: SavedStateHandle):ViewModel(){
    val loading = MutableLiveData<Boolean>()
    val items2 = ObservableArrayList<Categ>()
    val ingredients = MutableLiveData<IngredientsModel>()
    var lastSelectedCategory = 0
    val selecteditem = LiveEvent<Categ>()
    protected val snackBarEventFire = LiveEvent<SnackBarEvent>()
    val snackBarEvent = snackBarEventFire
    val orderlist = LiveEvent<OrderModel>()
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val coroutineMainContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    val scope = CoroutineScope(coroutineContext)
    val mainScope = CoroutineScope(coroutineMainContext)

    fun cancelAllRequests() = coroutineContext.cancel()

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
            response.categ.let {
                items2.addAll(it)

            }
            selectcartype.onItemSelected(items2.get(0))

        }
    }
    val itemBinding2Modified: OnItemBind<Categ> =
        OnItemBind { itemBinding, position, item ->
            itemBinding.set(
                BR.ingredienttab,
                 R.layout.tabscategory)

            itemBinding.bindExtra(BR.listener,selectcartype )
        }

}