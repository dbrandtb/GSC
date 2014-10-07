package mx.com.gseguros.portal.general.util;

public enum Ramo {
	
	SALUD_VITAL      ("2"),
	MULTISALUD       ("4"),
	AUTOS_FRONTERIZOS("16");

	private String cdramo;

	private Ramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getCdramo() {
		return cdramo;
	}
	
}