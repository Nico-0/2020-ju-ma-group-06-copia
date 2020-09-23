package dominio.presupuestos;

import java.util.*;

public enum CriterioDeSeleccionPresupuesto {
	SinCriterioDeSeleccion {
        @Override
        public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
    		return true;
    	}
    }, 
	PresupuestoMasBarato {
        @Override
        public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
        	return presupuestos.stream().min(Comparator.comparing(Presupuesto::getTotal))
        			.get().equals(presupuestoSeleccionado);
        }
    };

	public abstract boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado);
}