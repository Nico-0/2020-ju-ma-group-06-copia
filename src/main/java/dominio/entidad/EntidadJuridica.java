package dominio.entidad;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;

public abstract class EntidadJuridica extends Entidad {

	private List<EntidadBase> entidades_base = new ArrayList<EntidadBase>(); // puede ser vacia
	private String razonSocial;
	private String cuit;
	private String direccionPostal;
	private String codigoInscripcion;
	private List<EntidadBase> entidades_usadas = new ArrayList<EntidadBase>();
	
	public EntidadJuridica(String razonSocial, String nombreFicticio, String cuit, String direccionPostal, List<EntidadBase> entidades) {
		Validate.notNull(razonSocial, "razon social faltante");
		Validate.notNull(nombreFicticio, "nombre ficticio faltante");
		Validate.notNull(cuit, "cuit faltante");
		Validate.notNull(direccionPostal, "direccion postal faltante");
		this.razonSocial = razonSocial;
		this.nombreFicticio = nombreFicticio;
		this.cuit = cuit;
		this.direccionPostal = direccionPostal;
		this.entidades_base = this.tomarEntidadesDisponibles(entidades);
		this.entidades_base.stream().forEach(entidad -> this.entidades_usadas.add(entidad));
	}

	public void setCodigoInscripcion(String codigoInscripcion) {
		this.codigoInscripcion = codigoInscripcion;
	}
	
	public List<EntidadBase> tomarEntidadesDisponibles(List<EntidadBase> entidades){
		return entidades.stream().filter(entidad->!entidades_usadas.contains(entidad)).collect(Collectors.toList());
	}

	public void agregarEntidadBase(EntidadBase entidadBase) {
		categoria.agregarEntidadBase(this, entidadBase);
	}

	public List<EntidadBase> getEntidadesBase() {
		return entidades_base;
	}

	public boolean puedeAgregarEntidadesBase() {
		return categoria.puedeAgregarEntidadesBase();
	}
}
