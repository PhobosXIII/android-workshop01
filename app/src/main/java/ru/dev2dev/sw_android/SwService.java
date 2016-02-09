package ru.dev2dev.sw_android;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SwService extends IntentService {
    private static final String TAG = SwService.class.getSimpleName();

    public static final String ACTION_GET_PEOPLE = "get_people";
    public static final String EXTRA_SUCCESS = "success";
    public static final String EXTRA_ERROR = "error";

    public static void getPeople(Context context) {
        Intent intent = new Intent(context, SwService.class);
        context.startService(intent);
    }

    public SwService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Start retrieving data");

        String jsonPeople = Prefs.getPeople(this);
        if (jsonPeople == null) {
            String url = "http://swapi.co/api/people/";
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("User-Agent", "ws-sw-android-" + Build.VERSION.RELEASE)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    jsonPeople = response.body().string();
                    Prefs.savePeople(this, jsonPeople);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Intent errorIntent = new Intent().putExtra(EXTRA_ERROR, "Problem with network.");
                send(errorIntent);
                return;
            }
        }

        try {
            ArrayList<Person> people = Person.getList(jsonPeople);
            Intent successIntent = new Intent().putExtra(EXTRA_SUCCESS, people);
            send(successIntent);
        } catch (JSONException e) {
            e.printStackTrace();
            Intent errorIntent = new Intent().putExtra(EXTRA_ERROR, "Problem with JSON.");
            send(errorIntent);
        }
    }

    private void send(Intent intent) {
        intent.setAction(ACTION_GET_PEOPLE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        Log.d(TAG, "Sending broadcast intent");
    }
}
