<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<style type="text/css">    
 
        #impresionConsultaCotizacion { 
			visibility:hidden;
			height:0px;
			width:0px;
		}
                
</style>
    
<%-- 
La hoja de estilo de print.css es para los estilos de impresion de la ventana de ayuda de las coberturas
--%>

<link href="${ctx}/resources/css/print.css" rel="stylesheet" type="text/css" media="print" />


<title>Consulta de datos de Cotizacion</title>
	<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
    <script type="text/javascript">
		var _CONTEXT = "${ctx}";
	</script>
	<script type="text/javascript" src="${ctx}/resources/scripts/util/confirmaPassword.js"></script>
    <%@ include file="/resources/jsp-script/flujos/cotizacion/consultaCotizacion-script.jsp"%>  
</head>
<body>
<div id="impresionConsultaCotizacion"></div>
<div id="items"></div>
</body>
</html>