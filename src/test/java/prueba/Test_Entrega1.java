package prueba;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dominio.Compra;
import dominio.DocumentoComercial;
import dominio.Entidad;
import dominio.EntidadBase;
import dominio.EntidadJuridica;
import dominio.Item;
import dominio.MedioPago;
import dominio.OrganizacionSocial;
import dominio.PersonaProveedor;
import dominio.PreconditionFailed;
import dominio.TipoPago;


public class Test_Entrega1 {

	public EntidadJuridica entidad;
	
	public String razonSocial = "razonSocial";
	String cuit= "cuit";
	String direccionPostal = "direccionPostal";
	String nombreFicticio = "nombreFicticio";
	
	public PersonaProveedor proveedor;
	public MedioPago medioPago;
	public List<Item> items = new ArrayList<Item>();
	public Item item;
	
	
	@Before
	public void initialize() throws PreconditionFailed {
		entidad = new OrganizacionSocial(razonSocial, nombreFicticio, cuit, direccionPostal);
		proveedor = new PersonaProveedor("juancito", 45127845, "Avenida Siempreviva 123");
		medioPago = new MedioPago(TipoPago.EFECTIVO, "Identificador");
		item = new Item("cuaderno", 500, 2);
				
	}
	
	@Test
	public void entidadCompraYquedaRegistrado() throws PreconditionFailed{
		int size_compras = entidad.compras.size();
		items.add(item);
		entidad.realizarCompra(items, proveedor, medioPago);
		
		Assert.assertEquals("Error al agregar compra", size_compras + 1, entidad.compras.size());
	}
}
