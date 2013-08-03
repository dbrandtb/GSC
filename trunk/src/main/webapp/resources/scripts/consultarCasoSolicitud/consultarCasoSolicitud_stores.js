/* ********************************** JSONREADERS *************************** */

//COMBO PRIORIDAD
var storeComboPrioridad = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_PRIORIDAD}),
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
storeComboPrioridad.load({
		params:{cdTabla:'CATBOPRIOR'}
});

var jsonGrilla= new Ext.data.JsonReader(
{
root:'MEstructuraCasosList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'nmCaso',  mapping:'nmCaso',  type: 'string'},
{name: 'cdMatriz',  mapping:'cdMatriz',  type: 'string'},
{name: 'cdFormatoOrden',  mapping:'cdFormatoOrden',  type: 'string'},
{name: 'cdOrdenTrabajo',  mapping:'cdOrdenTrabajo',  type: 'string'},
{name: 'cdProceso',  mapping:'cdProceso',  type: 'string'},
{name: 'desProceso',  mapping:'desProceso',  type: 'string'},
{name: 'cdUsuario',  mapping:'cdUsuario',  type: 'string'},
{name: 'desUsuario',  mapping:'desUsuario',  type: 'string'},
{name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},
{name: 'desUnieco',  mapping:'desUnieco',  type: 'string'},
{name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
{name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
{name: 'cdPrioridad',  mapping:'cdPrioridad',  type: 'string'},
{name: 'desPrioridad',  mapping:'desPrioridad',  type: 'string'},
{name: 'cdStatus',  mapping:'cdStatus',  type: 'string'},
{name: 'desStatus',  mapping:'desStatus',  type: 'string'},
{name: 'cdPerson',  mapping:'cdPerson',  type: 'string'},
{name: 'desNombre',  mapping:'desNombre',  type: 'string'},
{name: 'indSemafColor',  mapping:'indSemafColor',  type: 'string'},
{name: 'idMetContact',  mapping:'idMetContact',  type: 'string'},
{name: 'feRegistro',  mapping:'feRegistro',  type: 'string'},
{name: 'feResolucion',  mapping:'feResolucion',  type: 'string'},
{name: 'treSolucion',  mapping:'treSolucion',  type: 'string'},
{name: 'feEscalamiento',  mapping:'feEscalamiento',  type: 'string'},
{name: 'nmVecesCompra',  mapping:'nmVecesCompra',  type: 'string'},
{name: 'tesCalamiento',  mapping:'tesCalamiento',  type: 'string'},
{name: 'cdnumerordencia',  mapping:'cdnumerordencia',  type: 'string'},
{name: 'indPoliza',  mapping:'indPoliza',  type: 'string'}

]
);



/* ********************************** STORES *************************** */ 


var storeGrilla = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_CASOS_SOLICITUD,
		waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonGrilla
});
