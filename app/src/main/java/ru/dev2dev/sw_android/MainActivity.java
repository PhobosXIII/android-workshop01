package ru.dev2dev.sw_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private static final String KEY_POSITION = "position";

    private ListView listView;
    private ProgressBar progressBar;
    private boolean twoPane;
    private int position = ListView.INVALID_POSITION;

    private BroadcastReceiver peopleReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            PeopleResult result = (PeopleResult) intent.getSerializableExtra(SwService.EXTRA_RESULT);
            if (result != null) {
                if (result.getError() != null) {
                    showError(result.getError());
                    return;
                }
                if (result.getPeople() != null) {
                    showPeople(result.getPeople());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        if (findViewById(R.id.person_container) != null) {
            twoPane = true;
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(KEY_POSITION, ListView.INVALID_POSITION);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPerson(position);
            }
        });

        showProgress(true);
        SwService.getPeople(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (position != ListView.INVALID_POSITION) {
            outState.putInt(KEY_POSITION, position);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter ifPeople = new IntentFilter(SwService.ACTION_GET_PEOPLE);
        LocalBroadcastManager.getInstance(this).registerReceiver(peopleReceiver, ifPeople);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(peopleReceiver);
    }

    private void showPeople(ArrayList<Person> people) {
        showProgress(false);
        ArrayAdapter<Person> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_activated_1, people);
        listView.setAdapter(adapter);
        if (position != ListView.INVALID_POSITION) {
            showPerson(position);
            listView.setSelection(position);
            listView.setItemChecked(position, true);
        }

    }

    private void showError(String error) {
        showProgress(false);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void showProgress(boolean isShow) {
        listView.setVisibility(isShow ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    private void showPerson(int position) {
        this.position = position;
        Person person = (Person) listView.getAdapter().getItem(position);
        if (twoPane) {
            PersonFragment personFragment = PersonFragment.newInstance(person);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.person_container, personFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        else {
            Intent intent = new Intent(this, PersonActivity.class)
                    .putExtra(PersonActivity.EXTRA_PERSON, person);
            startActivity(intent);
        }
    }
}
