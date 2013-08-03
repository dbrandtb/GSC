<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <style type="text/css">    
        .alinear {margin-left: auto; margin-right: auto;}
    </style>
<title>Pólizas a Renovar</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
        var _CONTEXT = "${ctx}";
        var COD_ASEGURADORA = "<s:property value='cdUniEco'/>";
        var CDUNIECO = "<s:property value='cdUnieco'/>";
        var COD_CLIENTE = "<s:property value='cdElemento'/>";
        var COD_PRODUCTO = "<s:property value='cdRamo'/>";

        var _OBTENER_POLIZAS_A_RENOVAR = "<s:url action='obtienePolizas' namespace='/flujorenovacion'/>";
        var _NUEVO_ACTION_EXPORT = "<s:url action='exportarConsultaPolizaRenovar' namespace='/flujorenovacion'/>";
        <!-- combos -->
        var _COMBO_PRODUCTO= "<s:url action='getComboProducto' namespace='/flujorenovacion'/>";
        var _COMBO_CLIENTE = "<s:url action='getComboCliente' namespace='/flujorenovacion'/>";
        var _COMBO_ASEGURADORA = "<s:url action='getComboAsegradora' namespace='/flujorenovacion'/>";

    	var _SESSION_PARAMETROS_REGRESAR = null;
    	<s:if test="%{#session.containsKey('PARAMETROS_REGRESAR')}">
    		if ( "<s:property value='#session.PARAMETROS_REGRESAR.clicBotonRegresar' />" == "S" ) {
    			_SESSION_PARAMETROS_REGRESAR = {
    				idRegresar: "<s:property value='#session.PARAMETROS_REGRESAR.idRegresar' />",
    				asegurado: "<s:property value='#session.PARAMETROS_REGRESAR.Asegurado' />",
    				aseguradora: "<s:property value='#session.PARAMETROS_REGRESAR.cdUnieco' />",
    				producto: "<s:property value='#session.PARAMETROS_REGRESAR.cdRamo' />",
    				poliza: "<s:property value='#session.PARAMETROS_REGRESAR.nmPoliEx' />",
    				nivel: "<s:property value='#session.PARAMETROS_REGRESAR.cdElemento' />"
    			};
    		}
    	</s:if>
	
		//se inicializa de esta forma ya que aun no existe la variable en sesion y de no ser declarada la exportacion no funciona
		//<%=session.getAttribute("helpMap")%>
		var helpMap = new Map();  
		<%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("204")%>
	</script>    

   	<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/flujos/renovacion/consultaPolizasRenovar.js"/></script>
    
</head>
    <body>
        <table cellspacing="10" border="0">
            <tr valign="top">
                <td>
                    <div id="filtrosRenovacion" />
                </td>
            </tr>
            <tr valign="top">
                <td>
                    <div id="listadoRenovacion" />
                </td>
            </tr>
            <tr valign="top">
                <td>
                    <div id="mainRenovacion" />
                </td>
            </tr>
        </table>
    </body>
</html>    
