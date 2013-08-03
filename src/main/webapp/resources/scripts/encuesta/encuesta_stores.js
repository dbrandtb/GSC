/* ********************************** JSONREADERS *************************** */
var jsonGrillaEncuesta= new Ext.data.JsonReader(
{
root:'MEncuestasList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'cdEncuesta',  mapping:'cdEncuesta',  type: 'string'},
{name: 'dsEncuesta',  mapping:'dsEncuesta',  type: 'string'},
{name: 'feRegistro',  mapping:'feRegistro',  type: 'string'},
{name: 'swEstado',  mapping:'swEstado',  type: 'string'}
]
);

/* ********************************** STORES *************************** */ 
var storeGrillaEncuesta = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_ENCUESTAS,
        waitMsg : getLabelFromMap('400017', helpMap, 'Espere por favor....')
        }),
reader:jsonGrillaEncuesta
});


