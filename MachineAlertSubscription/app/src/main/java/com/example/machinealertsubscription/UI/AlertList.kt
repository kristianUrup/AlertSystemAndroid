package com.example.machinealertsubscription.UI

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinealertsubscription.BE.Machine
import com.example.machinealertsubscription.DataAccess.FakeDB
import com.example.machinealertsubscription.R
import kotlinx.android.synthetic.main.activity_alert_list.*

class AlertList : WearableActivity() {

    private var fakeDb: FakeDB = FakeDB()
    private var identifier: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_list)

        // Enables Always-on


        var bundle: Bundle ?= intent.extras
        val toIdentify = bundle!!.getString("typeOfAlert")
        identifier = intent.getStringExtra("typeOfAlert")
        Toast.makeText(this,identifier,Toast.LENGTH_SHORT).show()

        val recyclerView = view_recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapter<Machine>(fakeDb.getMachines());
    }

}