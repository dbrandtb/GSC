
/* ********************************** JSONREADERS *************************** */
var elJson_GetNotifi= new Ext.data.JsonReader(
{
    root:'MTcataexList',
    totalProperty: 'totalCount',
    successProperty : '@success'
},
[
    {name: 'dsClave1',  mapping:'otClave',  type: 'string'},
    {name: 'dsClave2',  mapping:'otClave2',  type: 'string'},
    {name: 'dsClave3',  mapping:'otClave3',  type: 'string'},
    {name: 'dsClave4',  mapping:'otClave4',  type: 'string'},
    {name: 'dsClave5',  mapping:'otClave5',  type: 'string'},
    {name: 'dsOtValor',  mapping:'otValor',  type: 'string'}
]
);
/*
var elJson_CmbNomFormato = new Ext.data.JsonReader(
{
root: 'comboNotificacionFormato',
id: 'codigo'
},
[
{name: 'codigo', mapping:'cdFormato', type: 'string'},
{name: 'descripcion', mapping:'dsNomFormato', type: 'string'}
]
);

var elJson_CmbMetEnvFrm = new Ext.data.JsonReader(
{
    root: 'comboTipoMetodoEnvio',
    id: 'codigo'
},
[
    {name: 'codigo',mapping:'codigo', type: 'string'},
    {name: 'descripcion',mapping:'descripcion', type: 'string'}
]
);

/* ********************************** STORES *************************** */ 

//estore del get
var storeGetTcataex = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_TCATAEXT
        }),
reader:elJson_GetNotifi
});
/*
//store de la busqueda de la izquierda
var storeGetProceNotifi = new Ext.data.Store({
    url:_ACTION_BUSCAR_PROCESOS_NOTIFICACIONES,
    reader: new Ext.data.JsonReader({
    	id: 'cdProceso',
    	root: 'MEstructuraList',
    	totalProperty: 'totalCount',
    	successProperty: 'success'
    	},[
    	    {name: 'cdProceso',  type: 'string', mapping: 'cdProceso'},
    	    {name: 'dsProceso',  type: 'string', mapping: 'dsProceso'}
    	]
    )
});

//store que se llena junto con del get la derecha
var el_storeDer = new Ext.data.Store({
	url: _ACTION_BUSCAR_NOTIFICACIONES_PROCESOS,
	reader: new Ext.data.JsonReader({
		id: 'cdProceso',
		root: 'MEstructuraList',
    	totalProperty: 'totalCount',
    	successProperty: 'success'
    	},[
    	    {name: 'cdProceso',  type: 'string', mapping: 'cdProceso'},
    	    {name: 'dsProceso',  type: 'string', mapping: 'dsProceso'}
    	]
	)
});

//store del combo de formato de documentos
var dsNombreFormato = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_NOTIFICACIONES_FORMATO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
           }),
reader: elJson_CmbNomFormato
});

//store del combo metodo envio
var dsTipoMetodoEnvio = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_NOTIFICACIONES_TIPO_METODO_ENVIO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
           }),
reader: elJson_CmbMetEnvFrm 
});

*/


