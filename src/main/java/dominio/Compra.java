package dominio;

import java.util.ArrayList;
import java.util.List;

public class Compra {
	
	public List<Item> items = new ArrayList<Item>();
	public Proveedor proveedor;
	public MedioPago medio_pago;
	public DocumentoComercial doc_comercial; //puede ser nulo
	public java.util.Date fecha = new java.util.Date();
	public float valor_total; //no deberia ser un metodo que retorne la sumatoria de los valores totales de los items?
	public Entidad entidad;
	
}
