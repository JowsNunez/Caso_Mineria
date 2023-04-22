import { Injectable ,Inject} from '@angular/core';
import { DespachadorMensajeria, Mensaje } from './configuracion.mensaje';
import {Observable} from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class MensajeriaService {
 
  constructor(@Inject("Rabbit") private mensajeDispatcher:DespachadorMensajeria) {
      
   }

  public enviar(cola:string,mensaje:Mensaje){
    this.mensajeDispatcher.enviar(cola,mensaje);
  }
  public recibir(cola:string):Observable<Mensaje>{
    return this.mensajeDispatcher.recibir(cola);
  }

}
