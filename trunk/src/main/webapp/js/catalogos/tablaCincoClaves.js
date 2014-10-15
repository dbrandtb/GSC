/**
 * Grid excel para la Tabla de Cinco Claves
 */
Ext.onReady(function() {
	
	
	var  _NUM_MAX_FILAS = 1000;
	
	var loadMaskTabla = new Ext.LoadMask('mainDivTabs', {msg:"Cargando Tabla..."});
	loadMaskTabla.show();
	
	//Models:
	
    Ext.define('CabeceraClaveModel', {
        extend: 'Ext.data.Model',
        fields: ['DSCLAVE1','SWFORMA1','DSFORMA','NMLMIN1','NMLMAX1','NUMCLAVE']
    });

    Ext.define('CabeceraAtributoModel', {
        extend: 'Ext.data.Model',
        fields: ['CDATRIBU','DSATRIBU','SWFORMAT','DSFORMAT','NMLMIN','NMLMAX']
    });
    
    Ext.define('CincoClavesModel', {
        extend: 'Ext.data.Model',
        fields: ['NMTABLA','OTCLAVE1','OTCLAVE2','OTCLAVE3','OTCLAVE4','OTCLAVE5',
        		 {name: 'FEDESDE', defaultValue: Ext.Date.format(new Date(), 'd/m/Y')},
        		 {name: 'FEHASTA', defaultValue: Ext.Date.format(new Date(), 'd/m/Y')},
        		 'OTVALOR01','OTVALOR02','OTVALOR03','OTVALOR04','OTVALOR05',
        		 'OTVALOR06','OTVALOR07','OTVALOR08','OTVALOR09','OTVALOR10',
        		 'OTVALOR11','OTVALOR12','OTVALOR13','OTVALOR14','OTVALOR15',
        		 'OTVALOR16','OTVALOR17','OTVALOR18','OTVALOR19','OTVALOR20',
        		 'OTVALOR21','OTVALOR22','OTVALOR23','OTVALOR24','OTVALOR25','OTVALOR26']
    });
	
    
    //Stores:
    
    var storeCabecerasClaves = new Ext.data.Store({
        model: 'CabeceraClaveModel',
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_CABECERAS_CLAVES,
            reader: {
                type: 'json',
                root: 'loadList'
            }
        }
    });

    var storeCabecerasAtributos = new Ext.data.Store({
        model: 'CabeceraAtributoModel',
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_CABECERAS_ATRIBUTOS,
            reader: {
                type: 'json',
                root: 'loadList'
            }
        }
    });
    
    var storeTablaCincoClaves = new Ext.data.Store({
        model: 'CincoClavesModel',
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_VALORES_TABLA_CINCO_CLAVES,
            timeout: 100000,
            reader: {
                type: 'json',
                root: 'loadList'
            }
        }
    });
    
    
    // Create an instance of the Spread panel
    var panelValoresTabCincoClaves = new Spread.grid.Panel({
        store: storeTablaCincoClaves,
        height: 600,
        tbar: [{
        	icon: _CONTEXT + '/resources/fam3icons/icons/table_edit.png',
            text: 'Habilitar edici&oacute;n',
            handler: function() {
                panelValoresTabCincoClaves.setEditable(true);
            }
        }, {
        	icon: _CONTEXT + '/resources/fam3icons/icons/lock.png',
            text: 'Deshabilitar edici&oacute;n',
            handler: function() {
                panelValoresTabCincoClaves.setEditable(false);
            }
        },'-',{
            xtype      : 'numberfield',
            name       : 'rowsToCreate',
            minValue   : 0,
            maxValue   : 100,
            width      : 100
        },{
        	icon: _CONTEXT + '/resources/fam3icons/icons/table_add.png',
            text: 'Agregar Filas',
            handler: function() {
            	if(!this.previousSibling().isValid()){
                    Ext.Msg.alert('Aviso', 'Ingrese un n&uacute;mero v&aacute;lido');
                    return;
                }
                var numFilas = this.previousSibling().getValue();
                
                var totalFilas = storeTablaCincoClaves.getCount() + numFilas;
                if(totalFilas > _NUM_MAX_FILAS){
                	mensajeWarning("El valor m&aacute;ximo de filas a editar es: " + _NUM_MAX_FILAS);
                	return;
                }
                
                for(var count=0 ;count<numFilas; count++){
        			storeTablaCincoClaves.insert(storeTablaCincoClaves.getCount(),new CincoClavesModel());
        		}
                panelValoresTabCincoClaves.setEditable(true);//Comienza a Mode Edicion de la tabla
            }
        },{
        	icon: _CONTEXT + '/resources/fam3icons/icons/table_delete.png',
            text: 'Eliminar Fila/Clave',
            handler: function(btn,e) {
            	Ext.Msg.show({
    		            title: 'Aviso',
    		            msg: '&iquest;Esta seguro que desea eliminar la fila de la celda seleccionada?',
    		            buttons: Ext.Msg.YESNO,
    		            fn: function(buttonId, text, opt) {
    		            	if(buttonId == 'yes') {
    		            		var rowIndex = panelValoresTabCincoClaves.getSelectionModel().getCurrentFocusPosition().row;
                				storeTablaCincoClaves.removeAt(rowIndex);
    		            	}
            			},
    		            animateTarget: btn,
    		            icon: Ext.Msg.QUESTION
        		});
            }
        }],
        // You can supply your own viewConfig to change the config of Spread.grid.View!
        //viewConfig: {
            //stripeRows: true
        //},
        listeners: {
        	render: function(grid, eOpts) {
        		cambiarEncabezados(grid);
        	}
        },
        editable: false,
        editModeStyling: true,
        enableKeyNav: true,
        columns: [
        	{
        		text: '#', xtype: 'rownumberer', sortable: false, editable: false, resizable: true, width: 40,
        		tdCls: Ext.baseCSSPrefix + 'grid-cell-header ' + Ext.baseCSSPrefix + 'grid-cell-special'
        	},
            {text: 'NMTABLA',     dataIndex: 'NMTABLA', hidden: true, menuDisabled: true, sortable: false, editable: false},
            {dataIndex: 'OTCLAVE1', itemId: 'OTCLAVE1', hidden: true, menuDisabled: true, sortable: false,
            	cellwriter: function(newValue, position) {
		           /**
		    		 *Para inhabilitar la edicion de rows que ya esten en la Base de Datos 
		    		 */
					if(!position.record.phantom){
		    			return position.record.get('OTCLAVE1');
		    		}
		           return newValue;
		       }
            },
            {dataIndex: 'OTCLAVE2', itemId: 'OTCLAVE2', hidden: true, menuDisabled: true, sortable: false,
            	cellwriter: function(newValue, position) {
		           /**
		    		 *Para inhabilitar la edicion de rows que ya esten en la Base de Datos 
		    		 */
					if(!position.record.phantom){
		    			return position.record.get('OTCLAVE2');
		    		}
		           return newValue;
		       }
            },
            {dataIndex: 'OTCLAVE3', itemId: 'OTCLAVE3', hidden: true, menuDisabled: true, sortable: false,
            	cellwriter: function(newValue, position) {
		           /**
		    		 *Para inhabilitar la edicion de rows que ya esten en la Base de Datos 
		    		 */
					if(!position.record.phantom){
		    			return position.record.get('OTCLAVE3');
		    		}
		           return newValue;
		       }
            },
            {dataIndex: 'OTCLAVE4', itemId: 'OTCLAVE4', hidden: true, menuDisabled: true, sortable: false,
            	cellwriter: function(newValue, position) {
		           /**
		    		 *Para inhabilitar la edicion de rows que ya esten en la Base de Datos 
		    		 */
					if(!position.record.phantom){
		    			return position.record.get('OTCLAVE4');
		    		}
		           return newValue;
		       }
            },
            {dataIndex: 'OTCLAVE5', itemId: 'OTCLAVE5', hidden: true, menuDisabled: true, sortable: false,
            	cellwriter: function(newValue, position) {
		           /**
		    		 *Para inhabilitar la edicion de rows que ya esten en la Base de Datos 
		    		 */
					if(!position.record.phantom){
		    			return position.record.get('OTCLAVE5');
		    		}
		           return newValue;
		       }
            },
            {text: 'Fecha inicio', dataIndex: 'FEDESDE', menuDisabled: true, sortable: false,
            	cellwriter: function(newValue, position) {
		           /**
		    		 *Para inhabilitar la edicion de rows que ya esten en la Base de Datos 
		    		 */
					if(!position.record.phantom){
		    			return position.record.get('FEDESDE');
		    		}
		           return newValue;
		       }
            },
            {text: 'Fecha fin',    dataIndex: 'FEHASTA', menuDisabled: true, sortable: false,
            	cellwriter: function(newValue, position) {
		           /**
		    		 *Para inhabilitar la edicion de rows que ya esten en la Base de Datos 
		    		 */
					if(!position.record.phantom){
		    			return position.record.get('FEHASTA');
		    		}
		           return newValue;
		       }
            },
            {text: 'OTVALOR1',  itemId: 'OTVALOR1',  dataIndex: 'OTVALOR01', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR2',  itemId: 'OTVALOR2',  dataIndex: 'OTVALOR02', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR3',  itemId: 'OTVALOR3',  dataIndex: 'OTVALOR03', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR4',  itemId: 'OTVALOR4',  dataIndex: 'OTVALOR04', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR5',  itemId: 'OTVALOR5',  dataIndex: 'OTVALOR05', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR6',  itemId: 'OTVALOR6',  dataIndex: 'OTVALOR06', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR7',  itemId: 'OTVALOR7',  dataIndex: 'OTVALOR07', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR8',  itemId: 'OTVALOR8',  dataIndex: 'OTVALOR08', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR9',  itemId: 'OTVALOR9',  dataIndex: 'OTVALOR09', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR10', itemId: 'OTVALOR10', dataIndex: 'OTVALOR10', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR11', itemId: 'OTVALOR11', dataIndex: 'OTVALOR11', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR12', itemId: 'OTVALOR12', dataIndex: 'OTVALOR12', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR13', itemId: 'OTVALOR13', dataIndex: 'OTVALOR13', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR14', itemId: 'OTVALOR14', dataIndex: 'OTVALOR14', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR15', itemId: 'OTVALOR15', dataIndex: 'OTVALOR15', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR16', itemId: 'OTVALOR16', dataIndex: 'OTVALOR16', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR17', itemId: 'OTVALOR17', dataIndex: 'OTVALOR17', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR18', itemId: 'OTVALOR18', dataIndex: 'OTVALOR18', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR19', itemId: 'OTVALOR19', dataIndex: 'OTVALOR19', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR20', itemId: 'OTVALOR20', dataIndex: 'OTVALOR20', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR21', itemId: 'OTVALOR21', dataIndex: 'OTVALOR21', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR22', itemId: 'OTVALOR22', dataIndex: 'OTVALOR22', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR23', itemId: 'OTVALOR23', dataIndex: 'OTVALOR23', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR24', itemId: 'OTVALOR24', dataIndex: 'OTVALOR24', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR25', itemId: 'OTVALOR25', dataIndex: 'OTVALOR25', hidden: true, menuDisabled: true, sortable: false},
            {text: 'OTVALOR26', itemId: 'OTVALOR26', dataIndex: 'OTVALOR26', hidden: true, menuDisabled: true, sortable: false}/*,
            {
			 	xtype: 'actioncolumn',
			 	width: 30,
			 	sortable: false,
			 	menuDisabled: true,
			 	items: [{
			 		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
			 		tooltip: 'Eliminar Clave',
			 		handler: function(grid, rowIndex){
			 					debug('eliminando',rowIndex);
						 		storeTablaCincoClaves.removeAt(rowIndex);
						 	}
		 		}]
			}*/
        ]
    });
    
    
    var panelValoresTablaApoyo = Ext.create('Ext.form.Panel', {
    	renderTo: 'dvCincoClaves',
		border: false,
		defaults: {
			style : 'margin:5px;'
		},
	    items    : [{
			            layout: 'column',
            			columns: 3,
            			border: false,
            			defaults: {
							style : 'margin:5px;'
						},
            			items: [{
									xtype      : 'textfield',
									name       : 'PV_NMTABLA_I',
									fieldLabel : 'N&uacute;mero de Tabla',
									value      : _NMTABLA,
									readOnly      : true
								},{
						        	xtype      : 'textfield',
						    		name       : 'pi_cdtabla',
						    		fieldLabel : 'C&oacute;digo de la Tabla',
									maxLength  : 30,
									maxLengthText: 'Longitud m&aacute;xima de 30 caracteres',
						    		value      : _CDTABLA,
						    		readOnly      : true,
						    		allowBlank    : false
						        },{
						        	xtype      : 'textfield',
						    		name       : 'pi_dstabla',
						    		allowBlank : false,
						    		fieldLabel : 'Descripci&oacute;n de la Tabla',
						    		width      : 545,
						    		labelWidth : 130,
						    		maxLength  : 60,
									maxLengthText: 'Longitud m&aacute;xima de 60 caracteres',
						    		value      : _DSTABLA
						        }]
					}, panelValoresTabCincoClaves],
					buttonAlign : 'center',
							    buttons:
							    	[{
						        	text: 'Guardar Valores',
						        	itemId: 'botonGuardarValoresId',
						        	icon    : _CONTEXT+'/resources/fam3icons/icons/disk.png',
						        	handler: function(btn, e) {
						        		
						        		if (panelValoresTablaApoyo.isValid()) {
						        			
						        			Ext.Msg.show({
						    		            title: 'Confirmar acci&oacute;n',
						    		            msg: '&iquest;Esta seguro que desea actualizar esta tabla?',
						    		            buttons: Ext.Msg.YESNO,
						    		            fn: function(buttonId, text, opt) {
						    		            	if(buttonId == 'yes') {
						    		            		
						    		            		// PARA AGREGAR NUEVAS FILAS A  GUARDAR
						    		            		var deleteList = [];
						    		            		var saveList = [];
						    		            		var updateList = [];
    													
						    		            		storeTablaCincoClaves.getRemovedRecords().forEach(function(record,index,arr){
												        	deleteList.push(record.data);
												    	});
						    		            		
						    		            		storeTablaCincoClaves.getNewRecords().forEach(function(record,index,arr){
												    		if(record.dirty){
												    			saveList.push(record.data);
												    		}
												    	});
												    	
												    	storeTablaCincoClaves.getUpdatedRecords().forEach(function(record,index,arr){
												    		updateList.push(record.data);
												    	});
												    	
													    debug('Claves Removed: ' , deleteList);
													    debug('Claves Added: '   , saveList);
													    debug('Claves Updated: ' , updateList);
												    	
												    	
						    		            		panelValoresTablaApoyo.setLoading(true);
						    		            		
						    		            		Ext.Ajax.request({
										    	            url: _URL_GuardaValoresTablaApoyo,
										    	            jsonData : {
										    	            	params: panelValoresTablaApoyo.getValues(),
										    	                'deleteList' : deleteList,
										    	                'saveList'   : saveList,
										    	                'updateList' : updateList
										    	            },
										    	            success  : function(response){
										    	                panelValoresTablaApoyo.setLoading(false);
										    	                var json = Ext.decode(response.responseText);
										    	                if(json.success){
										    	                	storeTablaCincoClaves.load({
																        params : {
																            'params.PV_NMTABLA_I' : _NMTABLA 
																        },
																        callback: function(records, operation, success) {
																        	//Agregamos 10 rows vacios por defecto:
																        	for(var count=0 ;count<10; count++){
																        		storeTablaCincoClaves.insert(storeTablaCincoClaves.getCount(),new CincoClavesModel());
																        	}
																        }
																    });
										    	                }else{
										    	                    mensajeError(json.msgRespuesta);
										    	                }
										    	            }
										    	            ,failure  : function()
										    	            {
										    	                panelValoresTablaApoyo.setLoading(false);
										    	                errorComunicacion();
										    	            }
												    	});
						    		            	}
						            			},
						    		            animateTarget: btn,
						    		            icon: Ext.Msg.QUESTION
						        			});
						    			} else {
						    				Ext.Msg.show({
						    					title: 'Aviso',
						    		            msg: 'Complete la informaci&oacute;n requerida',
						    		            buttons: Ext.Msg.OK,
						    		            animateTarget: btn,
						    		            icon: Ext.Msg.WARNING
						    				});
						    			}
						        	}
						        }
						      ]
		});
    
    /////////////////////////////////////// INIT //////////////////////
    
    // Cargamos los valores de la tabla:
    
    storeTablaCincoClaves.load({
        params : {
            'params.PV_NMTABLA_I' : _NMTABLA 
        },
        callback: function(records, operation, success) {
        	//Agregamos 10 rows vacios por defecto:
        	for(var count=0 ;count<10; count++){
        		storeTablaCincoClaves.insert(storeTablaCincoClaves.getCount(),new CincoClavesModel());
        	}
        }
    });
    
    /**
     * Se cambian los encabezados de las columnas de clave según el nmtabla
     * @param grid grid al que se le cambiaran los encabezados
     */
    function cambiarEncabezados(grid) {
    	
    	storeCabecerasClaves.load({
            params : {
                'params.pi_nmtabla' : _NMTABLA 
            },
            callback: function(records, operation, success) {
                Ext.each(records, function(record, index) {
                    // Asignamos la descripción de las columnas de forma dinamica:
                    grid.getView().headerCt.child("#OTCLAVE"+(index+1)).setText(record.get('DSCLAVE1'));
                    grid.getView().headerCt.child("#OTCLAVE"+(index+1)).setVisible(true);
                });
                
                storeCabecerasAtributos.load({
		            params : {
		                'params.pi_nmtabla' : _NMTABLA 
		            },
		            callback: function(records, operation, success) {
		                Ext.each(records, function(record, index) {
		                    // Asignamos la descripción de las columnas de forma dinamica:
		                    grid.getView().headerCt.child("#OTVALOR"+(index+1)).setText(record.get('DSATRIBU'));
		                    grid.getView().headerCt.child("#OTVALOR"+(index+1)).setVisible(true);
		                });
		                
		                loadMaskTabla.hide();
		            }
		        });
            }
        });
        
    }
    
});