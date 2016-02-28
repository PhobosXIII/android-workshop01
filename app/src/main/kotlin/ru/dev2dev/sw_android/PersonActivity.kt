package ru.dev2dev.sw_android

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_person.*

class PersonActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)

        val person = intent.getSerializableExtra(EXTRA_PERSON) as Person
        val (name, height, mass, eyeColor, birthYear, gender) = person

        tvName.text = name
        tvGender.text = gender
        tvBirth.text = birthYear

        val info = getString(R.string.info_format, height, mass, eyeColor)
        tvInfo.text = info

        try {
            val color = Color.parseColor(eyeColor)
            ivPortrait.setBackgroundColor(color)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "onCreate: ", e)
        }
    }

    companion object {
        val EXTRA_PERSON = "ru.dev2dev.sw_android.PERSON"
    }
}
