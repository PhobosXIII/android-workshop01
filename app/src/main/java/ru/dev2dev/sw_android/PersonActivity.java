package ru.dev2dev.sw_android;

import android.os.Bundle;
import android.support.annotation.Nullable;

public class PersonActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
    }
}
