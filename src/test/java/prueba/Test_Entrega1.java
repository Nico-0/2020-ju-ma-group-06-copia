package prueba;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dominio.PreconditionFailed;
import dominio.compra.Compra;
import dominio.compra.DocumentoComercial;
import dominio.compra.Item;
import dominio.compra.MedioPago;
import dominio.compra.PersonaProveedor;
import dominio.compra.TipoPago;
import dominio.entidad.Entidad;
import dominio.entidad.EntidadBase;
import dominio.entidad.OrganizacionSocial;
import dominio.usuario.ValidadorDeContrasenias;


public class Test_Entrega1 {

	public Entidad entidad;
	
	public String razonSocial = "razonSocial";
	String cuit= "cuit";
	String direccionPostal = "direccionPostal";
	String nombreFicticio = "nombreFicticio";
	
	public PersonaProveedor proveedor;
	public MedioPago medioPago;
	public List<Item> items = new ArrayList<Item>();
	public Item item;
	ValidadorDeContrasenias validador;
	
	@Before
	public void initialize() throws PreconditionFailed, FileNotFoundException {
		entidad = new OrganizacionSocial(razonSocial, nombreFicticio, cuit, direccionPostal);
		proveedor = new PersonaProveedor("juancito", 45127845, "Avenida Siempreviva 123");
		medioPago = new MedioPago(TipoPago.EFECTIVO, "Identificador");
		item = new Item("cuaderno", 500, 2);
		validador = new ValidadorDeContrasenias();
	}
	
	@Test
	public void entidadCompraYquedaRegistrado() throws PreconditionFailed{
		int size_compras = entidad.compras.size();
		items.add(item);
		entidad.realizarCompra(items, proveedor, medioPago);
		
		Assert.assertEquals("Error al agregar compra", size_compras + 1, entidad.compras.size());
	}
	
	@Test
	public void laContraseniaEsUnaDeLas10000Peores() {
		String unaContrasenia = "1234";
		
		Assert.assertTrue(validador.peoresContrasenias.contains(unaContrasenia));
	}
	
}
