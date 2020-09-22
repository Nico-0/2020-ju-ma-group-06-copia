package dominio.presupuestos;

import java.util.*;

public abstract class CriterioDeSeleccionPresupuesto {
   abstract boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado);
}