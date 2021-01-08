package events;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import helpers.WeatherService;
import interfaces.VolleyHelper;

public class EventCityById {

    private Context context;

    public EventCityById(Context context){
        this.context = context;
    }


    public View.OnClickListener getCityById(EditText name){
        System.out.println("CITY NAME "+name.getText().toString().trim());
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(context
                            ,"CHAMP VIDE",Toast.LENGTH_LONG).show();
                } else {
                    String cityId = new WeatherService(context)
                            .getCityId(name.getText().toString().trim(),
                                    new VolleyHelper() {
                                        @Override
                                        public void onResponse(String cityId) {
                                            Toast.makeText(context,
                                                    "City Id ="+cityId,Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onError(String message) {
                                            Toast.makeText(context,
                                                    message,Toast.LENGTH_LONG).show();
                                        }
                                    });


                }
           }
        };
    }


}
