package ru.dev2dev.sw_android

import org.json.JSONException
import org.json.JSONObject
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
        fun fromJson(json: JSONObject): Person {
            val name = json.getString("name")
            val height = json.getString("height")
            val mass = json.getString("mass")
            val eyeColor = json.getString("eye_color")
            val birthYear = json.getString("birth_year")
            val gender = json.getString("gender")

            return Person(name, height, mass, eyeColor, birthYear, gender)
        }

        @Throws(JSONException::class)
        fun getList(json: String): ArrayList<Person> {
            val jsonObject = JSONObject(json)
            val results = jsonObject.getJSONArray("results")
            var people = ArrayList<Person>(results.length())
            for (i in 0..results.length() - 1) {
                val person = Person.fromJson(results.getJSONObject(i))
                people.add(person)
            }

            return people
        }
    }
}
