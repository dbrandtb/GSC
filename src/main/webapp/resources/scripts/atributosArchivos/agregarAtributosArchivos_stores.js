 var storeAtributos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_FORMATO}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboFormatos'},
            		[
            			{name: 'cdAtribu', type: 'string'},            			
            			{name: 'cdTipoar', type: 'string'},
            			{name: 'swFormat', type: 'string'},
            			{name: 'codigo', type: 'string'},
            			{name: 'nmLmin', type: 'string'},
            			{name: 'nmLmax', type: 'string'},
            			{name: 'swObliga', type: 'string'},
            			{name: 'descripcion', type: 'string'},
            		]
            )
        });
        
        
        
 var storeStatus = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_STATUS}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboTareaEstatus'},
            		[{name: 'codigo', type: 'string'},    //status
            		 {name: 'descripcion', type: 'string'}
            		])
        }); 
        
