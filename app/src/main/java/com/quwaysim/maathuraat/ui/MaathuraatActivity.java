/*
 *Created by Muhammad Akorede Qaasim
 *on June 1, 2020
 */

package com.quwaysim.maathuraat.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quwaysim.maathuraat.R;
import com.quwaysim.maathuraat.adapters.DuaAdapter;
import com.quwaysim.maathuraat.ui.fragments.AboutUsDialogFragment;

public class MaathuraatActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView recyclerView;
    private String[] maathuraatList;
    private DuaAdapter duaAdapter;
    private String[] transList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maathuraat);
        recyclerView = findViewById(R.id.dua_recyclerview);
        maathuraatList = getResources().getStringArray(R.array.maathuraat);
        transList = getResources().getStringArray(R.array.maathuraat_transliterations);

        setupSharedPreferences();
    }

    public void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean showTransliteration = sharedPreferences.getBoolean(getResources().getString(R.string.key_trans), true);
        int fontSize = sharedPreferences.getInt(getResources().getString(R.string.key_size), 24);

        setShowTransliteration(showTransliteration, fontSize);

        recyclerView.setAdapter(duaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL,
                false));

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setShowTransliteration(boolean showTransliteration, int size) {
        if (showTransliteration) {
            duaAdapter = new DuaAdapter(this,
                    maathuraatList,
                    transList,
                    null,
                    R.layout.dua_recyclerview_item,
                    size);
        } else {
            duaAdapter = new DuaAdapter(this,
                    maathuraatList,
                    null,
                    null,
                    R.layout.dua_recyclerview_item,
                    size);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.key_trans)) || key.equals(getString(R.string.key_size))) {
            //TODO find a replacement for recreate();
            recreate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            startActivity(new Intent(MaathuraatActivity.this, SettingsActivity.class));
            return true;
        } else if (id == R.id.share) {
            shareAppLink();
            return true;
        } else if (id == R.id.about_us) {
            new AboutUsDialogFragment().show(getSupportFragmentManager(), "AboutUsFragment");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareAppLink() {
        String msg = getString(R.string.share_text) + getString(R.string.share_link);
        ShareCompat.IntentBuilder.from(this)
                .setText(msg)
                .setType("text/plain")
                .setChooserTitle("Share App Link")
                .startChooser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
