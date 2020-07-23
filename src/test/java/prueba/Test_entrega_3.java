package prueba;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import dominio.entidad.Reporte;
import dominio.presupuestos.Detalle;
import dominio.presupuestos.Presupuesto;
import dominio.usuario.Usuario;
import dominio.compra.*;

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
	private Detalle detalle;
	private DireccionPostal direccionPostal;
	private Proveedor proveedor;
	private Reporte unReporte;
	Compra compraAmoblamientoUno;
	Compra compraAmoblamientoDos;
	Compra compraIndumentariaUno;
	Set<String> etiquetas = new HashSet<String>();
	
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
		
	}
	
	@Test
	public void testReporteDevuelveEtiquetas() {
		Set<String> etiquetasReporte = unReporte.etiquetas(listaDeCompras);
		assertEquals(etiquetasReporte, etiquetas);
	}
	
	@Test
	public void testSonDelMes() {
		List<Compra> comprasDelMes = new ArrayList<>();;
		comprasDelMes = unReporte.sonDelMes(listaDeCompras);
		List<Compra> comprasMesActual = new ArrayList<>();
		comprasMesActual.add(compraAmoblamientoUno);
		comprasMesActual.add(compraIndumentariaUno);
		assertEquals(comprasMesActual, comprasDelMes);
	}
	
}

