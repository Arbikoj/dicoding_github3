package com.arbi.gihubapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbi.gihubapp.api.RetrofitClient
import com.arbi.gihubapp.data.model.DetailUserResponse
import com.arbi.gihubapp.data.repo.FavoriteRepository
import com.arbi.gihubapp.data.db.FavoriteEntity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(username: String, app: Application): ViewModel(){
    val user = MutableLiveData<DetailUserResponse>()

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(app)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    init {
        viewModelScope.launch { setUserDetail(username) }
        Log.i(TAG, "DetailViewModel is Created")
    }

    fun insert(favEntity: FavoriteEntity) {
        mFavoriteRepository.insert(favEntity)

    }

    fun delete(favEntity: FavoriteEntity) {
        mFavoriteRepository.delete(favEntity)
    }

    fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>> {
        return mFavoriteRepository.getUserFavoriteById(id)
    }


    private fun setUserDetail(username:String){
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if(response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }
    fun getUserDetail(): LiveData<DetailUserResponse>{
        return user
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}