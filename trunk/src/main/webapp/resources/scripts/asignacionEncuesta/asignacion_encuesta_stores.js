/* ********************************** JSONREADERS *************************** */
var jsonGrillaEncuesta= new Ext.data.JsonReader(
{
root:'MConsularAsignacionEncuestaList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'nmConfig',  mapping:'nmConfig',  type: 'string'},
{name: 'dsUnieco',  mapping:'dsUnieco',  type: 'string'},
{name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},
{name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
{name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
{name: 'cdModulo',  mapping:'cdModulo',  type: 'string'},
{name: 'estado',  mapping:'estado',  type: 'string'},
{name: 'nmPoliza',  mapping:'nmPoliza',  type: 'string'},
{name: 'cdPerson',  mapping:'cdPerson',  type: 'string'},
{name: 'dsPerson',  mapping:'dsPerson',  type: 'string'},
{name: 'cdUsuari',  mapping:'cdUsuari',  type: 'string'},
{name: 'dsUsuari',  mapping:'dsUsuari',  type: 'string'},
{name: 'status',  mapping:'status',  type: 'string'},
{name: 'nmpoliex', mapping:'poliaExterna',type:'string'}
]
);



/* ********************************** STORES *************************** */ 
var storeGrillaEncuesta = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_ASIGNACION_ENCUESTAS_2,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonGrillaEncuesta
});

/*var storeAsignaEncuesta = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_GET_CONFIGURACION_ENCUESTAS,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonAsignaEncuesta
});*/


