package server;

import controllers.Home;
import controllers.Usuario;
import controllers.Entidad;
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
		
		Spark.get("/", Home::home, engine);
		Spark.get("/usuario", usuario::login, engine);
		Spark.get("/entidad", entidad::login, engine);
	}

}