package dominio.usuario;
import dominio.presupuestos.*;

public class Mensaje {
	CompraPendiente unaCompra;
	String unMensaje;
	
	public Mensaje(CompraPendiente unaCompra, String unMensaje){
		this.unaCompra = unaCompra;
		this.unMensaje = unMensaje;
	}
	
	public void enviarseBandejaMensajes(BandejaDeMensajes unaBandeja) {
		unaBandeja.agregarMensaje(this);
	}
	
}