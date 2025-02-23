package dominio.compra;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
public class Item {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String descripcion;
	private int cantidad;
	public double valorUnitario;

	public String getDescripcion() {
		return descripcion;
	}
	
	public String getCantidad() {
		return String.valueOf(cantidad);
	}
	
	public String getValorUnitario() {
		return String.valueOf(valorUnitario);
	}
	
	public String getValorItem() {
		return String.valueOf(this.getValorTotal());
	}
	
	
	public Item() { //rompe hibernate sin esto al crear item en la app
	}
	
    public String getId(){
  	  return String.valueOf(id);
    }
	
	public Item(String descripcion, float valorUnitario, int cantidad) {
		Validate.notNull(descripcion, "descripcion faltante");
		Validate.notNull(valorUnitario, "valor total faltante");
		Validate.notNull(cantidad, "cantidad faltante");
		this.descripcion = descripcion;
		this.valorUnitario = valorUnitario;
		this.cantidad = cantidad;
	}

	public double getValorTotal() {
		return valorUnitario * cantidad;
	}

    public boolean esIgualA(Item otroItem) {
		return 	otroItem.descripcionEsIgual(descripcion) && 
				otroItem.valorUnitarioEsIgual(valorUnitario) &&
				otroItem.cantidadEsIgual(cantidad); 
    }

    public boolean descripcionEsIgual(String otraDescripcion) {
        return descripcion.equals(otraDescripcion); 
    }

	public boolean valorUnitarioEsIgual(double otroValorUnitario) {
        return valorUnitario == otroValorUnitario;
	}
	
    public boolean cantidadEsIgual(int otraCantidad) {
        return cantidad == otraCantidad;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;		
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

}

