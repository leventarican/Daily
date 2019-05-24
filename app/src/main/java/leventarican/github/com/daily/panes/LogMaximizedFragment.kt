package leventarican.github.com.daily.panes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.log_maximized_fragmentv1.*
import leventarican.github.com.daily.R

class LogMaximizedFragment : Fragment() {
    private lateinit var motionLayout: ScrollView

    companion object {
        fun newInstance() = LogMaximizedFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.log_maximized_fragmentv1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        motionLayout = view.findViewById(R.id.sv_log_maximized_fragment)
        super.onViewCreated(view, savedInstanceState)

        txtLog.text = """
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this
            [LOG] scroll this

        """.trimIndent()
    }
}