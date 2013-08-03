   
var storeGridFax = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_BUSCAR_USUARIOS}),
	reader: new Ext.data.JsonReader({
					root:'MLisAdministrarUsuariosModulo',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
				{name: 'dsUsuario', type: 'string'},
				{name: 'cdUsuario', type: 'string'},
				{name: 'cdModulo', type: 'string'},
				{name: 'dsModulo', type: 'string'}
				
				]
			)
}); 