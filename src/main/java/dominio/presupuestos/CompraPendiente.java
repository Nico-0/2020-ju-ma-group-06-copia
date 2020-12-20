package dominio.presupuestos;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import converter.LocalDateTimeConverter;
import dominio.compra.*;
import dominio.entidad.Entidad;
import dominio.usuario.*;
import repositorios.RepositorioEntidades;

@Entity
public class CompraPendiente {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany
    private List<Presupuesto> presupuestos = new ArrayList<>();
	
	@OneToOne(cascade = {CascadeType.ALL})
    private Detalle detalle = new Detalle();
    
	@ManyToOne(optional = true, cascade = {CascadeType.ALL})
    private Proveedor proveedor;
    
	@Enumerated
	private CriterioDeSeleccionPresupuesto criterioDeSeleccion = CriterioDeSeleccionPresupuesto.SinCriterioDeSeleccion;
	
    private int cantidadPresupuestosRequeridos = 0;
    
	@Column
	@Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime fecha = LocalDateTime.now();
    
    @ManyToOne(optional = true, cascade = {CascadeType.ALL})
    private MedioPago medioPago;
    
    @ManyToMany
    private List<Usuario> usuariosRevisores = new ArrayList<>();
    
    @ManyToOne(optional = true,cascade = {CascadeType.ALL})
    private Entidad entidad;
    
    @ElementCollection
    private List<String> etiquetas = new ArrayList<String>();
    
    @Transient
    String tabla;
    
    public CompraPendiente() {

    }
    
    public void setCantidadPresupuestosRequeridos(int unaCantidad) {
        cantidadPresupuestosRequeridos = unaCantidad;
    }
    
    public long getId(){
  	  return this.id;
    }
    
    public List<Presupuesto> getPresupuestos(){
    	  return this.presupuestos;
      }
    
    public String getFecha(){
    	if(fecha == null)
    		return "NULL";
    		else
    	  return this.fecha.toString();//format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    } 
    
    public Detalle getDetalle(){
    	  return this.detalle;
    }
    
    public Proveedor getProveedor(){
    	  return this.proveedor;
    }
    
    public String getTipoMedioPago(){
  	  return this.medioPago.getTipoMedioPago();
    } 
    
    public String getIdentificadorMedioPago(){
    	  return this.medioPago.getIdentificadorMedioPago();
      } 
    
    public Entidad getEntidad(){
		return this.entidad;
    }   
    
    public long getCantidadpresupactuales(){ //si se pone mas de una mayuscula hibernate no lo detecta
  	  return this.presupuestos.size();
    }
    
    public int getCantidadpresuprequeridos(){ //si se pone mas de una mayuscula hibernate no lo detecta
    	  return this.cantidadPresupuestosRequeridos;
      }
    
    public String getCriterioseleccion(){ //si se pone mas de una mayuscula hibernate no lo detecta
    	  return this.criterioDeSeleccion.toString();
      } 
        
    public CompraPendiente setCriterioDeSeleccion(CriterioDeSeleccionPresupuesto unCriterio) {
    	this.criterioDeSeleccion = unCriterio;
    	return this;
    }
    
    public void setMedioPago(MedioPago medioPago) {
    	this.medioPago = medioPago;
    }
    
    public void setFecha(LocalDateTime fecha) {
    	this.fecha = fecha;
    }
    
    public void setDetalle(Detalle detalle) {
    	this.detalle = detalle;
    }
    
    public CompraPendiente setProveedor(Proveedor proveedor) {
		//final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		//EntityTransaction transaction = entityManager.getTransaction();
		//transaction.begin();
		this.proveedor = proveedor;
		//transaction.commit();
    	return this;
    }

    public CompraPendiente agregarPresupuesto(Presupuesto unPresupuesto) {
        presupuestos.add(unPresupuesto);
        return this;
    }

    public boolean verificarCantidadPresupuestos() {
    	return validarCondicion(presupuestos.size() == cantidadPresupuestosRequeridos,
    			"La cantidad de presupuestos cargada es incorrecta. Se necesitan " + 
    			cantidadPresupuestosRequeridos + " y se han cargado " + presupuestos.size());
    }
    
    public boolean verificarDetallePresupuesto() {
    	return validarCondicion(presupuestoProveedorSeleccionado().tieneMismoDetalle(detalle),
    			"El presupuesto del proveedor seleccionado no coincide con el detalle de la compra");
    }
    
    public boolean verificarCriterioDeSeleccion(){
    	return validarCondicion(criterioDeSeleccion.verificar(presupuestos, presupuestoProveedorSeleccionado()),
    									"El presupuesto del proveedor elegido no es el mï¿½s barato");
    }
    
    public boolean verificarEntidadPuedeAgregarCompra(){
    	return validarCondicion(entidad.sePuedeAgregarCompra(this),
    									"La entidad ha superado la cantidad máxima de egresos");
    }
    
    public boolean validarCondicion(boolean condicion, String mensaje) {
    	if(!condicion) {
    		enviarMensajeRevisores(mensaje);
    		return false;
    	}
    	return true;
    }
    
    public boolean verificarParametrosNotNull() {
    	return validarCondicion(proveedor != null,"proveedor faltante") &&
    	validarCondicion(medioPago != null, "medio de pago faltante") &&
    	validarCondicion(fecha != null, "fecha faltante") &&
		validarCondicion(entidad != null,"entidad faltante");
    }
    
    public Presupuesto presupuestoProveedorSeleccionado() {
    	return presupuestos.stream().filter(presupuesto -> presupuesto.proveedorEs(proveedor)).findFirst().get();
    }
    
    public CompraPendiente agregarItem(Item unItem) {
    	detalle.agregarItem(unItem);
    	return this;
    }
    
    public void agregarUsuarioRevisor(Usuario unUsuario) {
    	//EntityManager em = PerThreadEntityManagers.getEntityManager();
		//EntityTransaction transaction = em.getTransaction();
		//transaction.begin();
		usuariosRevisores.add(unUsuario);
		//transaction.commit();
    }
    
    public void quitarUsuarioRevisor(Usuario unUsuario) {
    	//EntityManager em = PerThreadEntityManagers.getEntityManager();
		//EntityTransaction transaction = em.getTransaction();
		//transaction.begin();
		usuariosRevisores.remove(unUsuario);
		//transaction.commit();	
    }
    
    public void enviarMensajeRevisores(String texto) {
    	usuariosRevisores.stream().forEach(unUsuario -> {
    		Mensaje unMensaje = new Mensaje(texto, this.id);
    		unUsuario.recibirMensaje(unMensaje);
    	}); 
    }
    
    public void setEntidad(Entidad entidad) {
		//final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		//EntityTransaction transaction = entityManager.getTransaction();
		//transaction.begin();
    	this.entidad = entidad;
		//transaction.commit();
    }
    
    public boolean verificarQueEsValida() {
    	return verificarParametrosNotNull() &&
                verificarCantidadPresupuestos() &&
    			verificarDetallePresupuesto() &&
    			verificarCriterioDeSeleccion() && 
    			verificarEntidadPuedeAgregarCompra();
    }
    
    public void validarCompra() {
	    if(verificarQueEsValida()) {
	    	final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
			//final EntityTransaction transaction = entityManager.getTransaction();
	    	DocumentoComercial documentoComercial = new DocumentoComercial();
	    	documentoComercial.setNumeroDocumento(this.presupuestoProveedorSeleccionado().getDocumentoComercial().getNumeroDocumento());
	    	documentoComercial.setTipoDocumentoComercial(this.presupuestoProveedorSeleccionado().getDocumentoComercial().getTipoDocumentoComercial());
			Compra compra = new Compra(proveedor, medioPago, fecha, presupuestos, detalle, usuariosRevisores, etiquetas, documentoComercial);
			//transaction.begin();
			entityManager.persist(documentoComercial);
			entityManager.persist(compra);
			entidad.agregarCompra(compra);
			enviarMensajeRevisores("La compra fue validada.");
			this.proveedor = null;
			this.medioPago = null;
			this.fecha = null;
			this.presupuestos = null;
			this.detalle = null;
			this.usuariosRevisores = null;
			this.etiquetas = null;
			this.entidad = null;
			entityManager.remove(this);
			//transaction.commit();
		}
    }

	public boolean estaSuscrito(Usuario usuario) {
		return usuariosRevisores.contains(usuario);
	}

	public String getUrlView() {
		return "/compras_pendientes/" + this.getId();
	}

	public String getUrlBorrar() {
		return "/compras_pendientes/" + this.getId() + "/borrar";
	}
	
	public String getDatosProveedor() {
		if(proveedor != null)
			return "<legend>PROVEEDOR</legend>" + 
					"Id: " + proveedor.getId() + "</br>" + 
					"Razon social: " + proveedor.getRazonSocial() + "</br>" + 
					"DNI/CUIL/CUIT: " + proveedor.getDniCuilCuit() + "</br>" + 
					"Pais: " + proveedor.getDireccionPostal().getPais() + "</br>" + 
					"Provincia: " + proveedor.getDireccionPostal().getProvincia() + "</br>" + 
					"Ciudad: " + proveedor.getDireccionPostal().getCiudad() + "</br>" + 
					"Direccion: " + proveedor.getDireccionPostal().getDireccion() + "</br>" + 
					"<a class = \"link_boton\" href = \"/compras_pendientes/" + this.getId() + "/seleccionar_proveedor\">  Cambiar proveedor </a>"+ 
					"<a class = \"link_boton\" href = \"/compras_pendientes/" + this.getId() + "/quitar_proveedor\">  Quitar proveedor </a>";
		else
			return "<legend>PROVEEDOR</legend>" + 
					"Falta seleccionar un proveedor" + 
					"<a class = \"link_boton\" href = \"/compras_pendientes/" + this.getId() + "/seleccionar_proveedor\">  Seleccionar proveedor </a>";

	}
	
	public String getDatosEntidad() {
		if(entidad != null)
			return "<legend>ENTIDAD</legend>" + 
					"Id: " + entidad.getId() + "</br>" + 
					"Nombre: " + entidad.getNombre() + "</br>" + 
					"<a class = \"link_boton\" href = " + entidad.getUrlView() + ">  Ver entidad </a>" + 
					"<a class = \"link_boton\" href = \"/compras_pendientes/" + this.getId() + "/seleccionar_entidad\">  Cambiar entidad </a>"+
					"<a class = \"link_boton\" href = \"/compras_pendientes/" + this.getId() + "/quitar_entidad\">  Quitar entidad </a>";
		else
			return "<legend>ENTIDAD</legend>" + 
					"Falta seleccionar una entidad" + 
					"<a class = \"link_boton\" href = \"/compras_pendientes/" + this.getId() + "/seleccionar_entidad\">  Seleccionar entidad </a>";
	}
	
	public String getTablaSeleccionarProveedor() {
		tabla = "<table>" + 
    			"<tr>" + 
    			"<th> Id </th>" + 
    			"<th> Razon Social </th>" + 
    			"<th> DNI/CUIL/CUIT </th>" + 
    			"<th> Pais </th>" + 
    			"<th> Provincia </th>" + 
    			"<th> Ciudad </th>" + 
    			"<th> Direccion </th>" + 
    			"<th></th>" + 
    			"</tr>";
		List<Proveedor> proveedores = presupuestos.stream().map(presupuesto -> presupuesto.getProveedor())
				.filter(presupuesto -> presupuesto != null).collect(Collectors.toList());

    	proveedores.stream().forEach((prov) -> {tabla = tabla + 
			    			"<tr>" + 
			    			"   <td> " + prov.getId() + "</td>" + 
			    			"   <td> " + prov.getRazonSocial() + "</td>" + 
			    			"   <td> " + prov.getDniCuilCuit() + "</td>" +
			    			"   <td> " + prov.getDireccionPostal().getPais() + "</td>" +
			    			"   <td> " + prov.getDireccionPostal().getProvincia() + "</td>" +
			    			"   <td> " + prov.getDireccionPostal().getCiudad() + "</td>" +
			    			"   <td> " + prov.getDireccionPostal().getDireccion() + "</td>";
    	if(this.proveedor == prov)
    		tabla = tabla + "   <td> Proveedor seleccionado </th>" +
    				"</tr>";
    	else
    		tabla = tabla + "   <td><a href = \"/compras_pendientes/" + this.getId() + "/seleccionar_proveedor/" + prov.getId() + "\"> Seleccionar </a></th>" +
    				"</tr>";
		});
    	return tabla + "</table>";
	}

	public String getTablaSeleccionarEntidad() {
		tabla = "<table>" + 
    			"<tr>" + 
    			"<th> Id </th>" + 
    			"<th> Nombre </th>" + 
    			"<th></th>" + 
    			"<th></th>" + 
    			"</tr>";
		List<Entidad> entidades = RepositorioEntidades.getInstance().getEntidades();

    	entidades.stream().forEach((entidad) -> {tabla = tabla + 
			    			"<tr>" + 
			    			"   <td> " + entidad.getId() + "</td>" + 
			    			"   <td> " + entidad.getNombre() + "</td>" + 
			    			"   <td><a href = " + entidad.getUrlView() + "> Ver Entidad </a></td>";
    	if(this.entidad == entidad)
    		tabla = tabla + "   <td> Entidad seleccionada </th>" +
    				"</tr>";
    	else
    		tabla = tabla + "   <td><a href = \"/compras_pendientes/" + this.getId() + "/seleccionar_entidad/" + entidad.getId() + "\"> Seleccionar </a></th>" +
    				"</tr>";
		});
    	return tabla + "</table>";
	}
	
	public String getTablaDetalle() {
		tabla = "<table>" + 
    			"<tr>" + 
    			"<th> Descripcion </th>" + 
    			"<th> Cantidad </th>" + 
    			"<th> Valor Unitario </th>" + 
    			"<th> Valor Item </th>" + 
    			"<th></th>" + 
    			"<th></th>" + 
    			"</tr>";
    	detalle.getItems().stream().forEach((item) -> {tabla = tabla + 
			"<tr>" + 
			"   <td> " + item.getDescripcion() + "</td>" + 
			"   <td> " + item.getCantidad() + "</td>" + 
			"   <td> " + item.getValorUnitario() + "</td>" +
			"   <td> " + item.getValorItem() + "</td>" +  
			"   <td><a href = \"/compras_pendientes/" + this.getId() + "/items/" + item.getId() + "/editar\"> Editar Item </a></th>" +
			"   <td><a href = \"/compras_pendientes/" + this.getId() + "/items/" + item.getId() + "/quitar\"> Quitar Item </a></th>" +
			"</tr>";
		});
    	return tabla + "</table>";
	}
	
	public String getTablaPresupuestos() {
		tabla = "<table>" + 
				"<tr>" + 
				"<th> Id </th>" + 
				"<th></th>" + 
				"<th></th>" + 
				"</tr>";
		presupuestos.stream().forEach((presupuesto) -> {tabla = tabla + 
				"<tr>" + 
				"   <td> " + presupuesto.getId() + "</td>" + 
				"   <td><a href = \"/compras_pendientes/" + this.getId() + "/presupuestos/" + presupuesto.getId() + "\"> Ver Presupuesto </a></th>" +
				"   <td><a href = \"/compras_pendientes/" + this.getId() + "/presupuestos/" + presupuesto.getId() + "/borrar\"> Borrar Presupuesto </a></th>" +
				"</tr>";
			});
		return tabla + "</table>";
	}

	public void editarMedioPago(String identificadorMedioPago, TipoPago tipoPago) {
		this.medioPago.setTipoPago(tipoPago);
		this.medioPago.setIdentificador(identificadorMedioPago);
	}

	public void quitarItem(Item item) {
		detalle.quitarItem(item);
	}

	public void quitarPresupuesto(Presupuesto presupuesto) {
		presupuestos.remove(presupuesto);		
	}

	public boolean tienePresupuestos() {
		return !presupuestos.isEmpty();
	}

	public boolean tieneUsuariosRevisores() {
		return !usuariosRevisores.isEmpty();
	}

	public double getCostoTotal() {
		return detalle.getTotal();
	}
}