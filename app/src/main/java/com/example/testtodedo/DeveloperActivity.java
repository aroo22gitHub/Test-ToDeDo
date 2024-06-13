package com.example.testtodedo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DeveloperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_tasks:
                        startActivity(new Intent(DeveloperActivity.this, MainActivity.class));
                        return true;
                    case R.id.nav_settings:
                        startActivity(new Intent(DeveloperActivity.this, UserActivity.class));
                        return true;
                    case R.id.nav_info:
                        // Already on developer activity
                        return true;
                }
                return false;
            }
        });

        // Set the selected item to Info
        bottomNavigationView.setSelectedItemId(R.id.nav_info);
    }
}

