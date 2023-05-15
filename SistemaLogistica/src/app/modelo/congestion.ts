export declare class Congestion {
    id: number;
    location: string;
    type: Type;
    description: string;

}

export enum Type {
    ACCIDENTE = "ACCIDENTE", SEMAFORO = "SEMAFORO", CAMION = "CAMION"

}
