 var storeTareas = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COMBO_TAREAS}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboTareas'},
            		[
            			{name: 'descripcion', type: 'string'},            			
            			{name: 'cdmatriz', type: 'string'},
            			{name: 'cdformatoorden', type: 'string'},
            			{name: 'cdunieco', type: 'string'},
            			{name: 'cdramo', type: 'string'},
            			{name: 'cdproceso', type: 'string'},
            			{name: 'dsunieco', type: 'string'},
            			{name: 'dsramo', type: 'string'},
            			{name: 'cdformatoMatriz', type: 'string'}
            			
            		]
            )
        });
        
 var storePrioridades = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COMBO_PRIORIDADES}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboPrioridades'},
            		[{name: 'codigo', type: 'string'},{name: 'descripcion', type: 'string'}])
        }); 
        
 
var storeUResponsables = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_USUARIOS_RESPONSABLES}),
	reader: new Ext.data.JsonReader(
		{
			root:'MUsuariosResponsablesList',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'cdRolmat', type: 'string'},
			{name: 'desRolmat',type: 'string'},
			{name: 'cdUsuario', type: 'string'},
			{name: 'desUsuario',type: 'string'},
			{name: 'cdMatriz',type: 'string'},
			{name: 'email',type: 'string'},
		]
		)
}); 

var storePersonaCaso = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_PERSONA_CASO}),
	reader: new Ext.data.JsonReader(
		{
			root:'MPersonaList',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'dsNombre', mapping: 'dsNombre', type: 'string'},
			{name: 'cdIdeper', mapping: 'cdIdeper', type: 'string'},
			{name: 'cdElemento', type: 'string'},
			{name: 'corpo', type: 'string'}
		]
		)
}); 