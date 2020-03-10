package com.example.androidlabs1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    ProgressBar progress;
    ImageView weatherImageView;
    TextView currentTempTextView;
    TextView minTempTextView;
    TextView maxTempTextView;
    TextView uvTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherImageView = (ImageView) findViewById(R.id.currentWeather);
        currentTempTextView = findViewById(R.id.currentTem);
        minTempTextView = findViewById(R.id.minTem);
        maxTempTextView = findViewById(R.id.maxTem);
        uvTextView = findViewById(R.id.uv);

        ForecastQuery forQuery = new ForecastQuery();
        forQuery.execute();

        progress = (ProgressBar) findViewById(R.id.progress1) ;
        progress.setVisibility(View.VISIBLE);
    }
    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String uv;
        String minTemp;
        String maxTemp;
        String currentTemp;
        Bitmap image;

        public String doInBackground(String... args) {
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("temperature")) {
                            currentTemp = xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            minTemp = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            maxTemp = xpp.getAttributeValue(null, "max");
                            publishProgress(75);

                        } else if (xpp.getName().equals("weather")) {
                            String icon = xpp.getAttributeValue(null, "icon");
                            if (fileExistance(icon+".png")) {
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(icon+".png");
                                    image = BitmapFactory.decodeStream(fis);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                Log.i("Found", "Found image from local");
                            } else {

                                URL imageUrl = new URL("http://openweathermap.org/img/w/" + icon + ".png");
                                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                                connection.connect();
                                int responseCode = connection.getResponseCode();
                                if (responseCode == 200) {
                                    image = BitmapFactory.decodeStream(connection.getInputStream());
                                }

                                Log.i("Found","Found image from URL and download it");

                                FileOutputStream outputStream = openFileOutput(icon + ".png", Context.MODE_PRIVATE);
                                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();

                            }
                        }
                        publishProgress(100);
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            try {
                URL uvUrl = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                HttpURLConnection uvUrlConnection = (HttpURLConnection) uvUrl.openConnection();
                InputStream inputStream = uvUrlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject jObject = new JSONObject(result);
                double value = jObject.getDouble("value");
                uv = String.valueOf(value);
            }catch(Exception e) {
                Log.e("Error",e.getMessage());
            }

            return "Done";
        }
            public void onProgressUpdate (Integer ...value){
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(value[0]);
            }

            public void onPostExecute (String fromDoInBackground){
            //super.onPostExecute(fromDoInBackground);
            currentTempTextView.setText(getString(R.string.Lab6te1)+currentTemp);
                minTempTextView.setText(getString(R.string.Lab6te2)+minTemp);
                maxTempTextView.setText(getString(R.string.Lab6te3) +maxTemp);
                uvTextView.setText(getString(R.string.Lab6te4)+uv);
                weatherImageView.setImageBitmap(image);


                progress.setVisibility(View.INVISIBLE);
                Log.i("Http", fromDoInBackground);
            }

        }

    private boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();   }

}
