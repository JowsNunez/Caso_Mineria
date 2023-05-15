import { Inject, Injectable } from '@angular/core';
import { DespachadorMensajeria } from '../mensajeria/mensajeria.configuracion';
import { SemaforoDto } from '../dto/semaforo-dto';
import { ConvertidorSemaforoDTO } from '../dto/convertidor-semaforo-dto';
import { ConvertidorObjetoDTO } from '../dto/convertidor-objeto-dto';
import { Estado, Semaforo } from '../modelo/semaforo';

@Injectable({
  providedIn: 'root'
})
export class SemaforoService {

  private _semaforos: SemaforoDto[]=[]

  private convertidor: ConvertidorObjetoDTO<Semaforo, SemaforoDto>
  constructor(@Inject("Rabbit") private mensajeDispatcher: DespachadorMensajeria) {
    this.convertidor = new ConvertidorSemaforoDTO()
    this.init()

  }

  public enviarSemaforo(idSemaforo: string,estado?:Estado,tiempo?:number): void {

    const semaforoDto = this.semaforos.find((e) => e.idSemaforo === idSemaforo);

    if (!semaforoDto) throw new Error("");
    if(estado) semaforoDto.estado=estado
    if(tiempo&&tiempo>100) semaforoDto.tiempo =tiempo
    
    const semaforo = this.convertidor.convertirDTOaObjeto(semaforoDto);

  

    const data = JSON.stringify(semaforo)

    this.mensajeDispatcher.enviar("/exchange/envio.semaforo/semaforo."+semaforo.idSemaforo, { data })



  }
  public recibirSemaforos() {

    this.mensajeDispatcher.recibir("/exchange/envio.semaforo/semaforos").subscribe(mensaje => {
      const data = mensaje.data
      if (!data) throw new Error("")  
       const semaforo = JSON.parse(data)
     
      const semaforoDto = this.convertidor.convertirObjetoAdto(semaforo)
      console.log(new Date(),">>>>> DATA UNIQUE " + data)

   //   this.semaforos.push(semaforoDto)
    

    })

  }

  public recibirDeSemaforo(idSemaforo: string) {
    if (!idSemaforo||idSemaforo==="") throw new Error("")

    this.mensajeDispatcher.recibir("/exchange/envio.semaforo/semaforos").subscribe(mensaje => {
    
      const data = mensaje.data
    
      if (!data) throw new Error("")
      
      const semaforo: Semaforo = JSON.parse(data)
      const dto: SemaforoDto = this.convertidor.convertirObjetoAdto(semaforo)
      const semaforoDto: SemaforoDto | undefined = this.semaforos.find((e) => {
        
        return e.idSemaforo === semaforo.idSemaforo});

        
      if (semaforoDto!=undefined) {
        semaforoDto.estado = dto.estado;
        semaforoDto.tiempo = dto.tiempo;
        semaforoDto.icon = dto.icon;
        semaforoDto.opciones = dto.opciones;
      }else{
        this.semaforos.push(dto)
      }

    })

  }

  
  public get semaforos() : SemaforoDto[] {
    return this._semaforos
  }
  


  
 

  public init() {
    // const aux: Semaforo[] = [{
    //   idSemaforo: "semaforo_1",
    //   estado: Estado.ROJO,
    //   tiempo: 2000,
    //   ubicacion: { latitud: 30.96719745678408, longitud: -110.32206220589997 }
    // },
    // {
    //   idSemaforo: "semaforo_2",
    //   estado: Estado.AMARILLO,
    //   tiempo: 1500,
    //   ubicacion: { latitud: 30.963636336478395, longitud: -110.32739755907858 }
    // },
    // {
    //   idSemaforo: "semaforo_4",
    //   estado: Estado.VERDE,
    //   tiempo: 2500,
    //   ubicacion: { latitud: 30.96711660132356, longitud: -110.32246927576035 }
    // },
    // {
    //   idSemaforo: "semaforo_5",
    //   estado: Estado.ROJO,
    //   tiempo: 3000,
    //   ubicacion: { latitud: 30.965512534191355, longitud: -110.32051756204399 }
    // },
    // {
    //   idSemaforo: "semaforo_6",
    //   estado: Estado.VERDE,
    //   tiempo: 2000,
    //   ubicacion: { latitud: 30.964567, longitud: -110.322097 }
    // }]

    // aux.forEach(e => {
    //   this.semaforos.push(this.convertidor.convertirObjetoAdto(e))
    // })
    


  }
}
