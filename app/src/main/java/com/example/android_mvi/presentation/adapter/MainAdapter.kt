package com.example.android_mvi.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_mvi.R
import com.example.android_mvi.data.model.Post
import kotlinx.android.synthetic.main.list_item.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val mPosts = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false))

    override fun getItemCount(): Int = mPosts.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(mPosts[position])
    }

    fun setNewData(data : List<Post>){
        mPosts.clear()
        mPosts.addAll(data)
        notifyDataSetChanged()
    }

    class MainViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data : Post){
            itemView.title.text = data.title
            itemView.body.text = data.body
        }
    }
}
