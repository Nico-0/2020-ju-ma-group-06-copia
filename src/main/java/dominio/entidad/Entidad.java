package dominio.entidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Compra;
import dominio.presupuestos.CompraPendiente;
import repositorios.RepositorioCategorias;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Entidad {
	
	@Id
	@GeneratedValue
	protected Long id;
	
	@OneToMany
	public List<Compra> compras = new ArrayList<Compra>();
	
	protected String nombreFicticio;
	
	
	@ManyToMany
	public List<Categoria> categorias = new ArrayList<Categoria>();
	
	private LocalDate fecha = LocalDate.now();
	
	@Transient
	private GeneradorReporte reporte;
	
	@Transient
	String tabla;
	
	public String getNombre() {
		return nombreFicticio;
	}
	
	public abstract String getTipo();
	
	public List<Compra> getCompras() {
		return compras;
	}

	public void agregarCompra(Compra unaCompra) {
		categorias.stream().forEach(categoria -> categoria.validarAgregarCompra(this, unaCompra));
		compras.add(unaCompra);
	}

	public boolean egresosSuperan(double valor){
		return valor < this.egresosTotales();
	}

	public double egresosTotales() {
		return compras.stream().mapToDouble(compra -> compra.valor_total()).sum();
	}

	public HashMap<String, List<Compra>> generarReporte(){
		reporte = new GeneradorReporte();
		return reporte.generarReporte(compras);
	}

	public void agregarCategoria(Categoria categoria) {
		//final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		//final EntityTransaction transaction = entityManager.getTransaction();
		//transaction.begin();
		categorias.add(categoria);
		//transaction.commit();
	}
	
	public void quitarCategoria(Categoria categoria) {
		//final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		//final EntityTransaction transaction = entityManager.getTransaction();
		//transaction.begin();
		categorias.remove(categoria);
		//transaction.commit();
	}
	
	public String getUrlDelete() {
		return "/entidades/" + id.toString() + "/delete";
	}
	
	public abstract String getUrlView();

    public long getId(){
    	  return this.id;
     }
    
    public List<Categoria> getCategorias() {
    	return categorias;
    }
    
    public String getFecha() {
    	return fecha.toString();
    }
    
    public String getTablaCategorias() {
    	tabla = "<table>" + 
    			"    	<tr>" + 
    			"            <th> Id </th>" + 
    			"            <th> Nombre </th>" + 
    			"            <th></th>" + 
    			"            <th></th>" + 
    			"        </tr>";
    	categorias.stream().forEach((categoria) -> {tabla = tabla + 
			    			"<tr>" + 
			    			"   <td> " + categoria.getId() + "</td>" + 
			    			"   <td> " + categoria.getNombre() + "</td>" + 
			    			"   <td><a href = " + categoria.getUrlView() + ">Ver</a></th>" +
			    			"</tr>";});
    	return tabla;
    }
    
    public String getTablaCategoriasEditar() {
    	List<Categoria> todasLasCategorias = RepositorioCategorias.getInstance().getCategorias(); 
    	tabla = "<table>" + 
    			"    	<tr>" + 
    			"            <th> Id </th>" + 
    			"            <th> Nombre </th>" + 
    			"            <th></th>" + 
    			"            <th></th>" + 
    			"            <th></th>" + 
    			"        </tr>";
    	todasLasCategorias.stream().forEach((categoria) -> {tabla = tabla + 
			    			"<tr>" + 
			    			"   <td> " + categoria.getId() + "</td>" + 
			    			"   <td> " + categoria.getNombre() + "</td>" + 
			    			"   <td><a href = " + categoria.getUrlView() + ">Ver</a></th>";
    						if(categorias.contains(categoria))
    							tabla = tabla +
    							"<td><a href = " + this.getUrlView() + "quitar_categoria/" + categoria.getId() + ">Quitar Categoria</a></th>" +
    					    	"</tr>";
    						else
    							tabla = tabla +
    							"<td><a href = " + this.getUrlView() + "agregar_categoria/" + categoria.getId() + ">Agregar Categoria</a></th>" +
    							"</tr>";	
			    			});
    	return tabla + "<table>";
    }
    
    public String getUrlEditarCategorias() {
    	return this.getUrlView() + "editar_categorias";
    }

	public boolean tieneCompras() {
		return !compras.isEmpty();
	}

	public abstract boolean perteneceAEntidadJuridica();

	public boolean tieneComprasPendientes() {	
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		List<CompraPendiente> comprasPendientes = entityManager.createQuery("select compraPendiente "
				+ "from CompraPendiente as compraPendiente "
				+ "left outer join compraPendiente.entidad as entidadBuscada "
				+ "where entidadBuscada.id="
				+ String.valueOf(this.id)).getResultList();
		return !comprasPendientes.isEmpty();
	}

	public abstract boolean tieneEntidadesBase();
}
