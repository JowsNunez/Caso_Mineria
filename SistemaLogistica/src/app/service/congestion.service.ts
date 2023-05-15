import { Inject, Injectable } from '@angular/core';
import { DespachadorMensajeria } from '../mensajeria/mensajeria.configuracion';
import { Congestion } from '../modelo/congestion';
import { CongestionDto } from '../dto/congestion-dto';
import { ConvertidorCongestionDto } from '../dto/convertidor-congestion-dto';
import { ConvertidorObjetoDTO } from '../dto/convertidor-objeto-dto';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CongestionService {

  private _congestionesDTO: CongestionDto[] = []
  private convertidor: ConvertidorObjetoDTO<Congestion, CongestionDto>
  constructor(@Inject("Rabbit") private despachadorMensajes: DespachadorMensajeria, private httpClient: HttpClient) {
    this.convertidor = new ConvertidorCongestionDto()

  }

  recibir() {
    this.despachadorMensajes.recibir("/exchange/envio.congestion/congestiones").subscribe(response => {
      let data = response.data
      if (data) {
        let congestiones: Congestion[]
        let msg = JSON.parse(data)
        congestiones = msg.contenido;
        console.log(congestiones)

        this._congestionesDTO = congestiones.map(e => this.convertidor.convertirObjetoAdto(e));
        console.log(this._congestionesDTO); // Verificar que se haya generado el nuevo array


      }
    })
  }

  resolver(id: number) {
    let actualizar: CongestionDto | undefined = this.congestiones.find(e => e.id == id)
    if (actualizar) {
      let congestion = this.convertidor.convertirDTOaObjeto(actualizar)
      console.log(congestion)
      this.httpClient.put("http://192.168.84.11:5000/api/v1/congestion/" + id, congestion,
        { headers: { 'Content-Type': 'application/json' } }).subscribe(e => {
          console.log("SUCCES", e)
        })


    }


  }


  public get congestiones(): CongestionDto[] {
    return this._congestionesDTO;
  }

}
