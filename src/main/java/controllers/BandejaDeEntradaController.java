package controllers;

import dominio.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;



public class BandejaDeEntradaController implements WithGlobalEntityManager{


	public ModelAndView bandejaDeEntrada(Request req, Response res){
		Usuario usuario = RepositorioUsuarios.getInstance().getUsuario(req.cookie("usuario_logueado"));
		return new ModelAndView(usuario, "bandeja_entrada.hbs");
	}
	
	public Void limpiar_bandeja(Request req, Response res){
		Usuario usuario = RepositorioUsuarios.getInstance().getUsuario(req.cookie("usuario_logueado"));
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		usuario.getBandejaDeMensajes().getMensajes().forEach(mensaje -> {
			entityManager.remove(mensaje);
		});
		usuario.getBandejaDeMensajes().getMensajes().clear();
		transaction.commit();

		res.redirect("/bandeja_de_entrada");
		return null;
	}
}
