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
            {name: 'nmtabla'},
            {name: 'otclave1'},
            {name: 'otclave2'},
            {name: 'otclave3'},
            {name: 'otclave4'},
            {name: 'otclave5'},
            {name: 'fedesde'},
            {name: 'fehasta'},
            {name: 'otvalor01'},
            {name: 'otvalor02'},
            {name: 'otvalor03'},
            {name: 'otvalor04'},
            {name: 'otvalor05'},
            {name: 'otvalor06'},
            {name: 'otvalor07'},
            {name: 'otvalor08'},
            {name: 'otvalor09'},
            {name: 'otvalor10'},
            {name: 'otvalor11'},
            {name: 'otvalor12'},
            {name: 'otvalor13'},
            {name: 'otvalor14'},
            {name: 'otvalor15'},
            {name: 'otvalor16'},
            {name: 'otvalor17'},
            {name: 'otvalor18'},
            {name: 'otvalor19'},
            {name: 'otvalor20'},
            {name: 'otvalor21'},
            {name: 'otvalor22'},
            {name: 'otvalor23'},
            {name: 'otvalor24'},
            {name: 'otvalor25'},
            {name: 'otvalor26'}
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
    
    
    //fields:
    var claves = [];
    
    var ed = Ext.create('CabeceraClaveModel', {name: 'Ed Spencer'});
	
    var fields = [
        {name: 'id', type: 'int'}, 
        'nmtabla', 
        'otclave1', 'otclave2', 'otclave3', 'otclave4', 'otclave5',
        {name: 'fedesde', type: 'date'},
        {name: 'fehasta', type: 'date', format: 'd/m/Y'},
        //{name: 'fehasta', type: 'date', format: 'd/m/Y'},
        'otvalor1',  'otvalor2',  'otvalor3',  'otvalor4',  'otvalor5',
        'otvalor6',  'otvalor7',  'otvalor8',  'otvalor9',  'otvalor10',
        'otvalor11', 'otvalor12', 'otvalor13', 'otvalor14', 'otvalor15',
        'otvalor16', 'otvalor17', 'otvalor18', 'otvalor19', 'otvalor20',
        'otvalor21', 'otvalor22', 'otvalor23', 'otvalor24', 'otvalor25',
        'otvalor26'
    ];

    var localDataStore = new Ext.data.Store({
        //storeId: 'storeTabla5Claves',
        data: creaRows(100),
        proxy: {
            type: 'memory',
            reader: {
                type: 'json'
            }
        },
        fields: fields
    });
    
    
    // Create an instance of the Spread panel
    var spreadPanel = new Spread.grid.Panel({

    	//renderTo: Ext.getBody(),
    	
        store: localDataStore,
        
        tbar: [{
            text: 'Habilitar edici&oacute;n',
            handler: function() {
            	console.log('this', this);
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
        
        // You can supply your own viewConfig to change
        // the config of Spread.grid.View!
        /*
        viewConfig: {
            stripeRows: true
        },
        */

        listeners: {
        	render: function(grid, eOpts) {
        		console.log('Grid columns:', grid.columns);
        		// Cambiamos los textos de encabezados:
        		//cambiarEncabezados(grid);
        	},
            covercell: function(view, position, coverEl, eOpts) {
                console.log('External listener to covercell', arguments);
                console.log(view, position, coverEl, eOpts);
            }
        },

        // Setting if editing is allowed initially
        editable: true,

        // Setting if edit mode styling shall be activated
        editModeStyling: true,

        // Configure visible grid columns
        columns: [
        	{xtype: 'spreadheadercolumn', text: 'ID'},
            {text: 'NMTABLA',   dataIndex: 'nmtabla'},
            {text: 'OTCLAVE1',  dataIndex: 'otclave1'},
            {text: 'OTCLAVE2',  dataIndex: 'otclave2'},
            {text: 'OTCLAVE3',  dataIndex: 'otclave3'},
            {text: 'OTCLAVE4',  dataIndex: 'otclave4'},
            {text: 'OTCLAVE5',  dataIndex: 'otclave5'},
            //{text: 'Fecha inicio', dataIndex: 'fedesde'},
            {text: 'Fecha inicio', dataIndex: 'fedesde', xtype: 'datecolumn',   format:'d/m/Y'},
            {text: 'Fecha fin',    dataIndex: 'fehasta', renderer: Ext.util.Format.dateRenderer('d/m/Y')},
            {text: 'OTVALOR1',  dataIndex: 'otvalor1'},
            {text: 'OTVALOR2',  dataIndex: 'otvalor2'},
            {text: 'OTVALOR3',  dataIndex: 'otvalor3'},
            {text: 'OTVALOR4',  dataIndex: 'otvalor4'},
            {text: 'OTVALOR5',  dataIndex: 'otvalor5'},
            {text: 'OTVALOR6',  dataIndex: 'otvalor6'},
            {text: 'OTVALOR7',  dataIndex: 'otvalor7'},
            {text: 'OTVALOR8',  dataIndex: 'otvalor8'},
            {text: 'OTVALOR9',  dataIndex: 'otvalor9'},
            {text: 'OTVALOR10', dataIndex: 'otvalor10'},
            {text: 'OTVALOR11', dataIndex: 'otvalor11'},
            {text: 'OTVALOR12', dataIndex: 'otvalor12'},
            {text: 'OTVALOR13', dataIndex: 'otvalor13'},
            {text: 'OTVALOR14', dataIndex: 'otvalor14'},
            {text: 'OTVALOR15', dataIndex: 'otvalor15'},
            {text: 'OTVALOR16', dataIndex: 'otvalor16'},
            {text: 'OTVALOR17', dataIndex: 'otvalor17'},
            {text: 'OTVALOR18', dataIndex: 'otvalor18'},
            {text: 'OTVALOR19', dataIndex: 'otvalor19'},
            {text: 'OTVALOR20', dataIndex: 'otvalor20'},
            {text: 'OTVALOR21', dataIndex: 'otvalor21'},
            {text: 'OTVALOR22', dataIndex: 'otvalor22'},
            {text: 'OTVALOR23', dataIndex: 'otvalor23'},
            {text: 'OTVALOR24', dataIndex: 'otvalor24'},
            {text: 'OTVALOR25', dataIndex: 'otvalor25'},
            {text: 'OTVALOR26', dataIndex: 'otvalor26'}
        ]
    });

    // Evil global reference for fast & easy console access
    //window.spreadPanel = spreadPanel;

    
    // Show spread inside a window
    var spreadWnd = new Ext.window.Window({
        title: 'Spread Example',
        layout: 'fit',
        maximizable: true,
        resizable: true,
        width: 1000,
        height: 300,
        items: [spreadPanel]
    });
    

    // Show the spread window
    spreadWnd.show();

    // And center it
    spreadWnd.center();
    
    
    /**
     * Crea rows para el grid excel
     * @param count RFC del asegurado
     * @param initialRowNumber (Optional) Numero de Renglón inicial para los nuevos datos
     * @return datos para el grid
     */
    function creaRows(count, initialRowNumber) {

    	initialRowNumber = initialRowNumber || 0;
    	
    	console.log('count', count);
    	console.log('initialRowNumber', initialRowNumber);
    	
        var data = [], curDate = new Date();
    
        // Clear milliseconds because toString() will loose them
        // and data change will be detected false-positively...
        curDate.setMilliseconds(0);
    
        // Generate data rows
        for (var i = 0; i < count; i++) {
        
            data.push({
                id       : initialRowNumber+1,
                nmtabla  : '',
                otclave1 : '', otclave2 : '', otclave3 : '', otclave4 : '', otclave5 : '',
                fedesde  : Ext.Date.format(new Date(), 'd/m/Y'),
                fehasta  : curDate,
                otvalor1 : '', otvalor2 : '', otvalor3 : '', otvalor4 : '', otvalor5 : '',
                otvalor6 : '', otvalor7 : '', otvalor8 : '', otvalor9 : '', otvalor10: '',
                otvalor11: '', otvalor12: '', otvalor13: '', otvalor14: '', otvalor15: '',
                otvalor16: '', otvalor17: '', otvalor18: '', otvalor19: '', otvalor20: '', 
                otvalor21: '', otvalor22: '', otvalor23: '', otvalor24: '', otvalor25: '',
                otvalor26: ''
            });
            initialRowNumber++;
        }
        console.log('data', data);
        return data;
    };
    
    
    // Cargamos los valores 
    storeTablaCincoClaves.load({
        params : {
            'params.nmtabla' : _NMTABLA 
        }
    });
    
    
    /*
    function cambiarEncabezados(grid) {
    	
    	storeCabecerasClaves.load({
            params : {
                'params.pi_nmtabla' : _NMTABLA 
            },
            callback: function(records, operation, success) {
                
                Ext.each(records, function(record, index) {
                    console.log('record:', record);
                    console.log('index:', index);
                    claves.push(record.get('DSCLAVE1'));
                });
                console.log('claves:', claves);
                console.log('dataindex:', grid.getView().getHeaderAtIndex(colIdx).dataIndex);
                //spreadPanel.columns
            }
        });
    }
    */
});