<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reenvio de datos de WS fallidos</title>
<script>

var _CONTEXT = '${ctx}';
extjs_custom_override_mayusculas = false;

var recargaGridPeticiones;

var _UrlPeticionesFallidas = '<s:url namespace="/general"    action="obtienePeticionesWSFallidas" />';
var _UrlReenviarPeticion   = '<s:url namespace="/general"    action="reenviaPeticionWS" />';
var _UrlEliminarPeticion   = '<s:url namespace="/general"    action="eliminaPeticionWS" />';

var _MSG_SIN_DATOS          = 'No hay datos';
var _MSG_BUSQUEDA_SIN_DATOS = 'No hay datos para la b\u00FAsqueda actual.';


Ext.onReady(function()
{
	
	/////////////////////
	////// modelos //////
	/*/////////////////*/
	Ext.define('gridPeticionesModel',
	{
		extend : 'Ext.data.Model'
		,fields :
		['CDUNIECO','CDRAMO','NMPOLIZA','NMSUPLEM','CDCODIGO',
		 'MENSAJE','USUARIO','FECHAHR','NTRAMITE','CDERRWS','SEQIDWS']
	});
	
	/*/////////////////*/
	////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
	/*////////////////*/
	var peticionesStore = Ext.create('Ext.data.Store',
    {
		pageSize : 30,
        autoLoad : true
        ,model   : 'gridPeticionesModel'
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
	var gridPeticiones = Ext.create('Ext.grid.Panel',
	    {
    	title : 'Peticiones de WS Fallidas'
    	,height : 400
    	,selModel: {
	 		selType: 'checkboxmodel',
	 		allowDeselect: true,
	 		mode: 'MULTI'
	 	}
    	,store : peticionesStore
    	,columns :
    	[ { header     : 'CodigoSeq', dataIndex : 'SEQIDWS', hidden: true},
    	  { header     : 'Producto' , dataIndex : 'CDRAMO', flex: 1},
    	  { header     : 'Sucursal' , dataIndex : 'CDUNIECO', flex: 1},
    	  { header     : 'P&oacute;liza' , dataIndex : 'NMPOLIZA', flex: 2},
    	  { header     : 'Tr&aacute;mite', dataIndex : 'NTRAMITE', flex: 1},
    	  { header     : 'Suplemento'    , dataIndex : 'NMSUPLEM', hidden: true},
    	  { header     : 'C&oacute;digo Tipo Error' , dataIndex : 'CDCODIGO', flex: 2},
    	  { header     : 'Mensaje'  , dataIndex : 'MENSAJE', flex: 5},
    	  { header     : 'C&oacute;digo Error WS' , dataIndex : 'CDERRWS', flex: 1},
    	  { header     : 'Fecha'   , dataIndex : 'FECHAHR', flex: 2},
          { header     : 'Usuario' , dataIndex : 'USUARIO' , flex: 2}
		]
    	,bbar :
        {
            displayInfo : true,
            store       : peticionesStore,
            xtype       : 'pagingtoolbar'
        },
        tbar: [{
            icon    : '${ctx}/resources/fam3icons/icons/arrow_right.png',
            text    : 'Reenviar Petici&oacute;n WS',
            handler : function(btn, e){
            	var model =  gridPeticiones.getSelectionModel();
            	if(model.hasSelection() && model.getSelection().length == 1){
            		Ext.Msg.show({
                        title: 'Aviso',
                        msg: '&iquest;Esta seguro que desea reenviar esta Petici&oacute;n WS?',
                        buttons: Ext.Msg.YESNO,
                        fn: function(buttonId, text, opt) {
                        	if(buttonId == 'yes') {
                        		
                        		var seqIdWS = model.getSelection()[0].get('SEQIDWS');
                        		
                        		var saveList = [];
                        		saveList.push(model.getSelection()[0].data);
    		            		
                        		Ext.Ajax.request({
            						url: _UrlReenviarPeticion,
            						params: {
            					    		'params.pv_seqidws_i' : seqIdWS,
            					    		'params.modo'         : panelPeticiones.down('[name=params.pv_cdramo_i]').getValue()
            						},
            						success: function(response, opt) {
            							var jsonRes=Ext.decode(response.responseText);

            							if(jsonRes.success == true){
            								//mensajeCorrecto('Aviso','Se ha reenviado correctamente la Petici&oacute;n WS, Respuesta: '+ jsonRes.mensajeRespuesta);
            								var ventanaResWS = Ext.create('Ext.window.Window',{
    											title   : 'Respuesta de la petici&oacute;n WS',
    											modal  : true,
    											layout: {type:'hbox', pack: 'center'},
    											width  : 500,
    											height : 500,
    											items  :
    											[{
    										        xtype     : 'textareafield',
    										        grow      : true,
    										        value     : jsonRes.mensajeRespuesta,
    										        width     : 450,
    										        height    : 400
    										    }],
    										    buttons: [{
    				        	            			text: 'Eliminar esta petici&oacute;n de la Bit&aacute;cora'
    				            	            		,icon:_CONTEXT+'/resources/fam3icons/icons/delete.png'
    				            	            		,handler: function() {
    				            	            			Ext.Ajax.request({
    				                    						url: _UrlEliminarPeticion,
    				                    						jsonData: {
    				                    							saveList : 	saveList
    				                    						},
    				                    						success: function(response, opt) {
    				                    							var jsonRes=Ext.decode(response.responseText);

    				                    							if(jsonRes.success == true){
    				                    								mensajeCorrecto('Aviso','Se ha eliminado la Petici&oacute;n WS.');
    				                    								recargaGridPeticiones();        							
    				                    								ventanaResWS.close();
    				                           						}else {
    				                           							mensajeError('No se pudo eliminar la Petici&oacute;n WS.');
    				                           						}
    				                    						},
    				                    						failure: function(){
    				                    							mensajeError('No se pudo eliminar la Petici&oacute;n WS.');
    				                    						}
    				                    					});
    				            	            		}
    				            	            	},{
    				            	            	    text: 'Cerrar',
    				            	            	    icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
    				            	            	    handler: function() {
    				            	            	    	ventanaResWS.close();
    				            	            	    }
    				            	            	}]
    										}).show();
    										centrarVentanaInterna(ventanaResWS);
            								recargaGridPeticiones();        							
                   						}else {
                   							mensajeError('No se pudo reenviar la Petici&oacute;n WS. ' + jsonRes.mensajeRespuesta);
                   						}
            						},
            						failure: function(){
            							mensajeError('No se pudo reenviar la Petici&oacute;n WS.');
            						}
            					});
                        	}
            			},
                        animateTarget: btn,
                        icon: Ext.Msg.QUESTION
            		});
            	
            	}else{
            		showMessage("Aviso","Debe seleccionar un solo registro para esta operaci&oacute;n", Ext.Msg.OK, Ext.Msg.INFO);
            	}
            	
            }
        },{
            icon    : '${ctx}/resources/fam3icons/icons/delete.png',
            text    : 'Eliminar Petici&oacute;n WS',
            scope   : this,
            handler : function (btn, e){
            	var model =  gridPeticiones.getSelectionModel();
            	if(model.hasSelection()){
            		
            		Ext.Msg.show({
    		            title: 'Aviso',
    		            msg: '&iquest;Esta seguro que desea eliminar esta(s) Petici&oacute;n(s) WS?',
    		            buttons: Ext.Msg.YESNO,
    		            fn: function(buttonId, text, opt) {
    		            	if(buttonId == 'yes') {
    		            		
    		            		var saveList = [];
    		            		model.getSelection().forEach(function(record,index,arr){
    								saveList.push(record.data);
    							});
    							
    		            		Ext.Ajax.request({
            						url: _UrlEliminarPeticion,
            						jsonData: {
            							saveList : 	saveList
            						},
            						success: function(response, opt) {
            							var jsonRes=Ext.decode(response.responseText);

            							if(jsonRes.success == true){
            								mensajeCorrecto('Aviso','Se ha eliminado la(s) Petici&oacute;n(es) WS.');
            								recargaGridPeticiones();        							
                   						}else {
                   							mensajeError('No se pudo eliminar la(s) Petici&oacute;n(es) WS.');
                   						}
            						},
            						failure: function(){
            							mensajeError('No se pudo eliminar la(s) Petici&oacute;n(es) WS.');
            						}
            					});
    		            	}
            			},
    		            animateTarget: btn,
    		            icon: Ext.Msg.QUESTION
        			});
            		
            	}else{
            		showMessage("Aviso","Debe seleccionar almenos un registro", Ext.Msg.OK, Ext.Msg.INFO);
            	}
            }
        }]
    });
	
	var panelPeticiones = Ext.create('Ext.form.Panel',{
		title: 'Administraci&oacute;n de Peticiones WS fallidas',
		defaults : {
			style : 'margin : 5px;'
		},
	    renderTo : 'mainDivPet',
	    buttonAlign: 'center',
	    items :
	    [{
	    	layout: {type:'hbox', pack: 'center'},
	    	border: false,
	    	bodyStyle:'padding:5px;',
	    	defaults: {
	    		style: 'margin: 15px'// para hacer que los componentes se separen 5px
	    	},
	    	buttonAlign: 'center',
	    	items: [{
		    	xtype: 'textfield',
		    	fieldLabel: 'Producto',
		    	labelWidth: 50,
		    	name: 'params.pv_cdramo_i'
		    },{
		    	xtype: 'textfield',
		    	fieldLabel: 'Sucursal',
		    	labelWidth: 50,
		    	name: 'params.pv_cdunieco_i'
		    },{
		    	xtype: 'textfield',
		    	fieldLabel: 'P&oacute;liza',
		    	labelWidth: 50,
		    	name: 'params.pv_nmpoliza_i'
		    }],
		    buttons:
	            [
	                {
	                    text     : 'Buscar'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
	                    ,handler : function()
	                    {
	                        if(panelPeticiones.isValid())
	                        {
	                        	recargaGridPeticiones();
	                        }
	                        else
	                        {
	                            Ext.Msg.show(
	                            {
	                                title    : 'Datos imcompletos'
	                                ,icon    : Ext.Msg.WARNING
	                                ,msg     : 'Favor de llenar los campos requeridos'
	                                ,buttons : Ext.Msg.OK
	                            });
	                        }
	                    }
	                },
	                {
	                    text     : 'Limpiar'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/control_repeat_blue.png'
	                    ,handler : function()
	                    {
	                    	panelPeticiones.getForm().reset();
	                    }
	                }
	            ]
	    },gridPeticiones]
	    
	});
	/*///////////////////*/
	////// contenido //////
	///////////////////////
	
	recargaGridPeticiones = function(){
		
		cargaStorePaginadoLocal(peticionesStore, _UrlPeticionesFallidas, 'loadList', panelPeticiones.getValues(), function (options, success, response){
    		if(success){
                var jsonResponse = Ext.decode(response.responseText);
                
                if(!jsonResponse.success) {
                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
                }
            }else{
                showMessage('Error', 'Error al obtener los datos.', Ext.Msg.OK, Ext.Msg.ERROR);
            }
    	}, gridPeticiones);
	};
	

});
</script>

</head>
<body>
<div id="mainDivPet" style="height:1500px;"></div>
</body>
</html>