package dominio.usuario;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import dominio.presupuestos.*;

@Entity
public class Mensaje {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Long idCompraPendiente;
	
	String unMensaje;
	
	public Mensaje() {}
	
	public Mensaje(String unMensaje, Long idCompraPendiente){
		this.unMensaje = unMensaje;
		this.idCompraPendiente = idCompraPendiente;
	}
	
	public void enviarseBandejaMensajes(BandejaDeMensajes unaBandeja) {
		unaBandeja.agregarMensaje(this);
	}
	
	public Long getIdCompraPendiente() {
		return idCompraPendiente;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getDescripcion() {
		return unMensaje;
	}
}