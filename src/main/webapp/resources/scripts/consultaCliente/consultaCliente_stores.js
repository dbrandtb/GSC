/*var storeComboEmpresas = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_EMPRESAS}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboClientesCorpBO'},
            		[
            			{name: 'cdElemento', type: 'string'},            			
            			{name: 'dsElemen', type: 'string'},
            			{name: 'cdPerson', type: 'string'}
            		]
            )
        });
        

var storeComboGrupos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_GRUPOS}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboGrupos'},
            		[
            			{name: 'descripcion', type: 'string'},            			
            			{name: 'cdmatriz', type: 'string'},
            			{name: 'cdformatoorden', type: 'string'},
            			{name: 'cdunieco', type: 'string'},
            			{name: 'cdramo', type: 'string'},
            			{name: 'cdproceso', type: 'string'}
            		]
            )
        });  */      


var storeGridClientes = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_BUSCAR_CLIENTES}),
	reader: new Ext.data.JsonReader({
					root:'MListConsultaClientes',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
				{name: 'dselemen', type: 'string'},
				{name: 'cdelemen', type: 'string'},
				{name: 'cdperson', type: 'string'},
				{name: 'cdtipide', type: 'string'},
				{name: 'cdideper', type: 'string'},
				{name: 'dsnombre', type: 'string'},
				{name: 'cdtipper', type: 'string'},
				{name: 'otfisjur', type: 'string'},
				{name: 'otsexo', type: 'string'},
				{name: 'fenacimi', type: 'string'},
				{name: 'cdrfc', type: 'string'},
				{name: 'telefonocasa', type: 'string'},
				{name: 'telefonoofic', type: 'string'},
				{name: 'mailpersonal', type: 'string'},
				{name: 'mailoficina', type: 'string'}
				]
			)
});