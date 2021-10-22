package com.anurag.newsfeedapp.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.anurag.newsfeedapp.R

class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }

}
