package com.esgi.ecole.bestway.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esgi.ecole.bestway.R;
import com.esgi.ecole.bestway.commun.BestWayClient;
import com.esgi.ecole.bestway.commun.Utils;
import com.esgi.ecole.bestway.models.Session;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class InscriptionActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button validerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        setTitle(getString(R.string.inscriptionTitle));

        assignViews();



        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailEditText.getText() != null && !emailEditText.getText().toString().isEmpty() &&
                        passwordEditText.getText() != null && !passwordEditText.getText().toString().isEmpty()
                        ){
                    if(Utils.isValidEmailAddress(emailEditText.getText().toString()))
                         registerUser();
                    else {
                        Snackbar snackbar = Snackbar
                                .make(v, "Veuillez saisir une adresse email valide", Snackbar.LENGTH_LONG);

                        snackbar.show();
                    }


                }else{
                    Snackbar snackbar = Snackbar
                            .make(v, "Veuillez remplir tous les champs", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            }
        });


        getSupportActionBar().hide();


    }

    private void assignViews(){
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        passwordEditText =(EditText) findViewById(R.id.editTextMdp);
        validerButton = (Button) findViewById(R.id.buttonValider);
    }




    private void registerUser(){
        String email = emailEditText.getText().toString();
        String mdp = passwordEditText.getText().toString();

       // BestWayClient.post();
        String url = BestWayClient.BASE_URL + BestWayClient.REGISTER;

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
                  //  Log.e("Success",session.getToken() + " userId : " + session.getUserId());

                    BestWayClient.saveSession(InscriptionActivity.this,session);

                    Intent i = new Intent(InscriptionActivity.this , PreferencesActivity.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("ERROR","REGISTER");

            }
        });

    }

/*
    public static String parseTrafficResponse(JSONObject response){

        try {
            JSONObject resp = response.getJSONObject("response");
            if(resp!=null){
                traffic.setLine(resp.getString("line"));
                traffic.setStatus(resp.getString("slug"));
                traffic.setTitle(resp.getString("title"));
                traffic.setMessage(resp.getString("message"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return traffic;
    }
    */
}
