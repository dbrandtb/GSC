package mx.com.gseguros.portal.general.util;

public enum TipoSituacion {
	
	SALUD_VITAL("SL"),
	SALUD_NOMINA("SN"),
	AUTOS_FRONTERIZOS("AF")
	;

	private String cdtipsit;

	private TipoSituacion(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}

	public String getCdtipsit() {
		return cdtipsit;
	}
	
}