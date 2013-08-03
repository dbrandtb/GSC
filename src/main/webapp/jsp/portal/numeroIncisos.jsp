<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Numeros de Incisos Sub-Incisos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_NUMERO_INCISOS = "<s:url action='buscarNumeroIncisos' namespace='/numeroIncisos'/>";
    var _ACTION_INSERTAR_NUMERO_INCISO = "<s:url action='insertarNumeroIncisos' namespace='/numeroIncisos'/>";
    var _ACTION_GUARDAR_NUMERO_INCISO = "<s:url action='guardarNumeroIncisos' namespace='/numeroIncisos'/>";
    var _ACTION_BORRAR_NUMERO_INCISO = "<s:url action='borrarNumeroIncisos' namespace='/numeroIncisos'/>";
    var _ACTION_EXPORT_NUMERO_INCISOS = "<s:url action='exportarNumeroIncisos' namespace='/numeroIncisos'/>";
    var _ACTION_GET_NUMERO_INCISO = "<s:url action='getNumeroInciso' namespace='/numeroIncisos'/>";
    var _ACTION_OBTENER_TRAMO = "<s:url action='loadTramos' namespace='/numeroIncisos'/>";
    
    var _ACTION_OBTENER_CLIENTE_CORPO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
    var _ACTION_OBTENER_ASEGURADORAS_CLIENTE = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
    var _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE = "<s:url action='obtenerProductosAseguradoraCliente' namespace='/combos'/>";
    var _ACTION_COMBO_FORMA_CALCULO_FOLIO = "<s:url action='comboFormaCalculoFolioNroIncisos' namespace='/combos'/>";
    var _ACTION_COMBO_INDICADOR_NUMERACION = "<s:url action='comboIndicadorNumeracionNroIncisos' namespace='/combos'/>";
    var _ACTION_COMBO_INDICADOR_SP= "<s:url action='comboIndicador_SP_NroIncisos' namespace='/combos'/>";
    
	//EXPRESIONES
	var ACTION_TABLA_EXPRESIONES ="<s:url namespace='expresiones' action='ComboTabla' includeParams='none'/>";
	var ACTION_COLUMNA_EXPRESIONES = "<s:url namespace='expresiones' action='ComboColumna' includeParams='none'/>";
	var ACTION_CLAVE_EXPRESIONES = "<s:url namespace='expresiones' action='ComboClave' includeParams='none'/>";
	
    var helpMap= new Map();
    var itemsPerPage=_NUMROWS;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("21")%>
 
 _URL_AYUDA = "/catweb/numeracionIncisos.html";
    
</script>


<!-- <script type="text/javascript">var helpMap = new Map();</script> -->
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<!-- Para usar lo de Expresiones:  -->
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/checkColumnPlugin.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/tablaApoyo5Claves/tablaApoyo5Claves.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/expresiones/expresiones.js"></script>

<!-- 
<script type="text/javascript" src="${ctx}/resources/scripts/portal/numeroIncisos/agregarNumeroIncisos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/numeroIncisos/editarNumeroIncisos.js"></script>
 -->
<script type="text/javascript" src="${ctx}/resources/scripts/portal/numeroIncisos/numeroIncisos.js"></script>



 
 
</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridIncisos" />
           </td>
       </tr>
    </table>
</body>
</html>