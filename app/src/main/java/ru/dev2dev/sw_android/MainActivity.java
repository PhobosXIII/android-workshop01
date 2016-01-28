package ru.dev2dev.sw_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
}
