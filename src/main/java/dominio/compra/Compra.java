package dominio.compra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import dominio.PreconditionFailed;
import dominio.entidad.Entidad;

public class Compra {

	private List<Item> items = new ArrayList<Item>();
	private Proveedor proveedor;
	private MedioPago medioPago;
	private DocumentoComercial documentoComercial; // puede ser nulo
	private LocalDate fecha;
	// private Entidad entidad;

	public double valor_total() {
		return items.stream().mapToDouble(item -> item.get_valor_total()).sum();
	}

	public Compra(Proveedor proveedor, MedioPago medioPago, LocalDate fecha) {
		Validate.notNull(proveedor, "proveedor faltante");
		Validate.notNull(medioPago, "medio de pago faltante");
		Validate.notNull(fecha, "fecha faltante");
		// Preconditions.validateNotNull(entidad,"entidad faltante");
		this.proveedor = proveedor;
		this.medioPago = medioPago;
		this.fecha = fecha;
		// this.entidad = entidad;
	}

	public void setDocumentoComercial(DocumentoComercial documentoComercial) {
		this.documentoComercial = documentoComercial;
	}

	public void setItems(List<Item> items) {
		this.items = items;

	}

}
