package dominio.compra;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
	private String etiqueta;
	// private Entidad entidad;

	public double valor_total() {
		return detalle.getTotal();
	}

	public String mes() {
		Month mes = fecha.getMonth();
		String nombre = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
		
		String primeraLetra = nombre.substring(0,1);
		String mayuscula = primeraLetra.toUpperCase();
		String demasLetras = nombre.substring(1, nombre.length());
		nombre = mayuscula + demasLetras;

		return nombre;
	}
	
	public Compra(Proveedor proveedor, 
				  MedioPago medioPago, 
				  LocalDate fecha,
				  List<Presupuesto> presupuestos,
				  Detalle detalle,
				  List<Usuario> usuariosRevisores,
				  String etiqueta) {
		Validate.notNull(proveedor, "proveedor faltante");
		Validate.notNull(medioPago, "medio de pago faltante");
		Validate.notNull(fecha, "fecha faltante");
		Validate.notNull(detalle, "detalle faltante");
		Validate.notNull(usuariosRevisores, "usuarios faltante");
		Validate.notNull(etiqueta, "etiqueta faltante");
		// Preconditions.validateNotNull(entidad,"entidad faltante");
		this.proveedor = proveedor;
		this.medioPago = medioPago;
		this.fecha = fecha;
		this.detalle = detalle;
		this.usuariosRevisores = usuariosRevisores;
		this.presupuestos = presupuestos;
		this.etiqueta = etiqueta;
		// this.entidad = entidad;
	}

	public void setDocumentoComercial(DocumentoComercial documentoComercial) {
		this.documentoComercial = documentoComercial;
	}
	
	public boolean esDelMes(String mes) {
		return this.mes().equals(mes);
	}
	
	public String etiqueta() {
		return etiqueta;
	}

}
