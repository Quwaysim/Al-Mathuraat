package com.quwaysim.maathuraat.ui;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.preference.PreferenceManager;

import com.quwaysim.maathuraat.R;

public class CounterActivity extends AppCompatActivity {

    private static final String TAG = CounterActivity.class.getSimpleName();
    private TextView counter;
    private int count;
    private MediaPlayer mp;
    private SharedPreferences mSharedPreferences;

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

        mp = MediaPlayer.create(this, R.raw.clickcounter);

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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count();
            }
        });
        counter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    count--;
                    play();
                    counter.setText(String.valueOf(count));
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
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
        mp.release();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("counter_value", count);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
        mp.release();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("counter_value", count);
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
        mp.release();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("counter_value", count);
        editor.apply();
    }

    private void count() {
        count += 1;
        play();
        counter.setText(String.valueOf(count));
    }

    private void reset() {
        count = 0;
        counter.setText(String.valueOf(count));
    }

    private void play() {
        mp.start();
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
