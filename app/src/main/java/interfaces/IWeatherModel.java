package interfaces;

import java.util.List;

import models.WeatherReportModel;

public interface IWeatherModel {
    void onResponse(List<WeatherReportModel> model);
    void onError(String message);
}
