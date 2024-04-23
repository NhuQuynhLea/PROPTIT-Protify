package com.example.proptit_protify.album_detail

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.proptit_protify.databinding.ItemAlbumDetailBinding
import com.example.proptit_protify.interfaces.ItemClick
import com.example.proptit_protify.models.Song

class AlbumDetailAdapter (
    private val context: Context,

    private val listener: ItemClick) :
    RecyclerView.Adapter<AlbumDetailAdapter.MyViewHolder>() {

    private var dataList: List<Song> = ArrayList<Song>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemAlbumDetailBinding = ItemAlbumDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = dataList[position]
        holder.title.text = currentData.title
        holder.artist.text = currentData.artist
        holder.itemLayout.setOnClickListener { listener.onCLick(position) }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList: List<Song>){
        this.dataList = dataList
        notifyDataSetChanged()
    }
    class MyViewHolder(itemView:ItemAlbumDetailBinding): RecyclerView.ViewHolder(itemView.root) {
        val title: TextView
        val artist: TextView
        val itemLayout: ConstraintLayout

        init {
            title = itemView.songTitleTxt
            artist = itemView.artistNameTxt
            itemLayout = itemView.songLayout
        }
    }
}