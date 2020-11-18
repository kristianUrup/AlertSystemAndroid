package com.example.machinealertsubscription.UI

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.example.machinealertsubscription.R
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        // Enables Always-on
        setAmbientEnabled()

        var bundle: Bundle? = intent.extras

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
}