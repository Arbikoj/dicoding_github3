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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(username: String, app: Application): ViewModel(){
    val user = MutableLiveData<DetailUserResponse>()


    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(app)
    private val _userDetail = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?> = _userDetail
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isDataFailed = MutableLiveData<Boolean>()
    val isDataFailed: LiveData<Boolean> = _isDataFailed
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
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

//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}