package com.nads.dindinnapp.ui.ingredients

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nads.dindinnapp.R
import com.nads.dindinnapp.databinding.IngredientScreenBinding
import com.nads.dindinnapp.databinding.OrderCardsBinding
import com.nads.dindinnapp.models.IngredientsModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ingredient_screen.view.*
import kotlinx.android.synthetic.main.order_cards.view.*


class IngredientAdapter(context: Activity, ls:IngredientsModel,id:Int,query:String = "")
    : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private val context: Context = context
    var lsd:IngredientsModel = ls
    private val ids:Int = id
      private val strval:String = query
    companion object{
        const val item_viewtype = 1
        const val item_viewtype2 = 2
    }

    class ViewHolder(view: IngredientScreenBinding) : RecyclerView.ViewHolder(view.root){
        val cardView: CardView
        init {
            cardView = view.root.findViewById(R.id.ingredientcards)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
        val ingredientScreenBinding = IngredientScreenBinding.inflate(view,parent,false)


        return ViewHolder(ingredientScreenBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val imager = holder.cardView.imager
        val conte = context as Activity
        val searchView = conte.searchview

        when(ids){
            0->fillwithBento(holder,position)
            1->fillwithMain(holder,position)
            2->fillwithAppetizer(holder,position)
            else -> lsd.datas.get(0).bento.size
        }
        holder.cardView.setOnClickListener{

        }

    }

    private fun fillwithBento(holder: ViewHolder,position: Int){
        if (strval.equals("") || lsd.datas.get(0).bento.get(position).title.contains(strval)){
        holder.cardView.title.text = lsd.datas.get(0).bento.get(position).title
        holder.cardView.count.text = lsd.datas.get(0).bento.get(position).quantity.toString()
        val imager = holder.cardView.imager
                Glide.with(context)
            .load(lsd.datas.get(0).bento.get(position).image)
            .fitCenter().override(250,290).into(
            imager
        )
        }else{
            holder.cardView.get(position).isVisible = false
        }
    }
    private fun fillwithMain(holder: ViewHolder,position: Int){
        if (strval.equals("") || lsd.datas.get(1).main.get(position).title.contains(strval)){
        holder.cardView.title.text = lsd.datas.get(1).main.get(position).title
        holder.cardView.count.text = lsd.datas.get(1).main.get(position).quantity.toString()
        val imager = holder.cardView.imager
        Glide.with(context)
            .load(lsd.datas.get(1).main.get(position).image)
            .fitCenter().override(250,290).into(
                imager
            )
        }
    }
    private fun fillwithAppetizer(holder: ViewHolder,position: Int){
        if (strval.equals("") || lsd.datas.get(2).appetizer.get(position).title.contains(strval)){
        holder.cardView.title.text = lsd.datas.get(2).appetizer.get(position).title
        holder.cardView.count.text = lsd.datas.get(2).appetizer.get(position).quantity.toString()
        val imager = holder.cardView.imager
        Glide.with(context)
            .load(lsd.datas.get(2).appetizer.get(position).image)
            .fitCenter().override(250,290).into(
                imager
            )
        }
    }






    override fun getItemCount(): Int {

        val sizer = when(ids){
            0->bentosize()
            1->mainsize()
            2->appetizersize()
            else -> bentosize()
        }
return sizer
    }


    private fun bentosize():Int{
        var count = 0
        val sizel = lsd.datas.get(0).bento
        if (strval.equals("")){
            return sizel.size
        }
//        for (i in sizel){
//            if(i.title.contains(strval.toString(),true)){
//                count++
//            }
//        }
        return sizel.size
    }
    private fun mainsize():Int{
        var count = 0
        val sizel = lsd.datas.get(1).main
        if (strval.equals("")){
            return sizel.size
        }
//        for (i in sizel){
//            if(i.title.contains(strval.toString(),true)){
//                count++
//            }
//        }
        return sizel.size
    }
    private fun appetizersize():Int{
        var count = 0
        val sizel = lsd.datas.get(2).appetizer
        if (strval.equals("")){
            return sizel.size
        }
//        for (i in sizel){
//            if(i.title.contains(strval.toString(),true)){
//                count++
//            }
//        }
        return sizel.size
    }



}