package ru.dev2dev.sw_android;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonFragment extends Fragment {
    private Person person;

    private ImageView ivPortrait;
    private TextView tvName;
    private TextView tvGender;
    private TextView tvBirth;
    private TextView tvInfo;

    public PersonFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        person = (Person) getArguments().getSerializable(PersonActivity.EXTRA_PERSON);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivPortrait = (ImageView) view.findViewById(R.id.iv_portrait);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvGender = (TextView) view.findViewById(R.id.tv_gender);
        tvBirth = (TextView) view.findViewById(R.id.tv_birth);
        tvInfo = (TextView) view.findViewById(R.id.tv_info);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (person != null) {
            tvName.setText(person.getName());
            tvGender.setText(person.getGender());
            tvBirth.setText(person.getBirthYear());

            String info = String.format(getResources().getString(R.string.info),
                    person.getHeight(), person.getMass(), person.getEyeColor());
            tvInfo.setText(info);

            int color = Color.TRANSPARENT;
            switch (person.getEyeColor()) {
                case "blue":
                    color = Color.BLUE;
                    break;

                case "red":
                    color = Color.RED;
                    break;

                case "yellow":
                    color = Color.YELLOW;
                    break;

                default:
                    break;
            }
            ivPortrait.setBackgroundColor(color);
        }
    }
}
