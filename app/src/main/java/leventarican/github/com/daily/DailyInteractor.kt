package leventarican.github.com.daily

import android.location.Location
import java.text.SimpleDateFormat
import java.util.*
import leventarican.github.com.daily.DailyPresenter.UI as UI

class DailyInteractor {

    private val user = User()
    val listener = mutableListOf<PIInterface>()

    fun updateRadius(radius: Int) {
        user.radius = radius.toFloat()
        listener.forEach {
            it.callback(UI.LOG, "\nRadius : ${user.radius}")
            it.callback(UI.RADIUS, "Radius: ${user.radius} [m]")
        }
    }

    fun updateLocation(location: Location) {
        user.userLocation = location
        val distanceInMeter = user.userLocation!!.distanceTo(user.workLocation)
        listener.forEach {
            val antarctic = Pair(-78.599864, 25.030605)
//            it.callback(0, "\nlat: ${this.user.userLocation.latitude} lon: ${this.user.userLocation.longitude}")
            it.callback(UI.LOG, "\nlat: ${antarctic.first} lon: ${antarctic.second}")
            it.callback(UI.LOG, "\ndistance: $distanceInMeter")
            it.callback(UI.DISTANCE, "Distance to User: $distanceInMeter [m]")
        }
        if (distanceInMeter > user.radius) {
            if (user.isWorking) {
                user.stopWorking()
                val total = user.total()
                listener.forEach {
                    it.callback(UI.TOTAL, "Total: ${total / 1000} [seconds]")
                }
            }
            listener.forEach {
                it.callback(UI.START_STOP, "inactive")
            }
        } else {
            if (!user.isWorking) {
                user.startWorking()
            }
            listener.forEach {
                it.callback(UI.START_STOP, "active")
            }
        }
    }

    fun updateWorkingZone(location: Location) {
        this.user.workLocation = location
        this.user.userLocation = location
    }

    interface PIInterface {
        fun callback(id: UI, event: String)
    }

    fun User.startWorking() {
        user.isWorking = true
        user.listOfWorkTime.add(WorkTime().apply {
            start = Calendar.getInstance()
        })
    }

    fun User.stopWorking() {
        user.isWorking = false
        user.listOfWorkTime.last().end = Calendar.getInstance()
    }

    fun User.total(): Long{
        var total = 0L
        user.listOfWorkTime.forEach {
            total += it.end.timeInMillis - it.start.timeInMillis
        }
        return total
    }

    data class User(
        var radius: Float = 1F, var isWorking: Boolean = false,
        val listOfWorkTime: MutableList<WorkTime> = mutableListOf<WorkTime>(),
        var userLocation: Location? = null, var workLocation: Location? = null
    )

    class WorkTime {
        lateinit var start: Calendar
        lateinit var end: Calendar

        private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
            val formatter = SimpleDateFormat(format, locale)
            return formatter.format(this)
        }

        override fun toString(): String {
            return """

            start: ${start.time.toString("dd.MM.YYYY HH:mm:ss")}
            end: ${end.time.toString("dd.MM.YYYY HH:mm:ss")}
            worked: ${(end.timeInMillis - start.timeInMillis) / 1000} [seconds]
        """.trimIndent()
        }
    }

}