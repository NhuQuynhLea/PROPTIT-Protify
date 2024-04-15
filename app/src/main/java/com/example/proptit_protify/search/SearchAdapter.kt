package com.example.proptit_protify.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proptit_protify.R
import com.example.proptit_protify.pojo.Data
import com.example.proptit_protify.databinding.ItemSongSearchBinding

class SearchAdapter(private val onSongClick:(Data)->Unit):
    ListAdapter<Data,SearchAdapter.ViewHolder>(ContactDiffCallback()){
    inner class ViewHolder(private val binding: ItemSongSearchBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(data: Data){
                binding.artistNameTxt.text = data.artist.name
                binding.songTitleTxt.text = data.title
                Glide.with(binding.root.context)
                    .load(data.album.cover)
                    .error(R.drawable.ic_pause)
                    .placeholder(R.drawable.ic_download)
                    .into(binding.avatar)
                binding.container.setOnClickListener{
                    onSongClick.invoke(data)
                }
            }
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSongSearchBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    private class ContactDiffCallback: DiffUtil.ItemCallback<Data>(){
        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}