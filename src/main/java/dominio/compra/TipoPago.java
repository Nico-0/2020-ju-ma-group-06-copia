package dominio.compra;

public enum TipoPago{
	  TARJETA_CREDITO{
			public String toString() {
				return "Tarjeta de Credito";
			}
	  },
	  TARJETA_DEBITO{
			public String toString() {
				return "Tarjeta de Debito";
			}
	  },
	  EFECTIVO{
			public String toString() {
				return "Efectivo";
			}
	  },
	  CAJERO_AUTOMATICO{
			public String toString() {
				return "Cajero Automatico";
			}
	  },
	  DINERO_CUENTA{
			public String toString() {
				return "Dinero Cuenta";
			}
	  }
}