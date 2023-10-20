package com.arbi.gihubapp.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arbi.gihubapp.data.db.FavoriteEntity
import com.arbi.gihubapp.databinding.ItemUserBinding
import com.arbi.gihubapp.databinding.ItemUserFavoriteBinding
import com.arbi.gihubapp.ui.detail.DetailUserActivity
import com.bumptech.glide.Glide

class FavoriteAdapter  : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {


    private val listFavorite = ArrayList<FavoriteEntity>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavViewHolder {
        val itemView =
            ItemUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(listFavorite[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFavorite[position]) }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun setListFavorite(items: List<FavoriteEntity>) {
        listFavorite.clear()
        listFavorite.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listFavorite.size
    class FavViewHolder(private val binding: ItemUserFavoriteBinding):
    RecyclerView.ViewHolder(binding.root)
    {
        fun bind(favEntity: FavoriteEntity) {
            with(binding) {
                tvUsernameFavorite.text = favEntity.login
                Glide.with(root)
                    .load(favEntity.avatar_url)
                    .circleCrop()
                    .into(ivUserFavorite)
                root.setOnClickListener {
                    val intent = Intent(itemView.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USERNAME, favEntity.login)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(favEntity: FavoriteEntity)
    }
}