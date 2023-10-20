package com.arbi.gihubapp.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arbi.gihubapp.R
import com.arbi.gihubapp.data.db.FavoriteEntity
import com.arbi.gihubapp.databinding.ActivityFavoriteBinding
import com.arbi.gihubapp.databinding.ActivityMainBinding
import com.arbi.gihubapp.ui.detail.DetailUserActivity
import com.arbi.gihubapp.ui.main.MainViewModel
import com.arbi.gihubapp.ui.main.MainViewModelFactory
import com.arbi.gihubapp.ui.main.UserAdapter
import com.arbi.gihubapp.ui.setting.SettingActivity

class FavoriteActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    private var _binding : ActivityFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: FavViewModel
    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpToolbar()
        viewModel = obtainViewModel(this@FavoriteActivity)
        setUpFavorite()
        setUserFavorite()
    }
    private fun obtainViewModel(activity: AppCompatActivity): FavViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavViewModel::class.java]
    }

    private fun setUpFavorite() {
        with(binding) {
            val layoutManager = LinearLayoutManager(this@FavoriteActivity)
            this?.rvFavoriteUser?.layoutManager = layoutManager
            val itemDecoration =
                DividerItemDecoration(this@FavoriteActivity, layoutManager.orientation)
            this?.rvFavoriteUser?.addItemDecoration(itemDecoration)
            this?.rvFavoriteUser?.adapter = adapter
            adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(favEntity: FavoriteEntity) {
                    val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USERNAME, favEntity.login)
                    intent.putExtra(DetailUserActivity.EXTRA_ID, favEntity.id)
                    intent.putExtra(DetailUserActivity.EXTRA_PHOTO, favEntity.avatar_url)
                    startActivity(intent)
                }
            })
        }
    }

    private fun setUserFavorite() {
        viewModel = obtainViewModel(this@FavoriteActivity)
        viewModel.getAllFavorites().observe(this@FavoriteActivity) { favList ->
            if (favList != null) {
                adapter.setListFavorite(favList)
            }
            if (favList.isEmpty()) {
                showNoDataSaved(true)
            } else {
                showNoDataSaved(false)

            }
        }
    }

    private fun showNoDataSaved(isNoData: Boolean) {
        binding?.tvNodata?.visibility = if (isNoData) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpToolbar() {
        binding?.toolbar?.setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.btn_setting_favorite -> {
                val setting = Intent(this, SettingActivity::class.java)
                startActivity(setting)
                true
            }
            android.R.id.home -> {
                finish()
                return true
            }
            else -> false
        }
    }
}