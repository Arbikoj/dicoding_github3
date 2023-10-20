package com.arbi.gihubapp.data.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.arbi.gihubapp.data.db.FavoriteDao
import com.arbi.gihubapp.data.db.FavoriteEntity
import com.arbi.gihubapp.data.db.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val favDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        favDao = db.favDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = favDao.getAllFavorite()
    fun getUserFavoriteById(id: Int): LiveData<List<FavoriteEntity>> =
        favDao.getUserFavoriteById(id)

    fun insert(fav: FavoriteEntity) {
        executorService.execute { favDao.insert(fav) }
    }

    fun delete(fav: FavoriteEntity) {
        executorService.execute { favDao.delete(fav) }
    }
}
