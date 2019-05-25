package leventarican.github.com.daily

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene


class Main : Activity(), DailyView {
    private val REQUEST_PERMISSION_ACCESS_FINE_LOCATION = 42
    private lateinit var log: TextView
    private lateinit var locationListener: LocationListener
    private var locationManager: LocationManager? = null
    private var provider: String? = null
    private var userLocation: Location? = null
    private var presenter = DailyPresenter(this, DailyInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
    }

    private fun initialize() {
        //        log = findViewById(R.id.txtLog)
        //
        //        findViewById<SeekBar>(R.id.seekBar).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        //            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
        //                presenter.changeRadius(i)
        //            }
        //            override fun onStartTrackingTouch(seekBar: SeekBar) { }
        //            override fun onStopTrackingTouch(seekBar: SeekBar) { }
        //        })
        //
        //        userLocation = Location(LocationManager.GPS_PROVIDER)
        //        presenter.changeWorkingZone(userLocation!!)
        //
        //        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_ACCESS_FINE_LOCATION)
        //        } else {
        //            initializeLocation()
        //        }
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

    private fun initializeLocation() {
        locationManager = getSystemService(LocationManager::class.java)
        locationManager ?: finish()
        locationListener = object : LocationListener {
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) { }
            override fun onProviderEnabled(provider: String?) { }
            override fun onProviderDisabled(provider: String?) { }
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
            }, true)
    }

    fun setWorkLocation(view: View) {
        presenter.changeWorkingZone(userLocation!!)
        Log.d("#code#", "set location: ${userLocation!!.latitude}, ${userLocation!!.longitude}")
    }

    fun startPomodoro(view: View) {
        val progressPomodoro = (findViewById<TextView>(R.id.progressPomodoro) as Progress)
        val componentPomodoro = findViewById<Pomodoro>(R.id.pomodoroComponent)
        val total = componentPomodoro.getMins()
        val time =  total * 60 * 1000
        var progress = 1
        Thread(Runnable {
            while (progress <= total) {
                runOnUiThread {
                    progressPomodoro.update(progress++, total)
                }
                Thread.sleep(time.toLong())
            }
        }).start()
    }

    fun logOnOff(view: View) {
        val element0 = findViewById<LinearLayout>(R.id.element0)
        val element1 = findViewById<LinearLayout>(R.id.element1)
        val layoutParams0 = element0.layoutParams as LinearLayout.LayoutParams
        val layoutParams1 = element1.layoutParams as LinearLayout.LayoutParams

        layoutParams0.weight = layoutParams1.weight.also {
            layoutParams1.weight = layoutParams0.weight
        }
        element0.layoutParams = layoutParams0
        element1.layoutParams = layoutParams1
    }

    override fun uiDistanceToLocation(txt: String) {
        findViewById<TextView>(R.id.txtDistanceToLocation).text = txt
        (findViewById<TextView>(R.id.progress) as Progress).update("[0-9]+".toRegex().findAll(txt).first().value.toInt(), findViewById<SeekBar>(R.id.seekBar).progress)
    }

    override fun uiRadius(txt: String) {
        findViewById<TextView>(R.id.txtRadius).text = txt
    }

    override fun uiLog(txt: String) {
        log.append(txt)
    }

    override fun uiTotal(txt: String) {
        findViewById<TextView>(R.id.txtTotal).text = txt
    }

    override fun uiStartStop(status: String) {
        val drawable = findViewById<ImageView>(R.id.svg0).drawable as Drawable
        ObjectAnimator.ofPropertyValuesHolder(drawable, PropertyValuesHolder.ofInt("alpha", 10), PropertyValuesHolder.ofInt("alpha", 255)).apply {
            when (status) {
                "active" -> { drawable.setTint(Color.GREEN) }
                "inactive" -> { drawable.setTint(Color.RED) }
            }
            target = drawable
            duration = 3000
            start()
        }
        findViewById<Button>(R.id.setLocation).apply {
            isEnabled = true
            isClickable = true
        }
    }
}