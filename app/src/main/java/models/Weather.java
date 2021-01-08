package models;

public class Weather {

    private String imageSRC;

    private float tempMin;

    private float tempMax;

    public Weather(String imageSRC , float tempMin , float tempMax){
        this.imageSRC=imageSRC;
        this.tempMin=tempMin;
        this.tempMax = tempMax;
    }

    public String getImageSRC() {
        return imageSRC;
    }

    public void setImageSRC(String imageSRC) {
        this.imageSRC = imageSRC;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "imageSRC='" + imageSRC + '\'' +
                ", tempMin='" + tempMin + '\'' +
                ", tempMax='" + tempMax + '\'' +
                '}';
    }
}
