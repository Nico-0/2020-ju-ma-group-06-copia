package dominio.compra;

import org.apache.commons.lang3.Validate;

public class Proveedor {
	
	private String razon_social;
	private String dni_cuil_cuit;
	DireccionPostal direccion_postal;

	public Proveedor(String razonSocial, String dni_cuil_cuit, DireccionPostal direccion_postal) {
		Validate.notNull(razonSocial, "razon social faltante");
		Validate.notNull(dni_cuil_cuit, "dni/cuil/cuit faltante");
		Validate.notNull(direccion_postal, "direccion postal faltante");
		this.razon_social = razonSocial;
		this.dni_cuil_cuit = dni_cuil_cuit;
		this.direccion_postal = direccion_postal;
	}
}
