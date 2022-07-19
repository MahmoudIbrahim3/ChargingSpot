package com.chargingspots.presentation.ui.filter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.chargingspots.R
import com.chargingspots.core.entities.FilterEntity
import com.chargingspots.di.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_filter.*
import javax.inject.Inject

@AndroidEntryPoint
class FilterFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var viewModel: FilterViewModel

    companion object {
        fun showDialogFragment(activity: FragmentActivity?) {
            val bottomSheetDialogFragment = FilterFragment()
            bottomSheetDialogFragment.show(
                activity?.supportFragmentManager!!,
                bottomSheetDialogFragment.tag
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[FilterViewModel::class.java]
    }

    private fun initViews() {
        cbOpenSpotsOnly.isChecked = viewModel.filterLiveData.value?.showOnlyOpen?: false

        btApply.setOnClickListener {
            applyFilter()
        }
    }

    private fun applyFilter() {
        viewModel.filterLiveData.postValue(
            FilterEntity(
                cbOpenSpotsOnly.isChecked
            )
        )
        dismiss()
    }
}