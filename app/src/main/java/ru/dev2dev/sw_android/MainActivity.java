package ru.dev2dev.sw_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

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
                Person person = (Person) listView.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), PersonActivity.class)
                        .putExtra(PersonActivity.EXTRA_PERSON, person);
                startActivity(intent);
            }
        });

        showPeople(null);
    }

    private void showPeople(ArrayList<Person> people) {
        showProgress(false);
        if (people != null) {
            ArrayAdapter<Person> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, people);
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
}
