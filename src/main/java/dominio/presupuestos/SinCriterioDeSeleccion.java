package dominio.presupuestos;

import java.util.List;

public class SinCriterioDeSeleccion extends CriterioDeSeleccionPresupuesto {

	public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
		return true;
	}

}
