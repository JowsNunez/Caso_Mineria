package garcia.hiram.mineriaapp.ui.reporte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import garcia.hiram.mineriaapp.databinding.FragmentReporteBinding

class ReporteFragment : Fragment() {

    private var _binding: FragmentReporteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val reporteViewModel =
            ViewModelProvider(this).get(ReporteViewModel::class.java)

        _binding = FragmentReporteBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}