package ru.dev2dev.sw_android;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Dmitriy on 01.02.2016.
 */
public class GetPeopleService extends IntentService {

    public GetPeopleService() {
        super("name");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        PeopleResponse response = null;
        try {
            response = API.getApi().getPeople();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response!=null && response.getPersons()!=null) {
            sendResult(response.getPersons());
        } else {
            sendResult(null);
        }
    }

    private void sendResult(ArrayList<Person> persons) {
        Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
        intent.putExtra(MainActivity.PEOPLE_EXTRA, persons);
        sendBroadcast(intent);
    }

}
