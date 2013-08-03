/* ********************************** JSONREADERS *************************** */

var jsonGrillaPolizasCancelar = new Ext.data.JsonReader(
{
    root:'MEncuestaList',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
[
    {name: 'cdFormatoDoc',  mapping:'cdFormato',  type: 'string'},
    {name: 'dsNomFormatoDoc',  mapping:'dsNomFormato',  type: 'string'},     
    {name: 'dsFormatoDoc',  mapping:'dsFormato',  type: 'string'}       
] 
);


/* ********************************** STORES *************************** */ 

var storeGrillaPolizasCancelar = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_POLIZAS,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:jsonGrillaPolizasCancelar
});

