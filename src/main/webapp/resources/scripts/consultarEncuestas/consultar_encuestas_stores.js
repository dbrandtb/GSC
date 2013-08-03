/* ********************************** JSONREADERS *************************** */
var jsonGrillaEncuesta= new Ext.data.JsonReader(
{
root:'MEstructuraList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'cdCampan',  mapping:'cdCampan',  type: 'string'},
{name: 'cdElemento',  mapping:'cdElemento',  type: 'string'},
{name: 'cdEncuesta',  mapping:'cdEncuesta',  type: 'string'},
{name: 'cdModulo',  mapping:'cdModulo',  type: 'string'},
{name: 'cdPerson',  mapping:'cdPerson',  type: 'string'},
{name: 'cdPregunta',  mapping:'cdPregunta',  type: 'string'},
{name: 'cdProceso',  mapping:'cdProceso',  type: 'string'},
{name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
{name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},
{name: 'dsCampan',  mapping:'dsCampan',  type: 'string'},
{name: 'dsModulo',  mapping:'dsElemen',  type: 'string'},
{name: 'dsEncuesta',  mapping:'dsEncuesta',  type: 'string'},

{name: 'dsProceso',  mapping:'dsProceso',  type: 'string'},
{name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
{name: 'dsUnieco',  mapping:'dsUnieco',  type: 'string'},
{name: 'nmPoliza',  mapping:'nmPoliza',  type: 'string'},
{name: 'nmPoliex',  mapping:'nmPoliex',  type: 'string'},
{name: 'nmConfig',  mapping:'nmConfig',  type: 'string'},
{name: 'estado',  mapping:'estado',  type: 'string'}
]
);

/* ********************************** STORES *************************** */ 
var storeGrillaEncuesta = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_ENCUESTAS,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonGrillaEncuesta
});


