package com.example.machinealertsubscription.UI

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.machinealertsubscription.R
import com.example.machinealertsubscription.Service.FirebaseService
import com.example.machinealertsubscription.Service.RegistrationIntentService
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : WearableActivity() {

  val mainActivity: MainActivity = this
  var isActivityVisible: Boolean = false
  private val TAG = "MainActivity"
  private val PLAY_SERVICES_RESOLUTION_REQUEST = 9000


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Enables Always-on
    setAmbientEnabled()
    setOnclickListeners()

    registerWithNotificationHubs()
    FirebaseService().createChannelAndHandleNotifications(this)

  }

  private fun setOnclickListeners() {
    btn_alerts.setOnClickListener { view ->
      var intent = Intent(this, AlertList::class.java).apply {
        putExtra("typeOfAlert", "Alerts")
      }
      startActivity(intent)
    }

    btn_machines.setOnClickListener { view ->
      var intent = Intent(this, AlertList::class.java).apply {
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
    isActivityVisible = true
  }

  override fun onPause() {
    super.onPause()
    isActivityVisible = false
  }

  override fun onResume() {
    super.onResume()
    isActivityVisible = true
  }

  override fun onStop() {
    super.onStop()
    isActivityVisible = false
  }

  fun ToastNotify(notificationMessage: String?) {
    runOnUiThread {
      Toast.makeText(this@MainActivity, notificationMessage, Toast.LENGTH_LONG).show()
      text_hello.text = notificationMessage
    }
  }

}
