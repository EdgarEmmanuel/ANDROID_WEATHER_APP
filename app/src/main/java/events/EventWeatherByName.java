package events;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import helpers.WeatherArrayAdapter;
import helpers.WeatherService;
import interfaces.IWeather;
import models.Weather;

public class EventWeatherByName {

    private  Context context;

    public EventWeatherByName(Context context){
        this.context = context;
    }

    public  View.OnClickListener getWeatherByName(EditText name, ListView lists_weathers){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(context,
                            "THE FIELD SHOULD NOT BE EMPTY",
                            Toast.LENGTH_SHORT)
                            .show();
                }else{
                    new WeatherService(context)
                            .getCityReportByName(name.getText().toString().trim(), new IWeather() {
                                @Override
                                public void onResponse(List<Weather> weathers) {
                                    Toast.makeText(context,
                                            weathers.toString(),Toast.LENGTH_LONG).show();

                                    //instantiate the array adapter
                                    WeatherArrayAdapter war =
                                            new WeatherArrayAdapter(context,weathers);

                                    //set the data formatted in the lIstView
                                    lists_weathers.setAdapter(war);
                                }

                                @Override
                                public void onError(String message) {
                                    Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }

        };

    }


}
