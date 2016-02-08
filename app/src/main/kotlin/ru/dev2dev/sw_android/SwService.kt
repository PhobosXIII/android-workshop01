package ru.dev2dev.sw_android

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class SwService : IntentService(SwService.TAG) {

    override fun onHandleIntent(intent: Intent) {
        Log.d(TAG, "Start retrieving data")

        var jsonPeople: String? = Prefs.getPeople(this)
        if (jsonPeople == null) {
            val url = "http://swapi.co/api/people/"
            val client = OkHttpClient()

            val request = Request.Builder().url(url).addHeader("User-Agent", "ws-sw-android-" + Build.VERSION.RELEASE).build()
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    jsonPeople = response.body().string()
                    Prefs.savePeople(this, jsonPeople)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        val people = Person.getList(jsonPeople)
        val peopleIntent = Intent().setAction(ACTION_GET_PEOPLE).putExtra(EXTRA_PEOPLE, people)
        LocalBroadcastManager.getInstance(this).sendBroadcast(peopleIntent)

        Log.d(TAG, "Sending broadcast intent")
    }

    companion object {
        private val TAG = SwService::class.java.simpleName

        val ACTION_GET_PEOPLE = "get_people"
        val EXTRA_PEOPLE = "people"

        fun getPeople(context: Context) {
            val intent = Intent(context, SwService::class.java)
            context.startService(intent)
        }
    }
}
