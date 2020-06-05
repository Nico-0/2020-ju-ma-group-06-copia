package dominio.compra;

import org.apache.commons.lang3.Validate;

public class Item {

	private String descripcion;
	private int cantidad;
	private double valorUnitario; // lo cambié por ahora de int a float por la precisión
									// cambio a double, no existe "mapToFloat"

	public double get_valor_total() {
		return valorUnitario * cantidad;
	}

	public Item(String descripcion, float valorUnitario, int cantidad) {
		Validate.notNull(descripcion, "descripcion faltante");
		Validate.notNull(valorUnitario, "valor total faltante");
		Validate.notNull(cantidad, "cantidad faltante");
		this.descripcion = descripcion;
		this.valorUnitario = valorUnitario;
		this.cantidad = cantidad;
	}

}
