/* ********************************** JSONREADERS *************************** */
var jsonGrillaActividad= new Ext.data.JsonReader(
{
root:'listaActividadUsuario',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'cdCampan',  mapping:'cdCampan',  type: 'string'},
{name: 'cdElemento',  mapping:'cdElemento',  type: 'string'},
{name: 'cdEncuesta',  mapping:'cdEncuesta',  type: 'string'},
{name: 'cdModulo',  mapping:'cdModulo',  type: 'string'}
]
);

/* ********************************** STORES *************************** */ 
var storeGrillaActividadUsuario = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_ACTIVIDAD_USUARIO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonGrillaActividad
});