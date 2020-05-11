package dominio;

import java.util.ArrayList;
import java.util.List;

public abstract class EntidadJuridica implements Entidad {

	public List<EntidadBase> entidades_base = new ArrayList<EntidadBase>();	//puede ser vacia
	public List<Compra> compras = new ArrayList<Compra>();
	public String razon_social;
	public String nombre_ficticio;
	public long cuit;
	public String direccion_postal;
	public String codigo_inscripcion;
	
}
