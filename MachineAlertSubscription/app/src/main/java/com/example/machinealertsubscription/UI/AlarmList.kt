package com.example.machinealertsubscription.UI

//import com.example.machinealertsubscription.DataAccess.FakeDB
import android.graphics.Color
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.example.machinealertsubscription.DataAccess.AlarmDAO
import com.example.machinealertsubscription.DataAccess.MachineDAO
import com.example.machinealertsubscription.R
import kotlinx.android.synthetic.main.activity_alert_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AlarmList : WearableActivity() {

    //private var fakeDb: FakeDB = FakeDB()
    private var alarmDao: AlarmDAO = AlarmDAO()
    private var machineDAO = MachineDAO()
    private var identifier: String = ""
    private var listOfItems: MutableList<Any> = mutableListOf()
    private var adapter: RecyclerAdapter<Any> = RecyclerAdapter(listOfItems, this)
    private var tokenFromPreferences: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_list)

        val view: View = findViewById(R.id.view_recyclerView)
        val root = view.rootView
        root.setBackgroundColor(Color.DKGRAY)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        tokenFromPreferences = sharedPreferences.getString("FCMToken","")!!
        // Enables Always-on

        var bundle: Bundle? = intent.extras
        val toIdentify = bundle!!.getString("typeOfAlert") as String
        identifier = intent.getStringExtra("typeOfAlert")
    }

    override fun onResume() {
        super.onResume()
        setRecyclerView()
        adapter.notifyDataSetChanged()
    }

    private fun setRecyclerView(){
        var recyclerView: WearableRecyclerView = view_recyclerView
        listOfItems.clear()
        if(identifier == "Alarms"){
            CoroutineScope(Main).launch {
                alarmDao.getAlarms(tokenFromPreferences).collect { value ->
                    listOfItems.add(value)
                    adapter.notifyDataSetChanged()

                }
            }
        }
        else{
            CoroutineScope(Main).launch {
                machineDAO.getMachines(tokenFromPreferences).collect { value ->
                    listOfItems.add(value)
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