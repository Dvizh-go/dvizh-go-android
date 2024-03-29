package com.start.eventgo.main.ui.detail.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.eventgo.R
import com.start.eventgo.main.ui.detail.data.model.CheckListDataModel

class CheckListAdapter : RecyclerView.Adapter<CheckListAdapter.ViewHolder>() {

    private var checkList = listOf<CheckListDataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val checkView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_checklist, parent, false)

        return ViewHolder(checkView)
    }

    override fun getItemCount(): Int {
        return checkList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val checkListItem = checkList[position]
        holder.bind(checkListItem)
    }

    fun setData(checkList: List<CheckListDataModel>) {
        this.checkList = checkList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(checkListItem: CheckListDataModel) {
            val checkImage: ImageView = itemView.findViewById(R.id.check_image)
            val checkText: TextView = itemView.findViewById(R.id.check_text)

            checkImage.setImageResource(checkListItem.checkImage)
            checkText.text = checkListItem.checkText
        }
    }
}
