/* ********************************** JSONREADERS *************************** */

var jsonGrillaCotizacionesMasivas = new Ext.data.JsonReader(
{
    root:'detalleCotizacionMasiva',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
[
	{name: 'seleccionar',	mapping: 'seleccionar',	type: 'string'},
	{name: 'cdAsegura',		mapping:'cdAsegura',	type: 'string'},
	{name: 'dsAsegura',		mapping:'dsAsegura',	type: 'string'},
	{name: 'cdRamo',		mapping:'cdRamo',		type: 'string'},     
	{name: 'dsRamo',		mapping:'dsRamo',		type: 'string'},
	{name: 'cdPerson',		mapping:'cdPerson',		type: 'string'},
	{name: 'dsNombre',		mapping:'dsNombre',		type: 'string'},
	{name: 'carga',			mapping:'carga',		type: 'string'},
	{name: 'nmPoliza',		mapping:'nmPoliza',		type: 'int'},
	{name: 'feInivig',		mapping:'feInivig',		type: 'string'},
	{name: 'feFinvig',		mapping:'feFinvig',		type: 'string'},
	{name: 'prima',			mapping:'prima',		type: 'string'},
	{name: 'cdplan',		mapping:'cdplan',		type: 'string'},
	{name: 'nmsituac',		mapping:'nmsituac',		type: 'string'},
	{name: 'estado',		mapping:'estado',		type: 'string'},
	{name: 'cdtipsit',		mapping:'cdtipsit',		type: 'string'},
	{name: 'cdcia',			mapping:'cdcia',		type: 'string'},
	{name: 'cdunieco',		mapping:'cdunieco',		type: 'string'}
] 
);


/* ********************************** STORES *************************** */ 

var storeGrillaCotizacionesMasivas = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_COTIZACIONES,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:jsonGrillaCotizacionesMasivas
});

var storeComboCotizacionesMasivasTipoRamo = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({
    		url: _ACTION_BUSCAR_TIPO_RAMO
	}),
    reader: new Ext.data.JsonReader({
    	root: 'comboTipoRamoList',
    	id: 'codigo'
    },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
    ])
});