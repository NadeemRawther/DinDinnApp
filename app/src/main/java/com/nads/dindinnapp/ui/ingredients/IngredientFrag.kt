package com.nads.dindinnapp.ui.ingredients

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nads.dindinnapp.R
import com.nads.dindinnapp.databinding.FragmentIngredientBinding
import com.nads.dindinnapp.models.Categ
import com.nads.dindinnapp.models.IngredientsModel
import com.nads.dindinnapp.ui.order.OrderAdapter
import com.nads.dindinnapp.ui.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.newFixedThreadPoolContext


class IngredientFrag : Fragment() {
    private val orderviewModel by navGraphViewModels<OrderViewModel>(R.id.main_navigation){defaultViewModelProviderFactory}
    var ls=ArrayList<IngredientsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val databinding:FragmentIngredientBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_ingredient,container,false)
        databinding.lifecycleOwner = viewLifecycleOwner
        databinding.orderViewModel = orderviewModel


        orderviewModel.getCategory()

        val selectedcat = Observer<Categ>(){it->

            methodForTabSelection(it.id,databinding)
            val queryTextListener: SearchView.OnQueryTextListener
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    Log.i("onQueryTextChange", newText)
                    methodForTabSelection(it.id,databinding,newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.i("onQueryTextSubmit", query)
                    methodForTabSelection(it.id,databinding,query)
                    return true
                }
            }
            requireActivity().searchview.setOnQueryTextListener(queryTextListener)

        }
        orderviewModel.selecteditem.observe(viewLifecycleOwner,selectedcat)

        return databinding.root
    }

    private fun methodForTabSelection(id:Int,databinding:FragmentIngredientBinding,strval:String=""){
               orderviewModel.getIngredients()
               val gotingredient = Observer<IngredientsModel> {it->
                   when(id){
                       0->getBento(it,databinding,strval)
                       1->getMain(it,databinding,strval)
                       2->getAppetizer(it,databinding,strval)
                   }
               }
        orderviewModel.ingredients.observe(viewLifecycleOwner,gotingredient)
    }

    private fun getBento(it: IngredientsModel,databinding:FragmentIngredientBinding,strval:String) {
        databinding.recyclervss.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2,RecyclerView.HORIZONTAL,false)
            adapter = IngredientAdapter(requireActivity(),it,0,strval)
            hasFixedSize()
        }
    }
     private fun getMain(it:IngredientsModel,databinding:FragmentIngredientBinding,strval:String){
         databinding.recyclervss.apply {
             layoutManager = GridLayoutManager(requireActivity(), 2,RecyclerView.HORIZONTAL,false)
             adapter = IngredientAdapter(requireActivity(),it,1,strval)
             hasFixedSize()
         }
     }
    private fun getAppetizer(it:IngredientsModel,databinding:FragmentIngredientBinding,strval:String){
        databinding.recyclervss.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2,RecyclerView.HORIZONTAL,false)
            adapter = IngredientAdapter(requireActivity(),it,2,strval)
            hasFixedSize()
        }
    }
}