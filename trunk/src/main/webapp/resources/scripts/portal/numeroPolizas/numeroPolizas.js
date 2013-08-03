//var helpMap = new Map();
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
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
        header: getLabelFromMap("NroPolCmCli", helpMap,"Cliente"),
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
        header: getLabelFromMap("NroPolCmTipPol", helpMap,"Tipo P&oacute;liza"),
        tooltip: getToolTipFromMap("NroPolCmTipPol", helpMap, "Tipo P&oacute;liza"),
        dataIndex: 'dsTipPol',
        width: 160,
        sortable: true
        },{
        header: getLabelFromMap("NroPolCmPrefi", helpMap,"Prefijo"),
        tooltip: getToolTipFromMap("NroPolCmPrefi", helpMap, "Prefijo"),
        dataIndex: 'dsSufPre',
        width: 160,
        sortable: true
        },{
        header: getLabelFromMap("NroPolCmFolIni", helpMap,"Folio Inicial"),
        tooltip: getToolTipFromMap("NroPolCmFolIni", helpMap, "Folio Inicial"),
        dataIndex: 'nmFolioIni',
        width: 160,
        sortable: true
        },{
        header: getLabelFromMap("NroPolCmFolFin", helpMap,"Folio Final"),
        tooltip: getToolTipFromMap("NroPolCmFolFin", helpMap, "Folio Final"),
        dataIndex: 'nmFolioFin',
        width: 160,
        sortable: true
        }
        
        ]);
        if(DES_UNIECO != ""){//&& DES_ELEMENTO && DES_RAMO){
     		_url = _ACTION_BUSCAR_NUMERO_POLIZAS+'?dsEelemen='+DES_ELEMENTO+'&dsUniEco='+DES_UNIECO+'&dsRamo='+DES_RAMO;
   			 }else{
    			 _url = _ACTION_BUSCAR_NUMERO_POLIZAS;
    			 //boton=true;
   				  //Ext.getCmp('maestraVolver').setVisible(false);
    		}
    		 var url = _url;
        
        
        
       //var url = _url;
       var store;
// function test(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: url //_ACTION_BUSCAR_NUMERO_POLIZAS
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

  //     return store;
 //	}


    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
        renderTo:'gridNroPolizas',
        store:store,
        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
		border:true,
        cm: cm,
        //loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
        buttonAlign:'center',
		buttons:[
        		{
        		text:getLabelFromMap("cmdAgregarId", helpMap, 'Agregar'),
        		tooltip: getToolTipFromMap("cmdAgregarId", helpMap, "Agrega un nuevo n&uacute;mero de P&oacute;liza"),
        		id: 'cmdAgregarId',		        
            	handler:function(){
                agregar();
               
                }
            	},{ 
            	text:getLabelFromMap("cmdEditarId", helpMap,"Editar"),
            	id: 'cmdEditarId',
		        tooltip: getToolTipFromMap("cmdEditarId", helpMap, "Edita un n&oacute;mero de P&oacute;liza"),
            	handler:function(){
                    if (getSelectedKey(grid2, "cdUniEco")  != "") {
                        editar(getSelectedRecord(grid2));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso') , getLabelFromMap('400000', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
            	}
            	},{
            	text: getLabelFromMap("cmdBorrarId", helpMap,"Eliminar"),
            	id: 'cmdBorrarId',
		        tooltip: getToolTipFromMap("cmdBorrarId", helpMap, "Elimina un n&uacute;mero de P&oacute;liza"),
                handler:function(){
                    if (getSelectedKey(grid2, "cdUniEco") != "" && getSelectedKey(grid2, "cdElemento") != "" && getSelectedKey(grid2, "cdRamo") != "") {
                    	borrar(getSelectedRecord(grid2));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso') , getLabelFromMap('400011', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
               },
            	{
               	text:getLabelFromMap("cmdExportarId", helpMap,"Exportar"),
               	id: 'cmdExportarId',
		        tooltip: getToolTipFromMap("cmdExportarId", helpMap, "Exporta un n&uacute;mero de P&oacute;liza"),
            	handler:function(){
                        var url = _ACTION_EXPORT_NUMERO_POLIZA + '?dsUniEco=' + desUniEco.getValue() + '&dsRamo='+ desRamo.getValue() + '&dsElemen=' + desElemen.getValue();
                	 	showExportDialog( url );
                        }
                },
                //Boton de regreso a Polizas Maestras
                {
					id : 'polizasMaestra',
					text: getLabelFromMap("polizasMaestra", helpMap,"Asignar"),
			        tooltip: getToolTipFromMap("cmdExportarId", helpMap, "Asignar"),
					//handler : exportButton(_ACTION_EXPORT)
					handler:function(){        
						if (getSelectedKey(grid2, "cdUniEco") != "") {
                           volver(getSelectedRecord(grid2));
                    		}else{
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                	    }	
					
                	 
				}}
                /*,{
            	text:getLabelFromMap("nroPolBtnBack", helpMap, "Regresar"),
            	id: 'cmdRegresarId',
		        tooltip: getToolTipFromMap("nroPolBtnBack", helpMap, "Regresa a la pantalla anterior")
            	}*/
            	],
            	
            	
    	width:500,
    	frame:true,
		height:320,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		stripeRows: true,
		collapsible: true,
		bbar: new Ext.PagingToolbar({
				pageSize: itemsPerPage,
				store: store,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap, 'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap, "No hay registros para visualizar")
		    })
		});

    grid2.render();
    
}

/*if(DES_UNIECO != ""){//&& DES_ELEMENTO && DES_RAMO){
     _url = _ACTION_BUSCAR_NUMERO_POLIZAS+'?dsEelemen='+DES_ELEMENTO+'&dsUniEco='+DES_UNIECO+'&dsRamo='+DES_RAMO;
    }else{
     _url = _ACTION_BUSCAR_NUMERO_POLIZAS;
     //boton=true;
     //Ext.getCmp('maestraVolver').setVisible(false);
    }
var url = _url;*/


//if (DES_UNIECO || DES_ELEMENTO || DES_RAMO){reloadGrid();}


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


   
    
var desUniEco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap("dsUniEco", helpMap, 'Aseguradora'),
        tooltip:getToolTipFromMap('dsUniEco',helpMap,'Aseguradora'),
        hasHelpIcon:getHelpIconFromMap('dsUniEco',helpMap),
	    Ayuda:getHelpTextFromMap('dsUniEco',helpMap),
        name:'dsUniEco',
        id: 'dsUniEco',
        anchor: '95%'
    });
    


var desRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap("dsRamo", helpMap,'Producto'),
        //helpText:getHelpTextFromMap
        tooltip:getToolTipFromMap('dsRamo',helpMap,'Producto'),
        hasHelpIcon:getHelpIconFromMap('dsRamo',helpMap),
	    Ayuda:getHelpTextFromMap('dsRamo',helpMap),
        name:'dsRamo',
        id: 'dsRamo',
        anchor: '95%'
    });
    
var desElemen = new Ext.form.TextField({
        fieldLabel: getLabelFromMap("dsElemen", helpMap,'Cliente'),
        //helpText:getHelpTextFromMap
        tooltip:getToolTipFromMap('dsElemen',helpMap,'Cliente'),
        hasHelpIcon:getHelpIconFromMap('dsElemen',helpMap),
	    Ayuda:getHelpTextFromMap('dsElemen',helpMap),
        name:'dsElemen',
        id: 'dsElemen',
        anchor: '95%'
    });


   
    

    var incisosForm = new Ext.form.FormPanel({
        el:'formBusqueda',
        id: 'formBusqueda',
		title: '<span style="color:black;font-size:12px;">' + getLabelFromMap('formBusqueda', helpMap, 'Numeraci&oacute;n de P&oacute;lizas') + '</span>',
        iconCls:'logo',
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
        		        items: [{
        		        	html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[{
						    	columnWidth:.7,
                				layout: 'form',
		                		border: false,
        		        		items:[
        		        				desUniEco,
        		        				desRamo,
        		        				desElemen
        		        			  ]
								},{
								columnWidth:.3,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
                					id: 'cmdBuscarId',
        							text: getLabelFromMap("cmdBuscarId", helpMap,'Buscar'),
        							tooltip: getToolTipFromMap("cmdBuscarId", helpMap, "Busca numeraci&oacute;n de p&oacute;lizas"),
        							handler: function() 
        							
        							{
									    store.baseParams= store.baseParams || {};
									    store.baseParams= { 'dsUniEco': desUniEco.getValue(),
									    								   'dsElemen': desElemen.getValue(),
									    								   'dsRamo': desRamo.getValue()
																	     };
										store.params = {start: 0, limit: 20};
										store.proxy = new Ext.data.HttpProxy({
																url : _ACTION_BUSCAR_NUMERO_POLIZAS
															});
										//alert(1);
										store.load({
																//proxy.url : _NUEVA_BUSQUEDA,
																callback: function(r, options, success){
																				if(!success){
																				//console.log(storeGridResultado.reader.jsonData.mensajeError);
																				//Ext.MessageBox.alert('Buscar',store.reader.jsonData.mensajeError);
																				Ext.MessageBox.alert('Aviso','No se Encontraron datos');
																				//Ext.MessageBox.alert('Buscar',storeGridResultado.reader.jsonData.mensajeError);
																				grid2.store.removeAll();
																				grid2.getBottomToolbar().updateInfo();
																				}
																		}
															});
																return;
										/*if (filtroForm.form.isValid()) {
											filtroForm.form.submit({
												url: _ACTION_BUSCAR_NUMERO_POLIZAS,
												waitMsg : 'Procesando...',
												failure : function(form, action) {
													Ext.MessageBox.alert('Buscar','No se Encontraron datos');
													grid2.removeAll();
												},
												success : function(form, action) {



												}
											});
										} else {
											Ext.MessageBox.alert('Error', 'Ocurrio un error');
										}*/
									}
        							
        							
        							/*{
				               			if (incisosForm.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
											Ext.MessageBox.alert(getLabelFromMap('400010', helpMap, 'Error'), getLabelFromMap('400013', helpMap, 'Complete la informaci&oacute;n requerida'));
										}
									}*/
        							},{
        							id: 'cmdCancelarId',
        							text: getLabelFromMap("cmdCancelarId", helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap("cmdCancelarId", helpMap, "Cancela busqueda numeración de pólizas"),
        							handler: function() {
        								//Ext.getCmp('dsUniEco').setValue("");
	    								//Ext.getCmp('dsRamo').setValue("");
	    								//Ext.getCmp('dsElemen').setValue("");
        								incisosForm.form.reset();
        							}
        						}]
        					}]
        				}]
				});
 		incisosForm.render();	
		Ext.getCmp('dsUniEco').setValue(DES_UNIECO);
	    Ext.getCmp('dsRamo').setValue(DES_RAMO);
	    Ext.getCmp('dsElemen').setValue(DES_ELEMENTO);
		//incisosForm.render();
        
        createGrid();
        grid2.render();
        //reloadGrid();
        
        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    	}

//var store;
//reloadGrid();
   
       function borrar(record) {
        if(record.get('cdUniEco') != "" && record.get('cdElemento') != "" && record.get('cdRamo') != "")
        {
            Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
            {
                if (btn == "yes")
                {
                	var _params = {
				              cdNumPol: record.get('cdNumPol')
                	};
                	execConnection(_ACTION_BORRAR_NUMERO_POLIZA_EMISION, _params, cbkConnection);
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



// Funcion de Agregar Desglose de Polizas
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
           //{name: 'cdPerson', type: 'string',mapping:'cdPerson'},
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
   
    
    formulaPanelAgregar=new Ext.Panel({
    	layout:'column',
		border: false,
		width : 350,
		//column items:
		items:[
			{
				columnWidth:.6,
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
                		width: 100,
                		blankText: 'Debe elegir una Expresi&oacute;n',
						disabled: true,
						readOnly: true
                	}
			  	]
			  },{
			  	columnWidth:.4,
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
                			//Si el campo Formula ya tiene valor, mostraremos la expresión con ése código, para que sea editada:
                			if(Ext.getCmp('dsCalculo').getValue() != ""){
                				ExpresionesVentana2(Ext.getCmp('dsCalculo').getValue(), Ext.getCmp('hidden-codigo-expresion-session').getValue());
                			}else{
                				var connect = new Ext.data.Connection();
								connect.request ({
									url:'atributosVariables/ObtenerCodigoExpresion.action',
									callback: function (options, success, response) {
										try{
											//alert(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
											Ext.getCmp('dsCalculo').setValue( Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').getValue() );
										}catch(e){
										}
									}
								});
                			}
						}
                	},{
						xtype:'hidden',
						id:'hidden-codigo-expresion-session',
						name:'codigoExpresionSession', 
						value:'EXPRESION_NUMERACION_POLIZAS'
					},{
                		xtype:'hidden',                	
                		id:'hidden-codigo-expresion-numeracion-polizas',
		           		name: "codigoExpresion"
                	}
                ]
			}
		]//end column items
	});
	
    
//se define el formulario
var formPanel = new Ext.FormPanel ({
            labelWidth : 100,
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            labelAlign:'right',
            //defaults : {width : 250 },
            defaultType : 'textfield',
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },
                {
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    //tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorp,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdElemento',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdElementoId', helpMap, "Cliente"),
                    tooltip: getToolTipFromMap('cdElementoId', helpMap, "Cliente"),
        			hasHelpIcon:getHelpIconFromMap('cdElementoId',helpMap),
	    			Ayuda:getHelpTextFromMap('cdElementoId',helpMap),
                    width: 250,
                    emptyText:getLabelFromMap('400016', helpMap, 'Seleccione Cliente ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
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
                },
                {
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
			        hasHelpIcon:getHelpIconFromMap('cdUniEcoId',helpMap),
	    			Ayuda:getHelpTextFromMap('cdUniEcoId',helpMap),
	                width: 250, 
	                emptyText: getLabelFromMap('400018', helpMap, 'Seleccione Aseguradora...'),
	                selectOnFocus:true, 
	                forceSelection:true,
	                allowBlank : false,
	                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
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
	             },

                {
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
			        hasHelpIcon:getHelpIconFromMap('cdRamoId',helpMap),
	    			Ayuda:getHelpTextFromMap('cdRamoId',helpMap),
                    width: 250,
                    emptyText: getLabelFromMap('cdRamoId', helpMap, 'Seleccione Producto...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
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
                    tooltip: getToolTipFromMap('indSufPreId', helpMap, "Indicador (S/P)"),
                    hasHelpIcon:getHelpIconFromMap('indSufPreId',helpMap),
				    Ayuda:getHelpTextFromMap('indSufPreId',helpMap),
                    width: 250,
                    emptyText: getLabelFromMap('indSufPreId', helpMap, 'Seleccione ...'),
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
                {
                xtype: 'textfield', 
                fieldLabel: getLabelFromMap('dsSufPre', helpMap, "Valor"), 
                tooltip: getToolTipFromMap('dsSufPre', helpMap, 'Valor'),
                hasHelpIcon:getHelpIconFromMap('dsSufPre',helpMap),
				Ayuda:getHelpTextFromMap('dsSufPre',helpMap),
                name: 'dsSufPre', 
                id: 'dsSufPre'
                },
               
               
               
                /*Combo Proceso Polizas*/
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsProceso,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdProceso',//procesoPoliza',
                    allowBlank : false,
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdProcesoId', helpMap, "Proceso"),
                    tooltip: getToolTipFromMap('cdProcesoId', helpMap, "Proceso"),
			        hasHelpIcon:getHelpIconFromMap('cdProcesoId',helpMap),
	    			Ayuda:getHelpTextFromMap('cdProcesoId',helpMap),
                    width: 250,
                    emptyText: getLabelFromMap('cdProcesoId', helpMap, 'Seleccione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'cdProcesoId',
                    onSelect: function (record) {
	                            				
	                            				/*if (record.get("id")=="N"){
	                            				          
	                            							formPanel.findById("dsSufPre").setDisabled(true);
	                            							formPanel.findById("dsSufPre").allowBlank = true;
	                            							formPanel.findById("dsSufPre").setValue('');
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="S" || record.get("id")=="P"){
	                            				
	                            							formPanel.findById("dsSufPre").setDisabled(false);
	                            							formPanel.findById("dsSufPre").allowBlank = false;
	                            							
	                            				}*/
	                            				
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
                    allowBlank : false,
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('swAgrupaId', helpMap, "¿Usa Agrupaci&oacute;n?"),
                    tooltip: getToolTipFromMap('swAgrupaId', helpMap, "¿Usa Agrupaci&oacute;n?"),
			        hasHelpIcon:getHelpIconFromMap('swAgrupaId',helpMap),
	    			Ayuda:getHelpTextFromMap('swAgrupaId',helpMap),
                    width: 250,
                    emptyText: getLabelFromMap('swAgrupaId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'swAgrupaId',
                    onSelect: function (record) {
	                            				
	                            				/*if (record.get("id")=="N"){
	                            				          
	                            							formPanel.findById("dsSufPre").setDisabled(true);
	                            							formPanel.findById("dsSufPre").allowBlank = true;
	                            							formPanel.findById("dsSufPre").setValue('');
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="S" || record.get("id")=="P"){
	                            				
	                            							formPanel.findById("dsSufPre").setDisabled(false);
	                            							formPanel.findById("dsSufPre").allowBlank = false;
	                            							
	                            				}*/
	                            				
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
                    allowBlank : false,
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdTipPolId', helpMap, "Tipo Poliza"),
                    tooltip: getToolTipFromMap('cdTipPolId', helpMap, "Tipo Poliza"),
			        hasHelpIcon:getHelpIconFromMap('cdTipPolId',helpMap),
	    			Ayuda:getHelpTextFromMap('cdTipPolId',helpMap),
                    width: 250,
                    emptyText: getLabelFromMap('cdTipPolId', helpMap, 'Selecione ...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'cdTipPolId',
                    onSelect: function (record) {
	                            				
	                            				/*if (record.get("id")=="N"){
	                            				          
	                            							formPanel.findById("dsSufPre").setDisabled(true);
	                            							formPanel.findById("dsSufPre").allowBlank = true;
	                            							formPanel.findById("dsSufPre").setValue('');
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="S" || record.get("id")=="P"){
	                            				
	                            							formPanel.findById("dsSufPre").setDisabled(false);
	                            							formPanel.findById("dsSufPre").allowBlank = false;
	                            							
	                            				}*/
	                            				
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
			        hasHelpIcon:getHelpIconFromMap('indCalcId',helpMap),
	    			Ayuda:getHelpTextFromMap('indCalcId',helpMap),
                    width: 250,
                    emptyText: getLabelFromMap('400020', helpMap, 'Seleccione Forma Calculo Folio...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    labelSeparator:'',
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
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
	                            							Ext.getCmp('btnExpresion').setDisabled(true);
	                            						    
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
	                            							Ext.getCmp('btnExpresion').setDisabled(false);
	                            							
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();	
	                            		        }
                } ,
                formulaPanelAgregar,
               // {xtype: 'numberfield', fieldLabel: getLabelFromMap('dsCalculo', helpMap, "F&oacute;rmula"), tooltip: getToolTipFromMap('dsCalculo', helpMap, 'Formula de Calculo de Folio'), name: 'dsCalculo', heigth:150, id: 'dsCalculo'},	
				{
				xtype: 'numberfield', 
				allowDecimals: false,
			    allowNegative: false,
				fieldLabel: getLabelFromMap('nmFolioIni', helpMap, "Folio Inicial"), 
				tooltip: getToolTipFromMap('nmFolioIni', helpMap, 'Folio Inicial'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioIni',helpMap),
			    Ayuda:getHelpTextFromMap('nmFolioIni',helpMap),
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
				xtype: 'numberfield', 
				allowDecimals: false,
			    allowNegative: false,
				fieldLabel: getLabelFromMap('nmFolioFin', helpMap, "Folio Final"), 
				tooltip: getToolTipFromMap('nmFolioFin', helpMap, 'Folio Final'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioFin',helpMap),
	    		Ayuda:getHelpTextFromMap('nmFolioFin',helpMap),
				name: 'nmFolioFin', 
				id: 'nmFolioFin',
				listeners: {
						    'change': function(){
						         validarFolios();
						    }
						  }
				},
			    {
			    xtype: 'numberfield',
			    allowDecimals: false,
			    allowNegative: false,
			    fieldLabel: getLabelFromMap('nmFolioAct', helpMap, "Folio Actual"), 
			    tooltip: getToolTipFromMap('nmFolioAct', helpMap, 'Folio Actual'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioAct',helpMap),
	    		Ayuda:getHelpTextFromMap('nmFolioAct',helpMap),
			    name: 'nmFolioAct', 
			    readOnly: true,  
			    id: 'nmFolioAct'
			    }
            ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: getLabelFromMap('windowAgregar', helpMap, 'Agregar N&uacute;mero de P&oacute;liza'),
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
        items: formPanel,
        //se definen los botones del formulario
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
                            waitTitle: getLabelFromMap('400021', helpMap, 'Espere...'),
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
								    Ext.getCmp('btnExpresion').setDisabled(false);
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
            }/*,{
                 text : getLabelFromMap('cmdFormula', helpMap, 'Formula'),
                 tooltip: getToolTipFromMap('cmdFormula', helpMap, 'Formula'),
                 id: 'cmdFormula',
                 handler : function() {
                 //window.close();
                    }
            }*/
            ]
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
    dsClientesCorp.load();
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
    
    
   
	formulaPanelEditar=new Ext.Panel({
    	layout:'column',
		border: false,
		width : 350,
		//column items:
		items:[
			{
				columnWidth:.6,
               	layout: 'form',
               	border: false,
				items:[
					{
						xtype: 'textfield', 
                		fieldLabel: getLabelFromMap('dsCalculo', helpMap, "F&oacute;rmula"), 
                		tooltip: getToolTipFromMap('dsCalculo', helpMap, 'Formula de Calculo de Folio'), 
		        		hasHelpIcon:getHelpIconFromMap('dsCalculo',helpMap),
	    				Ayuda:getHelpTextFromMap('dsCalculo',helpMap),
                		name: 'dsCalculo', 
                		id: 'dsCalculo',
                		width: 100,
                		blankText: 'Debe elegir una Expresi&oacute;n',
						disabled: true,
						readOnly: true
                	}
			  	]
			  },{
			  	columnWidth:.4,
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
                			//Si el campo Formula ya tiene valor, mostraremos la expresión con ése código, para que sea editada:
                			if(Ext.getCmp('dsCalculo').getValue() != ""){
                				ExpresionesVentana2(Ext.getCmp('dsCalculo').getValue(), Ext.getCmp('hidden-codigo-expresion-session').getValue());
                			}else{
                				//Si no tiene valor el campo Formula, creamos un nuevo codigoExpresion
                				var connect = new Ext.data.Connection();
								connect.request ({
									url:'atributosVariables/ObtenerCodigoExpresion.action',
									callback: function (options, success, response) {
										try{
											//alert(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
											Ext.getCmp('dsCalculo').setValue( Ext.getCmp('hidden-codigo-expresion-numeracion-polizas').getValue() );
										}catch(e){
										}
									}
								});
                			}
						}
                	},{
						xtype:'hidden',
						id:'hidden-codigo-expresion-session',
						name:'codigoExpresionSession', 
						value:'EXPRESION_NUMERACION_POLIZAS'
					},{
                		xtype:'hidden',                	
                		id:'hidden-codigo-expresion-numeracion-polizas',
		           		name: "codigoExpresion"
                	}
                ]
			}
		]//end column items
	});
	
       
//se define el formulario
var formPanel = new Ext.FormPanel ({

           labelWidth : 100,
           labelAlign:'right',
           
			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_NUMERO_POLIZA,

            baseParams : {cdUniEco: record.get("cdUniEco"),
                          cdElemento: record.get("cdElemento"),
                          cdRamo: record.get("cdRamo"),
                          cdNumPol: record.get("cdNumPol")
                         },

            bodyStyle : 'padding:5px 5px 0',

			//se setea el reader que va a usar el form cuando se cargue
            reader : _jsonFormReader,
            //reader : test(),
            //defaults : {width : 200},
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
                    fieldLabel: getLabelFromMap('dsElemenId', helpMap, "Cliente"),
                    tooltip: getToolTipFromMap('dsElemenId', helpMap, 'Cliente'),
			        hasHelpIcon:getHelpIconFromMap('dsElemenId',helpMap),
		    		Ayuda:getHelpTextFromMap('dsElemenId',helpMap),
                    disabled:true,
                    id: 'dsElemenId',
                    width: '200'
                },{
                    xtype: 'textfield',
                    name : 'dsUniEcoId',
                    fieldLabel: getLabelFromMap('dsUniEcoId', helpMap, "Aseguradora"),
                    tooltip: getToolTipFromMap('dsUniEcoId', helpMap, 'Aseguradora'),
			        hasHelpIcon:getHelpIconFromMap('dsUniEcoId',helpMap),
		    		Ayuda:getHelpTextFromMap('dsUniEcoId',helpMap),
                    disabled:true,
                    id: 'dsUniEcoId',
                    width: '200'
                },
                //Se borro definicion y configuracion de combo Clientes y combo Aseguradora
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
                    displayField: 'descripcion',
                    tooltip: getToolTipFromMap('cdRamoId', helpMap, 'descripcion'),
			        hasHelpIcon:getHelpIconFromMap('cdRamoId',helpMap),
		    		Ayuda:getHelpTextFromMap('cdRamoId',helpMap),
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
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
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
			        hasHelpIcon:getHelpIconFromMap('indSufPreId',helpMap),
		    		Ayuda:getHelpTextFromMap('indSufPreId',helpMap),
                    width: 200,
                    emptyText: getLabelFromMap('indSufPreId', helpMap, 'Seleccione ...'),
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
                {
                xtype: 'textfield',
                 fieldLabel: getLabelFromMap('dsSufPre', helpMap, "Valor"),
                 tooltip: getToolTipFromMap('dsSufPre', helpMap, 'Valor'),
 		         hasHelpIcon:getHelpIconFromMap('dsSufPre',helpMap),
		    	 Ayuda:getHelpTextFromMap('dsSufPre',helpMap),
                 name: 'dsSufPre', 
                 id: 'dsSufPre',
                 width: '200'
                 },
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
			        hasHelpIcon:getHelpIconFromMap('indCalcId',helpMap),
		    		Ayuda:getHelpTextFromMap('indCalcId',helpMap),
                    width: 200,
                    emptyText: getLabelFromMap('indCalcId', helpMap, 'Seleccione Forma C&aacute;lculo Folio...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
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
	                            							Ext.getCmp('btnExpresion').setDisabled(true);
	                            							
	                            							
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
	                            							Ext.getCmp('btnExpresion').setDisabled(false);
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();	
	                            		        }
                } ,
                
                
                formulaPanelEditar,
				
				/*{xtype: 'textarea', fieldLabel: getLabelFromMap('txtADsCalculo', helpMap, "F&oacute;rmula de C&aacute;lculo de Folio"), tooltip: getToolTipFromMap('txtADsCalculo', helpMap, 'Formula de Calculo de Folio'),
				name: 'dsCalculo',
				blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
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
                    fieldLabel: getLabelFromMap('cdProcesoId', helpMap, "Proceso"),
                    tooltip: getToolTipFromMap('cdProcesoId', helpMap, "Proceso"),
			        hasHelpIcon:getHelpIconFromMap('cdProcesoId',helpMap),
		    		Ayuda:getHelpTextFromMap('cdProcesoId',helpMap),
                    width: 200,
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
                    fieldLabel: getLabelFromMap('usaCombinacionId', helpMap, "¿Usa Agrupaci&oacute;n?"),
                    tooltip: getToolTipFromMap('usaCombinacionId', helpMap, "¿Usa Agrupaci&oacute;n?"),
			        hasHelpIcon:getHelpIconFromMap('usaCombinacionId',helpMap),
		    		Ayuda:getHelpTextFromMap('usaCombinacionId',helpMap),
                    width: 200,
                    emptyText: getLabelFromMap('usaCombinacionId', helpMap, 'Selecione ...'),
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
                    fieldLabel: getLabelFromMap('tipoPolizaId', helpMap, "Tipo Poliza"),
                    tooltip: getToolTipFromMap('tipoPolizaId', helpMap, "Tipo Poliza"),
			        hasHelpIcon:getHelpIconFromMap('tipoPolizaId',helpMap),
		    		Ayuda:getHelpTextFromMap('tipoPolizaId',helpMap),
                    width: 200,
                    emptyText: getLabelFromMap('tipoPolizaId', helpMap, 'Selecione Tipo P&oacute;liza...'),
                    selectOnFocus:true,
                    forceSelection:true,
                    //labelSeparator:'',
                    id:'tipoPolizaId',
                    onSelect: function (record) {
	                            				
	                            					                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
                } ,
				{
				xtype: 'numberfield', 
				allowDecimals: false,
			    allowNegative: false,
				fieldLabel: getLabelFromMap('nmFolioIni', helpMap, "Folio Inicial"), 
				tooltip: getToolTipFromMap('nmFolioIni', helpMap, 'Folio Inicial'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioIni',helpMap),
	    		Ayuda:getHelpTextFromMap('nmFolioIni',helpMap),
				name: 'nmFolioIni', 
				id: 'nmFolioIni',
				width: '200',
				listeners: {
						    'change': function(){
						    	 validarFolios();
						         actualizaFolioActual();
						    }
						  }
				},
				{
				xtype: 'numberfield', 
				allowDecimals: false,
			    allowNegative: false,
				fieldLabel: getLabelFromMap('nmFolioFin', helpMap, "Folio Final"), 
				tooltip: getToolTipFromMap('nmFolioFin', helpMap, 'Folio Final'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioFin',helpMap),
	    		Ayuda:getHelpTextFromMap('nmFolioFin',helpMap),
				name: 'nmFolioFin', 
				id: 'nmFolioFin',
				width: '200',
				listeners: {
						    'change': function(){
						         validarFolios();
						    }
						  }
				},
				{
				xtype: 'numberfield', 
				allowDecimals: false,
			    allowNegative: false,
				fieldLabel: getLabelFromMap('nmFolioAct', helpMap, "Folio Actual"), 
				tooltip: getToolTipFromMap('nmFolioAct', helpMap, 'Folio Actual'), 
		        hasHelpIcon:getHelpIconFromMap('nmFolioAct',helpMap),
	    		Ayuda:getHelpTextFromMap('nmFolioAct',helpMap),
				name: 'nmFolioAct', 
				readOnly: true,  
				id: 'nmFolioAct',
				width: '200'
				}

            ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: getLabelFromMap('windowEditar', helpMap, 'Editar N&uacute;mero de P&oacute;liza'),
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
                text : getLabelFromMap('cmdGuardarEdt', helpMap, 'Guardar'),
                tooltip: getToolTipFromMap('cmdGuardarEdt', helpMap, 'Guarda la actualizaci&oacute;n'),
                id: 'cmdGuardarEdt',
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
                text : getLabelFromMap('cmdGuardarAgregarEdt', helpMap, 'Guardar y Agregar'),
                tooltip: getToolTipFromMap('cmdGuardarAgregarEdt', helpMap, 'Guarda y Agrega un n&uacute;mero de P&oacute;liza'),
                id: 'cmdGuardarAgregarEdt',
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
                 text : getLabelFromMap('cmdRegresarEdt', helpMap, 'Regresar'),
                 id: 'cmdRegresarEdt',
                 tooltip: getToolTipFromMap('cmdRegresarEdt', helpMap, 'Regresa a la pantalla anterior'),
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
	                            							Ext.getCmp('btnExpresion').setDisabled(true);
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
	                            							Ext.getCmp('btnExpresion').setDisabled(false);
	                            							
	                            	}
	                            	
                        }
                });


};
function volver(record)	{
			
				window.location=_ACTION_POLIZAS_MAESTRAS+"?cdNumPol ="+ 121/*Arreglar*/+"& dsElemen ="+record.get('dsElemen')+"& dsUnieco="+record.get('dsUniEco')+"& dsRamo="+record.get('dsRamo')+"& cdPolMtra="+ CODIGO_POLMTRA+"& pantallaOrigen=NUMERACION_POLIZAS";//record.get('cdNumPol');
							
			}



});