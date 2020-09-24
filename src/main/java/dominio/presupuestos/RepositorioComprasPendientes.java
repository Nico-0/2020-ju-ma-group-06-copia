package dominio.presupuestos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import static java.util.stream.Collectors.toList;


public class RepositorioComprasPendientes implements WithGlobalEntityManager{
	private static RepositorioComprasPendientes INSTANCE = null;
	List<CompraPendiente> comprasPendientes = new ArrayList<>();
	
	public static RepositorioComprasPendientes getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new RepositorioComprasPendientes();
		}
		return INSTANCE;
	}
	
	/*	---------------------prueba
	public List<CompraPendiente> todas() {
		return this.comprasPendientes;
	}
	
    public void validarCompras() {
    	List<CompraPendiente> comprasValidas = comprasPendientes.stream().filter(CompraPendiente::verificarQueEsValida).collect(toList());
    	comprasValidas.stream().forEach(CompraPendiente::validarCompra);
       	
       	this.comprasPendientes.removeIf(pendiente -> comprasValidas.contains(pendiente));
    }
    */
    
	public static void main(String Args[]) {
		
	}
	
	public void agregar(CompraPendiente cp) {
		entityManager().persist(cp);
	}
	
	public List<CompraPendiente> todas() {
		return entityManager().createQuery("from comprapendiente").getResultList();
	}
	
    public void validarCompras() {
    	this.comprasPendientes = this.todas();
    	List<CompraPendiente> comprasValidas = comprasPendientes.stream().filter(CompraPendiente::verificarQueEsValida).collect(toList());
    	comprasValidas.stream().forEach(CompraPendiente::validarCompra);
       	
       	this.comprasPendientes.removeIf(pendiente -> comprasValidas.contains(pendiente));
       	
       	//TODO borrar todas las filas de la tabla comprasPendientes y volver a cargar solo las que faltan validar
    }
    
}
