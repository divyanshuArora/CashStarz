package com.developers.CrbClub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developers.CrbClub.R
import kotlinx.android.synthetic.main.welcome_screen_item.view.*


class
WelcomeScreenAdapter(
    var context: Context, var welcomeScreenArrayList: ArrayList<Int>
) : RecyclerView.Adapter<WelcomeScreenAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeScreenAdapter.ItemViewHolder {
//        welcomeScreenItemBinding = DataBindingUtil.inflate(
//            LayoutInflater.from(context),
//            R.layout.welcome_screen_item,
//            parent,
//            false
//        )
        val view = LayoutInflater.from(context).inflate(R.layout.welcome_screen_item,parent,false)
        return ItemViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return welcomeScreenArrayList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        holder.bind(welcomeScreenArrayList[position])
        Glide.with(context).load(welcomeScreenArrayList[position])
            .into(holder.welcomeScreenItemBinding.welcomeScreenTitleImageView)


    }

    class ItemViewHolder(var welcomeScreenItemBinding: View) :
        RecyclerView.ViewHolder(welcomeScreenItemBinding) {

    }
}