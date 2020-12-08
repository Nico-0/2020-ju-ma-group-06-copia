package repositorios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.compra.MedioPago;
import dominio.compra.TipoPago;
import dominio.entidad.Categoria;
import dominio.presupuestos.CompraPendiente;
import dominio.usuario.Usuario;

import static java.util.stream.Collectors.toList;


public class RepositorioComprasPendientes implements WithGlobalEntityManager{
	private static RepositorioComprasPendientes INSTANCE = null;
	private String tabla;
	Usuario usuarioLogueado;
	
	public static RepositorioComprasPendientes getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new RepositorioComprasPendientes();
		}
		return INSTANCE;
	}
	
	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}
	
	public List<CompraPendiente> getComprasPendientes() {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return entityManager.createQuery("from CompraPendiente").getResultList();
	}
	
	/*
    public void validarCompras() {
    	List<CompraPendiente> comprasValidas = comprasPendientes.stream().filter(CompraPendiente::verificarQueEsValida).collect(toList());
    	comprasValidas.stream().forEach(CompraPendiente::validarCompra);
       	
       	this.comprasPendientes.removeIf(pendiente -> comprasValidas.contains(pendiente));
    }
    */
	
	public void agregar(CompraPendiente cp) {
		entityManager().persist(cp);
	}
	
	public List<CompraPendiente> todas() {
		return entityManager().createQuery("from CompraPendiente").getResultList();
	}
	
    public void validarCompras() {
    	List<CompraPendiente> comprasPendientes = RepositorioComprasPendientes.getInstance().getComprasPendientes(); 
    	
    	/*
    	List<CompraPendiente> comprasValidas = comprasPendientes.stream().filter(CompraPendiente::verificarQueEsValida).collect(toList());
    	
    	comprasValidas.stream().forEach(CompraPendiente::validarCompra);
       	
       	this.comprasPendientes.removeIf(pendiente -> comprasValidas.contains(pendiente));
       	
       	comprasValidas.stream().forEach(compraValida -> entityManager().remove(compraValida));
       	*/
    	
    	//TODO volver al codigo comentado de arriba
       	comprasPendientes.stream().forEach(CompraPendiente::validarCompra);
    	
    }

	public void borrarCompraPendiente(Long id) {
		CompraPendiente compraPendiente = this.getCompraPendiente(id);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(compraPendiente);
		transaction.commit();
	}

	public CompraPendiente getCompraPendiente(Long id) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return (CompraPendiente) entityManager
				.createQuery("from CompraPendiente where id = :id")
				.setParameter("id", id)
				.getSingleResult();
	}

	public CompraPendiente crearCompraPendiente() {
		MedioPago medioPago = new MedioPago(TipoPago.EFECTIVO,"");
		CompraPendiente compraPendiente = new CompraPendiente();
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(medioPago);
		entityManager.persist(compraPendiente);
		compraPendiente.setMedioPago(medioPago);
		transaction.commit();
		return compraPendiente;
	}
	
	public String getTablaComprasPendientes() {
    	List<CompraPendiente> todasLasComprasPendientes = RepositorioComprasPendientes.getInstance().getComprasPendientes(); 
    	tabla = "<table>" + 
    			"    	<tr>" + 
    			"            <th> Id Compra </th>" + 
    			"            <th></th>" + 
    			"            <th></th>" + 
    			"            <th></th>" + 
    			"        </tr>";
    	todasLasComprasPendientes.stream().forEach((compraPendiente) -> {tabla = tabla + 
			    			"<tr>" + 
			    			"   <td> " + compraPendiente.getId() + "</td>";
    						if(compraPendiente.estaSuscrito(usuarioLogueado))
    							tabla = tabla +
    							"<td><a href = " + compraPendiente.getUrlView() + "/usuarios/" + usuarioLogueado.getId() + "/desuscribir>Desuscribirse</a></th>";
    						else
    							tabla = tabla +
    							"<td><a href = " + compraPendiente.getUrlView() + "/usuarios/" + usuarioLogueado.getId() + "/suscribir>Suscribirse</a></th>";
			    			tabla = tabla +
					    			"   <td><a href = " + compraPendiente.getUrlView() + ">Ver</a></td>" + 
					    			"   <td><a href = " + compraPendiente.getUrlBorrar() + ">Borrar</a></th>" +
					    			"</tr>";
    						});
    	return tabla + "<table>";
    }    
}
