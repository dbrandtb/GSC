
var treeProcesosMasterPanel = new Ext.Panel({
	border : false,
	title : 'Elementos de Proceso',
	autoScroll : true,
	items : [{
		xtype : 'treepanel',
		id : 'treeProcesosMaster',
		width : 190,
		border : false,
		autoScroll : true,
		split : true,
		clearOnLoad : true,
		lines : false,
		
		loader : new Ext.tree.TreeLoader({
			dataUrl : '<s:url value="../configuradorpantallas/cargarMaster.action"/>'
		}),
		
		root : new Ext.tree.AsyncTreeNode({
					text : 'Menu Procesos Master',
					draggable : false, // TODO: REVISAR PARA HACER DRAG AND DROP DE LOS ELEMENTOS...
					id : 'wizard-master-item'
		}),
		rootVisible : false,
		listeners : {
			/*
			dblclick : function(n) {
				clavePantalla = n.attributes.id;

				getRegistroPantalla(clavePantalla);
				storeRegistroPantalla.on('load', function() {
					
					if (storeRegistroPantalla.getTotalCount() == null || storeRegistroPantalla.getTotalCount() == "") {
					
					} else {
						var recordP = storeRegistroPantalla.getAt(0);
						formPanelDatos.getForm().loadRecord(recordP);
						cdTipoMaster.setValue(recordP.get('htipoPantalla'));
						if (recordP.get('cdPantalla') == null	|| recordP.get('cdPantalla') == "") {
							comboTipoPantalla.enable();
							formPanelDatos.getForm().reset();
							Ext.getCmp('btnEliminar').disable();
							Ext.getCmp('btnNPantalla').disable();
							Ext.getCmp('btnVistaPrevia').disable();
						} else {
							comboTipoPantalla.disable();
							Ext.getCmp('btnEliminar').enable();
							Ext.getCmp('btnNPantalla').enable();
							Ext.getCmp('btnVistaPrevia').enable();

							storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
							storeTipoPantalla.load({

										callback : function(r, options, success) {
											if (!success) {
												//  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
												storeProducto.removeAll();
											}
											//   comboTipoPantalla.setValue(recordP.get('htipoPantalla'));
										}

									});
						}

						//SI dsArchivo TRAE ELEMENTOS, LOS CARGAMOS EN EL EDITOR
						if (dsArchivo.getValue() != "{}") {
							var configuracion = null;
							configuracion = Ext.decode(dsArchivo.getValue());
							main.setConfig({
										items : [configuracion]
							});
						}
						//SINO, DEJAMOS VACIO EL EDITOR
						else {
							main.setConfig( { items : []	} );
						}
					}//else totalcount

				});
			}
			
			*/
			
		}// end listeners

	}]

});

