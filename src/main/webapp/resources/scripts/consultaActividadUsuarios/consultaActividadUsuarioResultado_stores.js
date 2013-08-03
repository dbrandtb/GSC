/* ********************************** JSONREADERS *************************** */
var jsonGrillaActividad= new Ext.data.JsonReader(
{
root:'listaActividadUsuario',
totalProperty: 'totalCount',
successProperty : '@success'
},

[
{name: 'dsUsuari',  mapping:'dsUsuari',  type: 'string'},
{name: 'timeStamp',  mapping:'timeStamp',  type: 'string'},
{name: 'url',  mapping:'url',  type: 'string'}
]
);

/* ********************************** STORES *************************** */ 
var storeGrillaActividadUsuario = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_ACTIVIDAD_USUARIO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonGrillaActividad,
remoteSort: true
});