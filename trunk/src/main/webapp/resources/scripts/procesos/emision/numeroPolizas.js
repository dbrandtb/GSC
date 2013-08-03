
function numeracionPolizas(gridResultados, asignarNumeroA){
	
	var record = getSelectedRecord(gridResultados);
	
    var cm = new Ext.grid.ColumnModel([
        {
       
        header: "cdNumPol",
        dataIndex: 'cdNumPol',
        hidden : true
        },{
       
        header: "cdUniEco",
        dataIndex: 'cdUniEco',
        hidden : true
        },{
        header: getLabelFromMap("NroPolCmAseg", helpMap, "Aseguradora"),
        tooltip: getToolTipFromMap("NroPolCmAseg", helpMap, "Aseguradora"),
        dataIndex: 'dsUniEco',
        width: 160,
        sortable: true
        },{
        header: "cdRamo",
        dataIndex: 'cdRamo',
        hidden : true
        },{
        header: getLabelFromMap("NroPolCmPro", helpMap, "Producto"),
        tooltip: getToolTipFromMap("NroPolCmPro", helpMap, "Producto"),
        dataIndex: 'dsRamo',
        width: 160,
        sortable: true
        },{
        header: "cdElemento",
        dataIndex: 'cdElemento',
        hidden : true
        },{
        header: getLabelFromMap("NroPolCmCli", helpMap, "Cliente"),
        tooltip: getToolTipFromMap("NroPolCmCli", helpMap, "Cliente"),
        dataIndex: 'dsElemen',
        width: 160,
        sortable: true
        },{
        header: getLabelFromMap("NroPolCmProceso", helpMap, "Proceso"),
        tooltip: getToolTipFromMap("NroPolCmProceso", helpMap, "Proceso"),
        dataIndex: 'dsProceso',
        width: 160,
        sortable: true
        },{
        header: getLabelFromMap("NroPolCmTipPol", helpMap, "Tipo P&oacute;liza"),
        tooltip: getToolTipFromMap("NroPolCmTipPol", helpMap, "Tipo P&oacute;liza"),
        dataIndex: 'dsTipPol',
        width: 160,
        sortable: true
        },{
        header: getLabelFromMap("NroPolCmPrefi", helpMap, "Prefijo"),
        tooltip: getToolTipFromMap("NroPolCmPrefi", helpMap, "Prefijo"),
        dataIndex: 'dsSufPre',
        width: 160,
        sortable: true
        },{
        header: getLabelFromMap("NroPolCmFolIni", helpMap, "Folio Inicial"),
        tooltip: getToolTipFromMap("NroPolCmFolIni", helpMap, "Folio Inicial"),
        dataIndex: 'nmFolioIni',
        width: 160,
        sortable: true
        },{
        header: getLabelFromMap("NroPolCmFolFin", helpMap, "Folio Final"),
        tooltip: getToolTipFromMap("NroPolCmFolFin", helpMap, "Folio Final"),
        dataIndex: 'nmFolioFin',
        width: 160,
        sortable: true
        }
        
        ]);
        
       

        if(DES_UNIECO != ""){//&& DES_ELEMENTO && DES_RAMO){
        
     		_url = _ACTION_BUSCAR_NUMERO_POLIZAS+'?dsEelemen='+record.get('dselemen')+'&dsUniEco='+record.get('dsunieco')+'&dsRamo='+record.get('dsunieco');
   			 }else{
    			 _url = _ACTION_BUSCAR_NUMERO_POLIZAS;
    		}
    		 var url = _url;
        
//*************Store Grilla**************        
        
       
       var store;


                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_NUMERO_POLIZAS
                }),
				remoteSort : false,
                reader: new Ext.data.JsonReader({
            	root:'listaNumeroPolizaVO',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
	        {name: 'cdUniEco',  type: 'string',  mapping:'cdUniEco'},
	        {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
	        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},
	        {name: 'cdElemento',  type: 'string',  mapping:'cdElemento'},
	        {name: 'dsElemen',  type: 'string',  mapping:'dsElemen'},
	        {name: 'cdProceso',  type: 'string',  mapping:'cdProceso'},
	        {name: 'dsProceso',  type: 'string',  mapping:'dsProceso'},
	        {name: 'cdTipPol',  type: 'string',  mapping:'cdTipPol'},
	        {name: 'dsTipPol',  type: 'string',  mapping:'dsTipPol'},
	        {name: 'dsSufPre',  type: 'string',  mapping:'dsSufPre'},
	        {name: 'nmFolioIni',  type: 'string',  mapping:'nmFolioIni'},
	        {name: 'nmFolioFin',  type: 'string',  mapping:'nmFolioFin'},
	       	{name: 'cdNumPol',  type: 'string',  mapping:'cdNumPol'}       
			])
        });


//*****************Grilla de la pantalla********************

var	grid2= new Ext.grid.GridPanel({
        store:store,
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
		border:true,
        cm: cm,
        buttonAlign:'center',
		buttons:[
        		{
        		text:getLabelFromMap("nroPolBtnAdd", helpMap, 'Agregar'),
        		tooltip: getToolTipFromMap("nroPolBtnAdd", helpMap, "Agrega un nuevo n&uacute;mero de P&oacute;liza"),
        		id: 'cmdAgregarId',		        
            	handler:function(){
                	agregar();
                }
            	},{ 
            	text:getLabelFromMap("nroPolBtnEdit", helpMap, "Editar"),
            	id: 'cmdEditarId',
		        tooltip: getToolTipFromMap("nroPolBtnEdit", helpMap, "Edita un n&oacute;mero de P&oacute;liza"),
            	handler:function(){
                    if (getSelectedKey(grid2, "cdUniEco")  != "") {
                        editar(getSelectedRecord(grid2));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso') , getLabelFromMap('400000', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
            	}
            	},{
            	text: getLabelFromMap("nroPolBtnDel", helpMap, "Eliminar"),
            	id: 'cmdBorrarId',
		        tooltip: getToolTipFromMap("nroPolBtnDel", helpMap, "Elimina un n&uacute;mero de P&oacute;liza"),
                handler:function(){
                    if (getSelectedKey(grid2, "cdUniEco") != "" && getSelectedKey(grid2, "cdElemento") != "" && getSelectedKey(grid2, "cdRamo") != "") {
                    	borrar(getSelectedRecord(grid2));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso') , getLabelFromMap('400011', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
               },
            	{
               	text:getLabelFromMap("nroPolBtnExp", helpMap, "Exportar"),
               	id: 'cmdExportarId',
		        tooltip: getToolTipFromMap("nroPolBtnExp", helpMap, "Exporta un n&uacute;mero de P&oacute;liza"),
            	handler:function(){
                        var url = _ACTION_EXPORT_NUMERO_POLIZA + '?dsUniEco=' + desUniEco.getValue() + '&dsRamo='+ desRamo.getValue() + '&dsElemen=' + desElemen.getValue();
                	 	showExportDialog( url );
                        }
                },
                //Boton de regreso a Polizas Maestras
                {                 
					text : getLabelFromMap('cmdRegresar', helpMap, 'Regresar'),
					id: 'cmdRegresar',
					tooltip: getToolTipFromMap('cmdRegresar', helpMap, 'Regresa a la pantalla anterior'),
					handler : function() {
						//if(getSelectedKey(grid2, "cdUniEco")  != ""){
							//modificaPolizaMaestra();
							window.close();
						//}else{
							//Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso') , getLabelFromMap('400011', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
						//}
				}
			}
		],           	
    	width:500,
    	frame:true,
		height:320,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		stripeRows: true,
		collapsible: true,
		bbar: new Ext.PagingToolbar({
			pageSize:20,
			store: store,
			displayInfo: true
		})
});

function reloadGrid(){
	var _params = {
    		dsUniEco: Ext.getCmp('dsUniEco').getValue(),
    		dsRamo: Ext.getCmp('dsRamo').getValue(),
    		dsElemen: Ext.getCmp('dsElemen').getValue()
	};
	reloadComponentStore(grid2, _params, cbkReload);
}

function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}

//***************Campos del Formulario de busqueda****************
   
    
var desUniEco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap("dsUniEco", helpMap, "Aseguradora"),
        hasHelpIcon: getHelpIconFromMap("dsUniEco", helpMap),
        name:'dsUniEco',
        id: 'dsUniEco',
        value: record.get('dsunieco'),//este es el valor que envío en el registro que paso por parámetro
        readOnly: true,
        anchor: '95%',
        tooltip: getToolTipFromMap("dsUniEco", helpMap, "Aseguradora")
    });
    


var desRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap("dsRamo", helpMap, "Producto"),
        hasHelpIcon: getHelpIconFromMap("dsRamo", helpMap),
        name:'dsRamo',
        id: 'dsRamo',
        value: record.get('dsramo'),
        readOnly: true,
        anchor: '95%',
        tooltip: getToolTipFromMap("dsRamo", helpMap, "Producto")
    });
    
var desElemen = new Ext.form.TextField({
        fieldLabel: getLabelFromMap("dsElemen", helpMap, "Cliente"),
        hasHelpIcon: getHelpIconFromMap("dsElemen", helpMap, "Cliente"),
        name:'dsElemen',
        id: 'dsElemen',
        value: record.get('dselemen'),
        readOnly: true,
        anchor: '95%',
        tooltip: getToolTipFromMap("dsElemen", helpMap, "Cliente")
    });


/******************Formulario de busqueda*********************/   
 var incisosForm = new Ext.form.FormPanel({
        //el:'formBusqueda',
        id: 'formBusqueda',
		title: '<span style="color:black;font-size:12px;">' + getLabelFromMap('20', helpMap, 'Numeraci&oacute;n de P&oacute;lizas') + '</span>',        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_NUMERO_POLIZAS,
        width: 500,
        autoHeight: true,
        items: [{        		
        		layout:'form',
				border: false,
				items:[{				
        		bodyStyle:'background: white',
		        labelWidth: 100,
                layout: 'form',
				frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [
        		        
        		        {
        		        	html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.6,
                				layout: 'form',
		                		border: false,
        		        		items:[
        		        				desUniEco,
        		        				desRamo,
        		        				desElemen
        		        			  ]
								},{
								columnWidth:.4,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
                					id: 'cmdBuscarId',
                					disabled: true,
        							text: getLabelFromMap("nroPolBtnFind", helpMap, "Buscar"),
        							tooltip: getToolTipFromMap("nroPolBtnFind", helpMap, "Busca numeraci&oacute;n de p&oacute;lizas"),
        							handler: function() 
        							
        							{
									    store.baseParams= store.baseParams || {};
									    store.baseParams= { 'dsUniEco': desUniEco.getValue(),
									    								   'dsElemen': desElemen.getValue(),
									    								   'dsRamo': desRamo.getValue()
																	     };
										store.params = {start: 0, limit: itemsPerPage};
										store.proxy = new Ext.data.HttpProxy({
																url : _ACTION_BUSCAR_NUMERO_POLIZAS
															});
										store.load({
												callback: function(r, options, success){
													if(!success){
														Ext.MessageBox.alert('Buscar','No se Encontraron datos');
														grid2.store.removeAll();
														grid2.getBottomToolbar().updateInfo();
														}
													}
												});
												return;
										}
        							},{
        							id: 'cmdCancelarId',
        							disabled: true,
        							text: getLabelFromMap("nroPolBtnCancel", helpMap, "Cancelar"),
        							tooltip: getToolTipFromMap("nroPolBtnCancel", helpMap, "Cancelar"),
        							handler: function() {
        							    Ext.getCmp('dsUniEco').setValue("");
        								
	    								Ext.getCmp('dsRamo').setValue("");
	    								Ext.getCmp('dsElemen').setValue("");
        							}
        						}]
        						
        					}]
        				}]
				});
 		
    
/***************Ventana principal**********************************/
	var window = new Ext.Window({
		id: 'windowEditar',
		width: 520,
		height:555,
		minWidth: 300,
		modal:true,
		minHeight: 100,
		layout: 'fit',
		plain:true,
		bodyStyle:'padding:5px;',
		buttonAlign:'center',
		labelAlign:'right',
		items: [
        	incisosForm,
        	grid2
        ]
	});
	
	window.show();
     
	store.baseParams= store.baseParams || {};
	store.baseParams= {
		'dsUniEco': desUniEco.getValue(),
	    'dsElemen': desElemen.getValue(),
		'dsRamo': desRamo.getValue()
	};
	store.params = {start: 0, limit: itemsPerPage};
	
	store.proxy = new Ext.data.HttpProxy({
		url : _ACTION_BUSCAR_NUMERO_POLIZAS
	});
	store.load({
		callback: function(r, options, success){
			if(!success){
				Ext.MessageBox.alert('Buscar','No se Encontraron datos');
				grid2.store.removeAll();
				grid2.getBottomToolbar().updateInfo();
			}
		}
	});

        
/***************************************************************/
/***************************************************************/
/************************Funciones Utiles***********************/
/***************************************************************/
/***************************************************************/
        
	function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
	}

   
	function borrar(record) {
        if(record.get('cdUniEco') != "" && record.get('cdElemento') != "" && record.get('cdRamo') != "")
        {
            Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
            {
                if (btn == "yes")
                {
                	var _params = {
				              cdUniEco: record.get('cdUniEco'),
				              cdElemento: record.get('cdElemento'),
				              cdRamo: record.get('cdRamo')
                	};
                	execConnection(_ACTION_BORRAR_NUMERO_POLIZA, _params, cbkConnection);
               }
            })
        }else{
                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
    	}
	};
	
	function cbkConnection(_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap, 'Aviso'), _message, function(){reloadGrid();});
		}
	}

	function agregar() {

		var dsAseguradora = new Ext.data.Store ({
			proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_ASEGURADORAS_CLIENTE}),
			reader: new Ext.data.JsonReader({
				root: 'aseguradoraComboBox',
				id: 'cdUniEco',
				successProperty: '@success'
			}, [
				{name: 'cdUniEco', type: 'string', mapping: 'cdUniEco'},
				{name: 'dsUniEco', type: 'string', mapping: 'dsUniEco'} 
			]),
			remoteSort: true
		});

		var dsClientesCorp = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: _ACTION_OBTENER_CLIENTE_CORPO
           }),
           reader: new Ext.data.JsonReader({
				root: 'clientesCorp',
           		id: 'clientesCorps'
           },[
				{name: 'cdElemento', type: 'string',mapping:'cdElemento'},
				{name: 'dsElemen', type: 'string',mapping:'dsElemen'}
			])
		});

		var dsProductos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
				root: 'productosAseguradoraCliente',
				id: 'codigo'
            },[
				{name: 'codigo', type: 'string',mapping:'codigo'},
				{name: 'descripcion', type: 'string',mapping:'descripcion'}
			])
		});
    
    
     var dsFormaCalculoFolio = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_FORMA_CALCULO_FOLIO
            }),
            reader: new Ext.data.JsonReader({
            root: 'formaCalculoFolioNroIncisos',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });


   var dsIndicadorSP = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_INDICADOR_SP
            }),
            reader: new Ext.data.JsonReader({
            root: 'indicador_SP_NroIncisos',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    
    var dsProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_PROCESO_POLIZA
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboProcesoPoliza',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    var dsUsaCombinacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_USA_AGRUPACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'siNo',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    var dsTipoPoliza = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_TIPO_POLIZA
            }),
            reader: new Ext.data.JsonReader({
            root: 'listaTipoPoliza',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    var Elemento = new Ext.form.ComboBox({ 
    	            xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorp,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdElemento',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdElementoId', helpMap, "Cliente"),
                    tooltip: getToolTipFromMap('cdElementoId', helpMap, "Cliente"),
                    width: 250,
                    emptyText:getLabelFromMap('400016', helpMap, 'Selecione Cliente ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                    id:'cdElementoId',
                    onSelect: function (record) {

                        formPanel.findById(('cdPerson')).setValue(record.get("cdPerson"));
                        formPanel.findById(('cdRamoId')).setValue('');
                        dsProductos.removeAll();
	                    dsProductos.load({
	                                    	params: {cdElemento: record.get("cdElemento") ,cdunieco: formPanel.findById(('cdUniEcoId')).getValue()},
	                         	            waitMsg: getLabelFromMap('400017', helpMap, 'Espere por favor....')
	                            		 });
	                    formPanel.findById(('cdUniEcoId')).setValue('');
                        dsAseguradora.removeAll();
                        dsAseguradora.load({
	                                    	params: {cdElemento: record.get("cdElemento")},
	                         	            waitMsg: getLabelFromMap('400017', helpMap, 'Espere por favor....')
	                            		 });
                        this.collapse();
                        this.setValue(record.get("cdElemento"));

                        }
    });
    
    var Aseguradora = new Ext.form.ComboBox({ 
                   xtype: 'combo', 
                    labelWidth: 70, 
                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                store: dsAseguradora,
	                displayField:'dsUniEco', 
	                valueField:'cdUniEco', 
	                hiddenName: 'cdUniEco', 
	                typeAhead: true,
	                mode: 'local', 
	                triggerAction: 'all', 
	                fieldLabel: getLabelFromMap('cdUniEcoId', helpMap, "Aseguradora"), 
                    tooltip: getToolTipFromMap('cdUniEcoId', helpMap, "Aseguradora"),
	                width: 250, 
	                emptyText: getLabelFromMap('400018', helpMap, 'Selecione Aseguradora...'),
	                selectOnFocus:true, 
	                forceSelection:true,
	                allowBlank : false,
	                blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                id: 'cdUniEcoId',// name: 'cdUniEco'
	                onSelect: function (record) {
	                            				
	                            				dsProductos.removeAll();
	                            				dsProductos.load({
	                            						params: {cdElemento: formPanel.findById(('cdElementoId')).getValue() ,cdunieco: record.get('cdUniEco')}
	                            					});
	                            				formPanel.findById("cdRamoId").setValue('');
	                            				this.setValue(record.get('cdUniEco'));
	                            				this.collapse();	
	                            		        }
    });
    
    var Producto = new Ext.form.ComboBox({
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdRamoId', helpMap, "Producto"),
                    tooltip: getToolTipFromMap('cdRamoId', helpMap, "Producto"),
                    width: 250,
                    emptyText: getLabelFromMap('cdRamoId', helpMap, 'Selecione Producto...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                    id:'cdRamoId'
    });
    
    var formulaPanelAgregar=new Ext.Panel({
    	layout:'column',
		border: false,
		width : 500,
		//column items:
		items:[
			{
				columnWidth:.7,
               	layout: 'form',
               	border: false,
				items:[
					{
                		xtype: 'textfield', 
                		fieldLabel: getLabelFromMap('dsCalculo', helpMap, "F&oacute;rmula"), 
                		tooltip: getToolTipFromMap('dsCalculo', helpMap, 'F&oacute;rmula de Calculo de Folio'), 
		        		hasHelpIcon:getHelpIconFromMap('dsCalculo',helpMap),
			    		Ayuda:getHelpTextFromMap('dsCalculo',helpMap),
                		name: 'dsCalculo', 
                		id: 'dsCalculo',
                		blankText: 'Debe elegir una Expresi&oacute;n',
                		width: 50,
						disabled: true,
						readOnly: true
                	}
			  	]
			  },{
			  	columnWidth:.3,
               	layout: 'form',
               	border: false,
               	//Componentes agregados para las Expresiones
				items:[
					{
			  			xtype:'button',
                		text: 'Expresi&oacute;n',
                		id: 'btnExpresion',
                		name: 'btnExpresion',
                		buttonAlign: "left",
                		handler: function() {
                			var connect = new Ext.data.Connection();
							connect.request ({
								url:'atributosVariables/ObtenerCodigoExpresion.action',
								callback: function (options, success, response) {
									try{
										//alert(Ext.util.JSON.decode(response.responseText).codigoExpresion);
										Ext.getCmp('hidden-codigo-expresion-atributos-variables').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
										ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
										Ext.getCmp('dsCalculo').setValue( Ext.getCmp('hidden-codigo-expresion-atributos-variables').getValue() );
									}catch(e){
									}
								}
							});
						}
                	},{
						xtype:'hidden',
						id:'hidden-codigo-expresion-session',
						name:'codigoExpresionSession', 
						value:'EXPRESION_ATRIBUTOS_VARIABLES'
					},{
                		xtype:'hidden',                	
                		id:'hidden-codigo-expresion-atributos-variables',
		           		name: "codigoExpresion"
                	}
                ]
			}
		]//end column items
	});
    
    
	//	se define el formulario
	var formPanel = new Ext.FormPanel ({
            labelWidth : 100,
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            labelAlign:'right',
            defaults : {width : 250 },
            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },
                //{
					Elemento
                //}
					,
                //{
 					Aseguradora,

//                {
					Producto
//                } 
 					,
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsIndicadorSP,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indSufPre',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSufPreId', helpMap, "Indicador (S/P)"),
                    tooltip: getToolTipFromMap('indSufPreId', helpMap, "Indicador (S/P)"),
                    width: 250,
                    emptyText: getLabelFromMap('indSufPreId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'indSufPreId',
                    onSelect: function (record) {
						if (record.get("id")=="N"){
							formPanel.findById("dsSufPre").setDisabled(true);
							formPanel.findById("dsSufPre").allowBlank = true;
							formPanel.findById("dsSufPre").setValue('');
						}
						if (record.get("id")=="S" || record.get("id")=="P"){
							formPanel.findById("dsSufPre").setDisabled(false);
							formPanel.findById("dsSufPre").allowBlank = false;
						}
						this.setValue(record.get('id'));
						this.collapse();
						}
                } ,
                {xtype: 'textfield', fieldLabel: getLabelFromMap('dsSufPre', helpMap, "Valor"), tooltip: getToolTipFromMap('dsSufPre', helpMap, 'Valor'), name: 'dsSufPre', id: 'dsSufPre'},
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsProceso,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdProceso',//procesoPoliza',
                    typeAhead: true,
                    allowBlank: false,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSufProcesoId', helpMap, "Proceso"),
                    tooltip: getToolTipFromMap('indSufProcesoId', helpMap, "Proceso"),
                    width: 250,
                    emptyText: getLabelFromMap('cdProcesoId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    id:'cdProcesoId',
                    onSelect: function (record) {
						this.setValue(record.get('id'));
						this.collapse();
					}
                } ,
                
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsUsaCombinacion,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'swAgrupa',
                    typeAhead: true,
                    allowBlank: false,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSufUsaCombinaId', helpMap, "¿Usa Agrupaci&oacute;n?"),
                    tooltip: getToolTipFromMap('indSufUsaCombinaId', helpMap, "¿Usa Agrupaci&oacute;n?"),
                    width: 250,
                    emptyText: getLabelFromMap('swAgrupaId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'swAgrupaId',
                    onSelect: function (record) {
						this.setValue(record.get('id'));
						this.collapse();
					}
                } ,
                
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsTipoPoliza,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdTipPol',
                    typeAhead: true,
                    allowBlank: false,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSufTipoPolizaId', helpMap, "Tipo Poliza"),
                    tooltip: getToolTipFromMap('indSufTipoPolizaId', helpMap, "Tipo Poliza"),
                    width: 250,
                    emptyText: getLabelFromMap('cdTipPolId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    id:'cdTipPolId',
                    onSelect: function (record) {
						this.setValue(record.get('id'));
						this.collapse();
					}
                } ,
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsFormaCalculoFolio,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indCalc',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indCalcId', helpMap, "Forma C&aacute;lculo Folio"),
                    tooltip: getToolTipFromMap('indCalcId', helpMap, 'Forma C&aacute;lculo Folio'),
                    width: 250,
                    emptyText: getLabelFromMap('400020', helpMap, 'Selecione Forma Calculo Folio...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    labelSeparator:'',
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                    id:'indCalcId',
					onSelect: function (record) {
	                            				
	                            				if (record.get("id")=="1"){
	                            				
	                            				            formPanel.findById("nmFolioIni").setDisabled(false);
	                            							formPanel.findById("nmFolioFin").setDisabled(false);
	                            							
	                            							formPanel.findById("nmFolioIni").allowBlank =  false;
	                            							formPanel.findById("nmFolioFin").allowBlank = false;
	                            							
	                            							
	                            							formPanel.findById("dsCalculo").setValue('');
	                            							formPanel.findById("dsCalculo").allowBlank = true;
	                            							formPanel.findById("dsCalculo").setDisabled(true);
	                            						    
	                            				}
	                            				
	                            				if (record.get("id")=="2"){
	                            				
	                            				            formPanel.findById("nmFolioIni").allowBlank = true;
	                            							formPanel.findById("nmFolioFin").allowBlank = true;
	                            							
	                            							formPanel.findById("nmFolioIni").setValue('');
	                            							formPanel.findById("nmFolioFin").setValue('');
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(true);
	                            							formPanel.findById("nmFolioFin").setDisabled(true);
	                            							
	                            							
	                            								                            							
	                            							
	                            							formPanel.findById("dsCalculo").allowBlank = false;
	                            							formPanel.findById("dsCalculo").setDisabled(false);
	                            							
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();	
	                            		        }
                } ,
                formulaPanelAgregar,
				{
					xtype: 'textfield', 
					fieldLabel: getLabelFromMap('nmFolioIni', helpMap, "Folio Inicial"), 
					tooltip: getToolTipFromMap('nmFolioIni', helpMap, 'Folio Inicial'), 
					name: 'nmFolioIni', 
					id: 'nmFolioIni',
					listeners: {
						    'change': function(){
						    	 validarFolios();
						         actualizaFolioActual();
						    }
						  }
				},
				{
					xtype: 'textfield', 
					fieldLabel: getLabelFromMap('nmFolioFin', helpMap, "Folio Final"), 
					tooltip: getToolTipFromMap('nmFolioFin', helpMap, 'Folio Final'), 
					name: 'nmFolioFin', 
					id: 'nmFolioFin',
					listeners: {
						    'change': function(){
						         validarFolios();
						    }
						  }
				},
			    {
			    	xtype: 'textfield', 
			    	fieldLabel: getLabelFromMap('nmFolioAct', helpMap, "Folio Actual"), 
			    	tooltip: getToolTipFromMap('nmFolioAct', helpMap, 'Folio Actual'), 
			    	name: 'nmFolioAct', 
			    	readOnly: true,  
			    	id: 'nmFolioAct'}
            ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: getLabelFromMap('90', helpMap, 'Agregar N&uacute;mero de P&oacute;liza'),
        id: 'windowAgregar',
        width: 500,
        height:450,
        modal: true,
        minWidth: 300,
        minHeight: 100,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: 
        	formPanel,
            buttons : [ {
                text : getLabelFromMap('cmdGuardar', helpMap, 'Guardar'),
                id: 'cmdGuardar',
                tooltip: getToolTipFromMap('cmdGuardar', helpMap, 'Guarda un n&uacute;mero de P&oacute;liza'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_NUMERO_POLIZA,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap, 'Error'), action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitTitle: getLabelFromMap('400021', helpMap, 'Espere'),
                            waitMsg : getLabelFromMap('400022', helpMap, getLabelFromMap('', helpMap, 'Guardando Actualizaci&oacute;n de Datos ...'))
                        });
                     } else {
                        Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), getLabelFromMap('400013', helpMap, 'Complete la informaci&oacute;n requerida'));
                     }
                }
            },{
                text : getLabelFromMap('cmdGuardarAgregar', helpMap, 'Guardar y Agregar'),
                id: 'cmdGuardarAgregar',
                tooltip: getToolTipFromMap('cmdGuardarAgregar', helpMap, 'Guarda y Agrega un nuevo registro'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_NUMERO_POLIZA,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                    
                                    dsClientesCorp.load();
                                    dsProductos.removeAll();
                                    dsAseguradora.removeAll();
								    dsFormaCalculoFolio.load();
								    dsIndicadorSP.load();
								    dsProceso.load();
								    dsUsaCombinacion.load();
								    dsTipoPoliza.load();
								    
								    formPanel.findById("dsCalculo").allowBlank = true;
								    formPanel.findById("nmFolioFin").allowBlank = true;
								    formPanel.findById("nmFolioIni").allowBlank = true;
								    formPanel.findById("dsSufPre").allowBlank = true;
								    
								    formPanel.findById("dsCalculo").setDisabled(false);
								    formPanel.findById("nmFolioFin").setDisabled(false);
								    formPanel.findById("nmFolioIni").setDisabled(false);
								    formPanel.findById("dsSufPre").setDisabled(false);
								    
								    
								    formPanel.findById("cdElementoId").setValue('');
								    formPanel.findById("cdUniEcoId").setValue('');
								    formPanel.findById("cdRamoId").setValue('');
								    formPanel.findById("indSufPreId").setValue('');
								    formPanel.findById("indCalcId").setValue('');
								    formPanel.findById("cdProcesoId").setValue('');
								    formPanel.findById("swAgrupaId").setValue('');     
								    formPanel.findBuId("cdTipPolId").setValue('');
								    
								    formPanel.findById("cdPerson").setValue('');
								    formPanel.findById("dsCalculo").setValue('');
								    formPanel.findById("dsSufPre").setValue('');
								    formPanel.findById("nmFolioIni").setValue('');
								    formPanel.findById("nmFolioFin").setValue('');      
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap, 'Error'), action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap, 'Guardando Actualizaci&oacute;n de Datos ...')
                        });
                     } else {
                        Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), getLabelFromMap('400013', helpMap, 'Por favor complete la informaci&oacute;n requerida'));
                     }
                }
            },
             {
                 text : getLabelFromMap('cmdRegresar', helpMap, 'Regresar'),
                 tooltip: getToolTipFromMap('cmdRegresar', helpMap, 'Regresa a la pantalla anterior'),
                 id: 'cmdRegresar',
                 handler : function() {
                 window.close();
                    }
            }
            ]
    	});
    	
    	dsClientesCorp.load({
    		callback: function(r,options,success){
				var numregistros = dsClientesCorp.getCount();
				var cdelemento = record.get('cdelemento');
				cdelemento += "";
				for ( i = 0; i < numregistros; i++ )
				{
					var registro = dsClientesCorp.getAt(i);
					if ( registro.get('cdElemento') != null && registro.get('cdElemento') == cdelemento )
					{
						formPanel.findById(('cdPerson')).setValue(record.get("cdPerson"));	
						Ext.getCmp('cdElementoId').setValue(registro.get('cdElemento'));
	                    formPanel.findById(('cdUniEcoId')).setValue('');
                        dsAseguradora.removeAll();
                        dsAseguradora.load({
	                                    	params: {cdElemento: record.get("cdelemento")},
	                         	            waitMsg: getLabelFromMap('400017', helpMap, 'Espere por favor....'),
											callback: function(r,options,success){
												var numregistros = dsAseguradora.getCount();
												var cdaseguradora = record.get("cdcia");
												cdaseguradora += "";
												for ( i = 0; i < numregistros; i++)
												{
													var registro = dsAseguradora.getAt(i);
													if ( registro.get('cdUniEco') != null && registro.get('cdUniEco') == cdaseguradora )
													{
														Ext.getCmp('cdUniEcoId').setValue(registro.get("cdUniEco"));
														break;
													}
												}
											}
	                            		 });
						break;
					}
				}
    		}
    	});

    	dsProductos.load({
    		callback: function(r,options,success){
				var numregistros = dsProductos.getCount();
				var cdramo = record.get("cdramo");
				cdramo += "";
				for ( i = 0; i < numregistros; i++)
				{
					var registro = dsProductos.getAt(i);
					if ( registro.get('codigo') != null && registro.get('codigo') == cdramo )
					{
						Ext.getCmp('cdRamoId').setValue(registro.get('codigo'));
						break;
					}
				}
    		}
    	});

   function actualizaFolioActual(){
   		   formPanel.findById("nmFolioAct").setValue('');	
   		   formPanel.findById("nmFolioAct").setValue(formPanel.findById("nmFolioIni").getValue());
   };
   
   function validarFolios(){
   		 
   		if (formPanel.findById("nmFolioIni").getValue()!="" && formPanel.findById("nmFolioFin").getValue()!="") {
   			if (eval(formPanel.findById("nmFolioIni").getValue()) >= eval(formPanel.findById("nmFolioFin").getValue())){
   			       Ext.Msg.alert(getLabelFromMap('400023', helpMap, 'Informacion'), getLabelFromMap('400024', helpMap, 'El folio de Inicio debe ser menor que el folio de Fin.'));
   			       var folFin = eval(formPanel.findById("nmFolioIni").getValue()) + 1;
   			       formPanel.findById("nmFolioFin").setValue(folFin);	
   			}
   		}
   };

	// aqui iba dsClientesCorp
    dsFormaCalculoFolio.load();
    dsIndicadorSP.load();
    dsProceso.load();
    dsUsaCombinacion.load();
    dsTipoPoliza.load();
   

    window.show();

};

        // Funcion de Agregar Desglose de Polizas
	function editar(record) {


    var dsProductos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosAseguradoraCliente',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ]),
        remoteSort: true
    });
    
    
     var dsFormaCalculoFolio = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_FORMA_CALCULO_FOLIO
            }),
            reader: new Ext.data.JsonReader({
            root: 'formaCalculoFolioNroIncisos',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
           
        ]),
        remoteSort: true
    });


    var dsIndicadorSP = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_INDICADOR_SP
            }),
            reader: new Ext.data.JsonReader({
            root: 'indicador_SP_NroIncisos',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
        ]),
        remoteSort: true
    });
    
     var dsProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_PROCESO_POLIZA
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboProcesoPoliza',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    var dsUsaCombinacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_USA_AGRUPACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'siNo',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    var dsTipoPoliza = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_TIPO_POLIZA
            }),
            reader: new Ext.data.JsonReader({
            root: 'listaTipoPoliza',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'codigo'},
            {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {

            //indica el arreglo a leer
            root : 'MNumeroPolizasManagerList',

            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',

            //indica el resultado de la respuesta de la action
            successProperty : 'success'

        }, [ {
               name : 'cdElementoId',mapping : 'cdElemento',type : 'string'
        }, {
               name : 'cdPerson',type : 'string',mapping : 'cdPerson'
        },{
               name : 'dsElemenId', mapping : 'dsElemen', type : 'string'
        },{
               name :'dsUniEco', mapping : 'dsUniEco', type : 'string'
        },{
               name : 'cdRamo1',type : 'string',mapping : 'cdRamo'
        },{
               name : 'dsRamo',type : 'string',mapping : 'dsRamo'
        },{
               name : 'dsElemen',type : 'string',mapping : 'dsElemen'
        },{
               name : 'cdUniEco',type : 'string',mapping : 'cdUniEco'
        },{
               name : 'dsUniEcoId',type : 'string',mapping : 'dsUniEco'
        },{
               name : 'dsCalculo',type : 'string',mapping : 'dsCalculo'
        },{
               name : 'indSufPre',type : 'string',mapping : 'indSufPre'
        },{
               name : 'dsIndSufPre',type : 'string',mapping : 'dsIndSufPre'
        },{
               name : 'indCalc',type : 'string',mapping : 'indCalc'
        },{
               name : 'dsCalculo',type : 'string',mapping : 'dsCalculo'
        },{
               name : 'nmFolioFin',type : 'string',mapping : 'nmFolioFin'
        },{
               name : 'nmFolioIni',type : 'string',mapping : 'nmFolioIni'
        },{
               name : 'dsSufPre',type : 'string',mapping : 'dsSufPre'
        },{
               name : 'nmFolioAct',type : 'string',mapping : 'nmFolioAct'
        },{
               name : 'dsAgrupa',type : 'string',mapping : 'dsAgrupa'
        },{
               name : 'swAgrupa',type : 'string',mapping : 'swAgrupa'
        },{
               name : 'swAgrupa',type : 'string',mapping : 'swAgrupa'
        },{
               name : 'cdProceso',type : 'string',mapping : 'cdProceso'
        },{
               name : 'dsProceso',type : 'string',mapping : 'dsProceso'
        },{
               name : 'dsTipPol',type : 'string',mapping : 'dsTipPol'
        },{
               name : 'cdNumPol',type : 'string',mapping : 'cdNumPol'
        },{
               name : 'cdTipPol',type : 'string',mapping : 'cdTipPol'
        }
        
        
        
       ]);
    
    
   
//se define el formulario
var formPanel = new Ext.FormPanel ({

           labelWidth : 100,
           labelAlign:'right',
           
			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_NUMERO_POLIZA,

            baseParams : {cdUniEco: record.get("cdUniEco"),
                          cdElemento: record.get("cdElemento"),
                          cdRamo: record.get("cdRamo")
                         },

            bodyStyle : 'padding:5px 5px 0',

          
			//se setea el reader que va a usar el form cuando se cargue
            reader : _jsonFormReader,

            //reader : test(),
            defaults : {

                width : 200

            },

            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },{
                    xtype: 'textfield',
                    name : 'dsElemenId',
                    fieldLabel: getLabelFromMap('dsElemen', helpMap, "Cliente"),
                    tooltip: getToolTipFromMap('dsElemen', helpMap, 'Cliente'),
                    disabled:true,
                    id: 'dsElemenId'
                },{
                    xtype: 'textfield',
                    name : 'dsUniEcoId',
                    fieldLabel: getLabelFromMap('dsUniEco', helpMap, "Aseguradora"),
                    tooltip: getToolTipFromMap('dsUniEco', helpMap, 'Aseguradora'),
                    disabled:true,
                    id: 'dsUniEcoId'
                },
                //Se borro definicion y configuracion de combo Clientes y combo Aseguradora
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
                    displayField: getLabelFromMap('cdRamoId', helpMap, 'descripcion'),
                    tooltip: getToolTipFromMap('cdRamoId', helpMap, 'descripcion'),
                    valueField:'codigo',
                    hiddenName: 'cdRamo1',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Producto",
                    width: 200,
                    emptyText: getLabelFromMap('400019', helpMap, 'Selecione Producto...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                    id:'cdRamoId'
                } ,
                {
                    xtype: 'combo',
                     tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsIndicadorSP,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indSufPre',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSufPreId', helpMap, "Indicador (S/P)"),
                    tooltip: getToolTipFromMap('indSufPreId', helpMap, 'Indicador (S/P)'),
                    width: 200,
                    emptyText: getLabelFromMap('indSufPreId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    id:'indSufPreId',
                    onSelect: function (record) {
	                            				
	                            				if (record.get("id")=="N"){
	                            				          
	                            							formPanel.findById("dsSufPre").setDisabled(true);
	                            							formPanel.findById("dsSufPre").allowBlank = true;
	                            							formPanel.findById("dsSufPre").setValue('');
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="S" || record.get("id")=="P"){
	                            				
	                            							formPanel.findById("dsSufPre").setDisabled(false);
	                            							formPanel.findById("dsSufPre").allowBlank = false;
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
                
                {xtype: 'textfield',
                 fieldLabel: getLabelFromMap('nroPolEditVal', helpMap, "Valor"),
                 tooltip: getToolTipFromMap('nroPolEditVal', helpMap, 'Valor'),
                  name: 'dsSufPre', id: 'dsSufPre'},
                
                {
                    xtype: 'combo',
                     tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsFormaCalculoFolio,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indCalc',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cmbIndCalcId', helpMap, "Forma C&aacute;lculo Folio"),
                    tooltip: getToolTipFromMap('cmbIndCalcId', helpMap, ''),
                    width: 200,
                    emptyText: getLabelFromMap('cmbIndCalcId', helpMap, 'Selecione Forma C&aacute;lculo Folio...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                    id:'indCalcId',
                    onSelect: function (record) {
	                            				
	                            				if (record.get("id")=="1"){
	                            						    
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(false);
	                            							formPanel.findById("nmFolioFin").setDisabled(false);
	                            							
	                            							formPanel.findById("nmFolioIni").allowBlank =  false;
	                            							formPanel.findById("nmFolioFin").allowBlank = false;
	                            							
	                            							formPanel.findById("dsCalculo").setValue('');
	                            							formPanel.findById("dsCalculo").allowBlank = true;
	                            							formPanel.findById("dsCalculo").setDisabled(true);
	                            							
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="2"){
	                            				
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(true);
	                            							formPanel.findById("nmFolioFin").setDisabled(true);
	                            							
	                            							formPanel.findById("nmFolioIni").setValue('');
	                            							formPanel.findById("nmFolioFin").setValue('');
	                            							
	                            							
	                            							formPanel.findById("nmFolioIni").allowBlank = true;
	                            							formPanel.findById("nmFolioFin").allowBlank = true;
	                            							
	                            							formPanel.findById("dsCalculo").allowBlank = false;
	                            							formPanel.findById("dsCalculo").setDisabled(false);
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();	
	                            		        }
                } ,
                
                
                {xtype: 'numberfield', fieldLabel: getLabelFromMap('dsCalculo', helpMap, "F&oacute;rmula"), tooltip: getToolTipFromMap('dsCalculo', helpMap, 'Formula de Calculo de Folio'), name: 'dsCalculo', heigth:150, id: 'dsCalculo'},
				
				/*{xtype: 'textarea', fieldLabel: getLabelFromMap('txtADsCalculo', helpMap, "F&oacute;rmula de C&aacute;lculo de Folio"), tooltip: getToolTipFromMap('txtADsCalculo', helpMap, 'Formula de Calculo de Folio'),
				name: 'dsCalculo',
				blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
				heigth:150, id: 'dsCalculo'},*/	

                /*Combo Proceso Polizas*/
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsProceso,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdProceso',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSufProcesoId', helpMap, "Proceso"),
                    tooltip: getToolTipFromMap('indSufProcesoId', helpMap, "Proceso"),
                    width: 250,
                    emptyText: getLabelFromMap('cdProcesoId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'cdProcesoId',
                    onSelect: function (record) {
	                            				
	                            				
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
                
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsUsaCombinacion,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'swAgrupa',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSufUsaCombinaId', helpMap, "¿Usa Agrupaci&oacute;n?"),
                    tooltip: getToolTipFromMap('indSufUsaCombinaId', helpMap, "¿Usa Agrupaci&oacute;n?"),
                    width: 250,
                    emptyText: getLabelFromMap('procesoPolizaId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'usaCombinacionId',
                    onSelect: function (record) {
	                            				
	                            				                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
                
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsTipoPoliza,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdTipPol',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSufTipoPolizaId', helpMap, "Tipo Poliza"),
                    tooltip: getToolTipFromMap('indSufTipoPolizaId', helpMap, "Tipo Poliza"),
                    width: 250,
                    emptyText: getLabelFromMap('procesoPolizaId', helpMap, 'Selecione Tipo P&oacute;liza...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'tipoPolizaId',
                    onSelect: function (record) {
	                            				
	                            					                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
                

				{xtype: 'textfield', fieldLabel: getLabelFromMap('txtnmFolioIni', helpMap, "Folio Inicial"), tooltip: getToolTipFromMap('txtnmFolioIni', helpMap, 'Folio Inicial'), name: 'nmFolioIni', id: 'nmFolioIni',
				listeners: {
						    'change': function(){
						    	 validarFolios();
						         actualizaFolioActual();
						    }
						  }
				},
				
				{xtype: 'textfield', fieldLabel: getLabelFromMap('nmFolioFin', helpMap, "Folio Final"), tooltip: getToolTipFromMap('nmFolioFin', helpMap, 'Folio Final'), name: 'nmFolioFin', id: 'nmFolioFin',
				listeners: {
						    'change': function(){
						         validarFolios();
						    }
						  }
				},
				
				{xtype: 'textfield', fieldLabel: getLabelFromMap('nmFolioAct', helpMap, "Folio Actual"), tooltip: getToolTipFromMap('nmFolioAct', helpMap, 'Folio Actual'), name: 'nmFolioAct', readOnly: true,  id: 'nmFolioAct'}

            ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: getLabelFromMap('91', helpMap, 'Editar N&uacute;mero de P&oacute;liza'),
        id: 'windowEditar',
        width: 500,
        height:450,
        minWidth: 300,
        minHeight: 100,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        labelAlign:'right',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
                text : getLabelFromMap('cmdGuardar', helpMap, 'Guardar'),
                tooltip: getToolTipFromMap('cmdGuardar', helpMap, 'Guarda la actualizaci&oacute;n'),
                id: 'cmdGuardar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_NUMERO_POLIZA,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap, 'Error'), action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap, 'Guardando Actualizacion de Datos ...')
                        });
                     } else {
                        Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), getLabelFromMap('400013', helpMap, 'Complete la informaci&oacute;n requerida'));
                     }
                }
            },{
                text : getLabelFromMap('cmdGuardarAgregar', helpMap, 'Guardar y Agregar'),
                tooltip: getToolTipFromMap('cmdGuardarAgregar', helpMap, 'Guarda y Agrega un n&uacute;mero de P&oacute;liza'),
                id: 'cmdGuardarAgregar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_NUMERO_POLIZA,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap, 'Error'), action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap, 'Guardando Actualizaci&oacute;n de Datos ...')
                        });
                     } else {
                        Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), getLabelFromMap('400013', helpMap, 'Complete la informaci&oacute;n requerida'));
                     }
                }
            },
             {
                 text : getLabelFromMap('cmdRegresar', helpMap, 'Regresar'),
                 id: 'cmdRegresar',
                 tooltip: getToolTipFromMap('cmdRegresar', helpMap, 'Regresa a la pantalla anterior'),
                 handler : function() {
                 window.close();
                    }
            }]
    	});



	 function actualizaFolioActual(){
   		   formPanel.findById("nmFolioAct").setValue('');	
   		   formPanel.findById("nmFolioAct").setValue(formPanel.findById("nmFolioIni").getValue());
   };
   
   function validarFolios(){
   		 
   		if (formPanel.findById("nmFolioIni").getValue()!="" && formPanel.findById("nmFolioFin").getValue()!="") {
   			if (eval(formPanel.findById("nmFolioIni").getValue()) >= eval(formPanel.findById("nmFolioFin").getValue())){
   			       Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400024', helpMap, 'El folio de Inicio debe ser menor que el folio de Fin.'));
   			       var folFin = eval(formPanel.findById("nmFolioIni").getValue()) + 1;
   			       formPanel.findById("nmFolioFin").setValue(folFin);	
   			}
   		}
   };
   
   
    dsProductos.load({
	                  params: {cdElemento: record.get("cdElemento") ,cdunieco: record.get('cdUniEco')}
	                 });
 
    dsFormaCalculoFolio.load();
  
    dsIndicadorSP.load();
    dsProceso.load();
    dsUsaCombinacion.load();
    dsTipoPoliza.load();

  
    formPanel.findById("cdRamoId").setValue(record.get('cdRamo'));
    
    window.show();
     //se carga el formulario con los datos de la estructura a editar
        formPanel.form.load({
    				waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),                                                            
                    success:function(){
                                     if (formPanel.findById("indCalcId").getValue()=="1"){
	                            						    
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(false);
	                            							formPanel.findById("nmFolioFin").setDisabled(false);
	                            							
	                            							formPanel.findById("nmFolioIni").allowBlank =  false;
	                            							formPanel.findById("nmFolioFin").allowBlank = false;
	                            							
	                            							formPanel.findById("dsCalculo").setValue('');
	                            							formPanel.findById("dsCalculo").allowBlank = true;
	                            							formPanel.findById("dsCalculo").setDisabled(true);
	                            				}
	                            				
	                            	if (formPanel.findById("indCalcId").getValue()=="2"){
	                            				
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(true);
	                            							formPanel.findById("nmFolioFin").setDisabled(true);
	                            							
	                            							formPanel.findById("nmFolioIni").setValue('');
	                            							formPanel.findById("nmFolioFin").setValue('');
	                            							
	                            							
	                            							formPanel.findById("nmFolioIni").allowBlank = true;
	                            							formPanel.findById("nmFolioFin").allowBlank = true;
	                            							
	                            							formPanel.findById("dsCalculo").allowBlank = false;
	                            							formPanel.findById("dsCalculo").setDisabled(false);
	                            							
	                            	}
	                            	
                        }
                });


};

	function volver(record)	{
		window.location=_ACTION_POLIZAS_MAESTRAS+"?cdNumPol ="+ 121/*Arreglar*/+"& dsElemen ="+record.get('dsElemen')+"& dsUnieco="+record.get('dsUniEco')+"& dsRamo="+record.get('dsRamo')+"& cdPolMtra="+ CODIGO_POLMTRA;//record.get('cdNumPol');
	};

	function feInicio(val) {
		try{
			var fecha = new Date();
			fecha = Date.parseDate(val, 'Y-m-d H:i:s.g');
               var _val2 = val.format ('Y-m-d H:i:s.g');
               return _val2.format('d/m/Y');
		}
		catch(e)
		{
              	return fecha.format('d/m/Y');
		}
	};
         

	function modificaPolizaMaestra () {
		//Si se da click a 'Asignar Numero a Emision'
		if(asignarNumeroA == 'cdnumpol'){
			var cdNumeroPoliza = getSelectedKey(grid2, "cdNumPol");//se toma el cdNumPol de la ventana
			var cdNumeroRenovacion = record.get('cdnumren');//valor que ya tenía
		}
		//Si se da click a 'Asignar Numero a Renovacion'
		else if(asignarNumeroA == 'cdnumren'){
			var cdNumeroRenovacion = getSelectedKey(grid2, "cdNumPol");//se toma el cdNumPol de la ventana
			var cdNumeroPoliza = record.get('cdnumpol');//valor que ya tenía
		}
		//alert( "valor para cdnumpol=" + cdNumeroPoliza );
		//alert( "valor para cdnumren=" + cdNumeroRenovacion );
		
		var _params = {
			'parametrosBusqueda.cdpolmtra': record.get('cdpolmtra'),//getSelectedKey(grid2, "cdNumPol"),
			'parametrosBusqueda.cdelemento': record.get('cdelemento'), 
			'parametrosBusqueda.cdcia': record.get('cdcia'), 
			'parametrosBusqueda.cdramo': record.get('cdramo'), 
			'parametrosBusqueda.cdtipo': record.get('cdtipo'),
			'parametrosBusqueda.nmpoliex': record.get('nmpoliex'),
			'parametrosBusqueda.nmpoliza': record.get('nmpoliza'),
			'parametrosBusqueda.feinicio': feInicio(record.get('feinicio')),
			'parametrosBusqueda.fefin': feInicio(record.get('fefin')),
			//'parametrosBusqueda.cdnumpol': getSelectedKey(grid2, "cdNumPol"),
			'parametrosBusqueda.cdnumpol': cdNumeroPoliza,
			'parametrosBusqueda.cdnumren': cdNumeroRenovacion,
			'parametrosBusqueda.cdtipsit': ''//TODO : poner valor correspondiente
			
		};
		//execConnection(_GUARDAR_POLIZA_MAESTRA, _params, cbkRehabilitarPoliza, 10000000);
		var conn = new Ext.data.Connection ();
		conn.request ({
			url: _GUARDAR_POLIZA_MAESTRA,
			method: 'POST',
			timeout: 30000,
			params: _params,
			callback: function (options, success, response) {
				try {
					var messageResult = Ext.util.JSON.decode(response.responseText).messageResult;
					var success = Ext.util.JSON.decode(response.responseText).success;
					cbkRehabilitarPoliza(success, messageResult);
					if(success){
						gridResultados.getStore().load();
					}
				} catch (e) {
					//Necesario en el caso de errores al establecer el action,
					//ó cuando se vence el timeout de la conexión y desde el action no se obtuvo respuesta alguna
					cbkRehabilitarPoliza(false, "Connection Problem!")
				}
			}
		});
	}
	
	function cbkRehabilitarPoliza (_success, _message) {
		//alert(1);
		if (!_success) {
			//alert(2);
			Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'), _message);
			
			//, function(){validarRecibosPagados();}
		} else {
		//alert(3);
			Ext.Msg.alert(getLabelFromMap('400000', helpMap, 'Aviso'), _message);
			//, function(){validarRecibosPagados();}
		}
	}
      
}