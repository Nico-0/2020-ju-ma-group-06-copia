package controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {

	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "login.html");
	}
	
	public ModelAndView login(Request req, Response res) throws NoSuchAlgorithmException, InvalidKeySpecException{
		// Buscar usuario en la base de datos y verificar
		String nombre = req.queryParams("nombre");
		String contrasenia = req.queryParams("contrasenia");
		//String tipoUsuario = req.queryParams("tipoUsuario");
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Usuario usuario;
		List<Usuario> listaUsuarios = entityManager
				.createQuery("from Usuario where usuario = :nombre")
				.setParameter("nombre", nombre)
				.getResultList();
		if(!listaUsuarios.isEmpty()) {
			usuario = listaUsuarios.get(1);
			if(usuario.laContraseniaEs(contrasenia)) {
				res.cookie("usuario_logueado", nombre);
				res.redirect("/");
				return null;
			}
		}		
		/*EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		transaction.commit();
		*/
		res.redirect("/login");
		return null;
	}
	
}
