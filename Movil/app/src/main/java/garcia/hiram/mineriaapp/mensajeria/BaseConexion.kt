package garcia.hiram.mineriaapp.mensajeria

interface BaseConexion<T> {
    fun conectar()
    fun getChannel():T


}