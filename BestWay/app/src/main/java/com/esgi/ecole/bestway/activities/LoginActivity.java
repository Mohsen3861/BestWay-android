package com.esgi.ecole.bestway.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.ecole.bestway.R;
import com.esgi.ecole.bestway.commun.BestWayClient;
import com.esgi.ecole.bestway.commun.Utils;
import com.esgi.ecole.bestway.models.Session;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    public  Button connexionButton;
    public  Button inscriptionButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        assignViews();

        connexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailEditText.getText() != null && !emailEditText.getText().toString().isEmpty() &&
                        passwordEditText.getText() != null && !passwordEditText.getText().toString().isEmpty()){
                    if(Utils.isValidEmailAddress(emailEditText.getText().toString()))
                     login();
                    else {
                        Snackbar snackbar = Snackbar
                                .make(v, "Veuillez saisir une adresse email valide", Snackbar.LENGTH_LONG);

                        snackbar.show();
                    }

                }else {
                    Snackbar snackbar = Snackbar
                            .make(v, "Veuillez remplir tous les champs", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }

            }
        });

        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this , InscriptionActivity.class);
                startActivity(i);
            }
        });

        getSupportActionBar().hide();
      //  getActionBar().hide();
    }

    public void assignViews(){
        connexionButton = (Button) findViewById(R.id.buttonConnexion);
        inscriptionButton = (Button) findViewById(R.id.buttonInscription);
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        passwordEditText =(EditText) findViewById(R.id.editTextPassword);
    }


    public void login(){

        String email = emailEditText.getText().toString();
        String mdp = passwordEditText.getText().toString();

        String url = BestWayClient.BASE_URL + BestWayClient.LOGIN;

        RequestParams params = new RequestParams();
        params.add("email",email);
        params.add("password",mdp);




        BestWayClient.post(url, params  , new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Session session = new Session();
                try {
                    session.setToken(response.getString("token"));
                    session.setUserId(response.getJSONObject("user").getString("_id"));
                    Log.e("Success",session.getToken() + " userId : " + session.getUserId());

                    BestWayClient.saveSession(LoginActivity.this,session);

                    if(getChoices()== null || getChoices().isEmpty()) {
                        Intent i = new Intent(LoginActivity.this, PreferencesActivity.class);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("ERROR","REGISTER");

                Toast.makeText(LoginActivity.this,"Votre identifiant ou mdp est incorrect ! ",Toast.LENGTH_LONG).show();

            }
        });

    }


    public String getChoices() {
        SharedPreferences prefs = getSharedPreferences("Choices", Context.MODE_PRIVATE);
        return prefs.getString("Choices", null);
    }
}
