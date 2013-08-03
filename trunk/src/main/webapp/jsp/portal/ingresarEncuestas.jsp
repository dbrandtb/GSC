<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Ingreso de Encuestas</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ManagedIFrame.js"></script>
<%@page import="mx.com.aon.portal.model.UserVO"%>
<script language="javascript">
    var _CONTEXT = "${ctx}";
  
    var _ACTION_BUSCAR_PREGUNTA_ENCUESTA = "<s:url action='obtenerPreguntaEncuestas' namespace='/ingresarEncuesta'/>";
    var _ACTION_BUSCAR_PREGUNTA_ENCUESTA_SIG = "<s:url action='obtenerPreguntaEncuestasSig' namespace='/ingresarEncuesta'/>";
    var _ACTION_GUARDAR_RESPUESTAS = "<s:url action='respuestaEncuestaGuardar' namespace='/ingresarEncuesta'/>";
    var _IR_CONSULTA_CLIENTES = "<s:url action='irConsultaClientes' namespace='/administracionCasos'/>";
    
    //combos
    var _ACTION_COMBO_MODULO = "<s:url action='obtenerComboModuloEnc' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_CAMPANHA  = "<s:url action='obtenerComboCampanhaEnc' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_TEMA = "<s:url action='obtenerComboTemaEnc' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ENCUESTA = "<s:url action='obtenerComboEncuestaEnc' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_GENERICO_ENCUESTA = "<s:url action='obtenerDatosCatalogo' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_ASEGURADORA = "<s:url action='obtenerAseguradoras' namespace='/combos-catbo'/>";
    var _ACTION_COMBO_PRODUCTOS = "<s:url action='comboProductosAseguradoraEncuestas' namespace='/combos-catbo'/>";
    
    //var _ACTION_COMBO_GENERICO = "<s:url action='obtenerComboGenerico' namespace='/combos'/>";

    
    var itemsPerPage=10;
      var helpMap= new Map();
    <%=session.getAttribute("helpMap")%>
    
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("827")%>

  	
  	var CDUNIECOCLI = "<s:property value='cdunieco'/>";
  	var CDRAMOCLI = "<s:property value='cdramo'/>";
  	var CDNMPOLIZACLI = "<s:property value='nmpoliza'/>";
  	var CDESTADOCLI = "<s:property value='estado'/>";
  	var CDPERSONCLI = "<s:property value='cdperson'/>";
  	var DSUNIECO = "<s:property value='dsunieco'/>";
  	var DSRAMO = "<s:property value='dsramo'/>";
  	
  	var NOMBRE = "<s:property value='nombrePersona'/>";
  	var NMPOLIEX = "<s:property value='nmpoliex'/>";
  	
  	
  			var readerDatosGenericos = new Ext.data.JsonReader({
				root: 'comboDatosCatalogo',
				totalProperty: 'totalCount',
				successProperty: 'success'
				}, [
					{name: 'codigo', type: 'string', mapping: 'id'},
					{name: 'descripcion', type: 'string', mapping: 'texto'}
				]
		);

		function crearStorePreguntaEncuesta (_idTablaLogica, _comboId, _value) {
			var dsGenerico;
			dsGenerico = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
							url: _ACTION_COMBO_GENERICO_ENCUESTA
				}),
				reader: readerDatosGenericos
			});
	
			dsGenerico.load({
				params: {
					cdTabla: _idTablaLogica
				},
				callback: function(r, opt, success) {
					//Ext.getCmp(_comboId).setValue(_value);
				}
			});
			return dsGenerico;
		}
		
    <% 	UserVO userVO = (UserVO)session.getAttribute("USUARIO");
    	
			%>
	
	var CDELEMENTO = '<%=userVO.getEmpresa().getElementoId()%>' 
		
	_URL_AYUDA = "/backoffice/ingresarEncuesta.html";	
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/MetaForm.js"></script>
<!-- 
<script type="text/javascript" src="${ctx}/resources/scripts/ingresarEncuestas/encuesta_stores_agrega.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/ingresarEncuestas/encuestaAgregar.js"></script>
-->
<script type="text/javascript" src="${ctx}/resources/scripts/ingresarEncuestas/ingresarEncuestas_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/ingresarEncuestas/ingresarEncuestas.js"></script>

</head>

<body>
   
   <table class="headlines">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="encabezado" />
            </td>
        </tr>
        <!-- <tr valign="top">
            <td class="headlines" colspan="2">
                <iframe id="atributosVariables" align="middle" frameBorder="NO" name="atributosVariables" width="800" scrolling="NO" height="1">
        		</iframe>

            </td>
        </tr>-->
        
  		<tr valign="top">
           <td class="headlines" colspan="2">
               <div id="formDatosAdicionales" />
           </td>
       </tr> 
  		
  		
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="formBotones" />
           </td>
       </tr> 
    </table>
   
   
      
</body>
</html>
