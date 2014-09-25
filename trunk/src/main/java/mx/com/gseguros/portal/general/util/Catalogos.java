package mx.com.gseguros.portal.general.util;

public enum Catalogos {
	
	AGENTES(""),
	AGENTES_POR_PROMOTOR(""),
	ANIOS_RENOVACION("ANIORENOVA"),
	CAUSA_SINIESTRO("TCAUSASSV"),
	COBERTURAS(""),
	COBERTURASXTRAMITE(""),
	COBERTURASXVALORES(""),
	CODIGOS_MEDICOS(""),
	COLONIAS(""),
	ENDOSOS(""),
	FORMAS_ASEGURAMIENTO("TFORMASEG"),
	GIROS("TGIROS"),
	ICD("2TABLICD"),
	MC_ESTATUS_TRAMITE("STATUSTRA"),
	MC_SUCURSALES_ADMIN("TUNIDECO"),
	MC_SUCURSALES_DOCUMENTO("TUNIDECO"),
	MC_TIPOS_TRAMITE("TRAMITES"),
	MEDICOS(""),
	MESES("MESES"),
	MOTIVOS_CANCELACION("TRAZCANC"),
	MOTIVOS_RECHAZO_SINIESTRO(""),
	MOTIVOS_REEXPEDICION("TRAZREEXP"),
	NACIONALIDAD("TNACIONALIDAD"),
	PENALIZACIONES("TPENALIZACIONES"),
	PLANES("TPLANES"),
	PLANES_X_PRODUCTO(""),
	PROVEEDORES(""),
	RAMOS(""),
	REFERENCIAS_TRAMITE_NUEVO("TREFERENCIA"),
	RELACION_CONT_ASEG("TRELCONTASEG"),
	REPARTO_PAGO_GRUPO("TREPPAG"),
	ROLES_POLIZA("TROLES"),
	ROLES_RAMO(""),
	ROLES_SISTEMA(""),
	SERVICIO_PUBLICO_AUTOS(""),
	SEXO("TSEXO"),
	SINO(""),
	STATUS_POLIZA("STATUSPOL"),
	SUBCOBERTURAS(""),
	SUBMOTIVOS_RECHAZO_SINIESTRO(""),
	TATRIGAR("TATRIGAR"),
	TATRIPER("TATRIPER"),
	TATRIPOL("TATRIPOL"),
	TATRISIN("TATRISIN"),
	TATRISIT("TATRISIT"),
	TIPO_CONCEPTO_SINIESTROS("TTIPCONC"),
	TIPOS_PAGO_POLIZA("TPERPAG"),
	TIPOS_PAGO_POLIZA_SIN_DXN("TPERPAG_NODXN"),
	TIPOS_PERSONA("TTIPOPERSONA"),
	TIPOS_POLIZA("TIPOPOL"),
	TIPOS_POLIZA_AUTO("TIPOPOLAUTO"),
	TIPSIT("TIPSIT"),
	TIPO_ATENCION_SINIESTROS("TTIPOATENCION"),
	TIPO_PAGO_SINIESTROS("TTIPOPAGO"),
	TIPO_RESIDENCIA("TRESIDENCIA"),
	TRATAMIENTOS("TTRATAMIENTO"),
	TIPO_MONEDA("TMONEDAS"),
	TIPO_MENU("TIPOMENU"),
	DESTINOPAGO("TCVIMPCH"),
	CATCONCEPTO("TCONCPAG"),
	TERRORWS("TERRORWS"),
	TCUMULOS("TCUMULOS"),
	TESTADOS("TESTADOS"),
	MUNICIPIOS(""),
	ZONAS_POR_PRODUCTO(""),
	TZONAS("TZONAS");

	private String cdTabla;

	private Catalogos(String cdTabla) {
		this.cdTabla = cdTabla;
	}

	public String getCdTabla() {
		return cdTabla;
	}
}