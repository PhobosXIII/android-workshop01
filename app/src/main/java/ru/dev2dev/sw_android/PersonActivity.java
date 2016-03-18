package ru.dev2dev.sw_android;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class PersonActivity extends BaseActivity {
    public static final String EXTRA_PERSON = "ru.dev2dev.sw_android.PERSON";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        PersonFragment personFragment =
                (PersonFragment) getSupportFragmentManager().findFragmentById(R.id.person_container);
        if (personFragment == null) {
            Person person = (Person) getIntent().getSerializableExtra(EXTRA_PERSON);
            personFragment = PersonFragment.newInstance(person);
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, personFragment)
                    .commit();
        }
    }
}
