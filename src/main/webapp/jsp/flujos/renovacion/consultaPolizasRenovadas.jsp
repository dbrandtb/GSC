<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
    <style type="text/css">    
        .alinear {margin-left: auto; margin-right: auto;}
    </style>
<title>Pólizas Renovadas</title>
    <script type="text/javascript">
        var _CONTEXT = "${ctx}";
		var POLIZAS_RENOVADAS=false;

        var _ACTION_EXPORT = "<s:url action='exportGenericoParams' namespace='/principal'/>";
        var _COMBO_PRODUCTO = "<s:url action='getComboProducto' namespace='/flujorenovacion'/>";
        var _COMBO_CLIENTE = "<s:url action='getComboCliente' namespace='/flujorenovacion'/>";
        var _COMBO_ASEGURADORA = "<s:url action='getComboAsegradora' namespace='/flujorenovacion'/>";
        var _COMBO_TIPO_POLIZA=  "<s:url action='getComboTiposPoliza' namespace='/flujorenovacion'/>";
        var _ACTION_VALIDA_ENDOSO=  "<s:url action='validacionPolizaEndososRenovacion' namespace='/flujorenovacion'/>";
        var _OBTIENE_POLIZAS_RENOVADAS = "<s:url action='obtienePolizasRenovadas' namespace='/flujorenovacion'/>";

		var _SESSION_PARAMETROS_REGRESAR = null;
		<s:if test="%{#session.containsKey('PARAMETROS_REGRESAR')}">
			if ( "<s:property value='#session.PARAMETROS_REGRESAR.clicBotonRegresar' />" == "S" ) {
				_SESSION_PARAMETROS_REGRESAR = {
					idRegresar: "<s:property value='#session.PARAMETROS_REGRESAR.idRegresar' />",
					asegurado: "<s:property value='#session.PARAMETROS_REGRESAR.Asegurado' />",
					aseguradora: "<s:property value='#session.PARAMETROS_REGRESAR.cdUnieco' />",
					producto: "<s:property value='#session.PARAMETROS_REGRESAR.cdRamo' />",
					poliza: "<s:property value='#session.PARAMETROS_REGRESAR.nmPoliEx' />",
					nivel: "<s:property value='#session.PARAMETROS_REGRESAR.cdElemento' />",
					estado: "<s:property value='#session.PARAMETROS_REGRESAR.estado' />"
				};
			}
    	</s:if>

		var helpMap= new Map();
		var itemsPerPage= _NUMROWS;          
        <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("205")%>
    </script>
    <jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
    
    <script type="text/javascript" src="${ctx}/resources/scripts/flujos/renovacion/consultaPolizasRenovadas.js"/></script>

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
