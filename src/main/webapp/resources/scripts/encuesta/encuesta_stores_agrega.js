/* ********************************** JSONREADERS *************************** */
var jsonEncuestaPregunta= new Ext.data.JsonReader(
{
root:'MEstructuraList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'cdEncuesta',  mapping:'cdEncuesta',  type: 'string'},
{name: 'dsEncuesta',  mapping:'dsEncuesta',  type: 'string'},
{name: 'feRegistro',  mapping: 'feRegistro',  type: 'string'},
{name: 'swEstado',  mapping:'swEstado',  type: 'string'}
]
);

/* ********************************** STORES *************************** */ 
var storeEncuestaPregunta = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_ENCUESTAS_GET,
        waitMsg : getLabelFromMap('400017', helpMap, 'Espere por favor....')
        }),
reader: jsonEncuestaPregunta
});
