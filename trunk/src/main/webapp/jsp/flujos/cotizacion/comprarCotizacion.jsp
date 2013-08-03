<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<style type="text/css">    
       
        .alinear {
        margin-left: auto; margin-right: auto; 
        }
                
    </style>
<title>Comprar Cotizacion</title>
    <link href="${ctx}/resources/extjs/resources/css/Multiselect.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/resources/extjs/ux/Multiselect.js"/></script>
    <script type="text/javascript" src="${ctx}/resources/extjs/ux/DDView.js"/></script>
   
    <script type="text/javascript">
        var _CONTEXT = '${ctx}';
		var _MSG_AVISO	= 'Aviso';
		var _MSG_NO_ROW_SELECTED= 'Debe seleccionar un registro para realizar esta operacion';
        var _CDRAMO = '<s:property value="#session.CCCDRAMO" />';
        var _CDTIPSIT = '<s:property value="#session.CCCDTIPSIT" />';
        
        var CD_SISROL='<s:property value="cdSisrol" />';
        
		var _ACTION_RESULTADO_COTIZACION = '<s:url namespace="/flujocotizacion" action="resultCotizacion" />';

		var _ACTION_PAGINA_PRINCIPAL = '<s:url namespace='/' action="load" />';
	</script>
    
    <script type="text/javascript">
        <%@ include file="/resources/jsp-script/flujos/cotizacion/comprarCotizacion-script.jsp"%>
    </script>
</head>
<body>
<div id="form-ct-multiselect"></div>
<div id="items"></div>
</body>
</html>