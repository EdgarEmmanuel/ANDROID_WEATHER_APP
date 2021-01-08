package com.example.myapplicationbrieftwo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import events.EventCityById;
import events.EventWeatherById;
import events.EventWeatherByName;
import interfaces.IWeather;
import interfaces.IWeatherModel;
import interfaces.VolleyHelper;
import helpers.WeatherService;
import models.Weather;
import models.WeatherReportModel;

public class MainActivity extends AppCompatActivity {

    //get the value of the widgets in the activity
    private Button btn_IdByCity , getWeatherByCityName , btn_weatherByID;

    private EditText tf_cityName;

    public ListView listOfResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permit all the request from the thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //do a binding for the widgets on the layout by their ID
        this.btn_IdByCity = findViewById(R.id.get_cityId);
        this.getWeatherByCityName = findViewById(R.id.btn_getWeatherByCityName);
        this.btn_weatherByID = findViewById(R.id.btn_getWeatherByCityID);
        this.tf_cityName  = findViewById(R.id.editTextTextPersonName2);
        this.listOfResults = findViewById(R.id.listViewResults);

        //instantiate the Weather Serv
        final WeatherService weatherService = new WeatherService(MainActivity.this);

        //add the click listeners events
        this.btn_weatherByID.setOnClickListener(
                new EventWeatherById(MainActivity.this)
                       .getWeatherById(tf_cityName,listOfResults)
        );


        this.getWeatherByCityName.setOnClickListener(
                new EventWeatherByName(MainActivity.this)
                        .getWeatherByName(tf_cityName,listOfResults)
        );


        this.btn_IdByCity.setOnClickListener(
                new EventCityById(MainActivity.this)
                        .getCityById(tf_cityName)
        );

    }

}