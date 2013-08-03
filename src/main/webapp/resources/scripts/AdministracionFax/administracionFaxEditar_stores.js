
 var storeTipoFax = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_TIPO_FAX}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboTipoFax'},
            		[
            			{name: 'dsarchivo', type: 'string',mapping:'dsarchivo'},            			
            			{name: 'cdtipoar', type: 'string',mapping:'cdtipoar'},
            			{name: 'indarchivo', type: 'string',mapping:'indarchivo'}
            			
            			
            		]
            )
        });
   //store que carga valores en el formulario estatico     
/*var jsonAdministracionFax = new Ext.data.JsonReader(
            		{root: 'mListAdministracionFaxes'},
            		[
            			{name: 'cdtipoar', type: 'string',mapping:'cdtipoar'},            			
            			{name: 'dsarchivo', type: 'string',mapping:'dsarchivo'},
            			{name: 'nmfax', type: 'string',mapping:'nmfax'},
            			{name: 'nmpoliex', type: 'string',mapping:'nmpoliex'},
            			{name: 'nmcaso', type: 'string',mapping:'nmcaso'},
            			{name: 'cdusuario', type: 'string',mapping:'cdusuario'},
            			{name: 'dsusuari', type: 'string',mapping:'dsusuari'},
            			{name: 'ferecepcion', type: 'string',mapping:'ferecepcion'}
            		]
            )


var storeAdministracionFax = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_ADMNISTRACION_FAX}),
            reader: jsonAdministracionFax
        });
        
 var storeAdministracionValorFax = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_ADMNISTRACION_VALOR_FAX}),
            reader: new Ext.data.JsonReader(
            		{root: 'mListAdministracionValorFaxes'},
            		[
            			{name: 'cdatribu', type: 'string',mapping:'cdatribu'},            			
            			{name: 'cdtipoar', type: 'string',mapping:'cdtipoar'},
            			{name: 'nmcaso', type: 'string',mapping:'nmcaso'},
            			{name: 'nmfax', type: 'string',mapping:'nmfax'},
            			{name: 'otvalor', type: 'string',mapping:'otvalor'}
            			
            		]
            )
        });*/
 


