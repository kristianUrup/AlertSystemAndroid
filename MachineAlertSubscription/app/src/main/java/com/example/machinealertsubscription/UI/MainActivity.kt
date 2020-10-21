package com.example.machinealertsubscription.UI

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import com.example.machinealertsubscription.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : WearableActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Enables Always-on
    setAmbientEnabled()
    setOnclickListeners()

  }

  private fun setOnclickListeners(){
    btn_alerts.setOnClickListener{ view ->
      var intent = Intent(this, AlertList::class.java).apply {
        putExtra("typeOfAlert", "Alerts")
      }
      startActivity(intent)
    }

    btn_machines.setOnClickListener {view ->
      var intent = Intent(this, AlertList::class.java).apply {
        putExtra("typeOfAlert", "Machines")
      }
      startActivity(intent)
    }
  }




}
