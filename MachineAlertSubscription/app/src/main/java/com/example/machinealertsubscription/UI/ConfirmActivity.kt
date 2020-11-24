package com.example.machinealertsubscription.UI

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.example.machinealertsubscription.DataAccess.AlarmDAO
import com.example.machinealertsubscription.DataAccess.MachineDAO
import com.example.machinealertsubscription.R
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : WearableActivity() {

    private var alarmDao: AlarmDAO = AlarmDAO()
    private var machineDAO = MachineDAO()
    private var identifier: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        identifier = intent.getStringExtra("typeOfAlert")

        // Enables Always-on
        setAmbientEnabled()
        setOnclickListeners()

        val descriptionFromBundle: String = intent.getStringExtra("description")
        val codeFromBundle: String = intent.getStringExtra("code")
        if(codeFromBundle != null && descriptionFromBundle != null){
            id.text = intent.getStringExtra("id")
            description.text = descriptionFromBundle
            code.text = codeFromBundle
        }
        else{
            id.text = intent.getStringExtra("id")
        }

    }

    private fun setOnclickListeners() {
        btn_cancel.setOnClickListener {view ->
            finish()
            }
        btn_ok.setOnClickListener {
            if(identifier == "Alarms") {
                alarmDao.subscribeToAlarm(4,"123123")
            } else {
                machineDAO.subscribeToMachine("30","400")

        }
        }
    }

}