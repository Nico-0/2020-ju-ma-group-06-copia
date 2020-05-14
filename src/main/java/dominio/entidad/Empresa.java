package dominio.entidad;

import dominio.Preconditions;

public class Empresa extends EntidadJuridica {
	private TipoEmpresa tipoEmpresa;

	public Empresa(String razonSocial, String nombreFicticio, String cuit, String direccionPostal,
			TipoEmpresa tipoEmpresa) {
		super(razonSocial, nombreFicticio, cuit, direccionPostal);
		Preconditions.validateNotNull(tipoEmpresa, "tipo empresa faltante");
		this.tipoEmpresa = tipoEmpresa;
	}
}
