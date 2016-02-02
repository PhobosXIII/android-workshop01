package ru.dev2dev.sw_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ListView listView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person = (Person) listView.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), PersonActivity.class)
                        .putExtra(PersonActivity.PERSON_EXTRA, person);
                startActivity(intent);
            }
        });
        new GetPeopleTask().execute();
    }

    private void showPeople(ArrayList<Person> people) {
        if (people != null) {
            PersonAdapter adapter = new PersonAdapter(this, people);
            listView.setAdapter(adapter);
        }
    }

    private void showProgress(boolean isShow) {
        if (isShow) {
            listView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private class GetPeopleTask extends AsyncTask<Void, Void, ArrayList<Person>> {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "AsyncTask onPreExecute()");
            showProgress(true);
        }
        @Override
        protected ArrayList<Person> doInBackground(Void... params) {
            Log.d(TAG, "AsyncTask doInBackground()");

            BufferedReader reader = null;
            try {
                SystemClock.sleep(5000);

                URL url = new URL("http://swapi.co/api/people/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("User-Agent", "ws-sw-android-" + Build.VERSION.RELEASE);
                connection.setRequestMethod("GET");
                connection.connect();

                int status = connection.getResponseCode();
                if (status == 200) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((inputLine = reader.readLine()) != null) {
                        stringBuilder.append(inputLine);
                    }

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    JSONArray results = jsonObject.getJSONArray("results");
                    ArrayList<Person> people = new ArrayList<>();
                    for (int i = 0; i < results.length(); i++) {
                        Person person = jsonToPerson(results.getJSONObject(i));
                        people.add(person);
                    }

                    return people;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Person> result) {
            Log.d(TAG, "AsyncTask onPostExecute()");
            showProgress(false);
            showPeople(result);
        }
    }

    private Person jsonToPerson(JSONObject jsonObject) {
        try {
            String name = jsonObject.getString("name");
            String height = jsonObject.getString("height");
            String mass = jsonObject.getString("mass");
            String eyeColor = jsonObject.getString("eye_color");
            String birthYear = jsonObject.getString("birth_year");
            String gender = jsonObject.getString("gender");

            return new Person(name, height, mass, eyeColor, birthYear, gender);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
