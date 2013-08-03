/* ********************************** JSONREADERS *************************** */

//json de la grilla de Caso
var elJsonGrillaCasoUsr = new Ext.data.JsonReader(
{
    id:'nmCaso',
    root : 'MEstructuraRsgCasoUsrList',
    totalProperty: 'totalCount',
    successProperty : 'success'
},
[
    {name: 'nmCaso', mapping: 'nmCaso',  type: 'string'},
    {name: 'cdProceso',  mapping: 'cdProceso', type: 'string'},
    {name: 'dsProceso',  mapping: 'desProceso', type: 'string'},
    {name: 'color', mapping: 'color', type: 'string'},
    {name: 'cdRolMat', mapping: 'cdRolmat', type: 'string' },
    {name: 'desRolMat', mapping: 'desRolmat', type: 'string' }
] 
);

//json de la grilla de Busqueda Usuarios Responsable
var elJsonGrillaUsrRspnsbl = new Ext.data.JsonReader(
{
    id:'cdUsuario',
    root : 'MEstructuraRsgCasoUsrRspnsblList',
    totalProperty: 'totalCount',
    successProperty : 'success'
},
[
  {name: 'desUsuario',  mapping: 'desUsuario',  type: 'string'},
  {name: 'cdUsuario',  mapping: 'cdUsuario', type: 'string'},
  {name: 'caso',  mapping: 'caso',  type: 'string'}     
] 
);

//json de la grilla de Caso Asignados
var elJsonGrillaCasoAsgnds = new Ext.data.JsonReader(
{
    id:'nmCaso',
    root : 'MEstructuraRsgCasoUsrList',
    totalProperty: 'totalCount',
    successProperty : 'success'
},
[
    {name: 'numCaso', mapping: 'nmCaso',  type: 'string'},
    {name: 'codProceso',  mapping: 'cdProceso', type: 'string'},
    {name: 'desProceso',  mapping: 'dsProceso', type: 'string'},
    {name: 'elColor', mapping: 'color', type: 'string'},
    {name: 'codRolMat', mapping: 'cdRolMat', type: 'string' },
    {name: 'dscrRolMat', mapping: 'desRolMat', type: 'string' }
] 
);



/* ********************************** STORES *************************** */ 

//store de la busqueda de la izquierda Caso
var el_storeIzquierdo = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({
		url: _ACTION_BUSCAR_REASIGNACION_CASO_USUARIO,
        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
	reader: elJsonGrillaCasoUsr
});

//store de la busqueda de la derecha Usuario Responsoble
var el_storeDerecha = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({
		url: _ACTION_BUSCAR_REASIGNACION_CASO_USUARIO_RSPNSBL,
        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
	reader: elJsonGrillaUsrRspnsbl
});

var el_storeGrillaCasoAsgnds = new Ext.data.Store({
proxy: new Ext.data.HttpProxy(),
reader: elJsonGrillaCasoAsgnds
});

