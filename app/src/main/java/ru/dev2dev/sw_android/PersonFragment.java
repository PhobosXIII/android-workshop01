package ru.dev2dev.sw_android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonFragment extends Fragment {
    private static final String TAG = PersonFragment.class.getSimpleName();
    private static final String KEY_PERSON = "person";

    private Person person;
    private ImageView portraitView;
    private TextView nameView;
    private TextView genderView;
    private TextView birthView;
    private TextView infoView;

    public static PersonFragment newInstance(Person person) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PERSON, person);
        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        person = (Person) getArguments().getSerializable(KEY_PERSON);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        portraitView = (ImageView) view.findViewById(R.id.portrait);
        nameView = (TextView) view.findViewById(R.id.name);
        genderView = (TextView) view.findViewById(R.id.gender);
        birthView = (TextView) view.findViewById(R.id.birth_year);
        infoView = (TextView) view.findViewById(R.id.info);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (person != null) {
            nameView.setText(person.getName());
            genderView.setText(person.getGender());
            birthView.setText(person.getBirthYear());

            String info = getString(R.string.info_format,
                    person.getHeight(), person.getMass(), person.getEyeColor());
            infoView.setText(info);

            try {
                int color = Color.parseColor(person.getEyeColor());
                portraitView.setBackgroundColor(color);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "onActivityCreated: ", e);
            }
        }
    }
}
