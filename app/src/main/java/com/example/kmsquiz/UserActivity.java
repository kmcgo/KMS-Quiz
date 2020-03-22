package com.example.kmsquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Bottom menu bar for settings, user progress, etc
        BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(UserActivity.this, StartingScreenActivity.class);
                        startActivity(intent);
                        item.setChecked(false);
                        break;
                    case R.id.navigation_user:
                        item.setChecked(true);
                        break;
                    case R.id.navigation_admin:
                        Intent intent3 = new Intent(UserActivity.this, AdminActivity.class);
                        startActivity(intent3);
                        item.setChecked(false);

                        break;
                }
                return false;
            }
        });
    }
}
