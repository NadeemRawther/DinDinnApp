package com.nads.dindinnapp.ui.ingredients

import android.os.Bundle
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
import androidx.recyclerview.widget.RecyclerView
import com.nads.dindinnapp.R
import com.nads.dindinnapp.databinding.FragmentIngredientBinding
import com.nads.dindinnapp.models.*
import com.nads.dindinnapp.ui.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.activity_main.*


class IngredientFrag : Fragment() {
    private val orderviewModel by navGraphViewModels<OrderViewModel>(R.id.main_navigation){defaultViewModelProviderFactory}
    var ls=ArrayList<IngredientsModel>()
    val bentolisted = mutableListOf<Bento>()
    val mainlisted = mutableListOf<Main>()
    val appetizerlisted = mutableListOf<Appetizer>()
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


        }
        orderviewModel.selecteditem.observe(viewLifecycleOwner,selectedcat)

        return databinding.root
    }

    private fun methodForTabSelection(id:Int,databinding:FragmentIngredientBinding,strval:String=""){
                orderviewModel.getIngredients()


               val gotingredient = Observer<IngredientsModel> {it->
                   bentolisted.clear()
                   mainlisted.clear()
                   appetizerlisted.clear()




                   bentolisted.addAll(it.datas.get(0).bento)
                   mainlisted.addAll(it.datas.get(1).main)
                   appetizerlisted.addAll(it.datas.get(2).appetizer)
                   when(id){
                       0->getBento(bentolisted,databinding,strval)
                       1->getMain(mainlisted,databinding,strval)
                       2->getAppetizer(appetizerlisted,databinding,strval)
                   }

                   val queryTextListener: SearchView.OnQueryTextListener
                   queryTextListener = object : SearchView.OnQueryTextListener {
                       override fun onQueryTextChange(newText: String): Boolean {
                           Log.i("onQueryTextChange", newText)
                           bentolisted.clear()
                           mainlisted.clear()
                           appetizerlisted.clear()
                           for (i in it.datas.get(0).bento){
                               if (i.title.contains(newText)){
                                   bentolisted.add(i)
                               }
                           }
                           for (i in it.datas.get(1).main){
                               if (i.title.contains(newText)){
                                   mainlisted.add(i)
                               }
                           }
                           for (i in it.datas.get(2).appetizer){
                               if (i.title.contains(newText)){
                                   appetizerlisted.add(i)
                               }
                           }

                           when(id){
                               0->getBento(bentolisted,databinding,newText)
                               1->getMain(mainlisted,databinding,newText)
                               2->getAppetizer(appetizerlisted,databinding,newText)
                           }










                           return true
                       }

                       override fun onQueryTextSubmit(query: String): Boolean {
                           Log.i("onQueryTextSubmit", query)
                           bentolisted.clear()
                           mainlisted.clear()
                           appetizerlisted.clear()
                           for (i in it.datas.get(0).bento){
                               if (i.title.contains(query)){
                                   bentolisted.add(i)
                               }
                           }
                           for (i in it.datas.get(1).main){
                               if (i.title.contains(query)){
                                   mainlisted.add(i)
                               }
                           }
                           for (i in it.datas.get(2).appetizer){
                               if (i.title.contains(query)){
                                   appetizerlisted.add(i)
                               }
                           }

                           when(id){
                               0->getBento(bentolisted,databinding,query)
                               1->getMain(mainlisted,databinding,query)
                               2->getAppetizer(appetizerlisted,databinding,query)
                           }
                           return true
                       }
                   }
                   requireActivity().searchview.setOnQueryTextListener(queryTextListener)

               }
        orderviewModel.ingredients.observe(viewLifecycleOwner,gotingredient)
    }



    private fun getBento(it: MutableList<Bento>, databinding:FragmentIngredientBinding, strval:String) {
        databinding.recyclervss.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2,RecyclerView.HORIZONTAL,false)
            adapter = IngredientAdapter(requireActivity(),it,0,strval)
            hasFixedSize()
        }
    }
     private fun getMain(it: MutableList<Main>, databinding:FragmentIngredientBinding, strval:String){
         databinding.recyclervss.apply {
             layoutManager = GridLayoutManager(requireActivity(), 2,RecyclerView.HORIZONTAL,false)
             adapter = MainAdapter(requireActivity(),it,1,strval)
             hasFixedSize()
         }
     }
    private fun getAppetizer(it: MutableList<Appetizer>, databinding:FragmentIngredientBinding, strval:String){
        databinding.recyclervss.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2,RecyclerView.HORIZONTAL,false)
            adapter = AppetizerAdapter(requireActivity(),it,2,strval)
            hasFixedSize()
        }
    }
}