package dominio.compra;

import dominio.Preconditions;

public class Item {

	private String descripcion;
	private int cantidad;
	private double valorUnitario; // lo cambié por ahora de int a float por la precisión
									// cambio a double, no existe "mapToFloat"

	public double get_valor_total() {
		return valorUnitario * cantidad;
	}

	public Item(String descripcion, float valorUnitario, int cantidad) {
		Preconditions.validateNotNull(descripcion, "descripcion faltante");
		Preconditions.validateNotNull(valorUnitario, "valor total faltante");
		Preconditions.validateNotNull(cantidad, "cantidad faltante");
		this.descripcion = descripcion;
		this.valorUnitario = valorUnitario;
		this.cantidad = cantidad;
	}

}
