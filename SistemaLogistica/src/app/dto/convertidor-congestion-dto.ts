import { Congestion } from "../modelo/congestion";
import { CongestionDto } from "./congestion-dto";
import { ConvertidorObjetoDTO } from "./convertidor-objeto-dto";

export class ConvertidorCongestionDto implements ConvertidorObjetoDTO<Congestion,CongestionDto>{
    convertirDTOaObjeto(dto: CongestionDto): Congestion {
        
        return {
            id: dto.id,
            description: dto.description,
            type: dto.type,
            location: `${dto.opciones.position?.lat},${dto.opciones.position?.lng}`
        }

    }
    convertirObjetoAdto(objeto: Congestion): CongestionDto {
        let position =objeto.location.split(",")
        
        return {
            id:objeto.id,
            icon: "/assets/alert.png",
            opciones:{
                position: {
                    lat: parseFloat( position[0]),
                    lng: parseFloat( position[1]),
                }
            },
            type: objeto.type,
            description: objeto.description

        }
    }
}
