<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
	    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	    <title>Ext JS 4 Dashboard</title>
	    
	    <!-- Ext JS Files -->
	    <!-- <link rel="stylesheet" href="extjs4/resources/ext-theme-neptune/ext-theme-neptune-all.css" /> -->
	    <%-- <link rel="stylesheet" href="../resources/extjs4/resources/my-custom-theme/my-custom-theme-all.css" />
	    <script src="../resources/extjs4/ext-all-debug.js"></script> --%>
	    
	    <!-- Archivos de la aplicacion -->
	    <script type="text/javascript">
		    var _CONTEXT = '${ctx}';
		    
	        var _URL_TIPOS_CONSULTA         =       _CONTEXT + '/resources/scripts/consultaPolizas/tiposConsulta.json';
	        
		    var _URL_CONSULTA_DATOS_POLIZA =        '<s:url action="consultaDatosPoliza"       namespace="/consultasPoliza"/>';
		    var _URL_CONSULTA_DATOS_SUPLEMENTO =    '<s:url action="consultaDatosSuplemento"   namespace="/consultasPoliza"/>';
		    var _URL_CONSULTA_DATOS_TARIFA_POLIZA = '<s:url action="consultaDatosTarifaPoliza" namespace="/consultasPoliza"/>';
		    var _URL_CONSULTA_DATOS_ASEGURADO =     '<s:url action="consultaDatosAsegurado"    namespace="/consultasPoliza"/>';
		    var _URL_CONSULTA_POLIZAS_ASEGURADO =   '<s:url action="consultaPolizasAsegurado"  namespace="/consultasPoliza"/>';
		    var _URL_CONSULTA_DOCUMENTOS =          '<s:url action="ventanaDocumentosPoliza"   namespace="/documentos" />';
		    var _URL_CONSULTA_DATOS_AGENTE =        '<s:url action="ventanaDocumentosPoliza"   namespace="/consultaDatosAgente" />';
		    
		    var _MSG_ERROR =                           'Error';
		    var _MSG_ERROR_HISTORICO_MOVIMIENTOS =     'Ocurri\u00F3 un error al obtener el hist\u00F3rico de movimientos';
		    var _MSG_SIN_DATOS =                       'Sin informaci\u00F3n';
		    var _MSG_SIN_DATOS_HISTORICO_MOVIMIENTOS = 'No se encontraron resultados. Intente cambiar los filtros de b\u00FAsqueda.';
	    
		    //Funciones comunes
		    /**
		     * Muestra un mensaje emergente
		     * @param title   String
		     * @param msg     String
		     * @param buttons Object/Object[]
		     * @param icon    String 
		     */
		    function setMessage(title, msg, buttons, icon){
		    	Ext.Msg.show({
	                title: title,
	                msg: msg,
	                buttons: buttons,
	                icon: icon
	            });
		    }
	    
	    </script>
	    <script type="text/javascript" src="${ctx}/resources/scripts/consultaPolizas/consultasPolizas.js"></script>
	    
    </head>
    <body>
    </body>
</html>