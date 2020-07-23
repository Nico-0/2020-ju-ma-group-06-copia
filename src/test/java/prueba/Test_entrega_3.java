package prueba;

import static org.junit.Assert.*;

import java.time.LocalDate;
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
	private LocalDate unaFecha;
	private Presupuesto presupuesto, PRESUPUESTO_BARATO, PRESUPUESTO_CARO;
	private List<Presupuesto> listaPresupuestos;
	private List<Usuario> usuariosRevisores;
	private List<Compra> listaDeCompras;
	private String etiquetaAmoblamiento;
	private String etiquetaIndumentaria;
	private Detalle detalle;
	private DireccionPostal direccionPostal;
	private Proveedor proveedor;
	private Reporte unReporte;
	Compra compraAmoblamientoUno;
	Compra compraAmoblamientoDos;
	Compra compraInodumentariaUno;
	Set<String> etiquetas;
	
	@Before
	public void setUp() throws Exception {
		unMedioDePago = new MedioPago(TipoPago.EFECTIVO,"2193829183928");
		otroMedioDePago = new MedioPago(TipoPago.TARJETA_CREDITO,"2193829183928");
		unaFecha = LocalDate.now();
		etiquetaAmoblamiento = "Amoblamiento";
		etiquetaIndumentaria = "Indumentaria";
		direccionPostal = new DireccionPostal();
		proveedor = new Proveedor("juancito", "45127845", direccionPostal);
		unReporte = new Reporte();
		listaPresupuestos.add(presupuesto);
		listaPresupuestos.add(PRESUPUESTO_BARATO);
		listaPresupuestos.add(PRESUPUESTO_CARO);
		compraAmoblamientoUno = new Compra(proveedor ,unMedioDePago, unaFecha, listaPresupuestos, detalle, usuariosRevisores, etiquetaAmoblamiento);
		compraAmoblamientoDos = new Compra(proveedor, otroMedioDePago, unaFecha, listaPresupuestos, detalle, usuariosRevisores, etiquetaAmoblamiento);
		compraInodumentariaUno = new Compra(proveedor ,unMedioDePago, unaFecha, listaPresupuestos, detalle, usuariosRevisores, etiquetaIndumentaria);
		listaDeCompras.add(compraAmoblamientoUno);
		listaDeCompras.add(compraAmoblamientoDos);
		listaDeCompras.add(compraInodumentariaUno);
		etiquetas = new HashSet<String>();
		etiquetas.add("Amoblamiento");
		etiquetas.add("Indumentaria");
		
	}
	
	@Test
	public void testReporteDevuelveEtiquetas() {
		Set<String> etiquetasReporte = unReporte.etiquetas(listaDeCompras);
		assertEquals(etiquetasReporte, etiquetas);
	}
	
	
}

