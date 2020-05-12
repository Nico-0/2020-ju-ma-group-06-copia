package dominio;

import java.util.ArrayList;
import java.util.List;

public abstract class EntidadJuridica implements Entidad {

	private List<EntidadBase> entidades_base = new ArrayList<EntidadBase>();	//puede ser vacia
	private List<Compra> compras = new ArrayList<Compra>();
	private String razonSocial;
	private String nombreFicticio;
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
