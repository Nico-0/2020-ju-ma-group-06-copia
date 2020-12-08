package dominio.presupuestos;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import dominio.compra.*;
import dominio.entidad.Entidad;
import repositorios.RepositorioEntidades;
import repositorios.RepositorioProveedores;

@Entity
public class Presupuesto {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
    private Proveedor proveedor;
	
	@OneToOne
    private Detalle detalle = new Detalle();
    
	@OneToOne(optional = true)
	private DocumentoComercial documentoComercial;
	
	@Transient
	String tabla;
	
	Long idCompraPendiente;
	
	public void setIdCompraPendiente(Long idCompraPendiente) {
		this.idCompraPendiente = idCompraPendiente;
	}

    public Presupuesto(CompraPendiente compraPendiente, Proveedor proveedor) {
        compraPendiente.agregarPresupuesto(this);
        this.proveedor = proveedor;
    }
    
	public Presupuesto() { //rompe hibernate sin esto al crear item en la app
	}
    
    public void setProveedor(Proveedor proveedor) {
    	this.proveedor = proveedor;
    }
    
    public void setDetalle(Detalle detalle) {
    	this.detalle = detalle;
    }

    public void setDocumentoComercial(DocumentoComercial documentoComercial) {
    	this.documentoComercial = documentoComercial;
    }
    
    public Presupuesto agregarItem(Item unItem) {
    	detalle.agregarItem(unItem);
    	return this;
    }
    
    public Proveedor getProveedor() {
        return this.proveedor;
    }
    
    public Detalle getDetalle() {
        return this.detalle;
    }
    
    public String getDatosDocumentoComercial() { //si se pone mas de una mayuscula hibernate no lo detecta
        return documentoComercial.getDocumentoComercial();
    }
    
    public DocumentoComercial getDocumentoComercial() {
    	return documentoComercial;
    }

    public double getTotal() {
        return detalle.getTotal();
    }
    
    public boolean proveedorEs(Proveedor unProveedor) {
    	return proveedor == unProveedor;
    }
    public boolean tieneMismoDetalle(Detalle unDetalle) {
        return this.detalle.esIgualA(unDetalle);
    }
    
    public long getId(){
    	  return this.id;
      }

	public void quitarItem(Item item) {
		detalle.quitarItem(item);		
	}

	public void editarDocumentoComercial(int numeroDocumentoComercial,
			TipoDocumentoComercial tipoDocumentoComercial) {
		this.documentoComercial.setNumeroDocumento(numeroDocumentoComercial);
		this.documentoComercial.setTipoDocumentoComercial(tipoDocumentoComercial);
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
					"<a class = \"link_boton\" href = \"/compras_pendientes/" + idCompraPendiente + "/presupuestos/" + this.getId() + "/seleccionar_proveedor\">  Cambiar proveedor </a>";
		else
			return "<legend>PROVEEDOR</legend>" + 
					"Falta seleccionar un proveedor" + 
					"<a class = \"link_boton\" href = \"/compras_pendientes/" + idCompraPendiente + "/presupuestos/" + this.getId() + "/seleccionar_proveedor\">  Seleccionar proveedor </a>";

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
		List<Proveedor> proveedores = RepositorioProveedores.getInstance().getProveedores();

    	proveedores.stream().forEach((proveedor) -> {tabla = tabla + 
			    			"<tr>" + 
			    			"   <td> " + proveedor.getId() + "</td>" + 
			    			"   <td> " + proveedor.getRazonSocial() + "</td>" + 
			    			"   <td> " + proveedor.getDniCuilCuit() + "</td>" +
			    			"   <td> " + proveedor.getDireccionPostal().getPais() + "</td>" +
			    			"   <td> " + proveedor.getDireccionPostal().getProvincia() + "</td>" +
			    			"   <td> " + proveedor.getDireccionPostal().getCiudad() + "</td>" +
			    			"   <td> " + proveedor.getDireccionPostal().getDireccion() + "</td>" +
			    			"   <td> " + proveedor.getRazonSocial() + "</td>";
    	if(this.proveedor == proveedor)
    		tabla = tabla + "   <td> Proveedor seleccionado </th>" +
    				"</tr>";
    	else
    		tabla = tabla + "   <td><a href = \"/compras_pendientes/" + idCompraPendiente + "/presupuestos/" + this.getId() + "/seleccionar_proveedor/" + proveedor.getId() + "\"> Seleccionar </a></th>" +
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
			"   <td><a href = \"/compras_pendientes/" + idCompraPendiente + "/presupuestos/" + this.getId() + "/items/" + item.getId() + "/editar\"> Editar Item </a></th>" +
			"   <td><a href = \"/compras_pendientes/" + idCompraPendiente + "/presupuestos/" + this.getId() + "/items/" + item.getId() + "/quitar\"> Quitar Item </a></th>" +
			"</tr>";
		});
    	return tabla + "</table>";
	}
}