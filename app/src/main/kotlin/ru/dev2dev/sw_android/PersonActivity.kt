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

        tvName.text = person.name
        tvGender.text = person.gender
        tvBirth.text = person.birthYear

        val info = getString(R.string.info_format, person.height, person.mass, person.eyeColor)
        tvInfo.text = info

        try {
            val color = Color.parseColor(person.eyeColor)
            ivPortrait.setBackgroundColor(color)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "onCreate: ", e)
        }
    }

    companion object {
        val EXTRA_PERSON = "ru.dev2dev.sw_android.PERSON"
    }
}
