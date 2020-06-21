package dominio.compra;

import org.apache.commons.lang3.Validate;

public class MedioPago {
	private TipoPago tipoPago;
	private String identificador;

	public MedioPago(TipoPago tipoPago, String identificador) {
		Validate.notNull(tipoPago, "tipo pago faltante");
		Validate.notNull(identificador, "identificador faltante");
		this.tipoPago = tipoPago;
		this.identificador = identificador;
	}
}
