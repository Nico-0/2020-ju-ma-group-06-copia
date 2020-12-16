package controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {

	public ModelAndView show(Request req, Response res){
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();	
		List<Usuario> usuarios = entityManager
				.createQuery("from Usuario order by id DESC", Usuario.class)
				.getResultList();
		return new ModelAndView(usuarios.get(0), "login.hbs");
	}
	
	public ModelAndView login(Request req, Response res) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException{
		// Buscar usuario en la base de datos y verificar
		String nombre = req.queryParams("nombre");
		String contrasenia = req.queryParams("contrasenia");
		//String tipoUsuario = req.queryParams("tipousuario");
		
		Usuario usuario = RepositorioUsuarios.getInstance().getUsuario(nombre);
		
		if(usuario == null) {
			res.redirect("/login#popupNoExisteUsuario");
			return null;
		}
		
		if(!usuario.laContraseniaEs(contrasenia)) {
			res.redirect("/login#popupContraseniaIncorrecta");
			return null;
		}
		
		res.cookie("usuario_logueado", nombre);
		res.redirect("/");
		return null;
	}
	
	public ModelAndView logout(Request req, Response res) {
		res.removeCookie("usuario_logueado");
		res.redirect("/login");
		return null;
	}
	
	public TipoUsuario getTipo(String tipo) {
		TipoUsuario tipoUsuario = null;
		if(tipo.equals("admin"))
			tipoUsuario = TipoUsuario.ADMINISTRADOR;
		if(tipo.equals("estandar"))
			tipoUsuario = TipoUsuario.ESTANDAR;
				
		return tipoUsuario;
	}
}
