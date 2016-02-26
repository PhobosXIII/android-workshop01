package ru.dev2dev.sw_android;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SwService extends IntentService {
    private static final String TAG = SwService.class.getSimpleName();

    public static final String ACTION_GET_PEOPLE = "ru.dev2dev.sw_android.GET_PEOPLE";
    public static final String EXTRA_RESULT = "ru.dev2dev.sw_android.RESULT";

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

        PeopleResult result = new PeopleResult();
        String jsonPeople = Prefs.getPeople(this);
        try {
            if (jsonPeople == null) {
                String url = "http://swapi.co/api/people/";
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("User-Agent", "ws-sw-android-" + Build.VERSION.RELEASE)
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    jsonPeople = response.body().string();
                    Prefs.savePeople(this, jsonPeople);
                }
            }

            ArrayList<Person> people = Person.getList(jsonPeople);
            result.setPeople(people);
        } catch (IOException e) {
            Log.e(TAG, "onHandleIntent: ", e);
            result.setError("Problem with network.");
        } catch (JsonSyntaxException e) {
            Log.e(TAG, "onHandleIntent: ", e);
            result.setError("Problem with JSON.");
        } finally {
            send(result);
        }
    }

    private void send(PeopleResult result) {
        Intent intent = new Intent()
                .setAction(ACTION_GET_PEOPLE)
                .putExtra(EXTRA_RESULT, result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        Log.d(TAG, "Sending broadcast intent");
    }
}
