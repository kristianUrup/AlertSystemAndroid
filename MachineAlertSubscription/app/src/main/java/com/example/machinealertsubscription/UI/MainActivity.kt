package com.example.machinealertsubscription.UI

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.machinealertsubscription.R
import com.example.machinealertsubscription.Service.FirebaseService
import com.example.machinealertsubscription.Service.RegistrationIntentService
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : WearableActivity() {

  var mainActivity: MainActivity = this
  var isAcitivityVisible = false
  private val TAG = "MainActivity"
  private val PLAY_SERVICES_RESOLUTION_REQUEST = 9000


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    mainActivity = this
    isAcitivityVisible = true

    // Enables Always-on
    setAmbientEnabled()
    setOnclickListeners()

    registerWithNotificationHubs()
    FirebaseService().createChannelAndHandleNotifications(applicationContext)
  }

  private fun setOnclickListeners() {
    btn_alerts.setOnClickListener { view ->
      var intent = Intent(this, AlarmList::class.java).apply {
        putExtra("typeOfAlert", "Alarms")
      }
      startActivity(intent)
    }

    btn_machines.setOnClickListener { view ->
      var intent = Intent(this, AlarmList::class.java).apply {
      putExtra("typeOfAlert", "Machines")
    }
      startActivity(intent)
    }
  }

  private fun checkPlayServices(): Boolean {
    val apiAvailability = GoogleApiAvailability.getInstance()
    val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
    if (resultCode != ConnectionResult.SUCCESS) {
      if (apiAvailability.isUserResolvableError(resultCode)) {
        apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
          .show()
      } else {
        Log.i(TAG, "This device is not supported by Google Play Services.")
        ToastNotify("This device is not supported by Google Play Services.")
        finish()
      }
      return false
    }
    return true
  }

  fun registerWithNotificationHubs() {
    if (checkPlayServices()) {
      // Start IntentService to register this application with FCM.
      val intent = Intent(this, RegistrationIntentService::class.java)
      startService(intent)

    }
  }

  override fun onStart() {
    super.onStart()
    isAcitivityVisible = true
  }

  override fun onPause() {
    super.onPause()
    isAcitivityVisible = false
  }

  override fun onResume() {
    super.onResume()
    isAcitivityVisible = true
  }

  override fun onStop() {
    super.onStop()
    isAcitivityVisible = false
  }

  fun ToastNotify(notificationMessage: String?) {
    runOnUiThread {
      Toast.makeText(this@MainActivity, notificationMessage, Toast.LENGTH_LONG).show()
    }
  }

}
