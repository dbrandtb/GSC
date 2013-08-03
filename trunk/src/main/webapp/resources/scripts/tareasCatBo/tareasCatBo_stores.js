/* ********************************** JSONREADERS *************************** */

var jsonGrillaTareas= new Ext.data.JsonReader(
{
    root:'MEstructuraListTareas',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
[
    {name: 'codProceso',  mapping:'cdProceso',  type: 'string'},
    {name: 'desProceso',  mapping:'dsProceso',  type: 'string'},     
    {name: 'codModulo',  mapping:'cdModulo',  type: 'string'},
    {name: 'desModulo',  mapping:'dsModulo',  type: 'string'},
    {name: 'codPriord',  mapping:'cdPriord',  type: 'string'},     
    {name: 'desPriord',  mapping:'dsPriord',  type: 'string'}          
] 
);


//json del combo de Prioridad
var elJson_CmbPrioridad = new Ext.data.JsonReader({
        root: 'comboTareaPrioridad',
        id: 'codigo'
        },[
        {name: 'codigo', mapping:'codigo', type: 'string'},
        {name: 'descripcion', mapping:'descripcion', type: 'string'}
        ]
);


/* ********************************** STORES *************************** */ 
var storeGrillaTareas = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_TAREAS_CAT_BO,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:jsonGrillaTareas
});


//store del combo de Prioridad
var descPrioridad = new Ext.data.Store({
   proxy: new Ext.data.HttpProxy({
        url: _ACTION_OBTENER_PRIORIDAD_TAREAS_CAT_BO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
    }),
   reader: elJson_CmbPrioridad
});