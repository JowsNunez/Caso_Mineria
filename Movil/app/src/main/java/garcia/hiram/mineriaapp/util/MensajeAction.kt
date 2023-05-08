package garcia.hiram.mineriaapp.util

// interface encargada de enviar notificacion a los Listeners
interface MensajeAction {
   fun agregarListener(o:MensajeListener)
   fun eliminarListener(o:MensajeListener)
   fun actualizarListener()
}