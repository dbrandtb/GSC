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
    	renderTo: 'dvCincoClaves',
        //store: localDataStore,
        store: storeTablaCincoClaves,
        height: 600,
        tbar: [{
            text: 'Habilitar edici&oacute;n',
            handler: function() {
            	//console.log('this', this);
                this.up('spread').setEditable(true);
            }
        }, {
            text: 'Deshabilitar edici&oacute;n',
            handler: function() {
                this.up('spread').setEditable(false);
            }
        }, {
            xtype      : 'numberfield',
            name       : 'rowsToCreate',
            fieldLabel : 'Agregar',
            minValue   : 0,
            maxValue   : 500,
            labelWidth : 50,
            width      : 165,
            labelAlign : 'right'
        }, {
            text: 'Filas',
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
        	{text: 'ID',        /*dataIndex: 'id',*/  columnWidth: 40,     xtype: 'spreadheadercolumn'},
            {text: 'NMTABLA',     dataIndex: 'NMTABLA', hidden: true},
            {dataIndex: 'OTCLAVE1', itemId: 'OTCLAVE1', hidden: true},
            {dataIndex: 'OTCLAVE2', itemId: 'OTCLAVE2', hidden: true},
            {dataIndex: 'OTCLAVE3', itemId: 'OTCLAVE3', hidden: true},
            {dataIndex: 'OTCLAVE4', itemId: 'OTCLAVE4', hidden: true},
            {dataIndex: 'OTCLAVE5', itemId: 'OTCLAVE5', hidden: true},
            //{text: 'Fecha inicio', dataIndex: 'FEDESDE'},
            {text: 'Fecha inicio', dataIndex: 'FEDESDE', xtype: 'datecolumn',   format:'d/m/Y'},
            {text: 'Fecha fin',    dataIndex: 'FEHASTA', renderer: Ext.util.Format.dateRenderer('d/m/Y')},
            {text: 'OTVALOR1',  dataIndex: 'OTVALOR1'},
            {text: 'OTVALOR2',  dataIndex: 'OTVALOR2'},
            {text: 'OTVALOR3',  dataIndex: 'OTVALOR3'},
            {text: 'OTVALOR4',  dataIndex: 'OTVALOR4'},
            {text: 'OTVALOR5',  dataIndex: 'OTVALOR5'},
            {text: 'OTVALOR6',  dataIndex: 'OTVALOR6'},
            {text: 'OTVALOR7',  dataIndex: 'OTVALOR7'},
            {text: 'OTVALOR8',  dataIndex: 'OTVALOR8'},
            {text: 'OTVALOR9',  dataIndex: 'OTVALOR9'},
            {text: 'OTVALOR10', dataIndex: 'OTVALOR10'},
            {text: 'OTVALOR11', dataIndex: 'OTVALOR11'},
            {text: 'OTVALOR12', dataIndex: 'OTVALOR12'},
            {text: 'OTVALOR13', dataIndex: 'OTVALOR13'},
            {text: 'OTVALOR14', dataIndex: 'OTVALOR14'},
            {text: 'OTVALOR15', dataIndex: 'OTVALOR15'},
            {text: 'OTVALOR16', dataIndex: 'OTVALOR16'},
            {text: 'OTVALOR17', dataIndex: 'OTVALOR17'},
            {text: 'OTVALOR18', dataIndex: 'OTVALOR18'},
            {text: 'OTVALOR19', dataIndex: 'OTVALOR19'},
            {text: 'OTVALOR20', dataIndex: 'OTVALOR20'},
            {text: 'OTVALOR21', dataIndex: 'OTVALOR21'},
            {text: 'OTVALOR22', dataIndex: 'OTVALOR22'},
            {text: 'OTVALOR23', dataIndex: 'OTVALOR23'},
            {text: 'OTVALOR24', dataIndex: 'OTVALOR24'},
            {text: 'OTVALOR25', dataIndex: 'OTVALOR25'},
            {text: 'OTVALOR26', dataIndex: 'OTVALOR26'}
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