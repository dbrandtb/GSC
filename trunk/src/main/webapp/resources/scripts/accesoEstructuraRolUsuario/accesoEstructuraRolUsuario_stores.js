//STORE DE COMBOS
var storeComboNivel = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_NIVEL}),
	reader: new Ext.data.JsonReader(
		{
			root:'comboClientesCorpBO',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'cdElemento', type: 'string'},
			{name: 'dsElemen', type: 'string'},
			{name: 'cdPerson', type: 'string'}
		]
		)
});

var storeComboRol = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ROL}),
	reader: new Ext.data.JsonReader(
		{
			root:'comboRolesUsuarios',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'codigo', type: 'string'},
			{name: 'descripcion', type: 'string'},
			{name: 'codigo2', type: 'string'},
		]
		)
});

var storeComboUsuario = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_USUARIO}),
	reader: new Ext.data.JsonReader(
		{
			root:'comboUsuarios',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'codigo', type: 'string'},
			{name: 'descripcion', type: 'string'}
		]
		)
});

var storeComboEstado = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ESTADO}),
	reader: new Ext.data.JsonReader(
		{
			root:'comboDatosCatalogo',
			totalProperty: 'totalCount',
			successProperty : '@success'
		},
		[
			{name: 'codigo',mapping:'id', type: 'string'},
			{name: 'descripcion',mapping:'texto', type: 'string'}
		]
		)
});

//STORE Y READER DE LA GRILLA
var jsonGrilla= new Ext.data.JsonReader({
	root:'listaDatos',
	totalProperty: 'totalCount',
	successProperty : '@success'
	},
	[
	{name: 'cdElemento', type: 'string'},
	{name: 'cdEstado', type: 'string'},
	{name: 'cdRol', type: 'string'},
	{name: 'cdUsuario', type: 'string'},
	{name: 'dsElemen', type: 'string'},
	{name: 'dsRol', type: 'string'},
	{name: 'dsEstado', type: 'string'},
	{name: 'dsUsuario', type: 'string'}
	]
);

var storeGrilla = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: _ACTION_BUSCAR_ACCESO_ESTRUCTURA_ROL_USUARIO}),
		reader:jsonGrilla
	});