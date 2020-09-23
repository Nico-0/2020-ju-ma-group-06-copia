package dominio.compra;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DocumentoComercial {

	@Id
	@GeneratedValue
	private Long id;
	
	private int numero_documento;
	private TipoDocumentoComercial tipoDocumentoComercial;
}
