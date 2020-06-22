package com.quwaysim.maathuraat.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.app.ShareCompat;
import androidx.preference.PreferenceManager;

import com.quwaysim.maathuraat.R;
import com.quwaysim.maathuraat.ui.fragments.AboutUsDialogFragment;

public class CounterActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = CounterActivity.class.getSimpleName();
    private TextView counter;
    private int count;
    private SharedPreferences mSharedPreferences;
    private MediaPlayer mp;
    private boolean clickSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //widgets
        ImageView add = findViewById(R.id.add);
        ImageView reset = findViewById(R.id.reset);
        ImageView minus = findViewById(R.id.minus);

        counter = findViewById(R.id.counter_textView);
        LinearLayout counter_layout = findViewById(R.id.counter_layout);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        clickSound = mSharedPreferences.getBoolean(getString(R.string.key_enable_sound), false);

        if (clickSound) {
            mp = MediaPlayer.create(this, R.raw.clickcounter);
        }

        if (savedInstanceState != null) {
            String countValue = savedInstanceState.getString("COUNTER");
            counter.setText(countValue);
            assert countValue != null;
            count = Integer.parseInt(countValue);
        } else if (mSharedPreferences.getInt("counter_value", 0) != 0) {
            count = mSharedPreferences.getInt("counter_value", 0);
            counter.setText(String.valueOf(count));
        } else {
            count = 0;
        }

        counter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOne();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOne();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCount();
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusOne();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.share:
                shareAppLink();
                return true;
            case R.id.about_us:
                new AboutUsDialogFragment().show(getSupportFragmentManager(), "AboutUsFragment");
                return true;
            case R.id.settings:
                startActivity(new Intent(CounterActivity.this, SettingsActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String counterValue = counter.getText().toString();
        outState.putString("COUNTER", counterValue);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
        releaseMediaPlayer();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("counter_value", count);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
        releaseMediaPlayer();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("counter_value", count);
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
        releaseMediaPlayer();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("counter_value", count);
        editor.apply();
    }

    private void addOne() {
        count += 1;
        play();
        counter.setText(String.valueOf(count));
    }

    private void resetCount() {
        count = 0;
        counter.setText(String.valueOf(count));
    }

    private void minusOne() {
        if (count > 0) {
            play();
            count--;
            counter.setText(String.valueOf(count));
        }
    }

    private void play() {
        if (clickSound) {
            try {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(this, R.raw.clickcounter);
                }
                mp.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void releaseMediaPlayer() {
        if (mp != null) {
            mp.release();
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

    //TODO not working
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.key_enable_sound))){
            recreate();
        }
    }
}