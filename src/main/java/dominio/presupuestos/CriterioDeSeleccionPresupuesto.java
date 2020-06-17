package dominio.presupuestos;

import java.util.*;
import dominio.compra.*;

public interface CriterioDeSeleccionPresupuesto {
   void verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado);
}