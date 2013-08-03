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
   id: 'codigo'
},
[
    {name: 'codTema', mapping:'codigo', type: 'string'},
    {name: 'desTema', mapping:'descripcion', type: 'string'}           
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
    {name: 'desEncuesta', mapping:'descripcion', type: 'string'},
    {name: 'numConfig', mapping:'nmConfig', type:'string'}
]
);


/* ********************************** STORES *************************** */ 

//store del combo campanha
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

//store del combo Tema
var storeEncuestaEnc = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_COMBO_ENCUESTA,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
    }),
	reader: elJson_CmbEncuestaEnc
});



