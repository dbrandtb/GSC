<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"               prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/struts-tags"                                   prefix="s" %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style></style>

<link href="../../../resources/extjs4/resources/my-custom-theme/my-custom-theme-all.css" rel="stylesheet" type="text/css" />
		
<!-- Libreria EXTJS4 -->
<script type="text/javascript" src="../../../resources/extjs4/ext-all.js"></script>
<!-- Mensajes  -->
<script type="text/javascript" src="../../../resources/extjs4/locale/ext-lang-es.js"></script>
        
<script>

////// overrides //////


////// variables //////
var _CONTEXT = '${ctx}';
var _p21_urlListarDirectorio            = '<s:url namespace="/seguridad"         action="listarDirectorio"            />';
console.log('entrando a la pagina');

////// onReady //////

Ext.onReady(function()
{
    ////// modelos //////
    var modeloDocumentos = Ext.define('modeloDocumentos',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            {type:'string', name:'nombreDoc'},
            {type:'string', name:'tamañoDoc'},
            {type:'string', name:'modificado'},
            {type:'string', name:'permisos'},
            {type:'string', name:'propietario'}
        ]
    });
    
   
    ////// stores //////
    var storeDocumentos = Ext.create('Ext.data.Store', {
		model     : 'modeloDocumentos',
		proxy     : {
			type        : 'ajax'
			,url        : _p21_urlListarDirectorio
		    ,extraParams: {path:'/RES/logs/gseguros'}
		    ,reader     :
		    {
		    	type  : 'json'
		      	,root : 'slist1'
		    }
		    	,autoLoad : true
		}	    
	});
    
  
	var storeDocumentos2 = Ext.create('Ext.data.Store', {
        storeId:'storeDocumentos2',
        fields:['nombreDoc', 
        		'tamañoDoc', 
        		'modificado', 
        		'permisos', 
        		'propietario'],
        data:{'items':[ //deben venir del formulario anterior
            { 'nombreDoc':'Dafna',  'tamañoDoc':'Brandt',  'modificado':'xxx', 'permisos':'Borges', 'propietario':'23'},
            { 'nombreDoc':'XXXX',  'tamañoDoc':'YYYYY',  'modificado':'xxx','permisos':'BoZZZZZrges', 'propietario':'11'}
        ]},
        proxy: {
            type: 'memory',
            reader: {
                type: 'json',
                root: 'items'
            }
        }
        ,autoload: true
    });

    ////// stores //////
    
    ////// componentes //////
	var gridDocumentos = Ext.create('Ext.grid.Panel', {
	    title: 'Documentos del Servidor',
	    //store: Ext.data.StoreManager.lookup('storeDocumentos'),
	    itemId: 'gridDocumentos',
	    store: storeDocumentos2,
	    columns: [
	        { text: 'NombreDoc',  dataIndex: 'nombreDoc', editor: 'textfield' },
	        { text: 'TamañoDoc', dataIndex: 'tamañoDoc', flex: 1, editor: {xtype: 'textfield', allowBlank: false} },
	        { text: 'Modificado', dataIndex: 'modificado', flex: 1, editor: {xtype: 'textfield', allowBlank: false} },
	        { text: 'Permisos', dataIndex: 'permisos', flex: 1, editor: {xtype: 'textfield', allowBlank: false} },
	        { text: 'Propietario', dataIndex: 'propietario', flex: 1, editor: {xtype: 'textfield', allowBlank: false} },
	        { xtype    :'actioncolumn',
	            text     : 'Descargar',
	            flex     : 2,
	            items: [{
	                tooltip : 'Descargar Documento',
	                icon    : '${ctx}/resources/fam3icons/icons/color_swatch.png',
	                handler : function(grid, rowIndex, colIndex, item, e, record, row) {
	                						                	
	                	/*var par = Ext.ComponentQuery.query('form')[0].getForm().getValues();
						par['params.consulta'] = 'RECUPERAR_TRAMITES_LINEANEGOCIO_POR_RAMO';
						par['params.lineanegocio'] = record.get('CD_LINEA_NEGOCIO');
						par['params.cdetapa'] = Ext.ComponentQuery.query('[name=params.cdetapa]')[0].getValue();
						
						storeDetalleLineaNegocioPorRamo.getProxy().extraParams = par;
						storeDetalleLineaNegocioPorRamo.load();
						*/
						
						//descargar documento
	                }
	            }]
	        }
	    ],
	    selType: 'cellmodel',
	    width: '100%',
	    listeners: {
			selectionchange : function(sm, selectedRecord) {
				console.log("en el selection change");
				storeDocumentos.load()
			}
	    }
	});
	
    ////// contenido //////
    Ext.create('Ext.Panel', {
	    bodyPadding: 5,  
	    width: '100%',
	    title: '',
	    itemId: 'panelPpal',
	    items: [
	    	{
	    		xtype: gridDocumentos
	    	}
	    ],
	    //renderTo: Ext.getBody()
	    renderTo: 'divDocumentos'
	});
    ////// custom //////
	
	////// funciones //////

});
</script>

</head>
<body>
	<div id="divDocumentos" style="height:500px;"></div>
</body>
</html>