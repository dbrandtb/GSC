/* ********************************** JSONREADERS *************************** */
var jsonGrillaEncuesta= new Ext.data.JsonReader(
{
root:'MEstructuraList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'nmConfig',  mapping:'nmConfig',  type: 'string'},
{name: 'cdEncuesta',  mapping:'cdEncuesta',  type: 'string'},
{name: 'cdCampan',  mapping:'cdCampan',  type: 'string'},
{name: 'cdElemento',  mapping:'cdElemento',  type: 'string'},
{name: 'dsElemen',  mapping:'dsElemen',  type: 'string'},
{name: 'cdModulo',  mapping:'cdModulo',  type: 'string'},
{name: 'cdPerson',  mapping:'cdPerson',  type: 'string'},
{name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},
{name: 'dsUnieco',  mapping:'dsUnieco',  type: 'string'},
{name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
{name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
{name: 'cdProceso',  mapping:'cdProceso',  type: 'string'},
{name: 'dsProceso',  mapping:'dsProceso',  type: 'string'},
{name: 'cdCampan',  mapping:'cdCampan',  type: 'string'},
{name: 'dsCampan',  mapping:'dsCampan',  type: 'string'},
{name: 'cdModulo',  mapping:'cdModulo',  type: 'string'},
{name: 'dsModulo',  mapping:'dsModulo',  type: 'string'},
{name: 'dsEncuesta',  mapping:'dsEncuesta',  type: 'string'}
]
);


var elJsonFrmDocTiempo= new Ext.data.JsonReader(
{
root:'MTiempoEstructuraList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'cdUnidtmpo',  mapping:'cdUnidtmpo',  type: 'string'},
{name: 'nmUnidad',  mapping:'nmUnidad',  type: 'string'}
]
);
/* ********************************** STORES *************************** */ 
var storeGrillaEncuesta = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_CONFIGURACION_ENCUESTAS,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonGrillaEncuesta
});


