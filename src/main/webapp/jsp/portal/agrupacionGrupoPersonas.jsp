<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Configurar Elementos de Estructura</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_AGRUPACION_GRUPO_PERSONAS = "<s:url action='buscarGruposPersonas' namespace='/agrupacionGrupoPersonas'/>";
    var _ACTION_GET_AGRUPACION_GRUPO_PERSONAS = "<s:url action='obtenerAgrupacionGrupoPersonas' namespace='/agrupacionGrupoPersonas'/>";
    var _ACTION_INSERTAR_AGRUPACION_GRUPO_PERSONAS = "<s:url action='agregarAgrupacionGrupoPersona' namespace='/agrupacionGrupoPersonas'/>";
    var _ACTION_GUARDAR_AGRUPACION_GRUPO_PERSONAS = "<s:url action='guardarAgrupacionGrupoPersona' namespace='/agrupacionGrupoPersonas'/>";
	var _ACTION_IR_AGRUPACION_POLIZAS = "<s:url action='irAgrupacionPolizas' namespace='/agrupacionGrupoPersonas'/>"; 
	var _AGRUPACION_BORRAR_GRUPO = "<s:url action='borrarGrupo' namespace='/agrupacionGrupoPersonas'/>"; 
 	var _ACTION_IR_ASIGNAR_POLIZAS = "<s:url action='irAsignarPolizas' namespace='/agrupacionGrupoPersonas'/>";
	var _ACTION_ACTUALIZAR_DATOS_GRILLA = "<s:url action='actualizarAgrupacionGrupoPersona' namespace='/agrupacionGrupoPersonas'/>";
 
 
	//Combos url de los combos
    var _ACTION_OBTENER_GRUPO_PERSONAS_CLIENTES = "<s:url action='obtenerGrupoPersonasClientes' namespace='/combos'/>";

	//Seteo de valores de la estructura obtenidos
    var CODIGO_GRUPO = "<s:property value='cveAgrupa'/>";
    
    var CODIGO_POLMTRA = "<s:property value='cdPolMtra'/>";
	var CODIGO_AGRUPACION = "<s:property value='codigoAgrupacion'/>";

    var helpMap = new Map();
    var itemsPerPage=_NUMROWS;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("18")%>
    
    _URL_AYUDA = "/catweb/configAgrupGrupoPers.html";    
    
</script>


<script type="text/javascript">
	
	helpMap.put('agrGPerCmGrPer',{tooltip:'Grupo de Personas...'});
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script src="${ctx}/resources/scripts/ValidacionGrilla/Ext.ux.plugins.GridValidator.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/agrupacionGrupoPersonas/agrupacionGrupoPersonas.js"></script>
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
               <div id="gridElementos" />
           </td>
       </tr>
    </table>
</body>
</html>