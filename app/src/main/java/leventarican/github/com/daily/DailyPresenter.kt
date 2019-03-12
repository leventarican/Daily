package leventarican.github.com.daily

import android.location.Location

class DailyPresenter(private val view: DailyView, private val interactor: DailyInteractor): DailyInteractor.PIInterface {

    init {
        interactor.listener.add(this)
    }

    override fun callback(id: Int, event: String) {
        when (id) {
            0 -> { view.uiLog(event) }
            1 -> { view.uiDistanceToLocation(event) }
            2 -> { view.uiRadius(event) }
            3 -> { view.uiTotal(event) }
            4 -> { view.uiStartStop(event) }
        }
    }

    fun changeRadius(radius: Int) {
        interactor.updateRadius(radius)
    }

    fun changeLocation(location: Location) {
        interactor.updateLocation(location)
    }

    fun changeWorkingZone(location: Location) {
        interactor.updateWorkingZone(location)
    }
}