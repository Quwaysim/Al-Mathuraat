package com.quwaysim.maathuraat.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
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

public class KhamsoonActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khamsoon);
        RecyclerView recyclerView = findViewById(R.id.khamsoon_recyclerView);
        String[] khamsoonList = getResources().getStringArray(R.array.khamsoon);
        String[] references = getResources().getStringArray(R.array.khamsoon_references);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int fontSize = sharedPreferences.getInt(getResources().getString(R.string.key_size), 24);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        DuaAdapter duaAdapter = new DuaAdapter(this,
                khamsoonList,
                null,
                references,
                R.layout.khamsoon_recyclerview_item,
                fontSize);

        recyclerView.setAdapter(duaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            startActivity(new Intent(KhamsoonActivity.this, SettingsActivity.class));
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.key_trans)) || key.equals(getString(R.string.key_size))) {
            //TODO find a replacement for recreate();
            recreate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}