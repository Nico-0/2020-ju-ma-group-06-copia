package dominio.presupuestos;

import java.time.LocalDate;
import java.util.*;

import dominio.compra.*;
import dominio.entidad.Entidad;
import dominio.usuario.*;

public class CompraPendiente {
    private List<Presupuesto> presupuestos = new ArrayList<>();
    private Detalle detalle = new Detalle();
    private Proveedor proveedor;
    private CriterioDeSeleccionPresupuesto criterioDeSeleccion = new SinCriterioDeSeleccion();
    private static int cantidadPresupuestosRequeridos = 0;
    private LocalDate fecha;
    private MedioPago medioPago;
    private List<Usuario> usuariosRevisores = new ArrayList<>();
    private Entidad entidad;

    public static void setCantidadPresupuestosRequeridos(int unaCantidad) {
        cantidadPresupuestosRequeridos = unaCantidad;
    }
    
    public void setMedioPago(MedioPago medioPago) {
    	this.medioPago = medioPago;
    }
    
    public void setFecha(LocalDate fecha) {
    	fecha = this.fecha;
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

    public boolean verificarCantidadPresupuestos() {
    	return validarCondicion(presupuestos.size() == cantidadPresupuestosRequeridos,
    			"La cantidad de presupuestos cargada es incorrecta. Se necesitan " + 
    			presupuestos.size() + "y se han cargado " + cantidadPresupuestosRequeridos);
    }
    
    public boolean verificarDetallePresupuesto() {
    	return validarCondicion(presupuestoProveedorSeleccionado().tieneMismoDetalle(detalle),
    			"El presupuesto del proveedor seleccionado no coincide con el detalle de la compra");
    }
    
    public boolean verificarCriterioDeSeleccion(){
    	return validarCondicion(criterioDeSeleccion.verificar(presupuestos, presupuestoProveedorSeleccionado()),
    									"El presupuesto del proveedor elegido no es el m�s barato");
    }
    
    public boolean validarCondicion(boolean condicion, String mensaje) {
    	if(!condicion) {
    		enviarMensajeRevisores(mensaje);
    		return false;
    	}
    	return true;
    }
    
    public boolean verificarParametrosNotNull() {
    	return validarCondicion(proveedor != null,"proveedor faltante") &&
    	validarCondicion(medioPago != null, "medio de pago faltante") &&
    	validarCondicion(fecha != null, "fecha faltante") &&
		validarCondicion(entidad != null,"entidad faltante");
    }
    
    public Presupuesto presupuestoProveedorSeleccionado() {
    	return presupuestos.stream().filter(presupuesto -> presupuesto.proveedorEs(proveedor)).findFirst().get();
    }
    
    public CompraPendiente agregarItem(Item unItem) {
    	detalle.agregarItem(unItem);
    	return this;
    }
    
    public void agregarUsuarioRevisor(Usuario unUsuario) {
    	usuariosRevisores.add(unUsuario);
    }
    
    public void quitarUsuarioRevisor(Usuario unUsuario) {
    	usuariosRevisores.remove(unUsuario);
    }
    
    public void enviarMensajeRevisores(String texto) {
    	Mensaje unMensaje = new Mensaje(this, texto);
    	usuariosRevisores.stream().forEach(unUsuario -> unUsuario.recibirMensaje(unMensaje)); 
    }
    
    public void setEntidad(Entidad entidad) {
    	this.entidad = entidad;
    }
    
    public boolean verificarQueEsValida() {
    	return verificarParametrosNotNull() &&
                verificarCantidadPresupuestos() &&
    			verificarDetallePresupuesto() &&
    			verificarCriterioDeSeleccion();
    }
    
    public void validarCompra() {
	    if(verificarQueEsValida()) {
			Compra compra = new Compra(proveedor, medioPago, fecha, presupuestos, detalle, usuariosRevisores);
			entidad.agregarCompra(compra);
			enviarMensajeRevisores("La compra fue validada.");
		}    	
    }
}