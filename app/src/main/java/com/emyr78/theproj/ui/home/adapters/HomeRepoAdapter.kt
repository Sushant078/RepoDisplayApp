package com.emyr78.theproj.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emyr78.theproj.databinding.RepoItemBinding
import com.emyr78.theproj.models.RepoItem

class HomeRepoAdapter : RecyclerView.Adapter<HomeRepoAdapter.RepoItemViewHolder>() {
    private val dataList: MutableList<RepoItem> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        return RepoItemViewHolder(
            RepoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        holder.bind(dataList[holder.bindingAdapterPosition])
    }

    class RepoItemViewHolder(private val binding: RepoItemBinding) : ViewHolder(binding.root) {
        fun bind(repoItem: RepoItem) {
            binding.tvRepoName.text = repoItem.name
            binding.tvRepoDesc.text = repoItem.description
            binding.tvForkCount.text = repoItem.forksCount.toString()
            binding.tvStarsCount.text = repoItem.starsCount.toString()
        }
    }
}