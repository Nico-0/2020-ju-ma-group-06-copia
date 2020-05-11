package dominio;

import java.util.ArrayList;
import java.util.List;

public class EntidadBase implements Entidad {

	public List<Compra> compras = new ArrayList<Compra>();
	public String nombre_ficticio;
	public String descripcion;
	public EntidadJuridica entidad_juridica; //para mi deberia conocer a la entidad juridica si es que la tiene
	
}
