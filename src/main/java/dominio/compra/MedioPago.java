package dominio.compra;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.Validate;

@Entity
public class MedioPago {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private TipoPago tipoPago;
	private String identificador;

	public MedioPago(TipoPago tipoPago, String identificador) {
		Validate.notNull(tipoPago, "tipo pago faltante");
		Validate.notNull(identificador, "identificador faltante");
		this.tipoPago = tipoPago;
		this.identificador = identificador;
	}
}
