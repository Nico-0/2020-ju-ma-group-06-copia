package dominio.entidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dominio.PreconditionFailed;
import dominio.compra.Compra;
import dominio.compra.Item;
import dominio.compra.MedioPago;
import dominio.compra.Proveedor;

public abstract class Entidad {
	public List<Compra> compras = new ArrayList<Compra>();
	protected String nombreFicticio;

	public void realizarCompra(List<Item> items, Proveedor proveedor, MedioPago medioPago) throws PreconditionFailed {
		LocalDate fecha = LocalDate.now();
		Compra miCompra = new Compra(proveedor, medioPago, fecha);
		miCompra.setItems(items);
		compras.add(miCompra);
	}

}
