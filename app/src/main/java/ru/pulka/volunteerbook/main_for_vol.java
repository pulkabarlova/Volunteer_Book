package ru.pulka.volunteerbook;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class main_for_vol extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_vol);
        View fragment_home = findViewById(R.id.fragment1);
        View fragment_main = findViewById(R.id.fragment2);
        View fragment_settings = findViewById(R.id.fragment3);

        BottomNavigationView bnv = findViewById(R.id.buttomNavView);
        fragment_home.setVisibility(View.GONE);
        fragment_main.setVisibility(View.VISIBLE);
        fragment_settings.setVisibility(View.GONE);

        bnv.setOnItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        default:
                            fragment_home.setVisibility(View.GONE);
                            fragment_main.setVisibility(View.GONE);
                            fragment_settings.setVisibility(View.GONE);
                        case R.id.main_frag:
                            fragment_home.setVisibility(View.VISIBLE);
                            fragment_main.setVisibility(View.GONE);
                            fragment_settings.setVisibility(View.GONE);
                            break;
                        case R.id.home_frag:
                            fragment_home.setVisibility(View.GONE);
                            fragment_main.setVisibility(View.VISIBLE);
                            fragment_settings.setVisibility(View.GONE);
                            break;
                        case R.id.blankFragment:
                            fragment_home.setVisibility(View.GONE);
                            fragment_main.setVisibility(View.GONE);
                            fragment_settings.setVisibility(View.VISIBLE);
                            break;
                    }
                    return true;
                });
    }
}