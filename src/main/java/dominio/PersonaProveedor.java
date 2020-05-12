package dominio;

public class PersonaProveedor implements Proveedor {

	private String nombre_y_apellido;
	private long dni;
	private String direccion_postal;
	
	public PersonaProveedor(String nombre_y_apellido, long dni, String direccion_postal) throws PreconditionFailed {
		Preconditions.validateNotNull(nombre_y_apellido, "nombre/apellido faltante");
		Preconditions.validateNotNull(dni, "dni faltante");
		Preconditions.validateNotNull(direccion_postal, "direccion_postal faltante");
		this.nombre_y_apellido = nombre_y_apellido;
		this.dni = dni;
		this.direccion_postal = direccion_postal;
	}
	
}
