package com.nads.dindinnapp.ui.order

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nads.dindinnapp.R
import com.nads.dindinnapp.databinding.OrderCardsBinding
import com.nads.dindinnapp.models.Data
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.order_cards.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class OrderAdapter(context: Context, ls:ArrayList<Data>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context: Context = context
    var lsd:ArrayList<Data> = ls

    companion object{
        const val item_viewtype = 1
        const val item_viewtype2 = 2
    }

    class ViewHolder(view:OrderCardsBinding) : RecyclerView.ViewHolder(view.root){
        val cardView: CardView
        init {
            cardView = view.root.findViewById(R.id.order_cards)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
        val orderCardsBinding = OrderCardsBinding.inflate(view,parent,false)


        return ViewHolder(orderCardsBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.cardView.createdid.text = lsd.get(position).id.toString()

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm+SSS'Z'")
        val outputFormat = SimpleDateFormat("hh:mm a")
        val alertedat = inputFormat.parse(lsd.get(position).alertedAt)
        val endtime = inputFormat.parse(lsd.get(position).expiredAt)
        val date = inputFormat.parse(lsd.get(position).createdAt)
        val formattedDate = outputFormat.format(date)
        val difference = printDifference(date,endtime)
        val diffinminutes = difference/1000
        holder.cardView.timeralarm.text = diffinminutes.toString()+"s"
        holder.cardView.createdat.text = "at " + formattedDate.toString()
        holder.cardView.setOnClickListener{

        }
        Observable.timer(1,  TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map { difference }
            .distinctUntilChanged()
            .subscribe {
                holder.cardView.timeralarm.text = it.toString()
            }



        holder.cardView.accept_button.setOnClickListener {

        }

    }
    fun printDifference(startDate: Date, endDate: Date):Int {
        //milliseconds
        var different = endDate.time - startDate.time
        println("startDate : $startDate")
        println("endDate : $endDate")
        println("different : $different")
//        val secondsInMilli: Long = 1000
//        val minutesInMilli = secondsInMilli * 60
//        val hoursInMilli = minutesInMilli * 60
//        val daysInMilli = hoursInMilli * 24
//        val elapsedDays = different / daysInMilli
//        different = different % daysInMilli
//        val elapsedHours = different / hoursInMilli
//        different = different % hoursInMilli
//        val elapsedMinutes = different / minutesInMilli
//        different = different % minutesInMilli
//        val elapsedSeconds = different / secondsInMilli
//        System.out.printf(
//            "%d days, %d hours, %d minutes, %d seconds%n",
//            elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds
//        )
        return different.toInt()
    }
    override fun getItemCount(): Int {
        return lsd.size
    }
}