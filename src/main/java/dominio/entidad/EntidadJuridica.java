package dominio.entidad;

import java.util.ArrayList;
import java.util.List;

import dominio.PreconditionFailed;
import dominio.Preconditions;

public abstract class EntidadJuridica extends Entidad {

	private List<EntidadBase> entidades_base = new ArrayList<EntidadBase>();	//puede ser vacia
	private String razonSocial;
	private String cuit;
	private String direccionPostal;
	private String codigoInscripcion;
	
	public EntidadJuridica(String razonSocial, String nombreFicticio, String cuit, String direccionPostal) throws PreconditionFailed {
		Preconditions.validateNotNull(razonSocial, "razon social faltante");
		Preconditions.validateNotNull(nombreFicticio, "nombre ficticio faltante");
		Preconditions.validateNotNull(cuit, "cuit faltante");
		Preconditions.validateNotNull(direccionPostal, "direccion postal faltante");
		this.razonSocial = razonSocial;
		this.nombreFicticio = nombreFicticio;
		this.cuit = cuit;
		this.direccionPostal = direccionPostal;
	}
	public void setCodigoInscripcion(String codigoInscripcion) {
		this.codigoInscripcion = codigoInscripcion;
	}

}
