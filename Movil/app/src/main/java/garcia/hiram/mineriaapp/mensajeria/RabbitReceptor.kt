package garcia.hiram.mineriaapp.mensajeria

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Consumer
import garcia.hiram.mineriaapp.modelo.Semaforo


/**
 * @param queue representa la cola de la cual llegan los mensajes
 * @param exchange representa el exchange del cual llegan los mensajes
 * @param key representa la llave que confirma si se reciben mensajes de dicho exchange
 */
class RabbitReceptor(var queue:String,var exchange:String, var key:String?) :BaseReceptor<Consumer> {

    lateinit var channel: Channel
    // se instancia un objeto BaseConexion para establecer conexion con rabbit
    private var baseConexion: BaseConexion<Channel> = RabbitConexion("192.168.84.11","root","sinoesque1",5672)

    init {
        this.baseConexion.conectar()

    }

    // se obtiene el canal de comunicacion y se declaran las colas de las cuales se recibiran los mensajes
    override fun configurar(){
        this.channel=baseConexion.getChannel()
        this.channel.queueDeclare(queue, true, false, false, null)
        this.channel.queueBind(queue, exchange, key)

    }

    // metodo para recibir mensajes de la cola
    override fun recibir(consumer: Consumer) {

        try{
            // se crea un consumidor basico con ack automático es decir con auto confirmación
            this.channel.basicConsume(queue, true, consumer)
        }catch (e:Exception ){
            Log.e("Error","${e.message}")
        }

    }

}