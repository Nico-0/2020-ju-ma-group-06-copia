package dominio.entidad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Convert;
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

import converter.LocalDateTimeConverter;
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
	
	@Column
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime fecha = LocalDateTime.now();
	
	@OneToMany
	private List<Reporte> reportes = new ArrayList<Reporte>();
	
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
	
	public boolean sePuedeAgregarCompra(CompraPendiente unaCompra) {
		return categorias.stream().allMatch(categoria -> categoria.sePuedeAgregarCompra(this, unaCompra));
	}

	public boolean egresosSuperan(double valor){
		return valor < this.egresosTotales();
	}

	public double egresosTotales() {
		return compras.stream().mapToDouble(compra -> compra.valor_total()).sum();
	}

	/*
	public HashMap<String, List<Compra>> generarReporte(){
		return new GeneradorReporte().generarReporte(compras);
	}
	*/
	
	public void agregarReporte(Reporte reporte) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		List<Reporte> reportesViejos = reportes.stream().filter(unReporte -> unReporte.tieneMismaFecha(reporte)).collect(Collectors.toList());
		reportesViejos.forEach(unReporte -> {
				unReporte.getComprasPorEtiqueta()
					.forEach(listaDeCompras -> {
						listaDeCompras.quitarTodasLasCompras();
						entityManager.remove(listaDeCompras);
					});
				unReporte.getComprasPorEtiqueta().clear();
				reportes.remove(unReporte);
				entityManager.remove(unReporte);
			});
		reportes.add(reporte);
	}
	
	public Reporte generarReporte(){
		return new GeneradorReporte().generarReporte(compras);
	}
	
	public List<Reporte> getReportesMensuales() {
		return reportes;
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
    
    
    public String getTablaReportesMensuales() {
    	tabla = "<table>" + 
    			"    	<tr>" + 
    			"            <th></th>" + 
    			"            <th> Mes </th>" + 
    			"            <th> A&ntildeo </th>" + 
    			"        </tr>";
    	reportes.stream().forEach((reporte) -> {tabla = tabla + 
			"<tr>" + 
			"   <td><a href = " + this.getUrlView() + "reportes_mensuales/"+ reporte.getId() + ">Ver reporte</a></th>" +
			"   <td> " + reporte.getMes() + "</td>" + 
			"   <td> " + reporte.getAnio() + "</td>" + 
			
			"</tr>";});
    	return tabla + "</table>";
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
	
	public String getUrlReportesMensuales() {
		return this.getUrlView() + "reportes_mensuales";
	}
}
