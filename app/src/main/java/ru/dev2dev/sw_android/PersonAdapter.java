package ru.dev2dev.sw_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonAdapter extends BaseAdapter {

    LayoutInflater lInflater;
    ArrayList<Person> people;

    public PersonAdapter(Context context, ArrayList<Person> people) {
        this.people = people;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return people.size();
    }

    @Override
    public Object getItem(int position) {
        return people.get(position);
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

        Person person = people.get(position);
        ((TextView) view.findViewById(android.R.id.text1)).setText(person.getName());

        return view;
    }
}
