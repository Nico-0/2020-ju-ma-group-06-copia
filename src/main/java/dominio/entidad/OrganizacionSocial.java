package dominio.entidad;

import java.util.List;

public class OrganizacionSocial extends EntidadJuridica {

	public OrganizacionSocial(String razonSocial, String nombreFicticio, String cuit, String direccionPostal, List<EntidadBase> entidades, Categoria categoria) {
		super(razonSocial, nombreFicticio, cuit, direccionPostal, entidades, categoria);
	}
}