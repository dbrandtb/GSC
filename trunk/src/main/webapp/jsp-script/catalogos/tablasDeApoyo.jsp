<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tablas de Apoyo</title>
<script>

///////////////////////
////// variables //////
/*///////////////////*/

var _UrlObtieneTablas       = '<s:url namespace="/catalogos"    action="obtieneTablasApoyo" />';
var _UrlEliminarTabla       = '<s:url namespace="/catalogos"    action="eliminaTablaApoyo" />';
var _URL_LOADER_NUEVA_TABLA = '<s:url namespace="/catalogos"    action="includes/agregaTablaApoyo" />';
var _URL_LOADER_DATOS_TABLA = '<s:url namespace="/catalogos"    action="includes/datosTablaApoyo" />';

var _MSG_SIN_DATOS          = 'No hay datos';
var _MSG_BUSQUEDA_SIN_DATOS = 'No hay datos para la b\u00FAsqueda actual.';

/*///////////////////*/
////// variables //////
///////////////////////

Ext.onReady(function(){
	
	/////////////////////
	////// modelos //////
	/*/////////////////*/
	Ext.define('gridTablasModel',
	{
		extend : 'Ext.data.Model'
		,fields :
		['NMTABLA','CDTABLA','DSTABLA','OTTIPOTB','OTTIPOTB_DESC']
	});
	
	/*/////////////////*/
	////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
	/*////////////////*/
	var tablasStore = Ext.create('Ext.data.Store',
    {
		pageSize : 30,
        autoLoad : true
        ,model   : 'gridTablasModel'
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
	var gridTablas = Ext.create('Ext.grid.Panel',
	    {
    	title : 'Tablas de Apoyo'
    	,height : 400
    	,selModel: {
	 		selType: 'checkboxmodel',
	 		allowDeselect: true,
	 		mode: 'SINGLE'
	 	}
    	,store : tablasStore
    	,columns :
    	[ 
    	  { header     : 'OTTIPOTB', dataIndex : 'OTTIPOTB', hidden: true},
    	  { header     : 'N&uacute;mero de Tabla' , dataIndex : 'NMTABLA', flex: 1},
    	  { header     : 'C&oacute;digo de Tabla' , dataIndex : 'CDTABLA', flex: 1},
    	  { header     : 'Descripci&oacute;n de Tabla' , dataIndex : 'DSTABLA', flex: 2},
          { header     : 'Tipo de Tabla' , dataIndex : 'OTTIPOTB_DESC' , flex: 1}
		]
    	,bbar :
        {
            displayInfo : true,
            store       : tablasStore,
            xtype       : 'pagingtoolbar'
        },
        tbar: [{
            icon    : '${ctx}/resources/fam3icons/icons/add.png',
            text    : 'Agregar tabla',
            handler : function()
            {
            	windowLoader = Ext.create('Ext.window.Window',
                {
                    title        : 'Agregar Tabla'
                    ,modal       : true
                    ,buttonAlign : 'center'
                    ,width       : 900
                    ,height      : 600
                    ,autoScroll  : true
                    ,loader      :
                    {
                        url       : _URL_LOADER_NUEVA_TABLA
                        ,scripts  : true
                        ,autoLoad : true
                        ,loadMask : true
                        ,ajaxOptions: {
                            method   : 'POST'
                        },
                        params: {
                        	'params.edit' : 'N'
                        }
                    }
                }).show();
            }
        },{
            icon    : '${ctx}/resources/fam3icons/icons/pencil.png',
            text    : 'Editar tabla',
            handler : function()
            {
            	var model =  gridTablas.getSelectionModel();
            	if(model.hasSelection()){
            		var record = model.getLastSelected();
            		
            		var dsusuario = record.get('dsUsuario');
            		var nombre = record.get('dsNombre');
            		var snombre = record.get('dsNombre1');
            		var appat = record.get('dsApellido');
            		var apmat = record.get('dsApellido1');
            		
            		windowLoader = Ext.create('Ext.window.Window',
                            {
                                title        : 'Editar Tabla'
                                ,modal       : true
                                ,buttonAlign : 'center'
                                ,width       : 800
                                ,height      : 600
                                ,autoScroll  : true
                                ,loader      :
                                {
                                    url       : _URL_LOADER_NUEVA_TABLA
                                    ,scripts  : true
                                    ,autoLoad : true
                                    ,loadMask : true
                                    ,ajaxOptions: {
                                        method   : 'POST'
                                    },
                                    params: {
                                    	'params.edit' : 'S',
                                    	'params.cdsisrol': record.get('cdrol'),
                                    	'params.cdmodgra': record.get('esAdmin'),
                                    	'params.cdunieco': record.get('cdunieco'),
                                    	'params.cdusuario': record.get('cdUsuario'),
                                    	'params.nombre': nombre,
                                    	'params.snombre': snombre,
                                    	'params.appat': appat,
                                    	'params.apmat': apmat,
                                    	'params.sexo': record.get('otSexo'),
                                    	'params.fecnac': record.get('feNacimi'),
                                    	'params.rfc': record.get('cdrfc'),
                                    	'params.curp': record.get('curp'),
                                    	'params.email': record.get('dsEmail'),
                                    	'params.feini': record.get('feini'),
                                    	'params.fefin': record.get('fefinlic')
                                    }
                                }
                            }).show();
            		
            	}else{
            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
            	}
            }
        },{
            icon    : '${ctx}/resources/fam3icons/icons/delete.png',
            text    : 'Eliminar Tabla',
            scope   : this,
            handler : function (btn, e){
            	var model =  gridTablas.getSelectionModel();
            	if(model.hasSelection()){
            		Ext.Msg.show({
    		            title: 'Aviso',
    		            msg: '&iquest;Esta seguro que desea eliminar esta tabla?',
    		            buttons: Ext.Msg.YESNO,
    		            fn: function(buttonId, text, opt) {
    		            	if(buttonId == 'yes') {
    		            		
    		            		Ext.Ajax.request({
            						url: _UrlEliminarTabla,
            						params: {
            					    		'params.PV_CDUSUARI_I'  : model.getLastSelected().get('cdUsuario')
            						},
            						success: function(response, opt) {
            							var jsonRes=Ext.decode(response.responseText);

            							if(jsonRes.success == true){
            								mensajeCorrecto('Aviso','Se ha eliminado la tabla correctamente.');
    										recargaGridUsuarios();        							
                   						}else {
                   							mensajeError('No se pudo eliminar la tabla.');
                   						}
            						},
            						failure: function(){
            							mensajeError('No se pudo eliminar la tabla.');
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
        },'->',{
            icon    : '${ctx}/resources/fam3icons/icons/database_add.png',
            text    : 'Consulta y Actualizaci&oacute;n de Valores',
            handler : function()
            {
            	var model =  gridUsuarios.getSelectionModel();
            	if(model.hasSelection()){
            		var record = model.getLastSelected();
            		
            		if('EJECUTIVOCUENTA' != record.get('cdrol')){
            			mensajeWarning('No se pueden editar los productos para un usuario que no sea Agente.');
            			return;
            		}
            		windowLoader = Ext.create('Ext.window.Window',
                            {
                                title        : 'Ver/Editar Productos del usuario: ' + record.get('cdUsuario')
                                ,modal       : true
                                ,buttonAlign : 'center'
                                ,width       : 500
                                ,height      : 350
                                ,autoScroll  : true
                                ,loader      :
                                {
                                    url       : _URL_LOADER_DATOS_TABLA
                                    ,scripts  : true
                                    ,autoLoad : true
                                    ,loadMask : true
                                    ,ajaxOptions: {
                                        method   : 'POST'
                                    },
                                    params: {
                                    	'params.cdagente': record.get('cdUsuario')
                                    }
                                }
                            }).show();
            		
            	}else{
            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
            	}
            }
        }]
    });
	
	var panelTablas = Ext.create('Ext.form.Panel',{
		title: 'B&uacute;squeda de Tablas de Apoyo',
		defaults : {
			style : 'margin : 5px;'
		},
	    renderTo : 'mainDivTabs',
	    buttonAlign: 'center',
	    items :
	    [{
	    	layout: {type:'hbox', pack: 'center'},
	    	border: false,
	    	bodyStyle:'padding:5px;',
	    	defaults: {
	    		style: 'margin: 5px'// para hacer que los componentes se separen 5px
	    	},
	    	buttonAlign: 'center',
	    	items: [{
		    	xtype: 'textfield',
		    	fieldLabel: 'N&uacute;mero Tabla',
		    	labelWidth: 60,
		    	name: 'params.PV_NMTABLA_I'
		    },{
		    	xtype: 'textfield',
		    	fieldLabel: 'C&oacute;digo Tabla',
		    	labelWidth: 60,
		    	name: 'params.PV_CDTABLA_I'
		    },{
		    	xtype: 'textfield',
		    	fieldLabel: 'Descripci&oacute;n Tabla',
		    	labelWidth: 70,
		    	name: 'params.PV_DSTABLA_I'
		    },{
	            xtype         : 'combobox',
	            labelWidth    : 40,
	            //width         : 200,
	            name          : 'params.PV_OTTIPOTB_I',
	            fieldLabel    : 'Tipo Tabla',
	            valueField    : 'key',
	            displayField  : 'value',
	            forceSelection: true,
	            queryMode     : 'local',
	            typeAhead     : true,
	            anyMatch      : true,
	            store         :  Ext.create('Ext.data.Store', {
							     model: 'Generic',
							     data : [
							         {key: '1', value: 'Tabla de 1 Clave'},
							         {key: '5', value: 'Tabla de 5 Claves'}
							     ]
							 })
	        }],
		    buttons:
		    	[	{
	                    text     : 'Limpiar'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/control_repeat_blue.png'
	                    ,handler : function()
	                    {
	                    	panelTablas.getForm().reset();
	                    }
	                },
	                {
	                    text     : 'Buscar'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
	                    ,handler : function()
	                    {
	                        if(panelTablas.isValid())
	                        {
	                        	recargagridTablas();
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
	                }
	            ]
	    },gridTablas]
	    
	});
	/*///////////////////*/
	////// contenido //////
	///////////////////////
	
	recargagridTablas = function(){
		
		cargaStorePaginadoLocal(tablasStore, _UrlObtieneTablas, 'loadList', panelTablas.getValues(), function (options, success, response){
    		if(success){
                var jsonResponse = Ext.decode(response.responseText);
                
                if(!jsonResponse.success) {
                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
                }
            }else{
                showMessage('Error', 'Error al obtener los datos.', Ext.Msg.OK, Ext.Msg.ERROR);
            }
    	}, gridTablas);
	};
	
});
</script>

</head>
<body>
<div id="mainDivTabs" style="height:500px;"></div>
</body>
</html>