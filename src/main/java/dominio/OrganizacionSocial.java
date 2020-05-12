package dominio;

public class OrganizacionSocial extends EntidadJuridica {

	public OrganizacionSocial(String razonSocial, String nombreFicticio, String cuit, String direccionPostal) throws PreconditionFailed {
		super(razonSocial, nombreFicticio, cuit, direccionPostal);
	}
}
