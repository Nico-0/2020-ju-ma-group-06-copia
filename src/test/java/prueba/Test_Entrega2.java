package prueba;

import dominio.compra.*;
import dominio.presupuestos.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Assert;

public class Test_Entrega2 {

    private APImercado requester;
	private CompraPendiente compraSinPresupuestos, compraBarata;
	private PersonaProveedor proveedor, PROVEEDOR_CARO, PROVEEDOR_BARATO;
	private Presupuesto presupuesto, PRESUPUESTO_BARATO, PRESUPUESTO_CARO;

	@Before
    public void setUp() throws Exception {
        this.requester = new APImercado();
		compraSinPresupuestos = new CompraPendiente();
		compraBarata = new CompraPendiente();
		CompraPendiente.setCantidadPresupuestosRequeridos(3);
		proveedor = new PersonaProveedor("juancito", 45127845, "Avenida Siempreviva 123");
		PROVEEDOR_BARATO = new PersonaProveedor("pancho", 59364958, "Av General Paz 1560");
		PROVEEDOR_CARO = new PersonaProveedor("mirtha legrand", 12345678, "As� no, as� no");
		PRESUPUESTO_BARATO = new Presupuesto(compraBarata, PROVEEDOR_BARATO)
								.agregarItem(new Item("El Mono Liso",2,1));
		PRESUPUESTO_CARO = new Presupuesto(compraBarata, PROVEEDOR_CARO)
								.agregarItem(new Item("Una ferrari",10000000,1));
		compraBarata.setCriterioDeSeleccion(new PresupuestoMasBarato());
	}

    @Test
    public void obtengoLaMonedaDelPais() throws Exception {
        String moneda = requester.obtenerMonedaPais("AR");
        assertEquals(moneda, "ARS");
    }
	
    @Test
    public void DolarValeMasDe60() throws Exception {
        Double valor = requester.convertirMoneda(1, "USD", "ARS");
        assertTrue("Dolar vale mas de 60", valor > 60);
    }
	
    @Test
    public void zip5000QuedaEnCordoba() throws Exception {
        String provincia = requester.obtenerProvinciaDeZip("5000");
        assertEquals(provincia, "Córdoba");
    }
    
	
	@Test(expected=CantidadPresupuestosIncorrectaException.class)
	public void noHaySuficientesPresupuestos() {
		compraSinPresupuestos.verificarCantidadPresupuestos();
	}

	@Test
	public void haySuficientesPresupuestos() {
		new Presupuesto(compraSinPresupuestos, proveedor);
		new Presupuesto(compraSinPresupuestos, proveedor);
		new Presupuesto(compraSinPresupuestos, proveedor);
		compraSinPresupuestos.verificarCantidadPresupuestos();
	}
	
	@Test
	public void compraEstaBasadaEnPresupuesto() {
		Presupuesto unPresupuesto = new Presupuesto(compraSinPresupuestos, proveedor);
		unPresupuesto.agregarItem(new Item("Turron",15000,1));
		compraSinPresupuestos.agregarItem(new Item("Turron",15000,1))
			.setProveedor(proveedor);
		compraSinPresupuestos.verificarDetallePresupuesto();
	}
	
	@Test(expected=PresupuestoNoCoincideException.class)
	public void presupuestoTieneMasItemsQueCompra() {
		Presupuesto unPresupuesto = new Presupuesto(compraSinPresupuestos, proveedor);
		unPresupuesto.agregarItem(new Item("Turron",30,2))
			.agregarItem(new Item("Alfajor",300,12));
		compraSinPresupuestos.agregarItem(new Item("Turron",30,2))
			.setProveedor(proveedor);
		compraSinPresupuestos.verificarDetallePresupuesto();
	}
	
	@Test(expected=PresupuestoNoCoincideException.class)
	public void compraTieneMasItemsQuePresupuesto() {
		Presupuesto unPresupuesto = new Presupuesto(compraSinPresupuestos, proveedor);
		unPresupuesto.agregarItem(new Item("Turron",30,2));
		compraSinPresupuestos.agregarItem(new Item("Turron",30,2))
			.agregarItem(new Item("Alfajor",300,12))
			.setProveedor(proveedor);
		compraSinPresupuestos.verificarDetallePresupuesto();
	}
	
	@Test(expected=PresupuestoNoCoincideException.class)
	public void compraTieneItemsDistintosAlPresupuesto() {
		Presupuesto unPresupuesto = new Presupuesto(compraSinPresupuestos, proveedor);
		unPresupuesto.agregarItem(new Item("Alfajor",100,4));
		compraSinPresupuestos.agregarItem(new Item("Alfajor",300,12))
			.setProveedor(proveedor);
		compraSinPresupuestos.verificarDetallePresupuesto();
	}
	
	@Test
	public void proveedorSeleccionadoTienePresupuestoMasBarato() {
		compraBarata.setProveedor(PROVEEDOR_BARATO);
		compraBarata.verificarCriterioDeSeleccion();
	}
	
	@Test(expected=NoCumpleCriterioDeSeleccionException.class)
	public void proveedorSeleccionadoNoTienePresupuestoMasBarato() {
		compraBarata.setProveedor(PROVEEDOR_CARO);
		compraBarata.verificarCriterioDeSeleccion();
	}
	
	@Before
	
	
	@Test()
	
}









