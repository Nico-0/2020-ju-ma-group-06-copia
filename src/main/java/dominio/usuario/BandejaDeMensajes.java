package dominio.usuario;

import java.util.ArrayList;
import java.util.List;

public class BandejaDeMensajes {
	public BandejaDeMensajes(){
		
	}
	
	private List<Mensaje> mensajes = new ArrayList<>();
	
	public void agregarMensaje(Mensaje unMensaje) {
		mensajes.add(unMensaje);
	}
	
	public List<Mensaje> listaDeMensajes() {
		return mensajes;
	}
	
	public int cantidadMensajes() {
		return mensajes.size();
	}
	
}
