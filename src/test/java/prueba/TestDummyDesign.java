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
	
	@Test
	public void testIntegrante2() {
		Assert.assertEquals(2, unObjeto.integrante2());
	}
	
	@Test
	public void testIntegrante3() {
		Assert.assertEquals(3,  unObjeto.integrante3());
	}
	
	@Test
	public void testIntegrante4() {
		Assert.assertEquals(4, unObjeto.integrante4());
	}
	
	@Test
	public void testIntegrante5() {
		
		Assert.assertEquals(5, unObjeto.integrante5());
	}

}
