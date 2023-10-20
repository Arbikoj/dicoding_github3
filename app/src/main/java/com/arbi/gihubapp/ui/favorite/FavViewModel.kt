package com.arbi.gihubapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arbi.gihubapp.data.db.FavoriteEntity
import com.arbi.gihubapp.data.repo.FavoriteRepository

class FavViewModel(application : Application) : ViewModel() {
    private val mFavRepository : FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorites() : LiveData<List<FavoriteEntity>> = mFavRepository.getAllFavorites()

}