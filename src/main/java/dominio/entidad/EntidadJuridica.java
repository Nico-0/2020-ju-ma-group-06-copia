package dominio.entidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dominio.PreconditionFailed;
import dominio.Preconditions;
import dominio.compra.Compra;
import dominio.compra.Item;
import dominio.compra.MedioPago;
import dominio.compra.Proveedor;

public abstract class EntidadJuridica implements Entidad {

	private List<EntidadBase> entidades_base = new ArrayList<EntidadBase>();	//puede ser vacia
	public List<Compra> compras = new ArrayList<Compra>();
	private String razonSocial;
	private String nombreFicticio;
	private String cuit;
	private String direccionPostal;
	private String codigoInscripcion;
	
	public EntidadJuridica(String razonSocial, String nombreFicticio, String cuit, String direccionPostal) throws PreconditionFailed {
		Preconditions.validateNotNull(razonSocial, "razon social faltante");
		Preconditions.validateNotNull(nombreFicticio, "nombre ficticio faltante");
		Preconditions.validateNotNull(cuit, "cuit faltante");
		Preconditions.validateNotNull(direccionPostal, "direccion postal faltante");
		this.razonSocial = razonSocial;
		this.nombreFicticio = nombreFicticio;
		this.cuit = cuit;
		this.direccionPostal = direccionPostal;
	}
	public void setCodigoInscripcion(String codigoInscripcion) {
		this.codigoInscripcion = codigoInscripcion;
	}
	
	public void realizarCompra(List<Item> items, Proveedor proveedor, MedioPago medioPago) throws PreconditionFailed {
		LocalDate fecha = LocalDate.now();
		Compra miCompra = new Compra(proveedor, medioPago, fecha, this);
		
		compras.add(miCompra);
	}
	
	
}
