package dominio.entidad;

import dominio.compra.Compra;

public abstract class Categoria {
	
	public abstract void setBloquearNuevosEgresos(boolean bloquearNuevasCompras);
	public abstract void setEgresosMaximos(double egresosMaximos) ;
	public abstract void setBloquarAgregarEntidadesBase(boolean bloquearAgregarEntidadesBase) ;
	public abstract void setBloquearFormarParteEntidadJuridica(boolean bloquearFormarParteEntidadJuridica);
	public abstract void validarAgregarCompra(Entidad entidad, Compra unaCompra);
    public abstract void validarAgregarEntidadBase(EntidadJuridica entidad, EntidadBase entidadBase);
    public abstract boolean puedeAgregarseAEntidadJuridica();
    public abstract boolean puedeAgregarEntidadesBase();
    
}