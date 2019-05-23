package leventarican.github.com.daily.panes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import leventarican.github.com.daily.R

class LogMinimizedFragment : Fragment() {
    companion object {
        fun newInstance() = LogMinimizedFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.log_minimized_fragment, container, false)
    }
}