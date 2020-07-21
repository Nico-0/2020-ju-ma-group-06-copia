package dominio.entidad;

import org.apache.commons.lang3.Validate;

public class EntidadBase extends Entidad {

	private String descripcion;
	//private Entidad entidadJuridica; //para mi deberia conocer a la entidad juridica si es que la tiene
	
	public EntidadBase(String nombreFicticio, String descripcion) {
		Validate.notNull(nombreFicticio, "nombre ficticio faltante");
		Validate.notNull(descripcion, "descripcion faltante");
		this.nombreFicticio = nombreFicticio;
		this.descripcion = descripcion;
	}

    public boolean puedeAgregarseAEntidadJuridica() {
        return categoria.puedeAgregarseAEntidadJuridica();
    }
}
