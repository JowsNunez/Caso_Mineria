import {
    AbstractEnviadorMensajeria,
    AbstractReceptorMensajeria,
    DespachadorMensajeria,
    Mensaje,
    MensajeriaFactory
} from "./configuracion.mensaje";
import { Injectable } from '@angular/core';
import { RxStompService } from "./stomp.service";
import { Observable, Subject } from "rxjs"


/**
 * @class @RabbitMensajeriarabbitFactory Clase concreta que implementa los métodos de las interface MensajeriarabbitFactory
 */
@Injectable({
    providedIn: 'root'
})
export class RabbitMensajeriarabbitFactory implements MensajeriaFactory {
    /**
     * 
     * @param stompService dependencia que representa al servicio de mensajeria de rabbit de la libreria {@stomop/rx-stomp}
     */
    constructor(private stompService: RxStompService) { }

    /**
     * Se encarga de construir una instancia de AbstractEnviadorMensajeria
     * @returns devuelve una instancia concreta de AbstractEnviadorMensajeria
     */
    crearEnviadorMensajeria(): AbstractEnviadorMensajeria {
        return new RabbitEnviadorMensaje(this.stompService)

    }


    /**
     * Se encarga de construir una instancia de AbstractReceptorMensajeria
     * @returns devuelve una instancia concreta de AbstractReceptorMensajeria
     */
    crearReceptorMensajeria(): AbstractReceptorMensajeria {
        return new RabbitReceptorMensaje(this.stompService)
    }

}

/**
 * @class @RabbitEnviadorMensaje clase concreta  e hija de AbstractEnviadorMensajeria que contiene el metodo a enviar
 * con el fin de enviar a diferentes colas 
 */
export class RabbitEnviadorMensaje extends AbstractEnviadorMensajeria {

    /**
     * @method enviar Método Método que implementa la lógica para enviar un mensaje a broker de Rabbit, extrae el campo data de el parametro mensaje
     * @param cola representa el nombre de la __queue, exchange__ 
     * @param mensaje  representa el contenido a enviar
     */
    public override enviar(cola: string, mensaje: Mensaje): void {

        this.serviceStomp.publish({ destination: cola, body: mensaje.data })
    }

}

/**
 * @class @RabbitReceptorMensaje clase concreta  e hija de AbstractReceptorMensajeria que contiene el metodo a enviar
 * con el fin de enviar a diferentes colas.
 */
export class RabbitReceptorMensaje extends AbstractReceptorMensajeria {

    /**
     * @method recibir Método que implementa la lógica para recibir mensaje a broker de Rabbit, extrae el campo data de el parametro mensaje
     * @param cola representa la cola de la cual se espera un mensaje
     * @returns devuelve un Observable de la libreria RxJS de tipo Mensaje
     */
    public override  recibir(cola: string): Observable<Mensaje> {
        const subject = new Subject<Mensaje>();

        this.serviceStomp.watch(cola).subscribe(msg => {
            const mensaje: Mensaje = { data: msg.body };
            subject.next(mensaje);
        });

        return subject.asObservable();
    }

}


/**
 * @class @RabbitDespachadorMensaje clase que implementa la interface DespachadorMensajeria 
 */

@Injectable({
    providedIn: 'root'
})
export class RabbitDespachadorMensaje implements DespachadorMensajeria {
    enviador: AbstractEnviadorMensajeria
    receptor: AbstractReceptorMensajeria
    /**
     * @constructor constructor que inicializa las instancias para sus atribituos {@link enviadror: AbstractEnviadorMensajeria} 
     * y  {@link enviadror: AbstractEnviadorMensajeria} 
     * @param rabbitFactory Representa el factory para instancias de Envio y Recepcion de Mensajes
     */
    constructor(private rabbitFactory: RabbitMensajeriarabbitFactory) {

        this.enviador = this.rabbitFactory.crearEnviadorMensajeria()
        this.receptor = this.rabbitFactory.crearReceptorMensajeria()

    }

    /**
     * @method enviar Método que hace la llamada al método enviar dentro de la clase Concreta del tipo AbstractEnviadorMensajeria
     * @param cola representa el nombre de la cola, exchange a enviar
     * @param mensaje representa un mensaje a enviar
     * 
     * 
     * 
     *  @example  Ejemplo para enviar a solo un exchange de acuerdo a una llave "/exchange/${nombre de Exchange}/{key}"
     *  @example Ejemplo para enviar a una queue /queue/{nombre_queue}
     */
    public enviar(cola: string, mensaje: Mensaje) {
        this.enviador.enviar(cola, mensaje);
    }


    /**
     * @method enviar Método que hace la llamada al método enviar dentro de la clase Concreta del tipo AbstractEnviadorMensajeria
     * @param cola representa el nombre de la cola, exchange a enviar
     * @param mensaje representa un mensaje a recibir
     * 
     * 
     * 
     *  @example  Ejemplo para enviar a solo un exchange de acuerdo a una llave "/exchange/${nombre de Exchange}/{key}"
     *  @example Ejemplo para enviar a una queue /queue/{nombre_queue}
     */
    public recibir(cola: string): Observable<Mensaje> {
        return this.receptor.recibir(cola);
    }

}