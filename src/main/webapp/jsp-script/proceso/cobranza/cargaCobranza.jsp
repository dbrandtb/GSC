<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Carga y Procesamiento de Archivos de Cobranza</title>
<script>

///////////////////////
////// Overrides //////
/*///////////////////*/
// Sobreescribimos CheckboxModel con Selección Simple:
Ext.selection.CheckboxModel.override({
    mode: 'SINGLE',
    allowDeselect: true
});

///////////////////////
////// variables //////
/*///////////////////*/
var _CONTEXT = '${ctx}';
extjs_custom_override_mayusculas = false;

var recargaGridCobAplicada;
var recargaGridRemesaAplicada;

var _URL_CARGA_CATALOGO = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
var _UrlSubirArchivoCobranza    = '<s:url namespace="/cobranza" action="subirArchivoCobranza" />';

var _UrlCobAplicada    = '<s:url namespace="/catalogos"    action="obtieneMenusPorRol" />';
var _UrlRemesaAplicada = '<s:url namespace="/catalogos"    action="obtieneOpMenu" />';

var _MSG_SIN_DATOS          = 'No hay datos';
var _MSG_BUSQUEDA_SIN_DATOS = 'No hay datos para la b\u00FAsqueda actual.';

/*///////////////////*/
////// variables //////
///////////////////////

Ext.onReady(function()
{
	
	/////////////////////
	////// modelos //////
	/*/////////////////*/
	
	Ext.define('gridCobAplicadaModel',
			{
				extend : 'Ext.data.Model'
				,fields :
				['CDTITULO','DSTITULO','DSURL','SWTIPDES','DSTIPDES']
	});
	
	Ext.define('gridRemesaAplicadaModel',
	{
		extend : 'Ext.data.Model'
		,fields :
		['CDMENU','DSMENU','CDELEMENTO','CDPERSON','CDROL', 'CDUSUARIO', 'CDESTADO', 'CDTIPOMENU']
	});
	
	/*/////////////////*/
	////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
	/*////////////////*/
	
	var cobAplicadaStore = Ext.create('Ext.data.Store',
    {
		pageSize : 20,
        autoLoad : true
        ,model   : 'gridCobAplicadaModel'
        ,proxy   :
        {
            type         : 'memory'
            ,enablePaging : true
            ,reader      : 'json'
            ,data        : []
        }
    });

	var remesaAplicadaStore = Ext.create('Ext.data.Store',
		{
		pageSize : 20,
		autoLoad : true
		,model   : 'gridRemesaAplicadaModel'
		,proxy   :
		{
			type         : 'memory'
			,enablePaging : true
			,reader      : 'json'
			,data        : []
		}
	});
	
	/*////////////////*/
	////// stores //////
	////////////////////
	
	///////////////////////
	////// contenido //////
	/*///////////////////*/
	
	var gridCobAplicada = Ext.create('Ext.grid.Panel',
		    {
	    	title : 'Cobranza Aplicada'
	    	,height : 250
	    	,selType: 'checkboxmodel'
	    	,store : cobAplicadaStore
	    	,columns :
	    	[ { header     : 'CDMENU' , dataIndex : 'CDMENU', hidden: true},
	    	  { header     : 'CDELEMENTO' , dataIndex : 'CDELEMENTO', hidden: true},
	          { header     : 'Nombre Men&uacute;', dataIndex : 'DSMENU', flex: 1},
	          { header     : 'CDPERSON' , dataIndex : 'CDPERSON', hidden: true},
	          { header     : 'CDROL' , dataIndex : 'CDROL', hidden: true},
	          { header     : 'CDUSUARIO' , dataIndex : 'CDUSUARIO', hidden: true},
	          { header     : 'CDESTADO' ,dataIndex : 'CDESTADO', hidden: true},
	          { header     : 'Tipo Men&uacute;' ,dataIndex : 'CDTIPOMENU', flex: 1, renderer : function(value){
	        	  if("1"== value) return "MENU GENERAL";
	        	  	else if("2"== value) return "MENU SESION";
	        	  		else return res;
	          }}
			]
	    	,bbar     :
	        {
	            displayInfo : true,
	            store       : cobAplicadaStore,
	            xtype       : 'pagingtoolbar'
	            
	        }
	    });
	
	var gridRemesaAplicada = Ext.create('Ext.grid.Panel',
		    {
	    	title : 'Remesa Aplicada'
	    	,height : 250
	    	,selType: 'checkboxmodel'
	    	,store : remesaAplicadaStore
	    	,columns :
	    	[ { header     : 'CDMENU' , dataIndex : 'CDMENU', hidden: true},
	    	  { header     : 'CDELEMENTO' , dataIndex : 'CDELEMENTO', hidden: true},
	          { header     : 'Nombre Men&uacute;', dataIndex : 'DSMENU', flex: 1},
	          { header     : 'CDPERSON' , dataIndex : 'CDPERSON', hidden: true},
	          { header     : 'CDROL' , dataIndex : 'CDROL', hidden: true},
	          { header     : 'CDUSUARIO' , dataIndex : 'CDUSUARIO', hidden: true},
	          { header     : 'CDESTADO' ,dataIndex : 'CDESTADO', hidden: true},
	          { header     : 'Tipo Men&uacute;' ,dataIndex : 'CDTIPOMENU', flex: 1, renderer : function(value){
	        	  if("1"== value) return "MENU GENERAL";
	        	  	else if("2"== value) return "MENU SESION";
	        	  		else return res;
	          }}
			]
	    	,bbar     :
	        {
	            displayInfo : true,
	            store       : remesaAplicadaStore,
	            xtype       : 'pagingtoolbar'
	            
	        }
	});
	
	var panelCobranza = Ext.create('Ext.form.Panel',{
		title: 'Cobranza Masiva',
		defaults : {
			style : 'margin : 5px;'
		}
	    ,renderTo : 'mainDivCobranza'
	    ,items :
	    [{
	    	layout: 'column',
	    	border: false,
	    	html:'<br/><strong>Oficina General de Seguros</strong><br/><br/>Elija la el Archivo de Cobranza a Cargar:'
	     },{
	        xtype: 'filefield',
	        name: 'file',
	        width: 300,
	        msgTarget: 'side',
	        allowBlank: false,
	        buttonText: 'Examinar...'
	    },{
	    	xtype: 'button',
	    	text : 'Ejecutar Cobranza Masiva',
	    	tooltip: 'Ejecuta la Cobranza Masiva para el Archivo Seleccionado',
	    	handler: function(){
	    		var form = panelCobranza.getForm();
	            if(form.isValid()){
	                form.submit({
	                    url: _UrlSubirArchivoCobranza,
	                    waitMsg: 'Subiendo Archivo...',
	                    success: function(fp, o) {
	                        Ext.Msg.alert('Exito', 'MEnsaje de Carga');
	                    }
	                });
	            }
	    	}
	    }],
	    buttonAlign: 'center',
	    buttons: [{
	        text: 'Ver Cobranza Aplicada',
	        handler: function() {
	            
	        }
	    },{
	        text: 'Ver Remesa Aplicada',
	        handler: function() {
	            var form = this.up('form').getForm();
	            
	        }
	    }]
		    
	});
	/*///////////////////*/
	////// contenido //////
	///////////////////////
	
});
</script>

</head>
<body>
<div id="mainDivCobranza" style="height:350px;"></div>
</body>
</html>