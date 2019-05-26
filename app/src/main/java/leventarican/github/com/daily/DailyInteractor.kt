package leventarican.github.com.daily

import android.location.Location
import java.text.SimpleDateFormat
import java.util.*

class DailyInteractor {

    val workTime = WorkTime()
    val user = User()
    val listener = mutableListOf<PIInterface>()

    fun updateRadius(radius: Int) {
        user.radius = radius.toFloat()
        listener.forEach {
            it.callback(0, "\nRadius : ${user.radius}")
            it.callback(2, "Radius: ${user.radius} [m]")
        }
    }

    fun updateLocation(location: android.location.Location) {
        user.userLocation = location
        val distanceInMeter = user.userLocation!!.distanceTo(user.workLocation)
        listener.forEach {
            val antarctic = Pair<Double, Double>(-78.599864, 25.030605)
//            it.callback(0, "\nlat: ${this.user.userLocation.latitude} lon: ${this.user.userLocation.longitude}")
            it.callback(0, "\nlat: ${antarctic.first} lon: ${antarctic.second}")
            it.callback(0, "\ndistance: $distanceInMeter")
            it.callback(1, "Distance to User: $distanceInMeter [m]")
        }
        if (distanceInMeter > user.radius) {
            if (user.isWorking) {
                user.isWorking = false
                user.listOfWorkTime.last().end = Calendar.getInstance()
                var total: Long = 0L
                user.listOfWorkTime.forEach {
                    total += it.end.timeInMillis - it.start.timeInMillis
                }
                listener.forEach {
                    it.callback(3, "Total: ${total / 1000} [seconds]")
                }
            }
            listener.forEach {
                it.callback(4, "inactive")
            }
        } else {
            if (!user.isWorking) {
                user.isWorking = true
                user.listOfWorkTime.add(WorkTime().apply {
                    start = Calendar.getInstance()
                })
            }
            listener.forEach {
                it.callback(4, "active")
            }
        }
    }

    fun updateWorkingZone(location: android.location.Location) {
        this.user.workLocation = location
        this.user.userLocation = location
    }

    interface PIInterface {
        fun callback(id: Int, event: String)
    }

    data class User(
        var radius: Float = 1F, var isWorking: Boolean = false,
        val listOfWorkTime: MutableList<WorkTime> = mutableListOf(),
        var userLocation: Location? = null, var workLocation: Location? = null
    )

//    class User() {
//        var radius = 1F
//        var isWorking = false
//        var listOfWorkTime = mutableListOf<WorkTime>()
//        lateinit var userLocation: android.location.Location
//        lateinit var workLocation: android.location.Location
//    }

    class WorkTime() {
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