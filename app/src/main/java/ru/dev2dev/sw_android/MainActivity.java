package ru.dev2dev.sw_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    private ListView listView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPerson(position);
            }
        });

        new GetPeopleTask().execute();
    }

    private void showPerson(int position) {
        Person person = (Person) listView.getAdapter().getItem(position);
        Intent intent = new Intent(MainActivity.this, PersonActivity.class)
                .putExtra(PersonActivity.EXTRA_PERSON, person);
        startActivity(intent);
    }

    private void showPeople(ArrayList<Person> people) {
        showProgress(false);
        ArrayAdapter<Person> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, people);
        listView.setAdapter(adapter);

    }

    private void showError(String error) {
        showProgress(false);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void showProgress(boolean isShow) {
        listView.setVisibility(isShow ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    private class GetPeopleTask extends AsyncTask<Void, Void, PeopleResult> {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "AsyncTask onPreExecute()");
            showProgress(true);
        }

        @Override
        protected PeopleResult doInBackground(Void... params) {
            Log.d(TAG, "AsyncTask doInBackground()");

            PeopleResult result = new PeopleResult();
            String url = "http://swapi.co/api/people/";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("User-Agent", "ws-sw-android-" + Build.VERSION.RELEASE)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d(TAG, body);
                    result.setPeople(Person.getList(body));
                }
            } catch (IOException | JSONException e) {
                Log.e(TAG, "doInBackground: ", e);
                result.setError("Something wrong");
            }

            return result;
        }

        @Override
        protected void onPostExecute(PeopleResult result) {
            Log.d(TAG, "AsyncTask onPostExecute()");
            if (result.getError() != null) {
                showError(result.getError());
                return;
            }
            if (result.getPeople() != null) {
                showPeople(result.getPeople());
            }
        }
    }
}
