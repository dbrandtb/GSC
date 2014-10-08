/**
 * Grid excel para la Tabla de Cinco Claves
 */
Ext.onReady(function() {
	
	//Models:
	
    Ext.define('CabeceraClaveModel', {
        extend: 'Ext.data.Model',
        fields: [
        	{name: 'DSCLAVE1'},
            {name: 'SWFORMA1'},
            {name: 'DSFORMA'},
            {name: 'NMLMIN1', type:'int'},
            {name: 'NMLMAX1', type:'int'},
            {name: 'NUMCLAVE', type:'int'}
        ]
    });
    
    Ext.define('CincoClavesModel', {
        extend: 'Ext.data.Model',
        fields: [
            //{name: 'id'},
            {name: 'NMTABLA'},
            {name: 'OTCLAVE1'},
            {name: 'OTCLAVE2'},
            {name: 'OTCLAVE3'},
            {name: 'OTCLAVE4'},
            {name: 'OTCLAVE5'},
            {name: 'FEDESDE'},
            {name: 'FEHASTA'},
            {name: 'OTVALOR01'},
            {name: 'OTVALOR02'},
            {name: 'OTVALOR03'},
            {name: 'OTVALOR04'},
            {name: 'OTVALOR05'},
            {name: 'OTVALOR06'},
            {name: 'OTVALOR07'},
            {name: 'OTVALOR08'},
            {name: 'OTVALOR09'},
            {name: 'OTVALOR10'},
            {name: 'OTVALOR11'},
            {name: 'OTVALOR12'},
            {name: 'OTVALOR13'},
            {name: 'OTVALOR14'},
            {name: 'OTVALOR15'},
            {name: 'OTVALOR16'},
            {name: 'OTVALOR17'},
            {name: 'OTVALOR18'},
            {name: 'OTVALOR19'},
            {name: 'OTVALOR20'},
            {name: 'OTVALOR21'},
            {name: 'OTVALOR22'},
            {name: 'OTVALOR23'},
            {name: 'OTVALOR24'},
            {name: 'OTVALOR25'},
            {name: 'OTVALOR26'}
        ]
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
    
    var storeTablaCincoClaves = new Ext.data.Store({
        model: 'CincoClavesModel',
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_VALORES_TABLA_CINCO_CLAVES,
            reader: {
                type: 'json',
                root: 'loadList'
            }
        }
    });
    
    
    // Create an instance of the Spread panel
    var spreadPanel = new Spread.grid.Panel({
        store: storeTablaCincoClaves,
        height: 600,
        tbar: [{
        	icon: _CONTEXT + '/resources/fam3icons/icons/table_edit.png',
            text: 'Habilitar edici&oacute;n',
            handler: function() {
            	//console.log('this', this);
                this.up('spread').setEditable(true);
            }
        }, {
        	icon: _CONTEXT + '/resources/fam3icons/icons/lock.png',
            text: 'Deshabilitar edici&oacute;n',
            handler: function() {
                this.up('spread').setEditable(false);
            }
        },'-',{
            xtype      : 'numberfield',
            name       : 'rowsToCreate',
//            fieldLabel : 'Agregar',
            minValue   : 0,
            maxValue   : 500,
            width      : 100,
            labelAlign : 'right'
        },{
        	icon: _CONTEXT + '/resources/fam3icons/icons/table_add.png',
            text: 'Agregar Filas',
            handler: function() {
            	if(!this.previousSibling().isValid()){
                    Ext.Msg.alert('Aviso', 'Ingrese un n&uacute;mero v&aacute;lido');
                    return;
                }
                var numFilas = this.previousSibling().getValue();
            	var storeSpread = this.up('spread').getStore(); 
                storeSpread.add( creaRows(numFilas, storeSpread.count()) );
                this.up('spread').setEditable(true);//Aplicar estilo
            }
        }],
        // You can supply your own viewConfig to change the config of Spread.grid.View!
        //viewConfig: {
            //stripeRows: true
        //},
        listeners: {
        	render: function(grid, eOpts) {
        		//console.log('Grid columns:', grid.columns);
        		// Cambiamos los textos de encabezados:
        		cambiarEncabezados(grid);
        	},
            covercell: function(view, position, coverEl, eOpts) {
                //console.log('External listener to covercell', arguments);
                //console.log(view, position, coverEl, eOpts);
            }
        },
        // Setting if editing is allowed initially
        editable: true,
        // Setting if edit mode styling shall be activated
        editModeStyling: true,
        // Configure visible grid columns
        columns: [
        	{text: 'ID',        /*dataIndex: 'id',*/  columnWidth: 40,     xtype: 'spreadheadercolumn', sortable: false},
            {text: 'NMTABLA',     dataIndex: 'NMTABLA', hidden: true, menuDisabled: true, sortable: false},
            {dataIndex: 'OTCLAVE1', itemId: 'OTCLAVE1', hidden: true, menuDisabled: true, sortable: false},
            {dataIndex: 'OTCLAVE2', itemId: 'OTCLAVE2', hidden: true, menuDisabled: true, sortable: false},
            {dataIndex: 'OTCLAVE3', itemId: 'OTCLAVE3', hidden: true, menuDisabled: true, sortable: false},
            {dataIndex: 'OTCLAVE4', itemId: 'OTCLAVE4', hidden: true, menuDisabled: true, sortable: false},
            {dataIndex: 'OTCLAVE5', itemId: 'OTCLAVE5', hidden: true, menuDisabled: true, sortable: false},
            //{text: 'Fecha inicio', dataIndex: 'FEDESDE'},
            {text: 'Fecha inicio', dataIndex: 'FEDESDE', xtype: 'datecolumn',   format:'d/m/Y', menuDisabled: true, sortable: false},
            {text: 'Fecha fin',    dataIndex: 'FEHASTA', renderer: Ext.util.Format.dateRenderer('d/m/Y'), menuDisabled: true, sortable: false},
            {text: 'OTVALOR1',  dataIndex: 'OTVALOR1', menuDisabled: true, sortable: false},
            {text: 'OTVALOR2',  dataIndex: 'OTVALOR2', menuDisabled: true, sortable: false},
            {text: 'OTVALOR3',  dataIndex: 'OTVALOR3', menuDisabled: true, sortable: false},
            {text: 'OTVALOR4',  dataIndex: 'OTVALOR4', menuDisabled: true, sortable: false},
            {text: 'OTVALOR5',  dataIndex: 'OTVALOR5', menuDisabled: true, sortable: false},
            {text: 'OTVALOR6',  dataIndex: 'OTVALOR6', menuDisabled: true, sortable: false},
            {text: 'OTVALOR7',  dataIndex: 'OTVALOR7', menuDisabled: true, sortable: false},
            {text: 'OTVALOR8',  dataIndex: 'OTVALOR8', menuDisabled: true, sortable: false},
            {text: 'OTVALOR9',  dataIndex: 'OTVALOR9', menuDisabled: true, sortable: false},
            {text: 'OTVALOR10', dataIndex: 'OTVALOR10', menuDisabled: true, sortable: false},
            {text: 'OTVALOR11', dataIndex: 'OTVALOR11', menuDisabled: true, sortable: false},
            {text: 'OTVALOR12', dataIndex: 'OTVALOR12', menuDisabled: true, sortable: false},
            {text: 'OTVALOR13', dataIndex: 'OTVALOR13', menuDisabled: true, sortable: false},
            {text: 'OTVALOR14', dataIndex: 'OTVALOR14', menuDisabled: true, sortable: false},
            {text: 'OTVALOR15', dataIndex: 'OTVALOR15', menuDisabled: true, sortable: false},
            {text: 'OTVALOR16', dataIndex: 'OTVALOR16', menuDisabled: true, sortable: false},
            {text: 'OTVALOR17', dataIndex: 'OTVALOR17', menuDisabled: true, sortable: false},
            {text: 'OTVALOR18', dataIndex: 'OTVALOR18', menuDisabled: true, sortable: false},
            {text: 'OTVALOR19', dataIndex: 'OTVALOR19', menuDisabled: true, sortable: false},
            {text: 'OTVALOR20', dataIndex: 'OTVALOR20', menuDisabled: true, sortable: false},
            {text: 'OTVALOR21', dataIndex: 'OTVALOR21', menuDisabled: true, sortable: false},
            {text: 'OTVALOR22', dataIndex: 'OTVALOR22', menuDisabled: true, sortable: false},
            {text: 'OTVALOR23', dataIndex: 'OTVALOR23', menuDisabled: true, sortable: false},
            {text: 'OTVALOR24', dataIndex: 'OTVALOR24', menuDisabled: true, sortable: false},
            {text: 'OTVALOR25', dataIndex: 'OTVALOR25', menuDisabled: true, sortable: false},
            {text: 'OTVALOR26', dataIndex: 'OTVALOR26', menuDisabled: true, sortable: false}
        ]
    });
    
    // Show spread inside a window
//    var spreadWnd = new Ext.window.Window({
//        title: 'Tabla de Cinco Claves',
//        layout: 'fit',
//        //closable: false,
//        //maximized: true,
//        //resizable: true,
//        //maximizable: true,
//        //width: 1000,
//        //height: 600,
//        items: [spreadPanel]
//    });
    
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
									name       : 'pi_nmtabla',
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
					}, spreadPanel],
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
						    		            		var saveList = [];
						    		            		
						    		            		storeTablaCincoClaves.getNewRecords().forEach(function(record,index,arr){
												    		if(record.dirty){
												    			saveList.push(record.data);
//												    			if(record.get("NMLMIN") > record.get("NMLMAX")){
//												    				validacionMinMax = false;
//												    				mensajeWarning("El valor m&iacute;nimo debe ser menor al valor m&aacute;ximo del atributo: " + record.get("DSATRIBU"));
//												    				return false; //break here
//												    			}
												    		}
//												    		else if(!Ext.isEmpty(record.get('DSATRIBU'))){
//												    			contadorAtributos++;
//												    		}
												    	});
												    	debug('Filas Added: ', saveList);
												    	
												    	
						    		            		panelValoresTablaApoyo.setLoading(true);
						    		            		
//						    		            		Ext.Ajax.request({
//										    	            url: _URL_GuardaTablaApoyo,
//										    	            jsonData : {
//										    	            	params: panelValoresTablaApoyo.getValues(),
//										    	                'saveList'   : saveList,
//										    	                'saveList2'   : saveList2
//										    	            },
//										    	            success  : function(response){
//										    	                panelValoresTablaApoyo.setLoading(false);
//										    	                var json = Ext.decode(response.responseText);
//										    	                if(json.success){
//										    	                	recargagridTablas();
//										    						windowLoader.close();
//										    						mensajeCorrecto('\u00C9xito', 'La tabla se guard\u00F3 correctamente', Ext.Msg.OK, Ext.Msg.INFO);
//										    						panelValoresTablaApoyo.getForm().reset();
//										    	                }else{
//										    	                    mensajeError(json.msgRespuesta);
//										    	                }
//										    	            }
//										    	            ,failure  : function()
//										    	            {
//										    	                panelValoresTablaApoyo.setLoading(false);
//										    	                errorComunicacion();
//										    	            }
//												    	});
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
    
//    // Show the spread window
//    spreadWnd.show();
//
//    // And center it
//    spreadWnd.center();
//    
    // Cargamos los valores de la tabla:
    storeTablaCincoClaves.load({
        params : {
            'params.PV_NMTABLA_I' : _NMTABLA 
        },
        callback: function(records, operation, success) {
        	//Agregamos los IDs a los records obtenidos:
        	Ext.each(records, function(record, index) {
        		//console.log('record:', record);
        		record.set('id', index+1 );
            });
        	//Agregamos rows vacios por defecto:
        	spreadPanel.getStore().add( creaRows(20, spreadPanel.getStore().count()) );
        }
    });
    
    
    
    //Functions:
    
    /**
     * Crea rows para el grid excel
     * @param count RFC del asegurado
     * @param initialRowNumber (Optional) Numero de Renglón inicial para los nuevos datos
     * @return datos para el grid
     */
    function creaRows(count, initialRowNumber) {

    	initialRowNumber = initialRowNumber || 0;
    	
    	//console.log('count', count);
    	//console.log('initialRowNumber', initialRowNumber);
    	
        var data = [], curDate = new Date();
    
        // Clear milliseconds because toString() will loose them
        // and data change will be detected false-positively...
        curDate.setMilliseconds(0);
    
        // Generate data rows
        for (var i = 0; i < count; i++) {
        
            data.push({
                id       : initialRowNumber+1,
                NMTABLA  : '',
                OTCLAVE1 : '', OTCLAVE2 : '', OTCLAVE3 : '', OTCLAVE4 : '', OTCLAVE5 : '',
                FEDESDE  : Ext.Date.format(new Date(), 'd/m/Y'),
                FEHASTA  : curDate,
                OTVALOR1 : '', OTVALOR2 : '', OTVALOR3 : '', OTVALOR4 : '', OTVALOR5 : '',
                OTVALOR6 : '', OTVALOR7 : '', OTVALOR8 : '', OTVALOR9 : '', OTVALOR10: '',
                OTVALOR11: '', OTVALOR12: '', OTVALOR13: '', OTVALOR14: '', OTVALOR15: '',
                OTVALOR16: '', OTVALOR17: '', OTVALOR18: '', OTVALOR19: '', OTVALOR20: '', 
                OTVALOR21: '', OTVALOR22: '', OTVALOR23: '', OTVALOR24: '', OTVALOR25: '',
                OTVALOR26: ''
            });
            initialRowNumber++;
        }
        //console.log('data', data);
        return data;
    };

    
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
            }
        });
    }
    
});