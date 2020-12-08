package dominio.compra;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class DocumentoComercial {

	@Id
	@GeneratedValue
	private Long id;
	
	private int numeroDocumento;
	private TipoDocumentoComercial tipoDocumentoComercial;
	
	@Transient
	String texto;
	
	public DocumentoComercial() {
		
	}

	public void setNumeroDocumento(int numeroDocumento){
		this.numeroDocumento = numeroDocumento;
	}
	
	public void setTipoDocumentoComercial(TipoDocumentoComercial tipoDocumentoComercial){
		this.tipoDocumentoComercial = tipoDocumentoComercial;
	}
	
    public long getId(){
    	  return this.id;
    }
    
    public String getDocumentoComercial(){
    	texto = " Tipo de documento comercial: " + tipoDocumentoComercial.toString() + "</br>";
    	if(!tipoDocumentoComercial.equals(TipoDocumentoComercial.SIN_DOCUMENTO))
    		texto = texto + "Numero de documento: " + String.valueOf(numeroDocumento) + "</br>";
		return texto;
	}
    
    public TipoDocumentoComercial getTipoDocumentoComercial(){
		return tipoDocumentoComercial;
	}

	public int getNumeroDocumento() {
		return numeroDocumento;
	}
}
