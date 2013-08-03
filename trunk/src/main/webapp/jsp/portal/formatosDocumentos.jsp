<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Formatos de Documentos</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    var _ACTION_BUSCAR_FORMATOS_DOCUMENTOS = "<s:url action='buscarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_BORRAR_FORMATOS_DOCUMENTOS = "<s:url action='borrarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_GET_FORMATO_DOCUMENTOS = "<s:url action='getFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_GUARDAR_FORMATO_DOCUMENTOS = "<s:url action='guardarFormatosDocumentos' namespace='/formatosDocumentos'/>";
    var _ACTION_EXPORTAR_FORMATO_DOCUMENTOS = "<s:url action='exportarFormatosDocumentos' namespace='/formatosDocumentos'/>";
     
    var itemsPerPage=10;
    var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("811")%>
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/formatosDocumentos/formatosDocumentos_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/formatosDocumentos/formatosDocumentosComun.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/formatosDocumentos/formatosDocumentosPantalla.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/formatosDocumentos/formatosDocumentosEditar.js"></script>


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