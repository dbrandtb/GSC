/* ********************************** JSONREADERS *************************** */

var jsonGrillaFrmDoc= new Ext.data.JsonReader(
{
    root:'MEstructuraList',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
[
    {name: 'cdFormatoDoc',  mapping:'cdFormato',  type: 'string'},
    {name: 'dsNomFormatoDoc',  mapping:'dsNomFormato',  type: 'string'},     
    {name: 'dsFormatoDoc',  mapping:'dsFormato',  type: 'string'}       
] 
);


var elJsonFrmDoc = new Ext.data.JsonReader(
{
    root : 'MEstructuraList',
    totalProperty: 'total',
    successProperty : '@success'
},
[
    {name: 'cdFormato',  mapping:'cdFormato',  type: 'string'},
    {name: 'dsNomFormato',  mapping:'dsNomFormato',  type: 'string'},     
    {name: 'dsFormato',  mapping:'dsFormato',  type: 'string'}            
] 
);

/* ********************************** STORES *************************** */ 


var storeGrillaFrmDoc = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_FORMATOS_DOCUMENTOS,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:jsonGrillaFrmDoc
});


var storeGrillaWin = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_GET_FORMATO_DOCUMENTOS,
        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:elJsonFrmDoc
});