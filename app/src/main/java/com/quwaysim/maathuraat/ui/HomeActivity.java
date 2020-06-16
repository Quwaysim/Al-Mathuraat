/*
 * Created by Muhammad Akorede Qaasim
 * on June 1, 2020
 */

package com.quwaysim.maathuraat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.quwaysim.maathuraat.R;

import java.util.Calendar;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    private CardView maathuraat, subhah, khamsoon, quraan;
    private TextView header_en;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();
        maathuraat = findViewById(R.id.maathuraaat_card);
        subhah = findViewById(R.id.subhah_card);
        khamsoon = findViewById(R.id.khamsoon_card);
        quraan = findViewById(R.id.Quraan_card);
        header_en = findViewById(R.id.header_en);

        String[] dailyMsg = getResources().getStringArray(R.array.header_strings);
        header_en.setText(setDailyMsg(dailyMsg));


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
}
