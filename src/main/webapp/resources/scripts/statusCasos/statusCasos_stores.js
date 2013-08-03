/* ********************************** JSONREADERS *************************** */

var jsonGrillaStatus= new Ext.data.JsonReader(
{
    root:'MEstructuraList',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
[
    {name: 'codStatus',  mapping:'cdStatus',  type: 'string'},
    {name: 'desStatus',  mapping:'dsStatus',  type: 'string'},     
    {name: 'indiAviso',  mapping:'indAviso',  type: 'string'},       
    {name: 'desIndAviso',  mapping:'dsIndAviso',  type: 'string'}       

] 
);


/* ********************************** STORES *************************** */ 


var storeGrillaStatus = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_STATUS_CASOS,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:jsonGrillaStatus
});




