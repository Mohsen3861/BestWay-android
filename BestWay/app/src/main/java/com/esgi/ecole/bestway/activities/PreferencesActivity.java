package com.esgi.ecole.bestway.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.esgi.ecole.bestway.R;

import java.util.ArrayList;

public class PreferencesActivity extends AppCompatActivity{

    ImageView imageViewCar;
    ImageView imageViewBus;
    ImageView imageViewWalk;
    ImageView imageViewMetro;
    ImageView imageViewBike;
    Button terminerButton;

    ArrayList<Integer> choices =  new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        setTitle(getString(R.string.Preferences_title));

        assignViews();
        initClickMethodes();
    }

    public void assignViews(){

        imageViewCar = (ImageView) findViewById(R.id.imageViewCarSelect);
        imageViewBus = (ImageView) findViewById(R.id.imageViewBusSelect);
        imageViewWalk = (ImageView) findViewById(R.id.imageViewWalkSelect);
        imageViewMetro = (ImageView) findViewById(R.id.imageViewMetroSelect);
        imageViewBike = (ImageView) findViewById(R.id.imageViewBikeSelect);
        terminerButton = (Button) findViewById(R.id.buttonTerminer);

    }


    public void initClickMethodes(){

        imageViewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageViewCar.isSelected()) {
                    imageViewCar.setImageResource(R.drawable.car_selected);
                    imageViewCar.setSelected(true);
                    choices.add(5);
                }else{
                    imageViewCar.setImageResource(R.drawable.car_unselect);
                    imageViewCar.setSelected(false);
                    choices.remove(Integer.valueOf(5));

                }
            }
        });


        imageViewBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageViewBus.isSelected()) {
                    imageViewBus.setImageResource(R.drawable.bus_selected);
                    imageViewBus.setSelected(true);
                    choices.add(4);
                }else{
                    imageViewBus.setImageResource(R.drawable.bus_unselect);
                    imageViewBus.setSelected(false);
                    choices.remove(Integer.valueOf(4));
                }
            }
        });


        imageViewWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageViewWalk.isSelected()) {
                    imageViewWalk.setImageResource(R.drawable.walk_selected);
                    imageViewWalk.setSelected(true);
                    choices.add(1);

                }else{
                    imageViewWalk.setImageResource(R.drawable.walk_unselect);
                    imageViewWalk.setSelected(false);
                    choices.remove(Integer.valueOf(1));

                }
            }
        });


        imageViewMetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageViewMetro.isSelected()) {
                    imageViewMetro.setImageResource(R.drawable.metro_selected);
                    imageViewMetro.setSelected(true);
                    choices.add(3);
                }else{
                    imageViewMetro.setImageResource(R.drawable.metro_unselect);
                    imageViewMetro.setSelected(false);
                    choices.remove(Integer.valueOf(3));
                }
            }
        });



        imageViewBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageViewBike.isSelected()) {
                    imageViewBike.setImageResource(R.drawable.bike_selected);
                    imageViewBike.setSelected(true);
                    choices.add(2);

                }else{
                    imageViewBike.setImageResource(R.drawable.bike_unselect);
                    imageViewBike.setSelected(false);
                    choices.remove(Integer.valueOf(2));
                }
            }
        });

        terminerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("choices" , choices.toString());
                saveChoices();
                Intent i = new Intent(PreferencesActivity.this , MainActivity.class);
                startActivity(i);
            }
        });

    }


    private void saveChoices(){

        SharedPreferences.Editor editor = getSharedPreferences("Choices", Context.MODE_PRIVATE).edit();
        String finalString = choices.toString().replace(" ","").replace("[","").replace( "]" , "");

        editor.putString("Choices",finalString);

        editor.apply();

    }


}
