package com.quwaysim.maathuraat.ui.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.quwaysim.maathuraat.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    //worked with no default constructor

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey);
    }
}
