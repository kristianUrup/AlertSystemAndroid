package com.example.machinealertsubscription.UI

//import com.example.machinealertsubscription.DataAccess.FakeDB
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.system.ErrnoException
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
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlin.Exception


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
        identifier = intent.getStringExtra("typeOfAlert")
    }


    override fun onResume() {
        super.onResume()
        setRecyclerView()
        hideError()
        adapter.notifyDataSetChanged()
    }

    fun showError() {
        progressBar.visibility = View.INVISIBLE
        txtError.visibility = View.VISIBLE
        txtErrorTitle.visibility = View.VISIBLE
    }
    fun hideError() {

        txtError.visibility = View.INVISIBLE
        txtErrorTitle.visibility = View.INVISIBLE
    }

    private fun  setRecyclerView(){
        var recyclerView: WearableRecyclerView = view_recyclerView
        listOfItems.clear()
        progressBar.visibility = View.VISIBLE

            if(identifier == "Alarms") {
                    CoroutineScope(Main).launch {
                        supervisorScope {
                            val task = async {
                                alarmDao.getAlarms(tokenFromPreferences).collect { value ->
                                    listOfItems.add(value)
                                    adapter.notifyDataSetChanged()
                                    progressBar.visibility = View.INVISIBLE
                                }
                            }
                            try {
                                task.await()
                            } catch (ex: Exception) {
                                when (ex) {
                                    is ErrnoException, is KotlinNullPointerException, is ConnectException, is SocketTimeoutException -> {
                                        showError()
                                        println(ex)
                                        Log.d("TAG", ex.toString())
                                    }
                                }
                                println(ex)
                            }
                    }
                }
            }
            else{
                CoroutineScope(Main).launch {
                    machineDAO.getMachines(tokenFromPreferences).collect { value ->
                        listOfItems.add(value)
                        adapter.notifyDataSetChanged()
                        progressBar.visibility = View.INVISIBLE
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
    }
}