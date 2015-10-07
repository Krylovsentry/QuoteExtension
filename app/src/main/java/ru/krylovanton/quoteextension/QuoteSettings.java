package ru.krylovanton.quoteextension;

import android.os.Bundle;

/**
 * Created by Антон on 07.10.2015.
 */
public class QuoteSettings extends BaseSettingsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setIcon(R.drawable.ic_extension);
    }

    @Override
    protected void setupSimplePreferencesScreen() {

       addPreferencesFromResource(R.xml.prefs_quote);
        bindPreferenceSummaryToValue(findPreference(QuoteExtension.PREF_LANGUAGE));


    }



}
