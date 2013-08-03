 var storeTareas = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COMBO_ASEGURADORAS_PRODCORP}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboTareas'},
            		[
            			{name: 'descripcion',  mapping:'descripcion', type: 'string'},            			
            			{name: 'cdmatriz',  mapping:'cdmatriz', type: 'string'},
            			{name: 'cdformatoorden',  mapping:'cdformatoorden', type: 'string'},
            			{name: 'cdunieco',  mapping:'cdunieco', type: 'string'},
            			{name: 'cdramo',  mapping:'cdramo', type: 'string'},
            			{name: 'cdproceso',  mapping:'cdproceso', type: 'string'},
            			{name: 'dsunieco',  mapping:'dsunieco', type: 'string'},
            			{name: 'dsramo',  mapping:'dsramo', type: 'string'}
            		]
            )
        });
        
/* var storePrioridades = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COMBO_PRIORIDADES}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboPrioridades'},
            		[{name: 'codigo', type: 'string'},{name: 'descripcion', type: 'string'}])
        }); */
        
 
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
			{name: 'cdMatriz',type: 'string'}
		]
		)
});              