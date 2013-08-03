/* ********************************** JSONREADERS *************************** */

var jsonGrillaArchivos= new Ext.data.JsonReader(
{
    root:'MEstructuraList',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
[   {name: 'cdtipoar',  type: 'string'},
    {name: 'dsarchivo', type: 'string'},
    {name: 'cdatribu',  type: 'string'},     
    {name: 'dsatribu', type: 'string'},       
    {name: 'otvalor', type: 'string'},       
    {name: 'feingreso', type: 'string'}, 
    

] 
);


/* ********************************** STORES *************************** */ 


var storeGrillaArchivos = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_DATOS_ARCHIVO,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:jsonGrillaArchivos
});





