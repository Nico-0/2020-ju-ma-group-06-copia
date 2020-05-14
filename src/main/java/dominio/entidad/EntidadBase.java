package dominio.entidad;

import dominio.PreconditionFailed;
import dominio.Preconditions;

public class EntidadBase extends Entidad {

	private String descripcion;
	//private Entidad entidadJuridica; //para mi deberia conocer a la entidad juridica si es que la tiene
	
	public EntidadBase(String nombreFicticio, String descripcion) throws PreconditionFailed {
		Preconditions.validateNotNull(nombreFicticio, "nombre ficticio faltante");
		Preconditions.validateNotNull(descripcion, "descripcion faltante");
		this.nombreFicticio = nombreFicticio;
		this.descripcion = descripcion;
	}
}
