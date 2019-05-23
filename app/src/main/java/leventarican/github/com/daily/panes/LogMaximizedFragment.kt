package leventarican.github.com.daily.panes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import leventarican.github.com.daily.R

class LogMaximizedFragment : Fragment() {
    private lateinit var motionLayout: MotionLayout

    companion object {
        fun newInstance() = LogMaximizedFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.log_maximized_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        motionLayout = view.findViewById(R.id.log_maximized_fragment)
        super.onViewCreated(view, savedInstanceState)
    }
}