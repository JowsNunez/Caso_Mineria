import { Component, OnDestroy, Input, OnInit, Output } from '@angular/core';
import { SemaforoDto } from 'src/app/dto/semaforo-dto';
import { Estado } from 'src/app/modelo/semaforo';
import { SemaforoService } from 'src/app/service/semaforo.service';

@Component({
  selector: 'control-semaforo',
  templateUrl: './control-semaforo.component.html',
  styleUrls: ['./control-semaforo.component.css']
})
export class ControlSemaforoComponent implements OnInit {
  @Input("currentSelected") currentSelected :SemaforoDto |undefined;
  @Input("nuevoEstado") nuevoEstado:Estado=Estado.NONE
  @Input("tiempo") tiempo: number=50000
  
 


  
  constructor(private semaforoService:SemaforoService) { }

  ngOnInit(): void {
   
  }

  enviar(){
   
    if(this.currentSelected){

      this.semaforoService.enviarSemaforo(this.currentSelected?.idSemaforo,this.nuevoEstado,this.tiempo)
      
    }

  }
  
  cancelar(){
      this.currentSelected=undefined
  }
  estados:Estado[] =[Estado.VERDE,Estado.AMARILLO,Estado.ROJO]

  onEstadoSeleccionado(event: Event) {
    const valorSeleccionado = (event.target as HTMLInputElement).value
    this.nuevoEstado = Estado[valorSeleccionado as keyof typeof Estado];
  }



  obtenerEstado(value: Estado) {
    
    if(value=='VERDE'){
      this.nuevoEstado=Estado.VERDE
    }
    if(value=='AMARILLO'){
      this.nuevoEstado=Estado.AMARILLO
    }
    if(value=='ROJO'){
      this.nuevoEstado=Estado.ROJO
    }
   
  }
  obtenerTiempo(value: number) {
    
    this.tiempo = value
    
  }

 

}
