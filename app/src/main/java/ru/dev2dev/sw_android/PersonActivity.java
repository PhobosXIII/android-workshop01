package ru.dev2dev.sw_android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonActivity extends BaseActivity {
    public static final String EXTRA_PERSON = "ru.dev2dev.sw_android.PERSON";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        ImageView portraitView = (ImageView) findViewById(R.id.portrait);
        TextView nameView = (TextView) findViewById(R.id.name);
        TextView genderView = (TextView) findViewById(R.id.gender);
        TextView birthView = (TextView) findViewById(R.id.birth_year);
        TextView infoView = (TextView) findViewById(R.id.info);

        Person person = (Person) getIntent().getSerializableExtra(EXTRA_PERSON);
        if (person != null) {
            nameView.setText(person.getName());
            genderView.setText(person.getGender());
            birthView.setText(person.getBirthYear());

            String info = String.format(getResources().getString(R.string.info_format),
                    person.getHeight(), person.getMass(), person.getEyeColor());
            infoView.setText(info);

            try {
                int color = Color.parseColor(person.getEyeColor());
                portraitView.setBackgroundColor(color);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "onCreate: ", e);
            }
        }
    }
}
