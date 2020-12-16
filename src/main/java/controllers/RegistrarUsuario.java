package controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;
import dominio.validacion.ContieneNombreDeUsuario;
import dominio.validacion.ContieneNombreUsuarioException;
import dominio.validacion.EsMalaException;
import dominio.validacion.EsMuyCortaException;
import dominio.validacion.RepiteCaracteresException;
import repositorios.RepositorioUsuarios;
import repositorios.UsuarioYaExisteException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RegistrarUsuario {
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "registrarUsuario.hbs");
	}
	
	public ModelAndView registrar(Request req, Response res) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException{

		String nombre = req.queryParams("nombre");
		String contrasenia = req.queryParams("contrasenia");
		String tipoUsuario = req.queryParams("tipousuario");
		
		try {	
			RepositorioUsuarios.getInstance().verificarNoExisteUsuario(nombre);
			final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
			final EntityTransaction transaction = entityManager.getTransaction();
			Usuario usuario = new Usuario(nombre,contrasenia,getTipo(tipoUsuario));
			transaction.begin();
			entityManager.persist(usuario.bandejaDeEntrada);
			entityManager.persist(usuario);	
			transaction.commit();
			res.redirect("/login");
		}
		
		catch(UsuarioYaExisteException exception) {
			res.redirect("/registrar_usuario#popupYaExiste");
		}
		
		catch(EsMuyCortaException exception) {
			res.redirect("/registrar_usuario#popupContraseniaCorta");
		}
		
		catch(RepiteCaracteresException exception) {
			res.redirect("/registrar_usuario#popupContraseniaRepiteCaracteres");
		}
		
		catch(EsMalaException exception) {
			res.redirect("/registrar_usuario#popupContraseniaMala");
		}
		
		catch(ContieneNombreUsuarioException exception) {
			res.redirect("/registrar_usuario#popupContieneNombreUsuario");
		}
		
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
