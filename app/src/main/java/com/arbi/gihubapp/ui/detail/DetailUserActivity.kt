package com.arbi.gihubapp.ui.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.arbi.gihubapp.R
import com.arbi.gihubapp.data.model.DetailUserResponse
import com.arbi.gihubapp.data.db.FavoriteEntity
import com.arbi.gihubapp.databinding.ActivityDetailUserBinding
import com.arbi.gihubapp.ui.setting.SettingActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class DetailUserActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    private lateinit var binding: ActivityDetailUserBinding

    private lateinit var viewModel: DetailUserViewModel

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
        val username = intent.getStringExtra(EXTRA_USERNAME)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

//        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
//        showLoading(true)
        username?.let {
//            viewModel.setUserDetail(it)
            addFavorite(it)
        }


//        viewModel.getUserDetail().observe(this) {
//            if (it != null) {
//
//
//                val favorite = FavoriteEntity()
//                favorite.login = username
//                favorite.id = intent.getIntExtra(KEY_ID, 0)
//                favorite.avatar_url = it?.avatar_url
//
//                val detailViewModel: DetailUserViewModel by viewModels {
//                    DetailViewModelFactory(username, application)
//                }
//
//
//
//                binding.fabFavorite.apply {
//                    setOnClickListener {
//                        if (isFavorite) {
////                    detailViewModel.delete(favorite)
//                            Toast.makeText(
//                                this@DetailUserActivity,
//                                "${favorite.login} telah dihapus dari data User Favorite ",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        } else {
////                    detailViewModel.insert(favorite)
//                            Toast.makeText(
//                                this@DetailUserActivity,
//                                "${favorite.login} telah ditambahkan ke data User Favorite",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    }
//                }
//
//                binding.apply {
//                    tvName.text = it.name
//                    tvUsername.text = it.login
//                    tvFollowersValue.text = "${it.followers}"
//                    tvFollowingValue.text = "${it.following}"
//                    tvCompany.text = it.company
//                    tvLocation.text = it.location
//                    tvRepoValue.text = it.public_repos
//                    tvBlog.text = it.blog
//                    tvBio.text = it.bio
//                    Glide.with(this@DetailUserActivity)
//                        .load(it.avatar_url)
//                        .transition(DrawableTransitionOptions.withCrossFade())
//                        .centerCrop()
//                        .into(ivProfile)
//                }
//                showLoading(false)
//
//
//
//
//            }
//        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }




    }


    private fun addFavorite(username : String) {
//        val user = intent.getParcelableExtra<DetailUserResponse>(KEY_USER)

        val favorite = FavoriteEntity()
        favorite.login = username
        favorite.id = intent.getIntExtra(EXTRA_ID, 0)
        favorite.avatar_url = intent.getStringExtra(EXTRA_PHOTO)

        val detailViewModel: DetailUserViewModel by viewModels {
            DetailViewModelFactory(username, application)
        }
        detailViewModel.isLoading.observe(this@DetailUserActivity) {
            showLoading(it)
        }

        detailViewModel.getUserDetail().observe(this@DetailUserActivity) { res ->
            if (res != null) {
                with(binding) {
                    tvName.text = res.name
                  tvUsername.text = res.login
                    tvFollowersValue.text = "${res.followers}"
                    tvFollowingValue.text = "${res.following}"
                    tvCompany.text = res.company
                    tvLocation.text = res.location
                    tvRepoValue.text = res.public_repos
                    tvBlog.text = res.blog
                    tvBio.text = res.bio
                    Glide.with(this@DetailUserActivity)
                        .load(res.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                }
//                binding.apply {
//                    tvName.text = res.name
//                    tvUsername.text = res.login
//                    tvFollowersValue.text = "${res.followers}"
//                    tvFollowingValue.text = "${res.following}"
//                    tvCompany.text = res.company
//                    tvLocation.text = res.location
//                    tvRepoValue.text = res.public_repos
//                    tvBlog.text = res.blog
//                    tvBio.text = res.bio
//                    Glide.with(this@DetailUserActivity)
//                        .load(res.avatar_url)
//                        .transition(DrawableTransitionOptions.withCrossFade())
//                        .centerCrop()
//                        .into(ivProfile)
//                        }
//        }
                showLoading(false)
            }
        }

        detailViewModel.getFavoriteById(favorite.id!!)
            .observe(this@DetailUserActivity) { listFav ->
                isFavorite = listFav.isNotEmpty()

                binding.fabFavorite.imageTintList = if (listFav.isEmpty()) {
                    ColorStateList.valueOf(Color.rgb(255, 255, 255))
                } else {
                    ColorStateList.valueOf(Color.rgb(247, 106, 123))
                }

            }

        binding.fabFavorite.apply {
            setOnClickListener {
                if (isFavorite) {
                    detailViewModel.delete(favorite)
                    Toast.makeText(
                        this@DetailUserActivity,
                        "${favorite.login} telah dihapus dari data User Favorite ",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    detailViewModel.insert(favorite)
                    Toast.makeText(
                        this@DetailUserActivity,
                        "${favorite.login} telah ditambahkan ke data User Favorite",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    private fun showLoading(state: Boolean){
        if(state){
            binding.progressDetail.visibility = View.VISIBLE
        }else{
            binding.progressDetail.visibility = View.GONE
        }
    }

    private fun setUpToolbar() {
        binding.toolbar.setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.btn_setting_detail -> {
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



    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_PHOTO = "extra_photo"
    }
}