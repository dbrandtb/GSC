/* ********************************** JSONREADERS *************************** */

var jsonGrillaFuncionalidadesPrivilegios = new Ext.data.JsonReader(
{
    root:'listaFuncionalidades',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
[
    {name: 'cdElemento',  mapping:'cdElemento',  type: 'string'},
    {name: 'dsElemen',  mapping:'dsElemen',  type: 'string'},     
    {name: 'cdSisRol',  mapping:'cdSisRol',  type: 'string'},
    {name: 'dsSisRol',  mapping:'dsSisRol',  type: 'string'},
    {name: 'cdUsuario',  mapping:'cdUsuario',  type: 'string'},     
    {name: 'dsNombre',  mapping:'dsNombre',  type: 'string'},
    {name: 'cdFunciona',  mapping:'cdFunciona',  type: 'string'},
    {name: 'dsFunciona',  mapping:'dsFunciona',  type: 'string'},     
    {name: 'swEstado',  mapping:'swEstado',  type: 'bool'},
    {name: 'dsEstado',  mapping:'dsEstado',  type: 'string'},
    {name: 'cdOpera',  mapping:'cdOpera',  type: 'string'},
    {name: 'dsOpera',  mapping:'dsOpera',  type: 'string'}
] 
);


/* ********************************** STORES *************************** */ 

var storeGrillaFuncionalidadesPrivilegios = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_FUNCIONALIDADES_PRIVILEGIOS,
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader: jsonGrillaFuncionalidadesPrivilegios
});


