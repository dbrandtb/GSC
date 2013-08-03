Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
    
    //Para indicar si luego de guardar se inicia o no una nueva fila en la grilla
    var agregarFila = false;


	/********* Comienzo del Store y el Reader del Form *********/
    var formReader = new Ext.data.JsonReader( {
            root : 'agrupacionList',
            totalProperty: 'totalCount',
			listeners: {
                            loadexception: function(proxy, store, response, e) {
                            	
                            }
                        },
            successProperty : '@success'

        }, [ {
            name : 'dsNombre',
            mapping : 'dsNombre',
            type : 'string'
        }, {
            name : 'dsUniEco',
            type : 'string',
            mapping : 'dsUniEco'
        }, {
            name : 'dsTipRam',
            type : 'string',
            mapping : 'dsTipRam'
        }, {
            name : 'dsRamo',
            type : 'string',
            mapping : 'dsRamo'
        }, {
            name : 'cdGrupo',
            type : 'string',
            mapping : 'cdGrupo'
     	}, {
     		name: 'dsAgrupa',
     		type: 'string',
     		mapping: 'dsAgrupa'
     	}, {
     		name: 'codigoElemento',
     		type: 'string',
     		mapping: 'cdPerson'
     	}, {
     		name: 'cdUniEco',
     		type: 'string',
     		mapping: 'cdUniEco'
     	}, {
     		name: 'cdRamo',
     		type: 'string',
     		mapping: 'cdRamo'
     	}, {
     		name: 'codTipo',
     		type: 'string',
     		mapping: 'cdTipo'
     	}	
     	]);
     
     var formStore = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_GET_AGRUPACIONES,
                waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')
            }),
            reader: formReader
        });		  	  	

	/********* Fin Comienzo del Store y el Reader del Form *********/

	/********* Comienza el form ********************************/
	var el_form = new Ext.FormPanel ({
			renderTo: 'formBusqueda',
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formBusquedaAgrPplId',helpMap,'Configurar Agrupaci&oacute;n por Rol')+'</span>',
            labelWidth : 75,            
            url : _ACTION_GET_AGRUPACIONES,
            frame : true,
            width : 600,
            bodyStyle:'background: white',
            //autoHeight: true,
            height: 130,
            waitMsgTarget : true,
            labelAlign:'right',
            layout: 'column',
            layoutConfig: {columns: 2, align: 'rigth'},
            store: formStore,
            reader: formReader,
            successProperty : 'success',
            items: [
			            {
			            xtype: 'hidden', 
			            id: 'codTipoId', 
			            name: 'codTipo'
			            },            			
            			{
            			layout: 'form',
            			//labelAlign: 'left',
            			columnWidth: .50,
            			items: [
				                  {
				                  xtype: 'textfield',
				                  fieldLabel: getLabelFromMap('dsNombre',helpMap,'Cliente'),
				                  tooltip:getToolTipFromMap('dsNombre',helpMap,'Cliente'), 
				                  hasHelpIcon:getHelpIconFromMap('dsNombre',helpMap),
								  Ayuda:getHelpTextFromMap('dsNombre',helpMap),
				                  id: 'dsNombre', 
				                  name: 'dsNombre', 
				                  disabled: true, 
				                  width: '180'
				                  },
				                  {xtype: 'hidden', id: 'codigoElemento', name: 'codigoElemento'},
				                  {xtype: 'hidden', id: 'IdCdRamo', name: 'cdRamo'},
				                  {xtype: 'hidden', id: 'IdCdUniEco', name: 'cdUniEco'}				                  
            					]
            			},
            			{layout: 'form',
            			labelAlign: 'right',
            			columnWidth: .50,
            			items: [
            						{
            						xtype: 'textfield',
				                    fieldLabel: getLabelFromMap('dsUniEco',helpMap,'Aseguradora'),
				                  	tooltip:getToolTipFromMap('dsUniEco',helpMap,'Aseguradora'),
				                  	hasHelpIcon:getHelpIconFromMap('dsUniEco',helpMap),
								  	Ayuda:getHelpTextFromMap('dsUniEco',helpMap),
				                  	name: 'dsUniEco', 
				                  	id: 'dsUniEco', 
				                  	disabled: true, 
				                  	width: '180'
				                  	}
            					]
            			},
            			{layout: 'form',
            			columnWidth: .50,
            			//labelAlign: 'left',
            			items: [
				                    {
				                    xtype: 'textfield', 
				                    fieldLabel: getLabelFromMap('dsTipRam',helpMap,'Tipo Ramo'),
				                    tooltip:getToolTipFromMap('dsTipRam',helpMap,'Tipo Ramo'),
				                    hasHelpIcon:getHelpIconFromMap('dsTipRam',helpMap),
								    Ayuda:getHelpTextFromMap('dsTipRam',helpMap),
				                    id: 'dsTipRam', 
				                    name: 'dsTipRam', 
				                    disabled: true, 
				                    width: '180'
				                    }
            					]
            			},
            			{layout: 'form',
            			labelAlign: 'right',
            			columnWidth: .50, //Para 2 columnas
            			items: [
            						{
            						xtype: 'textfield',  
				                    fieldLabel: getLabelFromMap('dsRamo',helpMap,'Producto'),
				                    tooltip:getToolTipFromMap('dsRamo',helpMap,'Producto'),
				                    hasHelpIcon:getHelpIconFromMap('dsRamo',helpMap),
								    Ayuda:getHelpTextFromMap('dsRamo',helpMap),
				                    id: 'dsRamo', 
				                    name: 'dsRamo', 
				                    disabled: true, 
				                    width: '180'
				                    }
            					]
            			},
	        			{
	        				layout: 'form',
	        				//labelAlign: 'left',
	        				columnWidth: .50,
	        				items: [
	        						{
	        						xtype: 'textfield',  
				                    fieldLabel: getLabelFromMap('dsAgrupa',helpMap,'Tipo de Agrupaci&oacute;n'),
				                    tooltip:getToolTipFromMap('dsAgrupa',helpMap,'Tipo de Agrupaci&oacute;n'),
				                    hasHelpIcon:getHelpIconFromMap('dsAgrupa',helpMap),
								    Ayuda:getHelpTextFromMap('dsAgrupa',helpMap),
				                    name: 'dsAgrupa', 
				                    id: 'dsAgrupa', 
				                    disabled: true, 
				                    width: '180'
				                    }
	        					   ]
	        			}
            		]
	});
	/********* Fin del form ************************************/

	/********* Definición Stores Para Combos *******/
	 var dsPapeles = new Ext.data.Store({
			     proxy: new Ext.data.HttpProxy({
			     url: _ACTION_OBTENER_PAPELES
		       	}),
		  		 reader: new Ext.data.JsonReader({
		  		 	root: 'agrupacionPapel',
		  		 	id: 'codigo'
		  		 },
		  		 [{name: 'codigo', type: 'string',mapping:'codigo'},
		  		  {name: 'descripcion', type: 'string',mapping:'descripcion'}
		  		 ])
		  		 });
	
	 var dsAseguradora = new Ext.data.Store({
			     proxy: new Ext.data.HttpProxy({
			     url: _ACTION_OBTENER_ASEGURADORA
		       	}),
		  		 reader: new Ext.data.JsonReader({
		  		 	root: 'aseguradoraComboBox',
		  		 	id: 'codigo'
		  		 },
		  		 [{name: 'codigo', type: 'string',mapping:'codigo'},
		  		  {name: 'descripcion', type: 'string',mapping:'descripcion'}
		  		 ]),
		  		 remoteSort: true,
		  		 baseParams: {cdElemento: el_form.findById('codigoElemento').getValue()}
		  		 });
	 var dsProductos = new Ext.data.Store({
			     proxy: new Ext.data.HttpProxy({
			     url: _ACTION_OBTENER_PRODUCTOS
		       	}),
		  		 reader: new Ext.data.JsonReader({
		  		 	root: 'productosComboBox',
		  		 	id: 'codigo'
		  		 },
		  		 [{name: 'codigo', type: 'string',mapping:'codigo'},
		  		  {name: 'descripcion', type: 'string',mapping:'descripcion'}
		  		 ])
		  		 });
	var comboPapeles = new Ext.form.ComboBox({
			                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			                    store: dsPapeles,
			                    anchor:'100%',
			                    displayField:'descripcion',
			                    valueField: 'codigo',
			                    hiddenName: 'codigoPapel',
			                    typeAhead: true,
			                    triggerAction: 'all',
			                    emptyText:'Seleccionar Papel...',
			                    selectOnFocus:true,
			                    forceSelection:true,
			                    allowBlank:false,
			                    labelSeparator:''
	           			});
	 var comboAseguradora = new Ext.form.ComboBox({
			                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			                    store: dsAseguradora,
			                    anchor:'100%',
			                    displayField:'descripcion',
			                    valueField: 'codigo',
			                    hiddenName: 'codigoAseguradora',
			                    typeAhead: true,
			                    triggerAction: 'all',
			                    emptyText:'Seleccionar Producto...',
			                    forceSelection:true,
			                    selectOnFocus:true,
			                    labelSeparator:''
	           			});
	 var comboProducto = new Ext.form.ComboBox({
			                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			                    store: dsProductos,
			                    anchor:'100%',
			                    displayField:'descripcion',
			                    valueField: 'codigo',
			                    hiddenName: 'codigoProducto',
			                    typeAhead: true,
			                    triggerAction: 'all',
			                    forceSelection:true,
						         lazyRender:   true,
			                    emptyText:'Seleccionar Producto...',
			                    selectOnFocus:true,
			                    labelSeparator:''
	           			});
	/********* Fin Definición Stores Para Combos*******/

	/********* Comienzo del grid *****************************/
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			dataIndex: 'cdAgrRol',
	    			hidden: true
	    		},
	    		{
	    			dataIndex: 'cdPolMtra',
	    			hidden: true
	    		}, {
		           	header: getLabelFromMap('agrPapCmAgrniv',helpMap,'Agrupar en nivel'),
		           	tooltip: getToolTipFromMap('agrPapCmAgrniv',helpMap,'Agrupar en nivel'),
		           	dataIndex: 'cdAgrupa',
	           		sortable: true,
		           	width: 120,
		           	editor: new Ext.form.NumberField({
		           				allowBlank: true,
		           				maxLength:2,
		           				id: 'IdNivel'
		           		})
	        	},{
		           	header: getLabelFromMap('agrPapCmPap',helpMap,'Roles'),
		           	tooltip: getToolTipFromMap('agrPapCmPap',helpMap,'Roles'),
		           	dataIndex: 'cdRol',
	           		sortable: true,
		           	width: 145,
		           	editor: comboPapeles,
	           		renderer: renderComboEditor(comboPapeles)
	           	}, {
		           	header: getLabelFromMap('agrPapCmAgrniv',helpMap,'P&oacute;liza Aseguradora'),
		           	tooltip: getToolTipFromMap('agrPapCmAgrniv',helpMap,'P&oacute;liza Aseguradora'),
		           	dataIndex: 'nmPoliEx',
	           		sortable: true,
		           	width: 140/*,
		           	editor: new Ext.form.TextField({
		           				allowBlank: true,
		           				id: 'IdNmPoliEx'
		           		})*/
	        	}, {
		           	header: getLabelFromMap('agrPapCmFchInc',helpMap,'Fecha Inicio'),
		           	tooltip: getToolTipFromMap('agrPapCmFchInc',helpMap,'Fecha Inicial'),
		           	dataIndex: 'feInicio',
	           		sortable: true,
		           	width: 80,
		           	format: 'd/m/Y'/*,
		           	editor: new Ext.form.DateField({name: 'feInicio', format: 'd/m/Y'}),
		           	renderer: function(val) {
		           			//Chequea que val != null y que es una fecha válida para JS.
		           			if (val && val instanceof Date) {
		           				return val.format ('d/m/Y');
		           			}
		           			return val;
		           	}*/
	        	}, {
		           	header: getLabelFromMap('agrPapCmFchFn',helpMap,'Fecha Fin'),
		           	tooltip: getToolTipFromMap('agrPapCmFchFn',helpMap,'Fecha Fin'),
		           	dataIndex: 'feFin',
	           		sortable: true,
		           	width: 80,
		           	format: 'd/m/Y'/*,
		           	editor: new Ext.form.DateField({name: 'feFin', format: 'd/m/Y'}),
		           	renderer: function(val) {
		           			//Chequea que val != null y que es una fecha válida para JS.
		           			if (val && val instanceof Date) {
		           				return val.format ('d/m/Y');
		           			}
		           			return val;
		           	}*/
		           	
	        	}/*,{
		           	header: getLabelFromMap('agrPapCmAseg',helpMap,'Aseguradora'),
		           	tooltip: getToolTipFromMap('agrPapCmAseg',helpMap,'Aseguradora'),
	           		sortable: true,
	           		width: 130,
		           	dataIndex: 'cdUniEco',
		           	editor: comboAseguradora,
		           	renderer: renderComboEditor(comboAseguradora)
	           	},
	           	{
	           		header: getLabelFromMap('agrPapCmPro',helpMap,'Producto'),
		           	tooltip: getToolTipFromMap('agrPapCmPro',helpMap,'Producto'),
	           		sortable: true,
	           		width: 130,
	           		dataIndex: 'cdRamo',
		           	editor: comboProducto,
		           	renderer: renderComboEditor(comboProducto)
	           	}*/
	           	]);
	           	
		//Fin Definición Column Model
		//Crea el Store
		var filaGrilla = new Ext.data.Record.create([
				{name: 'cdAgrRol', mapping:'cdAgrRol', type: 'string'},
				{name: 'cdAgrupa', mapping: 'cdAgrupa', type: 'string'},
				{name: 'cdRol', mapping: 'cdRol', type: 'string'},
				{name: 'cdRamo', mapping: 'cdRamo', type: 'string'},
				{name: 'cdUniEco', mapping: 'cdUniEco', type: 'string'},
				{name: 'cdPolMtra', mapping: 'cdPolMtra', type: 'string'},
				{name: 'nmPoliEx', mapping : 'nmPoliEx', type : 'string'},
				{name: 'feInicio', mapping: 'feInicio', type: 'string'},
				{name: 'feFin', mapping: 'feFin', type: 'string'}				
		]);
		var readerGrilla = new Ext.data.JsonReader({
		            	root:'MListaAgrupacion',
		            	totalProperty: 'totalCount',
			            successProperty : 'success'
			        },
			        	filaGrilla
			        );
		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_PAPELES,
						waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
						waitMsg: getLabelFromMap('400028',helpMap,'Leyendo datos ...')
		                }),
		                reader: readerGrilla
		        });
				return store;
		 	}
		//Fin Crea el Store

	/***** Crea una nueva fila editable en la grilla *****/
	function addGridNewRecord () {
		var new_record = new filaGrilla({
							cdAgrupa: '',
							cdRol: '',
							cdRamo: '',
							cdUniEco: '',
							nmPoliEx: '',
							feInicio: '',
							feFin: ''
						});
		grilla.stopEditing();
		grilla.store.insert(0, new_record);
		grilla.startEditing(0, 0);
	}
	
	var grilla;
	function createGrid(){
		grilla= new Ext.grid.EditorGridPanel({
	        el:'gridElementos',
	        store:crearGridStore(),
	        //reader: readerGrilla,
	        //title: '<span style="height:10">Listado</span>',
	        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        buttonAlign:'center',
			border:true,
			width: 600,
	        cm: cm,
	        clicksToEdit:1,
	        successProperty: 'success',
	        stripeRows: true,
	        collapsible: true,
	        loadMask: {msg: getLabelFromMap('400028',helpMap,'Leyendo datos ...'), disabled: false},
			buttons:[
		        		{
		            		text: getLabelFromMap('agrPapBtnAdd',helpMap,'Agregar'),
		           			tooltip: getToolTipFromMap('agrPapBtnAdd',helpMap,'Agregar'),
		            		handler: function() {
		            					agregarFila = true;
		            					agregar();
		            				}
		            	},
		        		{
		        			text: getLabelFromMap('agrPapBtnSave',helpMap,'Guardar'),
		           			tooltip: getToolTipFromMap('agrPapBtnSave',helpMap,'Guardar'),
		            		handler: function(){
		            					agregarFila = false;
		            					agregar();
		            				}
		            	},
		            	/*{
		            		text: getLabelFromMap('agrPapBtnDel',helpMap,'Borrar'),
		           			tooltip: getToolTipFromMap('agrPapBtnDel',helpMap,'Borrar'),
		            		handler: function(){
			                 			if (grilla.getSelectionModel().getSelections().length > 0){
			                  				if (grilla.getSelectionModel().getSelected().get("cdAgrupa")!="" || grilla.getSelectionModel().getSelected().get("cdRol")!=""){
			                  				     grilla.store.remove(grilla.getSelectionModel().getSelected());
			                       			}
			                 			}
			                   	  	}
		            	},*/
		            	
		            	{
		            		text: getLabelFromMap('agrPapBtnDel',helpMap,'Eliminar'),
		           			tooltip: getToolTipFromMap('agrPapBtnDel',helpMap,'Eliminar'),
		            		/*handler: function(){
			                 		if (getSelectedKey(grilla, "cdAgrRol")!="" || CODIGO_CONFIGURACION !=""){
			                  		    borrar(getSelectedRecord(grilla));
			                  		} else {
                        				Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                    				}
			                }*/
			                handler: function(){
			                	//alert(grilla.getSelectionModel().getSelected().get("cdAgrupa"));
								if (!grilla.getSelectionModel().getSelected()) {
									Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
					   			}
					   			else
					   			if (grilla.getSelectionModel().getSelected().get("cdAgrupa")==""){
					   				//alert(1);
					   				grilla.store.remove(grilla.getSelectionModel().getSelected());
					   			}else{
					   	    		   //alert(2); 
					   	    		   borrar(getSelectedRecord(grilla));
					   			}
			               }
			           },
			             {
		            		text: getLabelFromMap('agrPapBtnAsgnPlz',helpMap,'Asignar Poliza'),
		           			tooltip: getToolTipFromMap('agrPapBtnAsgnPlz',helpMap,'Asignar Poliza'),
		            		handler: function(){
		            					//alert(Ext.getCmp('dsNombre').getValue());
		            					//alert(Ext.getCmp('dsUniEco').getValue());
		            					//alert(Ext.getCmp('dsRamo').getValue());
		            					/*
		            									                  {xtype: 'hidden', id: 'codigoElemento', name: 'codigoElemento'},
				                  {xtype: 'hidden', id: 'IdCdRamo', name: 'cdRamo'},
				                  {xtype: 'hidden', id: 'IdCdUniEco', name: 'cdUniEco'}				                  
 
		            					*/
		            					
		            					if (!grilla.getSelectionModel().getSelected()) {
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
					   					}
					   					else{
					   						if (grilla.getSelectionModel().getSelected().get("cdAgrRol")==""){
					   							//alert(1);
					   							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'No existe el campo cdAgrRol'));
					   						}else{
					   	    		   			//alert(grilla.getSelectionModel().getSelected().get("cdAgrRol")); 
					   	    		   			var _descripcionNivel= Ext.getCmp('dsNombre').getValue();
					   	    		   			var _descripcionAseguradora= Ext.getCmp('dsUniEco').getValue();
					   	    		   			var _descripcionProducto= Ext.getCmp('dsRamo').getValue();
					   	    		   			//alert (_descripcionNivel);
					   	    		   			//alert (_descripcionAseguradora);
					   	    		   			//alert (_descripcionProducto);
					   	    		   			
					   	    		   			window.location = _ACTION_IR_ASIGNAR_POLIZAS + '?descripcionNivel=' + _descripcionNivel + '&codigoConfiguracion=' + CODIGO_CONFIGURACION +
			                 							'&descripcionAseguradora=' + _descripcionAseguradora + '&descripcionProducto=' + _descripcionProducto +
			                 							'&codigoAgrupacion='+grilla.getSelectionModel().getSelected().get("cdAgrRol")+'&codigoTipo='+Ext.getCmp('codTipoId').getValue();
					   						}
		            					}
			                 			
			                   	  	}
		            	},
			                   	  			                       			                     	
		            	{
		            		text: getLabelFromMap('agrPapBtnBack',helpMap,'Regresar'),
		           			tooltip: getToolTipFromMap('agrPapBtnBack',helpMap,'Regresar'),
		            		handler:function(){
		            			window.location = _ACTION_IR_AGRUPACION_POLIZAS;
		                	}
		            	}
	            	],
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: store, //crearGridStore(),
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	
	    grilla.render();
	}

	function reloadGrid(){
		var _params;
		
		_params = {codConfiguracion: CODIGO_CONFIGURACION};
		reloadComponentStore(grilla, _params, cbkReload);	
	}
	function cbkReload(r, options, success, _store) {
		if (success){
			if (agregarFila) addGridNewRecord();
		}else {
			//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
			grilla.store.removeAll();
		}
	}
	/********* Fin del grid **********************************/
	
	el_form.render();
	createGrid();
	el_form.form.load({
				params: {codigoConfiguracion: CODIGO_CONFIGURACION},
				//waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
				//waitMsg: getLabelFromMap('400028',helpMap,'Cargando datos ...'),
				success: function () {
								///Carga el Store de Aseguradoras con el código del cliente seleccionado
								dsAseguradora.baseParams = dsAseguradora.baseParams || {};
								dsAseguradora.baseParams['cdElemento'] = el_form.findById('codigoElemento').getValue();
								dsAseguradora.reload();
								///Fin Carga el Store de Aseguradoras con el código del cliente seleccionado
								grilla.store.load({
										params: {codConfiguracion: CODIGO_CONFIGURACION,
												 start: 0,
												 limit: itemsPerPage},
										callback: function (r, options, success) {
													if (!success) {
													}

													if (CODIGO_POLMTRA!="")
													{
														//alert(CODIGO_POLMTRA);
														count=grilla.store.getCount();
														for (var i=0; i< count; i++)
														{
															 records=grilla.store.data.items[i].data;													 
															 if (records.cdAgrRol==CODIGO_AGRUPACION)
															 {
															 	//alert(2);
															 	//console.log(grilla.store.data.items[i].data)
															 	actualizar(records);
															 	break;
															 }
															 //console.log(grilla.store.data.items[i].data.cdAgrRol);
														}
													}
													/*else
													{
													 alert(CODIGO_POLMTRA);
													}
													//console.log(grilla.store.data.items[0].data.cdAgrRol);*/
												}
									});
											
						},
				failure: function (form, action) {
							try{
								Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(action.response.responseText).actionErrors[0]);
							} catch (e) {
								Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), e.description);
							}
						}
		});
			
  
    function borrar(record) {
					var conn = new Ext.data.Connection();
				
					Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
					{
	           	       if (btn == "yes")
	           	        {
	           	        	var _params = {
				              cdAgrupa: CODIGO_CONFIGURACION ,
				              cdRol: record.get('cdAgrRol')
				          	};
	           	        	execConnection(_ACTION_BORRAR_ROL, _params, cbkBorrar);

	                	}
					})

	};
   	function cbkBorrar (_success, _message) {
   		if (!_success) {
   			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
   		}else {
   			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
   		}
   	} 
	


	/////Guarda los elementos de la grilla
	function agregar () {
		var _params = "codigoConfiguracion=" + CODIGO_CONFIGURACION + "&";
		grilla.stopEditing();
		var records = grilla.store.getModifiedRecords();
		if (records.length > 0) {
			for (var i=0; i<records.length; i++) {
				_params += "lista[" + i + "].cdAgrupa=" + records[i].get('cdAgrupa') + "&lista[" + i + "].cdAgrRol=" + ((records[i].get('cdAgrRol')==undefined)?"":records[i].get('cdAgrRol')) + "&lista[" + i + "].cdRol=" + records[i].get('cdRol') + "&lista[" + i + "].cdUniEco=" + records[i].get('cdUniEco') + "&lista[" + i + "].cdRamo=" + records[i].get('cdRamo') + "&lista[" + i + "].cdPolMtra=" + ((records[i].get('cdPolMtra')==undefined)?"":records[i].get('cdPolMtra')) +"&";
			}
			execConnection(_ACTION_GUARDAR_DATOS_GRILLA, _params, cbkGuardar);
		} else {
			if (agregarFila) {
				addGridNewRecord();
			}
		}
	}
	

	function actualizar(records){
		var _params = "codigoConfiguracion=" + CODIGO_CONFIGURACION + "&cdAgrupa="+records.cdAgrupa+"&cdAgrRol="+CODIGO_AGRUPACION+"&cdRol="+records.cdRol+"&cdUniEco="+records.cdUniEco+"&cdRamo="+records.cdRamo+"&cdPolMtra="+CODIGO_POLMTRA;
		execConnection(_ACTION_ACTUALIZAR_DATOS_GRILLA, _params, cbkGuardar);
	}	
	
	function cbkGuardar (success, errorMessages) {
		if (!success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), errorMessages);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), errorMessages, function() {reloadGrid()}); 
			grilla.store.commitChanges();
		}
	}
	
	dsProductos.load();
	dsPapeles.load();
	

});
