<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configurar Elementos de Estructura</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_AYUDA_COBERTURA = "<s:url action='buscarAyudaCoberturas' namespace='/ayudaCoberturas'/>";
    var _ACTION_GET_AYUDA_COBERTURA = "<s:url action='getAyudaCoberturas' namespace='/ayudaCoberturas'/>";
    var _ACTION_INSERTAR_NUEVA_AYUDA_COBERTURA = "<s:url action='insertarAyudaCoberturas' namespace='/ayudaCoberturas'/>";
    var _ACTION_GUARDAR_AYUDA_COBERTURA = "<s:url action='guardarAyudaCoberturas' namespace='/ayudaCoberturas'/>";
    var _ACTION_BORRAR_AYUDA_COBERTURA = "<s:url action='borrarAyudaCoberturas' namespace='/ayudaCoberturas'/>";
    var _ACTION_COPIAR_AYUDA_COBERTURA = "<s:url action='copiarAyudaCoberturas' namespace='/ayudaCoberturas'/>";
    var _ACTION_EXPORTAR_AYUDA_COBERTURA = "<s:url action='exportarAyudaCoberturas' namespace='/ayudaCoberturas'/>";
 
// Combos url de los combos
    var _ACTION_OBTENER_ASEGURADORAS = "<s:url action='obtenerAseguradoras' namespace='/combos'/>";
    var _ACTION_OBTENER_RAMOS = "<s:url action='obtenerRamos' namespace='/combos'/>";
    var _ACTION_OBTENER_SUBRAMOS = "<s:url action='obtenerSubramos' namespace='/combos'/>";
    var _ACTION_OBTENER_TIPOS_COBERTURA = "<s:url action='obtenerTiposCoberturaProducto' namespace='/combos'/>";
	var _ACTION_OBTENER_IDIOMAS_AYUDA = "<s:url action='obtenerIdiomasAyuda' namespace='/combos'/>";
    var _ACTION_OBTENER_PRODUCTOS_AYUDA = "<s:url action='obtenerProductosAyudaCoberturas' namespace='/combos'/>";


    var helpMap = new Map();
    var itemsPerPage=_NUMROWS;
    
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("11")%>
    
    _URL_AYUDA = "/catweb/ayudaCoberturas.html";
    
    
</script>


<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/ayudaCoberturas/ayudaCoberturas.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/ayudaCoberturas/editarAyudaCoberturas.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/ayudaCoberturas/insertarAyudaCoberturas.js"></script>    
<script type="text/javascript" src="${ctx}/resources/scripts/portal/ayudaCoberturas/copiarAyudaCoberturas.js"></script>
</head>
<body>

   <table class="headlines" cellspacing="10" border=0>
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridElementos" />
           </td>
       </tr>
    </table>
</body>
</html>