package mx.com.gseguros.portal.general.util;

public enum EstatusTramite {
	
	EN_REVISION_MEDICA("1"),
	PENDIENTE("2"),
	CONFIRMADO("3"),
	RECHAZADO("4"),
	VO_BO_MEDICO("5"),
	SOLICITUD_MEDICA("6"),
	EN_CAPTURA("7"),
	ENDOSO_EN_ESPERA("8"),
	ENDOSO_CONFIRMADO("9"),
	EN_ESPERA_DE_ASIGNACION("10");

	private String codigo;

	private EstatusTramite(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
}