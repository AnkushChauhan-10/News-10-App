package com.example.news10.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.news10.databinding.NewsLayoutBinding
import com.example.news10.models.DaoNewsModel

class NewsLayoutAdapter: ListAdapter<DaoNewsModel, NewsLayoutAdapter.Holder>(DiffUtil()) {

    class Holder(val binding: NewsLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(newsItem: DaoNewsModel){
            binding.temp = newsItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = NewsLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }


    class DiffUtil:androidx.recyclerview.widget.DiffUtil.ItemCallback<DaoNewsModel>(){
        override fun areItemsTheSame(oldItem: DaoNewsModel, newItem: DaoNewsModel): Boolean {
            return oldItem.title == oldItem.title
        }

        override fun areContentsTheSame(oldItem: DaoNewsModel, newItem: DaoNewsModel): Boolean {
            return oldItem == newItem
        }

    }

}