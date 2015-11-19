<%@ page language="java"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript">

	/* ********** VARIABLES GLOBALES ************** */
	
	var _global_urlConsultaDinamica        = '<s:url namespace="/consultas" action="consultaDinamica" />';
	var _GLOBAL_URL_LOGIN                  = '<s:url namespace="/seguridad" action="login" />';
	var _GLOBAL_URL_LOGOUT                 = '<s:url namespace="/seguridad" action="logout" />';
	var _GLOBAL_URL_MANTENER_SESION_ACTIVA = '<s:url namespace="/seguridad" action="mantenerSesionActiva" />';
	var _GLOBAL_MUESTRA_EXTENSION_TIMEOUT  = '<s:text name="sesion.muestra.extension.timeout"/>';
	var _GLOBAL_OCULTA_EXTENSION_TIMEOUT   = '<s:text name="sesion.oculta.extension.timeout"/>';
	var _GLOBAL_URL_GRABAR_EVENTO          = '<s:url namespace="/servicios" action="grabarEvento" />';
	
	var _GLOBAL_DIRECTORIO_ICONOS         = '${icons}';
	var _GLOBAL_URL_RECUPERACION          = '<s:url namespace="/recuperacion" action="recuperar"           />';
	var _GLOBAL_URL_IMPRIMIR_LOTE         = '<s:url namespace="/consultas"    action="imprimirLote"        />';
	var _GLOBAL_URL_DESCARGAR_LOTE        = '<s:url namespace="/consultas"    action="descargarLote"       />';
	var _GLOBAL_URL_ESPERAR_DESCARGA_LOTE = '<s:url namespace="/consultas"    action="esperarDescargaLote" />';
</script>