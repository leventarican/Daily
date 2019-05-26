package leventarican.github.com.daily

import android.location.Location

class DailyPresenter(private val view: DailyView, private val interactor: DailyInteractor) : DailyInteractor.PIInterface {

    init {
        interactor.listener.add(this)
    }

    enum class UI {
        LOG, DISTANCE, RADIUS, TOTAL, START_STOP
    }

    override fun callback(id: UI, event: String) {
        when (id) {
            UI.LOG -> { view.uiLog(event) }
            UI.DISTANCE -> { view.uiDistanceToLocation(event) }
            UI.RADIUS -> { view.uiRadius(event) }
            UI.TOTAL -> { view.uiTotal(event) }
            UI.START_STOP -> { view.uiStartStop(event) }
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