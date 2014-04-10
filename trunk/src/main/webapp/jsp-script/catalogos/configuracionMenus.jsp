<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Configuracion de Menus por Rol</title>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var _CONTEXT = '${ctx}';
extjs_custom_override_mayusculas = false;

var recargaGridOpcionesLiga;
var recargaGridMenus;
var recargaGridOpMenu;
var recargaGridOpSubMenu;

var _UrlOpcionesLiga      = '<s:url namespace="/catalogos"    action="obtieneOpcionesLiga" />';
var _UrlGuardaOpcionLiga  = '<s:url namespace="/catalogos"    action="guardaOpcionLiga" />';
var _UrlEliminaOpcionLiga = '<s:url namespace="/catalogos"    action="eliminaOpcionLiga" />';

var _URL_CARGA_CATALOGO = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
var _CAT_ROLES_SISTEMA = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@ROLES_SISTEMA"/>';

var _UrlMenusPorRol = '<s:url namespace="/catalogos"    action="obtieneMenusPorRol" />';
var _UrlGuardaMenu  = '<s:url namespace="/catalogos"    action="guardaMenu" />';
var _UrlEliminaMenu = '<s:url namespace="/catalogos"    action="eliminaMenu" />';

var _UrlOpMenu        = '<s:url namespace="/catalogos"    action="obtieneOpMenu" />';
var _UrlGuardaOpMenu  = '<s:url namespace="/catalogos"    action="guardaOpMenu" />';
var _UrlEliminaOpMenu = '<s:url namespace="/catalogos"    action="eliminaOpMenu" />';

var _UrlOpSubMenu        = '<s:url namespace="/catalogos"    action="obtieneOpSubMenu" />';
var _UrlGuardaOpSubMenu  = '<s:url namespace="/catalogos"    action="guardaOpSubMenu" />';
var _UrlEliminaOpSubMenu = '<s:url namespace="/catalogos"    action="guardaOpSubMenu" />';

var _MSG_SIN_DATOS          = 'No hay datos';
var _MSG_BUSQUEDA_SIN_DATOS = 'No hay datos para la b\u00FAsqueda actual.';

/*///////////////////*/
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
/*///////////////////*/
/*///////////////////*/
////// funciones //////
///////////////////////

Ext.onReady(function()
{
	
	/////////////////////
	////// modelos //////
	/*/////////////////*/
	Ext.define('gridOpLigaModel',
	{
		extend : 'Ext.data.Model'
		,fields :
		['CDTITULO','DSTITULO','DSURL','SWTIPDES','DSTIPDES']
	});
	Ext.define('gridMenusModel',
	{
		extend : 'Ext.data.Model'
		,fields :
		['CDMENU','DSMENU','CDELEMENTO','CDPERSON','CDROL', 'CDUSUARIO', 'CDESTADO', 'CDTIPOMENU']
	});
	
	Ext.define('gridOpMenuModel',
			{
				extend : 'Ext.data.Model'
				,fields :
				['CDNIVEL','CDNIVEL_PADRE','DSMENU_EST','CDTITULO','DSTITULO']
	});
	
	Ext.define('gridOpSubMenuModel',
	{
		extend : 'Ext.data.Model'
		,fields :
		['CDNIVEL','CDNIVEL_PADRE','DSMENU_EST','CDTITULO','DSTITULO']
	});
	/*/////////////////*/
	////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
	/*////////////////*/
	var opcionesLigaStore = Ext.create('Ext.data.Store',
    {
		pageSize : 20,
        autoLoad : true
        ,model   : 'gridOpLigaModel'
        ,proxy   :
        {
            type         : 'memory'
            ,enablePaging : true
            ,reader      : 'json'
            ,data        : []
        }
    });
	var menusStore = Ext.create('Ext.data.Store',
    {
		pageSize : 20,
        autoLoad : true
        ,model   : 'gridMenusModel'
        ,proxy   :
        {
            type         : 'memory'
            ,enablePaging : true
            ,reader      : 'json'
            ,data        : []
        }
    });
	
	var opcionesSubMenuStore = Ext.create('Ext.data.Store',
    {
		pageSize : 20,
        autoLoad : true
        ,model   : 'gridOpSubMenuModel'
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
	var gridOpcionesLiga = Ext.create('Ext.grid.Panel',
	    {
    	title : 'Opciones Liga/URL'
    	,height : 300
    	,selType: 'checkboxmodel'
    	,store : opcionesLigaStore
    	,columns :
    	[ { header     : 'CD Opci&oacute;n' , dataIndex : 'CDTITULO', hidden: true},
    	  { header     : 'Nombre Opci&oacute;n' , dataIndex : 'DSTITULO', flex: 1},
          { header     : 'Liga/URL' , dataIndex : 'DSURL' , flex: 3}
		]
    	,bbar     :
        {
            displayInfo : true,
            store       : opcionesLigaStore,
            xtype       : 'pagingtoolbar'
        },
        tbar: [{
            icon    : '${ctx}/resources/fam3icons/icons/add.png',
            text    : 'Agregar opci&oacute;n',
            handler : function(){agregarEditarOpcionLiga();}
        },{
            icon    : '${ctx}/resources/fam3icons/icons/pencil.png',
            text    : 'Editar opci&oacute;n',
            handler : function()
            {
            	var model =  gridOpcionesLiga.getSelectionModel();
            	if(model.hasSelection()){
            		var record = model.getLastSelected();
            		agregarEditarOpcionLiga(record);
            		
            	}else{
            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
            	}
            }
        },{
            icon    : '${ctx}/resources/fam3icons/icons/delete.png',
            text    : 'Eliminar opci&oacute;n',
            scope   : this,
            handler : function (btn, e){
            	var model =  gridOpcionesLiga.getSelectionModel();
            	if(model.hasSelection()){
            		
            		Ext.Msg.show({
    		            title: 'Aviso',
    		            msg: '&iquest;Esta seguro que desea eliminar esta opci&oacute;n liga/URL?',
    		            buttons: Ext.Msg.YESNO,
    		            fn: function(buttonId, text, opt) {
    		            	if(buttonId == 'yes') {
    		            		Ext.Ajax.request({
            						url: _UrlEliminaOpcionLiga,
            						params: {
            					    		'params.pv_cdtitulo_i' : model.getLastSelected().get('CDTITULO')
            						},
            						success: function(response, opt) {
            							var jsonRes=Ext.decode(response.responseText);

            							if(jsonRes.success == true){
            								mensajeCorrecto('Aviso','Se ha eliminado la opci&oacute;n correctamente.');
            								recargaGridOpcionesLiga();        							
                   						}else {
                   							mensajeError('No se pudo eliminar la opci&oacute;n.');
                   						}
            						},
            						failure: function(){
            							mensajeError('No se pudo eliminar la opci&oacute;n.');
            						}
            					});
    		            	}
            			},
    		            animateTarget: btn,
    		            icon: Ext.Msg.QUESTION
        			});
            		
            	}else{
            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
            	}
            }
        }]
    });
	
	var gridMenus = Ext.create('Ext.grid.Panel',
	    {
    	title : 'Menus del rol seleccionado'
    	,height : 250
    	,selType: 'checkboxmodel'
    	,store : menusStore
    	,columns :
    	[ { header     : 'CDMENU' , dataIndex : 'CDMENU', hidden: true},
          { header     : 'Nombre Men&uacute;', dataIndex : 'DSMENU', flex: 1},
          { header     : 'CDPERSON' , dataIndex : 'CDPERSON', hidden: true},
          { header     : 'CDROL' , dataIndex : 'CDROL', hidden: true},
          { header     : 'CDUSUARIO' , dataIndex : 'CDUSUARIO', hidden: true},
          { header     : 'CDESTADO' ,dataIndex : 'CDESTADO', hidden: true},
          { header     : 'CDTIPOMENU' ,dataIndex : 'CDTIPOMENU', hidden: true}
		]
    	,bbar     :
        {
            displayInfo : true,
            store       : menusStore,
            xtype       : 'pagingtoolbar'
            
        },
        tbar: [{
            icon    : '${ctx}/resources/fam3icons/icons/add.png',
            text    : 'Agregar menu',
            handler : function()
            {
            	if(Ext.isEmpty(panelMenus.down('[name=cdsisrol]').getValue())){
            		showMessage("Aviso","Debe seleccionar un rol.", Ext.Msg.OK, Ext.Msg.INFO);
            		return;
            	}
            	agregarEditarMenu();
            }
        },{
            icon    : '${ctx}/resources/fam3icons/icons/pencil.png',
            text    : 'Editar menu',
            handler : function()
            {
            	var model =  gridMenus.getSelectionModel();
            	if(model.hasSelection()){
            		var record = model.getLastSelected();
            		agregarEditarMenu(record);
            		
            	}else{
            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
            	}
            }
        },{
            icon    : '${ctx}/resources/fam3icons/icons/user_edit.png',
            text    : 'Ver/Editar Opciones Menu',
            handler : function()
            {
            	var model =  gridMenus.getSelectionModel();
            	if(model.hasSelection()){
            		var record = model.getLastSelected();
					windowOpcionesMenu(record.get('CDMENU'));            		
            	}else{
            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
            	}
            }
        },{
            icon    : '${ctx}/resources/fam3icons/icons/delete.png',
            text    : 'Eliminar menu',
            scope   : this,
            handler : function (btn, e){
            	var model =  gridMenus.getSelectionModel();
            	if(model.hasSelection()){
            		
            		Ext.Msg.show({
    		            title: 'Aviso',
    		            msg: '&iquest;Esta seguro que desea eliminar este menu?',
    		            buttons: Ext.Msg.YESNO,
    		            fn: function(buttonId, text, opt) {
    		            	if(buttonId == 'yes') {
    		            		Ext.Ajax.request({
            						url: _UrlEliminaMenu,
            						params: {
            					    		'params.pv_cdmenu_i' : model.getLastSelected().get('CDMENU')
            						},
            						success: function(response, opt) {
            							var jsonRes=Ext.decode(response.responseText);

            							if(jsonRes.success == true){
            								mensajeCorrecto('Aviso','Se ha eliminado el menu correctamente.');
    										recargaGridMenus();        							
                   						}else {
                   							mensajeError('No se pudo eliminar el menu.');
                   						}
            						},
            						failure: function(){
            							mensajeError('No se pudo eliminar el menu.');
            						}
            					});
    		            	}
            			},
    		            animateTarget: btn,
    		            icon: Ext.Msg.QUESTION
        			});
            		
            	}else{
            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
            	}
            }
        }]
    });
		
	var panelMenus = Ext.create('Ext.panel.Panel',{
		title: 'Administraci&oacute;n de Menus',
		defaults : {
			style : 'margin : 5px;'
		}
	    ,renderTo : 'mainDivMenus'
	    ,items :
	    [{
	    	layout: 'column',
	    	border: false,
	    	items: [{
		    	xtype: 'textfield',
		    	fieldLabel: 'Nombre de Opci&oacute;n:',
		    	name: 'dstitle',
		    	labelWidth: 120,
		    	flex: 1
		    },{
		    	xtype: 'button',
		    	text: 'Buscar Opciones',
		    	handler: function(){
		    		recargaGridOpcionesLiga();
		    	}
		    }]
	    },gridOpcionesLiga,
	     {
	    	layout: 'column',
	    	border: false,
	    	html:'<br/><br/><strong>Para configurar menus, primero seleccione un rol.</strong><br/>'
	     },
	     {
            xtype         : 'combo',
            labelWidth    : 100,
            width         : 400,
            name          : 'cdsisrol',
            fieldLabel    : 'Rol del Sistema',
            valueField    : 'key',
            displayField  : 'value',
            forceSelection: true,
            queryMode     :'local',
            editable      : false,
            forceSelection: true,
            store         : Ext.create('Ext.data.Store', {
                model     : 'Generic',
                autoLoad  : true,
                proxy     : {
                    type        : 'ajax'
                    ,url        : _URL_CARGA_CATALOGO
                    ,extraParams: {catalogo:_CAT_ROLES_SISTEMA}
                    ,reader     :
                    {
                        type  : 'json'
                        ,root : 'lista'
                    }
                }
            }),
           listeners: {
        	   select: function ( combo, records, eOpts ){
        		   //var cdrol = records[0].get('key');
        		   recargaGridMenus();
        		   
        	   }
           }
        },{
	    	layout: 'column',
	    	border: false,
	    	html:'<br/>'
	     },
        gridMenus]
	});
	/*///////////////////*/
	////// contenido //////
	///////////////////////
	
	recargaGridOpcionesLiga = function(){
		
		var params = {
			'params.pv_dstitulo_i'	: panelMenus.down('[name=dstitle]').getValue()
		};
		cargaStorePaginadoLocal(opcionesLigaStore, _UrlOpcionesLiga, 'loadList', params, function (options, success, response){
    		if(success){
                var jsonResponse = Ext.decode(response.responseText);
                
                if(!jsonResponse.success) {
                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
                }
            }else{
                showMessage('Error', 'Error al obtener los datos.', Ext.Msg.OK, Ext.Msg.ERROR);
            }
    	}, gridOpcionesLiga);
	};

	recargaGridMenus = function(){
    	
		var params = {
			'params.pv_cdmenu_i': '',
			'params.pv_dsmenu_i': '',
			'params.pv_cdrol_i'	: panelMenus.down('[name=cdsisrol]').getValue()
		};
		cargaStorePaginadoLocal(menusStore, _UrlMenusPorRol, 'loadList', params, function (options, success, response){
    		if(success){
                var jsonResponse = Ext.decode(response.responseText);
                
                if(!jsonResponse.success) {
                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
                }
            }else{
                showMessage('Error', 'Error al obtener los datos.', Ext.Msg.OK, Ext.Msg.ERROR);
            }
    	}, gridMenus);
	};
	
	function agregarEditarOpcionLiga(record){

		var panelEdicionOpcionLiga = Ext.create('Ext.form.Panel',{
	        border  : false
	        ,bodyStyle:'padding:5px;'
	        ,url: _UrlGuardaOpcionLiga
	        ,items :
	        [   {
			        xtype      : 'textfield'
			    	,fieldLabel : 'Nombre de la Opci&oacute;n'
		    		,labelWidth: 120
		    		,width: 350
		    		,allowBlank:false
			    	,name       : 'params.pv_dstitulo_i'
			    	,value: record? record.get('DSTITULO') : ''
			    	,readOnly: record?true:false
	    		},{
			        xtype      : 'textfield'
				   	,fieldLabel : 'Liga/URL'
			    	,labelWidth: 120
			    	,width: 600
			    	,allowBlank:false
				   	,name       : 'params.pv_dsurl_i'
				   	,value: record? record.get('DSURL') : ''
				
		    	}
	        ]
	    });
		 var windowOpcionLiga = Ext.create('Ext.window.Window', {
	          title: record?'Editar Opci&oacute;n Liga/URL':'Agregar Opci&oacute;n Liga/URL',
	          closeAction: 'hide',
	          modal:true,
	          items:[panelEdicionOpcionLiga],
	          bodyStyle:'padding:15px;',
	          buttons:[{
	                 text: 'Guardar',
	                 icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
	                 handler: function() {
	                       if (panelEdicionOpcionLiga.form.isValid()) {
	                    	   
	                    	   panelEdicionOpcionLiga.form.submit({
	           		        	waitMsg:'Procesando...',			
	           		        	params: {
	           		        		'params.pv_cdtitulo_i'   : record? record.get('CDTITULO') : ''
	           		        	},
	           		        	failure: function(form, action) {
	           		        		mensajeError("Error al guardar la opci&oacute;n");
	           					},
	           					success: function(form, action) {
	           						
	           						recargaGridOpcionesLiga();
	           						panelEdicionOpcionLiga.getForm().reset();
	           						windowOpcionLiga.close();
	                               	mensajeCorrecto("Aviso","Se ha guardado la opci&oacute;n.");
	           						
	           						
	           					}
	           				});
	                       	
	                       } else {
	                           Ext.Msg.show({
	                                  title: 'Aviso',
	                                  msg: 'Complete la informaci&oacute;n requerida',
	                                  buttons: Ext.Msg.OK,
	                                  icon: Ext.Msg.WARNING
	                              });
	                       }
	                   }
	             },
	           {
	                 text: 'Cancelar',
	                 icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
	                 handler: function() {
	                	 panelEdicionOpcionLiga.getForm().reset();
	                	 windowOpcionLiga.close();
	                 }
	           }
	          ]
	          });
		 windowOpcionLiga.show();
	}
	
	function agregarEditarMenu(record){

		var panelEdicionMenu = Ext.create('Ext.form.Panel',{
	        border  : false
	        ,bodyStyle:'padding:5px;'
	        ,url: _UrlGuardaMenu
	        ,items :
	        [   {
			        xtype      : 'textfield'
			    	,fieldLabel : 'Nombre del Men&uacute;'
		    		,labelWidth: 120
		    		,width: 350
		    		,allowBlank:false
			    	,name       : 'params.pv_dsmenu_i'
			    	,value: record? record.get('DSMENU') : ''
	    		}
	        ]
	    });
		 var windowMenu = Ext.create('Ext.window.Window', {
	          title: record?'Editar Men&uacute;':'Agregar Men&uacute;',
	          closeAction: 'hide',
	          modal:true,
	          items:[panelEdicionMenu],
	          bodyStyle:'padding:15px;',
	          buttons:[{
	                 text: 'Guardar',
	                 icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
	                 handler: function() {
	                       if (panelEdicionMenu.form.isValid()) {
	                    	   
	                    	   panelEdicionMenu.form.submit({
	           		        	waitMsg:'Procesando...',			
	           		        	params: {
	           		        		'params.pv_cdmenu_i'    : record? record.get('CDMENU') : '',
	           		        		'params.pv_cdelemento_i': '6442',
	           		        		'params.pv_cdperson_i'  : '',
	           		        		'params.pv_cdrol_i'     : record? record.get('CDROL') : panelMenus.down('[name=cdsisrol]').getValue(),
	           		        		'params.pv_cdusuario_i' : '',
	           		        		'params.pv_cdestado_i'  : '1',
	           		        		'params.pv_cdtipomenu_i': '1'
	           		        	},
	           		        	failure: function(form, action) {
	           		        		mensajeError("Error al guardar el Men&uacute;. " + action.result.errorMessage);
	           					},
	           					success: function(form, action) {
	           						recargaGridMenus();
	           						panelEdicionMenu.getForm().reset();
	           						windowMenu.close();
	                               	mensajeCorrecto("Aviso","Se ha guardado el Men&uacute;");
	           					}
	           				});
	                       	
	                       } else {
	                           Ext.Msg.show({
	                                  title: 'Aviso',
	                                  msg: 'Complete la informaci&oacute;n requerida',
	                                  buttons: Ext.Msg.OK,
	                                  icon: Ext.Msg.WARNING
	                              });
	                       }
	                   }
	             },
	           {
	                 text: 'Cancelar',
	                 icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
	                 handler: function() {
	                	 panelEdicionMenu.getForm().reset();
	                	 windowMenu.close();
	                 }
	           }
	          ]
	          });
		 windowMenu.show();
	}
	
	
	function windowOpcionesMenu(_cdMenu){
		debug('CdMenu: ',_cdMenu);
		
		var opcionesMenuStore = Ext.create('Ext.data.Store',
			    {
					pageSize : 20,
			        autoLoad : true
			        ,model   : 'gridOpMenuModel'
			        ,proxy   :
			        {
			            type         : 'memory'
			            ,enablePaging : true
			            ,reader      : 'json'
			            ,data        : []
			        }
		});
		
		var gridOpMenu = Ext.create('Ext.grid.Panel',
			    {
		    	height : 250
		    	,selType: 'checkboxmodel'
		    	,store : opcionesMenuStore
		    	,columns :
		    	[ { header     : 'CDNIVEL' , dataIndex : 'CDNIVEL', hidden: true},
		          { header     : 'Nombre Opci&oacute;n Men&uacute;', dataIndex : 'DSMENU_EST', flex: 1}
				]
		    	,bbar     :
		        {
		            displayInfo : true,
		            store       : opcionesMenuStore,
		            xtype       : 'pagingtoolbar'
		            
		        },
		        tbar: [{
		            icon    : '${ctx}/resources/fam3icons/icons/add.png',
		            text    : 'Agregar opci&oacute;n menu',
		            handler : function()
		            {
		            	agregarEditarOpMenu();
		            }
		        },{
		            icon    : '${ctx}/resources/fam3icons/icons/pencil.png',
		            text    : 'Editar opci&oacute;n menu',
		            handler : function()
		            {
		            	var model =  gridOpMenu.getSelectionModel();
		            	if(model.hasSelection()){
		            		var record = model.getLastSelected();
		            		agregarEditarOpMenu(record);
		            		
		            	}else{
		            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
		            	}
		            }
		        },{
		            icon    : '${ctx}/resources/fam3icons/icons/user_edit.png',
		            text    : 'Ver/Editar Opciones SubMenu',
		            handler : function()
		            {
		            	var model =  gridOpMenu.getSelectionModel();
		            	if(model.hasSelection()){
		            		var record = model.getLastSelected();
		            		
		            		
		            	}else{
		            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
		            	}
		            }
		        },{
		            icon    : '${ctx}/resources/fam3icons/icons/delete.png',
		            text    : 'Eliminar opci&oacute;n menu',
		            scope   : this,
		            handler : function (btn, e){
		            	var model =  gridOpMenu.getSelectionModel();
		            	if(model.hasSelection()){
		            		
		            		Ext.Msg.show({
		    		            title: 'Aviso',
		    		            msg: '&iquest;Esta seguro que desea eliminar esta opci&oacute;n de menu?',
		    		            buttons: Ext.Msg.YESNO,
		    		            fn: function(buttonId, text, opt) {
		    		            	if(buttonId == 'yes') {
		    		            		Ext.Ajax.request({
		            						url: _UrlEliminaOpMenu,
		            						params: {
		            					    		'params.pv_cdmenu_i' : model.getLastSelected().get('CDMENU')
		            						},
		            						success: function(response, opt) {
		            							var jsonRes=Ext.decode(response.responseText);

		            							if(jsonRes.success == true){
		            								mensajeCorrecto('Aviso','Se ha eliminado la opci&oacute;n de menu correctamente.');
		    										recargaGridMenus();        							
		                   						}else {
		                   							mensajeError('No se pudo eliminar la opci&oacute;n de menu.');
		                   						}
		            						},
		            						failure: function(){
		            							mensajeError('No se pudo eliminar la opci&oacute;n de menu.');
		            						}
		            					});
		    		            	}
		            			},
		    		            animateTarget: btn,
		    		            icon: Ext.Msg.QUESTION
		        			});
		            		
		            	}else{
		            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
		            	}
		            }
		        }]
		    });
		var windowOpMenu = Ext.create('Ext.window.Window', {
			  title : 'Opciones de Men&uacute;',
	          //closeAction: 'hide',
	          modal:true,
	          width : 600,
	          height : 400,
	          items:[gridOpMenu],
	          bodyStyle:'padding:15px;',
	          buttons:[{
		                 text: 'Cerrar',
		                 icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
		                 handler: function() {
		                	 windowOpMenu.close();
		                 }
	           		} ]
	          });
		 	windowOpMenu.show();
				
		
	}

});
</script>

</head>
<body>
<div id="mainDivMenus" style="height:1500px;"></div>
</body>
</html>