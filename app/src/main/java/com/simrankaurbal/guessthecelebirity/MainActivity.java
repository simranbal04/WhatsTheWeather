package com.simrankaurbal.guessthecelebirity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public ImageView background;
    public TextView cityname;
    public EditText citytext;
    public Button findbutton;
    public EditText result;

    public void findweather(View view)
    {
        Log.i("cityname",cityname.getText().toString());

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        background = (ImageView) findViewById(R.id.background);
        cityname = (TextView) findViewById(R.id.cityname);
        citytext = (EditText) findViewById(R.id.citytext);
        findbutton = (Button) findViewById(R.id.findbutton);
        result = (EditText) findViewById(R.id.result);

        DownloadTask task = new DownloadTask();
        task.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
//
    }

    public class DownloadTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1)
                {
                    char current = (char) data;
//                    result += current;
                    result += current;
                    data = reader.read();
                }

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject jsonObject = new JSONObject(result);
               String weatherinfo =  jsonObject.getString("weather");

                Log.i("Weather Content", weatherinfo);

                JSONArray jsonArray = new JSONArray(weatherinfo);

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonpart = jsonArray.getJSONObject(i);

                    Log.i("main",jsonpart.getString("main"));
                    Log.i("description",jsonpart.getString("description"));
                    Log.i("icon",jsonpart.getString("icon"));
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }

}
