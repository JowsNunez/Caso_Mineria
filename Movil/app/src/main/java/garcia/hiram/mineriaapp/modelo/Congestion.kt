package garcia.hiram.mineriaapp.modelo

data class Congestion  (
    val id: Long?,
    var location: String,
    val type: Type,
    val description: String,
    val resolve:Boolean = false

)


 enum class Type {
    ACCIDENTE, SEMAFORO, CAMION,NONE
}
