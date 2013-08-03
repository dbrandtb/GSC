/* ********************************** JSONREADERS *************************** */

var jsonGrillaPolizasCancelar = new Ext.data.JsonReader(
{
    root:'listaPolizas',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
 [
    {name: 'asegurado',  mapping:'asegurado',  type: 'string'},
    {name: 'cdUniage',  mapping:'cdUniage',  type: 'string'},     
    {name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},
    {name: 'dsUnieco',  mapping:'dsUnieco',  type: 'string'},
    {name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
    {name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},     
    {name: 'nmPoliza',  mapping:'nmPoliza',  type: 'string'},
    {name: 'nmSituac',  mapping:'nmSituac',  type: 'string'},
    {name: 'feCancel',  mapping:'feCancel',  type: 'string'},     
    {name: 'primaNoDevengada',  mapping:'primaNoDevengada',  type: 'bool'},
    {name: 'cdRazon',  mapping:'cdRazon',  type: 'string'},
    {name: 'dsRazon',  mapping:'dsRazon',  type: 'string'},     
    {name: 'tipoCancel',  mapping:'tipoCancel',  type: 'string'},
    {name: 'nmsuplem',  mapping:'nmsuplem',  type: 'string'},
    {name: 'cdagrupa',  mapping:'cdagrupa',  type: 'string'},
    {name: 'nmrecibo',  mapping:'nmrecibo',  type: 'string'},
    {name: 'cdcancel',  mapping:'cdcancel',  type: 'string'},
    {name: 'status',  mapping:'status',  type: 'string'},
    {name: 'swcancela',  mapping:'swcancela',  type: 'bool'},
    {name: 'estado',  mapping:'estado',  type: 'string'},
    {name: 'nmPoliex',  mapping:'nmPoliex',  type: 'string'}
  ]
);


/* ********************************** STORES *************************** */ 

var storeGrillaPolizasCancelar = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_POLIZAS_A_CANCELAR,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:jsonGrillaPolizasCancelar
});
