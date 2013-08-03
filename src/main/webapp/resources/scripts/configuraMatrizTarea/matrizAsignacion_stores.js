/* ********************************** JSONREADERS *************************** */
var jsonGrillaNoti= new Ext.data.JsonReader(
{
root:'MEstructuraList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'cdNotificacion',  mapping:'cdNotificacion',  type: 'string'},
{name: 'dsNotificacion',  mapping:'dsNotificacion',  type: 'string'},
{name: 'dsMensaje',  mapping:'dsMensaje',  type: 'string'},
{name: 'cdFormatoOrden',  mapping:'cdFormatoOrden',  type: 'string'},
{name: 'dsFormatoOrden',  mapping:'dsFormatoOrden',  type: 'string'},
{name: 'cdMetEnv',  mapping:'cdMetEnv',  type: 'string'},
{name: 'dsMetEnv',  mapping:'dsMetEnv',  type: 'string'}
]
);

/* ********************************** STORES *************************** */ 
var storeGrillaNoti = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_NOTIFICACIONES,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonGrillaNoti
});


