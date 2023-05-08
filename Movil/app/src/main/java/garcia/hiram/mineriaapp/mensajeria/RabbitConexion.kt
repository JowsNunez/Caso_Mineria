package garcia.hiram.mineriaapp.mensajeria

import android.util.Log
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

/**
 * @param host representa la direccio ip del servidor
 * @param user representa al usuario del broker
 * @param password representa la contrasena del usuario del broker
 * @param port representa el puerto donde se establecera la conexion para la llegada de mensajes
 *
 */
class RabbitConexion(var host: String, var user: String, var password: String, var port: Int) : BaseConexion<Channel> {

    private lateinit var  factory: ConnectionFactory
    private lateinit var channel: Channel
    private lateinit var connection: Connection

    // se crea la conexion con rabbit
    override fun conectar() {
        try {

            this.factory = ConnectionFactory()
            this.factory.host = this.host
            this.factory.password = this.password
            this.factory.username = this.user
            this.factory.port = this.port
            this.connection = this.factory.newConnection()
            this.channel= this.connection.createChannel()
            Log.d("CONECTADO","Conectado a Rabbit correctamente")
        }catch ( exception:Exception){
            Log.d("Error","Error ${exception.message}")

        }

    }
    // obtiene el canal para el envio y recepcion de mensajes
    override fun getChannel(): Channel {
        return this.channel
    }


}