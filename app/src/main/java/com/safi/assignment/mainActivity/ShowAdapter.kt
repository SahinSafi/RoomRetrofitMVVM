package com.safi.assignment.mainActivity

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.safi.assignment.R
import com.safi.assignment.databinding.ItemShowBinding

class ShowAdapter(val context: Context) : RecyclerView.Adapter<ShowAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemShowBinding.bind(itemView)
    }

    private val showList = mutableListOf<ShowSearchModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun addData(dataList: List<ShowSearchModel>) {
        showList.clear()
        showList.addAll(dataList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        showList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val show = showList[position]
        with(holder) {
            binding.scoreText.text = show.getScore().toString()
            binding.nameText.text = show.getShow()?.name
            binding.typeText.text = show.getShow()?.type
            Glide.with(context).load(show.getShow()?.image?.medium).into(binding.thumb)
        }
    }

    override fun getItemCount(): Int {
        return showList.size
    }
}