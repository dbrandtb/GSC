/* ********************************** JSONREADERS *************************** */
var jsonGrillaMatrizAsignacion= new Ext.data.JsonReader(
{
root:'MEstructuraList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'cdproceso',  mapping:'cdproceso',  type: 'string'},
{name: 'dsproceso',  mapping:'dsproceso',  type: 'string'},
{name: 'cdformatoorden',  mapping:'cdformatoorden',  type: 'string'},
{name: 'dsformatoorden',  mapping:'dsformatoorden',  type: 'string'},
{name: 'cdelemento',  mapping:'cdelemento',  type: 'string'},
{name: 'dselemen',  mapping:'dselemen',  type: 'string'},
{name: 'cdunieco',  mapping:'cdunieco',  type: 'string'},
{name: 'dsunieco',  mapping:'dsunieco',  type: 'string'},
{name: 'cdramo',  mapping:'cdramo',  type: 'string'},
{name: 'dsramo',  mapping:'dsramo',  type: 'string'},
{name: 'cdmatriz',  mapping:'cdmatriz',  type: 'string'}
]
);

/* ********************************** STORES *************************** */ 
var storeGrillaMatrizAsignacion = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_MATRICES,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonGrillaMatrizAsignacion
});


