package com.example.notify.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notify.R
import com.example.notify.data.User
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var noteList = emptyList<User>()

    class MyViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.itemView.id_txt.text = currentItem.id.toString()
        holder.itemView.userName_txt.text = currentItem.UserName
        holder.itemView.title_txt.text = currentItem.Title
        holder.itemView.preview_txt.text = currentItem.Body

    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setData(user: List<User>){
        this.noteList = user
        notifyDataSetChanged()
    }


}