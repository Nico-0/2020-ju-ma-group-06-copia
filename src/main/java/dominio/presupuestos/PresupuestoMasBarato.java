package dominio.presupuestos;

import java.util.*;
import dominio.compra.*;

public class PresupuestoMasBarato implements CriterioDeSeleccionPresupuesto {
    public void verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
        if(!cumpleCriterioDeSeleccion(presupuestos, presupuestoSeleccionado)) {
        	String mensajeException = "El presupuesto del proveedor seleccionado no es el m�s barato";
        	enviarMensajeRevisores(mensajeException);
        	throw new NoCumpleCriterioDeSeleccionException(mensajeException);
        }
    }
    
    public boolean cumpleCriterioDeSeleccion(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
    	return presupuestos.stream().min(Comparator.comparing(Presupuesto::getTotal))
    			.get().equals(presupuestoSeleccionado);
    }
}