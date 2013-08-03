/* ********************************** JSONREADERS *************************** */
var jsonNivel= new Ext.data.JsonReader(
{
root:'cboNivel',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'codigo',  mapping:'id',  type: 'string'},
{name: 'descripcion',  mapping:'texto',  type: 'string'}
]
);

var jsonUnidad= new Ext.data.JsonReader(
{
root:'cboUnidad',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'codigo',  mapping:'id',  type: 'string'},
{name: 'descripcion',  mapping:'texto',  type: 'string'}
]
);

var jsonObtener= new Ext.data.JsonReader(
{
root:'MConfigurarCompraTiempoVOList',
totalProperty: 'totalCount',
successProperty : '@success'
},
[
{name: 'unidadId',  mapping:'TUnidad',  type: 'string'},
{name: 'nivelId',  mapping:'nivAtn',  type: 'string'},
{name: 'dstarea',  mapping:'dsProceso',  type: 'string'},
{name: 'tdesde',  mapping:'nmcantDesde',  type: 'string'},
{name: 'thasta',  mapping:'nmcant_Hasta',  type: 'string'},
{name: 'compra',  mapping:'nmvecesCompra',  type: 'string'}

]
);



/* ********************************** STORES *************************** */ 



var storeNivel = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_NIVEL,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonNivel
});


var storeUnidad = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_UNIDAD,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonUnidad
});


var storeObtener = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_COMPRA_TIEMPO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:jsonObtener
});
