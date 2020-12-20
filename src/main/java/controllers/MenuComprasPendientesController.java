package controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.MedioPago;
import dominio.compra.TipoPago;
import dominio.presupuestos.CompraPendiente;
import dominio.usuario.Usuario;
import repositorios.RepositorioComprasPendientes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MenuComprasPendientesController {

	public ModelAndView show(Request req, Response res){
		String nombreUsuario = req.cookie("usuario_logueado");
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Usuario usuario = (Usuario) entityManager
				.createQuery("from Usuario where nombre = :nombre")
				.setParameter("nombre", nombreUsuario)
				.getSingleResult();
		RepositorioComprasPendientes.getInstance().setUsuarioLogueado(usuario);
		return new ModelAndView(RepositorioComprasPendientes.getInstance(), "menuComprasPendientes.hbs");
	}
	
	
	
	public ModelAndView suscribirUsuario(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		Long idUsuario = new Long(req.params("id_usuario"));
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Usuario usuario = (Usuario) entityManager
				.createQuery("from Usuario where id = :id")
				.setParameter("id", idUsuario)
				.getSingleResult();
		CompraPendiente compraPendiente = (CompraPendiente) entityManager
				.createQuery("from CompraPendiente where id = :id")
				.setParameter("id", idCompraPendiente)
				.getSingleResult();
		transaction.begin();
		compraPendiente.agregarUsuarioRevisor(usuario);	
		transaction.commit();
		res.redirect("/compras_pendientes");
		return null;
	}
	
	public ModelAndView desuscribirUsuario(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		Long idUsuario = new Long(req.params("id_usuario"));
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Usuario usuario = (Usuario) entityManager
				.createQuery("from Usuario where id = :id")
				.setParameter("id", idUsuario)
				.getSingleResult();
		CompraPendiente compraPendiente = (CompraPendiente) entityManager
				.createQuery("from CompraPendiente where id = :id")
				.setParameter("id", idCompraPendiente)
				.getSingleResult();
		transaction.begin();
		compraPendiente.quitarUsuarioRevisor(usuario);
		transaction.commit();
		res.redirect("/compras_pendientes");
		return null;
	}
	
	public Void crearCompra(Request req, Response res){		
		MedioPago medioPago = new MedioPago(TipoPago.EFECTIVO,"");
		CompraPendiente compraPendiente = new CompraPendiente();
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(medioPago);
		entityManager.persist(compraPendiente);
		compraPendiente.setMedioPago(medioPago);
		transaction.commit();
		res.redirect("/compras_pendientes/" + compraPendiente.getId());
		return null;
	}
	
	public ModelAndView validar_compras(Request req, Response res){	
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		List<CompraPendiente> comprasPendientes = entityManager.createQuery("from CompraPendiente").getResultList();    	
		transaction.begin();
		comprasPendientes.stream().forEach(compraPendiente -> compraPendiente.validarCompra());
		transaction.commit();
		res.redirect("/compras_pendientes");
		return null;
	}
}
