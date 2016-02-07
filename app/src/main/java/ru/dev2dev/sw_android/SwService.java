package ru.dev2dev.sw_android;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SwService extends IntentService {
    private static final String TAG = SwService.class.getSimpleName();

    public static final String ACTION_GET_PEOPLE = "get_people";
    public static final String EXTRA_PEOPLE = "people";

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

            try {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("User-Agent", "ws-sw-android-" + Build.VERSION.RELEASE)
                        .build();

                Response response = client.newCall(request).execute();
                jsonPeople = response.body().string();
                Prefs.savePeople(this, jsonPeople);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ArrayList<Person> people = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonPeople);
            JSONArray results = jsonObject.getJSONArray("results");
            people = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                Person person = Person.fromJson(results.getJSONObject(i));
                people.add(person);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent peopleIntent = new Intent().setAction(ACTION_GET_PEOPLE).putExtra(EXTRA_PEOPLE, people);
        LocalBroadcastManager.getInstance(this).sendBroadcast(peopleIntent);

        Log.d(TAG, "Sending broadcast intent");
    }
}
