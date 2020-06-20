package dominio.presupuestos;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.*;

import org.apache.commons.lang3.Validate;

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

    public void verificarCantidadPresupuestos() {
        if(presupuestos.size() != cantidadPresupuestosRequeridos) {
        	String mensajeException = "La cantidad de presupuestos cargada es incorrecta. Se necesitan " + presupuestos.size() + "y se han cargado " + cantidadPresupuestosRequeridos;
        	//enviarMensajeRevisores(mensajeException);
        	throw new CantidadPresupuestosIncorrectaException(mensajeException);
        }
    }
    
    public void verificarDetallePresupuesto() {
    	if(!presupuestoProveedorSeleccionado().tieneMismoDetalle(detalle)) {
    		String mensajeException = "El presupuesto del proveedor seleccionado no coincide con el detalle de la compra";
    		throw new PresupuestoNoCoincideException(mensajeException);
    	}
    }
    
    public void verificarCriterioDeSeleccion(){
    	criterioDeSeleccion.verificar(presupuestos, presupuestoProveedorSeleccionado());
    }
    
    public void verificarParametrosNotNull() {
		Validate.notNull(proveedor, "proveedor faltante");
		Validate.notNull(medioPago, "medio de pago faltante");
		Validate.notNull(fecha, "fecha faltante");
		Validate.notNull(entidad,"entidad faltante");
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
    	usuariosRevisores.stream().forEach(unUsuario -> unUsuario.bandejaDeEntrada.agregarMensaje(unMensaje)); 
    }
    
    public void setEntidad(Entidad entidad) {
    	this.entidad = entidad;
    }
    
    public void validarCompra() {
    	
    	try {
    		verificarParametrosNotNull();
    		verificarCantidadPresupuestos();
    		verificarDetallePresupuesto();
    		verificarCriterioDeSeleccion();
    		Compra compra = new Compra(proveedor, 
	  				   medioPago, 
	  				   fecha,
	  				   presupuestos,
	  				   detalle,
	  				 usuariosRevisores);
    		entidad.agregarCompra(compra);
    		enviarMensajeRevisores("La compra fue validada.");
    	}
    	
    	catch(NullPointerException e) {
    		enviarMensajeRevisores(e.getMessage());
    	}
    	
    	catch(CantidadPresupuestosIncorrectaException e){
    		enviarMensajeRevisores("La cantidad de presupuestos cargada es incorrecta. Se necesitan " + presupuestos.size() + "y se han cargado " + cantidadPresupuestosRequeridos);
    	}

		catch(PresupuestoNoCoincideException e){
			enviarMensajeRevisores("El presupuesto del proveedor seleccionado no coincide con el detalle de la compra");
		}
    	
    	catch(NoCumpleCriterioDeSeleccionException e) {
    		enviarMensajeRevisores("La compra no cumple con los criterios de selección");
    	}
    	
    	
    }
}