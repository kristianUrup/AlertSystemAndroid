package com.example.machinealertsubscription.UI

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import androidx.preference.PreferenceManager
import com.example.machinealertsubscription.BE.Alarm
import com.example.machinealertsubscription.BE.AlarmWatch
import com.example.machinealertsubscription.BE.Machine
import com.example.machinealertsubscription.BE.MachineWatch
import com.example.machinealertsubscription.R
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : WearableActivity() {

    private val SUBSCRIBED_EXPLANATION: String = "Are you sure want to unsubscribe this "
    private val DEFAULT_EXPLANATION: String = "Are you sure you want to subscribe to this "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        // Enables Always-on
        setAmbientEnabled()

        var bundle: Bundle? = intent.extras

        val descriptionFromBundle: String = intent.getStringExtra("description")
        val codeFromBundle: String = intent.getStringExtra("code")
        val idFromBundle: String = intent.getStringExtra("id")
        if(codeFromBundle != "" && descriptionFromBundle != ""){
            id.text = "ID: " + idFromBundle
            description.text = "Description: " + descriptionFromBundle
            code.text = "Code: " + codeFromBundle
            txt_subscription_explanation.text = DEFAULT_EXPLANATION + "alarm"
            val alarm = Alarm(idFromBundle.toInt(),codeFromBundle,descriptionFromBundle)
            setOnClickListeners(alarm)
        }
        else{
            id.text = "ID: " + intent.getStringExtra("id")
            txt_subscription_explanation.text = DEFAULT_EXPLANATION + "machine"
            code.text = ""
            description.text = ""
            val machine = Machine(idFromBundle)
            setOnClickListeners(machine)
        }
    }

    private fun setOnClickListeners(anyEntity: Any) {
        btn_yes.setOnClickListener {
            if(anyEntity is Alarm){
                val alarmWatch = AlarmWatch(PreferenceManager.getDefaultSharedPreferences(this).getString("FCMToken","")!!, anyEntity)
            }
            else if(anyEntity is Machine){
                val machineWatch = MachineWatch(PreferenceManager.getDefaultSharedPreferences(this).getString("FCMToken","")!!, anyEntity)
            }
            super.finish()
            // Do something when they want to subscribe to alarm
        }
        btn_no.setOnClickListener {
            super.finish()
        }
    }
}