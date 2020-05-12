package dominio;

import java.util.ArrayList;
import java.util.List;

public class Compra {
	
	public List<Item> items = new ArrayList<Item>();
	public Proveedor proveedor;
	public MedioPago medio_pago;
	public DocumentoComercial doc_comercial; //puede ser nulo
	public java.util.Date fecha = new java.util.Date();
	public Entidad entidad;
	
	public int valor_total(){    
		return items.stream().mapToInt(item -> item.get_valor_total()).sum();
	}
	
}
