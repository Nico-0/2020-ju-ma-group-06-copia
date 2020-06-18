package dominio.compra;

import org.apache.commons.lang3.Validate;

public class PrestadorServicios implements Proveedor {

	private String razon_social;
	private long cuil_o_cuit;
	DireccionPostal direccion_postal;
	
	public PrestadorServicios(String razon_social, long cuil_o_cuit, DireccionPostal direccionPostal) {
		Validate.notNull(razon_social, "razon social faltante");
		Validate.notNull(cuil_o_cuit, "cuil/cuit faltante");
		Validate.notNull(direccion_postal, "direccion postal faltante");
		this.razon_social = razon_social;
		this.cuil_o_cuit = cuil_o_cuit;
		this.direccion_postal = direccion_postal;
	}
	
}
