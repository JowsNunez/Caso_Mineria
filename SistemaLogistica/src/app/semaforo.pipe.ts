import { Pipe, PipeTransform } from '@angular/core';
import { Estado } from './modelo/semaforo';

@Pipe({
  name: 'semaforoToString'
})
export class SemaforoPipe implements PipeTransform {

  transform(value: Estado, esActivo?:boolean): string {
   
    switch (value) {
      case Estado.NONE:
        return ""
      case Estado.VERDE:
        return 'verde';
      case Estado.ROJO:
        return 'rojo'
      case Estado.AMARILLO:
        return 'amarillo'

    }
  }

}
