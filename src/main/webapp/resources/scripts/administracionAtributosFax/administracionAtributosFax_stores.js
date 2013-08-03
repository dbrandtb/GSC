/* ********************************** JSONREADERS *************************** */

var jsonGrillaAdmAtrFax= new Ext.data.JsonReader(
{
    root:'MEstructuraList',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
[
    {name: 'cdAtribu',  mapping:'dsFormato',  type: 'string'},
    {name: 'dsAtributo',  mapping:'cdFormato',  type: 'string'},
    {name: 'cdTipoAr',  mapping:'dsFormato',  type: 'string'},
    {name: 'dsFormato',  mapping:'dsNomFormato',  type: 'string'},     
    {name: 'dsMinimo',  mapping:'dsFormato',  type: 'string'},       
    {name: 'dsMaximo',  mapping:'cdFormato',  type: 'string'},
    {name: 'dsTabla',  mapping:'dsNomFormato',  type: 'string'},     
    {name: 'dsObligatorio',  mapping:'dsFormato',  type: 'string'},
    {name: 'dsTabla',  mapping:'dsNomFormato',  type: 'string'},     
    {name: 'status',  mapping:'dsFormato',  type: 'string'}
] 
);


var elJsonWinAtrFax = new Ext.data.JsonReader(
{
    root : 'MEstructuraList',
    totalProperty: 'total',
    successProperty : '@success'
},
[
    {name: 'cdAtribu',  mapping:'dsFormato',  type: 'string'},
    {name: 'dsAtributo',  mapping:'cdFormato',  type: 'string'},
    {name: 'cdTipoAr',  mapping:'dsFormato',  type: 'string'},
    {name: 'cdFormato',  mapping:'dsNomFormato',  type: 'string'},     
    {name: 'dsMinimo',  mapping:'dsFormato',  type: 'string'},       
    {name: 'dsMaximo',  mapping:'cdFormato',  type: 'string'},
    {name: 'dsTabla',  mapping:'dsNomFormato',  type: 'string'},     
    {name: 'cdObligatorio',  mapping:'dsFormato',  type: 'bool'},
    {name: 'dsTabla',  mapping:'dsNomFormato',  type: 'string'},     
    {name: 'status',  mapping:'dsFormato',  type: 'string'}
] 
);

/* ********************************** STORES *************************** */ 


var storeGrillaFrmDoc = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_ADMINISTRACION_ATRIBUTOS_FAX,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:jsonGrillaAdmAtrFax
});


var storeGrillaWin = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_ADMINISTRACION_ATRIBUTOS_FAX,
        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:elJsonWinAtrFax
});