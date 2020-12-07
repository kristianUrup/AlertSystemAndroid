package com.example.machinealertsubscription.UI

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.view.Gravity
import com.example.machinealertsubscription.DataAccess.AlarmDAO
import com.example.machinealertsubscription.DataAccess.MachineDAO
import com.example.machinealertsubscription.R
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_confirm.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfirmActivity : WearableActivity() {

    private var alarmDao: AlarmDAO = AlarmDAO()
    private var machineDAO = MachineDAO()
    private var isSubscribed = false
    private var identifier: String = ""
    private var tokenFromPreferences: String = ""
    private var listOfItems: MutableList<Any> = mutableListOf()
    private var adapter: RecyclerAdapter<Any> = RecyclerAdapter(listOfItems, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        identifier = intent.getStringExtra("typeOfAlert")
        isSubscribed = intent.getBooleanExtra("isSubscribed", false)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        tokenFromPreferences = sharedPreferences.getString("FCMToken","")!!

        // Enables Always-on
        setAmbientEnabled()
        setOnclickListeners()
        val descriptionFromBundle: String = intent.getStringExtra("description")
        val codeFromBundle: String = intent.getStringExtra("code")
        if(isSubscribed) {
            txt_subscription_explanation.text = resources.getString(R.string.unsubscribeString)
        } else {
            txt_subscription_explanation.text = resources.getString(R.string.subscribeString)
        }
        if(codeFromBundle != "" && descriptionFromBundle != ""){
            code.text = codeFromBundle
            code.setTextSize(3,10f)
            code.gravity = Gravity.CENTER
            description.text = descriptionFromBundle
            description.gravity = Gravity.CENTER

        }
        else{
            code.text = intent.getStringExtra("id")
            code.setTextSize(3,7f)
            code.gravity = Gravity.CENTER

        }
    }

    private fun setOnclickListeners() {
        btn_cancel.setOnClickListener { view ->
            finish()
        }
        if (isSubscribed) {
            btn_ok.setOnClickListener {
                if (identifier == "Alarms") {
                    CoroutineScope(Dispatchers.Main).launch {
                        alarmDao.DeleteAlarmSubscription(
                            intent.getStringExtra("id").toInt(),
                            tokenFromPreferences
                        )
                        finish()
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        machineDAO.DeleteMachineSubscription(
                            intent.getStringExtra("id"),
                            tokenFromPreferences
                        )
                        finish()
                    }
                }
            }
        } else {
            btn_ok.setOnClickListener {
                if (identifier == "Alarms") {
                    CoroutineScope(Dispatchers.Main).launch {
                        alarmDao.subscribeToAlarm(
                            intent.getStringExtra("id").toInt(),
                            tokenFromPreferences
                        )
                        finish()
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        machineDAO.subscribeToMachine(
                            intent.getStringExtra("id"),
                            tokenFromPreferences
                        )
                        finish()
                    }
                }
            }
        }
    }

}