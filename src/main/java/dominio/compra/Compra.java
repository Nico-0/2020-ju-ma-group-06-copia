package dominio.compra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import dominio.presupuestos.Detalle;
import dominio.presupuestos.Presupuesto;
import dominio.usuario.Usuario;

public class Compra {

	private List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
	private Detalle detalle = new Detalle();
	private Proveedor proveedor;
	private MedioPago medioPago;
	private DocumentoComercial documentoComercial; // puede ser nulo
	private LocalDate fecha;
	private List<Usuario> usuariosRevisores = new ArrayList<>();
	// private Entidad entidad;

	public double valor_total() {
		return detalle.getTotal();
	}

	public Compra(Proveedor proveedor, 
				  MedioPago medioPago, 
				  LocalDate fecha,
				  List<Presupuesto> presupuestos,
				  Detalle detalle,
				  List<Usuario> usuariosRevisores) {
		Validate.notNull(proveedor, "proveedor faltante");
		Validate.notNull(medioPago, "medio de pago faltante");
		Validate.notNull(fecha, "fecha faltante");
		Validate.notNull(detalle, "detalle faltante");
		Validate.notNull(usuariosRevisores, "usuarios faltante");
		// Preconditions.validateNotNull(entidad,"entidad faltante");
		this.proveedor = proveedor;
		this.medioPago = medioPago;
		this.fecha = fecha;
		this.detalle = detalle;
		this.usuariosRevisores = usuariosRevisores;
		this.presupuestos = presupuestos;
		// this.entidad = entidad;
	}

	public void setDocumentoComercial(DocumentoComercial documentoComercial) {
		this.documentoComercial = documentoComercial;
	}

}
