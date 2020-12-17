package dominio.compra;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.Validate;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.Presupuesto;

@Entity
public class Proveedor {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String razon_social;
	private String dni_cuil_cuit;
	
	@OneToOne(cascade = {CascadeType.ALL})
	DireccionPostal direccion_postal;

	public Proveedor() {
		
	}
	
    public long getNumeroId(){
    	  return this.id;
      } 
	
	public Proveedor(String razonSocial, String dni_cuil_cuit, DireccionPostal direccion_postal) {
		Validate.notNull(razonSocial, "razon social faltante");
		Validate.notNull(dni_cuil_cuit, "dni/cuil/cuit faltante");
		Validate.notNull(direccion_postal, "direccion postal faltante");
		this.razon_social = razonSocial;
		this.dni_cuil_cuit = dni_cuil_cuit;
		this.direccion_postal = direccion_postal;
	}
	
	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public void setDni_cuil_cuit(String dni_cuil_cuit) {
		this.dni_cuil_cuit = dni_cuil_cuit;
	}

	public String getId() {
		return String.valueOf(id);
	}
	
	public String getRazonSocial() {
		return razon_social;
	}
	
	public String getDniCuilCuit() {
		return dni_cuil_cuit;
	}
	
	public DireccionPostal getDireccionPostal () {
		return direccion_postal;
	}
	
	public String getUrlDelete() {
		return "proveedores/" + getId() + "/borrar"; 
	}

	public boolean tieneCompras() {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		List<Compra> compras = entityManager.createQuery("select compra "
				+ "from Compra as compra "
				+ "left outer join compra.proveedor as proveedorBuscado "
				+ "where proveedorBuscado.id="
				+ String.valueOf(this.id)).getResultList();
		return !compras.isEmpty();
	}

	public boolean tienePresupuestos() {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		List<Presupuesto> presupuestos = entityManager.createQuery("select presupuesto "
				+ "from Presupuesto as presupuesto "
				+ "left outer join presupuesto.proveedor as proveedorBuscado "
				+ "where proveedorBuscado.id="
				+ String.valueOf(this.id)).getResultList();
		return !presupuestos.isEmpty();
	}
		
	public boolean tieneComprasPendientes() {	
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		List<CompraPendiente> comprasPendientes = entityManager.createQuery("select compraPendiente "
				+ "from CompraPendiente as compraPendiente "
				+ "left outer join compraPendiente.proveedor as proveedorBuscado "
				+ "where proveedorBuscado.id="
				+ String.valueOf(this.id)).getResultList();
		return !comprasPendientes.isEmpty();
	}


}
