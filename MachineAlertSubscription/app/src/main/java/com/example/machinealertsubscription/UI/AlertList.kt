package com.example.machinealertsubscription.UI

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.Toast
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
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


        var bundle: Bundle? = intent.extras
        val toIdentify = bundle!!.getString("typeOfAlert")
        identifier = intent.getStringExtra("typeOfAlert")

        setRecyclerView()
    }

    private fun setRecyclerView(){
        val recyclerView: WearableRecyclerView = view_recyclerView
        var listOfItems: List<Any>
        if(identifier == "Machines"){
            listOfItems = fakeDb.getMachines()
        }
        else{
            listOfItems = fakeDb.getAlerts()
        }

        recyclerView.apply {
            adapter = RecyclerAdapter(listOfItems)
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = true
            bezelFraction = 0.5f
            scrollDegreesPerScreen = 90f
            layoutManager = WearableLinearLayoutManager(this@AlertList)

        }
        //recyclerView.setHasFixedSize(true)
    }

}