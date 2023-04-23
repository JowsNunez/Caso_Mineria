export interface ConvertidorObjetoDTO<T,D> {
    convertirDTOaObjeto(dto:D):T
    convertirObjetoAdto(objeto:T):D
}
