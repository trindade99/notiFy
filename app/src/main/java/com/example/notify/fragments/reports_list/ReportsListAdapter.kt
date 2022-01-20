package com.example.notify.fragments.reports_list

import android.content.Context
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notify.R
import com.example.notify.api_notify.model.Note
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.custom_row_reports_list.view.*


class ReportsListAdapter(userIdInSession: String?) : RecyclerView.Adapter<ReportsListAdapter.MyViewHolder>() {
    private var reportsList = emptyList<Note>()
    private  val _userIdInSession = userIdInSession
    private  var  _ctx : Context? = null

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _ctx = parent.context

        return ReportsListAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_row_reports_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = reportsList[position]
        holder.itemView.reports_list_title.text = currentItem.title
        holder.itemVieww.reports_list_description.text = currentItem.description
        holder.itemView.reports_list_created_at.text = currentItem.created_at
        holder.itemView.reports_list_created_by.text = currentItem.user_name

        if(position%2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#d6d4e0"))
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#b8a9c9"))
        }

        holder.itemView.rowLayout_reports_list.setOnClickListener {
            if(_userIdInSession == currentItem.users_id.toString()){
                val action =
                    ReportsListFragmentDirections.actionReportsListFragmentToUpdateReportFragment(
                        currentItem
                    )
                holder.itemView.findNavController().navigate(action)
            }
            else {
                Toast.makeText(_ctx,R.string.ony_edit_your_reports, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return reportsList.size
    }

    fun setData(reportsList: List<Note>){
        this.reportsList = reportsList
        notifyDataSetChanged()
    }
}