package repositorios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.entidad.Categoria;
import dominio.presupuestos.CompraPendiente;

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
    
	/*public static void main(String Args[]) {
		
	}*/
	
	public void agregar(CompraPendiente cp) {
		entityManager().persist(cp);
	}
	
	public List<CompraPendiente> todas() {
		return entityManager().createQuery("from CompraPendiente").getResultList();
	}
	
    public void validarCompras() {
    	this.comprasPendientes = this.todas();
    	/*
    	List<CompraPendiente> comprasValidas = comprasPendientes.stream().filter(CompraPendiente::verificarQueEsValida).collect(toList());
    	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa se validan "+comprasValidas.size()+" compras");
    	comprasValidas.stream().forEach(CompraPendiente::validarCompra);
       	
       	this.comprasPendientes.removeIf(pendiente -> comprasValidas.contains(pendiente));
       	
       	comprasValidas.stream().forEach(compraValida -> entityManager().remove(compraValida));
       	*/
    	
    	//TODO volver al codigo comentado de arriba
    	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa se validan "+this.comprasPendientes.size()+" compras");
    	this.comprasPendientes.stream().forEach(CompraPendiente::validarCompra);
    	
    }

	public void borrarCompraPendiente(Long id) {
		CompraPendiente compraPendiente = this.getCompraPendiente(id);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(compraPendiente);
		transaction.commit();
	}

	private CompraPendiente getCompraPendiente(Long id) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return (CompraPendiente) entityManager
				.createQuery("from CompraPendiente where id = :id")
				.setParameter("id", id)
				.getSingleResult();
	}

	public CompraPendiente crearCompraPendiente() {
		CompraPendiente compraPendiente = new CompraPendiente();
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(compraPendiente);
		transaction.commit();
		return compraPendiente;
	}
    
}
