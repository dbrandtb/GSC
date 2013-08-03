/* ********************************** JSONREADERS *************************** */

var elJsonStatusProcesos = new Ext.data.JsonReader(
{
    id:'cdProceso',
    root : 'MEstructuraListTrsStsCs',
    totalProperty: 'totalCount',
    successProperty : 'success'
},
[
    {name: 'cdProcesos',  mapping:'cdProceso',  type: 'string'},
    {name: 'dsProcesos',  mapping:'dsProceso',  type: 'string'}        
] 
);

var elJsonStatusPorProcesos = new Ext.data.JsonReader(
{
    id:'cdProceso',
    root : 'MEstructuraListTrsStsCs',
    totalProperty: 'totalCount',
    successProperty : 'success'
},
[
	{name: 'cdStatus',  type: 'string', mapping: 'cdStatus'},
    {name: 'cdProcesos',  mapping:'cdProceso',  type: 'string'},
    {name: 'dsProcesos',  mapping:'dsProceso',  type: 'string'}        
] 
);
/* ********************************** STORES *************************** */ 

//store de la busqueda de la izquierda
var storeStatusProcesos = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_STATUS_CASOS_TAREAS,
        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:elJsonStatusProcesos
});

var el_storeDerecho = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_STATUS_CASOS_TAREAS_PROCESOS,
        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:elJsonStatusPorProcesos
});

