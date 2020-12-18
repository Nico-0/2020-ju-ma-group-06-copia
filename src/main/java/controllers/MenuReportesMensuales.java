package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.Entidad;
import dominio.entidad.Reporte;
import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MenuReportesMensuales {
	static List<String> tiposEntidades = new ArrayList<>(Arrays.asList(
		    "entidades_base",
		    "empresas",
		    "organizaciones_sociales"
	));
	
	//Spark.get("/entidades/:tipo_entidad/:id_entidad/reportes_mensuales", menuReportesMensuales::show, engine);
	public ModelAndView show(Request req, Response res){
		this.verificarTipoEntidad(req.params("tipo_entidad"), res);
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(new Long(req.params("id_entidad")));
		return new ModelAndView(entidad, "menuReportesMensuales.hbs");
	}
	
	//Spark.get("/entidades/:tipo_entidad/:id_entidad/reportes_mensuales/:id_reporte_mensual", menuReportesMensuales::mostrarReporteMensual, engine);
	public ModelAndView mostrarReporteMensual(Request req, Response res){
		this.verificarTipoEntidad(req.params("tipo_entidad"), res);
		Long idReporte = new Long(req.params("id_reporte_mensual"));
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Reporte reporte = (Reporte) entityManager
				.createQuery("from Reporte where id = :id")
				.setParameter("id", idReporte)
				.getSingleResult();
		return new ModelAndView(reporte, "mostrarReporteMensual.hbs");
	}
	
	public void verificarTipoEntidad(String tipoEntidad, Response res) {
		if(!tiposEntidades.contains(tipoEntidad))
			res.redirect("/unknown");
	}
	
}
