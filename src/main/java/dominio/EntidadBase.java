package dominio;

import java.util.ArrayList;
import java.util.List;

public class EntidadBase implements Entidad {

	private List<Compra> compras = new ArrayList<Compra>();
	private String nombre_ficticio;
	private String descripcion;
	private EntidadJuridica entidad_juridica; //para mi deberia conocer a la entidad juridica si es que la tiene
	
}
