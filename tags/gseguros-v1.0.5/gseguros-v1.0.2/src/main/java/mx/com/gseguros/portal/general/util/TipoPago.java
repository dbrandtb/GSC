package mx.com.gseguros.portal.general.util;

public enum TipoPago {
	
	PAGO_DIRECTO("1"),
	REEMBOLSO("2");

	private String codigo;

	private TipoPago(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
}