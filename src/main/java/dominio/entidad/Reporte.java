package dominio.entidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Reporte {
	
	@Id
	@GeneratedValue
	protected Long id;
	
	private LocalDate fecha;
	
	@Transient
	String tabla;
	
	@OneToMany
	private List<ListaDeCompras> comprasPorEtiqueta = new ArrayList<ListaDeCompras>();
		
	public Reporte() {
		
	}
	
	public Reporte(List<ListaDeCompras> comprasPorEtiqueta, LocalDate fecha) {
		this.comprasPorEtiqueta = comprasPorEtiqueta;
		this.fecha = fecha;
	}
	
	public List<ListaDeCompras> getComprasPorEtiqueta() {
		return comprasPorEtiqueta;
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
	
	public String getTablaCompras() {
		tabla = "";
		comprasPorEtiqueta.stream().forEach((listaDeCompras) -> {
			tabla = tabla + listaDeCompras.getTabla();				
		});
		return tabla;
	}

	public boolean tieneMismaFecha(Reporte reporte) {
		return this.getMes().equals(reporte.getMes()) && this.getAnio().equals(reporte.getAnio());
	}
}
