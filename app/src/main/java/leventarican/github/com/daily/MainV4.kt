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

class MainV4 : AppCompatActivity(), TransitionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_v4)
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        Log.d("#code#", "transition...")
    }
}
