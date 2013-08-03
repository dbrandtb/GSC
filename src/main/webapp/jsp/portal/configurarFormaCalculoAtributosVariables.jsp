<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Configurar Forma Calculo Atributos Variables</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS = "<s:url action='buscarConfigurarFormaCalculoAtributo' namespace='/configurarFormaCalculoAtributosVariables'/>";
    var _ACTION_OBTENER_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS = "<s:url action='getConfigurarFormaCalculoAtributo' namespace='/configurarFormaCalculoAtributosVariables'/>";    
    var _ACTION_BORRAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS = "<s:url action='borrarConfigurarFormaCalculoAtributo' namespace='/configurarFormaCalculoAtributosVariables'/>";
    var _ACTION_GUARDAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS = "<s:url action='guardarConfigurarFormaCalculoAtributo' namespace='/configurarFormaCalculoAtributosVariables'/>";
    var _ACTION_COPIAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS = "<s:url action='copiarConfigurarFormaCalculoAtributo' namespace='/configurarFormaCalculoAtributosVariables'/>";
    var _ACTION_EXPORTAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS = "<s:url action='exportarConfigurarFormaCalculoAtributo' namespace='/configurarFormaCalculoAtributosVariables'/>";    
    var _ACTION_OBTENER_COPIA_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS = "<s:url action='getCopiaConfigurarFormaCalculoAtributo' namespace='/configurarFormaCalculoAtributosVariables'/>";    
    
    var itemsPerPage=10;
   
    
    //VARIABLES PARA COMBOS
    var _ACTION_OBTENER_CLIENTE_CORPO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
    var _ACTION_OBTENER_ASEGURADORAS_CLIENTE = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
    var _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE = "<s:url action='obtenerProductosAseguradoraCliente' namespace='/combos'/>";
    var _ACTION_OBTENER_SITUACION_PRODUCTOS = "<s:url action='obtenerComboTipoSituacionProductos' namespace='/combos'/>";    
    
    //VARIABLES PARA LAS TABLAS DE APOYO DE LOS CAMPOS ATRIBUTO Y CALCULO
    var _ACTION_OBTENER_COMBO_CALCULO = "<s:url action='obtenerFormaCalculo' namespace='/combos'/>"; 
    var _ACTION_OBTENER_COMBO_ATRIBUTO = "<s:url action='obtenerFormaAtributo' namespace='/combos'/>";
    
      <%=session.getAttribute("helpMap")%>
 
 	<%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("5")%>
     
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap = new Map();</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configurarFormaCalculoAtributosVariables/configurarFormaCalculoAtributosVariables.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configurarFormaCalculoAtributosVariables/guardarFormaCalculoAtributosVariables.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configurarFormaCalculoAtributosVariables/copiarFormaCalculoAtributosVariables.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/configurarFormaCalculoAtributosVariables/editarFormaCalculoAtributosVariables.js"></script>

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