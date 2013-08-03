
/* ********************************** JSONREADERS *************************** */

var jsonGrillaPolizasCanceladas = new Ext.data.JsonReader(
{
    root:'listaPolizasCanceladas',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
[
    {name: 'asegurado',  mapping:'asegurado',  type: 'string'},
    {name: 'cdUniage',  mapping:'cdUniage',  type: 'string'},     
    {name: 'dsUnieco',  mapping:'dsUnieco',  type: 'string'},
    {name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},
    {name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
    {name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},     
    {name: 'nmPoliza',  mapping:'nmPoliza',  type: 'string'},
    {name: 'nmSituac',  mapping:'nmSituac',  type: 'string'},
    {name: 'feCancel',  mapping:'feCancel',  type: 'string'},     
    {name: 'primaNoDevengada',  mapping:'primaNoDevengada',  type: 'string'},
    {name: 'cdRazon',  mapping:'cdRazon',  type: 'string'},
    {name: 'dsRazon',  mapping:'dsRazon',  type: 'string'},     
    {name: 'tipoCancel',  mapping:'tipoCancel',  type: 'string'},
    {name: 'nmsuplem',  mapping:'nmsuplem',  type: 'string'},
    {name: 'estado',  mapping:'estado',  type: 'string'},
     {name: 'nmPoliex',  mapping:'nmPoliex',  type: 'string'},
     {name: 'inciso',  mapping:'inciso',  type: 'string'},
     
     {name: 'feefecto',  mapping:'feefecto',  type: 'string'},
     {name: 'feanulac',  mapping:'feanulac',  type: 'string'},
     {name: 'fevencim',  mapping:'fevencim',  type: 'string'},
     {name: 'feproren',  mapping:'feproren',  type: 'string'},
    {name: 'comentarios',  mapping:'comentarios',  type: 'string'},
    {name: 'nmCancel',  mapping:'nmCancel',  type: 'string'},
    {name: 'cdPerson',  mapping:'cdPerson',  type: 'string'},
    {name: 'cdMoneda',  mapping:'cdMoneda',  type: 'string'},
    {name: 'reha',  mapping:'reha',  type: 'string'},
] 
);

//json del combo de Prioridad
var elJson_CmbMotivoCancelacion = new Ext.data.JsonReader({
        root: 'comboTareaPrioridad',
        id: 'codigo'
        },[
        {name: 'codigo', mapping:'codigo', type: 'string'},
        {name: 'descripcion', mapping:'descripcion', type: 'string'}
        ]
);


/* ********************************** STORES *************************** */ 

var storeGrillaPolizasCanceladas = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_POLIZAS_CANCELADAS,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
		method: 'POST'
        }),
reader:jsonGrillaPolizasCanceladas
});



