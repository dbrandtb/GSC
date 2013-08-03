<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Atributos Variables por Instrumento de Pago</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
  
 	var _ACTION_IR_INSTRUMENTO_PAGO_AGREGAR = "<s:url action='irInstrumentoPagoAgregar' namespace='/atributo'/>";  
    var _ACTION_IR_INSTRUMENTO_PAGO_EDITAR = "<s:url action='irInstrumentoPagoEditar' namespace='/atributo'/>";
   
    var _ACTION_COMBO_ISTRUMENTO_PAGO  =  "<s:url action='obtenerComboProcesosCat' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_NIVEL_CORPO = "<s:url action='comboClientesCorpBO' namespace='/combos-catbo'/>";    
    var _ACTION_COMBO_ASEGURADORA =  "<s:url action='comboAseguradorasPorCliente' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_PRODUCTOS = "<s:url action='comboProductosPorAseguradorYCliente' namespace='/combos-catbo'/>";
    
    var _ACTION_BUSCAR_INSTRUMENTO_PAGO = "<s:url action='buscar' namespace='/atributo'/>";
    var _ACTION_BORRAR_INSTRUMENTO_PAGO = "<s:url action='borrarAtributosVblesInstrumentoPago' namespace='/atributo'/>";
    var _ACTION_EXPORTAR_INSTRUMENTOS_PAGO = "<s:url action='exportarConfigEncuesta' namespace='/atributo'/>";
    
   
    var helpMap= new Map();
   
    var itemsPerPage=_NUMROWS;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("88")%> 
</script>


<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>



<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/atributosVblesInstrumentoPago/atributosVblesInstrumentoPago_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/atributosVblesInstrumentoPago/atributosVblesInstrumentoPago.js"></script>

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
               <div id="gridConfiguracionEncuestas" />
           </td>
       </tr>
       
    </table>
</body>
</html>
