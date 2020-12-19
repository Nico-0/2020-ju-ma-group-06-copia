package dominio.entidad;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import dominio.compra.Compra;

@Entity
public class ListaDeCompras {
	
	@Id
	@GeneratedValue
	protected Long id;
	
	@ManyToMany
	List<Compra> compras = new ArrayList<Compra>();
	
	String etiqueta;
	
	@Transient
	String tabla;
	
	public ListaDeCompras() {
		
	}
	
	public ListaDeCompras(String etiqueta, List<Compra> listaDeCompras) {
		this.etiqueta = etiqueta;
		this.compras = listaDeCompras;
	}

	public String getTabla() {
		tabla = "</br></br><h2>" + etiqueta + "</h2><table>"
				+ "<tr>"
				+ "<th> ID de la compra </th>"
				+ "<th></th>"
				+ "</tr>";
		compras.stream().forEach((compra) -> {
			tabla = tabla
					+ "<tr>"
					+ "<td> " + compra.getId() + "</td>"
					+ "   <td><a href = " + compra.getUrlView() + "> Ver </a></th>"
					+ "</tr>";
		});
		return tabla + "</table>"	+ "</br>";		
	}

	public void quitarTodasLasCompras() {
		compras.clear();
	}
}
