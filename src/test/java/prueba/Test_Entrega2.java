package prueba;

import dominio.compra.APImercado;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Test_Entrega2 {

    private APImercado requester;

	@Before
    public void setUp() throws Exception {
        this.requester = new APImercado();
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
}
