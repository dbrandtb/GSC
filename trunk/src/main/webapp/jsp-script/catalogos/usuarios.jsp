<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Usuarios</title>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var busquedaUsuariosStore;
var windowLoader;
var recargaGridUsuarios;

var _UrlBusquedaUsuarios    = '<s:url namespace="/catalogos"    action="busquedaUsuarios" />';
var _URL_LOADER_NUEVO_USUARIO   = '<s:url namespace="/catalogos"    action="includes/agregaUsuarios" />';
var _URL_LOADER_EDITAR_ROLES    = '<s:url namespace="/catalogos"    action="includes/editarRolesUsuario" />';
var _UrlActualizarUsuario      = '<s:url namespace="/catalogos"    action="guardaUsuario" />';
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
	Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });

	/////////////////////
	////// modelos //////
	/*/////////////////*/
	Ext.define('gridUsuariosModel',
	{
		extend : 'Ext.data.Model'
		,fields :
		[
            <s:property value="fields" />
		]
	});
	/*/////////////////*/
	////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
	/*////////////////*/
	busquedaUsuariosStore = Ext.create('Ext.data.Store',
    {
		pageSize : 20,
        autoLoad : true
        ,model   : 'gridUsuariosModel'
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
	
	/////////////////////////
	////// componentes //////
	/*/////////////////////*/
	/*/////////////////////*/
	////// componentes //////
	/////////////////////////
	
	///////////////////////
	////// contenido //////
	/*///////////////////*/
	var panelUsuarios = Ext.create('Ext.form.Panel',
	        {
    	title : 'B&uacute;squeda de Usuarios'
    	,layout :
    		{
    		type : 'table'
    		,columns : 2
    		}
    	,items :
    	[
    	    <s:property value="items" />
    	]
    	,buttonAlign: 'center'
    	,buttons       :
            [
                {
                    text     : 'Buscar'
                    ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                    ,handler : function()
                    {
                    	var form=this.up().up();
                        if(form.isValid())
                        {
                        	recargaGridUsuarios();
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
                    	this.up().up().getForm().reset();
                    }
                }
            ]
    });
	
	var gridUsuarios = Ext.create('Ext.grid.Panel',
	    {
    	title : 'Grid Usuarios'
    	,height : 350
    	,selType: 'checkboxmodel'
    	,store : busquedaUsuariosStore
    	,columns :
    	[
            <s:property value="columns" />
    	]
    	,bbar     :
        {
            displayInfo : true,
            store       : busquedaUsuariosStore,
            xtype       : 'pagingtoolbar',
            id: 'usuariosbbar'
            
        },
        tbar: [{
            icon    : '${ctx}/resources/fam3icons/icons/add.png',
            text    : 'Agregar usuario',
            handler : function()
            {
            	windowLoader = Ext.create('Ext.window.Window',
                {
                    title        : 'Agregar Usuario'
                    ,modal       : true
                    ,buttonAlign : 'center'
                    ,width       : 500
                    ,height      : 650
                    ,autoScroll  : true
                    ,loader      :
                    {
                        url       : _URL_LOADER_NUEVO_USUARIO
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
            text    : 'Editar usuario',
            handler : function()
            {
            	var model =  gridUsuarios.getSelectionModel();
            	if(model.hasSelection()){
            		var record = model.getLastSelected();
            		
            		var dsusuario = record.get('dsUsuario');
            		var nombre = record.get('dsNombre');
            		var snombre = record.get('dsNombre1');
            		var appat = record.get('dsApellido');
            		var apmat = record.get('dsApellido1');
            		
            		if(record.get('esAgente') == 'N'){
            			nombre = dsusuario;
            		}
            		
            		windowLoader = Ext.create('Ext.window.Window',
                            {
                                title        : 'Editar Usuario'
                                ,modal       : true
                                ,buttonAlign : 'center'
                                ,width       : 500
                                ,height      : 650
                                ,autoScroll  : true
                                ,loader      :
                                {
                                    url       : _URL_LOADER_NUEVO_USUARIO
                                    ,scripts  : true
                                    ,autoLoad : true
                                    ,loadMask : true
                                    ,ajaxOptions: {
                                        method   : 'POST'
                                    },
                                    params: {
                                    	'params.edit' : 'S',
                                    	'params.cdusuario': record.get('cdUsuario'),
                                    	'params.esAgente': record.get('esAgente'),
                                    	'params.nombre': nombre,
                                    	'params.snombre': snombre,
                                    	'params.appat': appat,
                                    	'params.apmat': apmat,
                                    	'params.sexo': record.get('otSexo'),
                                    	'params.fecnac': record.get('feNacimi'),
                                    	'params.rfc': record.get('cdrfc'),
                                    	'params.curp': record.get('curp'),
                                    	'params.email': record.get('dsEmail')
                                    }
                                }
                            }).show();
            		
            	}else{
            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
            	}
            }
        },{
            icon    : '${ctx}/resources/fam3icons/icons/delete.png',
            text    : 'Eliminar usuario',
            scope   : this,
            handler : function (btn, e){
            	var model =  gridUsuarios.getSelectionModel();
            	if(model.hasSelection()){
            		
            		Ext.Msg.show({
    		            title: 'Aviso',
    		            msg: '&iquest;Esta seguro que desea eliminar este usuario?',
    		            buttons: Ext.Msg.YESNO,
    		            fn: function(buttonId, text, opt) {
    		            	if(buttonId == 'yes') {
    		            		Ext.Ajax.request({
            						url: _UrlActualizarUsuario,
            						params: {
            					    		'params.cdusuari' : model.getLastSelected().get('cdUsuario'),
            					    		'params.esAgente' : '0',
            					    		'params.accion'   : 'E'
            						},
            						success: function(response, opt) {
            							var jsonRes=Ext.decode(response.responseText);

            							if(jsonRes.success == true){
            								mensajeCorrecto('Aviso','Se ha eliminado usuario correctamente.');
    										recargaGridUsuarios();        							
                   						}else {
                   							mensajeError('No se pudo eliminar el usuario.');
                   						}
            						},
            						failure: function(){
            							mensajeError('No se pudo eliminar el usuario.');
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
        },'-',{
            icon    : '${ctx}/resources/fam3icons/icons/user_edit.png',
            text    : 'Ver/Editar Roles',
            handler : function()
            {
            	var model =  gridUsuarios.getSelectionModel();
            	if(model.hasSelection()){
            		var record = model.getLastSelected();
            		
            		windowLoader = Ext.create('Ext.window.Window',
                            {
                                title        : 'Ver/Editar Roles del usuario: ' + record.get('dsUsuario')
                                ,modal       : true
                                ,buttonAlign : 'center'
                                ,width       : 500
                                ,height      : 350
                                ,autoScroll  : true
                                ,loader      :
                                {
                                    url       : _URL_LOADER_EDITAR_ROLES
                                    ,scripts  : true
                                    ,autoLoad : true
                                    ,loadMask : true
                                    ,ajaxOptions: {
                                        method   : 'POST'
                                    },
                                    params: {
                                    	'params.cdusuario': record.get('cdUsuario'),
                                    	'params.esAgente': record.get('esAgente')
                                    }
                                }
                            }).show();
            		
            	}else{
            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
            	}
            }
        },'->',{
            icon    : '${ctx}/resources/fam3icons/icons/award_star_silver_2.png',
            text    : 'Creaci&oacute;n de Roles',
            handler : function(){
            			crearEditarRoles();
            		}
        }]
    });
		
		
	Ext.create('Ext.panel.Panel',{
		defaults : {
			style : 'margin : 5px;'
		}
	    ,renderTo : 'mainDiv'
	    ,items :
	    [panelUsuarios,gridUsuarios]
	});
	/*///////////////////*/
	////// contenido //////
	///////////////////////
	
	recargaGridUsuarios = function(){
		
		gridUsuarios.setLoading(true);
    	Ext.getCmp('usuariosbbar').moveFirst();
    	
		cargaStorePaginadoLocal(busquedaUsuariosStore, 
    			_UrlBusquedaUsuarios, 
                'usuarios', 
                panelUsuarios.getValues(), 
                function (options, success, response){
    		
    		if(success){
                var jsonResponse = Ext.decode(response.responseText);
                if(jsonResponse.usuarios && jsonResponse.usuarios.length == 0) {
                    //showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
                }
                
            }else{
                showMessage('Error', 'Error al obtener los datos.', 
                    Ext.Msg.OK, Ext.Msg.ERROR);
            }
    		gridUsuarios.setLoading(false);
    	});
	};
	
	
	function crearEditarRoles(){
		
		var _URL_CARGA_CATALOGO = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
		var _CAT_ROLES_SISTEMA  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@ROLES_SISTEMA"/>';
		var _UrlCrearEditarRol  = '<s:url namespace="/catalogos"    action="creaEditaRolSistema" />';
		
		var recargaGridCreaRoles;
		
		var creaRolesStore = Ext.create('Ext.data.Store',
			    {
					pageSize : 20,
			        autoLoad : true
			        ,model   : 'Generic'
			        ,proxy   :
			        {
			            type         : 'memory'
			            ,enablePaging : true
			            ,reader      : 'json'
			            ,data        : []
			        }
			    });
		
		var gridCreaRoles = Ext.create('Ext.grid.Panel',
			    {
		    	title : 'Roles existentes en la aplicaci&oacute;n'
		    	,height : 300
		    	,selType: 'checkboxmodel'
		    	,store : creaRolesStore
		    	,columns :
		    	[ { header     : 'C&oacute;digo del Rol' , dataIndex : 'key', flex: 2},
		    	  { header     : 'Nombre del Rol' , dataIndex : 'value', flex: 5}
				]
		    	,bbar     :
		        {
		            displayInfo : true,
		            store       : creaRolesStore,
		            xtype       : 'pagingtoolbar'
		        },
		        tbar: [{
		            icon    : '${ctx}/resources/fam3icons/icons/add.png',
		            text    : 'Crear Rol',
		            handler : function(){crearEditarRol();}
		        },{
		            icon    : '${ctx}/resources/fam3icons/icons/pencil.png',
		            text    : 'Editar Rol',
		            handler : function()
		            {
		            	var model =  gridCreaRoles.getSelectionModel();
		            	if(model.hasSelection()){
		            		var record = model.getLastSelected();
		            		crearEditarRol(record);
		            		
		            	}else{
		            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
		            	}
		            }
		        }]
		    });
		
		
			var windowCrearRoles = Ext.create('Ext.window.Window', {
		          title: 'Creci&oacute;n de Roles',
		          modal:true,
		          height : 450,
		          width  : 800,
		          items:[{
		        	layout: {type:'hbox', pack: 'center'},
		        	defaults: {
			    		style: 'margin: 10px'// para hacer que los componentes se separen 5px
			    	},
			    	border: false,
			    	items: [{
				    	xtype: 'textfield',
				    	fieldLabel: 'Nombre del Rol:',
				    	name: 'dsCreaEditaRol',
				    	labelWidth: 120
				    },{
				    	xtype: 'button',
				    	text: 'Buscar Rol',
				    	handler: function(){
				    		recargaGridCreaRoles();
				    	}
				    }]
		          	},gridCreaRoles],
		          bodyStyle:'padding:15px;',
		          buttons:[
		           {
		                 text: 'Aceptar',
		                 icon:'${ctx}/resources/fam3icons/icons/accept.png',
		                 handler: function() {
		                	 windowCrearRoles.close();
		                 }
		           }
		          ]
		          });
				windowCrearRoles.show();
				
				recargaGridCreaRoles = function(){
					
					var params = {
						'params.dsRol' : windowCrearRoles.down('[name=dsCreaEditaRol]').getValue(),
						'catalogo'     : _CAT_ROLES_SISTEMA
					};
					cargaStorePaginadoLocal(creaRolesStore, _URL_CARGA_CATALOGO, 'lista', params, function (options, success, response){
			    		if(success){
			                var jsonResponse = Ext.decode(response.responseText);
			                
			                if(!jsonResponse.success) {
			                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
			                }
			            }else{
			                showMessage('Error', 'Error al obtener los datos.', Ext.Msg.OK, Ext.Msg.ERROR);
			            }
			    	}, gridCreaRoles);
				};
				
				function crearEditarRol(record){

					var panelCrearEditarRol = Ext.create('Ext.form.Panel',{
				        border  : false
				        ,bodyStyle:'padding:5px;'
				        ,url: _UrlCrearEditarRol
				        ,items :
				        [   {
								xtype      : 'hidden',
								name       : 'params.pv_accion_i',
								value      : record? 'U' : 'I'
							},{
						        xtype      : 'textfield'
						    	,fieldLabel : 'C&oacute;digo del Rol'
					    		,labelWidth: 120
					    		,width: 350
					    		,allowBlank:false
					    		,readOnly: record?true:false
						    	,name       : 'params.pv_cdsisrol_i'
						    	,value: record? record.get('key') : ''
				    		},{
						        xtype      : 'textfield'
							   	,fieldLabel : 'Nombre del Rol'
						    	,labelWidth: 120
						    	,width: 350
						    	,allowBlank:false
							   	,name       : 'params.pv_dssisrol_i'
							   	,value: record? record.get('value') : ''
							
					    	}
				        ]
				    });
					
					 var windowCrearEditarRol = Ext.create('Ext.window.Window', {
				          title: record?'Editar Rol':'Crear Rol',
				          closeAction: 'hide',
				          modal:true,
				          items:[panelCrearEditarRol],
				          bodyStyle:'padding:15px;',
				          buttons:[{
				                 text: 'Guardar',
				                 icon: '${ctx}/resources/fam3icons/icons/accept.png',
				                 handler: function() {
				                       if (panelCrearEditarRol.form.isValid()) {
				                    	   
				                    	   panelCrearEditarRol.form.submit({
				           		        	waitMsg:'Procesando...',			
				           		        	failure: function(form, action) {
				           		        		mensajeError("Error al guardar el Rol");
				           					},
				           					success: function(form, action) {
				           						
				           						recargaGridCreaRoles();
				           						panelCrearEditarRol.getForm().reset();
				           						windowCrearEditarRol.close();
				                               	mensajeCorrecto("Aviso","Se ha guardado el Rol.");
				           						
				           						
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
				                 icon: '${ctx}/resources/fam3icons/icons/cancel.png',
				                 handler: function() {
				                	 panelCrearEditarRol.getForm().reset();
				                	 windowCrearEditarRol.close();
				                 }
				           }
				          ]
				          });
					 windowCrearEditarRol.show();
				}
		
	}

});
</script>

</head>
<body>
<div id="mainDiv" style="height:600px;"></div>
</body>
</html>