import { Estado, Semaforo } from "../modelo/semaforo";
import { Ubicacion } from "../modelo/ubicacion";
import { ConvertidorObjetoDTO } from "./convertidor-objeto-dto";
import { SemaforoDto } from "./semaforo-dto";

export class ConvertidorSemaforoDTO implements ConvertidorObjetoDTO<Semaforo, SemaforoDto> {
    public convertirDTOaObjeto(dto: SemaforoDto): Semaforo {

        const { lat, lng } = dto.opciones.position as google.maps.LatLngLiteral

        const ubicacion: Ubicacion = {
            latitud: lat,
            longitud: lng
        }

        const semaforo: Semaforo = {
            estado: dto.estado,
            idSemaforo: dto.idSemaforo,
            tiempo: dto.tiempo,
            ubicacion: ubicacion
        }

        return semaforo
    }
    public convertirObjetoAdto(objeto: Semaforo): SemaforoDto {
        if(!objeto) throw new Error("");
        
        return {
            idSemaforo: objeto.idSemaforo,
            estado: objeto.estado,
            icon: this.verificarEstado(objeto.estado),
            opciones: {
                position: {
                    lat: objeto.ubicacion.latitud,
                    lng: objeto.ubicacion.longitud,
                }
            },
            tiempo: objeto.tiempo
        }
    }



    private verificarEstado(estado: Estado): string {

        switch (estado) {
            case Estado.VERDE:
                return "verde"
            case Estado.ROJO:
                return "rojo"
            case Estado.AMARILLO:
                return "amarillo"
            default:
                return "verde"
        }
    }

}
