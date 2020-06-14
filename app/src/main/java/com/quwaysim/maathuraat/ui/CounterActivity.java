package com.quwaysim.maathuraat.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.quwaysim.maathuraat.R;

public class CounterActivity extends AppCompatActivity {
    private TextView counter;
    private int count;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        //widgets
        ImageView add = findViewById(R.id.add);
        ImageView reset = findViewById(R.id.reset);
        counter = findViewById(R.id.counter_textView);
        LinearLayout counter_layout = findViewById(R.id.counter_layout);

        mp = MediaPlayer.create(this, R.raw.clickcounter);

        if (savedInstanceState != null) {
            String countValue = savedInstanceState.getString("COUNTER");
            counter.setText(countValue);
            assert countValue != null;
            count = Integer.parseInt(countValue);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.counter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.dec_one) {
            if (count > 0) {
                count--;
                counter.setText(String.valueOf(count));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String counterValue = counter.getText().toString();
        outState.putString("COUNTER", counterValue);
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

    @Override
    protected void onPause() {
        super.onPause();
        mp.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mp.release();
    }
}
