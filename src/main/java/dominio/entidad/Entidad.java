package dominio.entidad;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import dominio.compra.Compra;

public abstract class Entidad {
	public List<Compra> compras = new ArrayList<Compra>();
	protected String nombreFicticio;
	private List<EntidadBase> entidades_usadas = new ArrayList<EntidadBase>();
	public List<Categoria> categorias = new ArrayList<Categoria>();
	private LocalDate fecha;
	private Reporte reporte;
	
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
		reporte = new Reporte();
		return reporte.generarReporte(compras);
	}

	public void agregarCategoria(Categoria categoria) {
		categorias.add(categoria);
	}
	
	public void quitarCategoria(Categoria categoria) {
		categorias.remove(categoria);
	}
}
