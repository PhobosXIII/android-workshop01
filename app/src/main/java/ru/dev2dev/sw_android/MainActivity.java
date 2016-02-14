package ru.dev2dev.sw_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ListView listView;
    private ProgressBar progressBar;

    private BroadcastReceiver peopleReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(SwService.EXTRA_SUCCESS)) {
                ArrayList<Person> people = (ArrayList<Person>) intent.getSerializableExtra(SwService.EXTRA_SUCCESS);
                showPeople(people);
            }
            if (intent.hasExtra(SwService.EXTRA_ERROR)) {
                String error = intent.getStringExtra(SwService.EXTRA_ERROR);
                showError(error);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPerson(position);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter ifPeople = new IntentFilter(SwService.ACTION_GET_PEOPLE);
        LocalBroadcastManager.getInstance(this).registerReceiver(peopleReceiver, ifPeople);

        showProgress(true);
        SwService.getPeople(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(peopleReceiver);
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
}
