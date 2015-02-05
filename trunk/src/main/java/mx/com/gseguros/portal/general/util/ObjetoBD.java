package mx.com.gseguros.portal.general.util;
/**
 * Enum de Objetos de Base de Datos
 */
public enum ObjetoBD {
	
	BORRAR_MPOLIPER                 ("PKG_SATELITES.P_BORRA_MPOLIPER"              , "SP"),
	CARGAR_CENSO                    ("PKG_SATELITES.P_LAYOUT_CENSO_MS_COLECTIVO"   , "SP"),
	CARGAR_CENSO_AGRUPADO           ("PKG_SATELITES.P_LAYOUT_CENSO_MS_COLEC_AGRUP" , "SP"),
	GENERAR_CDPERSON                ("PKG_COTIZA.P_GET_CDPERSON"                   , "SP"),
	MOV_MPERSONA                    ("PKG_SATELITES.P_MOV_MPERSONA"                , "SP"),
	MOV_MPOLIPER                    ("PKG_SATELITES.P_MOV_MPOLIPER"                , "SP"),
	OBTIENE_COBERTURAS_X_PLAN       ("PKG_LISTAS.P_GET_COBERTURAS"                 , "SP"),
	OBTIENE_CDAGENTE_POLIZA         ("PKG_CONSULTA.P_GET_CDAGENTE"                 , "SP"),
	OBTIENE_DATOS_FACTURAS          ("PKG_CONSULTA.P_GET_DATOS_FACTURAS"           , "SP"),
	OBTIENE_DATOS_PROVEEDORES       ("PKG_CONSULTA.P_GET_DATOS_PROVEEDORES"        , "SP"),
	OBTIENE_DATOS_REEXPED_DOC       ("PKG_CONSULTA.P_OBTIENE_DATOS_REEXPED_DOC"    , "SP"),
	OBTIENE_DATOS_WS_COTIZACION_AUTO ("PKG_CONSULTA.P_WS_COTIZACION_AUTOS"         , "SP"),
	OBTIENE_DATOS_WS_COTIZACION_SRV_PUBLICO ("PKG_CONSULTA.P_WS_COTIZACION_SERV_PUBLICO", "SP"),
	OBTIENE_DATOS_WS_COTIZACION_RESIDENTES ("PKG_CONSULTA.P_WS_AUTOS_RESIDENTES"   , "SP"),
	OBTIENE_DETALLE_COTI_GRUPO      ("PKG_COTIZA.P_GET_DETALLE_COTI_GRUPO"         , "SP"),
	OBTIENE_EMAIL                   ("PKG_CONSULTA.P_OBTIENE_EMAIL"                , "SP"),
	OBTIENE_FACTOR_CONVENIDO        ("PKG_LISTAS.P_GET_FACTOR_CONVENIDO"           , "SP"),
	OBTIENE_MPERSONA_COTIZACION     ("PKG_COTIZA.P_OBTIENE_MPERSONA_COTIZACION"    , "SP"),
	OBTIENE_PLANES_X_PRODUCTO       ("PKG_LISTAS.P_GET_PLANES"                     , "SP"),
	OBTIENE_RETROACTIVIDAD_TIPSUP   ("PKG_SATELITES.P_OBT_RETRO_DIFER"             , "SP"),
	OBTIENE_TVALOSIT_COTIZACION     ("PKG_COTIZA.P_OBTIENE_TVALOSIT_COTIZACION"    , "SP"),
	VALIDA_CANC_A_PRORRATA          ("PKG_SATELITES.P_VALIDA_CANC_A_PRORRATA"      , "SP"),
	VALIDA_CARGAR_COTIZACION        ("PKG_COTIZA.P_VALIDA_CARGAR_COTIZACION"       , "SP"),
	VALIDA_EDAD_ASEGURADOS          ("PKG_CONSULTA.P_VALIDA_EDAD_ASEGURADOS"       , "SP");
	

	/**
	 * Nombre del objeto de BD
	 */
	private String nombre;
	
	/**
	 * Tipo del objeto de BD
	 */
	private String tipo;

	private ObjetoBD(String nombre, String tipo) {
		this.nombre = nombre;
		this.tipo   = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTipo() {
		return tipo;
	}
}