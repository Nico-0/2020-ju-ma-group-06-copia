package dominio.entidad;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import dominio.compra.Compra;

@Entity
public class Reporte {
	
	@Id
	@GeneratedValue
	protected Long id;
	
	private LocalDate fecha;
	
	private HashMap<String, List<Compra>> comprasPorEtiqueta = new HashMap<String, List<Compra>>();
	
	public Reporte() {
		
	}
	
	public Reporte(HashMap<String, List<Compra>> comprasPorEtiqueta, LocalDate fecha) {
		this.comprasPorEtiqueta = comprasPorEtiqueta;
		this.fecha = fecha;
	}
	
	public String getId() {
		return id.toString();
	}
	
	public String getMes() {
		return fromGringoToEspaniolLatino(fecha.getMonth().toString());
	}
	
	public String getAnio() {
		return String.valueOf(fecha.getYear());
	}
	
	public String fromGringoToEspaniolLatino(String gringo) {
		if(gringo.contentEquals("DECEMBER")) 	return "DICIEMBRE";
		if(gringo.contentEquals("NOVEMBER")) 	return "NOVIEMBRE";
		if(gringo.contentEquals("OCTOBER"))	 	return "OCTUBRE";
		if(gringo.contentEquals("SEPTEMBER")) 	return "SEPTIEMBRE";
		if(gringo.contentEquals("AUGUST")) 		return "AGOSTO";
		if(gringo.contentEquals("JULY")) 		return "JULIO";
		if(gringo.contentEquals("JUNE")) 		return "JUNIO";
		if(gringo.contentEquals("MAY")) 		return "MAYO";
		if(gringo.contentEquals("APRIL")) 		return "ABRIL";
		if(gringo.contentEquals("MARCH")) 		return "MARZO";
		if(gringo.contentEquals("FEBRUARY"))	return "FEBRERO";
		if(gringo.contentEquals("JANUARY")) 	return "ENERO";
		return "";
	}
}
