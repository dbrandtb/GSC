<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">

var _CONTEXT = '${ctx}';

var _parametros = <s:property value='%{convertToJSON("params")}' escapeHtml="false" />;
debug('parametrosTApoyo:',_parametros);
var editMode = (_parametros.edit == 'S');

var _URL_GuardaTablaApoyo = '<s:url namespace="/catalogos" action="guardaTablaApoyo" />';
var _URL_CatalogoClnatura = '<s:url namespace="/tablaCincoClaves" action="CatalogoClnatura" />';

/**
 * Para combos de dependencias para Una Clave: 
 */
var _URL_Catalogo1     = '<s:url namespace="/atributosVariables" action="CargaCatalogos" />';
var _URL_Catalogo2     = '<s:url namespace="/atributosVariables" action="CargaCatalogos2" />';
var _URL_CatalogoClave = '<s:url namespace="/atributosVariables" action="CargaClaveDependcia" />';

Ext.onReady(function() {

	var panelTablaApoyo = Ext.create('Ext.form.Panel', {
    	url: _URL_GuardaTablaApoyo,
		border: false,
		defaults: {
			style : 'margin:5px;'
		},
	    renderTo : 'maindivAgrTbl',
	    items    : [
					{
						xtype      : 'hidden',
						name       : 'params.pi_tip_tran',
						value      : editMode? '2' : '1'  // 1 INSERT, 2 UPDATE
					},{
			            layout: 'column',
            			columns: 3,
            			border: false,
            			defaults: {
							style : 'margin:5px;'
						},
            			items: [{
									xtype      : 'textfield',
									name       : 'params.',
									fieldLabel : 'N&uacute;mero de Tabla',
									value      : _parametros.nmtabla,
									readOnly      : true
								},{
						        	xtype      : 'textfield',
						    		name       : 'params.',
						    		fieldLabel : 'C&oacute;digo de la Tabla',
						    		regex      : /^[a-zA-Z0-9]+$/,
									regexText  : 'El nombre solo puede contener letras y n&uacute;meros',
									maxLength  : 30,
									maxLengthText: 'Longitud m&aacute;xima de 30 caracteres',
						    		value      : _parametros.cdtabla,
						    		readOnly      : editMode,
						    		allowBlank    : false
						        },{
			                        xtype         : 'combo',
			                        name          : 'params.',
			                        fieldLabel    : 'Naturaleza',
			                        readOnly      : editMode,
						    		allowBlank    : false,
			                        valueField    : 'key',
			                        displayField  : 'value',
			                        forceSelection: true,
			                        queryMode     :'local',
			                        store         : Ext.create('Ext.data.Store', {
				                        model     : 'Generic',
				                        autoLoad  : true,
				                        proxy     : {
				                            type        : 'ajax'
				                            ,url        : _URL_CatalogoClnatura
				                            ,reader     :
				                            {
				                                type  : 'json'
				                                ,root : 'listaClnatura'
				                            }
				                        },
				                        listeners: {
				                        	load: function (){
				                        		if(editMode){
				                        			panelTablaApoyo.getForm().findField('params.cdsisrol').setValue(_parametros.cdsisrol);
				                        			
				                        		   var fieldUnCla = panelTablaApoyo.down('#fieldUnaClave');
				                         		   if(ROL_AGENTE != _parametros.cdsisrol){
				                         			   panelTablaApoyo.getForm().findField('params.cdusuari').setFieldLabel('Id Usuario');
				                         			   panelTablaApoyo.getForm().findField('params.cdusuari').maxLength = 30;
				                         			   panelTablaApoyo.getForm().findField('params.cdusuari').regex = /^[a-zA-Z0-9]+$/;
				                         			   panelTablaApoyo.getForm().findField('params.cdusuari').regexText = 'La clave del Usuario solo puede contener letras y n&uacute;meros';
				                         			   
				                         			   fieldUnCla.hide();
				                         			   panelTablaApoyo.getForm().findField('params.feini').allowBlank = true;
				                         			   panelTablaApoyo.getForm().findField('params.fefin').allowBlank = true;
				                         			   panelTablaApoyo.getForm().findField('params.dsapellido').allowBlank = false;
				                         			   panelTablaApoyo.getForm().findField('params.dsapellido1').allowBlank = false;
				                         			   panelTablaApoyo.getForm().findField('params.cdrfc').allowBlank = true;
				                         		   }else {
				                         			   panelTablaApoyo.getForm().findField('params.cdusuari').setFieldLabel('Id Agente');
				                         			   panelTablaApoyo.getForm().findField('params.cdusuari').maxLength = 15;
				                         			   panelTablaApoyo.getForm().findField('params.cdusuari').regex = /^A[0-9]+$/;
				                         			   panelTablaApoyo.getForm().findField('params.cdusuari').regexText = 'La clave del Agente debe de comenzar con A y seguir de cualquier n&uacute;mero';
				                         			   
				                         			   fieldUnCla.show();
				                         			   panelTablaApoyo.getForm().findField('params.feini').allowBlank = false;
				                         			   panelTablaApoyo.getForm().findField('params.fefin').allowBlank = false;
				                         			   panelTablaApoyo.getForm().findField('params.dsapellido').allowBlank = true;
				                         			   panelTablaApoyo.getForm().findField('params.dsapellido1').allowBlank = true;
				                         			   panelTablaApoyo.getForm().findField('params.cdrfc').allowBlank = false;
				                         		   }
				                        		}
				                        	}
				                        }
			                        }),
			                       listeners: {
			                    	   select: function ( combo, records, eOpts ){
			                    		   var cdnatura = records[0].get('key');
			                    		   var fieldUnCla = panelTablaApoyo.down('#fieldUnaClave');
			                    		   var combTipAcc = panelTablaApoyo.down('#tipoAccId');
			                    		   if(1 != cdnatura){
			                    		   		fieldUnCla.hide();
			                    		   		combTipAcc.select('T');
			                    		   		combTipAcc.setReadOnly(false);
			                    		   }else {
			                    			   	fieldUnCla.show();
			                    			   	combTipAcc.select('U');
			                    			   	combTipAcc.setReadOnly(true);
			                    		   }
			                    	   }
			                       }
						        },{
						        	xtype      : 'textfield',
						    		name       : 'params.',
						    		allowBlank : false,
						    		fieldLabel : 'Descripci&oacute;n de la Tabla',
						    		width      : 545,
						    		labelWidth : 130,
						    		maxLength  : 60,
									maxLengthText: 'Longitud m&aacute;xima de 60 caracteres',
						    		value      : _parametros.dstabla
						        },{
						            xtype         : 'combobox',
						            name          : 'params.',
						            fieldLabel    : 'Tipo Acceso',
						            itemId        : 'tipoAccId',
						            valueField    : 'key',
						            displayField  : 'value',
						            forceSelection: true,
						            queryMode     : 'local',
						            typeAhead     : true,
						            anyMatch      : true,
						            readOnly      : editMode,
						    		allowBlank    : false,
						            store         :  Ext.create('Ext.data.Store', {
												     model: 'Generic',
												     data : [
												         {key: 'U', value: 'Único'},
												         {key: 'T', value: 'Por Tramos'}
												     ]
												 })
				        		}]
					},{
						xtype      : 'fieldset',
						itemId     : 'fieldUnaClave',
						title      : 'Dependencias para Lista de Valores',
						collapsible: true,
            			defaults: {
							style : 'margin:5px;'
						},
						items      : [{
									layout: 'column',
			            			columns: 3,
			            			border: false,
			            			defaults: {
										style : 'margin:5px;'
									},
			            			items: [{
								        	xtype       : 'combo',
								        	name        : 'params.',
								        	fieldLabel  : 'Cat&aacute;logo 1',
								        	width       : 290,
								        	labelWidth  : 55,
								        	hidden      : editMode,
								        	valueField  : 'cdCatalogo1',
											displayField: 'dsCatalogo1',
											forceSelection: true,
					                        queryMode   :'local',
					                        typeAhead     : true,
						            		anyMatch      : true,
											store       : Ext.create('Ext.data.Store', {
												model   : Ext.define('Modelo1', {extend: 'Ext.data.Model',fields: [{type: 'string', name: 'cdCatalogo1'},{type: 'string', name: 'dsCatalogo1'}]}),
												autoLoad : true,
												proxy : {
													type : 'ajax',
													url : _URL_Catalogo1,
													reader : {
														type : 'json',
														root : 'listaDeValores'
													}
												},
						                        listeners: {
						                        	load: function (){
						                        		if(editMode){
						                        			panelTablaApoyo.getForm().findField('params.cdramo').setValue(_parametros.cdramo);
						                        		}
						                        	}
						                        }
											})
								        },{
								        	xtype       : 'combo',
								        	name        : 'params.',
								        	fieldLabel  : 'Cat&aacute;logo 2',
								        	width       : 290,
								        	labelWidth  : 55,
								        	hidden      : editMode,
								        	valueField  : 'cdCatalogo1',
											displayField: 'dsCatalogo1',
											forceSelection: true,
					                        queryMode   :'local',
					                        typeAhead     : true,
						            		anyMatch      : true,
											store       : Ext.create('Ext.data.Store', {
												model   : Ext.define('Modelo2', {extend: 'Ext.data.Model',fields: [{type: 'string', name: 'cdCatalogo1'},{type: 'string', name: 'dsCatalogo1'}]}),
												autoLoad : true,
												proxy : {
													type : 'ajax',
													url : _URL_Catalogo2,
													reader : {
														type : 'json',
														root : 'listaDeValores2'
													}
												},
						                        listeners: {
						                        	load: function (){
						                        		if(editMode){
						                        			panelTablaApoyo.getForm().findField('params.cdramo').setValue(_parametros.cdramo);
						                        		}
						                        	}
						                        }
											})
								        },{
								        	xtype       : 'combo',
								        	name        : 'params.',
								        	fieldLabel  : 'Clave',
								        	labelWidth  : 35,
								        	hidden      : editMode,
								        	valueField  : 'key',
											displayField: 'value',
											forceSelection: true,
					                        queryMode   :'local',
					                        typeAhead     : true,
						            		anyMatch      : true,
											store       : Ext.create('Ext.data.Store', {
												model : 'Generic',
												autoLoad : true,
												proxy : {
													type : 'ajax',
													url : _URL_CatalogoClave,
													reader : {
														type : 'json',
														root : 'listaClavesDependencia'
													}
												},
						                        listeners: {
						                        	load: function (){
						                        		if(editMode){
						                        			panelTablaApoyo.getForm().findField('params.cdramo').setValue(_parametros.cdramo);
						                        		}
						                        	}
						                        }
											})
								        }]
									}]
					}
        ],
        buttonAlign: 'center',
	    buttons: [{
        	text: 'Guardar',
        	icon    : _CONTEXT+'/resources/fam3icons/icons/disk.png',
        	handler: function(btn, e) {
        		var form = this.up('form').getForm();
        		
        		if (form.isValid()) {
        			
        			var msjeConfirmaGuardadoUsuario;
        			if(editMode){
        				msjeConfirmaGuardadoUsuario = '&iquest;Esta seguro que desea actualizar este usuario?';
        			}else{
        				msjeConfirmaGuardadoUsuario = '&iquest;Esta seguro que desea crear este usuario?';
        			}
        			
        			Ext.Msg.show({
    		            title: 'Confirmar acci&oacute;n',
    		            msg: msjeConfirmaGuardadoUsuario,
    		            buttons: Ext.Msg.YESNO,
    		            fn: function(buttonId, text, opt) {
    		            	if(buttonId == 'yes') {
    		            		form.submit({
			    		        	waitMsg:'Procesando...',			        	
			    		        	failure: function(form, action) {
			    		        		switch (action.failureType) {
				    	                    case Ext.form.action.Action.CONNECT_FAILURE:
				    	                	    Ext.Msg.show({title: 'Error', msg: 'Error de comunicaci&oacute;n', buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
				    	                        break;
				    	                    case Ext.form.action.Action.SERVER_INVALID:
				    	                    case Ext.form.action.Action.LOAD_FAILURE:
				    	                    	 var msgServer = Ext.isEmpty(action.result.errorMessage) ? 'Error interno del servidor, verifique su sesi&oacute;n' : action.result.errorMessage;
				    	                         Ext.Msg.show({title: 'Error', msg: msgServer, buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
				    	                        break;
				    	                }
			    					},
			    					success: function(form, action) {
			    						recargaGridUsuarios();
			    						windowLoader.close();
			    						mensajeCorrecto('\u00C9xito', 'El usuario se guard\u00F3 correctamente', Ext.Msg.OK, Ext.Msg.INFO);
			    						form.reset();
			    					}
			    				});
    		            	}
            			},
    		            animateTarget: btn,
    		            icon: Ext.Msg.QUESTION
        			});
    			} else {
    				Ext.Msg.show({
    					title: 'Aviso',
    		            msg: 'Complete la informaci&oacute;n requerida',
    		            buttons: Ext.Msg.OK,
    		            animateTarget: btn,
    		            icon: Ext.Msg.WARNING
    				});
    			}
        	}
        },
        {
            	text: 'Cancelar',
            	icon    : _CONTEXT+'/resources/fam3icons/icons/cancel.png',
            	handler: function(btn, e) {
            		windowLoader.close();
            	}
        }]
    });
	
    var tmField = panelTablaApoyo.down('#fieldUnaClave');
    tmField.hide();
    
});


</script>

<div id="maindivAgrTbl" style="height:600px;"></div>