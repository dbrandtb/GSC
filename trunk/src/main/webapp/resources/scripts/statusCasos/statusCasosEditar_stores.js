/* ********************************** JSONREADERS *************************** */

var elJsonEstatus = new Ext.data.JsonReader(
{
    root : 'MEstructuraList',
    totalProperty: 'total',
    successProperty : '@success'
},
[
    {name: 'codStatus',  mapping:'cdStatus',  type: 'string'},
    {name: 'desStatus',  mapping:'dsStatus',  type: 'string'},     
    {name: 'indiAviso',  mapping:'indAviso',  type: 'string'}            
] 
);

//json del combo
var elJson_CmbIndAviso = new Ext.data.JsonReader(
{
root: 'comboIndicadorAviso',
id: 'codigo'
},
[
{name: 'codigo', mapping:'codigo', type: 'string'},
{name: 'descripcion', mapping:'descripcion', type: 'string'}
]
);

/* ********************************** STORES *************************** */ 


var storeGrillaWin = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_STATUS_CASOS,
        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:elJsonEstatus
});

//store del combo de indicador de Aviso
var dsIndAviso = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_STATUS_INDICADOR_AVISO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
           }),
reader: elJson_CmbIndAviso
});