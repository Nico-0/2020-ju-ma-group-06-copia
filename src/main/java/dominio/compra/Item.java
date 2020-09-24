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

	public Item(String descripcion, float valorUnitario, int cantidad) {
		Validate.notNull(descripcion, "descripcion faltante");
		Validate.notNull(valorUnitario, "valor total faltante");
		Validate.notNull(cantidad, "cantidad faltante");
		this.descripcion = descripcion;
		this.valorUnitario = valorUnitario;
		this.cantidad = cantidad;
	}

	public double get_valor_total() {
		return valorUnitario * cantidad;
	}

    public boolean esIgualA(Item otroItem) {
		return 	otroItem.descripcionEsIgual(descripcion) && 
				otroItem.valorUnitarioEsIgual(valorUnitario) &&
				otroItem.cantidadEsIgual(cantidad); 
    }

    public boolean descripcionEsIgual(String otraDescripcion) {
        return descripcion == otraDescripcion; 
    }

	public boolean valorUnitarioEsIgual(double otroValorUnitario) {
        return valorUnitario == otroValorUnitario;
	}
	
    public boolean cantidadEsIgual(int otraCantidad) {
        return cantidad == otraCantidad;
	}

}

