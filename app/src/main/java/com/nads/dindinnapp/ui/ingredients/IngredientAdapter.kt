package com.nads.dindinnapp.ui.ingredients

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nads.dindinnapp.R
import com.nads.dindinnapp.databinding.IngredientScreenBinding
import com.nads.dindinnapp.models.Bento
import com.nads.dindinnapp.models.Datas
import com.nads.dindinnapp.models.IngredientsModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ingredient_screen.view.*


class IngredientAdapter(context: Activity, ls: MutableList<Bento>, id:Int, query:String = "")
    : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private val context: Context = context
   var lsd: MutableList<Bento> = ls
    private val ids:Int = id
      private val strval:String = query

    init {

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


          fillwithBento(holder,position)

        holder.cardView.setOnClickListener{

        }

    }

    private fun fillwithBento(holder: ViewHolder,position: Int){
        if (strval.equals("") || lsd.get(position).title.contains(strval)){
        holder.cardView.title.text = lsd.get(position).title
        holder.cardView.count.text = lsd.get(position).quantity.toString()
        val imager = holder.cardView.imager
                Glide.with(context)
            .load(lsd.get(position).image)
            .fitCenter().override(250,290).into(
            imager
        )
        }else{
            holder.cardView.isVisible = false
        }
    }







    override fun getItemCount(): Int {

    val sizer = lsd.size
    return sizer
    }





}