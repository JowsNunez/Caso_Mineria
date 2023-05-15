package garcia.hiram.mineriaapp.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class HttpConsumer {


    fun post(context:Context, json: JSONObject,queue:RequestQueue,url:String){

        // Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, url, json,
            { response ->
                // El cuerpo de la respuesta
                Log.i("TAG", response.toString())
                Toast.makeText(
                    context,
                    "Congestión enviada correctamente: ${json.getString("type")}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            { error ->
                // Manejo de errores
                Toast.makeText(
                    context,
                    "Ocurrio algún error al enviar congestion: ${json.getString("type")}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )


        // Agregar peticion ala cola de peticiones.
        queue.add(stringRequest)

    }

}