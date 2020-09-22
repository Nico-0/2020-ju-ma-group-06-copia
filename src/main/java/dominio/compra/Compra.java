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

import javax.persistence.*;

@Entity
public class Compra {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
	private Detalle detalle = new Detalle();
	private Proveedor proveedor;
	private MedioPago medioPago;
	private DocumentoComercial documentoComercial; // puede ser nulo
	private LocalDate fecha;
	private List<Usuario> usuariosRevisores = new ArrayList<>();
	private List<String> etiquetas;
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
				  List<String> etiquetas) {
		Validate.notNull(proveedor, "proveedor faltante");
		Validate.notNull(medioPago, "medio de pago faltante");
		Validate.notNull(fecha, "fecha faltante");
		Validate.notNull(detalle, "detalle faltante");
		Validate.notNull(usuariosRevisores, "usuarios faltante");
		Validate.notNull(etiquetas, "lista de etiquetas faltante");
		// Preconditions.validateNotNull(entidad,"entidad faltante");
		this.proveedor = proveedor;
		this.medioPago = medioPago;
		this.fecha = fecha;
		this.detalle = detalle;
		this.usuariosRevisores = usuariosRevisores;
		this.presupuestos = presupuestos;
		this.etiquetas = etiquetas;
		// this.entidad = entidad;
	}

	public void setDocumentoComercial(DocumentoComercial documentoComercial) {
		this.documentoComercial = documentoComercial;
	}
	
	public boolean esDelMes(String mes) {
		return this.mes().equals(mes);
	}
	
	public List<String> getEtiquetas() {
		return etiquetas;
	}
	
	public void agregarEtiqueta(String etiqueta) {
		etiquetas.add(etiqueta);
	}
	
	public void quitarEtiqueta(String etiqueta) {
		etiquetas.remove(etiqueta);
	}

	public boolean tieneEtiqueta(String etiqueta) {
		return etiquetas.contains(etiqueta);
	}
	

}
