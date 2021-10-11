package com.nads.dindinnapp.ui.viewmodel

class HomeActivityViewModel:BaseViewModel() {
    companion object {
        private var instance: HomeActivityViewModel? = null
        fun getInstance() =
            instance ?: synchronized(HomeActivityViewModel::class.java) {
                instance ?: HomeActivityViewModel().also { instance = it }
            }
    }
}