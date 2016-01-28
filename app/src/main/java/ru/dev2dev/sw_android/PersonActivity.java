package ru.dev2dev.sw_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class PersonActivity extends BaseActivity {
    public static final String PERSON_EXTRA = "person";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        TextView tvName = (TextView) findViewById(R.id.tv_name);
        TextView tvGender = (TextView) findViewById(R.id.tv_gender);
        TextView tvBirth = (TextView) findViewById(R.id.tv_birth);
        TextView tvInfo = (TextView) findViewById(R.id.tv_info);

        Intent intent = getIntent();
        if (intent.hasExtra(PERSON_EXTRA)) {
            Person person = (Person) intent.getSerializableExtra(PERSON_EXTRA);
            tvName.setText(person.getName());
            tvGender.setText(person.getGender());
            tvBirth.setText(person.getBirthYear());
            String info = String.format(getResources().getString(R.string.info),
                    person.getHeight(), person.getMass(), person.getEyeColor());
            tvInfo.setText(info);
        }
    }
}
