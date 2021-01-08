package helpers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplicationbrieftwo.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import interfaces.IWeather;
import interfaces.IWeatherModel;
import interfaces.VolleyHelper;
import models.Weather;
import models.WeatherReportModel;

public class WeatherService {


    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_WEATHER_FROM_NAME = "https://www.metaweather.com/api/location/search/?query=";
    public  static final String QUERY_FOR_WEATHER_FROM_ID = "https://www.metaweather.com/api/location/";

    private Context context;

    String cityId=null;

    public WeatherService(Context ctx){
        this.context = ctx;
    }

    public String getCityId(String cityName , VolleyHelper volley_Helper){

        String url =
                QUERY_FOR_CITY_ID
                        +cityName.trim();


        //get json response for the request
        JsonArrayRequest jarequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length()==0){
                    //when there is no data in the array
                    Toast.makeText(context
                            ,"THIS COUNTRY DOESN'T EXIST",Toast.LENGTH_LONG).show();

                }else{
                    //when the response comes with some data
                    JSONObject cityInfo = null;
                    cityId = null;

                    try {
                        cityInfo = response.getJSONObject(0);
                        cityId = cityInfo.getString("woeid");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    volley_Helper.onResponse(cityId);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                volley_Helper.onError("Something Wrong happened");
            }
        });

        //do the request
        MySingleton.getInstance(context)
                .addToRequestQueue(jarequest);


        return cityId;

    }


    public List<WeatherReportModel> getCityReportById(String cityId, IWeatherModel iWeatherModel){

        String url = QUERY_FOR_WEATHER_FROM_ID + cityId +"/";


        //get the json object
        JsonObjectRequest request_for_weather = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<WeatherReportModel> weatherReportModels = new ArrayList<>();

                        //get the property called "consolidated_weather"
                        JSONArray list_of_results=null;

                        //instantiate a weatherReportModels Model
                        WeatherReportModel one_day_weather = null;

                        try {
                            list_of_results = response.getJSONArray("consolidated_weather");

                            for (int i=0;i<list_of_results.length();i++) {

                                //firstly get the JSON
                                JSONObject first_of_result = (JSONObject) list_of_results.get(i);

                                //get the first instance of the array
                                one_day_weather = new WeatherReportModel();
                                one_day_weather.setId(first_of_result.getInt("id"));
                                one_day_weather.setAir_pressure(first_of_result.getInt("air_pressure"));
                                one_day_weather.setApplicable_date(first_of_result.getString("applicable_date"));
                                one_day_weather.setCreated(first_of_result.getString("created"));
                                one_day_weather.setHumidity(first_of_result.getInt("humidity"));
                                one_day_weather.setMax_temp(Float.parseFloat(first_of_result.getString("max_temp")));
                                one_day_weather.setMin_temp(Float.parseFloat(first_of_result.getString("min_temp")));
                                one_day_weather.setThe_temp(Float.parseFloat(first_of_result.getString("the_temp")));
                                one_day_weather.setWeather_state_abbr(first_of_result.getString("weather_state_abbr"));
                                one_day_weather.setPredictability(first_of_result.getInt("predictability"));
                                one_day_weather.setVisibility(Float.parseFloat(first_of_result.getString("visibility")));
                                one_day_weather.setWind_speed(Float.parseFloat(first_of_result.getString("wind_speed")));
                                one_day_weather.setWind_direction_compass(first_of_result.getString("wind_direction_compass"));
                                one_day_weather.setWeather_state_name(first_of_result.getString("weather_state_name"));
                                one_day_weather.setWind_direction(Float.parseFloat(first_of_result.getString("wind_direction")));

                                //put in the list of WeatherReportModel
                                weatherReportModels.add(one_day_weather);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        iWeatherModel.onResponse(weatherReportModels);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iWeatherModel.onError(error.toString());
                    }
                }

        );

        //do the request
        MySingleton.getInstance(context)
                .addToRequestQueue(request_for_weather);



        return null;
    }


    //this function helps the function at the bottom
    public List<Weather> specialFunction(String woeid,IWeather iweather){
        //url for the second request
        String url_second=QUERY_FOR_WEATHER_FROM_ID+woeid;

        //get the json object
        JsonObjectRequest request_for_weather_second = new JsonObjectRequest(Request.Method.GET,
                url_second, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //instantiate the list
                        List<Weather> weathers = new ArrayList<>();

                        JSONArray firt_array = null;

                        Weather weather = null;

                        try {
                            //get the result from the first array
                            firt_array = response.getJSONArray("consolidated_weather");


                            for(int j=0;j<firt_array.length();j++){

                                //get the array at the position j
                                JSONObject array = (JSONObject) firt_array.get(j);

                                //create the weather model
                                weather = new Weather(
                                        array.getString("weather_state_abbr"),
                                        array.getLong("min_temp"),
                                        array.getLong("max_temp")
                                );

                                //add in the list
                                weathers.add(weather);
                            }

                        }catch (Exception ex){
                            iweather.onError(ex.toString());
                        }

                        iweather.onResponse(weathers);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iweather.onError("ERROR OCCURED AT THE SECOND REQUEST");
                    }
                }
        );

        //do the request
        MySingleton.getInstance(context)
                .addToRequestQueue(request_for_weather_second);

        return null;
    }





    public void getCityReportByName(String Name, IWeather iweather){
        //url for the first request
        String url = QUERY_FOR_WEATHER_FROM_NAME+Name;

        //do the request
        JsonArrayRequest request_for_weather_by_name = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Weather> weathers = new ArrayList<>();

                        //get the first json
                        String woeid_of_the_result = null;

                        //instantiate one model
                        Weather weather = null;

                        try {
                            //get the array of results
                            JSONObject result_first = response.getJSONObject(0);

                            //get the woeid of the result
                            woeid_of_the_result = result_first.getString("woeid");

                            //call the function for the second request
                            specialFunction(woeid_of_the_result,iweather);

                        }catch (Exception e){
                            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    iweather.onError("ERROR OCCURED AT THE FIRST REQUEST");
                }
            }
        );

        //do the request
        MySingleton.getInstance(context)
                .addToRequestQueue(request_for_weather_by_name);


    }




}
