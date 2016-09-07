package com.esgi.ecole.bestway.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.esgi.ecole.bestway.R;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.home_page_title));

        assignViews();

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , ResulatActivity.class);
                startActivity(i);
            }
        });
    }

    public void assignViews(){
        goButton = (Button) findViewById(R.id.buttonGo);
    }

}
