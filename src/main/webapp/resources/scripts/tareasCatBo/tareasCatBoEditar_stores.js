/* ********************************** JSONREADERS *************************** */

var elJsonTareas = new Ext.data.JsonReader(
{
    root : 'MEstructuraList',
    totalProperty: 'total',
    successProperty : '@success'
}, 
[
    {name: 'codProceso',  mapping:'cdProceso',  type: 'string'},
    {name: 'desProceso',  mapping:'dsProceso',  type: 'string'},     
    {name: 'codModulo',  mapping:'cdModulo',  type: 'string'},
    {name: 'desModulo',  mapping:'dsModulo',  type: 'string'},
    {name: 'codPriord',  mapping:'cdPriord',  type: 'string'},     
    {name: 'desPriord',  mapping:'dsPriord',  type: 'string'},
    {name: 'desEstatus',  mapping:'dsEstatus',  type: 'string'},
    {name: 'codEstatus',  mapping:'estatus',  type: 'string'},
    {name: 'indiSemaforo',  mapping:'indSemaforo',  type: 'string'}     
] 
);

//json del combo de Proceso
var elJson_CmbProcesos = new Ext.data.JsonReader({
        root: 'comboProcesosCat',
        id: 'codProceso'
        },[
        {name: 'codigoProceso', mapping:'cdProceso', type: 'string'},
        {name: 'descriProceso', mapping:'dsProceso', type: 'string'}
        ]
);




//json del combo de Estatus
var elJson_CmbEstatus = new Ext.data.JsonReader({
        root: 'comboTareaEstatus',
        id: 'codigo'
        },[
        {name: 'codigo', mapping:'codigo', type: 'string'},
        {name: 'descripcion', mapping:'descripcion', type: 'string'}
        ]
);


//json del combo de modulo
var elJson_CmbModulo = new Ext.data.JsonReader({
        root: 'comboDatosModulo',
        id: 'codigo'
        },[
        {name: 'codigoModulo', mapping:'codigo', type: 'string'},
        {name: 'descriModulo', mapping:'descripLarga', type: 'string'},
        {name: 'nomModulo', mapping:'descripCorta', type: 'string'}
        ]
);

var elJson_resultValida = new Ext.data.JsonReader({
        root: 'messageResult2',
        id: 'messageResult2'
        },[
        {name: 'messageResult2', mapping:'messageResult2', type: 'string'}
       
        ]
);

/* ********************************** STORES *************************** */ 

var storeGrillaWin = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
    url: _ACTION_OBTENER_TAREAS_CAT_BO,
        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
    }),
    reader:elJsonTareas
});

//store del combo de Tarea
var descProcesos = new Ext.data.Store({
   proxy: new Ext.data.HttpProxy({
        url: _ACTION_OBTENER_TAREAS_TAREAS_CAT_BO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
    }),
   reader: elJson_CmbProcesos
});

//store del combo de Estatus
var descEstatus = new Ext.data.Store({
   proxy: new Ext.data.HttpProxy({
        url: _ACTION_OBTENER_ESTATUS_TAREAS_CAT_BO, 
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
    }),
   reader: elJson_CmbEstatus
});

//store del combo de Modulo
var descModulos = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_OBTENER_MODULO_TAREAS_CAT_BO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
           }),

    reader: elJson_CmbModulo
});

var resulValida = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_BUSCAR_TAREAS_CAT_BO_VALIDA,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
           }),

    reader: elJson_resultValida
});

