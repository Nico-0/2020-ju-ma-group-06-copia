package dominio;

public class Item {

	private String descripcion;
	private int valorTotal;         //lo cambié por ahora de int a float por la precisión
	
	public int get_valor_total(){
		return valorTotal;
	}
	
	public Item(String descripcion, int valorTotal) throws PreconditionFailed {
		Preconditions.validateNotNull(descripcion,"descripcion faltante");
		Preconditions.validateNotNull(valorTotal,"valor total faltante");
		this.descripcion = descripcion;
		this.valorTotal = valorTotal;
	}
	
}
