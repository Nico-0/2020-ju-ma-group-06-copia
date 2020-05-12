package dominio;

import java.util.ArrayList;
import java.util.List;

public class Compra {
	
	private List<Item> items = new ArrayList<Item>();
	private Proveedor proveedor;
	private MedioPago medio_pago;
	private DocumentoComercial doc_comercial; //puede ser nulo
	private java.util.Date fecha = new java.util.Date();
	private Entidad entidad;
	
	public int valor_total(){    
		return items.stream().mapToInt(item -> item.get_valor_total()).sum();
	}
	
}
