package ru.dev2dev.sw_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
//        getPeople();
        GetPeopleTask getPeopleTask = new GetPeopleTask();
        getPeopleTask.execute(API.API_URL+API.PEOPLE_PATH);
    }

    private void getPeople() {
        showProgress(true);
        API.getApi().getPeople(new Callback<PeopleResponse>() {
            @Override
            public void success(PeopleResponse peopleResponse, Response response) {
                showProgress(false);
                if (peopleResponse.getPersons()!=null) {
                    showPersons(peopleResponse.getPersons());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showProgress(false);
                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPersons(ArrayList<Person> persons) {
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

    private class GetPeopleTask extends AsyncTask<String, Integer, ArrayList<Person>> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.show();
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

                int lengthOfFile = connection.getContentLength();

                while ((inputLine = in.readLine()) != null) {
                    stringBuilder.append(inputLine);
                    publishProgress((int)((stringBuilder.length()*100)/lengthOfFile));
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
        protected void onProgressUpdate(Integer... progress) {
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Person> result) {
            if (result!=null) {
                showPersons(result);
            }
            progressDialog.dismiss();
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
