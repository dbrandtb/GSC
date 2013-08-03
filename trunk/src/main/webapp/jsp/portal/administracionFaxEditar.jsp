<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla de Administracion de Fax</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script language="javascript">
    var _CONTEXT = "${ctx}";  
    var _ACTION_OBTENER_POLIZAS = "<s:url action='obtenerPolizas' namespace='/consultaPolizas'/>";
    var _ACTION_OBTENER_ADMNISTRACION_FAX = "<s:url action='buscarFax' namespace='/consultaFax'/>"; //
    var _ACTION_OBTENER_ADMNISTRACION_VALOR_FAX = "<s:url action='obtenerAdministracionValorFaxClick' namespace='/administracionFax'/>";
    var _ACTION_GUARDAR_ADMINISTRACION_FAX = "<s:url action='guardarAdministracionFax' namespace='/administracionFax'/>";
    var _ACTION_GUARDAR_ADMINISTRACION_VALOR_FAX = "<s:url action='guardarAdministracionValorFax' namespace='/administracionFax'/>";
     
    var _ACTION_COMBO_TIPO_FAX = "<s:url action='comboObtenerTipoFax' namespace='/combos-catbo'/>";
    var _ACTION_OBTENER_ATRIBUTOS_VARIABLES = "<s:url action='obtenerAtributosVariablesFaxEditar' namespace='/administracionFax'/>";
    
    var _ACTION_AGREGAR_ARCHIVO = "<s:url action='agregarArchivosFaxEditar' namespace='/administracionFax'/>";
    
    var _ACTION_COMBO_GENERICO = "<s:url action='obtenerIndicadorNumeracion' namespace='/combos-catbo'/>";
    
    var _ACTION_EXPORTAR_ADMINISTRACION_FAX = "<s:url action='exportarAdministracionFax' namespace='/administracionFax'/>";
    
    var _NMFAX = "<s:property value='nmfax'/>";
    var _NMCASO = "<s:property value='nmcaso'/>";
    var _CDTIPOAR = "<s:property value='cdtipoar'/>";
    var _DSARCHIVO = "<s:property value='dsarchivo'/>";
    var _NMPOLIEX = "<s:property value='nmpoliex'/>";    
    var _VAR = "<s:property value='cdVariable'/>";
    
     
     
     var _IR_A_CONSULTA_FAX= "<s:url action='irConsultaFax' namespace='/consultaFax'/>";
  	 
    var itemsPerPage=10;
   <%=session.getAttribute("helpMap")%>
    var helpMap= new Map();
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("815")%>
    
     _URL_AYUDA = "/backoffice/editarAdministracionFax.html"; 
     
    
    var CDPERSON = "<s:property value='cdperson'/>";
    //var CDUSUARIO = "SLIZARDI";
    
    
    var readerComboAtributosVariables = new Ext.data.JsonReader({
			root: 'comboGenerico',
			totalProperty: 'totalCount',
			successProperty: 'success'
			}, [
				{name: 'codigo', type: 'string'},
				{name: 'descripcion', type: 'string'}
			]
	);

	function crearStoreComboAtributosVariables(_idTablaLogica, _comboId, _value) {
		var storeComboAtributosVariables;
		storeComboAtributosVariables = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_GENERICO}),
			reader: readerComboAtributosVariables
		});
		
		storeComboAtributosVariables.load({
			params: {idTablaLogica: _idTablaLogica},
			callback: function(r, opt, success){
						if (_value != null) {
							Ext.getCmp(_comboId).setValue(_value);							
						}
					}
		});
		return storeComboAtributosVariables;
	}
	

    <%if(session.getAttribute("modelControl") != null){%>
			ATTS_DINAMICOS = <%=session.getAttribute("modelControl")%>
		<%}else{%>
			ATTS_DINAMICOS = [{html: '<br/><span class="x-form-item" style="font-weight:bold">No se pudo cargar los atributos variables</span>'}]
			<%}
     %>   
    
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<!-- Algunos css para el UploadPanel:   -->
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/icons.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetree.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetype.css" />
  
  <script src="${ctx}/resources/scripts/util/FileUploader.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.form.BrowseButton.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/util/UploadPanel.js" type="text/javascript"></script>


<script type="text/javascript" src="${ctx}/resources/scripts/consultaPolizas/consultaPolizas.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/AdministracionFax/administracionFaxEditar_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/AdministracionFax/administracionFaxEditar.js"></script>




</head>
<body>
   
   <table>
        <tr>
            <td>
                <div id="encabezadoFijo" />
            </td>
        </tr>
       <tr>
            <td>
                <iframe id="atributosVariablesFaxEditar" align="top" frameBorder="SI" name="atributosVariablesFaxEditar" width="700" scrolling="SI" height="1">
        		</iframe>

            </td>
        </tr>
            
       <tr>
           <td>
               <div id="formBotones" />
           </td>
       </tr>
    </table>
   
   
      
</body>
</html>