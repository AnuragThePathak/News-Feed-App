package com.anurag.newsfeedapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.anurag.newsfeedapp.BuildConfig
import com.anurag.newsfeedapp.R

class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        val version = findPreference<Preference>("version")
        if (version != null) {
            version.summary = BuildConfig.VERSION_NAME
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view?.setBackgroundColor((resources.getColor(R.color.dark_grey)))
        return view
    }

}
