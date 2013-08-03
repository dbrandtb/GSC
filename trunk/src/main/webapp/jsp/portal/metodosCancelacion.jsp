<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Metodos de Cancelacion</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>


<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_METODOS_CANCELACION = "<s:url action='buscarMetodosCancelacion' namespace='/metodosCancelacion'/>";
    var _ACTION_GET_METODO_CANCELACION = "<s:url action='getMetodoCancelacion' namespace='/metodosCancelacion'/>";
    var _ACTION_INSERTAR_GUARDAR_METODOS_CANCELACION = "<s:url action='guardarMetodosCancelacion' namespace='/metodosCancelacion'/>";
    var _ACTION_BORRAR_METODOS_CANCELACION = "<s:url action='borrarMetodosCancelacion' namespace='/metodosCancelacion'/>";
    var _ACTION_EXPORT_METODOS_CANCELACION = "<s:url action='exportarMetodosCancelacion' namespace='/metodosCancelacion'/>";
    var _ACTION_IR_FORMULA_METODO_CANCELACION = "<s:url action='irFormulaMetodosCancelacion' namespace='/formulaMetodoCancelacion'/>";


     var helpMap=new Map();
     var itemsPerPage=_NUMROWS;
     <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("25")%>
        
     _URL_AYUDA = "/catweb/metodosCancelacion.html";
     
</script>

<!-- <script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap = new Map();</script> -->
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/metodosCancelacion/metodoCancelacion.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/metodosCancelacion/agregarMetodoCancelacion.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/metodosCancelacion/editarMetodoCancelacion.js"></script> 
 
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
               <div id="gridMetodosCancelacion" />
           </td>
       </tr>
    </table>
</body>
</html>