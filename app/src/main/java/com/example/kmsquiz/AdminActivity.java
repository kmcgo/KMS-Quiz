package com.example.kmsquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Bottom menu bar for settings, user progress, etc
        BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(2);

        menuItem.setChecked(true);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(AdminActivity.this, StartingScreenActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_user:
                        Intent intent2 = new Intent(AdminActivity.this, UserActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_admin:
                        break;
                }
                return false;
            }
        });
    }
}
