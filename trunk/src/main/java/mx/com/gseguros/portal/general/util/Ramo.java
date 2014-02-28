package mx.com.gseguros.portal.general.util;

public enum Ramo {
	
	SALUD_VITAL("2");

	private String cdramo;

	private Ramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getCdramo() {
		return cdramo;
	}
	
}