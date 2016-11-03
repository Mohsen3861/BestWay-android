package com.esgi.ecole.bestway.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.esgi.ecole.bestway.R;
import com.esgi.ecole.bestway.models.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button goButton;
    EditText departEditText;
    EditText arriveEditText;
    ImageView locationImage;

    String dep ="";
    String arr ="";
    public static String TAG = "GOOGLE PLACES";

    private static final int REQUEST_CODE_AUTOCOMPLETE_DEPART = 1;
    private static final int REQUEST_CODE_AUTOCOMPLETE_ARRIVE = 2;

    int GOOGLE_API_CLIENT_ID = 0;


    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.home_page_title));

        //focus out editText when activity starts
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        assignViews();

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(arr != null && !arr.isEmpty() && !dep.isEmpty() && dep != null) {
                Intent i = new Intent(MainActivity.this, ResulatActivity.class);
                i.putExtra("arr", arr);
                i.putExtra("dep", dep);
                i.putExtra("choices", getChoices());
                startActivity(i);
            }else{
                Snackbar snackbar = Snackbar
                        .make(v, "Veuillez remplir tous les champs", Snackbar.LENGTH_LONG);

                snackbar.show();
            }

            }
        });


        departEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                openAutocompleteActivity(REQUEST_CODE_AUTOCOMPLETE_DEPART);

            }
        });

        arriveEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                openAutocompleteActivity(REQUEST_CODE_AUTOCOMPLETE_ARRIVE);

            }
        });


        locationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentPlace();
            }
        });
    }

    public void assignViews() {

        goButton = (Button) findViewById(R.id.buttonGo);
        departEditText = (EditText) findViewById(R.id.editTextDepart);
        arriveEditText = (EditText) findViewById(R.id.editTextArrive);
        locationImage = (ImageView) findViewById(R.id.location_icon);

    }


    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_DEPART) {

            if(resultCode ==  RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + place.getLatLng().toString());

                dep = place.getLatLng().latitude+","+place.getLatLng().longitude;
                // Format the place's details and display them in the TextView.
                departEditText.setText(place.getAddress());

            }
        }

        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_ARRIVE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + place.getName());

                arr = place.getLatLng().latitude+","+place.getLatLng().longitude;

                // Format the place's details and display them in the TextView.
                arriveEditText.setText(place.getAddress());
            }

        }
    }


    private void openAutocompleteActivity(int requestCode) {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.

            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, requestCode);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e("GOOGLE PLACES", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }


    public void getCurrentPlace() {

        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
                "Chargement ... ", true);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            dialog.dismiss();
            return;
        }
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                .getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    Log.i(TAG, String.format("Place '%s' has likelihood: %g",
                            placeLikelihood.getPlace().getName(),
                            placeLikelihood.getLikelihood()));
                    departEditText.setText(placeLikelihood.getPlace().getAddress());
                }

                likelyPlaces.release();
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }


    public String getChoices() {
        SharedPreferences prefs = getSharedPreferences("Choices", Context.MODE_PRIVATE);
        return prefs.getString("Choices", null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
