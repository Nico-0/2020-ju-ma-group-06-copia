package dominio;

import java.util.ArrayList;
import java.util.List;

public class EntidadBase implements Entidad {

	private List<Compra> compras = new ArrayList<Compra>();
	private String nombreFicticio;
	private String descripcion;
	private EntidadJuridica entidadJuridica; //para mi deberia conocer a la entidad juridica si es que la tiene
	
	public EntidadBase(String nombreFicticio, String descripcion) throws PreconditionFailed {
		Preconditions.validateNotNull(nombreFicticio, "nombre ficticio faltante");
		Preconditions.validateNotNull(descripcion, "descripcion faltante");
		this.nombreFicticio = nombreFicticio;
		this.descripcion = descripcion;
	}
}
