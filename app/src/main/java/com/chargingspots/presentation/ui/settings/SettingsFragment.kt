package com.chargingspots.presentation.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.chargingspots.R
import com.chargingspots.core.entities.SettingsEntity
import com.chargingspots.di.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var viewModel: SettingsViewModel

    companion object {
        fun showDialogFragment(activity: FragmentActivity?) {
            val bottomSheetDialogFragment = SettingsFragment()
            bottomSheetDialogFragment.show(
                activity?.supportFragmentManager!!,
                bottomSheetDialogFragment.tag
            )
        }

        const val MAX_DISTANCE_PROGRESS_STEP = 50
        const val PAGE_SIZE_PROGRESS_STEP = 5
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)
    }

    private fun initViews() {
        btApply.setOnClickListener {
            applyFilter()
        }

        tvMaxDistance.text = getString(
            R.string.max_distance,
            viewModel.settingsLiveData.value?.maxDistance
        )
        maxDistanceSeekBar.progress = viewModel.settingsLiveData.value?.maxDistance?.div(
            MAX_DISTANCE_PROGRESS_STEP
        )!!

        maxDistanceSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvMaxDistance.text = getString(
                    R.string.max_distance,
                    progress.times(MAX_DISTANCE_PROGRESS_STEP)
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })


        tvPageSize.text = getString(
            R.string.page_size,
            viewModel.settingsLiveData.value?.pageSize
        )
        pageSizeSeekBar.progress = viewModel.settingsLiveData.value?.pageSize?.div(
            PAGE_SIZE_PROGRESS_STEP
        )!!

        pageSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvPageSize.text = getString(
                    R.string.page_size,
                    progress.times(
                        PAGE_SIZE_PROGRESS_STEP
                    )
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun applyFilter() {
        viewModel.settingsLiveData.postValue(
            SettingsEntity(
                maxDistanceSeekBar.progress.times(MAX_DISTANCE_PROGRESS_STEP),
                pageSizeSeekBar.progress.times(PAGE_SIZE_PROGRESS_STEP)
            )
        )
        dismiss()
    }
}