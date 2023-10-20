package com.arbi.gihubapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arbi.gihubapp.R
import com.arbi.gihubapp.data.model.User
import com.arbi.gihubapp.databinding.FragmentFollowBinding
import com.arbi.gihubapp.ui.main.UserAdapter
import com.arbi.gihubapp.ui.main.UserFollowsAdapter

class FollowingFragment : Fragment(R.layout.fragment_follow){
    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

        }

        adapter.setOnItemClickCallback(object : UserFollowsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailUserActivity.EXTRA_PHOTO, data.avatar_url)
                startActivity(intent)
            }
        })
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it.size != 0) {
                adapter.setList(it)
                showLoading(false)
                binding.tvNodata.visibility = View.GONE
            }else{
                adapter.setList(it)
                showLoading(false)
                binding.tvNodata.visibility = View.VISIBLE
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}