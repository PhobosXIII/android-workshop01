package ru.dev2dev.sw_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dmitriy on 27.01.2016.
 */
public class PersonAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater lInflater;
    ArrayList<Person> mPersons;

    public PersonAdapter(Context context, ArrayList<Person> persons) {
        mContext = context;
        mPersons = persons;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mPersons.size();
    }

    @Override
    public Object getItem(int position) {
        return mPersons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        Person person = mPersons.get(position);

        ((TextView) view.findViewById(android.R.id.text1)).setText(person.getName());

        return view;
    }
}
