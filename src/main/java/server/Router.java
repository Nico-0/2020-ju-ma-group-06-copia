package server;

import controllers.Home;
import controllers.UsuarioController;

import org.apache.commons.lang3.StringUtils;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import controllers.BandejaDeEntradaController;
import controllers.Compras;
import controllers.CrearEmpresa;
import controllers.CrearEntidadBase;
import controllers.CrearOrganizacionSocial;
import controllers.Presupuestos;
import controllers.MenuEntidadesController;
import controllers.EntidadJuridicaController;
import controllers.EntidadBaseController;
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
		MenuEntidadesController entidad = new MenuEntidadesController();
		Compras compras = new Compras();
		Presupuestos presupuestos = new Presupuestos();
		EntidadJuridicaController entidadJuridica = new EntidadJuridicaController();
		EntidadBaseController entidadBase = new EntidadBaseController();
		LoginController loginController = new LoginController();
		BandejaDeEntradaController bandejaDeEntrada = new BandejaDeEntradaController();
		
		CrearEntidadBase crearEntidadBase = new CrearEntidadBase();
		CrearOrganizacionSocial crearOrganizacionSocial = new CrearOrganizacionSocial();
		CrearEmpresa crearEmpresa = new CrearEmpresa();
		
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
		
		Spark.get("/entidades", entidad::show, engine);
		Spark.get("/usuario/compras", usuario::compras,engine);
		Spark.post("/usuario/compras", usuario::crear_compra);
		Spark.get("/bandeja_de_entrada", bandejaDeEntrada::bandejaDeEntrada,engine);
		Spark.get("/usuario/crear", usuario::crear,engine);
		Spark.post("/usuario/crear", usuario::creacion);
		
		Spark.get("/entidades/crear_empresa", crearEmpresa::show,engine);
		Spark.get("/entidades/crear_organizacion_social", crearOrganizacionSocial::show,engine);
		Spark.get("/entidades/crear_entidad_base", crearEntidadBase::show,engine);
		Spark.post("/entidades/crear_empresa", crearEmpresa::crear,engine);
		Spark.post("/entidades/crear_organizacion_social", crearOrganizacionSocial::crear,engine);
		Spark.post("/entidades/crear_entidad_base", crearEntidadBase::crear,engine);
		//Spark.post("/entidades/:id/", entidad::, engine);
		Spark.get("/entidades/:id/delete", entidad::borrarEntidad, engine);
		
		Spark.get("/entidades/entidad_juridica", entidad::entidadJuridica,engine);
		Spark.get("/entidades/entidad_base", entidad::entidadBase,engine);
		
		Spark.get("/compra/editar", compras::editarCompra, engine);
		Spark.get("/compra/editar/presupuestos", compras::presupuestos, engine);
		Spark.get("/compra/editar/etiquetas", compras::etiquetas, engine);

		Spark.get("/presupuesto/editar", presupuestos::editarPresupuesto, engine);
		
		Spark.get("/entidades/entidad_juridica/compras", entidadJuridica::compras, engine);
		Spark.get("/entidades/entidad_juridica/reportes_mensuales", entidadJuridica::reportesMensuales, engine);
		Spark.get("/entidades/entidad_juridica/categorias", entidadJuridica::categorias, engine);
		Spark.get("/entidades/entidad_juridica/entidades_base", entidadJuridica::entidadesBase, engine);
		
		Spark.get("/entidades/entidad_base/compras", entidadBase::compras, engine);
		Spark.get("/entidades/entidad_base/reportes_mensuales", entidadBase::reportesMensuales, engine);
		Spark.get("/entidades/entidad_base/categorias", entidadBase::categorias, engine);	
		
		
		Spark.get("/usuario/crear/erroritem", (request, response) -> {
			return "no existe detalle con ese ID";
		});
		Spark.get("/usuario/crear/errordp", (request, response) -> {
			return "no existe proveedor con ese ID";
		});
		Spark.get("/usuario/compras/errordetalle", (request, response) -> {
			return "no existe detalle con ese ID";
		});
		Spark.get("/usuario/compras/errorproveedor", (request, response) -> {
			return "no existe proveedor con ese ID";
		});
		
	}

}