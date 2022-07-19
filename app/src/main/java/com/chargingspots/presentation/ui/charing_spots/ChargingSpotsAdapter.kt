package com.chargingspots.presentation.ui.charing_spots

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chargingspots.R
import com.chargingspots.core.entities.SpotEntity
import com.chargingspots.utils.DiffUtilCallBack

class ChargingSpotsAdapter:
    PagingDataAdapter<SpotEntity, ChargingSpotsAdapter.ViewHolder>(DiffUtilCallBack()) {

    val onSpotClickedLiveData = MutableLiveData<SpotEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_charging_spots_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bindView(it) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val myItemView: CardView = view.findViewById(R.id.itemView)
        private val ivPosterImage: ImageView = view.findViewById(R.id.ivImage)
        private val tvName: TextView = view.findViewById(R.id.tvName)
        private val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        private val tvDistance: TextView = view.findViewById(R.id.tvDistance)
        private val tvStatus: TextView = view.findViewById(R.id.tvStatus)

        fun bindView(spotEntity: SpotEntity) {
            with(spotEntity) {
                val context = itemView.context

                tvName.text = name
                tvAddress.text = address
                tvDistance.text = context.getString(R.string.distance_in_km, distance.toString())

                if(status == true) {
                    tvStatus.text = context.getString(R.string.opened)
                    tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_opened))
                }
                else {
                    tvStatus.text = context.getString(R.string.closed)
                    tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_closed))
                }

                Glide.with(context)
                    .load(thumbnail?.replace("http", "https"))
                    .placeholder(R.drawable.image_poster_placeholder)
                    .error(R.drawable.image_poster_placeholder)
                    .into(ivPosterImage)

                myItemView.setOnClickListener {
                    onSpotClickedLiveData.postValue(spotEntity)
                }
            }
        }
    }
}