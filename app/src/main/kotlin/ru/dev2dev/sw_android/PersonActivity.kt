package ru.dev2dev.sw_android

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_person.*

class PersonActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)

        val person = intent.getSerializableExtra(EXTRA_PERSON) as Person

        tvName.text = person.name
        tvGender.text = person.gender
        tvBirth.text = person.birthYear

        val info = getString(R.string.info, person.height, person.mass, person.eyeColor)
        tvInfo.text = info

        when(person.eyeColor) {
            "black" -> supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
            "yellow" -> supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.YELLOW))
            "red" -> supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.RED))
            "brown" -> supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.rgb(79, 55, 48)))
            "blue" -> supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.BLUE))
        }
    }

    companion object {
        val EXTRA_PERSON = "person"
    }
}
