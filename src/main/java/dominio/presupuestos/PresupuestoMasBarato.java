package dominio.presupuestos;

import java.util.*;

public class PresupuestoMasBarato implements CriterioDeSeleccionPresupuesto {
    public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
        return presupuestos.stream().min(Comparator.comparing(Presupuesto::getTotal))
    			.get().equals(presupuestoSeleccionado);
    }
}