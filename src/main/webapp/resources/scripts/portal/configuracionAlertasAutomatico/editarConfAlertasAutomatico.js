function editar(record) {

	var alert_reg = new Ext.data.JsonReader({
						root: 'MConfiguracionAlertasAutomaticoList',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
							{name: 'cdUsuario', type: 'string', mapping: 'cdUsuario'},
							{name: 'codigoUsuario', type: 'string', mapping: 'dsUsuario'},
							{name: 'cdRol', type: 'string', mapping: 'cdRol'},
							{name: 'codigoRol', type: 'string', mapping: 'dsRol'},
							{name: 'HCliente', type: 'string', mapping: 'cdCliente'},
							{name: 'dsNombre', type: 'string', mapping: 'dsNombre'},
							{name: 'codigoPersona', type: 'string', mapping: 'dsNombre'},
							{name: 'cdProceso', type: 'string', mapping: 'cdProceso'},
							{name: 'desProceso', type: 'string', mapping: 'dsProceso'},
							{name: 'dsUsuario', type: 'string', mapping: 'dsUsuario'},
							{name: 'HRamo', type: 'string', mapping: 'cdTipRam'},
							{name: 'dsRamo', type: 'string', mapping: 'dsRamo'},
							{name: 'TipoRamo', type: 'string', mapping: 'dsRamo'},
							{name: 'HAseguradora', type: 'string', mapping: 'cdUniEco'},
							{name: 'dsUniEco', type: 'string', mapping: 'dsUniEco'},
							{name: 'desUniEco', type: 'string', mapping: 'dsUniEco'},
							{name: 'HProducto', type: 'string', mapping: 'cdProducto'},
							{name: 'desProducto', type: 'string', mapping: 'dsProducto'},
							{name: 'cdRol', type: 'string', mapping: 'cdRol'},
							{name: 'dsRol', type: 'string', mapping: 'dsRol'},
							{name: 'dsRegion', type: 'string', mapping: 'cdRegion'},
							{name: 'desRegion', type: 'string', mapping: 'dsRegion'},
							{name: 'cdTemporalidad', type: 'string', mapping: 'cdTemporalidad'},
							{name: 'dsTemporalidad', type: 'string', mapping: 'dsTemporalidad'},
							{name: 'dsMensaje', type: 'string', mapping: 'dsMensaje'},
							{name: 'feInicio', type: 'string', mapping: 'feInicio'},
							{name: 'feInicioV', type: 'string', mapping: 'feInicio'},
							{name: 'nmDiasAnt', type: 'string', mapping: 'nmDiasAnt'},
							{name: 'nmDuracion', type: 'string', mapping: 'nmDuracion'},
							{name: 'fgMandaEmail', type: 'string', mapping: 'fgMandaEmail'},
							{name: 'fgMandaPantalla', type: 'string', mapping: 'fgMandaPantalla'},
							{name: 'fgPermPantalla', type: 'string', mapping: 'fgPermPantalla'},
							{name: 'dsAlerta', type: 'string', mapping: 'dsAlerta'},
							{name: 'nmFrecuencia', type: 'string', mapping: 'nmFrecuencia'},
							{name: 'txtRecordarEn', type: 'string', mapping: 'dsFrecuencia'}
						]
		);
	var store_reg = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: _ACTION_CONF_ALERTAS_GET}),
		reader: alert_reg
	});
	var users_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_USUARIOS}),
						reader: new Ext.data.JsonReader({
								root: 'confAlertasAutoUsuarios',
								id: 'cdUsuario',
								successProperty: '@success'
							}, [
								{name: 'cdUsuario', type: 'string', mapping: 'cdUsuario'},
								{name: 'dsUsuario', type: 'string', mapping: 'dsUsuario'} 
							]),
							remoteSort: true
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
	
		/*		
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
			*/	
				
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
								successProperty: '@success',
								id: 'cdUniEco'
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


     var recorda_store = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_FRECUENCIAS}),
						reader: new Ext.data.JsonReader({
								root: 'frecuencias',
								id: 'codigo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string',mapping: 'codigo'},
								{name: 'descripcion', type: 'string',mapping: 'descripcion'} 
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
				
				
				
				
	var form_edit = new Ext.FormPanel ({
		form_edit:'form_editId',
        labelWidth : 10,
        url : _ACTION_CONF_ALERTAS_GET,
        frame : true,
        bodyStyle : 'padding:5px 5px 0',
        bodyStyle:'background: white',
        width : 700,
        autoHeight: true,
        waitMsgTarget : true,
        defaults: {labelWidth: 90},
        layout: 'column',
        labelAlign:'right',
        layoutConfig: {columns: 2, align: 'left'},
        store: store_reg,
        reader: alert_reg,
        items: [
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
        						{xtype: 'hidden', id: 'cdIdUnico', name: 'cdIdUnico', id: 'cdIdUnico'},
        						{xtype: 'hidden', id: 'HCliente', name: 'HCliente'},
        						{xtype: 'hidden', id: 'HProducto', name: 'HProducto'},
        						{xtype: 'hidden', id: 'HRamo', name: 'HRamo'},
        						{xtype: 'hidden', id: 'HAseguradora', name: 'HAseguradora'},
        						{xtype: 'textfield', 
        						id:'cmbUsuarEditConfAlert',
        						fieldLabel: getLabelFromMap('cmbUsuarEditConfAlert',helpMap,'Usuario'), 
	                            tooltip: getToolTipFromMap('cmbUsuarEditConfAlert',helpMap,'Usuario'),
	                            hasHelpIcon:getHelpIconFromMap('cmbUsuarEditConfAlert',helpMap),
								Ayuda: getHelpTextFromMap('cmbUsuarEditConfAlert',helpMap),
								width: 200,
	                            disabled: true,
	                            name: 'codigoUsuario'
        						},
     							{xtype: 'hidden', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{cdUsuario}. {dsUsuario}" class="x-combo-list-item">{dsUsuario}</div></tpl>',
	                            store: users_store,
	                            hidden: true,
	                            displayField:'dsUsuario',
	                            valueField:'cdUsuario', 
	                            hiddenName: 'cdUsuario', 
	                            name: 'cdUsuario',
	                            id: 'cdUsuario',
	                            typeAhead: true,
	                            mode: 'local',
	                            triggerAction: 'all',
	                            labelSeparator: '',
	                            //fieldLabel: getLabelFromMap('cmbUsuarEditConfAlert',helpMap,'Usuario'), 
	                            //tooltip: getToolTipFromMap('cmbUsuarEditConfAlert',helpMap,'Usuario'),
	                            width: 200,
	                            emptyText:'Seleccione Usuario...',
	                            selectOnFocus:true,id: 'cmbUsuario',forceSelection: true,
	                            onSelect: function (record) {
	                            				this.setValue(record.get('cdUsuario'));
	                            				this.collapse();
	                            				cli_store.load({
	                            						params: {cdUsuario: record.get('cdUsuario')},
	                            						failure: cli_store.removeAll()
	                            				});
	                            		}
	                            }
        					]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
        						{xtype: 'textfield', disabled: true, name: 'codigoRol', id: 'codigoRol', 
        						fieldLabel: getLabelFromMap('codigoRol',helpMap,'Rol'), 
        						tooltip: getToolTipFromMap('codigoRol',helpMap,'Rol'),
        						hasHelpIcon:getHelpIconFromMap('codigoRol',helpMap),
								Ayuda: getHelpTextFromMap('codigoRol',helpMap),
        						width: 200},
      							{xtype: 'hidden', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{cdRol}. {dsRol}" class="x-combo-list-item">{dsRol}</div></tpl>',
	                            store: rol_store, displayField:'dsRol', valueField:'cdRol', hiddenName: 'cdRol', typeAhead: true, name: 'cdRol', id: 'cdRol',
	                            mode: 'local', triggerAction: 'all', fieldLabel: getLabelFromMap('cmbRolEditConfAlert',helpMap,'Rol'), tooltip: getToolTipFromMap('cmbRolEditConfAlert',helpMap,'Rol'), width: 200, emptyText:'Seleccione Rol...',
	                            selectOnFocus:true, forceSelection: true,allowBlank:false}
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
        						{xtype: 'textfield', disabled: true, name: 'codigoPersona', id: 'codigoPersona', 
        						fieldLabel: getLabelFromMap('codigoPersona',helpMap,'Nivel'), 
        						tooltip: getToolTipFromMap('codigoPersona',helpMap,'Nivel'),
        						hasHelpIcon:getHelpIconFromMap('codigoPersona',helpMap),
								Ayuda: getHelpTextFromMap('codigoPersona',helpMap),
        						width: 200},
      							{xtype: 'hidden', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
	                            store: cli_store, displayField:'dsElemen', valueField:'cdElemento', hiddenName: 'cdPerson', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', fieldLabel: getLabelFromMap('cmbCliEditConfAlert',helpMap,'Nivel'), tooltip: getToolTipFromMap('cmbCliEditConfAlert',helpMap,'Nivel'), width: 200, emptyText:'Seleccione Nivel...',
	                            selectOnFocus:true, /*id: 'cdElemento',*/id: 'cdPerson', name: 'cdPerson', forceSelection: true,
	                            onSelect: function (record) {
	                            				this.setValue(record.get('cdElemento'));
	                            				this.collapse();
	                            				ramo_store.load({
	                            					params: {cdElemento: record.get('cdElemento')},
	                            					failure: ramo_store.removeAll()
	                            				});
	                            				
	                            				aseg_store.load({
	                            					params: {cdElemento: record.get('cdElemento')},
	                            					failure: aseg_store.removeAll()
	                            				});
	                            		}
	                            }
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
								{xtype: 'textfield', disabled: true, name: 'TipoRamo', id: 'TipoRamo', 
								fieldLabel: getLabelFromMap('TipoRamo',helpMap,'Producto'), 
								tooltip: getToolTipFromMap('TipoRamo',helpMap,'Producto'),
								hasHelpIcon:getHelpIconFromMap('TipoRamo',helpMap),
								Ayuda: getHelpTextFromMap('TipoRamo',helpMap),
								width: 200},
      							{xtype: 'hidden', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                            store: ramo_store, displayField:'descripcion', valueField:'codigo', hiddenName: 'cdTipRam', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', fieldLabel: getLabelFromMap('cmbRamoConfAlert',helpMap,'Producto'), tooltip: getToolTipFromMap('cmbRamoConfAlert',helpMap,'Producto'), width: 200, emptyText:'Seleccione Tipo de Producto...',
	                            selectOnFocus:true, /*id: 'codTipRam',*/ id: 'cdTipRam', name: 'cdTipRam', forceSelection: true}
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
      							{xtype: 'textfield', name: 'dsAlerta',allowBlank:false, id: 'dsAlerta',
      							fieldLabel: getLabelFromMap('dsAlerta',helpMap,'Nombre'), 
      							tooltip: getToolTipFromMap('dsAlerta',helpMap,'Nombre'),
      							hasHelpIcon:getHelpIconFromMap('dsAlerta',helpMap),
								Ayuda: getHelpTextFromMap('dsAlerta',helpMap)
      							}
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
        						{xtype: 'textfield', id: 'desUniEco', name: 'desUniEco', disabled: true, 
        						fieldLabel: getLabelFromMap('desUniEco',helpMap,'Aseguradora'), 
        						tooltip: getToolTipFromMap('desUniEco',helpMap,'Aseguradora'),
        						hasHelpIcon:getHelpIconFromMap('desUniEco',helpMap),
								Ayuda: getHelpTextFromMap('desUniEco',helpMap),
        						width: 200},
      							{xtype: 'hidden', labelWidth: 70, tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                            store: aseg_store, displayField:'dsUniEco', valueField:'cdUniEco', hiddenName: 'cdUniEco', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', fieldLabel: getLabelFromMap('cmbAsegConfAlert',helpMap,'Aseguradora'), tooltip: getToolTipFromMap('cmbAsegConfAlert',helpMap,'Aseguradora'), width: 200, emptyText:'Seleccione Aseguradora...',
	                            selectOnFocus:true, /*id: 'codUniEco',*/ id: 'cdUniEco', name: 'cdUniEco', forceSelection: true,
	                            onSelect: function (record) {
	                            				this.setValue(record.get('cdUniEco'));
	                            				this.collapse();
	                            				prod_store.load({
	                            					params: {
	                            								cdElemento: form_edit.findById('cdElemento').getValue(),
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
        						{xtype: 'textfield', name: 'desProceso', disabled: true, 
        						id:'cmbProcesoConfAlert',
        						fieldLabel: getLabelFromMap('cmbProcesoConfAlert',helpMap,'Proceso'), 
        						tooltip: getToolTipFromMap('cmbProcesoConfAlert',helpMap,'Proceso'),
        						hasHelpIcon:getHelpIconFromMap('cmbProcesoConfAlert',helpMap),
								Ayuda: getHelpTextFromMap('cmbProcesoConfAlert',helpMap),
								width: 200},
      							{xtype: 'hidden', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{otClave}. {otValor}" class="x-combo-list-item">{otValor}</div></tpl>',
	                            store: proc_store, displayField:'otValor', valueField:'otClave', hiddenName: 'cdProceso', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', fieldLabel: getLabelFromMap('cmbProcesoConfAlert',helpMap,'Proceso'), tooltip: getToolTipFromMap('cmbProcesoConfAlert',helpMap,'Proceso'), width: 200, emptyText:'Seleccione Proceso...',
	                            selectOnFocus:true, forceSelection: true, name: 'cdProceso', id: 'cdProceso'}
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
        						{xtype: 'textfield', id: 'desProducto', name: 'desProducto', disabled: true, 
        						fieldLabel: getLabelFromMap('desProducto',helpMap,'Producto'), 
        						tooltip: getToolTipFromMap('desProducto',helpMap,'Producto'), 
        						hasHelpIcon:getHelpIconFromMap('desProducto',helpMap),
								Ayuda: getHelpTextFromMap('desProducto',helpMap),
        						width: 200},
        						{xtype: 'hidden', id: 'cdProducto', name: 'cdProducto'}
	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
        						{xtype: 'textfield', disabled: true, name: 'dsTemporalidad', id: 'dsTemporalidad', 
        						fieldLabel: getLabelFromMap('dsTemporalidad',helpMap,'Temporal'), 
        						tooltip: getToolTipFromMap('dsTemporalidad',helpMap,'Temporal'), 
        						hasHelpIcon:getHelpIconFromMap('dsTemporalidad',helpMap),
								Ayuda: getHelpTextFromMap('dsTemporalidad',helpMap),
        						width: 200},
      							/*{xtype: 'hidden', labelWidth: 80, tpl: '<tpl for="."><div ext:qtip="{otValor}. {otClave}" class="x-combo-list-item">{otValor}</div></tpl>',
	                            store: temp_store, displayField:'otValor', valueField:'otClave', hiddenName: 'cdTemporalidad', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', fieldLabel: getLabelFromMap('cmbTempConfAlert',helpMap,'Temporal'), tooltip: getToolTipFromMap('cmbTempConfAlert',helpMap,'Temporal'), width: 200, emptyText:'Seleccione Temporalidad...',
	                            selectOnFocus:true, name: 'cdTemporalidad',forceSelection: true,allowBlank:false}*/
        						{xtype: 'hidden', name: 'cdTemporalidad', id: 'cdTemporalidadId'}

	        				]
        		},
        		{
        			layout: 'form',
        			columnWidth: .50,
        			items: [
        						{xtype: 'textfield', disabled: true, name: 'desRegion', id: 'desRegion', 
        						fieldLabel: getLabelFromMap('desRegion',helpMap,'Region'), 
        						tooltip: getToolTipFromMap('desRegion',helpMap,'Region'), 
        						hasHelpIcon:getHelpIconFromMap('desRegion',helpMap),
								Ayuda: getHelpTextFromMap('desRegion',helpMap),
        						width: 200},
      							
      								{xtype: 'hidden', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                            store: reg_store, displayField:'descripcion', valueField:'codigo', hiddenName: 'dsRegion', typeAhead: true, name: 'dsRegion', id: 'dsRegion',
	                            mode: 'local', triggerAction: 'all', fieldLabel: getLabelFromMap('cmbRegConfAlert',helpMap,'Region'), tooltip: getToolTipFromMap('cmbRegConfAlert',helpMap,'Region'), width: 200, emptyText:'Seleccione Region...',
	                            selectOnFocus:true,forceSelection: true}
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
										        layout:'accordion',
										        layoutConfig:{
										             animate:true
										        },
										        items: [
										        	arbol
										        ]
			        						}
			        			]
			        		},
			        		{
			        			
			        			layout: 'form',
			        			columnWidth: .78,
			        			labelWidth: 0,
			        			labelSeparator: '',
			        			labelAlign: 'top',
						        bodyStyle:'padding:0px',
			        			items: [
			        					{
    						
    									xtype: 'htmleditor',
										fieldLabel: getLabelFromMap('dsMensaje',helpMap,'Mensaje'),
		      							tooltip: getToolTipFromMap('dsMensaje',helpMap,'Mensaje'),
		      							hasHelpIcon:getHelpIconFromMap('dsMensaje',helpMap),
										Ayuda: getHelpTextFromMap('dsMensaje',helpMap),
		      							width:520,
		      							height:150,
		      							allowBlank: false,
					        			labelSeparator: '',
										blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		      							tooltip: getToolTipFromMap('edAlerTfMsg',helpMap,'Este campo es requerido'),
			        					name: 'dsMensaje',
			        					id: 'dsMensaje'
			        					}
        						]
        					}
        			]
        		},
        		{
        			layout: 'form',
        			columnWidth: 1.0,
        			items: [
        					                   
        				
        				//{xtype: 'htmleditor', name: 'dsMensaje',allowBlank:false, fieldLabel: getLabelFromMap('addConfAlertMsg',helpMap,'Mensaje'), tooltip: getToolTipFromMap('addConfAlertMsg',helpMap,'Mensaje'), id: 'dsMensaje', width:550, height: 100},
        	      		/*		{xtype: 'textfield', id: 'txtRecordarEn', name: 'txtRecordarEn', 
        						fieldLabel: getLabelFromMap('txtRecordarEn',helpMap,'Recordar en'), 
        						tooltip: getToolTipFromMap('txtRecordarEn',helpMap,'Recordar en'),
        						hasHelpIcon:getHelpIconFromMap('txtRecordarEn',helpMap),
								Ayuda: getHelpTextFromMap('txtRecordarEn',helpMap),
        						width: 200},
        			    */			
        					   {
                                xtype: 'combo',
                                labelWidth: 80,
                                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                            store: recorda_store,
	                            displayField:'descripcion',
	                            valueField:'codigo',
	                            hiddenName: 'nmFrecuencia',
	                            typeAhead: true,
	                            mode: 'local',
	                            triggerAction: 'all',
	                            name:'cmbFrecuencia', 
	                            id:'cmbFrecuencia',
	                            fieldLabel: getLabelFromMap('cmbFrecuencia',helpMap,'Recordar en'), 
	                            tooltip: getToolTipFromMap('cmbFrecuencia',helpMap,'Recordar en'),
	                            hasHelpIcon:getHelpIconFromMap('cmbFrecuencia',helpMap),
								Ayuda: getHelpTextFromMap('cmbFrecuencia',helpMap),
	                            width: 200,
	                            emptyText:'Seleccione...',
	                            selectOnFocus:true,
	                            forceSelection: true,
	                            allowBlank:false
	                           },
        						
        						//{xtype: 'hidden', id: 'nmFrecuencia', name: 'nmFrecuencia'},
	                            {xtype: 'datefield', name: 'feInicioV', id: 'feInicioV', format: 'Y/m/d', altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g", allowBlank:false,
	                            fieldLabel: getLabelFromMap('feInicioV',helpMap,'Fecha Inicio'), 
	                            tooltip: getToolTipFromMap('feInicioV',helpMap,'Fecha Inicio'),
	                            hasHelpIcon:getHelpIconFromMap('feInicioV',helpMap),
								Ayuda: getHelpTextFromMap('feInicioV',helpMap)},
	                            {xtype: 'hidden', name: 'feInicio', id: 'feInicio', format: 'd/m/Y', altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g", allowBlank:false,fieldLabel: getLabelFromMap('txtConfAlertFeIni',helpMap,'Fecha Inicio'), tooltip: getToolTipFromMap('txtConfAlertFeIni',helpMap,'Fecha Inicio')},
	                            {xtype: 'numberfield', name: 'nmDiasAnt', id: 'nmDiasAnt',/*allowBlank:false,*/ 
	                            fieldLabel: getLabelFromMap('nmDiasAnt',helpMap,'Dias Anticip.'), 
	                            tooltip: getToolTipFromMap('nmDiasAnt',helpMap,'Dias Anticip.'),
	                            hasHelpIcon:getHelpIconFromMap('nmDiasAnt',helpMap),
								Ayuda: getHelpTextFromMap('nmDiasAnt',helpMap)},
	                            {xtype: 'numberfield', name: 'nmDuracion', id: 'nmDuracion',/*allowBlank:false,*/ 
	                            fieldLabel: getLabelFromMap('nmDuracion',helpMap,'Duraci&oacute;n'), 
	                            tooltip: getToolTipFromMap('nmDuracion',helpMap,'Duraci&oacute;n'),
	                            hasHelpIcon:getHelpIconFromMap('nmDuracion',helpMap),
								Ayuda: getHelpTextFromMap('nmDuracion',helpMap)},
	                            {xtype: 'checkbox', name: 'fgMandaEmail', id: 'fgMandaEmail', labelWidth: 80,
	                            fieldLabel: getLabelFromMap('fgMandaEmail',helpMap,'Mandar en email'), 
	                            tooltip: getToolTipFromMap('fgMandaEmail',helpMap,'Mandar en email'),
	                            hasHelpIcon:getHelpIconFromMap('fgMandaEmail',helpMap),
								Ayuda: getHelpTextFromMap('fgMandaEmail',helpMap)
	                            },
	                            {xtype: 'checkbox', name: 'fgMandaPantalla', id: 'fgMandaPantalla', labelWidth: 80,
	                            fieldLabel: getLabelFromMap('fgMandaPantalla',helpMap,'Pop up'), 
	                            tooltip: getToolTipFromMap('fgMandaPantalla',helpMap,'Pop up'),
	                            hasHelpIcon:getHelpIconFromMap('fgMandaPantalla',helpMap),
								Ayuda: getHelpTextFromMap('fgMandaPantalla',helpMap)
	                            },
	                            {xtype: 'checkbox', name: 'fgPermPantalla', id: 'fgPermPantalla', labelWidth: 80,
	                            fieldLabel: getLabelFromMap('fgPermPantalla',helpMap,'Pantalla'), 
	                            tooltip: getToolTipFromMap('fgPermPantalla',helpMap,'Pantalla'),
	                             hasHelpIcon:getHelpIconFromMap('fgPermPantalla',helpMap),
								Ayuda: getHelpTextFromMap('fgPermPantalla',helpMap)
	                            }	
        				
        				
        				]
        		}
        	]	
    });

      var comboProducto = new Ext.form.ComboBox({xtype: 'combo', labelWidth: 50, tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                            store: prod_store, displayField:'descripcion', valueField:'codigo', hiddenName: 'cdProducto', typeAhead: true,
	                            mode: 'local', triggerAction: 'all', labelSeparator: '',width: 200, emptyText:'Seleccione Producto...',
	                            selectOnFocus:true, id: 'cdProducto', name: 'cdProducto', forceSelection: true, hidden: true});


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
			Ext.getCmp('dsMensaje').insertAtCursor(nodo.text);
		});

	                            
	                            
	                            
	var _window = new Ext.Window ({
			//title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('72',helpMap,'Editar Configuraci&oacute;n de Alertas')+'</span>',
			title: getLabelFromMap('wndwEditarConfAlertasAutId', helpMap,'Editar Configuraci&oacute;n de Alertas'),
			id:'wndwEditarConfAlertasAutId',
			width: 700,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	modal:true,
        	items: form_edit,
            //se definen los botones del formulario
            buttons : [ {

                text : getLabelFromMap('editConfAlertBtnSave',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('editConfAlertBtnSave',helpMap,'Guardar'),
				
                disabled : false,





                handler : function() {
					if (Ext.getCmp('dsMensaje').getValue() == "&nbsp;" || Ext.getCmp('dsMensaje').getValue() == "") {
							form_edit.form.isValid();
                       		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'), function () {
                       			Ext.getCmp('dsMensaje').focus();
                       		});
                       		
                       		return;
					}

                    if (form_edit.form.isValid()) {
						Ext.getCmp('feInicio').setValue(Ext.getCmp('feInicioV').getRawValue());
                        form_edit.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_CONFIGURACION_ALERTAS,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0], function(){reloadGrid(Ext.getCmp("grillaId"),Ext.getCmp("el_formId"))});
                                
                                _window.close();                                     
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Guardar: ' + action.result.errorMessages[0]);
                           },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando actualizacion de datos')

                        });

                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));

                    }

                }

            }, {

                text : getLabelFromMap('editConfAlertBtnCancel',helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('editConfAlertBtnCancel',helpMap,'Cancelar'),

                handler : function() {
                    _window.close();
                }
            }

            ]

	});
	if (record != null && record.get('cdIdUnico') != "") {
		form_edit.findById('cdIdUnico').setValue(record.get('cdIdUnico'));
	}
	_window.show();
	//recorda_store.load();
	//proc_store.load();
	//rol_store.load();
	
	
	//temp_store.load();
	reg_store.load();
	
	users_store.load({
		timeout: 50000
	});
	
	form_edit.form.load ({
			params: {cdIdUnico: record.get('cdIdUnico')},
			waitMsg : getLabelFromMap('400022', helpMap,'Leyendo datos...'),
			success: function(form, action){
							//console.log(action.result)
							var cdTemp_In = action.result.data.cdTemporalidad;
							cargarCombos();
							//alert(action.result.data.cdTemporalidad);
							if (cdTemp_In=='1')
 							{
								Ext.getCmp('nmDuracion').disable();
								Ext.getCmp('nmDiasAnt').enable();
								Ext.getCmp('nmDiasAnt').allowBlank=false;
							}else{
								Ext.getCmp('nmDuracion').enable();
								Ext.getCmp('nmDuracion').allowBlank=false;
								Ext.getCmp('nmDiasAnt').disable();
							}
               				
			} 	
	});
	function cargarCombos() {
	           recorda_store.load({
	              callback:function(){
               				cli_store.reload({
               						params: {cdUsuario: form_edit.findById('cmbUsuario').getValue()},
               						callback: function (r, options, success) {
               									if (success) {
               										form_edit.findById('cdPerson').setValue(form_edit.findById('HCliente').getValue());
               										
               										ramo_store.reload({
						               					params: {cdElemento: form_edit.findById('cdPerson').getValue()},
						               					callback: function(r, options, success){
						               							if (success){ 
						               								form_edit.findById('cdTipRam').setValue(form_edit.findById('HRamo').getValue());
										               				aseg_store.reload({
										               					params: {cdElemento: form_edit.findById('cdPerson').getValue()},
										               					callback: function(r, options, success){
										               								if (success) {
										               									form_edit.findById('cdUniEco').setValue(form_edit.findById('HAseguradora').getValue());
															               				prod_store.load({
															               					params: {
															               								cdElemento: form_edit.findById('cdPerson').getValue(),
															               								cdunieco: form_edit.form.findField('cdUniEco').getValue()
															               							},
															               					callback: function(r, options, success){
															               								if (success) {
															               									//form_edit.findById('cdProducto').setValue(form_edit.findById('HProducto').getValue());
															               									comboProducto.setValue(form_edit.findById('HProducto').getValue());
															               									//alert(comboProducto.getValue());
															               									form_edit.findById('cdProducto').setValue(form_edit.findById('HProducto').getValue());
															               									
															               								}else {
															               									prod_store.removeAll();
															               								}

															               					}
															               				});
										               								}else {
										               									aseg_store.removeAll();
										               								}
										               					}
										               				});
						               							}else{
						               								ramo_store.removeAll();
						               							}
						               					}
						               				});
               									}else {
               										cli_store.removeAll();
               									}
               									
               									
               						}
               				});	
               		     }		
               		});		
               					
	}
}