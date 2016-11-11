package mx.com.gseguros.portal.general.util;

public enum TipoSituacion {
	
	AUTOS_FRONTERIZOS         ("AF"),
	AUTOS_PICK_UP             ("PU"),
	AUTOS_RESIDENTES          ("AR"),
	CAMIONES_CARGA            ("CR"),
	PICK_UP_CARGA             ("PC"),
	PICK_UP_PARTICULAR        ("PP"),
	MULTISALUD                ("MS"),
	MULTISALUD_COLECTIVO      ("MSC"),
	SALUD_NOMINA              ("SN"),
	SALUD_VITAL               ("SL"),
	SERVICIO_PUBLICO_AUTO     ("AT"),
	SERVICIO_PUBLICO_MICRO    ("MC"),
	RECIPERA_INDIVIDUAL       ("RI"),
	RECUPERA_COLECTIVO        ("RC"),
	GASTOS_MEDICOS_INDIVIDUAL ("GMI")
	;

	private String cdtipsit;

	private TipoSituacion(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}

	public String getCdtipsit() {
		return cdtipsit;
	}
	
}