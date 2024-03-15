package com.start.eventgo.main.ui.profile.data.manageEvents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.start.eventgo.databinding.ItemOrganizationEventBinding
import com.start.eventgo.main.ui.home.presentation.model.Event
import com.start.eventgo.main.ui.profile.data.model.AvailableEvents

class ManageEventsAdapter(private val onClick: (Event) -> Unit) :
    RecyclerView.Adapter<ManageEventsAdapter.ManageEventsHolder>() {

    private var eventsList = listOf<Event>()

    inner class ManageEventsHolder(private val itemBinding: ItemOrganizationEventBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(event: Event) = with(itemBinding) {
            itemView.setOnClickListener { onClick(event) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ManageEventsAdapter.ManageEventsHolder {
        val itemBinding = ItemOrganizationEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ManageEventsHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ManageEventsAdapter.ManageEventsHolder, position: Int) {
        val event: Event = eventsList[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    fun setData(list: AvailableEvents) {
        eventsList = list.events
        notifyDataSetChanged()
    }
}
