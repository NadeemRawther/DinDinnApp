package com.nads.dindinnapp.ui.order

import com.nads.dindinnapp.databinding.OrderListBinding
import com.nads.dindinnapp.models.Addon
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nads.dindinnapp.R
import com.nads.dindinnapp.databinding.IngredientScreenBinding
import com.nads.dindinnapp.models.Bento
import com.nads.dindinnapp.models.Main
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ingredient_screen.view.*
import kotlinx.android.synthetic.main.order_list.view.*

class AddonAdapter(context: Context, ls: MutableList<Addon>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private val context: Context = context
    var lsd: MutableList<Addon> = ls

    companion object{
        const val item_viewtype = 1
        const val item_viewtype2 = 2
    }
    init {

    }


    class ViewHolder(view: OrderListBinding) : RecyclerView.ViewHolder(view.root){
        val cardView: CardView
        init {
            cardView = view.root.findViewById(R.id.orderlist)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
        val orderListBinding = OrderListBinding.inflate(view,parent,false)


        return ViewHolder(orderListBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.cardView.setOnClickListener{

        }
        holder.cardView.textView4.text =  "x " + lsd.get(position).quantity.toString()
        holder.cardView.textView3.text = lsd.get(position).title
    }








    override fun getItemCount(): Int {

        val sizer = lsd.size
        return sizer
    }





}