package mx.com.gseguros.portal.general.util;

public enum TipoSituacion {
	
	AUTOS_FRONTERIZOS("AF"),
	MULTISALUD       ("MS"),
	SALUD_NOMINA     ("SN"),
	SALUD_VITAL      ("SL")
	;

	private String cdtipsit;

	private TipoSituacion(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}

	public String getCdtipsit() {
		return cdtipsit;
	}
	
}