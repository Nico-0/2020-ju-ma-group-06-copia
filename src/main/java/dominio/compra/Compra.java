package dominio.compra;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.Validate;

import converter.LocalDateTimeConverter;
import dominio.presupuestos.Detalle;
import dominio.presupuestos.Presupuesto;
import dominio.usuario.Usuario;

import javax.persistence.*;

@Entity
public class Compra {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany
	private List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
	
	@OneToOne
	private Detalle detalle = new Detalle();
	
	@ManyToOne
	private Proveedor proveedor;
	
	@ManyToOne
	private MedioPago medioPago;
	
	@OneToOne
	private DocumentoComercial documentoComercial;
	
	@Column
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime fecha = LocalDateTime.now();
	
	@ManyToMany
	private List<Usuario> usuariosRevisores = new ArrayList<>();
	
	@ElementCollection
	private List<String> etiquetas;
	
    @Transient
    String tabla;

	public double valor_total() {
		return detalle.getTotal();
	}
	
	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
	}
	
	public List<Item> getDetalle() {
		return detalle.getItems();
	}
	
	public Proveedor getProveedor() {
		return proveedor;
	}
	
	public MedioPago getMedioPago() {
		return medioPago;
	}
	
	public String getDocumentoComercial() {
		return documentoComercial.toString();
	}
	
	public List<String> getEtiquetas() {
		return etiquetas;
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
	
	public Compra() {
		
	}
	
	public Compra(Proveedor proveedor, 
				  MedioPago medioPago, 
				  LocalDateTime fecha,
				  List<Presupuesto> presupuestos,
				  Detalle detalle,
				  List<Usuario> usuariosRevisores,
				  List<String> etiquetas,
				  DocumentoComercial documentoComercial) {
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
		this.documentoComercial = documentoComercial;
		// this.entidad = entidad;
	}

	public void setDocumentoComercial(DocumentoComercial documentoComercial) {
		this.documentoComercial = documentoComercial;
	}
	
	public boolean esDelMes(String mes) {
		return this.mes().equals(mes);
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
	
	public String getId() {
		return String.valueOf(id);
	}
	
	public String getUrlView() {
		return "/compras/" + getId();
	}
	
    public String getDatosDocumentoComercial() {
        return documentoComercial.getDocumentoComercial();
    }

	public String getDatosProveedor() {
			return "<legend>PROVEEDOR</legend>" + 
					"Id: " + proveedor.getId() + "</br>" + 
					"Razon social: " + proveedor.getRazonSocial() + "</br>" + 
					"DNI/CUIL/CUIT: " + proveedor.getDniCuilCuit() + "</br>" + 
					"Pais: " + proveedor.getDireccionPostal().getPais() + "</br>" + 
					"Provincia: " + proveedor.getDireccionPostal().getProvincia() + "</br>" + 
					"Ciudad: " + proveedor.getDireccionPostal().getCiudad() + "</br>" + 
					"Direccion: " + proveedor.getDireccionPostal().getDireccion() + "</br>";
	}
	
	public String getTablaDetalle() {
		tabla = "<table>" + 
    			"<tr>" + 
    			"<th> Descripcion </th>" + 
    			"<th> Cantidad </th>" + 
    			"<th> Valor Unitario </th>" + 
    			"<th> Valor Item </th>" + 
    			"</tr>";
    	detalle.getItems().stream().forEach((item) -> {tabla = tabla + 
			"<tr>" + 
			"   <td> " + item.getDescripcion() + "</td>" + 
			"   <td> " + item.getCantidad() + "</td>" + 
			"   <td> " + item.getValorUnitario() + "</td>" +
			"   <td> " + item.getValorItem() + "</td>" +  
			"</tr>";
		});
    	return tabla + "</table>";
	}
	
	public String getTablaEtiquetas() {
		tabla = "<table>" + 
    			"<tr>" + 
    			"<th> Nombre </th>" + 
    			"<th></th>" + 
    			"<th></th>" + 
    			"</tr>";
    	etiquetas.stream().forEach((etiqueta) -> {tabla = tabla + 
			"<tr>" + 
			"   <td> " + etiqueta + "</td>" + 
			"   <td><a href = \"/compras/" + this.getId() + "/etiquetas/" + etiqueta + "/quitar\"> Quitar Etiqueta </a></th>" +
			"</tr>";
		});
    	return tabla + "</table>";
	}
}
