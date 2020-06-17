package dominio.presupuestos;
import dominio.compra.*;

public class Presupuesto {
    private Proveedor proveedor;
    private Detalle detalle = new Detalle();
	private DocumentoComercial documentoComercial; // puede ser nulo

    public Presupuesto(CompraPendiente compraPendiente, Proveedor proveedor) {
        // Asociamos el presupuesto a una compra pendiente
        compraPendiente.agregarPresupuesto(this);
        this.proveedor = proveedor;
    }
    
    public void setDetalle(Detalle detalle) {
    	this.detalle = detalle;
    }

    public void setDocumentoComercial(DocumentoComercial documentoComercial) {
    	this.documentoComercial = documentoComercial;
    }
    
    public Presupuesto agregarItem(Item unItem) {
    	detalle.agregarItem(unItem);
    	return this;
    }
    
    public Proveedor getProveedor() {
        return this.proveedor;
    }

    public double getTotal() {
        return detalle.getTotal();
    }
    
    public boolean proveedorEs(Proveedor unProveedor) {
    	return proveedor == unProveedor;
    }
    public boolean tieneMismoDetalle(Detalle unDetalle) {
        return this.detalle.esIgualA(unDetalle);
    }
}