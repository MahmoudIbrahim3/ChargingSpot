package com.chargingspots.presentation.ui.charing_spots

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.chargingspots.AppConst
import org.junit.Test
import org.junit.runner.RunWith
import com.chargingspots.R
import com.chargingspots.core.entities.SpotEntity
import com.chargingspots.launchFragmentInHiltContainer
import com.chargingspots.presentation.ui.charging_spot_details.ChargingSpotDetailsFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ChargingSpotDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testNavigateToSpotDetailsFragment_DisplayedOnUI() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val bundle = Bundle()
        val spotEntity = SpotEntity(
            name = "name",
            address = "address"
        )

        bundle.putString(AppConst.INTENT_SPOT_ENTITY, Gson().toJson(spotEntity))
        launchFragmentInHiltContainer<ChargingSpotDetailsFragment>(bundle, R.style.AppTheme) {
            navController.setGraph(R.navigation.nav_graph_main)
            navController.setCurrentDestination(R.id.spotDetailsFragment)
            Navigation.setViewNavController(requireView(), navController)
        }

        Thread.sleep(1000)

        onView(withId(R.id.tvName)).check(matches(withText(spotEntity.name)))
        onView(withId(R.id.tvAddress)).check(matches(withText(spotEntity.address)))
    }
}
