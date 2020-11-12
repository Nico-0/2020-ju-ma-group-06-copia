package server;

import controllers.Home;
import controllers.Usuario;
import controllers.Compras;
import controllers.Presupuestos;
import controllers.Entidad;
import controllers.EntidadJuridica;
import controllers.EntidadBase;
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
		
		Usuario usuario = new Usuario();
		Entidad entidad = new Entidad();
		Compras compras = new Compras();
		Presupuestos presupuestos = new Presupuestos();
		EntidadJuridica entidadJuridica = new EntidadJuridica();
		EntidadBase entidadBase = new EntidadBase();
		
		Spark.get("/", Home::home, engine);
		Spark.get("/usuario", usuario::login, engine);
		Spark.get("/entidades", entidad::login, engine);
		Spark.get("/usuario/compras", usuario::compras,engine);
		Spark.get("/usuario/bandeja_entrada", usuario::bandejaDeEntrada,engine);
		Spark.get("/usuario/crear", usuario::crear,engine);
		
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

	}

}