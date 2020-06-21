package dominio.entidad;

import java.util.ArrayList;
import java.util.List;

import dominio.compra.Compra;

public abstract class Entidad {
	public List<Compra> compras = new ArrayList<Compra>();
	protected String nombreFicticio;
	private List<EntidadBase> entidades_usadas = new ArrayList<EntidadBase>();
	
	public void agregarCompra(Compra unaCompra) {
		compras.add(unaCompra);
	}
	

}
