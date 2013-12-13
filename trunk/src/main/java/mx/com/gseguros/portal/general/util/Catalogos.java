package mx.com.gseguros.portal.general.util;

public enum Catalogos {
	
	AGENTES(""),
	COLONIAS("COLONIAS"),
	MC_ESTATUS_TRAMITE("STATUSTRA"),
	MC_SUCURSALES_ADMIN("TUNIDECO"),
	MC_SUCURSALES_DOCUMENTO("TUNIDECO"),
	MC_TIPOS_TRAMITE("TRAMITES"),
	MOTIVOS_CANCELACION("TRAZCANC"),
	NACIONALIDAD("TNACIONALIDAD"),
	ROLES_POLIZA("TROLES"),
	STATUS_POLIZA("STATUSPOL"),
	TATRISIT("TATRISIT"),
	TATRIPOL("TATRIPOL"),
	TATRIPER("TATRIPER"),
	TATRIGAR("TATRIGAR"),
	TIPOS_PERSONA("TTIPOPERSONA"),
	TIPOS_PAGO_POLIZA("TPERPAG"),
	TIPOS_POLIZA("TIPOPOL"),
	TIPSIT("TIPSIT"),
	RAMOS("RAMOS"),
	ROLES_SISTEMA("");

	private String cdTabla;

	private Catalogos(String cdTabla) {
		this.cdTabla = cdTabla;
	}

	public String getCdTabla() {
		return cdTabla;
	}
}