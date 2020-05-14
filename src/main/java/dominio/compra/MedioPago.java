package dominio.compra;

import dominio.PreconditionFailed;
import dominio.Preconditions;

public class MedioPago {
	private TipoPago tipoPago;
	private String identificador;

	public MedioPago(TipoPago tipoPago, String identificador) {
		Preconditions.validateNotNull(tipoPago, "tipo pago faltante");
		Preconditions.validateNotNull(identificador, "identificador faltante");
		this.tipoPago = tipoPago;
		this.identificador = identificador;
	}
}
