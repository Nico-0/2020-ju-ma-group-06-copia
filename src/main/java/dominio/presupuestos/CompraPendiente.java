package dominio.presupuestos;

import java.util.*;
import dominio.compra.*;
import dominio.usuario.*;

public class CompraPendiente {
    private List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
    private Detalle detalle = new Detalle();
    private Usuario revisor;
    private Proveedor proveedor;
    private CriterioDeSeleccionPresupuesto criterioDeSeleccion = new SinCriterioDeSeleccion();
    private static int cantidadPresupuestosRequeridos = 0;

    public static void setCantidadPresupuestosRequeridos(int unaCantidad) {
        cantidadPresupuestosRequeridos = unaCantidad;
    }
    
    public CompraPendiente setCriterioDeSeleccion(CriterioDeSeleccionPresupuesto unCriterio) {
    	this.criterioDeSeleccion = unCriterio;
    	return this;
    }
    
    public CompraPendiente setProveedor(Proveedor proveedor) {
    	this.proveedor = proveedor;
    	return this;
    }

    public CompraPendiente agregarPresupuesto(Presupuesto unPresupuesto) {
        presupuestos.add(unPresupuesto);
        return this;
    }

    public void verificarCantidadPresupuestos() {
        if(presupuestos.size() != cantidadPresupuestosRequeridos) {
        	throw new CantidadPresupuestosIncorrectaException(presupuestos.size(),cantidadPresupuestosRequeridos);
        }
    }
    
    public void verificarDetallePresupuesto() {
    	if(!presupuestoProveedorSeleccionado().tieneMismoDetalle(detalle)) {
    		throw new PresupuestoNoCoincideException("El presupuesto del proveedor seleccionado no coincide con el detalle de la compra");
    	}
    }
    
    public void verificarCriterioDeSeleccion(){
    	criterioDeSeleccion.verificar(presupuestos, presupuestoProveedorSeleccionado());
    }
    
    public Presupuesto presupuestoProveedorSeleccionado() {
    	return presupuestos.stream().filter(presupuesto -> presupuesto.proveedorEs(proveedor)).findFirst().get();
    }
    
    public CompraPendiente agregarItem(Item unItem) {
    	detalle.agregarItem(unItem);
    	return this;
    }

    public void validarCompra() {
    	verificarCantidadPresupuestos();
    	verificarDetallePresupuesto();
    	verificarCriterioDeSeleccion();
        // crear compra válida
    }
}