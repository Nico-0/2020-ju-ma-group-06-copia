package dominio.presupuestos;

import java.util.*;

public enum CriterioDeSeleccionPresupuesto {
	
	ElUsuarioOlvidoElegirCriterio {//TODO borrar
        @Override
        public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
    		return true;
    	}
    },
	SinCriterioDeSeleccion {
        @Override
        public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
    		return true;
    	}
        @Override
        public String toString() {
        	return "Sin criterio de seleccion";
        }
    }, 
	PresupuestoMasBarato {
        @Override
        public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
        	return presupuestos.stream().min(Comparator.comparing(Presupuesto::getTotal))
        			.get().equals(presupuestoSeleccionado);
        }
        @Override
        public String toString() {
        	return "Presupuesto mas barato";
        }
    };

	public abstract boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado);
}