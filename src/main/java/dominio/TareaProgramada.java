package dominio;

import java.util.TimerTask;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.presupuestos.CompraPendiente;
import repositorios.RepositorioComprasPendientes;

import java.lang.System;
import java.util.List;
import java.util.Timer;

public class TareaProgramada extends TimerTask{
    Timer timer;

    public TareaProgramada(long periodo) {
        timer = new Timer();
        this.setPeriodo(periodo);
    }

	public void setPeriodo(long periodo){
        timer.schedule(this, 0, periodo);
    }

    @Override
    public void run() {    
        final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		List<CompraPendiente> comprasPendientes = entityManager.createQuery("from CompraPendiente").getResultList();    	
		transaction.begin();
		comprasPendientes.stream().forEach(compraPendiente -> compraPendiente.validarCompra());
		transaction.commit();
    }
}