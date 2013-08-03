/* ********************************** JSONREADERS *************************** */

//json del combo modulo
var elJson_CmbModuloEnc = new Ext.data.JsonReader({
        root: 'comboModuloEnc',
        id: 'codigo'
        },[
        {name: 'codModulo', mapping:'codigo', type: 'string'},
        {name: 'desModulo', mapping:'descripcion', type: 'string'}        ]
);



//json del combo campanha
var elJson_CmbCampanhaEnc = new Ext.data.JsonReader({
        root: 'comboCampanhaEnc',
        id: 'codigo'
        },[
        {name: 'codCampan', mapping:'codigo', type: 'string'},
        {name: 'desCampan', mapping:'descripcion', type: 'string'}        ]
);


//json del combo Tema
var elJson_CmbTemaEnc = new Ext.data.JsonReader(
{
   root: 'comboTemaEnc',
   id: 'nmConfig'
},
[
    {name: 'codTema', mapping:'codigo', type: 'string'},
    {name: 'desTema', mapping:'descripcion', type: 'string'},
    {name: 'numConfig', mapping:'nmConfig', type:'string'}           
]
);


//json del combo Encuesta
var elJson_CmbEncuestaEnc = new Ext.data.JsonReader(
{
   root: 'comboEncuestaEnc',
   id: 'codigo'
},
[
    {name: 'codEncuesta', mapping:'codigo', type: 'string'},
    {name: 'desEncuesta', mapping:'descripcion', type: 'string'}
]
);

//json del combo Aseguradoras
var elJson_CmbAseguradoraEnc = new Ext.data.JsonReader(
{
   root: 'aseguradoraComboBox',
   id: 'cdUniEco'
},
[
    {name: 'codAseguradora', mapping:'cdUniEco', type: 'string'},
    {name: 'desAseguradora', mapping:'dsUniEco', type: 'string'}
]
);

//json del combo Productos
var elJson_CmbProductoEnc = new Ext.data.JsonReader(
{
   root: 'cboProductosAsegEncuestas',
   id: 'cdUniEco'
},
[
    {name: 'codProducto', mapping:'cdRamo', type: 'string'},
    {name: 'desProducto', mapping:'dsRamo', type: 'string'}
]
);


/* ********************************** STORES *************************** */ 

//store del combo modulo
var storeModuloEnc = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_COMBO_MODULO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
           }),

    reader: elJson_CmbModuloEnc
});

//store del combo campanha
var storeCampanhaEnc = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_COMBO_CAMPANHA,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
           }),

    reader: elJson_CmbCampanhaEnc
});

//store del combo Tema
var storeTemaEnc = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_COMBO_TEMA,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
    }),
	reader: elJson_CmbTemaEnc
});

//store del combo Encuesta
var storeEncuestaEnc = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_COMBO_ENCUESTA,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
    }),
	reader: elJson_CmbEncuestaEnc
});

//store del combo Aseguradora
var storeAseguradoraEnc = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_COMBO_ASEGURADORA,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
    }),
	reader: elJson_CmbAseguradoraEnc
});


//store del combo Producto
var storeProductosEnc = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_COMBO_PRODUCTOS,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
    }),
	reader: elJson_CmbProductoEnc
});


