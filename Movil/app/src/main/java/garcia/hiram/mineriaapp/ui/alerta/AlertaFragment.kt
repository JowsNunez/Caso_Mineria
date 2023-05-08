package garcia.hiram.mineriaapp.ui.alerta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import garcia.hiram.mineriaapp.databinding.FragmentAlertaBinding

class AlertaFragment : Fragment() {

    private var _binding: FragmentAlertaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val alertaViewModel =
            ViewModelProvider(this).get(AlertaViewModel::class.java)

        _binding = FragmentAlertaBinding.inflate(inflater, container, false)
        val root: View = binding.root




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}