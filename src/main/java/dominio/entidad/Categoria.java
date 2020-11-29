package dominio.entidad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import dominio.compra.Compra;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Categoria {
	@Id
	@GeneratedValue
	protected Long id;
	protected String nombre;
	public abstract void setBloquearNuevosEgresos(boolean bloquearNuevasCompras);
	public abstract void setEgresosMaximos(double egresosMaximos) ;
	public abstract void setBloquarAgregarEntidadesBase(boolean bloquearAgregarEntidadesBase) ;
	public abstract void setBloquearFormarParteEntidadJuridica(boolean bloquearFormarParteEntidadJuridica);
	public abstract void validarAgregarCompra(Entidad entidad, Compra unaCompra);
    public abstract void validarAgregarEntidadBase(EntidadJuridica entidad, EntidadBase entidadBase);
    public abstract boolean puedeAgregarseAEntidadJuridica();
    public abstract boolean puedeAgregarEntidadesBase();
    
}