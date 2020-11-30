package controllers;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {

	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "login.hbs");
	}
	
	public ModelAndView login(Request req, Response res) throws NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException{
		// Buscar usuario en la base de datos y verificar
		String nombre = req.queryParams("nombre");
		String contrasenia = req.queryParams("contrasenia");
		//String tipoUsuario = req.queryParams("tipoUsuario");
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Usuario usuario = RepositorioUsuarios.getInstance().getUsuario(nombre);
		if(usuario != null && usuario.laContraseniaEs(contrasenia)) {
			res.cookie("usuario_logueado", nombre);
			res.redirect("/");
			return null;
		}
		/*EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		transaction.commit();
		*/
		res.redirect("/login");
		return null;
	}
	
}
