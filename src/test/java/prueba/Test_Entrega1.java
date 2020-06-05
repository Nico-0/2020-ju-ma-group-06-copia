package prueba;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
import dominio.usuario.Usuario;
import dominio.usuario.ValidadorDeContrasenias;
import dominio.validacion.ContieneNombreUsuarioException;
import dominio.validacion.EsMalaException;
import dominio.validacion.EsMuyCortaException;
import dominio.validacion.RepiteCaracteresException;


public class Test_Entrega1 {

	public Entidad entidad;
	
	public String razonSocial = "razonSocial";
	String cuit= "cuit";
	String direccionPostal = "direccionPostal";
	String nombreFicticio = "nombreFicticio";
	List<EntidadBase> entidades = new ArrayList<EntidadBase>();
	
	public PersonaProveedor proveedor;
	public MedioPago medioPago;
	public List<Item> items = new ArrayList<Item>();
	public Item item;
	public Usuario usuario;
	ValidadorDeContrasenias validador;
	
	@Before
	public void initialize() throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		entidad = new OrganizacionSocial(razonSocial, nombreFicticio, cuit, direccionPostal, entidades);
		proveedor = new PersonaProveedor("juancito", 45127845, "Avenida Siempreviva 123");
		medioPago = new MedioPago(TipoPago.EFECTIVO, "Identificador");
		item = new Item("cuaderno", 500, 2);
		validador = new ValidadorDeContrasenias();
		usuario = new Usuario("Carlitos","ContraseniaVerdadera");
	}
	
	@Test
	public void entidadCompraYquedaRegistrado() throws PreconditionFailed{
		int size_compras = entidad.compras.size();
		items.add(item);
		entidad.realizarCompra(items, proveedor, medioPago);
		
		Assert.assertEquals("Error al agregar compra", size_compras + 1, entidad.compras.size());
	}
	
	@Test(expected=EsMalaException.class)
	public void laContraseniaEsUnaDeLas10000Peores() {
		validador.validarContrasenia("1234","Nombre de Usuario");
	}
	
	@Test(expected=EsMuyCortaException.class)
	public void laContraseniaEsCorta() {
		validador.validarContrasenia("Holanda","Nombre de Usuario");
	}
	
	@Test(expected=RepiteCaracteresException.class)
	public void laContraseniaRepiteCaracteres() {
		validador.validarContrasenia("zaaazaaazaaa","Nombre de Usuario");
	}
	
	@Test(expected=ContieneNombreUsuarioException.class)
	public void laContraseniaContieneElNombreDeUsuario() {
		validador.validarContrasenia("nombreUsuario","nombreUsuario");
	}
	
	@Test
	public void laContraseniaEsCorrecta() throws NoSuchAlgorithmException, InvalidKeySpecException {
		Assert.assertTrue(usuario.laContraseniaEs("ContraseniaVerdadera"));
	}
	
	@Test
	public void laContraseniaEsIncorrecta() throws NoSuchAlgorithmException, InvalidKeySpecException {
		Assert.assertFalse(usuario.laContraseniaEs("ContraseniaEquivocada"));
	}
}
