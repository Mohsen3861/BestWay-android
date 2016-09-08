package com.esgi.ecole.bestway.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.ecole.bestway.R;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    EditText departEditText;
    EditText arriveEditText;

    public static String TAG = "GOOGLE PLACES";

    private static final int REQUEST_CODE_AUTOCOMPLETE_DEPART = 1;
    private static final int REQUEST_CODE_AUTOCOMPLETE_ARRIVE = 2;

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

        departEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutocompleteActivity(REQUEST_CODE_AUTOCOMPLETE_DEPART);
            }
        });

        arriveEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutocompleteActivity(REQUEST_CODE_AUTOCOMPLETE_ARRIVE);
            }
        });
    }

    public void assignViews(){

        goButton = (Button) findViewById(R.id.buttonGo);
        departEditText = (EditText) findViewById(R.id.editTextDepart);
        arriveEditText = (EditText) findViewById(R.id.editTextArrive);

    }



    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_DEPART) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + place.getName());


                // Format the place's details and display them in the TextView.
                departEditText.setText(place.getAddress());

        }

        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_ARRIVE) {
            //if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + place.getName());


                // Format the place's details and display them in the TextView.
                arriveEditText.setText(place.getAddress());


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
}
