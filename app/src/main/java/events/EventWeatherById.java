package events;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import helpers.WeatherService;
import interfaces.IWeatherModel;
import models.WeatherReportModel;

public class EventWeatherById {
    private Context context;

    public EventWeatherById(Context context){
        this.context = context;
    }


    public View.OnClickListener getWeatherById(EditText name, ListView listOfResults){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(context
                            ,"THE FIELD SHOULD NOT BE EMPTY",Toast.LENGTH_LONG).show();
                }else {
                    try {
                        int val = Integer.parseInt(name.getText().toString().trim());
                        List<WeatherReportModel> mv = new WeatherService(context)
                                .getCityReportById("" + val, new IWeatherModel() {
                                    @Override
                                    public void onResponse(List<WeatherReportModel> model) {

                                        //set all the content in the ListView Widget
                                        ArrayAdapter arrayAdapter = new ArrayAdapter(
                                                context,
                                                android.R.layout.simple_list_item_activated_1,
                                                model
                                        );

                                        //print the list View
                                        listOfResults.setAdapter(arrayAdapter);
                                    }

                                    @Override
                                    public void onError(String message) {
                                        Toast.makeText(context
                                                , "THIS ID HAS NO RESULT", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } catch (Exception ex) {
                        Toast.makeText(context, "AN ERROR OCCURED", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
    }




}
