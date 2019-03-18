package com.example.android.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);
        loadWeatherData();
    }


        // TODO (4) Delete the dummy weather data. You will be getting REAL data from the Internet in this lesson.
        /*
         * This String array contains dummy weather data. Later in the course, we're going to get
         * real weather data. For now, we want to get something on the screen as quickly as
         * possible, so we'll display this dummy data.
         */


        // TODO (3) Delete the for loop that populates the TextView with dummy data
        /*
         * Iterate through the array and append the Strings to the TextView. The reason why we add
         * the "\n\n\n" after the String is to give visual separation between each String in the
         * TextView. Later, we'll learn about a better way to display lists of data.
         */


        // TODO (9) Call loadWeatherData to perform the network request to get the weather

    // TODO (8) Create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData
    private void loadWeatherData() {
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new WeatherTask().execute(location);
    }

    // TODO (5) Create a class that extends AsyncTask to perform network requests
    public class WeatherTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... urls) {
            if (urls.length == 0) {
                return null;
            }

            String location = urls[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);
            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
                String[] sampleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);
                return sampleJsonWeatherData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            if (weatherData != null) {
                for (String weatherString : weatherData) {
                    mWeatherTextView.append((weatherString) + "\n\n\n");
                }
            }
        }
    }
}

    // TODO (6) Override the doInBackground method to perform your network requests

    // TODO (7) Override the onPostExecute method to display the results of the network request