package com.esgi.ecole.bestway.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.esgi.ecole.bestway.R;

public class LoginActivity extends AppCompatActivity {

    public Button connexionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        assignViews();

        connexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this , PreferencesActivity.class);
                startActivity(i);

            }
        });
    }

    public void assignViews(){
        connexionButton = (Button) findViewById(R.id.buttonConnexion);

    }
}
