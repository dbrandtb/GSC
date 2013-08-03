/* ********************************** JSONREADERS *************************** */

var jsonGrillaAtributos= new Ext.data.JsonReader(
   {
    root:'MAtributoArchivosList',
    totalProperty: 'totalCount',
    successProperty : '@success'
   }, 
   [
    {name: 'cdTipoar',  mapping:'cdTipoar',  type: 'string'},
    {name: 'dsArchivo',  mapping:'dsArchivo',  type: 'string'},     
    {name: 'cdAtribu',  mapping:'cdAtribu',  type: 'string'},       
    {name: 'dsAtribu',  mapping:'dsAtribu',  type: 'string'},       
 
    {name: 'swFormat',  mapping:'swFormat',  type: 'string'},
    {name: 'nmLmax',  mapping:'nmLmax',  type: 'string'},     
    {name: 'nmLmin',  mapping:'nmLmin',  type: 'string'},       
    {name: 'swObliga',  mapping:'swObliga',  type: 'string'},  
    
    {name: 'dsTabla',  mapping:'dsTabla',  type: 'string'},
    {name: 'otTabval',  mapping:'otTabval',  type: 'string'},     
    {name: 'status',  mapping:'status',  type: 'string'}    
   ] 
  );


var elJsonWinAtr = new Ext.data.JsonReader(
   {
      root : 'MEstructuraList',
      totalProperty: 'total',
      successProperty : '@success'
   },
   [
     {name: 'cdTipoar',  mapping:'cdTipoar',  type: 'string'},
     {name: 'dsArchivo',  mapping:'dsArchivo',  type: 'string'},     
     {name: 'cdAtribu',  mapping:'cdAtribu',  type: 'string'},       
     {name: 'dsAtribu',  mapping:'dsAtribu',  type: 'string'},       
 
     {name: 'swFormat',  mapping:'swFormat',  type: 'string'},
     {name: 'nmLmax',  mapping:'nmLmax',  type: 'string'},     
     {name: 'nmLmin',  mapping:'nmLmin',  type: 'string'},       
     {name: 'swObliga',  mapping:'swObliga',  type: 'string'},  
    
     {name: 'dsTabla',  mapping:'dsTabla',  type: 'string'},
     {name: 'otTabval',  mapping:'otTabval',  type: 'string'},     
     {name: 'status',  mapping:'status',  type: 'string'}    
    ] 
  );


/* ********************************** STORES *************************** */ 


var storeGrillaAtributos = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_ATRIBUTOS_ARCHIVOS,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
        
        
reader:jsonGrillaAtributos
});

//var storeGrillaWin = new Ext.data.Store({
//proxy: new Ext.data.HttpProxy({
//url: _ACTION_OBTENER_ADMINISTRACION_ATRIBUTOS,             //CAMBIAR ESTA LINEA (OBTENER ATRIBUTOS REG)
//        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
//        }),
//reader:elJsonWinAtrFax
//});



