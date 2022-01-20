package com.example.notify.fragments.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notify.R
import com.example.notify.data.entities.Note
import com.example.notify.fragments.list.ListFragmentDirections
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var notesList = emptyList<Note>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = notesList[position]
        holder.itemView.note_txt.text = currentItem.note

        if (position % 2 == 0) {
            holder.itemView.rowLayout.setBackgroundColor(Color.parseColor("#d6d4e0"))
        } else {
            holder.itemView.rowLayout.setBackgroundColor(Color.parseColor("#b8a9c9"))
        }

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(note: List<Note>) {
        this.notesList = note
        notifyDataSetChanged()
    }
}