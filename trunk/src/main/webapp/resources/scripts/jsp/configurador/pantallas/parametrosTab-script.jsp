<%@ include file="/taglibs.jsp"%>

/*************************************************************
 ** FieldSets del form de parametros (TAB 1)
 **************************************************************/
var combosFieldSet = new Ext.form.FieldSet({
	id : 'combosFieldSet',
	style : 'margin:5px',
	labelWidth : 70,
	bodyStyle : 'padding:5px',
	title : '',
	height : 130,
	hideBorders : true,
	border: false,
	bodyBorder: false,
	layout: 'column',
	autoHeight:true, autoWidth:true, anchor:'100%',
	items: [{
			layout: 'form',
			columnWidth: 0.8,
			defaults:{cls:'profile-field read-only', anchor:'100%'},
			items : [comboProceso, comboNivel, comboProducto, comboTipoSituacion,
			cdConjunto]
		//end items FieldSet
	}]
	}); // end FieldSet de combos

var datosFieldSet = new Ext.form.FieldSet({
			id : 'datosFieldSet',
			style : 'margin:5px',
			labelWidth : 100,
			bodyStyle : 'padding:5px',
			border: false,
			bodyBorder: false,
			title : '',
			//autoHeight:true,
			height : 130,
			hideBorders : true,
			layout: 'column',
			autoHeight: true, autoWidth: true, anchor: '100%',
			items: [{
				layout: 'form',
				columnWidth: .9,
				defaults: {cls: 'profile-field read-only', anchor: '100%'},
				items : [nombreConjunto, descripcion]
			}]
			//defaultType: 'textfield',
			

		}); // end FieldSet de datos

/*************************************************************
 ** FormPanel del TAB 1
 **************************************************************/
var formPanelParam = new Ext.form.FormPanel({
	id : 'formPanelParam',
	layout : 'column',
	border : false,
	columnWidth : .1,
	hideBorders : true,
	items : [{
		columnWidth : .4,
		layout : 'form',
		border : false,
		items : [combosFieldSet]
			//end items columna 1     
		}, {
		columnWidth : .5,
		layout : 'form',
		border : false,
		items : [datosFieldSet]
			//end items columna2
		}],//end items FormPanel
	buttons : [{
		text : TAB_1_BOTON_SALVAR,
		id : 'btnSalvarConjunto',
		tooltip: 'Guarda el conjunto de pantallas',
		handler : function() {
			if (formPanelParam.form.isValid()) {
				formPanelParam.form.submit({

					url : 'configuradorpantallas/salvarConjunto.action',

					waitMsg : TAB_1_BOTON_SALVAR_WAIT_MSG,
					failure : function(form, action) {
						Ext.MessageBox.alert(TAB_1_BOTON_SALVAR_FAILURE,
								action.result.actionErrors[0]/*TAB_1_BOTON_SALVAR_FAILURE_DESC*/);
					},
					success : function(form, action) {
						getRegistro();
						storeRegistro.on('load', function() {
							if (storeRegistro.getTotalCount() == null
									|| storeRegistro.getTotalCount() == "") {
							} else {

								var record = storeRegistro.getAt(0);
								formPanelParam.getForm().loadRecord(record);
								cdProceso.setValue(record.get('cdProceso'));
								cdProducto.setValue(record.get('cdProducto'));
								cdCliente.setValue(record.get('cdCliente'));
								cdProcesoActivacion.setValue(record.get('cdProceso'));
								cdProductoActivacion.setValue(record.get('cdProducto'));
								claveSituacion.setValue(record.get('dsTipoSituacion'));

								comboTipoPantalla.clearValue();
								storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
								storeTipoPantalla.load({
											callback : function(r, options,	success) {
												if (!success) {
													//  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
													storeProducto.removeAll();
												}
											}
										});

								Ext.getCmp('btnAceptarActivacion').enable();
								Ext.getCmp('btnCopiarConjunto').enable();
								Ext.getCmp('btnSalvarPantalla').enable();

								Ext.getCmp('btnEliminar').enable();
								Ext.getCmp('btnNPantalla').enable();
								Ext.getCmp('btnVistaPrevia').enable();
							}//else getTotalCount
						});

						var cdSetTmp = Ext.getCmp('cdConjuntoTest').getValue();
						Ext.MessageBox.alert(TAB_1_BOTON_SALVAR_SUCCESS, action.result.actionMessages[0]/*TAB_1_BOTON_SALVAR_SUCCESS_DESC + cdSetTmp*/);

						comboProceso.disable();
						comboNivel.disable();
						comboProducto.disable();
						comboTipoSituacion.disable();

						var treeN = Ext.getCmp('treeNavegacion');
						treeN.root.reload();

					}
				});
			} else {
				Ext.MessageBox.alert(TAB_1_BOTON_SALVAR_ERROR_VALIDA,
						TAB_1_BOTON_SALVAR_ERROR_VALIDA_DESC);
			}
		} //end handler    

	}, {
		text : TAB_1_BOTON_NUEVO_CONJUNTO,
		tooltip: 'Genera un nuevo conjunto de pantallas',
		handler : function() {

			formPanelParam.getForm().reset();
			formPanelDatos.form.reset();
			formPanelActivacion.form.reset();
			comboProceso.enable();
			comboNivel.enable();
			comboProducto.enable();
			comboTipoPantalla.enable();
			comboTipoSituacion.enable();
			formPanelParam.form.load({
												//Testiando...
						url : "<s:url action='nuevoConjunto' namespace='/confpantallas'/>", //'/confpantallas/nuevoConjunto.action',

						failure : function(form, action) {
							var treeN = Ext.getCmp('treeNavegacion');
							treeN.root.reload();
							
							var treeMasterTest = Ext.getCmp('treeProcesosMaster');							
							treeMasterTest.loader.baseParams['cdTipoMaster'] = '';
							treeMasterTest.loader.baseParams['cdProceso'] = '';
							treeMasterTest.loader.baseParams['tipoMaster'] = '';
							treeMasterTest.loader.baseParams['cdProducto'] = '';
							treeMasterTest.loader.baseParams['claveSituacion'] = '';
							treeMasterTest.root.reload();			
														
						}

					});
			comboTipoPantalla.clearValue();
			storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
			storeTipoPantalla.load({
						callback : function(r, options, success) {
							if (!success) {
								//  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
								storeProducto.removeAll();
							}
						}
					});
			Ext.getCmp('btnAceptarActivacion').disable();
			Ext.getCmp('btnSalvarPantalla').disable();
			Ext.getCmp('btnVistaPrevia').disable();
			Ext.getCmp('btnCopiarConjunto').disable();

			//BORRAR LOS COMPONENTES DEL EDITOR
			
			
			main.resetAll();

		}//end handler
	}, {
		text : TAB_1_BOTON_COPIAR_CONJUNTO,
		id : 'btnCopiarConjunto',
		tooltip: 'Copia el conjunto de pantallas a otro conjunto de pantallas',
		handler : function() {

			if (cdConjunto.getValue() == null || cdConjunto.getValue() == "") {
				Ext.MessageBox.alert(TAB_1_BOTON_COPIAR_CONJUNTO_ERROR,
						TAB_1_BOTON_COPIAR_CONJUNTO_ERROR_DESC);
			} else {
				var _url_copiar = "<s:url action='entrarCopiarConjunto' namespace='/confpantallas'/>";
				//window.open('entrarCopiarConjunto.action' + '?cdConjunto=' + cdConjunto.getValue(), '',	'height=600,width=800')
				window.open(_url_copiar + '?cdConjunto=' + cdConjunto.getValue(), '',	'height=600,width=800')

			}

		} //end handler     
	}]
		// end buttons FormPanel
});//end FormPanel

// formPanelParam.render('parametros');

