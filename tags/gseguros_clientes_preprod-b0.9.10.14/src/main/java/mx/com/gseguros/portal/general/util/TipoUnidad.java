package mx.com.gseguros.portal.general.util;

public enum TipoUnidad {
	
    AUTO_SP("5"),
    AUTOBUS("1"),
    AUTOBUS_ARTICULADO("6"),
    AUTOBUS_TURISMO("7"),
    CAMIONETA_SP("4"),
    CAMIONETA_SP_23P("8"),
    FRONTERIZO("13"),
    MICROBUS("2"),
    MIDIBUS("3");

	private String clave;

	private TipoUnidad(String clave) {
		this.clave = clave;
	}

	public String getClave() {
		return clave;
	}
	
}