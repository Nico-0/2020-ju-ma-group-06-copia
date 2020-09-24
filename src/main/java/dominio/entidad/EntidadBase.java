package dominio.entidad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.Validate;

public class EntidadBase extends Entidad {

	private String descripcion;	
	
	public EntidadBase(String nombreFicticio, String descripcion) {
		Validate.notNull(nombreFicticio, "nombre ficticio faltante");
		Validate.notNull(descripcion, "descripcion faltante");
		this.nombreFicticio = nombreFicticio;
		this.descripcion = descripcion;
	}

    public boolean puedeAgregarseAEntidadJuridica() {
        return categorias.stream().allMatch(categoria -> categoria.puedeAgregarseAEntidadJuridica());
    }
    
}
