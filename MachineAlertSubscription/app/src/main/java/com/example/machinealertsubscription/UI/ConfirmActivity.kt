package com.example.machinealertsubscription.UI

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
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
        if(codeFromBundle != "" && descriptionFromBundle != ""){
            id.text = "ID: " + intent.getStringExtra("id")
            description.text = "Description: " + descriptionFromBundle
            code.text = "Code: " + codeFromBundle
            txt_subscription_explanation.text = DEFAULT_EXPLANATION + "alarm"
        }
        else{
            id.text = "ID: " + intent.getStringExtra("id")
            txt_subscription_explanation.text = DEFAULT_EXPLANATION + "machine"
            code.text = ""
            description.text = ""
        }

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btn_yes.setOnClickListener {
            // Do something when they want to subscribe to alarm
        }
        btn_no.setOnClickListener {
            super.finish()
        }
    }
}