package com.nads.dindinnapp.ui.order

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nads.dindinnapp.R
import com.nads.dindinnapp.databinding.OrderCardsBinding
import com.nads.dindinnapp.models.Data
import com.nads.dindinnapp.ui.activity.MainActivity
import com.nads.dindinnapp.ui.viewmodel.HomeActivityViewModel
import kotlinx.android.synthetic.main.order_cards.view.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.media.RingtoneManager
import android.net.Uri
import android.os.CountDownTimer
import androidx.lifecycle.lifecycleScope
import com.nads.dindinnapp.ui.viewmodel.OrderViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers.from
import io.reactivex.schedulers.Schedulers.from
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class OrderAdapter(context: Context, ls:ArrayList<Data>,activityViewModel: HomeActivityViewModel,viewlifeowner:LifecycleOwner
                   ,orderViewModel: OrderViewModel)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val orderViewModels = orderViewModel
    private val context: Context = context

    var lsd:ArrayList<Data> = ls
    var viewmodel :HomeActivityViewModel = activityViewModel


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
        val alerdiff = printDifference(date,alertedat)/1000
        val diffinminutes = difference/1000

        holder.cardView.createdat.text = "at " + formattedDate.toString()
        Log.e("NadeemTime$diffinminutes", "GOT")
        if (!viewmodel.arr.isEmpty() && viewmodel.arr.get(position) != null) {
            Log.e("Nads","GotValue")
          orderViewModels.tickerFlow(viewmodel.arr.get(position)!!.toLong())
                .onEach {
                    Log.e("NadeemTime$position", it.toString())
                    holder.cardView.timeralarm.text = it.toString()
                    holder.cardView.progressBar.max = diffinminutes
                    holder.cardView.progressBar.setProgress(it.toInt())
                    viewmodel.arr.add(position, it.toInt())
                    if (it.toInt() == alerdiff) {
                       notifyalarm()
                    }
                    if (it.toInt() == 0) {
                        holder.cardView.accept_button.text = "Okay"
                        holder.cardView.progressBar.isVisible = false
                        holder.cardView.autorejected.isVisible = false
                        holder.cardView.timeralarm.isVisible = false
                    }
                }
                .launchIn(orderViewModels.mainScope)

        // or lifecycleScope or other
        }
        else {
        orderViewModels.tickerFlow(diffinminutes.toLong())
                .onEach {
                    Log.e("NadeemTime$position", it.toString())
                    holder.cardView.timeralarm.text = it.toString()
                    holder.cardView.progressBar.max = diffinminutes
                    holder.cardView.progressBar.setProgress(it.toInt())
                    viewmodel.arr.add(position, it.toInt())
                    if (it.toInt() == alerdiff) {
                       notifyalarm()

                    }
                    if (it.toInt() == 0) {
                        holder.cardView.accept_button.text = "Okay"
                        holder.cardView.progressBar.isVisible = false
                        holder.cardView.autorejected.isVisible = false
                        holder.cardView.timeralarm.isVisible = false
                    }
                }
                .launchIn(orderViewModels.mainScope)
                    // or lifecycleScope or other

        }



        holder.cardView.recycleaddons.apply {
               layoutManager = LinearLayoutManager(context)
               adapter = AddonAdapter(context,lsd.get(position).addon)
               hasFixedSize()
       }
        holder.cardView.accept_button.setOnClickListener {
            lsd.removeAt(position)
            refreshView(position)
        }


    }











    fun refreshView(position: Int) {
        notifyDataSetChanged()
    }
    fun printDifference(startDate: Date, endDate: Date):Int {
        var different = endDate.time - startDate.time
        println("startDate : $startDate")
        println("endDate : $endDate")
        println("different : $different")
        return different.toInt()
    }


    fun notifyalarm(){
        val notification = RingtoneManager.getRingtone(context,Uri.parse("android.resource://"
                + context.getPackageName() + "/" + R.raw.notificationalert))

        notification.play()
    }


    override fun getItemCount(): Int {
        return lsd.size
    }
}