import { Ubicacion } from "./ubicacion";

export declare class Semaforo {
    idSemaforo:string
    estado:Estado
    ubicacion:Ubicacion
    tiempo:number
}


export enum Estado{
    NONE="NONE",
    VERDE="VERDE",
    ROJO="ROJO",
    AMARILLO="AMARILLO"
}