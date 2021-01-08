package helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplicationbrieftwo.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import models.Weather;

public class WeatherArrayAdapter extends ArrayAdapter<Weather> {
    private List<Weather> weathers;
    private Context context;
    public WeatherArrayAdapter(@NonNull Context context, List<Weather> weathers) {
        super(context,0, weathers);
        this.context=context;
        this.weathers=weathers;
    }

    private Bitmap getBitmapfromURL(String adresse) throws IOException {
        String source ="";
        switch(adresse){
            case "sn":
                source = "https://png.pngtree.com/png-clipart/20190516/original/pngtree-snow-vector-icon-png-image_3725323.jpg";
                break;
            case "sl":
                source="https://cdn2.iconfinder.com/data/icons/weather-icons-8/512/sleet-512.png";
                break;
            case "t":
                source="https://st2.depositphotos.com/8511412/11363/v/950/depositphotos_113633822-stock-illustration-storm-storm-icon-storm-icon.jpg";
                break;
            case "h":
                source="https://icon-library.com/images/hail-icon/hail-icon-19.jpg";
                break;
            case "hr":
                source="https://png.pngtree.com/png-clipart/20190516/original/pngtree-snow-vector-icon-png-image_3725323.jpg";
                break;
            case "lr":
                source="https://png.pngtree.com/element_our/20190601/ourlarge/pngtree-light-rain-icon-image_1366071.jpg";
                break;
            case "s":
                source="data:image/png;base64,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";
                break;
            case "hc":
                source="https://png.pngtree.com/png-clipart/20190516/original/pngtree-snow-vector-icon-png-image_3725323.jpg";
                break;
            case "lc":
                source="https://i.pinimg.com/originals/01/8d/54/018d5482518ac2ea8d3093dd24df8aeb.jpg";
                break;
            case "c":
                source="https://icon-library.com/images/sunny-weather-icon/sunny-weather-icon-13.jpg";
                break;
        }

        URL url = new URL(source);

        URLConnection connection=url.openConnection();

        HttpsURLConnection HCon=(HttpsURLConnection)connection;

        int ResCode=HCon.getResponseCode();

        System.out.println("Responce Code is = "+ResCode);

        Bitmap b=null;
        if(ResCode== HttpURLConnection.HTTP_OK)
        {
            InputStream ins=((URLConnection)HCon).getInputStream();
            b = BitmapFactory.decodeStream(ins);
        }
        return b;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listWeather = convertView;

        if(listWeather==null)
            listWeather= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        Weather currentWeather = this.weathers.get(position);

        ImageView imageView = (ImageView) listWeather.findViewById(R.id.imageView_poster);
        try {
            System.out.println("weather : "+currentWeather.getImageSRC());
            imageView.setImageBitmap(getBitmapfromURL(currentWeather.getImageSRC()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView temmin = (TextView) listWeather.findViewById(R.id.textView_name);
        temmin.setText(currentWeather.getTempMin()+" Degres");

        TextView temp_max = (TextView) listWeather.findViewById(R.id.textView_max_temp);
        temp_max.setText(currentWeather.getTempMax()+" Degres");

        return listWeather;
    }
}
