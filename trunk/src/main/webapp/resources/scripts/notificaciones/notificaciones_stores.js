
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
{name: 'cdRegion',  mapping:'cdRegion',  type: 'string'},
{name: 'dsRegion',  mapping:'dsRegion',  type: 'string'},
{name: 'dsProceso',  mapping:'dsProceso',  type: 'string'},
{name: 'cdEstado',  mapping:'cdEstado',  type: 'string'},
{name: 'dsEstado',  mapping:'dsEstado',  type: 'string'},
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


