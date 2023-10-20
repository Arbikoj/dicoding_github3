package com.arbi.gihubapp.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavViewModelFactory private constructor(private val application: Application) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name} ")
    }

    companion object {
        @Volatile
        private var INSTANCE: FavViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavViewModelFactory::class.java) {
                    INSTANCE = FavViewModelFactory(application)
                }
            }
            return INSTANCE as FavViewModelFactory
        }
    }

}