/* ********************************** JSONREADERS *************************** */

//json del combo Modulo
var elJson_CmbModulo = new Ext.data.JsonReader({
        root: 'comboDatosModulo',
        id: 'codigo'
        },[
        {name: 'codigoModulo', mapping:'codigo', type: 'string'},
        {name: 'descriModulo', mapping:'descripLarga', type: 'string'},
        {name: 'nomModulo', mapping:'descripCorta', type: 'string'}
        ]
);


//json del combo rol
var elJson_CmbRol = new Ext.data.JsonReader(
{
   root: 'comboRoles',
   id: 'id'
},
[
    {name: 'id', type: 'string',mapping:'id'},
    {name: 'texto', type: 'string',mapping:'texto'}           
]
);

var elJsonReasignacionCaso = new Ext.data.JsonReader(
{
    id:'cdUsuario',
    root : 'MEstructuraRsgCasoList',
    totalProperty: 'totalCount',
    successProperty : 'success'
},
[
    {name: 'cdUsuario',  type: 'string', mapping: 'cdUsuario'},
    {name: 'desUsuario',  type: 'string', mapping: 'desUsuario'},
    {name: 'cdModulo',  type: 'string'}
] 
);

var elJsonRsigcGrilla = new Ext.data.JsonReader(
{
    id:'cdUsuario',
    root : 'MEstructuraRsgCasoList',
    totalProperty: 'totalCount',
    successProperty : 'success'
},
[
  {name: 'cdUsuario',  mapping: 'cdUsuario',  type: 'string'},
  {name: 'desUsrRsp',  mapping: 'desUsrRsp', type: 'string'},
  {name: 'cdRolmat',  mapping: 'cdRolmat',  type: 'string'}     
] 
);




/* ********************************** STORES *************************** */ 

//store del combo Modulo
var descModulos = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_OBTENER_MODULO_TAREAS_CAT_BO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
           }),

    reader: elJson_CmbModulo
});

//store del combo rol
var descRoles = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_COMBO_ROLES
    }),
	reader: elJson_CmbRol
});

//store de la busqueda de la izquierda
var el_storeIzquierdo = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_REASIGNACION_CASO,
        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader: elJsonReasignacionCaso
});

var el_storeGrilla = new Ext.data.Store({
proxy: new Ext.data.HttpProxy(),
reader: elJsonReasignacionCaso
});

