package com.example.machinealertsubscription.UI

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.example.machinealertsubscription.BE.Alert
import com.example.machinealertsubscription.BE.Machine
import com.example.machinealertsubscription.DataAccess.AlertDAO
import com.example.machinealertsubscription.DataAccess.FakeDB
import com.example.machinealertsubscription.DataAccess.MachineDAO
import com.example.machinealertsubscription.R
import kotlinx.android.synthetic.main.activity_alert_list.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import java.io.InputStream
import kotlin.coroutines.CoroutineContext

class AlertList : WearableActivity() {

    private var fakeDb: FakeDB = FakeDB()
    private var alertDao: AlertDAO = AlertDAO()
    private var machineDAO = MachineDAO()
    private var identifier: String = ""
    private var listOfItems: MutableList<Any> = mutableListOf()
    private var adapter: RecyclerAdapter<Any> = RecyclerAdapter(listOfItems)



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
        var recyclerView: WearableRecyclerView = view_recyclerView
        println(listOfItems)


        if(identifier == "Alerts"){
            CoroutineScope(Main).launch {
                alertDao.getAlerts().collect { value ->
                    listOfItems.add(value)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        else{
            CoroutineScope(Main).launch {
                machineDAO.getMachines().collect { value ->
                    listOfItems.add(value)
                    adapter.notifyDataSetChanged()
                }


            }
        }

        println("Done!")

        recyclerView.apply {
            adapter = this@AlertList.adapter
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = true
            bezelFraction = 0.5f
            scrollDegreesPerScreen = 90f
            layoutManager = WearableLinearLayoutManager(this@AlertList)
        }
        //recyclerView.setHasFixedSize(true)
    }

}