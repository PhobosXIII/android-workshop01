package ru.dev2dev.sw_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {
    private val peopleReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.hasExtra(SwService.EXTRA_PEOPLE)) {
                val people = intent.getSerializableExtra(SwService.EXTRA_PEOPLE) as ArrayList<Person>
                showPeople(people)
            }
            if (intent.hasExtra(SwService.EXTRA_ERROR)) {
                val error = intent.getStringExtra(SwService.EXTRA_ERROR)
                showError(error)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showProgress(true)
        SwService.getPeople(this)

        listView.setOnItemClickListener({ parent, view, position, id ->
            val person = listView.adapter.getItem(position) as Person
            val intent = Intent(this, PersonActivity::class.java).putExtra(PersonActivity.EXTRA_PERSON, person)
            startActivity(intent)
        })
    }

    override fun onStart() {
        super.onStart()
        val ifPeople = IntentFilter(SwService.ACTION_GET_PEOPLE)
        LocalBroadcastManager.getInstance(this).registerReceiver(peopleReceiver, ifPeople)
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(peopleReceiver)
    }

    private fun showPeople(people: ArrayList<Person>) {
        showProgress(false)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, people)
        listView.adapter = adapter
    }

    private fun showError(error: String) {
        showProgress(false)
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private fun showProgress(isShow: Boolean) {
        listView.visibility = if (isShow) View.GONE else View.VISIBLE
        progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}
