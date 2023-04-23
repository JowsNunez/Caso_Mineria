import { Estado } from "../modelo/semaforo"

export declare class SemaforoDto {
    idSemaforo: string
    icon:string 
    estado:Estado
    opciones: google.maps.MarkerOptions
    tiempo: number


}
