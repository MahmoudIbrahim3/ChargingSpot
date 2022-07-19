package com.chargingspots.presentation.ui.charging_spot_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.chargingspots.presentation.ui.main.MainActivity
import com.chargingspots.R
import com.chargingspots.di.ViewModelFactory
import com.chargingspots.presentation.ui.base.BaseFragment
import com.chargingspots.AppConst
import com.chargingspots.core.entities.SpotEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_charging_spot_details.*
import javax.inject.Inject

@AndroidEntryPoint
class ChargingSpotDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var viewModel: ChargingSpotDetailsViewModel

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        twoPane = requireContext().resources.getBoolean(R.bool.isTwoPane)
        return inflater.inflate(R.layout.fragment_charging_spot_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        loadNewsDetails()
    }

    private fun setUpActionBar(title: String?) {
        if (activity is MainActivity) {
            val act = (activity as MainActivity)
            act.hideActionBar()

            toolbar.title = title
            act.setSupportActionBar(toolbar)

            if(twoPane.not()) {
                act.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener {
                    act.onBackPressed()
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ChargingSpotDetailsViewModel::class.java)
    }

    private fun loadNewsDetails() {
        arguments?.let {
            val spotEntity = Gson().fromJson(
                it.getString(AppConst.INTENT_SPOT_ENTITY),
                SpotEntity::class.java
            )
            setUpActionBar(spotEntity.name)

            renderData(spotEntity)
        }
    }

    private fun renderData(spotEntity: SpotEntity) {
        with(spotEntity) {
            Glide.with(this@ChargingSpotDetailsFragment)
                .load(thumbnail?.replace("http", "https"))
                .placeholder(R.drawable.image_banner_placeholder)
                .error(R.drawable.image_banner_placeholder)
                .into(ivBackImage)

            tvName.text = name
            tvAddress.text = address
            tvDistance.text = getString(R.string.distance_in_km, distance.toString())

            if(status == true) {
                tvStatus.text = getString(R.string.opened)
                tvStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.status_opened))
            }
            else {
                tvStatus.text = getString(R.string.closed)
                tvStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.status_closed))
            }
        }
    }
}