<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Numeracion de Casos CAT-BO</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_NUMERACION_CASOS = "<s:url action='buscarNumeracionCasos' namespace='/administracionCasos'/>";
    var _ACTION_BORRAR_NUMERACION_CASOS = "<s:url action='borrarNumeracionCasos' namespace='/administracionCasos'/>";
    var _ACTION_OBTENER_NUMERACION_CASOS = "<s:url action='getNumeracionCasos' namespace='/administracionCasos'/>";
    var _ACTION_AGREGAR_GUARDAR_NUMERACION_CASOS = "<s:url action='guardarNumeracionCasos' namespace='/administracionCasos'/>";
    var _ACTION_EXPORTAR_NUMERACION_CASOS = "<s:url action='exportarNumeracionCasos' namespace='/administracionCasos'/>";
     
// COMBOS  - url de los combos -
    var _ACTION_OBTENER_INDICADOR_NUMERACION = "<s:url action='obtenerIndicadorNumeracion' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_MODULO = "<s:url action='ObtenerDatosModulos' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_DATOS_MODULO = "<s:url action='traerDatosModulos' namespace='/combos-catbo'/>";
    
    var helpMap=new Map();
     
    var itemsPerPage=_NUMROWS;
    var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("314")%>
    
    _URL_AYUDA = "/backoffice/numSolicitudesCATBO.html";
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/numeracionCasos/editarNumeracionCasos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/numeracionCasos/agregarNumeracionCasos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/numeracionCasos/numeracionCasos.js"></script>

</head>

<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formDocumentos" />
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