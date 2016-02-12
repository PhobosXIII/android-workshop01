package ru.dev2dev.sw_android;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class PersonActivity extends BaseActivity {
    public static final String EXTRA_PERSON = "person";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            PersonFragment personFragment = new PersonFragment();
            personFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, personFragment)
                    .commit();
        }
    }
}
