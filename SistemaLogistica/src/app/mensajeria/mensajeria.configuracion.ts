import { RxStompService } from "./stomp.service"
import { Observable } from "rxjs"

/**
 * @interface Mensaje
 */
export interface Mensaje {
    /**
     * @param data<Opcional> representa los datos de un mensaje de tipo string
     */
    data?: string
}


/**
 * @abstract @class BaseRabbitMensajeria Clase abstracta que utiliza la configuracion para rabbit con la libreria @angular/RxStomp.
 *     
 */
export abstract class BaseRabbitMensajeria {


    /**
     * @constructor crea una instancia de BaseRabbitMensajeria
     * @param serviceStomp representa el servicio de rxStomp previamente configurado
     */
    constructor(protected serviceStomp: RxStompService) { }
}

/**
 * @abstract @class AbstractEnviadorMensajeria, clase hija de BaseRabbitMensajeria con el metodo abstracto enviar
 */
export abstract class AbstractEnviadorMensajeria extends BaseRabbitMensajeria {
    /**
     * @method enviar se encarga de enviar un mensaje a la cola de mensajes del Broker configurado
     * @param cola representa el nombre de la cola o exchange 
     * @param mensaje representa el contenido a enviar 
     */
    public abstract enviar( mensaje: Mensaje,cola?: string): void
}

/**
 * @abstract @class AbstractReceptorMensajeria, clase hija de BaseRabbitMensajeria con el metodo abstracto recibir
 */
export abstract class AbstractReceptorMensajeria extends BaseRabbitMensajeria {
    /**
     * @method recibir se encarga de recibir un mensaje a la cola de mensajes del Broker configurado
     * @param cola representa el nombre de la cola o exchange 
     */
    public abstract recibir(cola?: string): Observable<Mensaje>
}


/**
 * @interface MensajeriaFactory Se encarga de contruir instancias de las clases abstractas AbstractEnviadorMensajeria, AbstractReceptorMensajeria
 */
export interface MensajeriaFactory {


    /**
     * @method crearEnviadorMensajeria Método que se encarga de crear una instancia de {@class AbstractEnviadorMensajeria}
     */
    crearEnviadorMensajeria(): AbstractEnviadorMensajeria


    /**
     * @method crearReceptorMensajeria Método que se encarga de crear una instancia de {@class AbstractReceptorMensajeria}
     */
    crearReceptorMensajeria(): AbstractReceptorMensajeria

}



/**
 * @interface DespachadorMensajeria interface que representa el tipo de Despachador de Mensaje
 */
export interface DespachadorMensajeria {
    /**
    * @method enviar Método que hace la llamada al método enviar dentro de la clase Concreta del tipo AbstractEnviadorMensajeria
    * @param cola representa el nombre de la cola, exchange a enviar
    * @param mensaje representa un mensaje a recibir
    * 
    * 
    */

    enviar(cola: string, mensaje: Mensaje): void


    /**
     * @method recibir Método que hace la llamada al método enviar dentro de la clase Concreta del tipo AbstractEnviadorMensajeria
     * @param cola representa el nombre de la cola, exchange a recibir
     * @param mensaje representa un mensaje a enviar
     * 
     * 
     * 
     */
    recibir(cola?: string): Observable<Mensaje>

}