/* ********************************** JSONREADERS *************************** */

var jsonGrillaMecanismoAlerta = new Ext.data.JsonReader(
{
    root:'MEstructuraList',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
[
    {name: 'dsMensaje',  mapping:'dsMensaje',  type: 'string'}
] 
);



/* ********************************** STORES *************************** */ 

var storeGrillaMecanismoAlerta = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _OBTENER_MENSAJES_ALERTA,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:jsonGrillaMecanismoAlerta
});



