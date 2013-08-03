function agregar(record) {
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
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_CLIENTES_CORP_POR_USUARIO}),
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
	var recorda_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_FRECUENCIAS}),
						reader: new Ext.data.JsonReader({
								root: 'frecuencias',
								id: 'codigo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string'},
								{name: 'descripcion', type: 'string'} 
							])
				});
	var rol_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ROL}),
						reader: new Ext.data.JsonReader({
								root: 'confAlertasAutoRol',
								id: 'cdRol',
								successProperty: '@success'
							}, [
								{name: 'cdRol', type: 'string', mapping: 'cdRol'},
								{name: 'dsRol', type: 'string', mapping: 'dsRol'} 
							])
				});
	var ramo_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_TIPORAMO}),
						reader: new Ext.data.JsonReader({
								root: 'tiposRamo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'} 
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


	//Definir quién lo ha hecho ya y usarlo
	var prod_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_PRODUCTOS}),
						reader: new Ext.data.JsonReader({
								root: 'productosAseguradoraCliente',
								id: 'codigo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'} 
							])
				});
	var reg_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_REGION}),
						reader: new Ext.data.JsonReader({
								root: 'regionesComboBox',
								id: 'codigo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'} 
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
		id:'form_nuevoId',
        labelWidth : 10,
        url : _ACTION_GUARDAR_CONFIGURACION_ALERTAS,
        frame : true,
        bodyStyle : 'padding:5px 5px 0',
        width : 700,
        bodyStyle:'background: white',
        autoHeight: true,
        waitMsgTarget : true,
        defaults: {labelWidth: 90},
        layout: 'column',
        labelAlign:'right',
        layoutConfig: {columns: 2},
        items: [
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
        						{xtype: 'hidden', id: 'cdIdUnico', name: 'cdIdUnico', id: 'cdIdUnico'},
     							{xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{cdUsuario}. {dsUsuario}" class="x-combo-list-item">{dsUsuario}</div></tpl>',
	                            store: users_store, 
	                            displayField:'dsUsuario',
	                            valueField:'cdUsuario',
	                            hiddenName: 'cdUsuario',
	                            typeAhead: true, 
	                            id:'cmbUsuarAddConfAlertId', 
	                            mode: 'local',
	                            triggerAction: 'all',
	                            fieldLabel: getLabelFromMap('cmbUsuarAddConfAlertId',helpMap,'Usuario'),
	                            tooltip: getToolTipFromMap('cmbUsuarAddConfAlertId',helpMap,'Usuario'),
	                            hasHelpIcon:getHelpIconFromMap('cmbUsuarAddConfAlertId',helpMap),
								Ayuda: getHelpTextFromMap('cmbUsuarAddConfAlertId',helpMap),
	                            width: 200, 
	                            emptyText:'Seleccione Usuario...',
	                            selectOnFocus:true, 
	                            forceSelection: true,
	                            onSelect: function (record) {
	                            				this.setValue(record.get('cdUsuario'));
	                            				this.collapse();

	                            				Ext.getCmp('cmbRolAddConfAlert').setValue('');
	                            				rol_store.removeAll();
	                            				cli_store.removeAll();
	                            				cli_store.reload({
	                            						params: {cdUsuario: record.get('cdUsuario')}
	                            						//failure: cli_store.removeAll()
	                            				});
	                            				rol_store.load({
	                            					params: {cdElemento: Ext.getCmp('cliente').getValue(),
	                            							cdUsuario: record.get('cdUsuario')}
	                            				});
	                            		}
	                            }
        					]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
      							{xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{cdRol}. {dsRol}" class="x-combo-list-item">{dsRol}</div></tpl>',
	                            store: rol_store, displayField:'dsRol', valueField:'cdRol', hiddenName: 'cdRol', typeAhead: true,
	                            id:'cmbRolAddConfAlert', mode: 'local', triggerAction: 'all', fieldLabel: getLabelFromMap('cmbRolAddConfAlert',helpMap,'Rol'), tooltip: getToolTipFromMap('cmbRolAddConfAlert',helpMap,'Rol'), width: 200, emptyText:'Seleccione Rol...',
	                            hasHelpIcon:getHelpIconFromMap('cmbRolAddConfAlert',helpMap),
								Ayuda: getHelpTextFromMap('cmbRolAddConfAlert',helpMap),
	                            selectOnFocus:true, forceSelection: true/*, allowBlank:false*/}
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
      							{xtype: 'combo', 
      							labelWidth: 50, 
      							tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
	                            store: cli_store, 
	                            displayField:'dsElemen',
	                            valueField:'cdElemento', 
	                            hiddenName: 'cdPerson',
	                            typeAhead: true,
	                            id:'cliente',
	                            mode: 'cmbClienAddConfAlert',
	                            triggerAction: 'all', 
	                            fieldLabel: getLabelFromMap('cmbClienAddConfAlert',helpMap,'Nivel'), 
	                            tooltip: getToolTipFromMap('cmbClienAddConfAlert',helpMap,'Nivel'),
	                            hasHelpIcon:getHelpIconFromMap('cmbClienAddConfAlert',helpMap),
								Ayuda: getHelpTextFromMap('cmbClienAddConfAlert',helpMap),
								width: 200, 
	                            emptyText:'Seleccione Nivel...',
	                            selectOnFocus:true,
	                            //allowBlank:false,
	                            forceSelection: true,
	                            onSelect: function (record) {
	                            				this.setValue(record.get('cdElemento'));
	                            				this.collapse();
	                            				
	                            				Ext.getCmp('cmbAsegAddConfAlert').setValue('');
	                            				aseg_store.removeAll();
	                            				aseg_store.load({
	                            					params: {cdElemento: record.get('cdElemento')}
	                            					//failure: aseg_store.removeAll()
	                            				});
	                            				Ext.getCmp('cmbRamoAddConfAlert').setValue('');
	                            				ramo_store.removeAll();
	                            				ramo_store.load({
	                            					params: {cdElemento: record.get('cdElemento')}
	                            					//failure: ramo_store.removeAll()
	                            				});
	                            				Ext.getCmp('cmbRolAddConfAlert').setValue('');
	                            				rol_store.removeAll();
	                            				rol_store.load({
	                            					params: {cdElemento: record.get('cdElemento'),
	                            							cdUsuario: Ext.getCmp('cmbUsuarAddConfAlertId').getValue()}
	                            				});
	                            		}
	                            }
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
      							{xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                            store: ramo_store, displayField:'descripcion', valueField:'codigo', hiddenName: 'cdTipRam', typeAhead: true,
	                            id:'cmbRamoAddConfAlert' ,mode: 'local', triggerAction: 'all', fieldLabel: getLabelFromMap('cmbRamoAddConfAlert',helpMap,'Producto'), tooltip: getToolTipFromMap('cmbRamoAddConfAlert',helpMap,'Producto'), width: 200, emptyText:'Seleccione Tipo de Producto...',
	                            hasHelpIcon:getHelpIconFromMap('cmbRamoAddConfAlert',helpMap),
								Ayuda: getHelpTextFromMap('cmbRamoAddConfAlert',helpMap),
	                            selectOnFocus:true, forceSelection: true}
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
      							{xtype: 'textfield', name: 'dsAlerta', id: 'addConfAlertdsAlertaNom',allowBlank:false, fieldLabel: getLabelFromMap('addConfAlertdsAlertaNom',helpMap,'Nombre'), tooltip: getToolTipFromMap('addConfAlertdsAlerta',helpMap,'Nombre'),
      							hasHelpIcon:getHelpIconFromMap('addConfAlertdsAlertaNom',helpMap),
								Ayuda: getHelpTextFromMap('addConfAlertdsAlertaNom',helpMap)}
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
      							{xtype: 'combo', labelWidth: 70, tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                            store: aseg_store, displayField:'dsUniEco', valueField:'cdUniEco', hiddenName: 'cdUniEco', typeAhead: true,
	                            id:'cmbAsegAddConfAlert', mode: 'local', triggerAction: 'all', fieldLabel: getLabelFromMap('cmbAsegAddConfAlert',helpMap,'Aseguradora'), tooltip: getToolTipFromMap('cmbAsegAddConfAlert',helpMap,'Aseguradora'), width: 200, emptyText:'Seleccione Aseguradora...',
	                            hasHelpIcon:getHelpIconFromMap('cmbAsegAddConfAlert',helpMap),
								Ayuda: getHelpTextFromMap('cmbAsegAddConfAlert',helpMap),
	                            selectOnFocus:true, forceSelection: true,
	                            onSelect: function (record) {
	                            				this.setValue(record.get('cdUniEco'));
	                            				this.collapse();

	                            				Ext.getCmp('cmbProdAddConfAlert').setValue('');
	                            				prod_store.removeAll();
	                            				prod_store.load({
	                            					params: {
	                            								cdElemento: form_nuevo.form.findField('cdPerson').getValue(),
	                            								cdunieco: record.get('cdUniEco')
	                            							},
	                            					failure: prod_store.removeAll()
	                            				});
	                            		}
	                            }
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
      							{xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{otClave}. {otValor}" class="x-combo-list-item">{otValor}</div></tpl>',
	                            store: proc_store, displayField:'otValor', valueField:'otClave', hiddenName: 'cdProceso', typeAhead: true, 
	                            mode: 'local', triggerAction: 'all', 
	                            fieldLabel: getLabelFromMap('cmbProceAddConfAlert',helpMap,'Proceso'), 
	                            tooltip: getToolTipFromMap('cmbProceAddConfAlert',helpMap,'Proceso'),
	                            hasHelpIcon:getHelpIconFromMap('cmbProceAddConfAlert',helpMap),
								Ayuda: getHelpTextFromMap('cmbProceAddConfAlert',helpMap),
								id:'cmbProceAddConfAlert',
	                            width: 200, emptyText:'Seleccione Proceso...',
	                            selectOnFocus:true,forceSelection: true,allowBlank:false}
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
      							{xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                            store: prod_store, displayField:'descripcion', valueField:'codigo', hiddenName: 'cdProducto', typeAhead: true,
	                            id:'cmbProdAddConfAlert', mode: 'local', triggerAction: 'all', 
	                            fieldLabel: getLabelFromMap('cmbProdAddConfAlert',helpMap,'Producto'), 
	                            tooltip: getToolTipFromMap('cmbProdAddConfAlert',helpMap,'Producto'), 
	                            hasHelpIcon:getHelpIconFromMap('cmbProdAddConfAlert',helpMap),
								Ayuda: getHelpTextFromMap('cmbProdAddConfAlert',helpMap),
	                            width: 200, emptyText:'Seleccione Producto...',
	                            selectOnFocus:true, forceSelection: true}
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
      							{
      								xtype: 'combo', 
      								labelWidth: 80, tpl: '<tpl for="."><div ext:qtip="{otClave}. {otValor}" class="x-combo-list-item">{otValor}</div></tpl>',
	                            	store: temp_store, 
	                            	displayField:'otValor', 
	                            	valueField:'otClave', 
	                            	hiddenName: 'cdTemporalidad', 
	                            	typeAhead: true,
	                            	mode: 'local', 
	                            	triggerAction: 'all', 
	                            	id:'cmbTempAddConfAlert',
	                            	fieldLabel: getLabelFromMap('cmbTempAddConfAlert',helpMap,'Temporalidad'), 
	                            	tooltip: getToolTipFromMap('cmbTempAddConfAlert',helpMap,'Temporalidad'),
	                            	hasHelpIcon:getHelpIconFromMap('cmbTempAddConfAlert',helpMap),
								    Ayuda: getHelpTextFromMap('cmbTempAddConfAlert',helpMap),
	                            	width: 200, emptyText:'Seleccione Temporalidad...',
		                            selectOnFocus:true, 
		                            forceSelection: true,
		                            allowBlank:false,
		                            onSelect: function (record) {
	                            				this.setValue(record.get('otClave'));
												
												if (record.get("otClave")=='1')
                    							{
                    								Ext.getCmp('nfConfAlertDur').disable();
                    								Ext.getCmp('nfConfAlertDiasAnt').enable();
                    							}else{
                    								Ext.getCmp('nfConfAlertDur').enable();
                    								Ext.getCmp('nfConfAlertDiasAnt').disable();
                    							}
	                            				
	                            				this.collapse();
	                            		}
		                         }
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
      							{xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                            store: reg_store, displayField:'descripcion', valueField:'codigo', hiddenName: 'dsRegion', typeAhead: true,
	                            id:'cmbRegAddConfAlert', mode: 'local', triggerAction: 'all',  
	                            fieldLabel: getLabelFromMap('cmbRegAddConfAlert',helpMap,'Regi&oacute;n'), 
	                            tooltip: getToolTipFromMap('cmbRegAddConfAlert',helpMap,'Regi&oacute;n'), 
	                            hasHelpIcon:getHelpIconFromMap('cmbRegAddConfAlert',helpMap),
								Ayuda: getHelpTextFromMap('cmbRegAddConfAlert',helpMap),
	                            width: 200, emptyText:'Seleccione Región...',
	                            selectOnFocus:true, forceSelection: true}
	        				]
        		},{
        			layout: 'column',
        			columnWidth: 1.0,
        			labelWidth: 0,
        			labelSeparator: '',
        			items: [
			        		{
			        			layout: 'form',
			        			columnWidth: .22,
			        			labelWidth: 0,
			        			labelSeparator: '',
			        			items: [
			        						{
			        							id: 'accordion1',
										        split:true,
										        width:140,
										        height: 150,
										        minSize:'200',
										        maxSize:'200',
										        margins:'0 2 0 2',
										        bodyStyle:'padding:0px',
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
			        			columnWidth: .78,
			        			labelWidth: 0,
			        			labelAlign: 'top',
			        			labelSeparator: '',
						        bodyStyle:'padding:0px',
			        			items: [
        						{xtype: 'htmleditor', name: 'dsMensaje',allowBlank:false, 
        						fieldLabel: getLabelFromMap('addConfAlertMsg',helpMap,'Mensaje'), 
        						tooltip: getToolTipFromMap('addConfAlertMsg',helpMap,'Mensaje'),
        						hasHelpIcon:getHelpIconFromMap('addConfAlertMsg',helpMap),
								Ayuda: getHelpTextFromMap('addConfAlertMsg',helpMap),
        						id: 'addConfAlertMsg', width:520, height: 150}
        						]
        					}
        			]
        		},
        		{
        			layout: 'form',
        			columnWidth: 1.0,
        			items: [
        						//{xtype: 'htmleditor', name: 'dsMensaje',allowBlank:false, fieldLabel: getLabelFromMap('addConfAlertMsg',helpMap,'Mensaje'), tooltip: getToolTipFromMap('addConfAlertMsg',helpMap,'Mensaje'), id: 'dsMensaje', width:550, height: 100},
      							{xtype: 'combo', labelWidth: 80, tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                            store: recorda_store, displayField:'descripcion', valueField:'codigo', hiddenName: 'nmFrecuencia', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', 
	                            id:'cmbConfAlertRecEn',
	                            fieldLabel: getLabelFromMap('cmbConfAlertRecEn',helpMap,'Recordar en'), 
	                            tooltip: getToolTipFromMap('cmbConfAlertRecEn',helpMap,'Recordar en'),
	                            hasHelpIcon:getHelpIconFromMap('cmbConfAlertRecEn',helpMap),
								Ayuda: getHelpTextFromMap('cmbConfAlertRecEn',helpMap),
	                            width: 200, emptyText:'Seleccione...',
	                            selectOnFocus:true, forceSelection: true,allowBlank:false},

	                            {xtype: 'datefield', name: 'feInicio', 
	                            format: 'd/m/Y', 
	                            altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g", allowBlank:false,
	                            id:'txtConfAlertFeIni',
	                            fieldLabel: getLabelFromMap('txtConfAlertFeIni',helpMap,'Fecha Inicio'), 
	                            tooltip: getToolTipFromMap('txtConfAlertFeIni',helpMap,'Fecha Inicio'),
	                            hasHelpIcon:getHelpIconFromMap('txtConfAlertFeIni',helpMap),
								Ayuda: getHelpTextFromMap('txtConfAlertFeIni',helpMap)
	                            },
	                            {xtype: 'numberfield', name: 'nmDiasAnt', id: 'nfConfAlertDiasAnt',allowBlank:false, 
	                            fieldLabel: getLabelFromMap('nfConfAlertDiasAnt',helpMap,'Dias Anticip.'), 
	                            tooltip: getToolTipFromMap('nfConfAlertDiasAnt',helpMap,'Dias Anticip.'),
	                            hasHelpIcon:getHelpIconFromMap('nfConfAlertDiasAnt',helpMap),
								Ayuda: getHelpTextFromMap('nfConfAlertDiasAnt',helpMap)},
								
	                            {xtype: 'numberfield', name: 'nmDuracion', id: 'nfConfAlertDur',allowBlank:false, 
	                            fieldLabel: getLabelFromMap('nfConfAlertDur',helpMap,'Duraci&oacute;n (d&iacute;as)'), 
	                            tooltip: getToolTipFromMap('nfConfAlertDur',helpMap,'Duraci&oacute;n (d&iacute;as)'),
	                            hasHelpIcon:getHelpIconFromMap('nfConfAlertDur',helpMap),
								Ayuda: getHelpTextFromMap('nfConfAlertDur',helpMap)
	                            },
	                            {xtype: 'checkbox', name: 'fgMandaEmail', id: 'cbxConfAlertRecEn', labelWidth: 80,
	                            fieldLabel: getLabelFromMap('cbxConfAlertRecEn',helpMap,'Mandar en email'), 
	                            tooltip: getToolTipFromMap('cbxConfAlertRecEn',helpMap,'Mandar en email'),
	                            hasHelpIcon:getHelpIconFromMap('cbxConfAlertRecEn',helpMap),
								Ayuda: getHelpTextFromMap('cbxConfAlertRecEn',helpMap)
	                            },
	                            {xtype: 'checkbox', name: 'fgMandaPantalla', id: 'cbxConfAlertRecEnMP', labelWidth: 80,
	                            fieldLabel: getLabelFromMap('cbxConfAlertRecEnMP',helpMap,'Pop up'), 
	                            tooltip: getToolTipFromMap('cbxConfAlertRecEnMP',helpMap,'Pop up'),
	                            hasHelpIcon:getHelpIconFromMap('cbxConfAlertRecEnMP',helpMap),
								Ayuda: getHelpTextFromMap('cbxConfAlertRecEnMP',helpMap)
	                            },
	                            {xtype: 'checkbox', name: 'fgPermPantalla', id: 'cbxConfAlertRecEnPP', labelWidth: 80,
	                            fieldLabel: getLabelFromMap('cbxConfAlertRecEnPP',helpMap,'Pantalla'), 
	                            tooltip: getToolTipFromMap('cbxConfAlertRecEnPP',helpMap,'Pantalla'),
	                            hasHelpIcon:getHelpIconFromMap('cbxConfAlertRecEnPP',helpMap),
								Ayuda: getHelpTextFromMap('cbxConfAlertRecEnPP',helpMap)
	                            }	                            
        				]
        		}
        	]	
    });

		arbol.on('dblclick', function (nodo, evt) {
			Ext.getCmp('addConfAlertMsg').insertAtCursor(nodo.text);
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
	
	var _window = new Ext.Window ({
			//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('71',helpMap,'Agregar Configuraci&oacute;n de Alertas')+'</span>',
			title: getLabelFromMap('wndwAgrConfAlertId', helpMap,'Agregar Configuraci&oacute;n de Alertas'),
			id:'wndwAgrConfAlertId',
			width: 700,
			autoHeight: true,
			modal:true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: form_nuevo,
            buttons : [ {
                text : getLabelFromMap('btnAddConfAlertAgregar',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('btnAddConfAlertAgregar',helpMap,'Guardar'),
                disabled : false,
                handler : function(){
                	if (form_nuevo.form.isValid()) {
                		if (Ext.getCmp('addConfAlertMsg').getValue() == "&nbsp;" || Ext.getCmp('addConfAlertMsg').getValue() == "") {
                       		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400065', helpMap,'Ingrese un mensaje'));
                       		return;
						}else{
							form_nuevo.form.submit( {
	                            //action a invocar cuando el formulario haga submit
	                            url : _ACTION_GUARDAR_CONFIGURACION_ALERTAS,
	                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
	                            success : function(from, action) {
	                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0],function(){reloadGrid(Ext.getCmp('grillaId'),Ext.getCmp('el_formId'));});
	                                _window.close();                                     
	                            },
	                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
	                            failure : function(form, action) {
	                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Guardar: ' + action.result.errorMessages[0]);
	                           },
	                            waitMsg : getLabelFromMap('400027', helpMap,'guardando datos ...')
	                        });
						}
                	}else{
                       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                	}
                }

            }, {

                text : getLabelFromMap('btnCancelConfAlertAgregar',helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('btnCancelConfAlertAgregar',helpMap,'Cancelar'),

                handler : function() {
                    _window.close();
                }

            }]

	});
	if (record != null && record.get('cdIdUnico') != "") {
		form_nuevo.findById('cdIdUnico').setValue(record.get('cdIdUnico'));
	}
	
	
	_window.show();
	
	recorda_store.load({
		callback:function(){
			proc_store.load({
				callback:function(){
					rol_store.load({
						callback:function(){
							temp_store.load({
								callback:function(){
									reg_store.load({
										callback:function(){
											users_store.load();
										}
									});
								}
							});
						}
					});
				}
			});
		}
	});
	
}