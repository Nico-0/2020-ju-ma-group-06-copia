package dominio.entidad;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hsqldb.Table;

import dominio.compra.Compra;

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
	
	private LocalDate fecha;
	
	@Transient
	private GeneradorReporte reporte;
	
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
		categorias.add(categoria);
	}
	
	public void quitarCategoria(Categoria categoria) {
		categorias.remove(categoria);
	}
	
	public String getUrlDelete() {
		return "/entidades/" + id.toString() + "/delete";
	}
	
	public abstract String getUrlView();

    public long getId(){
    	  return this.id;
     } 
}
