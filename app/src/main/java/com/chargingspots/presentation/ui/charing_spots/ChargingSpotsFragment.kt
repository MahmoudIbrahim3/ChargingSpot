package com.chargingspots.presentation.ui.charing_spots

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.google.gson.Gson
import com.chargingspots.presentation.ui.main.MainActivity
import com.chargingspots.R
import com.chargingspots.di.ViewModelFactory
import com.chargingspots.presentation.ui.base.BaseFragment
import com.chargingspots.AppConst
import com.chargingspots.core.entities.LocationEntity
import com.chargingspots.core.entities.SpotEntity
import com.chargingspots.presentation.ui.filter.FilterFragment
import com.chargingspots.presentation.ui.filter.FilterViewModel
import com.chargingspots.presentation.ui.location.LocationViewModel
import com.chargingspots.presentation.ui.settings.SettingsFragment
import com.chargingspots.presentation.ui.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_charging_spots.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class ChargingSpotsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var viewModel: ChargingSpotsViewModel

    @Inject
    lateinit var settingsViewModel: SettingsViewModel

    @Inject
    lateinit var filterViewModel: FilterViewModel

    @Inject
    lateinit var locationViewModel: LocationViewModel

    private lateinit var adapter: ChargingSpotsAdapter
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        twoPane = requireContext().resources.getBoolean(R.bool.isTwoPane)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_charging_spots, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModels()
        setupAdapter()
        initSwipeToRefresh()
        initChargingSpotsLiveData()
        initNewsClickLiveData()
        initSettingsLiveData()
        initFilterLiveData()
        initLocationLiveData()
    }

    override fun onStart() {
        super.onStart()
        if (activity is MainActivity)
            setUpActionBar()
    }

    private fun setUpActionBar(title: String? = null) {
        val act = activity as MainActivity
        act.setUpActionBar()
        act.setActionBarTitle(title ?: getString(R.string.charging_spots), false)
    }

    private fun initViewModels() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[ChargingSpotsViewModel::class.java]

        settingsViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[SettingsViewModel::class.java]

        locationViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[LocationViewModel::class.java]
    }

    private fun setupAdapter() {
        adapter = ChargingSpotsAdapter()
        rvItems.adapter = adapter.withLoadStateFooter(
            ChargingSpotsLoadingAdapter { adapter.retry() }
        )
    }

    private fun initSwipeToRefresh() {
        swipeToRefresh.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun initChargingSpotsLiveData() {
        viewModel.chargingSpotsLiveData.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
                stopLoading(swipeToRefresh)
            }
        }
    }

    private fun initNewsClickLiveData() {
        adapter.onSpotClickedLiveData.observe(viewLifecycleOwner) {
            val arg = setUpBundle(it as SpotEntity)

            if (twoPane) {
                navigateToChargingSpotDetailsFragment(arg)
            } else {
                findNavController().navigate(
                    R.id.action_newsFragment_to_newsDetailsFragment, arg
                )
            }
        }
    }

    private fun initSettingsLiveData() {
        settingsViewModel.settingsLiveData.observe(viewLifecycleOwner) {
            it?.let {
                startLoading(swipeToRefresh)
                viewModel.chargingSpotsLiveData.value = null
                if (locationViewModel.location != null) {
                    loadChargingSpots(locationViewModel.location?.value!!)
                }
            }
        }
    }

    private fun initFilterLiveData() {
        filterViewModel.filterLiveData.observe(viewLifecycleOwner) {
            it?.let {
                startLoading(swipeToRefresh)
                viewModel.chargingSpotsLiveData.value = null
                if (locationViewModel.location != null) {
                    loadChargingSpots(locationViewModel.location?.value!!)
                }
            }
        }
    }

    private fun initLocationLiveData() {
        locationViewModel.location?.observe(viewLifecycleOwner) {
            it?.let {
                loadChargingSpots(it)
            }
        }
    }

    private fun loadChargingSpots(
        locationEntity: LocationEntity
    ) {
        if (viewModel.chargingSpotsLiveData.value == null) {
            startLoading(swipeToRefresh)
            viewModel.loadChargingSpots(
                settingsViewModel.settingsLiveData.value,
                filterViewModel.filterLiveData.value,
                locationEntity
            )
        } else
            viewModel.chargingSpotsLiveData.value = viewModel.chargingSpotsLiveData.value
    }

    private fun renderData(data: PagingData<SpotEntity>) {
        lifecycleScope.launch {
            adapter.submitData(data)
        }

        lifecycleScope.launch {
            try {
                delay(500)
                showFirstChargingSpotForTwoPane()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showFirstChargingSpotForTwoPane() {
        if (twoPane) {
            val arg = setUpBundle(adapter.snapshot().items.first())
            navigateToChargingSpotDetailsFragment(arg)
        }
    }

    private fun setUpBundle(spotEntity: SpotEntity): Bundle {
        val arg = Bundle()
        arg.putString(AppConst.INTENT_SPOT_ENTITY, Gson().toJson(spotEntity))
        return arg
    }

    private fun navigateToChargingSpotDetailsFragment(arg: Bundle) {
        val navHostFragment = childFragmentManager.findFragmentById(
            R.id.nav_host_fragment_news_details
        ) as NavHostFragment
        navHostFragment.navController.navigate(R.id.newsDetailsFragment, arg)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_news, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_filter)
            FilterFragment.showDialogFragment(requireActivity())
        else if (item.itemId == R.id.menu_item_settings)
            SettingsFragment.showDialogFragment(requireActivity())
        return super.onOptionsItemSelected(item)
    }
}