package com.example.news10.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.news10.databinding.NewsLayoutBinding
import com.example.news10.models.DaoNewsModel

class NewsLayoutAdapter: ListAdapter<DaoNewsModel, NewsLayoutAdapter.Holder>(DiffUtil()) {

    private var onClickListener:OnClickListener?=null

    fun setOnClick(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClickReadMore(url: String)
        fun onClickShare(url: String)
    }
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
        val item = getItem(position)
        holder.bind(item)
        holder.binding.readMore.setOnClickListener {
            if(onClickListener != null){
                onClickListener!!.onClickReadMore(item.url)
            }
        }
        holder.binding.share.setOnClickListener {
            if(onClickListener!=null) onClickListener!!.onClickShare(item.url)
        }
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