package com.esgi.ecole.bestway.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esgi.ecole.bestway.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.home_page_title));

    }
}
