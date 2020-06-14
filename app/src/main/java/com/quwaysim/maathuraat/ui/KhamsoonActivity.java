package com.quwaysim.maathuraat.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quwaysim.maathuraat.R;
import com.quwaysim.maathuraat.adapters.DuaAdapter;

public class KhamsoonActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DuaAdapter mDuaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khamsoon);
        mRecyclerView = findViewById(R.id.khamsoon_recyclerView);
        String[] khamsoonList = getResources().getStringArray(R.array.khamsoon);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int fontSize = sharedPreferences.getInt(getResources().getString(R.string.key_size), 24);

        mDuaAdapter = new DuaAdapter(this,
                khamsoonList,
                null,
                R.layout.khamsoon_recyclerview_item,
                fontSize);

        mRecyclerView.setAdapter(mDuaAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}