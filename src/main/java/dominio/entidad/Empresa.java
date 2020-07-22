package dominio.entidad;

import java.util.List;

import org.apache.commons.lang3.Validate;

public class Empresa extends EntidadJuridica {
	private TipoEmpresa tipoEmpresa;

	public Empresa(String razonSocial, String nombreFicticio, String cuit, String direccionPostal,
			TipoEmpresa tipoEmpresa, List<EntidadBase> entidades, Categoria categoria) {
		super(razonSocial, nombreFicticio, cuit, direccionPostal, entidades, categoria);
		Validate.notNull(tipoEmpresa, "tipo empresa faltante");
		this.tipoEmpresa = tipoEmpresa;
	}
}
