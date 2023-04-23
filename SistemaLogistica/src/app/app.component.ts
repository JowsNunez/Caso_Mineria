import { Component } from '@angular/core';
import { MensajeriaService } from './mensajeria/mensajeria.service';
import { Mensaje } from './mensajeria/configuracion.mensaje';
import { SemaforoService } from './service/semaforo.service';
import { SemaforoDto } from './dto/semaforo-dto';
import { Estado } from './modelo/semaforo';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  mensaje: Mensaje = {}
  mensajeVerde: Mensaje = {}
  contador: number = 0;
  contadorVerde: number = 0;
  currentSelected: string = "semaforo_1"
  estado: Estado = Estado.VERDE
  state: string = ""
  tiempo: number = 0
  semaforos: SemaforoDto[] = this.semaforoService.semaforos

  constructor(private semaforoService: SemaforoService) {

  }

  ngOnInit(): void {


    this.semaforoService.recibirDeSemaforo(this.currentSelected);

  }

  public enviar() {

    if (!this.currentSelected || this.currentSelected === "") alert(null)

    this.mostrar(this.state)
    this.semaforoService.enviarSemaforo(this.currentSelected, this.estado, this.tiempo);

  }

  public setCurrent(idSemaforo: string) {
    this.currentSelected = idSemaforo

  }

  mostrar(state: string) {
    state = state.toLowerCase()
    switch (state) {
      case 'verde':
        this.estado = Estado.VERDE
        break;
      case 'rojo':
        this.estado = Estado.ROJO
        break;
      case 'amarillo':
        this.estado = Estado.AMARILLO
        break;
      default:
        alert("Error")
        throw new Error("Error");


    }

  }

  mostrarTiempo(tiempo: number) {


    this.tiempo = tiempo
  }




}
