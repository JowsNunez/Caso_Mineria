package garcia.hiram.mineriaapp.ui.mapa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import garcia.hiram.mineriaapp.R
import garcia.hiram.mineriaapp.databinding.FragmentMapaBinding
import garcia.hiram.mineriaapp.util.MensajeListener
import garcia.hiram.mineriaapp.util.SemaforoReceptor
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MapaFragment : Fragment() , OnMapReadyCallback, CoroutineScope, MensajeListener {

    private var _binding: FragmentMapaBinding? = null
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
    private val handler = CoroutineExceptionHandler() { _, throwable ->
        Log.e("Exception", ":$throwable")
    }


    private lateinit var googleMap:  GoogleMap;
    private lateinit var semaforoReceptor: SemaforoReceptor

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentMapaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)


        semaforoReceptor=SemaforoReceptor()
        semaforoReceptor.agregarListener(this)
        job = SupervisorJob()
        // se inicia en un hilo fuera de la vista la conexion con rabbitmq
        launch(handler) {
            withContext(Dispatchers.Default) {
                semaforoReceptor.init()
                semaforoReceptor.recibir()
            }

        }




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onMapReady(map: GoogleMap) {
        this.googleMap=map


        googleMap.setMaxZoomPreference(18f)
        googleMap.setMinZoomPreference(15f)


        val adelaideBounds =  LatLng( 30.967011756830306,  -110.32392624978195)

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(adelaideBounds, 16.toFloat())
        googleMap.animateCamera(cameraUpdate)



    }

    override fun actualizar() {
        // al llegar un mensaje se ejecuta esta accion desde la implementacion de  MensajeAction
        requireActivity().runOnUiThread {
            // se eliminan los marcadores
            googleMap.clear()
            // se recorre el map con los semaforos
            semaforoReceptor.semaforos.value?.forEach{semaforo->
                val currentSemaforo=semaforo.value
                val icon = semaforoReceptor.verificarEstado(currentSemaforo.estado)
                // se agrega el marcador con la ubicacion y el icono correspondiente
                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(currentSemaforo.ubicacion.latitude,currentSemaforo.ubicacion.longitude))
                        .icon(BitmapDescriptorFactory.fromResource(icon!!))
                        .title(currentSemaforo.idSemaforo)
                )
            }
            // mensaje de consola
            Log.d(this.javaClass.name.toString(),semaforoReceptor.semaforos.value.toString())
        }
    }
}