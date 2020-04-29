package prueba;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dominio.DummyDesign;

public class TestDummyDesign {

	public DummyDesign unObjeto;
	
	@Before
	public void initialize() {
		unObjeto = new DummyDesign();
	}
	
	@Test
	public void testIntegrante1() {
		
		Assert.assertEquals(1, unObjeto.integrante1());
	}

}
