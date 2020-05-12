package dominio;

import java.util.ArrayList;
import java.util.List;

public abstract class EntidadJuridica implements Entidad {

	private List<EntidadBase> entidades_base = new ArrayList<EntidadBase>();	//puede ser vacia
	private List<Compra> compras = new ArrayList<Compra>();
	private String razon_social;
	private String nombre_ficticio;
	private long cuit;
	private String direccion_postal;
	private String codigo_inscripcion;
	
}
