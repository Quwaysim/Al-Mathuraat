/*
 * Created by Muhammad Akorede Qaasim
 * on June 1, 2020
 */

package com.quwaysim.maathuraat.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ShareCompat;
import androidx.preference.PreferenceManager;

import com.quwaysim.maathuraat.R;
import com.quwaysim.maathuraat.ui.fragments.AboutUsDialogFragment;
import com.quwaysim.maathuraat.utils.Notification;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CardView maathuraat = findViewById(R.id.maathuraaat_card);
        CardView subhah = findViewById(R.id.subhah_card);
        CardView khamsoon = findViewById(R.id.khamsoon_card);
        CardView quraan = findViewById(R.id.Quraan_card);
        TextView header_en = findViewById(R.id.header_en);

        String[] dailyMsg = getResources().getStringArray(R.array.header_strings);
        header_en.setText(setDailyMsg(dailyMsg));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        boolean reminderSet = sharedPreferences.getBoolean(getResources().getString(R.string.key_enable_reminder), false);
        if (reminderSet) {
            //Create notification with AlarmManager
            Notification notification = new Notification(this);
            notification.createNotification();
        } else {
            //Cancel notifications with AlarmManager
        }
        maathuraat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MaathuraatActivity.class));
            }
        });

        subhah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CounterActivity.class));
            }
        });

        khamsoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, KhamsoonActivity.class));
            }
        });

        quraan.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        }));

    }

    public String setDailyMsg(String[] strings) {
        Calendar time = Calendar.getInstance();
        int day = time.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                return strings[0];
            case 2:
                return strings[1];
            case 3:
                return strings[2];
            case 4:
                return strings[3];
            case 5:
                return strings[4];
            case 6:
                return strings[5];
            case 7:
                return strings[6];
            default:
                return strings[7];
        }
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
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
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
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
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
        if (key.equals(getResources().getString(R.string.key_enable_reminder))){
            recreate();
        }

    }
}
