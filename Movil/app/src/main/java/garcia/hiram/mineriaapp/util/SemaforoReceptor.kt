package garcia.hiram.mineriaapp.util

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Consumer
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import garcia.hiram.mineriaapp.R
import garcia.hiram.mineriaapp.mensajeria.RabbitReceptor
import garcia.hiram.mineriaapp.modelo.Estado
import garcia.hiram.mineriaapp.modelo.Semaforo
import java.util.concurrent.ConcurrentHashMap

class SemaforoReceptor: MensajeAction {

    private lateinit var rabbitReceptor:  RabbitReceptor
    private var observadores :ArrayList<MensajeListener> =ArrayList()
    companion object{
        val  semaforos:MutableLiveData<ConcurrentHashMap<String,Semaforo>> =MutableLiveData()

    }
    private lateinit var channel: Channel

    // se inicia el map con los semaforos de manera concurrente por los cambios
    init {
        semaforos.value= ConcurrentHashMap()
    }

    fun init() {
        // se inicia la configuracion de la cola
        this.rabbitReceptor=RabbitReceptor("semaforo", "envio.semaforo", "semaforos")
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

                    val gson:Gson= Gson()

                    val semaforoEntrante:Semaforo =gson.fromJson<Semaforo>(mensaje,Semaforo::class.java)

                    // se verifica si el semaforo esta dentro de la lista
                    val semaforoExistente =semaforos.value?.get(semaforoEntrante.idSemaforo)


                    if (semaforoExistente != null) {
                        // si esta dentro actualiza el estado dentro del map
                        semaforoExistente.estado=semaforoEntrante.estado
                    } else {
                        // si no agrega el nuevo semaforo
                        semaforos.value?.put(semaforoEntrante.idSemaforo, semaforoEntrante)
                    }

                    // espera 60ms para el renderizado
                    Thread.sleep(60)
                    // renderiza en el mapa
                    actualizarListener()


                }
            }
        }
    }

// metodo para obtener el icono correspondiente de acuerdo al estado
     fun verificarEstado(estado:Estado): Int? {
        val icon: Int? = when (estado) {
            Estado.AMARILLO -> R.drawable.amarillo
            Estado.VERDE -> R.drawable.verde
            Estado.ROJO -> R.drawable.rojo
            else -> null
        }
        return icon

    }

    override fun agregarListener(o: MensajeListener) {
        observadores.add(o)
    }

    override fun eliminarListener(o: MensajeListener) {
        observadores.remove(o)
    }

    override fun actualizarListener() {
        observadores.forEach {
            observer->observer.actualizar("Semaforo")
        }
    }
}