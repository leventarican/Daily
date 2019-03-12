package leventarican.github.com.daily

interface DailyView {
    fun uiDistanceToLocation(txt: String)
    fun uiRadius(txt: String)
    fun uiLog(txt: String)
    fun uiTotal(txt: String)
    fun uiStartStop(status: String)
}