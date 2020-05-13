package dominio.compra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dominio.PreconditionFailed;
import dominio.Preconditions;
import dominio.entidad.Entidad;

public class Compra {
	
	private List<Item> items = new ArrayList<Item>();
	private Proveedor proveedor;
	private MedioPago medioPago;
	private DocumentoComercial documentoComercial; //puede ser nulo
	private LocalDate fecha;
	private Entidad entidad;
	
	public double valor_total(){    
		return items.stream().mapToDouble(item -> item.get_valor_total()).sum();
	}
	
	public Compra(Proveedor proveedor, MedioPago medioPago, LocalDate fecha, Entidad entidad) throws PreconditionFailed {
		Preconditions.validateNotNull(proveedor,"proveedor faltante");
		Preconditions.validateNotNull(medioPago,"medio de pago faltante");
		Preconditions.validateNotNull(fecha,"fecha faltante");
		Preconditions.validateNotNull(entidad,"entidad faltante");
		this.proveedor = proveedor;
		this.medioPago = medioPago;
		this.fecha = fecha;
		this.entidad = entidad;
	}
	
	public void setDocumentoComercial(DocumentoComercial documentoComercial) {
		this.documentoComercial = documentoComercial;
	}
	
}
