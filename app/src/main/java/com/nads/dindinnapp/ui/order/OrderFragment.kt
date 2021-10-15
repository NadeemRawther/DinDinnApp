package com.nads.dindinnapp.ui.order

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope

import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nads.dindinnapp.R
import com.nads.dindinnapp.databinding.FragmentOrderBinding
import com.nads.dindinnapp.models.Data
import com.nads.dindinnapp.models.OrderModel
import com.nads.dindinnapp.ui.viewmodel.HomeActivityViewModel
import com.nads.dindinnapp.ui.viewmodel.OrderViewModel
import com.nads.dindinnapp.ui.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.util.BackpressureHelper.add


@AndroidEntryPoint
class OrderFragment : Fragment() {
    private val activityViewModel: HomeActivityViewModel by viewModels { ViewModelFactory.getInstance() }
    private val CHANNEL_ID = "THISID"
    private var myCompositeDisposable: CompositeDisposable? = null
   var ls=ArrayList<String>()
   private val orderviewModel by navGraphViewModels<OrderViewModel>(R.id.main_navigation){defaultViewModelProviderFactory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val binding:FragmentOrderBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_order,container,false)
        binding.lifecycleOwner = requireActivity()
        binding.orderViewModel = orderviewModel


        orderviewModel.getOrder()
        createNotificationChannel()
        val buttonforfoods = Observer<OrderModel> { newName ->
          var lsd = arrayListOf<Data>()
            lsd.addAll(newName.data)
            binding.recyclervs.apply {
                layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
                adapter = OrderAdapter(requireActivity(),lsd,activityViewModel,viewLifecycleOwner)
                hasFixedSize()
            }
        }
        orderviewModel.orderlist.observe(viewLifecycleOwner, buttonforfoods)















        return binding.root
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = requireActivity().getString(R.string.channel_name)
            val descriptionText = requireActivity().getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }

}