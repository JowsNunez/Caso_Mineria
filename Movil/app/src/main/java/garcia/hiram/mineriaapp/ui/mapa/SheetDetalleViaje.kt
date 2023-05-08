package garcia.hiram.mineriaapp.ui.mapa


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import garcia.hiram.mineriaapp.databinding.FragmentDetalleViajeBinding
import garcia.hiram.mineriaapp.R

class SheetDetalleViaje: BottomSheetDialogFragment() {

    private var _binding: FragmentDetalleViajeBinding?=null
    private  val  binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =FragmentDetalleViajeBinding.inflate(inflater,container,false)
        val root: View = binding.root

        



        return root
    }


}


