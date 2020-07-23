package prueba;

import static org.junit.Assert.*;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.Before;

import dominio.presupuestos.Detalle;
import dominio.presupuestos.Presupuesto;
import dominio.usuario.Usuario;
import dominio.compra.*;
import dominio.entidad.*;

public class Test_entrega_3 {
	private MedioPago unMedioDePago;
	private MedioPago otroMedioDePago;
	private LocalDate fechaJulio, otraFechaJulio, fechaEnero;
	private Presupuesto presupuesto, PRESUPUESTO_BARATO, PRESUPUESTO_CARO;
	private List<Presupuesto> listaPresupuestos = new ArrayList<>();
	private List<Usuario> usuariosRevisores = new ArrayList<>();
	private List<Compra> listaDeCompras = new ArrayList<>();
	private String etiquetaAmoblamiento;
	private String etiquetaIndumentaria;
	private Detalle detalle = new Detalle();
	private DireccionPostal direccionPostal;
	private Proveedor proveedor;
	private Reporte unReporte;
	private Compra compraAmoblamientoUno, compraAmoblamientoDos, compraIndumentariaUno;
	private Set<String> etiquetas = new HashSet<String>();
	private Categoria unaCategoria = new Categoria();
	private EntidadBase unaEntidadBase;
	private List<EntidadBase> listaEntidadesBase = new ArrayList<>();
	private EntidadJuridica unaEntidadJuridica;
	private Item tresCamas;
	
	
	
	@Before
	public void setUp() throws Exception {
		unMedioDePago = new MedioPago(TipoPago.EFECTIVO,"2193829183928");
		otroMedioDePago = new MedioPago(TipoPago.TARJETA_CREDITO,"2193829183928");
		fechaJulio = LocalDate.of( 2020, Month.JULY, 9 );
		otraFechaJulio = LocalDate.of( 2020, Month.JULY, 12 );
		fechaEnero = LocalDate.of( 2020, Month.JANUARY, 23 );
		etiquetaAmoblamiento = "Amoblamiento";
		etiquetaIndumentaria = "Indumentaria";
		direccionPostal = new DireccionPostal();
		proveedor = new Proveedor("juancito", "45127845", direccionPostal);
		unReporte = new Reporte();
		listaPresupuestos.add(presupuesto);
		listaPresupuestos.add(PRESUPUESTO_BARATO);
		listaPresupuestos.add(PRESUPUESTO_CARO);
		compraAmoblamientoUno = new Compra(proveedor ,unMedioDePago, fechaJulio, listaPresupuestos, detalle, usuariosRevisores, etiquetaAmoblamiento);
		compraAmoblamientoDos = new Compra(proveedor, otroMedioDePago, fechaEnero, listaPresupuestos, detalle, usuariosRevisores, etiquetaAmoblamiento);
		compraIndumentariaUno = new Compra(proveedor ,unMedioDePago, otraFechaJulio, listaPresupuestos, detalle, usuariosRevisores, etiquetaIndumentaria);
		listaDeCompras.add(compraAmoblamientoUno);
		listaDeCompras.add(compraAmoblamientoDos);
		listaDeCompras.add(compraIndumentariaUno);
		etiquetas.add("Amoblamiento");
		etiquetas.add("Indumentaria");
		unaEntidadBase = new EntidadBase("Empresa 123", "Es una entidad base", unaCategoria);
		unaEntidadJuridica = new Empresa("La Entidad", "La Entidad Ficticia", "2913J923", "OD2N9D", TipoEmpresa.MICRO, listaEntidadesBase, unaCategoria);
		tresCamas = new Item("Cama", 3, 2500);
		detalle.agregarItem(tresCamas);
		
	}
	
	@Test
	public void testReporteDevuelveEtiquetas() {
		final Set<String> etiquetasReporte = new Reporte().etiquetas(listaDeCompras);
		assertEquals(etiquetasReporte, etiquetas);
	}
	
	@Test
	public void testSonDelMes() {
		List<Compra> comprasDelMes = new ArrayList<>();
		comprasDelMes = unReporte.sonDelMes(listaDeCompras);
		List<Compra> comprasMesActual = new ArrayList<>();
		comprasMesActual.add(compraAmoblamientoUno);
		comprasMesActual.add(compraIndumentariaUno);
		assertEquals(comprasMesActual,comprasDelMes);
	}
	
	@Test
	public void categoraNoPuedeAgregarCompraSiTieneNuevosEgresosBloqueados() throws RuntimeException{
		unaCategoria.setBloquearNuevosEgresos(true);
		unaCategoria.agregarCompra(unaEntidadBase, compraIndumentariaUno);
	}
	
	@Test
	public void noPuedeAgregarEntidadBase() throws RuntimeException{
		unaCategoria.setBloquarAgregarEntidadesBase(false);
		unaCategoria.agregarEntidadBase(unaEntidadJuridica, unaEntidadBase);
	}
	
	@Test
	public void noPuedeAgregarEntidadJuridica() throws RuntimeException{
		unaCategoria.setBloquearFormarParteEntidadJuridica(false);
		unaCategoria.agregarEntidadBase(unaEntidadJuridica, unaEntidadBase);
	}
	
	@Test
	public void noPuedeAgregarCompraSiExcedeLosEgresosMaximos() throws RuntimeException{
		unaCategoria.setEgresosMaximos(1000);
		unaCategoria.agregarCompra(unaEntidadBase, compraAmoblamientoUno);
	}
	
	@Test
	public void puedeAgregarCompraSiNoExcedeLosEgresosMaximos(){
		unaCategoria.setEgresosMaximos(10000);
		unaCategoria.agregarCompra(unaEntidadBase, compraAmoblamientoUno);
	}
	
}








