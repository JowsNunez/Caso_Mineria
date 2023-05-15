package garcia.hiram.mineriaapp.ui.alerta

import com.android.volley.Request
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import garcia.hiram.mineriaapp.databinding.FragmentAlertaBinding
import garcia.hiram.mineriaapp.modelo.Congestion
import garcia.hiram.mineriaapp.modelo.Type
import garcia.hiram.mineriaapp.modelo.Ubicacion
import garcia.hiram.mineriaapp.util.HttpConsumer
import org.json.JSONObject
import java.util.Random

class AlertaFragment : Fragment() {

    private var _binding: FragmentAlertaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var radioTypeGroup: RadioGroup
    private lateinit var decriptionText: EditText
    private lateinit var btnEnviar: Button
    private lateinit var type: Type


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        type = Type.NONE
        _binding = FragmentAlertaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        radioTypeGroup = binding.radioTypeCongestion
        decriptionText = binding.editTextDescription
        btnEnviar = binding.btnEnviarCongestion


        val queue = Volley.newRequestQueue(root.context)

        seleccionarTipoCongestion()
        enviarCongest(queue)




        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun seleccionarTipoCongestion() {
        radioTypeGroup.setOnCheckedChangeListener { group, checkedId ->
            if (radioTypeGroup.checkedRadioButtonId == binding.checkboxAccident.id) {
                type = Type.ACCIDENTE
            }
            if (radioTypeGroup.checkedRadioButtonId == binding.checkboxTraffic.id) {
                type = Type.SEMAFORO
            }
            if (radioTypeGroup.checkedRadioButtonId == binding.checkboxMecanic.id) {
                type = Type.CAMION
            }

            Log.d("TYPE", type.name)
        }


    }

    fun enviarCongest(queue: RequestQueue) {
        btnEnviar.setOnClickListener {

            if (type == Type.NONE) {
                Toast.makeText(
                    binding.root.context,
                    "Se debe seleccionar un tipo de congestión...",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val url = "http://192.168.84.11:5000/api/v1/congestion"
                val ubicacionRandom = ubicacionRandom()


                val congestion = Congestion(
                    null,
                    type = type,
                    location = ubicacionRandom.latitude.toString() + "," + ubicacionRandom.longitude.toString(),
                    description = decriptionText.text.toString()
                );

                val json = JSONObject()
                json.put("type", congestion.type)
                json.put("location", congestion.location)
                json.put("description", congestion.description)

                HttpConsumer().post(binding.root.context, json, queue, url)
                decriptionText.setText("");
                radioTypeGroup.clearCheck()



            }

        }
    }

    fun ubicacionRandom(): Ubicacion {
        val ubicaciones = arrayOf(
            Ubicacion(30.967417761824162, -110.32407988458164),
            Ubicacion(30.967349137180715, -110.3250891825724),
            Ubicacion(30.96686495079471, -110.32177228256744),
            Ubicacion(30.970316258462823, -110.32410961335448),
            Ubicacion(30.96883481182287, -110.32206358872483),
            Ubicacion(30.964604765224706, -110.32707634907494),
            Ubicacion(30.964175902118562, -110.32099510920348),
            Ubicacion(30.967090183934634, -110.32247279365824),
            Ubicacion(30.96730460918399, -110.32375724245351),
            Ubicacion(30.966788038447795, -110.32063137149154)
        )
        val random = Random()
        val index = random.nextInt(ubicaciones.size)
        return ubicaciones[index]
    }

}