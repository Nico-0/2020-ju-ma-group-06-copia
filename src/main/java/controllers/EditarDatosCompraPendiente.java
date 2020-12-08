package controllers;

import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.TipoPago;
import dominio.entidad.Entidad;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.CriterioDeSeleccionPresupuesto;
import dominio.presupuestos.Presupuesto;
import repositorios.RepositorioComprasPendientes;
import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EditarDatosCompraPendiente {
	public ModelAndView show(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente")); 
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		return new ModelAndView(compraPendiente, "editarDatosCompraPendiente.hbs");
	}

	public ModelAndView editar(Request req, Response res){
		int cantidadDePresupuestosRequeridos = Integer. parseInt(req.queryParams("cantidad_presupuestos"));
		String identificadorMedioPago = req.queryParams("identificador_medio_pago");
		TipoPago tipoPago = toTipoPago(req.queryParams("tipo_medio_pago"));
		CriterioDeSeleccionPresupuesto criterioDeSeleccionPresupuesto = toCriterioDeSeleccion(req.queryParams("criterio"));
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		compraPendiente.setCantidadPresupuestosRequeridos(cantidadDePresupuestosRequeridos);
		compraPendiente.setCriterioDeSeleccion(criterioDeSeleccionPresupuesto);
		compraPendiente.editarMedioPago(identificadorMedioPago, tipoPago);
		transaction.commit();
		res.redirect(compraPendiente.getUrlView());
		return null;
	}

	private CriterioDeSeleccionPresupuesto toCriterioDeSeleccion(String s) {
		if(s.equals("masBarato"))
			return CriterioDeSeleccionPresupuesto.PresupuestoMasBarato; 
		return CriterioDeSeleccionPresupuesto.SinCriterioDeSeleccion; 
	}

	private TipoPago toTipoPago(String s) {
		if(s.equals("tarjetaDeCredito"))
			return TipoPago.TARJETA_CREDITO;
		if(s.equals("tarjetaDeDebito"))
			return TipoPago.TARJETA_DEBITO;
		if(s.equals("efectivo"))
			return TipoPago.EFECTIVO;
		if(s.equals("cajeroAutomatico"))
			return TipoPago.CAJERO_AUTOMATICO;
		if(s.equals("dineroCuenta"))
			return TipoPago.DINERO_CUENTA;
		return TipoPago.EFECTIVO;
	}
}