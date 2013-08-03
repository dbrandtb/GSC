<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">    
        .alinear { margin-left: auto; margin-right: auto; }
</style>
<title>Consulta de poliza</title>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
    <script type="text/javascript">
        var _CONTEXT = "${ctx}";
        var MPOLIEX= "<s:property value='nmpoliex'/>";
        
        var _ACTION_EXPORT = "<s:url action='exportGenericoParams' namespace='/principal'/>";
        var _ACTION_IR_CANCELACION_MANUAL = "<s:url action='irCancelacionManual' namespace='/procesoemision'/>";
        var _ACTION_VALIDA_ENDOSO = "<s:url action='validacionPolizaEndosos' namespace='/procesoemision'/>";
		
    	var _SESSION_PARAMETROS_REGRESAR = null;
    	<s:if test="%{#session.containsKey('PARAMETROS_REGRESAR')}">
	    	if ( "<s:property value='#session.PARAMETROS_REGRESAR.clicBotonRegresar' />" == "S" ) {
    			_SESSION_PARAMETROS_REGRESAR = {
	    			idRegresar: "<s:property value='#session.PARAMETROS_REGRESAR.idRegresar' />",
    				producto: "<s:property value='#session.PARAMETROS_REGRESAR.producto' />",
    				aseguradora: "<s:property value='#session.PARAMETROS_REGRESAR.aseguradora' />",
    				poliza: "<s:property value='#session.PARAMETROS_REGRESAR.poliza' />",
    				vigencia: "<s:property value='#session.PARAMETROS_REGRESAR.fechaInicioFin' />",
    				asegurado: "<s:property value='#session.PARAMETROS_REGRESAR.nombreAsegurado' />",
    				desde: "<s:property value='#session.PARAMETROS_REGRESAR.inicioVigencia' />",
    				hasta: "<s:property value='#session.PARAMETROS_REGRESAR.finVigencia' />",
    				inciso: "<s:property value='#session.PARAMETROS_REGRESAR.cdInciso' />",
    				estatus: "<s:property value='#session.PARAMETROS_REGRESAR.statusPoliza' />"
    			};
    		}
    	</s:if>
        
    </script>
    <script type="text/javascript" src="${ctx}/resources/jsp-script/procesos/emision/busquedaPoliza-script.js"/></script>
  
</head>
<body>

<div id="items"></div>
</body>
</html>