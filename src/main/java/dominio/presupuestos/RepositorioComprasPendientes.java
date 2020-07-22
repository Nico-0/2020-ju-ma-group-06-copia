package dominio.presupuestos;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;


public class RepositorioComprasPendientes {
	private static RepositorioComprasPendientes INSTANCE = null;
	List<CompraPendiente> comprasPendientes = new ArrayList<>();
	
	public static RepositorioComprasPendientes getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new RepositorioComprasPendientes();
		}
		return INSTANCE;
	}
	
	public List<CompraPendiente> todas() {
		return this.comprasPendientes;
	}
	
    public void validarCompras() {
    	List<CompraPendiente> comprasValidas = comprasPendientes.stream().filter(CompraPendiente::verificarQueEsValida).collect(toList());
    	comprasValidas.stream().forEach(CompraPendiente::validarCompra);
       	
       	this.comprasPendientes.removeIf(pendiente -> comprasValidas.contains(pendiente));
    }
    
}
