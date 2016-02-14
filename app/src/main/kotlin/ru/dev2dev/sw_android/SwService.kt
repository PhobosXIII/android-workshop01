package ru.dev2dev.sw_android

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.content.LocalBroadcastManager
import android.text.TextUtils
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import java.io.IOException

class SwService : IntentService(SwService.TAG) {

    override fun onHandleIntent(intent: Intent) {
        Log.d(TAG, "Start retrieving data")

        var jsonPeople: String = Prefs.getPeople(this)
        try {
            if (TextUtils.isEmpty(jsonPeople)) {
                val url = "http://swapi.co/api/people/"
                val client = OkHttpClient()
                val request = Request.Builder()
                        .url(url)
                        .addHeader("User-Agent", "ws-sw-android-" + Build.VERSION.RELEASE)
                        .build()

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    jsonPeople = response.body().string()
                    Prefs.savePeople(this, jsonPeople)
                }
            }

            val people = Person.getList(jsonPeople)
            val successIntent = Intent().putExtra(EXTRA_PEOPLE, people)
            send(successIntent)
        } catch (e: IOException) {
            Log.e(TAG, "onHandleIntent ", e)
            val errorIntent = Intent().putExtra(EXTRA_ERROR, "IO exception!")
            send(errorIntent)
        } catch (e: JSONException) {
            Log.e(TAG, "onHandleIntent ", e)
            val errorIntent = Intent().putExtra(EXTRA_ERROR, "JSON exception!")
            send(errorIntent)
        }
    }

    private fun send(intent: Intent) {
        intent.action = ACTION_GET_PEOPLE;
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        Log.d(TAG, "Sending broadcast intent")
    }

    companion object {
        private val TAG = SwService::class.java.simpleName

        val ACTION_GET_PEOPLE = "ru.dev2dev.sw_android.GET_PEOPLE"
        val EXTRA_PEOPLE = "ru.dev2dev.sw_android.PEOPLE"
        val EXTRA_ERROR = "ru.dev2dev.sw_android.ERROR"

        fun getPeople(context: Context) {
            val intent = Intent(context, SwService::class.java)
            context.startService(intent)
        }
    }
}
