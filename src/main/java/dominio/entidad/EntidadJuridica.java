package dominio.entidad;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class EntidadJuridica extends Entidad {

	@OneToMany
	private List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>(); // puede ser vacia
	
	private String razonSocial;
	private String cuit;
	private String direccionPostal;
	private String codigoInscripcion;
	
	@OneToMany
	private List<EntidadBase> entidades_usadas = new ArrayList<EntidadBase>();
	
	public String getRazonSocial() {
		return razonSocial;
	}
	
	public String getCuit() {
		return cuit;
	}
	
	public String getDireccionPostal() {
		return direccionPostal;
	}
	
	public String getCodigoInscripcion() {
		return codigoInscripcion;
	}
	
	public EntidadJuridica() {
		
	}
	
	public EntidadJuridica(String razonSocial, String nombreFicticio, String cuit, String direccionPostal, List<EntidadBase> entidades) {
		Validate.notNull(razonSocial, "razon social faltante");
		Validate.notNull(nombreFicticio, "nombre ficticio faltante");
		Validate.notNull(cuit, "cuit faltante");
		Validate.notNull(direccionPostal, "direccion postal faltante");
		this.razonSocial = razonSocial;
		this.nombreFicticio = nombreFicticio;
		this.cuit = cuit;
		this.direccionPostal = direccionPostal;
		this.entidadesBase = this.tomarEntidadesDisponibles(entidades);
		this.entidadesBase.stream().forEach(entidad -> this.entidades_usadas.add(entidad));
	}

	public void setCodigoInscripcion(String codigoInscripcion) {
		this.codigoInscripcion = codigoInscripcion;
	}
	
	public List<EntidadBase> tomarEntidadesDisponibles(List<EntidadBase> entidades){
		return entidades.stream().filter(entidad->!entidades_usadas.contains(entidad)).collect(Collectors.toList());
	}

	public void agregarEntidadBase(EntidadBase entidadBase) {
		categorias.stream().forEach(categoria -> categoria.validarAgregarEntidadBase(this, entidadBase));
		entidadesBase.add(entidadBase);
	}

	public List<EntidadBase> getEntidadesBase() {
		return entidadesBase;
	}

	public boolean puedeAgregarEntidadesBase() {
		return categorias.stream().allMatch(categoria -> categoria.puedeAgregarEntidadesBase());
	}
}
