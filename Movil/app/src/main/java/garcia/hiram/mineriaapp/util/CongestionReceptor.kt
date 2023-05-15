package garcia.hiram.mineriaapp.util

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.rabbitmq.client.*
import garcia.hiram.mineriaapp.R
import garcia.hiram.mineriaapp.mensajeria.RabbitReceptor
import garcia.hiram.mineriaapp.modelo.Congestion
import garcia.hiram.mineriaapp.modelo.Estado
import garcia.hiram.mineriaapp.modelo.Mensaje
import garcia.hiram.mineriaapp.modelo.Semaforo
import java.util.concurrent.ConcurrentHashMap

class CongestionReceptor:MensajeAction {
    private lateinit var rabbitReceptor: RabbitReceptor
    private var observadores :ArrayList<MensajeListener> =ArrayList()
    companion object{
        val  congestiones: MutableLiveData<List<Congestion>> = MutableLiveData()

    }
    private lateinit var channel: Channel



    fun init() {
        // se inicia la configuracion de la cola
        this.rabbitReceptor= RabbitReceptor("congestion", "envio.congestion", "congestiones")
        this.rabbitReceptor.configurar()
        this.channel= this.rabbitReceptor.channel


    }
    // se establece el consumidor para iniciar la recepcion de mensajes
    fun recibir(){
        this.rabbitReceptor.recibir(getConsumidor())
    }

    // se crea el consumidor de mensajes
    private fun getConsumidor(): Consumer {
        return object : DefaultConsumer(channel) {
            override fun handleDelivery(
                consumerTag: String?,
                envelope: Envelope?,
                properties: AMQP.BasicProperties?,
                body: ByteArray?
            ) {
                body?.let {


                    val mensaje:String = String(it, charset("UTF-8"))
                    // se convierte el mensaje a Objeto usando GSON

                    val gson: Gson = Gson()
                    Log.d("mens",mensaje)

                    val mensajeEntrante: Mensaje =gson.fromJson<Mensaje>(mensaje, Mensaje::class.java)

                    Log.d("mens",mensajeEntrante.toString())

                   CongestionReceptor.congestiones.postValue(mensajeEntrante.contenido  )

                    Thread.sleep(20)


                    actualizarListener()


                }
            }
        }
    }



    override fun agregarListener(o: MensajeListener) {
        observadores.add(o)
    }

    override fun eliminarListener(o: MensajeListener) {
        observadores.remove(o)
    }

    override fun actualizarListener() {
        observadores.forEach {
                observer->observer.actualizar("Congestion")
        }
    }
}