package dominio.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BandejaDeMensajes {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany
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
