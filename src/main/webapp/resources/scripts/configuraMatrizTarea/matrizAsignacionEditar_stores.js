/* ********************************** JSONREADERS *************************** */
var elJson_GetNotifi= new Ext.data.JsonReader(
{
    root:'MEstructuraList',
    totalProperty: 'totalCount',
    successProperty : '@success'
},
[
    {name: 'cdNotificacion',  mapping:'cdNotificacion',  type: 'string'},
    {name: 'dsNotificacion',  mapping:'dsNotificacion',  type: 'string'},
    {name: 'dsFormatoOrden',  mapping:'dsFormatoOrden',  type: 'string'},
    {name: 'cdFormatoOrden',  mapping:'cdFormatoOrden',  type: 'string'},
    {name: 'cdMetEnv',  mapping:'cdMetEnv',  type: 'string'},
    {name: 'dsMetEnv',  mapping:'dsMetEnv',  type: 'string'}
]
);

var elJson_GetProceNotifi= new Ext.data.JsonReader(
{
    root:'MEstructuraList',
    totalProperty: 'totalCount',
    successProperty : '@success'
},
[
    {name: 'cdProceso',  mapping:'cdProceso',  type: 'string'},
    {name: 'dsProceso',  mapping:'dsProceso',  type: 'string'}
]
);

var elJson_GetNotifiProce= new Ext.data.JsonReader(
{
	id: 'cdProceso',
    root: 'MEstructuraList',
    totalProperty: 'totalCount'/*,
    successProperty : '@success'*/
},
[
    {name: 'cdProceso',  mapping:'cdProceso'},
    {name: 'dsProceso',  mapping:'dsProceso'}
]
);

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
var storeGetNotifi = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_NOTIFICACIONES,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:elJson_GetNotifi
});

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




