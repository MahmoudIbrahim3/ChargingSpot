package com.chargingspots.utils

import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.*

/**
 * Created by Mahmoud on 4/15/2018.
 */
class ContextWrapper(base: Context?) : android.content.ContextWrapper(base) {
    companion object {
        fun wrap(context: Context?, lang: String?): ContextWrapper {
            var context = context
            val newLocale = Locale(lang)
            val res = context?.resources
            val configuration = res?.configuration
            context = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                configuration?.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration?.setLocales(localeList)
                configuration?.let { context?.createConfigurationContext(it) }
            } else {
                configuration?.setLocale(newLocale)
                configuration?.let { context?.createConfigurationContext(it) }
            }
            return ContextWrapper(context)
        }
    }
}