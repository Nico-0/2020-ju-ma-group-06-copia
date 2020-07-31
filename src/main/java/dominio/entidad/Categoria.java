package dominio.entidad;

import dominio.compra.Compra;

public interface Categoria {
	
	public void setBloquearNuevosEgresos(boolean bloquearNuevasCompras);
	public void setEgresosMaximos(double egresosMaximos) ;
	public void setBloquarAgregarEntidadesBase(boolean bloquearAgregarEntidadesBase) ;
	public void setBloquearFormarParteEntidadJuridica(boolean bloquearFormarParteEntidadJuridica);
	public void validarAgregarCompra(Entidad entidad, Compra unaCompra);
    public void validarAgregarEntidadBase(EntidadJuridica entidad, EntidadBase entidadBase);
    public boolean puedeAgregarseAEntidadJuridica();
    public boolean puedeAgregarEntidadesBase();
}