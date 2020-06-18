package dominio.compra;

import com.sun.jersey.api.client.ClientResponse;

public class DireccionPostal {

	private APImercado mercado_libre;
	
	public ClientResponse pais(String codigo_pais) {
		return mercado_libre.getInfoDePais(codigo_pais);
	}
	
	public String provincia(String provincia) {
		return mercado_libre.obtenerProvinciaDeZip(provincia);
	}
	
	public String direccion(String direccion) {
		return direccion;
	}
	
}
