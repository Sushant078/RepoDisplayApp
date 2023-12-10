package com.emyr78.theproj.ui.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.emyr78.theproj.databinding.ContributorsItemBinding
import com.emyr78.theproj.models.ContributorsItem

class ContributorsAdapter(
    private val dataList: List<ContributorsItem>,
) : RecyclerView.Adapter<ContributorsAdapter.ContributorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        return ContributorViewHolder(
            ContributorsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        holder.bind(dataList[holder.bindingAdapterPosition])
    }

    class ContributorViewHolder(
        private val binding: ContributorsItemBinding,
    ) : ViewHolder(binding.root) {
        fun bind(contributorItem: ContributorsItem) {
            binding.contributorName.text = contributorItem.name
            binding.contributorAvatar.load(contributorItem.avatarUrl)
        }
    }
}