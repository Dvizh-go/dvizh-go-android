package com.start.eventgo.main.ui.home.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.start.eventgo.R
import com.start.eventgo.main.ui.home.presentation.model.Event

class DefaultEventAdapter(
    private val context: Context,
    private val resources: Resources
) : RecyclerView.Adapter<DefaultEventAdapter.ViewHolder>() {

    private var events = listOf<Event>()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event_default, parent, false)
        return ViewHolder(view, resources)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = events[position]
        holder.bind(item, listener, context)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun setData(events: List<Event>) {
        this.events = events
        notifyDataSetChanged()
    }

    fun setListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View, val resources: Resources) : RecyclerView.ViewHolder(itemView) {

        private var image: ImageView = itemView.findViewById(R.id.item_event_image_default)
        private var title: TextView = itemView.findViewById(R.id.item_event_title_text_view)
        private var price: TextView = itemView.findViewById(R.id.item_event_price_text_view)

        @SuppressLint("StringFormatMatches")
        fun bind(event: Event, listener: OnItemClickListener?, context: Context) {

            Glide.with(itemView)
                .load(event.main_image)
                .placeholder(R.drawable.ic_logo)
                .transform(MultiTransformation(CenterCrop(), RoundedCorners(resources.getDimensionPixelSize(R.dimen.big_event_default_image_radius))))
                .into(image)
            title.text = event.name
            price.text = context.getString(R.string.event_price_from, event.datetime.price)

            itemView.setOnClickListener {
                listener?.onItemClick(event)
            }
        }
    }
}
