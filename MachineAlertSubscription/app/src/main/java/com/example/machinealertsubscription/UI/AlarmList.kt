package com.example.machinealertsubscription.UI

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import androidx.preference.PreferenceManager
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.example.machinealertsubscription.BE.MachineWatch
import com.example.machinealertsubscription.DataAccess.AlarmDAO
import com.example.machinealertsubscription.DataAccess.FakeDB
import com.example.machinealertsubscription.DataAccess.MachineDAO
import com.example.machinealertsubscription.DataAccess.MachineWatchDAO
import com.example.machinealertsubscription.R
import kotlinx.android.synthetic.main.activity_alert_list.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect

class AlarmList : WearableActivity() {

    private var fakeDb: FakeDB = FakeDB()
    private var alarmDao: AlarmDAO = AlarmDAO()
    private var machineDAO = MachineDAO()
    private var identifier: String = ""
    private var listOfItems: MutableList<Any> = mutableListOf()
    private var listOfSubscriptions: MutableList<Any> = mutableListOf()
    private var adapter: RecyclerAdapter<Any,Any> = RecyclerAdapter(listOfItems, listOfSubscriptions, this)
    private lateinit var recyclerView: WearableRecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_list)


        // Enables Always-on

        var bundle: Bundle? = intent.extras
        val toIdentify = bundle!!.getString("typeOfAlert") as String
        identifier = intent.getStringExtra("typeOfAlert")

        setRecyclerView()
    }

    private fun setRecyclerView(){
        recyclerView = view_recyclerView
        println(listOfItems)


        if(identifier == "Alarms"){
            CoroutineScope(Main).launch {
                alarmDao.getAlarms().collect { value ->
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
                MachineWatchDAO().getMachineWatches(PreferenceManager.getDefaultSharedPreferences(this@AlarmList).getString("FCMToken","")!!).collect {
                    listOfSubscriptions.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        recyclerView.apply {
            adapter = this@AlarmList.adapter
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = true
            bezelFraction = 0.5f
            scrollDegreesPerScreen = 90f
            layoutManager = WearableLinearLayoutManager(this@AlarmList)
        }
        //recyclerView.setHasFixedSize(true)
    }

}