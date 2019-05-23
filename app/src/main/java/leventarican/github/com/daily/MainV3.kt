package leventarican.github.com.daily

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.main_v3.*
import leventarican.github.com.daily.panes.LogMaximizedFragment
import leventarican.github.com.daily.panes.LogMinimizedFragment

class MainV3 : AppCompatActivity(), TransitionListener {
    private var lastProgress = 0f
    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_v3)

        if (savedInstanceState == null) {   // lifecycle starts
            fragment = LogMinimizedFragment.newInstance().also {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, it)
                    .commitNow()
            }
        }
        motionLayout.setTransitionListener(this)
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        Log.d("#code#", "transition change")

        if (p3 - lastProgress > 0) {
            // from start to end
            val atEnd = Math.abs(p3 - 1f) < 0.1f
            if (atEnd && fragment is LogMinimizedFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.animator.show, 0)
                fragment = LogMaximizedFragment.newInstance().also {
                    transaction
                        .setCustomAnimations(R.animator.show, 0)
                        .replace(R.id.container, it)
                        .commitNow()
                }
            }
        } else {
            // from end to start
            val atStart = p3 < 0.9f
            if (atStart && fragment is LogMaximizedFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(0, R.animator.hide)
                fragment = LogMinimizedFragment.newInstance().also {
                    transaction
                        .replace(R.id.container, it)
                        .commitNow()
                }
            }
        }
        lastProgress = p3
    }
}

interface TransitionListener : MotionLayout.TransitionListener {
    override fun allowsTransition(p0: MotionScene.Transition?): Boolean = true
    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}
}