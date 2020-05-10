package dominio;

import java.util.ArrayList;
import java.util.List;

public class Compra {
	
	public List<Item> items = new ArrayList<Item>();
	public Proveedor proveedor;
	public MedioPago medio_pago;
	public DocumentoComercial doc_comercial; //puede ser nulo
	
}
