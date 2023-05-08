package garcia.hiram.mineriaapp.mensajeria

interface BaseReceptor<T> {
    fun configurar()
    fun recibir(consumidor: T)
}