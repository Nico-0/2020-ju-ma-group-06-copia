package dominio.compra;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.Validate;

import dominio.presupuestos.CriterioDeSeleccionPresupuesto;

@Entity
public class MedioPago {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private TipoPago tipoPago;
	private String identificador;

	public MedioPago(TipoPago tipoPago, String identificador) {
		//Validate.notNull(tipoPago, "tipo pago faltante");
		Validate.notNull(identificador, "identificador faltante");
		this.tipoPago = tipoPago;
		this.identificador = identificador;
	}
	
	public MedioPago() { //rompe hibernate sin esto al crear item en la app
	}
	
    public long getId(){
    	  return this.id;
      } 
	
    public void setTipoPago(TipoPago tipoPago) {
    	this.tipoPago = tipoPago;
    }

	public void setIdentificador(String identificadorMedioPago) {
		this.identificador = identificadorMedioPago;
	}

	public String getTipoMedioPago() {
		return tipoPago.toString();
	}

	public String getIdentificadorMedioPago() {
		return identificador;
	}
}
