package dominio.entidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dominio.compra.Compra;
import dominio.compra.Item;
import dominio.compra.MedioPago;
import dominio.compra.Proveedor;
import dominio.presupuestos.Detalle;
import dominio.presupuestos.Presupuesto;
import dominio.usuario.Usuario;

public abstract class Entidad {
	public List<Compra> compras = new ArrayList<Compra>();
	protected String nombreFicticio;
	private List<EntidadBase> entidades_usadas = new ArrayList<EntidadBase>();
	
	public void realizarCompra(Proveedor proveedor, 
			  				   MedioPago medioPago, 
			  				   List<Presupuesto> presupuestos,
			  				   Detalle detalle,
			  				   Usuario revisor) {
		LocalDate fecha = LocalDate.now();
		Compra miCompra = new Compra(proveedor, 
				   					 medioPago, 
				   					 fecha,
				   					 presupuestos,
				   					 detalle,
				   					 revisor);
		compras.add(miCompra);
	}

}
