<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Consulta de Segmentacion</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ManagedIFrame.js"></script>
<%@page import="mx.com.aon.portal.model.UserVO"%>
<script language="javascript">
    var _CONTEXT = "${ctx}";
  
  
  //  var _ACTION_COMBO_CLIENTES_CORPO = "<s:url action='comboClientesAseguradoraEncuestas' namespace='/combos-catbo'/>";
 
    var _ACTION_COMBO_CLIENTES_CORPORA ="<s:url action='comboClientesCorpBO' namespace='/combos'/>" ;
    var _ACTION_COMBO_ASEGURADORAS = "<s:url action='obtenerAseguradoras' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_PRODUCTOS_ASEG_ENCUESTA=  "<s:url action='comboProductosAseguradoraEncuestas' namespace='/combos-catbo'/>";
    
   
   // estaba 
    var _ACTION_BUSCAR_ENCUESTAS = "<s:url action='obtenerValoresEncuesta' namespace='/configuracionEncuestas'/>";
    var _ACTION_BUSCAR_ENCUESTAS_EXPORT = "<s:url action='exportarValoresEncuesta' namespace='/configuracionEncuestas'/>";
     var _ACTION_BORRAR_ENCUESTAS = "<s:url action='borrarValoresEncuesta' namespace='/configuracionEncuestas'/>";
    
    
    var _ACTION_BUSCAR_PREGUNTA_ENCUESTA = "<s:url action='getValorEncuesta' namespace='/configuracionEncuestas'/>";
    var _ACTION_BUSCAR_PREGUNTA_ENCUESTA_ENC = "<s:url action='getValorEncuestaEnc' namespace='/configuracionEncuestas'/>";
    var  _ACTION_EXPORTAR_INGRESAR_ENCUESTAS_EDITAR = "<s:url action='exportarValorEncuesta' namespace='/configuracionEncuestas'/>";
   
   // var _ACTION_BUSCAR_PREGUNTA_ENCUESTA_SIG = "<s:url action='obtenerPreguntaEncuestasSig' namespace='/ingresarEncuesta'/>";
   // var _ACTION_GUARDAR_RESPUESTAS = "<s:url action='respuestaEncuestaGuardar' namespace='/ingresarEncuesta'/>";
    
    //combos
    var _ACTION_COMBO_MODULO = "<s:url action='obtenerComboModuloEnc' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_CAMPANHA  = "<s:url action='obtenerComboCampanhaEnc' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_TEMA = "<s:url action='obtenerComboTemaEnc' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ENCUESTA = "<s:url action='obtenerComboEncuestaEnc' namespace='/combos-catbo'/>";
    
    
    
   //buscarConfiguracionEncuestas
    var helpMap= new Map();
      // var itemsPerPage=10;
      
        var itemsPerPage= _NUMROWS;
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("")%>
   

   
 //    _URL_AYUDA = "/backoffice/consultaEncuestas.html";
   
    <% 	UserVO userVO = (UserVO)session.getAttribute("USUARIO");
    	
			%>
	
	var CDELEMENTO = '<%=userVO.getEmpresa().getElementoId()%>' 
    
    
     
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/MetaForm.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>



<script type="text/javascript" src="${ctx}/resources/scripts/portal/segmentacion/consultar_segmentacion_store.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/segmentacion/consultarSegmentacion.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/segmentacion/configuracionSegmentacionAgregar.js"></script>





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
               <div id="gridEncuestas" />
           </td>
       </tr>
       
    </table>
</body>
</html>
