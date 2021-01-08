package interfaces;

import java.util.List;

import models.Weather;

public interface IWeather {
    void onResponse(List<Weather> weathers);
    void onError(String message);
}
