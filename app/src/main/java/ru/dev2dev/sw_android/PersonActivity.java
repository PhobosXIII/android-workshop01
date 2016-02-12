package ru.dev2dev.sw_android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonActivity extends BaseActivity {
    public static final String EXTRA_PERSON = "person";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        ImageView ivPortrait = (ImageView) findViewById(R.id.iv_portrait);
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        TextView tvGender = (TextView) findViewById(R.id.tv_gender);
        TextView tvBirth = (TextView) findViewById(R.id.tv_birth);
        TextView tvInfo = (TextView) findViewById(R.id.tv_info);

        Person person = (Person) getIntent().getSerializableExtra(EXTRA_PERSON);
        if (person != null) {
            tvName.setText(person.getName());
            tvGender.setText(person.getGender());
            tvBirth.setText(person.getBirthYear());

            String info = String.format(getResources().getString(R.string.info),
                    person.getHeight(), person.getMass(), person.getEyeColor());
            tvInfo.setText(info);

            try {
                int color = Color.parseColor(person.getEyeColor());
                ivPortrait.setBackgroundColor(color);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
