<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ICE BROKER</title>

<script language="javascript">
    //var _CONTEXT = "${ctx}";
   
    var _OBTENER_MENSAJES_ALERTA = "<s:url action='buscarMensajesAlerta' namespace='/mecanismoAlerta'/>";
    var _OBTENER_MENSAJES_ALERTA_PANTALLA = "<s:url action='buscarMensajesAlertaPantalla' namespace='/mecanismoAlerta'/>";
    //var itemsPerPage=1;
    /*<%=session.getAttribute("helpMap")%>*/
</script>

<!-- 
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/mecanismoAlerta/mecanismoAlerta.js"></script>
-->
<script language="JavaScript">
function Abrir_ventana (pagina) {
	validarMensajesAlerta();
	//var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=508, height=365, top=85, left=140";
	//window.open(pagina,"",opciones);

	//Llamar a la funcion que muestra alertas en pantalla en las secciones correspondientes
	<s:iterator value="%{#session['SECCIONES_ALERTAS_PANTALLA']}">
		validarMensajesAlertaPantalla('<s:property />');
	</s:iterator>
}
</script>

</head>
<body onload="Abrir_ventana('popup.html')" >

</body>
</html>
