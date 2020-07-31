package dominio.entidad;

import dominio.compra.Compra;

public class Categoria {
	boolean bloquearNuevasCompras = false;
	boolean bloquearAgregarEntidadesBase = false;
    boolean bloquearFormarParteEntidadJuridica = false;
	double egresosMaximos;
	
	public Categoria() {
		
	}

    public void validarAgregarCompra(Entidad entidad, Compra unaCompra) {
        if(bloquearNuevasCompras && entidad.egresosSuperan(egresosMaximos)){
            throw new RuntimeException("La entidad ha superado el limite de "+egresosMaximos+
                                        " egresos totales. No se puede agregar la compra");
        }
    }

	public void setBloquearNuevosEgresos(boolean bloquearNuevasCompras) {
		this.bloquearNuevasCompras = bloquearNuevasCompras;
	}

	public void setEgresosMaximos(double egresosMaximos) {
		this.egresosMaximos = egresosMaximos;
    }

	public void setBloquarAgregarEntidadesBase(boolean bloquearAgregarEntidadesBase) {
		this.bloquearAgregarEntidadesBase = bloquearAgregarEntidadesBase;
	} 

	public void setBloquearFormarParteEntidadJuridica(boolean bloquearFormarParteEntidadJuridica) {
		this.bloquearFormarParteEntidadJuridica = bloquearFormarParteEntidadJuridica;
    }

    public void validarAgregarEntidadBase(EntidadJuridica entidad, EntidadBase entidadBase){
        if(!entidad.puedeAgregarEntidadesBase()){
            throw new RuntimeException("La categoria de la entidad no permite agregar entidades base");
        }
        if(!entidadBase.puedeAgregarseAEntidadJuridica()){
            throw new RuntimeException("La categoria de la entidad base no permite añadirla a una entidad juridica");
        }
    }

    public boolean puedeAgregarseAEntidadJuridica() {
        return !bloquearFormarParteEntidadJuridica;
    }

    public boolean puedeAgregarEntidadesBase() {
		return !bloquearAgregarEntidadesBase;
	}
}