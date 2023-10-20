package com.arbi.gihubapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arbi.gihubapp.data.db.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: FavoriteEntity)

    @Update
    fun update(fav: FavoriteEntity)

    @Delete
    fun delete(fav: FavoriteEntity)

    @Query("SELECT  * from favorite")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    @Query("SELECT  * from favorite WHERE id = :id")
    fun getUserFavoriteById(id: Int): LiveData<List<FavoriteEntity>>
}