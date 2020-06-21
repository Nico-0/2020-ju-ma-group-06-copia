package dominio.presupuestos;

import java.util.*;

public interface CriterioDeSeleccionPresupuesto {
   boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado);
}