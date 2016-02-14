package ru.dev2dev.sw_android

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import java.io.Serializable
import java.util.*

data class Person(val name: String,
                  val height: String,
                  val mass: String,
                  val eyeColor: String,
                  val birthYear: String,
                  val gender: String) : Serializable {

    override fun toString(): String {
        return name
    }

    companion object {
        private val serialVersionUID = 13L

        @Throws(JSONException::class)
        fun getList(json: String): ArrayList<Person> {
            val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            val parser = JsonParser();
            val results = parser.parse(json).getAsJsonObject().getAsJsonArray("results");
            val collectionType = object : TypeToken<ArrayList<Person>>() {}.type;
            return gson.fromJson(results, collectionType);
        }
    }
}
