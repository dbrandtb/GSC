function editar(record) {
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

	var poliza_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_POLIZAS}),
						reader: new Ext.data.JsonReader({
								root: 'polizasPorAseguradora',
								id: 'nmPoliza',
								successProperty: '@success'
							}, [
								{name: 'nmPoliza', type: 'string', mapping: 'nmPoliza'}
							])
				});
	var recibo_store = new Ext.data.Store({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_RECIBOS}),
						reader: new Ext.data.JsonReader({
								root: 'confAlertasAutoRol',
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

	var alerta_reg = new Ext.data.JsonReader({
						root: 'listaAlerta',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
							{name: 'cdIdUnico', type: 'string', mapping: 'cdIdUnico'},
							{name: 'codCliente', type: 'string', mapping: 'cdElemento'},
							{name: 'dsCliente', type: 'string', mapping: 'dsNombre'},				
							{name: 'codProceso', type: 'string', mapping: 'cdProceso'},
							{name: 'dsProceso', type: 'string', mapping: 'dsProceso'},
							{name: 'codUsuario', type: 'string', mapping: 'cdUsuario'},
							{name: 'codAseguradora', type: 'string', mapping: 'cdUniEco'},
							{name: 'dsAseguradora', type: 'string', mapping: 'dsUniEco'},
							{name: 'cdProducto', type: 'string', mapping: 'cdProducto'},
							{name: 'dsProducto', type: 'string', mapping: 'dsProducto'},

							{name: 'codPoliza', type: 'string', mapping: 'nmPoliza'},							
							{name: 'nmPoliza', type: 'string', mapping: 'nmPoliza'},							
							{name: 'nmRecibo', type: 'string', mapping: 'nmRecibo'},
							{name: 'codRecibo', type: 'string', mapping: 'nmRecibo'},							
							{name: 'feUltimoEnvio', type: 'string', mapping: 'feUltimoEvento'},
							{name: 'feSiguienteEnvio', type: 'string', mapping: 'feSiguienteEnvio'},
							{name: 'codFrecuencia', type: 'string', mapping: 'nmFrecuencia'},
							{name: 'cdTemporalidad', type: 'string', mapping: 'cdTemporalidad'},
							{name: 'feVencimiento', type: 'string', mapping: 'feVencimiento'},
							{name: 'correo', type: 'string', mapping: 'dsCorreo'},	
							{name: 'mensaje', type: 'string', mapping: 'dsMensaje'},
							{name: 'popUp', type: 'string', mapping: 'fgMandaPantalla'},														
							{name: 'pantalla', type: 'string', mapping: 'fgPermPantalla'},
					
							{name: 'dsAlerta', type: 'string', mapping: 'dsAlerta'},
							{name: 'dsUsuario', type: 'string', mapping: 'dsUsuario'},
							{name: 'dsTemporalidad', type: 'string', mapping: 'dsTemporalidad'}
							
						]
	);
	var alerta_reg_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_CONF_ALERTAS_GET}),
						reader: alerta_reg
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


	var form_edit = new Ext.FormPanel ({
        url : _ACTION_CONF_ALERTAS_GET,
        frame : true,
        bodyStyle: {background: 'white', padding:'0px 0px 0px'},
        width : 680,
        autoHeight: true,
        reader: alerta_reg,
        successProperty: 'success',
        items: [
        						new Ext.form.Hidden ({name: 'codigoConfAlerta', value: record.get('codigoConfAlerta')}),
        						new Ext.form.Hidden ({name: 'cdIdUnico'}),
        						new Ext.form.Hidden ({name: 'codUsuario'}),
        						new Ext.form.Hidden ({name: 'codProceso'}),
        						new Ext.form.Hidden ({name: 'cdTemporalidad'}),
        						new Ext.form.TextField({fieldLabel: getLabelFromMap('edAlerTfUsuario',helpMap,'Usuario'),
        						allowBlank: false,
        						
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
        						tooltip: getToolTipFromMap('edAlerTfUsuario',helpMap), name: 'dsUsuario', disabled: true}),
								
								new Ext.form.Hidden({name: 'codCliente'}),
								new Ext.form.TextField({
									fieldLabel: getLabelFromMap('edAlerTfClie',helpMap,'Cliente'),
									tooltip: getToolTipFromMap('edAlerTfClie',helpMap), name: 'dsCliente',
									allowBlank: false,
									blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
									disabled: true}),
								

      							new Ext.form.Hidden({name: 'codAseguradora'}),
      							new Ext.form.TextField({fieldLabel: getLabelFromMap('edAlerTfAseg',helpMap,'Aseguradora'),
      							tooltip: getToolTipFromMap('edAlerTfAseg',helpMap,'Aseguradora'),
      							name: 'dsAseguradora', disabled: true}),      							

      							new Ext.form.Hidden ({name: 'codPoliza'}),
      							new Ext.form.TextField({fieldLabel: getLabelFromMap('edAlerTfPoli',helpMap,'P&oacute;liza'),
      							tooltip: getToolTipFromMap('edAlerTfPoli',helpMap,'P&oacute;liza'),
      							name: 'nmPoliza', disabled: true}),

								new Ext.form.Hidden({name: 'codRecibo'}),
								new Ext.form.TextField({fieldLabel: getLabelFromMap('edAlerTfReci',helpMap,'Recibo'),
								tooltip: getToolTipFromMap('edAlerTfReci',helpMap,'Recibo'), 
								name: 'nmRecibo', disabled: true}),
								new Ext.form.TextField({fieldLabel: getLabelFromMap('desTemporalidad',helpMap,'Temporalidad'),
								tooltip: getToolTipFromMap('desTemporalidad',helpMap,'Temporalidad'), 
								name: 'dsTemporalidad', disabled: true}),
      							/*new Ext.form.ComboBox({tpl: '<tpl for="."><div ext:qtip="{otValor}. {otClave}" class="x-combo-list-item">{otValor}</div></tpl>',
	                            store: temp_store, id: 'comboTempId', 
	                            displayField:'otValor', valueField:'otClave', hiddenName: 'cdTemporalidad', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', 
	                            allowBlank: false,
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                            fieldLabel: getLabelFromMap('edAlerCmbTemp',helpMap,'Temporalidad'),tooltip: getToolTipFromMap('edAlerCmbTemp',helpMap), width: 200, emptyText:'Seleccione Temporalidad...',
	                            selectOnFocus:true, forceSelection:true, name: 'dsTemporalidad', disabled: true}),*/
																
	                            new Ext.form.DateField({name: 'feUltimoEnvio',
	                            allowBlank: false,
	                            
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                            fieldLabel: getLabelFromMap('edAlerDfUltEnv',helpMap,'Ult. Envio'),
	                            tooltip: getToolTipFromMap('edAlerDfUltEnv',helpMap,'Ultimo envio'), 
	                            format: 'd/m/Y', altFormats : 'm/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g'}),
	                            
	                            new Ext.form.DateField({name: 'feSiguienteEnvio', allowBlank: false, 
	                            fieldLabel: getLabelFromMap('edAlerDfSigEnv',helpMap,'Sig. Envio'),
	                            tooltip: getToolTipFromMap('edAlerDfSigEnv',helpMap,'Siguiente envio'), 
	                            format: 'd/m/Y', altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g"}),
	                            new Ext.form.DateField({name: 'feVencimiento',
	                            allowBlank: false,
	                            
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                            fieldLabel: getLabelFromMap('edAlerDfVto',helpMap,'Vencimiento'),
	                            tooltip: getToolTipFromMap('edAlerDfVto',helpMap,'Vencimiento'),
	                            format: 'd/m/Y', altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g"}),

      							new Ext.form.TextField({fieldLabel: getLabelFromMap('edAlerTfMail',helpMap,'Correo'),
      							tooltip: getToolTipFromMap('edAlerTfMail',helpMap,'Correo'), 
      							name: 'correo', id: 'correo', vtype: 'email'}),

								{
									layout: 'column',
									layoutConfig: {columns: 2, labelAlign: 'right', padding: '0px 0px 0px 0px'},
									bodyStyle: {padding: '0px 0px 0px 0px'},
				        			labelWidth: 0,
				        			labelSeparator: '',
									items: [
											{
												layout: 'form',
												columnWidth: 1.0,
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
      							/*new Ext.form.HtmlEditor({fieldLabel: getLabelFromMap('edAlerTfMsg',helpMap,'Mensaje'),
      							tooltip: getToolTipFromMap('edAlerTfMsg',helpMap,'Mensaje'),
      							width:550,height:100,
      							allowBlank: false,
      							
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
      							tooltip: getToolTipFromMap('edAlerTfMsg',helpMap,'Este campo es requerido'),
      							name: 'mensaje', id: 'mensaje'}),*/

	                            new Ext.form.Checkbox({name: 'popUp', fieldLabel: getLabelFromMap('edAlerCbxPopup',helpMap,'Popup'),
	                            tooltip: getToolTipFromMap('edAlerCbxPopup',helpMap,'Popup'),
	                            labelWidth: 80}),
	                            new Ext.form.Checkbox({name: 'pantalla', fieldLabel: getLabelFromMap('edAlerCbxPan',helpMap,'Pantalla'),
	                            tooltip: getToolTipFromMap('edAlerCbxPan',helpMap,'Pantalla'),
	                            labelWidth: 80}),	                            

      							new Ext.form.ComboBox({tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
	                            store: recorda_store, displayField:'texto', valueField:'id', hiddenName: 'codFrecuencia', typeAhead: true,
	                            mode: 'local',
	                            allowBlank: false,
	                            
								blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                            triggerAction: 'all', fieldLabel: getLabelFromMap('edAlerCmbRec',helpMap,'Recordar en'),
	                            tooltip: getToolTipFromMap('edAlerCmbRec',helpMap,'Recordar en'), 
	                            width: 200, emptyText:'Seleccione...',
	                            selectOnFocus:true, forceSelection:true, name: 'codFrecuencia'})
	                            
        		
        		]	
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
			Ext.getCmp('mensaje').insertAtCursor(nodo.text);
		});


	var _window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('74',helpMap,'Editar Alertas')+'</span>',
			width: 700,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	modal:true,
        	buttonAlign:'center',
        	items: form_edit,
            //se definen los botones del formulario
            buttons : [ {

                text :getLabelFromMap('edAlerBtnSave',helpMap,'Guardar'),
                tooltip:getToolTipFromMap('edAlerBtnSave',helpMap,'Guardar edici&oacute;n alertas'),

                disabled : false,

                handler : function() {

                    if (form_edit.form.isValid()) {

                        form_edit.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_ALERTA,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Guardado satisfactoriamente',function(){reloadGrid();});
                                _window.close();                                     
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

                text :getLabelFromMap('edAlerBtnCancel',helpMap,'Cancelar'),
                tooltip:getToolTipFromMap('edAlerBtnCancel',helpMap,'Cancelar edici&oacute;n alertas'),

                handler : function() {
                    _window.close();
                }

            }]

	});

	window.parent.setSizeHeight(700);
	_window.show();
	recorda_store.load();	
	
	form_edit.form.load({
		params: {
				cdIdUnico: record.get('cdIdUnico'),
				codigoConfAlerta: record.get('codigoConfAlerta')
		},
		waitTitle: getLabelFromMap('400021', helpMap,'Espere...'),
		waitMsg: getLabelFromMap('400028', helpMap,'Leyendo datos...'),
		success: function(form,action){
			var cdTem_In = action.result.data.cdTemporal;
			/*temp_store.load({
				callback: function(r, o, success){
					if (success){
					       comboTempId.setValue(cdTem_In);
					}
				}
				})*/
			
		},
		failure: function() {Ext.Msg.alert('Error', 'No se pudo cargar los datos de la alerta');}
	});
}