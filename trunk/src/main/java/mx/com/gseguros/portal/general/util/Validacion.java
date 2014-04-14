package mx.com.gseguros.portal.general.util;

public enum Validacion {
	
	/**
	 * Edad m&aacute;xima de cotizaci&oacute;n
	 */
	EDAD_MAX_COTIZACION("1"),
	/**
	 * Longitud m&aacute;xima del nombre del contratante
	 */
	LONGITUD_MAX_CONTRATANTE("2");
	
	private String clave;
	
	private Validacion(String clave) {
		this.clave = clave;
	}

	public String getClave() {
		return clave;
	}
	
}