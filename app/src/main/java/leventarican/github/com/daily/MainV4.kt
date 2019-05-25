package leventarican.github.com.daily

import android.Manifest
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_v4.*
import kotlinx.android.synthetic.main.progress_fragment.*

class MainV4 : AppCompatActivity(), DailyView {

    private val REQUEST_PERMISSION_ACCESS_FINE_LOCATION = 42
    private lateinit var locationListener: LocationListener
    private var userLocation: Location? = null
    private var provider: String? = null
    private var presenter = DailyPresenter(this, DailyInteractor())
    private var locationManager: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_v4)

        initialize()
    }

    override fun onResume() {
        super.onResume()
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager?.requestLocationUpdates(provider, 50L, 0F, locationListener);
        }
    }

    override fun onPause() {
        super.onPause()
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager?.removeUpdates(locationListener)
        }
    }

    private fun initialize() {
        findViewById<SeekBar>(R.id.seekBar).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                presenter.changeRadius(i)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        userLocation = Location(LocationManager.GPS_PROVIDER)
        presenter.changeWorkingZone(userLocation!!)

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSION_ACCESS_FINE_LOCATION
            )
        } else {
            initializeLocation()
        }
    }

    private fun initializeLocation() {
        locationManager = getSystemService(LocationManager::class.java)
        locationManager ?: finish()
        locationListener = object : LocationListener {
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String?) {}
            override fun onProviderDisabled(provider: String?) {}
            override fun onLocationChanged(location: Location?) {
                userLocation = location
                presenter.changeLocation(location!!)
                Log.d("#code#", "location changed")
            }
        }
        provider = locationManager?.getBestProvider(
            Criteria().apply {
                this.accuracy = Criteria.ACCURACY_COARSE
                this.powerRequirement = Criteria.POWER_LOW
            }, true
        )
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
        // TODO impl
    }
}

