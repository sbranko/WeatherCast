package net.simplifiedcoding.navigationdrawerexample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elpatron on 11/3/2017.
 */

class CityWeatherModel implements Parcelable {

    private String currentWeather;
    private String cityName;
    private String weatherIcon;
    private double currentTemp;
    private Boolean isSelected;

    public CityWeatherModel(String currentWeather, String cityName, String weatherIcon, double currentTemp, Boolean isSelected) {
        this.currentWeather = currentWeather;
        this.cityName = cityName;
        this.currentTemp = currentTemp;
        this.weatherIcon = weatherIcon;
        this.isSelected = isSelected;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(String currentWeather) {
        this.currentWeather = currentWeather;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.currentWeather);
        dest.writeString(this.cityName);
        dest.writeString(this.weatherIcon);
        dest.writeDouble(this.currentTemp);
        dest.writeValue(this.isSelected);
    }

    protected CityWeatherModel(Parcel in) {
        this.currentWeather = in.readString();
        this.cityName = in.readString();
        this.weatherIcon = in.readString();
        this.currentTemp = in.readDouble();
        this.isSelected = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CityWeatherModel> CREATOR = new Parcelable.Creator<CityWeatherModel>() {
        @Override
        public CityWeatherModel createFromParcel(Parcel source) {
            return new CityWeatherModel(source);
        }

        @Override
        public CityWeatherModel[] newArray(int size) {
            return new CityWeatherModel[size];
        }
    };
}
