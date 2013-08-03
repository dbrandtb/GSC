function agregar() {
	var users_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_USUARIOS}),
						reader: new Ext.data.JsonReader({
								root: 'confAlertasAutoUsuarios',
								id: 'cdUsuario',
								successProperty: '@success'
							}, [
								{name: 'cdUsuario', type: 'string', mapping: 'cdUsuario'},
								{name: 'dsUsuario', type: 'string', mapping: 'dsUsuario'} 
							])
				});

	/******************* Falta definir cuál es el SP asociado a los clientes ******************************/
	var cli_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_CLIENTES_CORP}),
						reader: new Ext.data.JsonReader({
								root: 'clientesCorp',
								id: 'cdPerson',
								successProperty: '@success'
							}, [
								{name: 'cdPerson', type: 'string', mapping: 'cdPerson'},
								{name: 'dsElemen', type: 'string', mapping: 'dsElemen'},
								{name: 'cdElemento', type: 'string', mapping: 'cdElemento'} 
							])
				});
	/******************* Falta definir cuál es el SP asociado a los clientes ******************************/

	var proc_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_PROCESOS}),
						reader: new Ext.data.JsonReader({
								root: 'confAlertasAutoProcesos',
								id: 'otClave',
								successProperty: '@success'
							}, [
								{name: 'otClave', type: 'string', mapping: 'otClave'},
								{name: 'otValor', type: 'string', mapping: 'otValor'} 
							])
				});
	var temp_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_TEMPORALIDAD}),
						reader: new Ext.data.JsonReader({
								root: 'confAlertasAutoTemporalidad',
								id: 'otClave',
								successProperty: '@success'
							}, [
								{name: 'otClave', type: 'string', mapping: 'otClave'},
								{name: 'otValor', type: 'string', mapping: 'otValor'} 
							])
				});


/*
	var poliza_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_POLIZAS}),
						reader: new Ext.data.JsonReader({
								root: 'polizasPorAseguradora',
								id: 'nmPoliza',
								successProperty: '@success'
							}, [
								{name: 'nmPoliza', type: 'string', mapping: 'nmPoliza'},
								{name: 'nmPoliex', type: 'string', mapping: 'nmPoliex'}
							])
				});
				
*/

   var poliza_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_POLIZAS2}),
						reader: new Ext.data.JsonReader({
								root: 'polizasPorAseguradora',
								id: 'nmPoliza',
								successProperty: '@success'
							}, [
								{name: 'nmPoliza', type: 'string', mapping: 'nmPoliza'},
								{name: 'nmPoliex', type: 'string', mapping: 'nmPoliex'}
							])
				});
				
				
	var recibo_store = new Ext.data.Store({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_RECIBOS}),
						reader: new Ext.data.JsonReader({
								//root: 'confAlertasAutoRol',
								root: 'reciboPorPolizaPorAseguradora',
								id: 'cdRol',
								successProperty: '@success'
							}, [
								{name: 'nmRecibo', type: 'string', mapping: 'nmRecibo'},
								{name: 'cdPoliza', type: 'string', mapping: 'cdPoliza'} 
							])
				}); 
	var aseg_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ASEGURADORA}),
						reader: new Ext.data.JsonReader({
								root: 'aseguradoraComboBox',
								id: 'cdUniEco',
								successProperty: '@success'
							}, [
								{name: 'cdUniEco', type: 'string', mapping: 'cdUniEco'},
								{name: 'dsUniEco', type: 'string', mapping: 'dsUniEco'} 
							])
				});
	var recorda_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_FRECUENCIAS}),
						reader: new Ext.data.JsonReader({
								root: 'frecuencias',
								id: 'id',
								successProperty: '@success'
							}, [
								{name: 'id', type: 'string', mapping: 'codigo'},
								{name: 'texto', type: 'string', mapping: 'descripcion'} 
							])
				});


		    var root = new Ext.tree.TreeNode({
		                text: 'elementosTreeView', 
		                draggable:false, 
		                id:'elementosTreeView'
		            });

			var arbol = new Ext.tree.TreePanel ({
              title:'Variables',
                      autoScroll:true,
                      border:false,
                      iconCls:'nav',
              split:true,
              width: 200,
              minSize: 175,
              maxSize: 400,
              collapsible: true,
              margins:'0 0 0 0',
              loader: new Ext.tree.TreeLoader(),
              root:root,
              rootVisible: false
			});


	var form_nuevo = new Ext.FormPanel ({
        url : _ACTION_GUARDAR_ALERTA,
        frame : true,
        bodyStyle: {background: 'white', padding:'0px 0px 0px'},
        width : 700,
        autoHeight: true,
        defaults: {labelWidth: 90},
        labelAlign:'right',
        layout: 'column',
        layoutConfig: {columns: 1, align: 'right'},
        items: [
        		{
        			layout: 'form',
        			width: 700,
        			
        			items: [
        						{xtype: 'hidden', id: 'cdIdUnico', name: 'cdIdUnico', id: 'cdIdUnico'},
     							{xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{cdUsuario}. {dsUsuario}" class="x-combo-list-item">{dsUsuario}</div></tpl>',
	                            store: users_store, displayField:'dsUsuario', valueField:'cdUsuario', hiddenName: 'codUsuario', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', width: 200,
	                            emptyText:'Seleccione Usuario...',forceSelection:true,
	                            
	                            selectOnFocus:true,
	                            fieldLabel: getLabelFromMap('cmbAddAlertUsuarioId',helpMap,'Usuario'),
	                            allowBlank : false , 
	                            tooltip:getToolTipFromMap('cmbAddAlertUsuarioId',helpMap,'Usuario'),
	                            onSelect: function (record) {
	                            			this.setValue(record.get('cdUsuario'));
	                            			this.collapse();
	                            			cli_store.load({
	                            				params: {cdUsuario: record.get('cdUsuario')},
	                            				callback: function () {
	                            				}
	                            			});
	                            }
	                            },

      							{xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
	                            store: cli_store, displayField:'dsElemen', valueField:'cdElemento', hiddenName: 'codCliente', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', width: 200,
	                            allowBlank: false,
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                            emptyText:'Seleccione Clientes...',forceSelection:true,
	                            selectOnFocus:true,
	                            fieldLabel: getLabelFromMap('cmbAddAlertClienteId',helpMap,'Cliente'),
	                            tooltip:getToolTipFromMap('cmbAddAlertClienteId',helpMap,'Cliente'),
	                            onSelect: function(record) {
	                            			this.setValue(record.get('cdElemento'));
	                            			this.collapse();
	                            			aseg_store.load({
	                            				params: {cdElemento: record.get('cdElemento')}
	                            			});
	                            }
	                            },

      							{xtype: 'combo',labelWidth: 70, tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                            store: aseg_store, displayField:'dsUniEco', valueField:'cdUniEco', hiddenName: 'codAseguradora', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', width: 200, emptyText:'Seleccione Aseguradora...',forceSelection:true,
	                            selectOnFocus:true, loadMask: true,
	                            fieldLabel: getLabelFromMap('cmbAddAlertAsegId',helpMap,'Aseguradora'),
	                            allowBlank : false ,
	                            tooltip:getToolTipFromMap('cmbAddAlertAsegId',helpMap,'Aseguradora'),
	                            
	                            onSelect: function (record) {
	                            				this.setValue(record.get('cdUniEco'));
	                            				this.collapse();
	                            				poliza_store.load({
	                            						params: {
	                            						   cdunieco: record.get('cdUniEco'),
	                            						   cdElemento: form_nuevo.form.findField('codCliente').getValue()
	                            						},
	                            						waitMsg: 'Espere por favor....'
	                            					});
	                            		}
	                            },

      							{xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{nmPoliza}. {nmPoliex}" class="x-combo-list-item">&nbsp;{nmPoliex}</div></tpl>',
	                            store: poliza_store, displayField:'nmPoliex', valueField:'nmPoliza', hiddenName: 'codPoliza', typeAhead: true,
	                            mode: 'local', triggerAction: 'all',  width: 200, emptyText:'Seleccione Poliza...',forceSelection:true,
	                            selectOnFocus:true,
	                            fieldLabel: getLabelFromMap('cmbAddAlertPolId',helpMap,'P&oacute;liza'),
	                            allowBlank : false ,
	                            tooltip:getToolTipFromMap('cmbAddAlertPolId',helpMap,'P&oacute;liza'),
	                            onSelect: function (record) {
	                            				this.setValue(record.get('nmPoliza'));
	                            				this.collapse();
	                            				
	                            				
	                            				recibo_store.load({
	                            						params: {
	                            								cdunieco: form_nuevo.form.findField('codAseguradora').getValue(),
	                            								cdRamo: form_nuevo.form.findField('codCliente').getValue(),
	                            								nmPoliza: record.get('nmPoliza')
	                            						},
	                            						waitMsg: 'Espere por favor...'
	                            				});
	                            }},

      							{xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{nmRecibo}. {nmRecibo}" class="x-combo-list-item">{nmRecibo}</div></tpl>',
	                            store: recibo_store, displayField:'nmRecibo', valueField:'nmRecibo', hiddenName: 'codRecibo', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', width: 200, emptyText:'Seleccione Recibo...',forceSelection:true,
	                            selectOnFocus:true,
	                            fieldLabel: getLabelFromMap('cmbAddAlertReciboId',helpMap,'Recibo'),
	                            tooltip:getToolTipFromMap('cmbAddAlertReciboId',helpMap,'Recibo')},

      							{xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{otClave}. {otValor}" class="x-combo-list-item">{otValor}</div></tpl>',
	                            store: proc_store, displayField:'otValor', valueField:'otClave', hiddenName: 'codProceso', typeAhead: true,forceSelection:true,
	                            mode: 'local', triggerAction: 'all', width: 200,
	                            allowBlank: false,
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                            emptyText:'Seleccione Proceso...',
	                            selectOnFocus:true, 
	                            fieldLabel: getLabelFromMap('cmbAddAlertProcId',helpMap,'Proceso'),
	                            tooltip:getToolTipFromMap('cmbAddAlertProcId',helpMap,'Proceso')},

      							/*{xtype: 'combo', labelWidth: 80, tpl: '<tpl for="."><div ext:qtip="{otValor}. {otClave}" class="x-combo-list-item">{otValor}</div></tpl>',
	                            store: temp_store, displayField:'otValor', valueField:'otClave', hiddenName: 'cdTemporalidad', typeAhead: true,forceSelection:true,
	                            mode: 'local',
	                            allowBlank: false,
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                            triggerAction: 'all', width: 200, emptyText:'Seleccione Temporalidad...',
	                            selectOnFocus:true,fieldLabel: getLabelFromMap('cmbAddAlertTempId',helpMap,'Temporalidad'),tooltip:getToolTipFromMap('cmbAddAlertTempId',helpMap)},*/

	                            {xtype: 'datefield', name: 'feUltimoEnvio', format: 'd/m/Y',
	                            allowBlank: false,
								altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                            fieldLabel: getLabelFromMap('txtAddAlertUltEId',helpMap,'Ult. Envio'),
	                            tooltip:getToolTipFromMap('txtAddAlertUltEId',helpMap,'Ultimo envio')},
	                            {xtype: 'datefield',name: 'feSiguienteEnvio', format: 'd/m/Y',
	                            allowBlank: false,
								altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                            fieldLabel: getLabelFromMap('txtAddAlertSigEId',helpMap,'Sig. Envio'),
	                            tooltip:getToolTipFromMap('txtAddAlertSigEId',helpMap,'Siguiente envio')},
	                            
	                            {xtype: 'datefield', name: 'feVencimiento', format: 'd/m/Y',
	                            allowBlank: false,
								altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                            fieldLabel: getLabelFromMap('txtAddAlertVtoEId',helpMap,'Vencimiento'),
	                            tooltip:getToolTipFromMap('txtAddAlertVtoEId',helpMap,'Vencimiento')},

      							{xtype: 'textfield',name: 'correo', id: 'correo', vtype: 'email',
      							fieldLabel: getLabelFromMap('txtAddAlertCorreoId',helpMap,'Correo'),
      							tooltip:getToolTipFromMap('txtAddAlertCorreoId',helpMap,'Correo')},

								{
									layout: 'column',
									layoutConfig: {columns: 2, labelAlign: 'right', padding: '0px 0px 0px 0px'},
									bodyStyle: {padding: '0px 0px 0px 0px'},
				        			labelWidth: 0,
				        			labelSeparator: '',
									columnWidth: 1.0,
									items: [
											{
												layout: 'form',
							        			labelWidth: 0,
												bodyStyle: {padding: '0px 0px 0px 0px'},
							        			labelSeparator: '',
												items: [
						        						{
						        							//xtype: 'panel',
						        							id: 'accordion1',
						        							//renderTo: 'accordion-div',
													        //title: 'MENU',
													        split:true,
													        width:140,
													        height: 150,
													        minSize:'200',
													        maxSize:'200',
													        margins:'0 2 0 2',
													        bodyStyle:'padding:0px',
													        //frame: true,
													        //collapsible: true,
													        layout:'accordion',
													        layoutConfig:{
													             animate:true
													        },
													        items: [arbol]
						        						}
												]
											},
											{
												layout: 'form',
							        			labelWidth: 0,
							        			labelSeparator: '',
							        			labelWidth: 0,
							        			labelSeparator: '',
												bodyStyle: {padding: '0px 0px 0px 0px'},
							        			items: [{
														xtype: 'htmleditor',
														//fieldLabel: getLabelFromMap('edAlerTfMsg',helpMap,'Mensaje'),
						      							tooltip: getToolTipFromMap('edAlerTfMsg',helpMap,'Mensaje'),
						      							width:520,height:150,
						      							allowBlank: false,
									        			labelSeparator: '',
														blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
						      							tooltip: getToolTipFromMap('edAlerTfMsg',helpMap,'Este campo es requerido'),
						      							name: 'mensaje', id: 'mensaje'
							        			}]
      										}
									]
								},
      							/*{xtype: 'htmleditor', name: 'mensaje',
      							allowBlank: false,
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
      							id: 'mensaje',
      							width:550,height:100,
      							fieldLabel: getLabelFromMap('txtAddAlertMensajeId',helpMap,'Mensaje'),
      							tooltip:getToolTipFromMap('txtAddAlertMensajeId',helpMap,'Mensaje')},*/

	                            {xtype: 'checkbox', name: 'popUp', id: 'popUp', labelWidth: 80,
	                            fieldLabel: getLabelFromMap('cbAddAlertPppId',helpMap,'Popup'),
	                            tooltip:getToolTipFromMap('cbAddAlertPppId',helpMap,'Popup')},
	                            {xtype: 'checkbox', name: 'pantalla', id: 'pantalla', labelWidth: 80,
	                            fieldLabel: getLabelFromMap('txtAddAlertPppId',helpMap,'Pantalla'),
	                            tooltip:getToolTipFromMap('txtAddAlertPppId',helpMap,'Pantalla')},	                            

      							{xtype: 'combo', labelWidth: 80, tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
	                            store: recorda_store, displayField:'texto', valueField:'id', hiddenName: 'codFrecuencia', typeAhead: true,forceSelection:true,
	                            mode: 'local', triggerAction: 'all', width: 200, emptyText:'Seleccione...',
	                            selectOnFocus:true,labelWidth: 80,
	                            allowBlank: false,
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                            fieldLabel: getLabelFromMap('cmbAddAlertRecEnId',helpMap,'Recordar en'),
	                            tooltip:getToolTipFromMap('cmbAddAlertRecEnId',helpMap,'Recordar en')}
        					]
        		}        	]	
    });

	Ext.Ajax.request ({
		url: 'configuracionAlertasAutomatico/buscarVariables.action',
		method: 'POST',
		success: function (result, request) {
				var listaElementos = Ext.util.JSON.decode(result.responseText).elementosTreeView;
				for (var i=0; i<listaElementos.length; i++) {
					root.appendChild(arbol.getLoader().createNode(listaElementos[i]));
				}
		}
	});

		arbol.on('dblclick', function (nodo, evt) {
			//Ext.getCmp('mensaje').setValue(Ext.getCmp('mensaje').getValue() + nodo.text);
			Ext.getCmp('mensaje').insertAtCursor(nodo.text);
		});


	var window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('73',helpMap,'Agregar Alertas')+'</span>',
			width: 700,
			modal:true,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: [form_nuevo],
            //se definen los botones del formulario
            buttons : [ {
				
                text : getLabelFromMap('windowAddAlertId',helpMap,'Guardar'),
                tooltip : getToolTipFromMap('windowAddAlertId',helpMap,'Guardar alertas'),
                

                disabled : false,

                handler : function() {

                    if (form_nuevo.form.isValid()) {

                        form_nuevo.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_ALERTA,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Guardado satisfactoriamente',function(){reloadGrid();});
                                window.close();         
                                reloadGrid();                            
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Guardar: ' + action.result.errorMessages[0]);
                           },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                        });

                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }

                }

            }, {

                text : getLabelFromMap('windowCancelAlertId',helpMap,'Cancelar'),
                tooltip : getToolTipFromMap('windowCancelAlertId',helpMap,'Cancelar alertas'),

                handler : function() {
                    window.close();
                }

            }]

	});

	window.show();
	recorda_store.load({
	    params: {
	    			cdIdioma: '1',
	    			cdRegion: '1'
	    		 },
		callback:function(){
			proc_store.load({
				callback:function(){
					temp_store.load({
						callback:function(){
							users_store.load();
							/*cli_store.load({
								callback:function(){
									}
								});*/
							}
						});
					}
				});
				}
			});
}