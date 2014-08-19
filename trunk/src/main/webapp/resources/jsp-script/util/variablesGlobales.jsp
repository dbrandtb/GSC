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
	
</script>