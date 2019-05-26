package leventarican.github.com.daily

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_v4.*
import kotlinx.android.synthetic.main.progress_fragment.*

class MainV4 : AppCompatActivity(), DailyView, LocationChangedListener {

    private val REQUEST_PERMISSION_ACCESS_FINE_LOCATION = 42
    private var userLocation: Location? = null
    private var provider: String? = null
    private var presenter = DailyPresenter(this, DailyInteractor())
    private var locationManager: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_v4)

        initialize()
    }


    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        if (GPSPermission()) {
            locationManager?.requestLocationUpdates(provider, 50L, 0F, this)
        }
    }

    override fun onPause() {
        super.onPause()
        if (GPSPermission()) {
            locationManager?.removeUpdates(this)
        }
    }

    private fun initialize() {
        getSeekBar()?.onProgressChanged { progress, _ ->
            presenter.changeRadius(progress)
        }
        userLocation = Location(LocationManager.GPS_PROVIDER)
        presenter.changeWorkingZone(userLocation!!)
        if (GPSPermission()) {
            initializeLocation()
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_ACCESS_FINE_LOCATION)
        }
    }

    private fun initializeLocation() {
        with(getSystemService(LocationManager::class.java)) {
            this ?: finish()
            provider = getBestProvider(Criteria().apply {
                this.accuracy = Criteria.ACCURACY_COARSE
                this.powerRequirement = Criteria.POWER_LOW
            }, true)
        }
    }

    override fun onLocationChanged(location: Location?) {
        userLocation = location
        location?.let { presenter.changeLocation(it) }
    }

    // onClick
    fun setWorkLocation(view: View) {
        presenter.changeWorkingZone(userLocation!!)
        Log.d("#code#", "set location: ${userLocation!!.latitude}, ${userLocation!!.longitude}")
    }

    // DailyView
    override fun uiDistanceToLocation(txt: String) {
        txtDistanceToLocation.text = txt
        val progressView = progressFragment.view?.findViewById<Progress>(R.id.progress)
        val progress = "[0-9]+".toRegex().findAll(txt).first().value.toInt()
        val total = progressFragment.view?.findViewById<SeekBar>(R.id.seekBar)?.progress
        total?.let { progressView?.update(progress, it) }
    }

    override fun uiRadius(txt: String) {
        Log.d("#code#", "ui radius TODO: $txt")
    }

    override fun uiLog(txt: String) = txtLog.append(txt)

    override fun uiTotal(txt: String) {
        Log.d("#code#", "ui total TODO: $txt")
    }

    override fun uiStartStop(status: String) {
        progressFragment.view?.findViewById<Button>(R.id.setLocation)?.apply {
            isEnabled = true
            isClickable = true
        }
    }

    private fun GPSPermission(): Boolean = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    private fun getSeekBar() = progressFragment.view?.findViewById<SeekBar>(R.id.seekBar)
}

interface LocationChangedListener : LocationListener {
    override fun onLocationChanged(location: Location?)
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) = Unit
    override fun onProviderEnabled(provider: String?) = Unit
    override fun onProviderDisabled(provider: String?) = Unit
}

// make usage of extension function and higher order function for readable code.
fun SeekBar.onProgressChanged(callback: (Int, Boolean) -> Unit) {

    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onStopTrackingTouch(seekBar: SeekBar) = Unit
        override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) = callback(progress, fromUser)
    })
}