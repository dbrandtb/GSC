/* ********************************** JSONREADERS *************************** */
var jsonGrilla= new Ext.data.JsonReader(
{
root:'MOperacionCatList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'cdUniEco',  mapping:'cdUniEco',  type: 'string'},
{name: 'dsUniEco',  mapping:'dsUniEco',  type: 'string'},
{name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
{name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
{name: 'cdElemento',  mapping:'cdElemento',  type: 'string'},
{name: 'cdProceso',  mapping:'cdProceso',  type: 'string'},
{name: 'dsProceso',  mapping:'dsProceso',  type: 'string'},
{name: 'cdGuion',  mapping:'cdGuion',  type: 'string'},
{name: 'dsGuion',  mapping:'dsGuion',  type: 'string'},
{name: 'status',  mapping:'status',  type: 'string'},
{name: 'dsTipGuion',  mapping:'dsTipGuion',  type: 'string'},
{name: 'dsElemen',  mapping:'dsElemen',  type: 'string'}
]
);



/* ********************************** STORES *************************** */ 


var storeGrilla = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_GUIONES,
		waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonGrilla
});
