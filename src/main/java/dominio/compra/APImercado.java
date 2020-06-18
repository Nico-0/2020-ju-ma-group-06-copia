package dominio.compra;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;
import org.json.*;

public class APImercado {
    private Client client;
    private static final String API_MERCADO = "https://api.mercadolibre.com/";
    private static final String DETALLE_PAIS = "classified_locations/countries/";
    private static final String DETALLE_CIUDAD = "classified_locations/cities/";
    private static final String CONVERTIR = "currency_conversions/search/";
    private static final String CODIGO_POSTAL = "countries/AR/zip_codes/";
    
    //Inicializacion del cliente.
    public APImercado() {
        this.client = Client.create();
        //En la documentacion se puede ver como al cliente agregarle un ClientConfig
        //para agregarle filtros en las respuestas (por ejemplo, para loguear).
    }


    public ClientResponse getInfoDePais(String codigo_pais){
        WebResource recurso = this.client.resource(API_MERCADO).path(DETALLE_PAIS + codigo_pais);
        WebResource.Builder builder = recurso.accept(MediaType.APPLICATION_JSON);
        return builder.get(ClientResponse.class);
    }
    
    public String obtenerMonedaPais(String codigo_pais) {
    	ClientResponse response = this.getInfoDePais(codigo_pais);
    	String json = response.getEntity(String.class);
    	JSONObject obj = new JSONObject(json);
    	return obj.getString("currency_id");
    }
    
    public ClientResponse getInfoDeCiudad(String codigo_ciudad){
        WebResource recurso = this.client.resource(API_MERCADO).path(DETALLE_CIUDAD + codigo_ciudad);
        WebResource.Builder builder = recurso.accept(MediaType.APPLICATION_JSON);
        return builder.get(ClientResponse.class);
    }
    
    public String obtenerProvinciaDeCiudad(String codigo_ciudad) {
    	ClientResponse response = this.getInfoDeCiudad(codigo_ciudad);
    	String json = response.getEntity(String.class);
    	JSONObject obj = new JSONObject(json);
    	return obj.getJSONObject("state").getString("name");
    }
    
    public String obtenerPaisDeCiudad(String codigo_ciudad) {
    	ClientResponse response = this.getInfoDeCiudad(codigo_ciudad);
    	String json = response.getEntity(String.class);
    	JSONObject obj = new JSONObject(json);
    	return obj.getJSONObject("country").getString("name");
    }
    
    public String obtenerCiudadDeID(String codigo_ciudad) {
    	ClientResponse response = this.getInfoDeCiudad(codigo_ciudad);
    	String json = response.getEntity(String.class);
    	JSONObject obj = new JSONObject(json);
    	return obj.getString("name");
    }
    
    public double convertirMoneda(double cantidad, String moneda, String moneda_destino) {
        WebResource recurso = this.client.resource(API_MERCADO).path(CONVERTIR);
        WebResource recursoConParametros = recurso.queryParam("from", moneda).queryParam("to", moneda_destino);
        WebResource.Builder builder = recursoConParametros.accept(MediaType.APPLICATION_JSON);
        ClientResponse response = builder.get(ClientResponse.class);
        String json = response.getEntity(String.class);
        JSONObject obj = new JSONObject(json);
    	double ratio = obj.getDouble("ratio");
    	
    	return cantidad * ratio;
    }
    
    public String obtenerProvinciaDeZip(String zip) {
        WebResource recurso = this.client.resource(API_MERCADO).path(CODIGO_POSTAL + zip);
        WebResource.Builder builder = recurso.accept(MediaType.APPLICATION_JSON);
        ClientResponse response = builder.get(ClientResponse.class);	
        String json = response.getEntity(String.class);
        JSONObject obj = new JSONObject(json);
    	
    	return obj.getJSONObject("state").getString("name");
    }
    
    
    
    
}

