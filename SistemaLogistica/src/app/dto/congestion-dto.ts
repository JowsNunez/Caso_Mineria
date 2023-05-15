import { Type } from "../modelo/congestion"

export declare class CongestionDto {
    id: number
    icon:string 
    type:Type
    opciones: google.maps.MarkerOptions
    description: string
}
