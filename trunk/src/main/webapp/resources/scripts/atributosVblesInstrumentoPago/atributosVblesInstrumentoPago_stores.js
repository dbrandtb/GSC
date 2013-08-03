/* ********************************** JSONREADERS *************************** */

var jsonGrillaAtributosVblesInstPago= new Ext.data.JsonReader(
{
root:'MAtributosVblesInstPagoList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'dsPago',  mapping:'dsPago',  type: 'string'},
{name: 'dsElemen',  mapping:'dsElemen',  type: 'string'},
{name: 'dsUnieco',  mapping:'dsUnieco',  type: 'string'},
{name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
{name: 'dsCampan',  mapping:'dsCampan',  type: 'string'},
{name: 'cdPago',  mapping:'cdPago',  type: 'string'},
{name: 'cdElemen',  mapping:'cdElemen',  type: 'string'},
{name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},
{name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
{name: 'cdatribu',  mapping:'cdatribu',  type: 'string'}]
);


/* ********************************** STORES *************************** */ 
var storeGrillaInstrumentoPago = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_INSTRUMENTO_PAGO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonGrillaAtributosVblesInstPago
});


