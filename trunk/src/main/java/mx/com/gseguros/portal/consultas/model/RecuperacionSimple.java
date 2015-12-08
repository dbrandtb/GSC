package mx.com.gseguros.portal.consultas.model;

public enum RecuperacionSimple
{
	//NOMBRE                                               L=LISTA/M=MAPA
	 RECUPERAR_CLAUSULAS_POLIZA                              ("L" , "RECUPERAR_CLAUSULAS_POLIZA")
	,RECUPERAR_COBERTURAS_ENDOSO_DEVOLUCION_PRIMAS           ("L" , "RECUPERAR_COBERTURAS_ENDOSO_DEVOLUCION_PRIMAS")
	,VERIFICAR_CODIGO_POSTAL_FRONTERIZO                      ("M" , "VERIFICAR_CODIGO_POSTAL_FRONTERIZO")
	,RECUPERAR_CONFIGURACION_VALOSIT_FLOTILLAS               ("L" , "RECUPERAR_CONFIGURACION_VALOSIT_FLOTILLAS")
	,RECUPERAR_CONTEO_BLOQUEO                                ("M" , "RECUPERAR_CONTEO_BLOQUEO")
	,RECUPERAR_DATOS_VEHICULO_RAMO_5                         ("M" , "RECUPERAR_DATOS_VEHICULO_RAMO_5")
	,RECUPERAR_DERECHOS_POLIZA_POR_PAQUETE_RAMO_1            ("M" , "RECUPERAR_DERECHOS_POLIZA_POR_PAQUETE_RAMO_1")
	,RECUPERAR_DESCUENTO_RECARGO_RAMO_5                      ("M" , "RECUPERAR_DESCUENTO_RECARGO_RAMO_5")
	,RECUPERAR_DETALLE_IMPRESION_LOTE                        ("M" , "RECUPERAR_DETALLE_IMPRESION_LOTE")
	,RECUPERAR_DETALLE_REMESA                                ("L" , "RECUPERAR_DETALLE_REMESA")
	,RECUPERAR_DETALLES_COBERTURAS_COTIZACION_AUTOS_FLOTILLA ("L" , "RECUPERAR_DETALLES_COBERTURAS_COTIZACION_AUTOS_FLOTILLA")
	,RECUPERAR_DETALLES_COTIZACION_AUTOS_FLOTILLA            ("L" , "RECUPERAR_DETALLES_COTIZACION_AUTOS_FLOTILLA")
	,RECUPERAR_DSATRIBUS_TATRISIT                            ("M" , "RECUPERAR_DSATRIBUS_TATRISIT")
	,RECUPERAR_ENDOSOS_CANCELABLES                           ("L" , "RECUPERAR_ENDOSOS_CANCELABLES")
	,RECUPERAR_ENDOSOS_REHABILITABLES                        ("L" , "RECUPERAR_ENDOSOS_REHABILITABLES")
	,RECUPERAR_FAMILIAS_POLIZA                               ("L" , "RECUPERAR_FAMILIAS_POLIZA")
	,RECUPERAR_FECHAS_LIMITE_ENDOSO                          ("M" , "RECUPERAR_FECHAS_LIMITE_ENDOSO")
	,RECUPERAR_GRUPOS_POLIZA                                 ("L" , "RECUPERAR_GRUPOS_POLIZA")
	,RECUPERAR_HISTORICO_POLIZA                              ("L" , "RECUPERAR_HISTORICO_POLIZA")
	,RECUPERAR_IMPRESIONES_DISPONIBLES                       ("M" , "RECUPERAR_IMPRESIONES_DISPONIBLES")
	,RECUPERAR_IMPRESORAS                                    ("L" , "RECUPERAR_IMPRESORAS")
	,RECUPERAR_INCISOS_POLIZA_GRUPO_FAMILIA                  ("L" , "RECUPERAR_INCISOS_POLIZA_GRUPO_FAMILIA")
	,RECUPERAR_MOVIMIENTOS_ENDOSO_ALTA_BAJA_ASEGURADO        ("L" , "RECUPERAR_MOVIMIENTOS_ENDOSO_ALTA_BAJA_ASEGURADO")
	,RECUPERAR_MPOLIPER_OTROS_ROLES_POR_NMSITUAC             ("L" , "RECUPERAR_MPOLIPER_OTROS_ROLES_POR_NMSITUAC")
	,RECUPERAR_PERMISO_USUARIO_DEVOLUCION_PRIMAS             ("M" , "RECUPERAR_PERMISO_USUARIO_DEVOLUCION_PRIMAS")
	,RECUPERAR_PERMISOS_IMPRESION                            ("L" , "RECUPERAR_PERMISOS_IMPRESION")
	,RECUPERAR_POLIZAS_ENDOSABLES                            ("L" , "RECUPERAR_POLIZAS_ENDOSABLES")
	,RECUPERAR_POLIZAS_PARA_EXPLOTAR_DOCS                    ("L" , "RECUPERAR_POLIZAS_PARA_EXPLOTAR_DOCS")
	,RECUPERAR_PORCENTAJE_RECARGO_POR_PRODUCTO               ("M" , "RECUPERAR_PORCENTAJE_RECARGO_POR_PRODUCTO")
	,RECUPERAR_RECIBOS_PARA_EXPLOTAR_DOCS                    ("L" , "RECUPERAR_RECIBOS_PARA_EXPLOTAR_DOCS")
	,RECUPERAR_REVISION_COLECTIVOS                           ("L" , "RECUPERAR_REVISION_COLECTIVOS")
	,RECUPERAR_TATRISIT_AMPARADO                             ("M" , "RECUPERAR_TATRISIT_AMPARADO")
	,RECUPERAR_TEXTO_CLAUSULA_POLIZA                         ("M" , "RECUPERAR_TEXTO_CLAUSULA_POLIZA")
	,RECUPERAR_TVALOSIT                                      ("L" , "RECUPERAR_TVALOSIT")
	,RECUPERAR_ULTIMO_NMSUPLEM                               ("M" , "RECUPERAR_ULTIMO_NMSUPLEM")
	,RECUPERAR_USUARIOS_REASIGNACION_TRAMITE                 ("L" , "RECUPERAR_USUARIOS_REASIGNACION_TRAMITE")
	,RECUPERAR_VALOR_ATRIBUTO_UNICO                          ("M" , "RECUPERAR_VALOR_ATRIBUTO_UNICO")
	,RECUPERAR_VALOR_MAXIMO_SITUACION_POR_ROL                ("M" , "RECUPERAR_VALOR_MAXIMO_SITUACION_POR_ROL")
	,RECUPERAR_VALORES_ATRIBUTOS_FACTORES                    ("L" , "RECUPERAR_VALORES_ATRIBUTOS_FACTORES")
	,RECUPERAR_VALORES_PANTALLA                              ("L" , "RECUPERAR_VALORES_PANTALLA")
	,RECUPERAR_TTIPTRAMC                                     ("L" , "RECUPERAR_TTIPTRAMC")  // MC
	,RECUPERAR_TTIPFLUMC 									 ("L" , "RECUPERAR_TTIPFLUMC")  // MC
	,RECUPERAR_TESTADOMC									 ("L" , "RECUPERAR_TESTADOMC")  // MC
	,RECUPERAR_TPANTMC										 ("L" , "RECUPERAR_TPANTMC")   // MC
	,RECUPERAR_TCOMPMC										 ("L" , "RECUPERAR_TCOMPMC")    // MC
	,RECUPERAR_TPROCMC 									 	 ("L" , "RECUPERAR_TPROCMC")    // MC
	,RECUPERAR_TDOCUME                                       ("L" , "RECUPERAR_TDOCUME")    // MC
	,RECUPERAR_TICONOS                                       ("L" , "RECUPERAR_TICONOS")    // MC
	,RECUPERAR_TFLUJOMC                                      ("L" , "RECUPERAR_TFLUJOMC")   // MC
	,RECUPERAR_TFLUEST 									 	 ("L" , "RECUPERAR_TFLUEST")    // MC
	,RECUPERAR_TFLUESTROL									 ("L" , "RECUPERAR_TFLUESTROL") // MC
	,RECUPERAR_TFLUESTAVI									 ("L" , "RECUPERAR_TFLUESTAVI") // MC
    ,RECUPERAR_TFLUPANT                                      ("L" , "RECUPERAR_TFLUPANT")   // MC
	,RECUPERAR_TFLUCOMP                                      ("L" , "RECUPERAR_TFLUCOMP")   // MC
	,RECUPERAR_TFLUPROC                                      ("L" , "RECUPERAR_TFLUPROC")   // MC
	,RECUPERAR_TFLUVAL 									 	 ("L" , "RECUPERAR_TFLUVAL")    // MC
	,RECUPERAR_TFLUREV										 ("L" , "RECUPERAR_TFLUREV")    // MC
	,RECUPERAR_TFLUREVDOC									 ("L" , "RECUPERAR_TFLUREVDOC") // MC
	,RECUPERAR_TFLUACC                                       ("L" , "RECUPERAR_TFLUACC")    // MC
	,RECUPERAR_TFLUACCROL                                    ("L" , "RECUPERAR_TFLUACCROL") // MC
	,RECUPERAR_ROLES                                         ("L" , "RECUPERAR_ROLES")
	;

	 
	private String tipo;
	private String procedimiento;

	private RecuperacionSimple(String tipo, String procedimiento)
	{
		this.tipo          = tipo;
		this.procedimiento = procedimiento;
	}

	public String getProcedimiento() {
		return procedimiento;
	}

	public String getTipo() {
		return tipo;
	}
	
}