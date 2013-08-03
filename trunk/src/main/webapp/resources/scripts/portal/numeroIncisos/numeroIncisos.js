Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

    var cm = new Ext.grid.ColumnModel([
        {
       
        header: "cdUniEco",
        dataIndex: 'cdUniEco',
        hidden : true
        },{
        header: getLabelFromMap('nroInsCmAseg',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('nroInsCmAseg',helpMap,'Aseguradora'),
        dataIndex: 'dsUniEco',
        width: 80,
        sortable: true
        },{
        header: "cdRamo",
        dataIndex: 'cdRamo',
        hidden : true
        },{
        header: getLabelFromMap('nroInsCmPro',helpMap,'Producto'),
        tooltip: getToolTipFromMap('nroInsCmPro',helpMap,'Producto'),
        dataIndex: 'dsRamo',
        width: 120,
        sortable: true
        },{
        header: "cdElemento",
        dataIndex: 'cdElemento',
        hidden : true
        },{
        header: getLabelFromMap('nroInsCmCli',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('nroInsCmCli',helpMap,'Cliente'),
        dataIndex: 'dsElemen',
        width: 130,
        sortable: true
        },{
        header: "indSituac",
        dataIndex: 'indSituac',
        hidden : true
        },{
        header: getLabelFromMap('nroInsCmSubIn',helpMap,'Numeraci&oacute;n Inciso / Subinciso'),
        tooltip: getToolTipFromMap('nroInsCmSubIn',helpMap,'Numeraci&oacute;n Inciso / Subinciso'),
        dataIndex: 'dsIndSituac',
        width: 180,
        sortable: true
        }]);


 function test(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_NUMERO_INCISOS
                }),

                reader: new Ext.data.JsonReader({
            	root:'listaNumeroIncisos',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
	        {name: 'cdUniEco',  type: 'string',  mapping:'cdUniEco'},
	        {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
	        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},
	        {name: 'cdElemento',  type: 'string',  mapping:'cdElemento'},
	        {name: 'dsElemen',  type: 'string',  mapping:'dsElemen'},
	        {name: 'indSituac',  type: 'string',  mapping:'indSituac'},
	        {name: 'dsIndSituac',  type: 'string',  mapping:'dsIndSituac'}
			])
        });

       return store;
 	}


    var grid2;



function createGrid(){
	grid2= new Ext.grid.GridPanel({
        el:'gridIncisos',
        store:test(),
        
		border:true,
		title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
        cm: cm,
        loadMask: {msg: getLabelFromMap('400070',helpMap,'cargando datos ...'), disabled: false},
        buttonAlign:'center',
		buttons:[
        		{
        		text:getLabelFromMap('nroInsBtnAdd',helpMap,'Agregar'),
        		tooltip: getToolTipFromMap('nroInsBtnAdd',helpMap,'Agrega un nuevo N&uacute;mero de Inciso'),
            	handler:function(){
                agregar();
                }
            	},{ 
            	text:getLabelFromMap('nroInsBtnEd',helpMap,'Editar'),
        		tooltip: getToolTipFromMap('nroInsBtnEd',helpMap,'Edita un N&uacute;mero de Inciso'),
            	handler:function(){
                    if (getSelectedKey(grid2, "cdUniEco") != "") {
                        editar(getSelectedRecord(grid2));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
            	}
            	},{
            	text:getLabelFromMap('nroInsBtnDel',helpMap,'Eliminar'),
        		tooltip: getToolTipFromMap('nroInsBtnDel',helpMap,'Elimina un N&uacute;mero de Inciso'),
                handler:function(){
                    if (getSelectedKey(grid2, "cdUniEco") != "" && getSelectedKey(grid2, "cdElemento") != "" && getSelectedKey(grid2, "cdRamo") != "") {
                        borrar(getSelectedRecord(grid2));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
               },{
               	text:getLabelFromMap('nroInsBtnExprt',helpMap,'Exportar'),
            	tooltip: getToolTipFromMap('nroInsBtnExprt',helpMap,'Exporta listado'),
            	handler:function(){
                        var url = _ACTION_EXPORT_NUMERO_INCISOS + '?dsUniEco=' + desUniEco.getValue() + '&dsRamo='+ desRamo.getValue() + '&dsElemen=' + desElemen.getValue() + '&dsIndSitSubSit=' + desIndSitSubSit.getValue();
                	 	showExportDialog( url );
                        }
                }
                /*,{
            	text:getLabelFromMap('nroInsBtnBack',helpMap,'Regresar'),
            	tooltip: getToolTipFromMap('nroInsBtnBack',helpMap,'Regresa a la pantalla anterior')
            	}*/
            	],
            	
            	
    	width:500,
    	frame:true,
		height:320,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		collapsible: true,
		bbar: new Ext.PagingToolbar({
				pageSize: itemsPerPage,
				store: store,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })
		});

    grid2.render()
}



function reloadGrid(){
	var _params = {
    		dsUniEco: desUniEco.getValue(),
    		dsRamo: desRamo.getValue(),
    		dsElemen: desElemen.getValue(),
    		dsIndSitSubSit: desIndSitSubSit.getValue()
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
        fieldLabel: getLabelFromMap('nroInsTxtAseg', helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('nroInsTxtAseg',helpMap,'Aseguradora'),
        hasHelpIcon:getHelpIconFromMap('nroInsTxtAseg',helpMap),
		Ayuda:getHelpTextFromMap('nroInsTxtAseg',helpMap),
        name:'dsUniEco',
        anchor: '95%'
    });
    


var desRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('nroInsTxtProd', helpMap,'Producto'),
        tooltip: getToolTipFromMap('nroInsTxtProd',helpMap,'Producto'),
        hasHelpIcon:getHelpIconFromMap('nroInsTxtProd',helpMap),
		Ayuda:getHelpTextFromMap('nroInsTxtProd',helpMap),
        name:'dsRamo',
        anchor: '95%'
    });
    
var desElemen = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('nroInsTxtCli', helpMap,'Cliente'),
        tooltip: getToolTipFromMap('nroInsTxtCli',helpMap,'Cliente'),
        hasHelpIcon:getHelpIconFromMap('nroInsTxtCli',helpMap),
		Ayuda:getHelpTextFromMap('nroInsTxtCli',helpMap),
        name:'dsElemen',
        anchor: '95%'
    });


var desIndSitSubSit = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('nroInsTxtSubIns', helpMap,'Numeraci&oacute;n Inciso / Subinciso'),
        tooltip: getToolTipFromMap('nroInsTxtSubIns',helpMap,'Numeraci&oacute;n Inciso / Subinciso'),
        hasHelpIcon:getHelpIconFromMap('nroInsTxtSubIns',helpMap),
		Ayuda:getHelpTextFromMap('nroInsTxtSubIns',helpMap),
        name:'dsIndSitSubSit',
        anchor: '95%'
    });
    
    

    var incisosForm = new Ext.form.FormPanel({
    	id: 'incisosForm',
        el:'formBusqueda',
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm',helpMap,'Numeraci&oacute;n de Incisos / Subincisos')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        url:_ACTION_BUSCAR_NUMERO_INCISOS,
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
        		        				desElemen,
        		        				desIndSitSubSit
        		        			  ]
								},{
								columnWidth:.3,
                				layout: 'form'
                				}]
                			}],
                			buttons:[{
        							text:getLabelFromMap('nroInsBtnFind',helpMap,'Buscar'),
        							tooltip:getToolTipFromMap('nroInsBtnFind',helpMap,'Busca Numeraci&oacute;n de Subincisos'),
        							handler: function() {
				               			if (incisosForm.form.isValid()) {
                                               if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										}
									}
        							},{
        							text:getLabelFromMap('nroInsBtnCan',helpMap,'Cancelar'),
        							tooltip:getToolTipFromMap('nroInsBtnCan',helpMap,'Cancela la b&uacute;squeda de Numeraci&oacute;n de Subincisos'),
        							handler: function() {
        								incisosForm.form.reset();
        							}
        						}]
        					}]
        				}]
				});

		incisosForm.render();
        createGrid();

        function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    	}

var store;


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
				              cdRamo: record.get('cdRamo'),
				              indSituac: record.get('indSituac')
                	};
                	execConnection(_ACTION_BORRAR_NUMERO_INCISO, _params, cbkConnection);
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
 		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid();});
 	}
 }
   

     
 //Se borraron las funciones getSelectedCodigo y getSelectedRecord        
        
 // Funcion de Agregar Numero de Inciso
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
           {name: 'cdPerson', type: 'string',mapping:'cdPerson'},
           {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
       ]),
       remoteSort: true
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


   var dsIndicadorNumeracion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_INDICADOR_NUMERACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'indicadorNumeracionNroIncisos',
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
    
    
     var dsTramos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TRAMO
            }),
            reader: new Ext.data.JsonReader({
            root: 'numeroIncisosManagerListLoadTramo',
            id: 'swSubInc',
            successProperty:"success"
            },[
            {name: 'swSubInc', type: 'string',mapping:'swSubInc'}
              ]),
        remoteSort: true
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
        				fieldLabel: getLabelFromMap('dsCalculo',helpMap,'F&oacute;rmula'),
        				tooltip:getToolTipFromMap('dsCalculo',helpMap,'F&oacute;rmula de C&aacute;lculo de Folio'),
        				hasHelpIcon:getHelpIconFromMap('dsCalculo',helpMap),
        				Ayuda:getHelpTextFromMap('dsCalculo',helpMap),
        				name: 'dsCalculo',
        				width: 100,
        				id: 'dsCalculo',
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
											Ext.getCmp('hidden-codigo-expresion-numeracion-incisos').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-numeracion-incisos').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
											Ext.getCmp('dsCalculo').setValue( Ext.getCmp('hidden-codigo-expresion-numeracion-incisos').getValue() );
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
						value:'EXPRESION_NUMERACION_INCISOS'
					},{
                		xtype:'hidden',                	
                		id:'hidden-codigo-expresion-numeracion-incisos',
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
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            labelAlign:'right',
            //defaults : {width : 200 },
            defaultType : 'textfield',
            store:dsTramos,
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },{
                    xtype: 'hidden',
                    name : 'swSubInc',
                    id: 'swSubInc'
                },
                {
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorp,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdElemento',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdElementoId',helpMap,'Cliente'),
                    tooltip:getToolTipFromMap('cdElementoId',helpMap,'Cliente'),
			        hasHelpIcon:getHelpIconFromMap('cdElementoId',helpMap),
					Ayuda:getHelpTextFromMap('cdElementoId',helpMap),
                    width: 200,
                    emptyText:'Seleccione Cliente ...',
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
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
	                    formPanel.findById(('cdUniEcoId')).setValue('');
                        dsAseguradora.removeAll();
                        dsAseguradora.load({
	                                    	params: {cdElemento: record.get("cdElemento")},
	                         	            waitMsg: 'Espere por favor....'
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
	                fieldLabel: getLabelFromMap('cdUniEcoId',helpMap,'Aseguradora'),
                    tooltip:getToolTipFromMap('cdUniEcoId',helpMap,'Aseguradora'), 
			        hasHelpIcon:getHelpIconFromMap('cdUniEcoId',helpMap),
					Ayuda:getHelpTextFromMap('cdUniEcoId',helpMap),
	                width: 200, 
	                emptyText:'Seleccione Aseguradora...',
	                selectOnFocus:true, 
	                forceSelection:true, 
	                allowBlank : false,
	                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
	                id: 'cdUniEcoId',
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
                    fieldLabel: getLabelFromMap('cdRamoId',helpMap,'Producto'),
                    tooltip:getToolTipFromMap('cdRamoId',helpMap,'Producto'),
			        hasHelpIcon:getHelpIconFromMap('cdRamoId',helpMap),
					Ayuda:getHelpTextFromMap('cdRamoId',helpMap),
                    width: 300,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                    id:'cdRamoId',
                    onSelect: function (record) {
	                            				this.setValue(record.get('codigo'));
	                            				 var mStore = dsTramos;
										        var o = {start: 0};
										        mStore.baseParams = mStore.baseParams || {};
										        mStore.baseParams['cdRamo'] = formPanel.findById("cdRamoId").getValue();
										        
										        mStore.reload(
										                  {
										                      params:{start:0,limit:20},
										                       
										                      callback : function(r,options,success) {
										                          if (success) {
										                                 if (mStore.getCount() > 0 && mStore.getAt(0).get('swSubInc') == "N"){
						                            					 Ext.MessageBox.alert('Aviso','Producto No permite manejo de Subincisos.');
						                            					 formPanel.findById("indSituacId").setValue('I');
						                            					 formPanel.findById("indSituacId").setDisabled(true);    
				                            				              }else{
				                            				              		formPanel.findById("indSituacId").setDisabled(false);
				                            				              }
										                           
										                          }else{
										                          		Ext.Msg.alert('Error', "No se encontraron datos");
										                          }
										                      }
										
										                  }
										                );
	                            				
	                            				this.setValue(record.get('codigo'));
	                            				this.collapse();	
	                            		        }
	                            		        
                } ,
                 {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsIndicadorNumeracion,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indSituac',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSituacId',helpMap,'Indicador de Numeraci&oacute;n'),
                    tooltip:getToolTipFromMap('indSituacId',helpMap,'Indicador de Numeraci&oacute;n'),
			        hasHelpIcon:getHelpIconFromMap('indSituacId',helpMap),
					Ayuda:getHelpTextFromMap('indSituacId',helpMap),
                    width: 300,
                    emptyText:'Seleccione Indicador de Numeracion...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                    id:'indSituacId'
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
                    fieldLabel: getLabelFromMap('indSufPreId',helpMap,'Indicador (S/P)'),
                    tooltip:getToolTipFromMap('indSufPreId',helpMap,'Indicador (S/P)'),
			        hasHelpIcon:getHelpIconFromMap('indSufPreId',helpMap),
					Ayuda:getHelpTextFromMap('indSufPreId',helpMap),
                    width: 300,
                    emptyText:'Seleccione Indicador (S/P)...',
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
                fieldLabel: getLabelFromMap('indSufPreId',helpMap,'Valor'),
                tooltip:getToolTipFromMap('indSufPreId',helpMap,'Valor'),
			    hasHelpIcon:getHelpIconFromMap('indSufPreId',helpMap),
				Ayuda:getHelpTextFromMap('indSufPreId',helpMap),
                 name: 'dsSufPre',
                 disabled: true, 
                 width: 200,
                 id: 'dsSufPre'
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
                    fieldLabel: getLabelFromMap('indCalcId',helpMap,'Forma C&aacute;lculo Folio'),
                    tooltip:getToolTipFromMap('indCalcId',helpMap,'Forma C&aacute;lculo Folio'),                    
			        hasHelpIcon:getHelpIconFromMap('indCalcId',helpMap),
					Ayuda:getHelpTextFromMap('indCalcId',helpMap),
                    width: 300,
                    emptyText:'Seleccione Forma Calculo Folio...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                    id:'indCalcId',
                    onSelect: function (record) {
	                            				
	                            				if (record.get("id")=="1"){
	                            				
	                            				formPanel.findById("nmFolioIni").setDisabled(false);
	                            				
	                            							formPanel.findById("nmFolioIni").setDisabled(false);
	                            							formPanel.findById("nmFolioFin").setDisabled(false);
	                            								                            							
	                            							formPanel.findById("nmFolioIni").allowBlank = false;
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
               },
				formulaPanelAgregar,
               {
                 xtype: 'textfield',
                 fieldLabel: getLabelFromMap('nmFolioIni',helpMap,'Folio Inicial'),
                 tooltip:getToolTipFromMap('nmFolioIni',helpMap,'Folio Inicial'),
		         hasHelpIcon:getHelpIconFromMap('nmFolioIni',helpMap),
				 Ayuda:getHelpTextFromMap('nmFolioIni',helpMap),
                 name: 'nmFolioIni', 
                 id: 'nmFolioIni',
                 width: 200,
				 listeners: {
						    'change': function(){
						    	 validarFolios();
						        
						    }
						  }
				},
				
				{
				xtype: 'textfield',
				fieldLabel: getLabelFromMap('nmFolioFin',helpMap,'Folio Final'),
                tooltip:getToolTipFromMap('nmFolioFin',helpMap,'Folio Final'),
    		    hasHelpIcon:getHelpIconFromMap('nmFolioFin',helpMap),
				Ayuda:getHelpTextFromMap('nmFolioFin',helpMap),
				name: 'nmFolioFin', 
				id: 'nmFolioFin',
				width: 200,
				listeners: {
						    'change': function(){
						         validarFolios();
						    }
						  }
				}
				
			
            ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: getLabelFromMap('wndwAgrgr',helpMap,'Agrega un nuevo N&uacute;mero de Inciso'),
        width: 500,
        height:450,
        minWidth: 300,
        modal: true,
        minHeight: 100,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
                text : getLabelFromMap('nroInsBtnAdd',helpMap,'Guardar'),
                tooltip : getToolTipFromMap('nroInsBtnAdd',helpMap,'Guarda nueva Numeraci&oacute;n de Incisos'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            url : _ACTION_INSERTAR_NUMERO_INCISO,
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                                },
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                                },
                            waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });
                     } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                     }
                }
            },{
                text : getLabelFromMap('nroInsBtnAN',helpMap,'Guardar y Agregar'),
                tooltip : getToolTipFromMap('nroInsBtnAN',helpMap,'Guarda y Agrega nueva Numeraci&oacute;n de Incisos'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            url : _ACTION_INSERTAR_NUMERO_INCISO,
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                    
                                    dsProductos.removeAll();
                                    dsAseguradora.removeAll();
                                    
                                    dsFormaCalculoFolio.removeAll();
								    dsIndicadorNumeracion.removeAll();
								    dsIndicadorSP.removeAll();
								    
                                    
                                    dsFormaCalculoFolio.load();
								    dsIndicadorNumeracion.load();
								    dsIndicadorSP.load();
								    
								    
								    
								    formPanel.findById("cdElementoId").setValue('');
								    formPanel.findById("cdUniEcoId").setValue('');
								    formPanel.findById("cdRamoId").setValue('');
								    formPanel.findById("indSituacId").setValue('');
								    formPanel.findById("indSufPreId").setValue('');      
								    formPanel.findById("indCalcId").setValue('');   
								    
								    formPanel.findById("indSituacId").setDisabled(false);  
								    formPanel.findById("dsCalculo").setDisabled(false); 
								    Ext.getCmp('btnExpresion').setDisabled(false);
								    formPanel.findById("nmFolioIni").setDisabled(false);  
	                            	formPanel.findById("nmFolioFin").setDisabled(false);  
	                            	 
								    formPanel.findById("cdPerson").setValue('');
								    formPanel.findById("dsCalculo").setValue('');
								    formPanel.findById("dsSufPre").setValue('');
								    formPanel.findById("nmFolioIni").setValue('');
								    formPanel.findById("nmFolioFin").setValue('');      
                                },
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                                },
                            waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizaci&oacute;n de datos...')
                        });
                     } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                     }
                }
            },
             {
                 text : getLabelFromMap('nroInsBtnBack',helpMap,'Regresar'),
                tooltip : getToolTipFromMap('nroInsBtnBack',helpMap,'Regresar a la pantalla anterior'),
                 handler : function() {
                 window.close();
                    }
            }]
    	});

   function validarFolios(){
   		 
   		if (formPanel.findById("nmFolioIni").getValue()!="" && formPanel.findById("nmFolioFin").getValue()!="") {
   			if (eval(formPanel.findById("nmFolioIni").getValue()) >= eval(formPanel.findById("nmFolioFin").getValue())){
   			       Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400024', helpMap,'El folio de Inicio debe ser menor que el folio de Fin'));
   			       var folFin = eval(formPanel.findById("nmFolioIni").getValue()) + 1;
   			       formPanel.findById("nmFolioFin").setValue(folFin);	
   			}
   		}
   }; 

    
    dsFormaCalculoFolio.load({
    	callback:function(){
    		 dsIndicadorNumeracion.load({
    		 		callback:function(){
    		 			dsIndicadorSP.load({
    		 				callback:function(){
    		 					dsClientesCorp.load();
    		 				}
    		 			});
    		 		}
    		 });
    	}
    });
   
    

    window.show();

};

 
 
 // Funcion de Editar Numero de Inciso
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


   var dsIndicadorNumeracion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_INDICADOR_NUMERACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'indicadorNumeracionNroIncisos',
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
    
   var dsTramos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TRAMO
            }),
            reader: new Ext.data.JsonReader({
            root: 'numeroIncisosManagerListLoadTramo',
            id: 'swSubInc',
            successProperty:"success"
            },[
            {name: 'swSubInc', type: 'string',mapping:'swSubInc'}
              ]),
        remoteSort: true
    });
    
    
    
    //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {

            //indica el arreglo a leer
            root : 'numeroIncisosManagerList',

            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',

            //indica el resultado de la respuesta de la action
            successProperty : 'success'

        }, [ {
                name : 'cdElementoId1',mapping : 'cdElemento',type : 'string'
        },{
                name : 'dsElemenId', mapping : 'dsElemen', type : 'string'
        },{
                name : 'dsUniEcoId', mapping : 'dsUniEco', type : 'string'
        },{ 
               name : 'cdPerson',type : 'string',mapping : 'cdPerson'
        },{
               name : 'cdRamo1',type : 'string',mapping : 'cdRamo'
        },{
               name : 'cdSufPreCia',type : 'string',mapping : 'cdSufPreCia'
        },{
               name : 'cdUniEco1',type : 'string',mapping : 'cdUniEco'
        },{
               name : 'dsCalculo',type : 'string',mapping : 'dsCalculo'
        },{
               name : 'dsSufPre',type : 'string',mapping : 'dsSufPre'
        },{
               name : 'indCalc',type : 'string',mapping : 'indCalc'
        },{
               name : 'indSituac1',type : 'string',mapping : 'indSituac'
        },{
               name : 'indSufPre',type : 'string',mapping : 'indSufPre'
        },{
               name : 'nmFolioFin',type : 'string',mapping : 'nmFolioFin'
        },{
               name : 'nmFolioIni',type : 'string',mapping : 'nmFolioIni'
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
                		fieldLabel: getLabelFromMap('dsCalculoId',helpMap,'Formula'),
                		tooltip: getToolTipFromMap('dsCalculoId',helpMap,'Formula de C&aacute;lculo de Folio'),
			    		hasHelpIcon:getHelpIconFromMap('dsCalculoId',helpMap),
						Ayuda:getHelpTextFromMap('dsCalculoId',helpMap),
                		name: 'dsCalculo',
                		id: 'dsCalculoId',
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
                		id: 'btnExpresionId',
                		name: 'btnExpresionId',
                		buttonAlign: "left",
                		handler: function() {
                			//Si el campo Formula ya tiene valor, mostraremos la expresión con ése código, para que sea editada:
                			if(Ext.getCmp('dsCalculoId').getValue() != ""){
                				ExpresionesVentana2(Ext.getCmp('dsCalculoId').getValue(), Ext.getCmp('hidden-codigo-expresion-session').getValue());
                			}else{
                				var connect = new Ext.data.Connection();
								connect.request ({
									url:'atributosVariables/ObtenerCodigoExpresion.action',
									callback: function (options, success, response) {
										try{
											//alert(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											Ext.getCmp('hidden-codigo-expresion-numeracion-incisos').setValue(Ext.util.JSON.decode(response.responseText).codigoExpresion);
											ExpresionesVentana2(Ext.getCmp('hidden-codigo-expresion-numeracion-incisos').getValue(),Ext.getCmp('hidden-codigo-expresion-session').getValue());
											Ext.getCmp('dsCalculoId').setValue( Ext.getCmp('hidden-codigo-expresion-numeracion-incisos').getValue() );
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
						value:'EXPRESION_NUMERACION_INCISOS'
					},{
                		xtype:'hidden',                	
                		id:'hidden-codigo-expresion-numeracion-incisos',
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
            url : _ACTION_GET_NUMERO_INCISO,

            baseParams : {cdUniEco: record.get("cdUniEco"),
                          cdElemento: record.get("cdElemento"),
                          cdRamo: record.get("cdRamo"),
                          indSituac: record.get("indSituac")
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
                    id: 'cdPersonId'
                },{
                    xtype: 'textfield',
                    name : 'dsElemenId',
                    fieldLabel: getLabelFromMap('dsElemenId',helpMap,'Cliente'),
                    tooltip: getToolTipFromMap('dsElemenId',helpMap,'Cliente'),
			        hasHelpIcon:getHelpIconFromMap('dsElemenId',helpMap),
					Ayuda:getHelpTextFromMap('dsElemenId',helpMap),
                    disabled:true,
                    width: 200,
                    id: 'dsElemenId'
                },{
                    xtype: 'textfield',
                    name : 'dsUniEcoId',
                    fieldLabel: getLabelFromMap('dsUniEcoId',helpMap,'Aseguradora'),
                    tooltip: getToolTipFromMap('dsUniEcoId',helpMap,'Aseguradora'),
			        hasHelpIcon:getHelpIconFromMap('dsUniEcoId',helpMap),
					Ayuda:getHelpTextFromMap('dsUniEcoId',helpMap),
                    disabled:true,
                    width: 200,
                    id: 'dsUniEcoId'
                },
                //Se eliminaron combo aseguradora y combo cliente
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo1',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('cdRamoId1',helpMap,'Producto'),
                    tooltip: getToolTipFromMap('cdRamoId1',helpMap,'Producto'),
			        hasHelpIcon:getHelpIconFromMap('cdRamoId1',helpMap),
					Ayuda:getHelpTextFromMap('cdRamoId1',helpMap),
                    width: 200,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    id:'cdRamoId1',
                    onSelect: function (record) {
	                            				this.setValue(record.get('codigo'));
	                            				var mStore = dsTramos;
										        var o = {start: 0};
										        mStore.baseParams = mStore.baseParams || {};
										        mStore.baseParams['cdRamo'] = formPanel.findById("cdRamoId1").getValue();
										        
										        mStore.reload(
										                  {
										                      params:{start:0,limit:20},
										                       
										                      callback : function(r,options,success) {
										                          if (success) {
										                                 if (mStore.getAt(0).get('swSubInc') == "N"){
						                            					 Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400042',helpMap,'Producto No permite manejo de Sub-Incisos'));
						                            					 formPanel.findById("indSituacId1").setValue('1');
						                            					 formPanel.findById("indSituacId1").setDisabled(true);    
				                            				              }else{
				                            				              		formPanel.findById("indSituacId1").setDisabled(false);
				                            				              }
										                           
										                          }else{
										                          		//alert("fallo");
										                          }
										                      }
										
										                  }
										                );
	                            				
	                            				this.setValue(record.get('codigo'));
	                            				this.collapse();	
	                            		        }
	                            		        
                } ,
                 {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsIndicadorNumeracion,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indSituac1',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSituacId1',helpMap,'Indicador de Numeraci&oacute;n'),
                    tooltip: getToolTipFromMap('indSituacId1',helpMap,'Indicador de Numeraci&oacute;n'),
			        hasHelpIcon:getHelpIconFromMap('indSituacId1',helpMap),
					Ayuda:getHelpTextFromMap('indSituacId1',helpMap),
                    width: 200,
                    emptyText:'Seleccione Indicador de Numeracion...',
                    selectOnFocus:true,
                    forceSelection:true,
                    labelSeparator:'',
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                    id:'indSituacId1'
                },
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    id:'indSufPreId1',
                    store: dsIndicadorSP,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indSufPre',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('indSufPreId1',helpMap,'Indicador (S/P)'),
                    tooltip: getToolTipFromMap('indSufPreId1',helpMap,'Indicador (S/P)'),
			        hasHelpIcon:getHelpIconFromMap('indSufPreId1',helpMap),
					Ayuda:getHelpTextFromMap('indSufPreId1',helpMap),
                    forceSelection:true,
                    width: 200,
                    selectOnFocus:true,
                    allowBlank : false,
                    emptyText:'Seleccione Indicador (S/P)...',
                    onSelect: function (record) {
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
                    
	                            				if (record.get("id")=="N"){
	                            				
	                            							formPanel.findById("dsSufPreId").setValue('');
	                            							formPanel.findById("dsSufPreId").allowBlank = true;
	                            							formPanel.findById("dsSufPreId").setDisabled(true);
	                            				}
	                            				
	                            				if ((record.get("id")=="S")||(record.get("id")=="P")){
	                            				
	                            							formPanel.findById("dsSufPreId").allowBlank = false;
	                            							formPanel.findById("dsSufPreId").setDisabled(false);
	                            				}
	                            				}
                },
                {
                  xtype: 'textfield',
                  id: 'dsSufPreId',
                  fieldLabel: getLabelFromMap('dsSufPreId',helpMap,'Valor'),
                  tooltip: getToolTipFromMap('dsSufPreId',helpMap,'Valor'),
		          hasHelpIcon:getHelpIconFromMap('dsSufPreId',helpMap),
				  Ayuda:getHelpTextFromMap('dsSufPreId',helpMap),
                  width: 200,
                  name: 'dsSufPre' 
                  
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
                    fieldLabel: getLabelFromMap('indCalcId1',helpMap,'Forma C&aacute;lculo Folio'),
                    tooltip: getToolTipFromMap('indCalcId1',helpMap,'Forma C&aacute;lculo Folio'),
			        hasHelpIcon:getHelpIconFromMap('indCalcId1',helpMap),
					Ayuda:getHelpTextFromMap('indCalcId1',helpMap),
                    width: 200,
                    emptyText:'Seleccione Forma Calculo Folio...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                    id:'indCalcId1',
                    onSelect: function (record) {
	                            				
	                            				if (record.get("id")=="1"){
	                            						    
	                            							
	                            							formPanel.findById("nmFolioIniId").setDisabled(false);
	                            							formPanel.findById("nmFolioFinId").setDisabled(false);
	                            							
	                            							formPanel.findById("nmFolioIniId").allowBlank =  false;
	                            							formPanel.findById("nmFolioFinId").allowBlank = false;
	                            							
	                            							formPanel.findById("dsCalculoId").setValue('');
	                            							formPanel.findById("dsCalculoId").allowBlank = true;
	                            							formPanel.findById("dsCalculoId").setDisabled(true);
	                            							Ext.getCmp('btnExpresionId').setDisabled(true);
	                            				}
	                            				
	                            				if (record.get("id")=="2"){
	                            				
	                            							
	                            							formPanel.findById("nmFolioIniId").setDisabled(true);
	                            							formPanel.findById("nmFolioFinId").setDisabled(true);
	                            							
	                            							formPanel.findById("nmFolioIniId").setValue('');
	                            							formPanel.findById("nmFolioFinId").setValue('');
	                            							
	                            							
	                            							formPanel.findById("nmFolioIniId").allowBlank = true;
	                            							formPanel.findById("nmFolioFinId").allowBlank = true;
	                            							
	                            							formPanel.findById("dsCalculoId").allowBlank = false;
	                            							formPanel.findById("dsCalculoId").setDisabled(false);
	                            							Ext.getCmp('btnExpresionId').setDisabled(false);
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();	
	                            		        }
                } ,
                formulaPanelEditar,
				{
				xtype: 'textfield',
				fieldLabel: getLabelFromMap('nmFolioIniId',helpMap,'Folio Inicial'),
                tooltip: getToolTipFromMap('nmFolioIniId',helpMap,'Folio Inicial'),
 			    hasHelpIcon:getHelpIconFromMap('nmFolioIniId',helpMap),
				Ayuda:getHelpTextFromMap('nmFolioIniId',helpMap),
				name: 'nmFolioIni', 
				id: 'nmFolioIniId',
                allowBlank : false,
                heigth:150, 
                width: 200,
                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
				listeners: {
						    'change': function(){
						    	 validarFolios();
						        
						    }
						  }
				},
				
				{
				xtype: 'textfield',
				fieldLabel: getLabelFromMap('nmFolioFinId',helpMap,'Folio Final'),
                tooltip: getToolTipFromMap('nmFolioFinId',helpMap,'Folio Final'),
			    hasHelpIcon:getHelpIconFromMap('nmFolioFinId',helpMap),
				Ayuda:getHelpTextFromMap('nmFolioFinId',helpMap),
				name: 'nmFolioFin', 
				id: 'nmFolioFinId',
                allowBlank : false,
                heigth:150, 
                width: 200,
                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
				listeners: {
						    'change': function(){
						         validarFolios();
						    }
						  }
				}
				
            ]});

//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: getLabelFromMap('wndwEdtr',helpMap,'Editar N&uacute;mero de Inciso'),
        width: 500,
        height:450,
        minWidth: 300,
        minHeight: 100,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
            buttons : [ {
                text : getLabelFromMap('edNroInsBtnAdd',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('edNroInsBtnAdd',helpMap,'Guarda una nueva Numeraci&oacute;n de Incisos'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            url : _ACTION_GUARDAR_NUMERO_INCISO,
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                                },
                            waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizaci&oacute;n de datos...')
                        });
                     } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                     }
                }
            },{
                text : getLabelFromMap('edNroInsBtnAddNew',helpMap,'Guardar y Agregar'),
                tooltip: getToolTipFromMap('edNroInsBtnAddNew',helpMap,'Guarda y Agrega una nueva numeraci&oacute;n de incisos'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            url : _ACTION_GUARDAR_NUMERO_INCISO,
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                 	dsProductos.removeAll();
                                    
                                    dsFormaCalculoFolio.removeAll();
								    dsIndicadorNumeracion.removeAll();
								    dsIndicadorSP.removeAll();
								    
                                    
                                    dsFormaCalculoFolio.load();
								    dsIndicadorNumeracion.load();
								    dsIndicadorSP.load();
								    
								    
								    formPanel.findById("indSufPreId1").setValue('');      
								    formPanel.findById("indCalcId1").setValue('');   
								     
								    formPanel.findById("dsCalculoId").setDisabled(false);
								    Ext.getCmp('btnExpresionId').setDisabled(false);
								    formPanel.findById("nmFolioIniId").setDisabled(false);  
	                            	formPanel.findById("nmFolioFinId").setDisabled(false);  
	                            	 
								    formPanel.findById("cdPersonId").setValue('');
								    formPanel.findById("dsCalculoId").setValue('');
								    formPanel.findById("dsSufPreId").setValue('');
								    formPanel.findById("nmFolioIniId").setValue('');
								    formPanel.findById("nmFolioFinId").setValue('');      
                                },
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                                },
                            waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizaci&oacute;n de datos...')
                        });
                     } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                     }
                }
            },
             {
                 text : getLabelFromMap('edNroInsBtnBack',helpMap,'Regresar'),
                tooltip: getToolTipFromMap('edNroInsBtnBack',helpMap,'Regresa a la p&aacute;gina anterior'),
                 handler : function() {
                 window.close();
                    }
            }]
    	});

    //dsAseguradora.load({params: {cdElemento: record.get("cdElemento")}});
    dsProductos.load({
	                  params: {cdElemento: record.get("cdElemento") ,cdunieco: record.get('cdUniEco')}
	                 });
    //dsClientesCorp.load();
    dsFormaCalculoFolio.load({
    	callback:function(){
    		dsIndicadorNumeracion.load();
    	}
    });
    dsIndicadorSP.load();

   // formPanel.findById("cdElementoId1").setValue(record.get('cdElemento'));
   // formPanel.findById("cdUniEcoId1").setValue(record.get('cdUniEco'));
    formPanel.findById("cdRamoId1").setValue(record.get('cdRamo'));
    
    window.show();
     //se carga el formulario con los datos de la estructura a editar
        formPanel.form.load( {
                    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
                    success:function(){
                    
                    
                    
                                    if (formPanel.findById("indCalcId1").getValue()=="1"){
	                            				
	                            				formPanel.findById("nmFolioIniId").setDisabled(false);
	                            				
	                            							formPanel.findById("nmFolioIniId").setDisabled(false);
	                            							formPanel.findById("nmFolioFinId").setDisabled(false);
	                            								                            							
	                            							formPanel.findById("nmFolioIniId").allowBlank = false;
	                            							formPanel.findById("nmFolioFinId").allowBlank = false;
	                            							
	                            							formPanel.findById("dsCalculoId").allowBlank = true;
	                            							formPanel.findById("dsCalculoId").setValue('');
	                            							formPanel.findById("dsCalculoId").setDisabled(true);
	                            							Ext.getCmp('btnExpresionId').setDisabled(true);
	                            				     }
	                            				
	                            				if (formPanel.findById("indCalcId1").getValue()=="2"){
	                            				
	                            						    formPanel.findById("nmFolioIniId").allowBlank = true;
	                            							formPanel.findById("nmFolioFinId").allowBlank = true;
	                            							
	                            							formPanel.findById("nmFolioIniId").setValue('');
	                            							formPanel.findById("nmFolioFinId").setValue('');
	                            							
	                            							formPanel.findById("nmFolioIniId").setDisabled(true);
	                            							formPanel.findById("nmFolioFinId").setDisabled(true);
	                            							
	                            							formPanel.findById("dsCalculoId").allowBlank = false;
	                            							formPanel.findById("dsCalculoId").setDisabled(false);
	                            							Ext.getCmp('btnExpresionId').setDisabled(false);
	                            				}
	                            				
	                            				//if (record.get("id")=="N"){
	                            				if (formPanel.findById("indSufPreId1").getValue()=="N"){
	                            							formPanel.findById("dsSufPreId").setValue('');
	                            							formPanel.findById("dsSufPreId").allowBlank = true;
	                            							formPanel.findById("dsSufPreId").setDisabled(true);
	                            				}
	                            				
	                            				//if ((record.get("id")=="S")||(record.get("id")=="P")){
	                            				if ((formPanel.findById("indSufPreId1").getValue()=="S")||(formPanel.findById("indSufPreId1").getValue()=="S")){
	                            				
	                            							formPanel.findById("dsSufPreId").allowBlank = false;
	                            							formPanel.findById("dsSufPreId").setDisabled(false);
	                            				}

	                            				
	                            	
	                            	 var mStore = dsTramos;
										        var o = {start: 0};
										        mStore.baseParams = mStore.baseParams || {};
										        mStore.baseParams['cdRamo'] = formPanel.findById("cdRamoId1").getValue();
										        
										        mStore.reload(
										                  {
										                      params:{start:0,limit:20},
										                       
										                      callback : function(r,options,success) {
										                          if (success) {
										                                 if (mStore.getAt(0).get('swSubInc') == "N"){
						                            					 Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400042',helpMap,'Producto No permite manejo de Sub-Incisos'));
						                            					 formPanel.findById("indSituacId1").setValue('I');
						                            					 formPanel.findById("indSituacId1").setDisabled(true);    
				                            				              }else{
				                            				              		formPanel.findById("indSituacId1").setDisabled(false);
				                            				              }
										                           
										                          }else{
										                          		//alert("fallo");
										                          }
										                      }
										
										                  }
										                );
										                
	                            			//	this.setValue(record.get('id'));
	                            			//	this.collapse();
                    
                       }
                    
        });
        
      
      function validarFolios(){
   		 
   		if (formPanel.findById("nmFolioIniId").getValue()!="" && formPanel.findById("nmFolioFinId").getValue()!="") {
   			if (eval(formPanel.findById("nmFolioIniId").getValue()) >= eval(formPanel.findById("nmFolioFinId").getValue())){
   			       Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400024', helpMap,'El folio de Inicio debe ser menor que el folio de Fin'));
   			       var folFin = eval(formPanel.findById("nmFolioIniId").getValue()) + 1;
   			       formPanel.findById("nmFolioFinId").setValue(folFin);	
   			}
   		}
   }; 
   

};

        

	
});