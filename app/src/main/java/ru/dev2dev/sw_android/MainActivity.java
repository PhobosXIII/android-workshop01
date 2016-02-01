package ru.dev2dev.sw_android;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends BaseActivity {

    private ListView listView;
    private ProgressBar progressBar;
    private ArrayList<Person> mPersons;

    private BroadcastReceiver broadcastReceiver;

    public final static String BROADCAST_ACTION = "ru.dev2dev.sw_android.MainActivity";
    public final static String PEOPLE_EXTRA = "people";


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

        registerBroadcastReceiver();
        getPeople(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState(Bundle outState)");
        super.onSaveInstanceState(outState);
        outState.putSerializable("persons", mPersons);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);
    }

    private void showPersons(ArrayList<Person> persons) {
        mPersons = persons;
        PersonAdapter adapter = new PersonAdapter(this, persons);
        listView.setAdapter(adapter);
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

    private class GetPeopleTask extends AsyncTask<String, Void, ArrayList<Person>> {

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected ArrayList<Person> doInBackground(String... params) {

            try {

                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.addRequestProperty("User-Agent", "ws-sw-android-" + Build.VERSION.RELEASE);
                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                in.close();

                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray results = jsonObject.getJSONArray("results");
                ArrayList<Person> persons = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    Person person = jsonToPerson(results.getJSONObject(i));
                    persons.add(person);
                }

                return persons;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Person> result) {
            if (result!=null) {
                showPersons(result);
            }
            showProgress(false);
        }

    }

    private void registerBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, final Intent intent) {
                showProgress(false);
                if (intent.getSerializableExtra(PEOPLE_EXTRA)!=null) {
                    ArrayList<Person> persons = (ArrayList<Person>) intent.getSerializableExtra(PEOPLE_EXTRA);
                    if (persons!=null) {
                        showPersons(persons);
                    }
                }

            }
        };
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void getPeople(Bundle savedInstanceState) {
        if (savedInstanceState!=null && savedInstanceState.getSerializable("persons")!=null) {
            showPersons((ArrayList<Person>) savedInstanceState.getSerializable("persons"));
        } else {
//            GetPeopleTask getPeopleTask = new GetPeopleTask();
//            getPeopleTask.execute(API.API_URL+API.PEOPLE_PATH);
            startService(new Intent(this, GetPeopleService.class));
            showProgress(true);
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
