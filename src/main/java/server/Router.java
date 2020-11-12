package server;

import controllers.Home;
import controllers.UsuarioController;

import org.apache.commons.lang3.StringUtils;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import controllers.Compras;
import controllers.Entidad;
import controllers.Organizacion;
import controllers.EntidadJuridica;
import controllers.EntidadBase;
import controllers.LoginController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.build();
		
		Spark.staticFiles.location("/public");
		
		UsuarioController usuario = new UsuarioController();
		Entidad entidad = new Entidad();
		Organizacion organizacion = new Organizacion();
		Compras compras = new Compras();
		EntidadJuridica entidadJuridica = new EntidadJuridica();
		EntidadBase entidadBase = new EntidadBase();
		LoginController loginController = new LoginController();
		
		Spark.before((request, response)-> {
			if(!request.pathInfo().equals("/login") &&
					StringUtils.isEmpty(request.cookie("usuario_logueado"))) {
				response.redirect("/login");
			}
			//PerThreadEntityManagers.getEntityManager();
		});
		/*
		Spark.after((request, response)-> {
			PerThreadEntityManagers.closeEntityManager();
		});*/
		
		Spark.get("/", Home::home, engine);
		
		Spark.get("/login", loginController::show, engine);
		Spark.post("/login", loginController::login, engine);
		Spark.get("/usuario", usuario::menuUsuario, engine);
		
		Spark.get("/entidades", entidad::login, engine);
		Spark.get("/usuario/compras", usuario::compras,engine);
		Spark.get("/usuario/bandeja_entrada", usuario::bandejaDeEntrada,engine);
		
		
		Spark.get("/entidades/entidad_juridica", entidad::entidadJuridica,engine);
		Spark.get("/entidades/entidad_base", entidad::entidadBase,engine);
		
		Spark.get("/compra/editar", compras::editarCompra, engine);

		Spark.get("/entidades/entidad_juridica/compras", entidadJuridica::compras, engine);
		Spark.get("/entidades/entidad_juridica/reportes_mensuales", entidadJuridica::reportesMensuales, engine);
		Spark.get("/entidades/entidad_juridica/categorias", entidadJuridica::categorias, engine);
		Spark.get("/entidades/entidad_juridica/entidades_base", entidadJuridica::entidadesBase, engine);
		
		Spark.get("/entidades/entidad_base/compras", entidadBase::compras, engine);
		Spark.get("/entidades/entidad_base/reportes_mensuales", entidadBase::reportesMensuales, engine);
		Spark.get("/entidades/entidad_base/categorias", entidadBase::categorias, engine);		
		
	}

}