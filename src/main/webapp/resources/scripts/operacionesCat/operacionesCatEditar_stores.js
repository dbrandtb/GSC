/* ********************************** JSONREADERS *************************** */
var jsonGrilla= new Ext.data.JsonReader(
{
root:'mEstructuraList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'cdNotificacion',  mapping:'cdNotificacion',  type: 'string'},
{name: 'dsNotificacion',  mapping:'dsNotificacion',  type: 'string'},
{name: 'dsMensaje',  mapping:'dsMensaje',  type: 'string'},
{name: 'dsFormatoOrden',  mapping:'dsFormatoOrden',  type: 'string'},
{name: 'cdFormatoOrden',  mapping:'cdFormatoOrden',  type: 'string'},
{name: 'cdMetEnv',  mapping:'cdMetEnv',  type: 'string'},
{name: 'dsMetEnv',  mapping:'dsMetEnv',  type: 'string'},
{name: 'cdProceso',  mapping:'cdProceso',  type: 'string'}
]
);



/* ********************************** STORES *************************** */ 


var storeGrilla = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_MOTIVOS_CANCELACION,
		waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonGrilla
});
