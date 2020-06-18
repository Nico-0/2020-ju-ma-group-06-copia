package dominio.compra;

import org.apache.commons.lang3.Validate;

public class PersonaProveedor implements Proveedor {

	private String nombre_y_apellido;
	private long dni;
	DireccionPostal direccion_postal;

	public PersonaProveedor(String nombre_y_apellido, long dni, DireccionPostal direccion_postal) {
		Validate.notNull(nombre_y_apellido, "nombre/apellido faltante");
		Validate.notNull(dni, "dni faltante");
		Validate.notNull(direccion_postal, "direccion postal faltante");
		this.nombre_y_apellido = nombre_y_apellido;
		this.dni = dni;
		this.direccion_postal = direccion_postal;
	}

}
