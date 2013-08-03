var helpMap = new Map();

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
//VALORES POR DEFECTO DE LA PAGINA
var codigoCarro = new Ext.form.Hidden(
    {
        disabled:false,
        name:'codigoCarro',
        value: CODIGO_CARRO 
   }
);

var codigoContrato = new Ext.form.Hidden(
    {
        disabled:false,
        name:'codigoContrato',
        value: CODIGO_CONTRATO 
   }
);

var codigoTipoDom = new Ext.form.Hidden(
    {
        disabled:false,
        name:'codigoTipoDom',
        value: CODIGO_TIPO_DOM 
   }
);

//##########################################################################
// DATOS DE LAS PERSONAS
var contratante = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('contratante', helpMap,'Contratante'), 
		tooltip: getToolTipFromMap('contratante', helpMap,'Contratante Detalle Orden de Compras'),  
		hasHelpIcon:getHelpIconFromMap('contratante',helpMap),
		Ayuda: getHelpTextFromMap('contratante',helpMap),
        //fieldLabel: 'Contratante', 
        id: 'contratante', 
        name: 'contratante',
        allowBlank: true,
        readOnly: true,
        width:200
    });

var nmOrden = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('nmOrden', helpMap,'Orden'), 
		tooltip: getToolTipFromMap('nmOrden', helpMap,'N&uacute;mero de Orden Detalle Orden de Compras'),  
		hasHelpIcon:getHelpIconFromMap('nmOrden',helpMap),
		Ayuda: getHelpTextFromMap('nmOrden',helpMap),
        //fieldLabel: 'Orden', 
        id: 'nmOrden', 
        width:150,
        name: 'nmOrden',
        allowBlank: true,
        readOnly: true
    });


//JSONREADER DE DATOS DE LAS PERSONAS
 var readerEncOrden = new Ext.data.JsonReader({
          root : 'MEstructuraList',
          totalProperty: 'totalCount',
          successProperty : '@success'
    },
         [{
          	name : 'nmOrden',
          	type : 'string',
           	mapping : 'nmOrden'
          },{
          	name : 'nombreContratante',
          	type : 'string',
          	mapping : 'contratante'
          }]);
      
var dsEncOrden = new Ext.data.Store({
          proxy: new Ext.data.HttpProxy({
              url: _ACTION_OBTENER_ORDEN_DE_COMPRAS_PERSONA
          }),
          reader: readerEncOrden,
            baseParams: {
			         cdCarro: codigoCarro.getValue()   
			      }
      });
            
persona=new Ext.Panel(
 {
  layout:'form',
  borderStyle:false,
  items:[ 
         contratante,
         nmOrden
        ]
 }
);

var tableDatosPersona = new Ext.Panel(
{
	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('136', helpMap,'Datos de las personas')+'</span>',    
    layout:'column',
    border :false,
    width: 590,
    defaults: {
       bodyStyle:'padding:5px'
        },
    items: [
            {
                columnWidth: .1 
            },
            {
                columnWidth: .8,
                items:[persona]
            },
            {
                columnWidth: .1
            }
           ]
    }
); 

//##########################################################################
//DIRECCION
//RECORD DE LA GRILLA DOMICILIO
var recordDomicilios = new Ext.data.Record.create(
        [
        {name: 'nmOrdDom',  mapping:'nmOrdDom',  type:'string'},
        {name: 'cdTipDom',  mapping:'cdTipDom',  type:'string'},
        {name: 'dsTipDom', mapping:'dsTipDom', type:'string'},  
        {name: 'dsDomici',   mapping:'dsDomici',   type:'string'},
        {name: 'nmNumero',   mapping:'nmNumero',   type:'string'},
        {name: 'nmNumInt',   mapping:'nmNumInt',   type:'string'},
        {name: 'cdPais',   mapping:'cdPais',   type:'string'},
        {name: 'dsPais',  mapping:'dsPais',  type:'string'},
        {name: 'cdEdo',  mapping:'cdEdo',  type:'string'},
        {name: 'dsEdo', mapping:'dsEdo', type:'string'},    
        {name: 'cdMunici',   mapping:'cdMunici',   type:'string'},
        {name: 'dsMunici',   mapping:'dsMunici',   type:'string'},
        {name: 'cdColoni',   mapping:'cdColoni',   type:'string'},
        {name: 'dsColoni',   mapping:'dsColoni',   type:'string'}       
        ]
);


//JSONREADER DE DOMICILIOS
var jreaderGrillaDomicilio= new Ext.data.JsonReader(
  {
   root:'MCarritoComprasManagerListDire',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
  recordDomicilios
);

//STORE DE DATOS DEL DOMICILIO
function storeGrillaDomicilio(){
       storeDomicilio = new Ext.data.Store(
           {
             proxy: new Ext.data.HttpProxy(
                 {
                     url: _ACTION_OBTENER_ORDEN_DE_COMPRAS_DOMICILIO
                 }
             ),
             reader:jreaderGrillaDomicilio,
             baseParams: {
                          cdContra:codigoContrato.getValue(),
                          cdTipDom:codigoTipoDom.getValue()        
                         }
          }
      );
     return storeDomicilio;
};

var grillaDomicilios;

//GRILLA DE DOMICILIOS
function createGridDomicilios(){
  var cmDirecciones = new Ext.grid.ColumnModel(
    [  
       {
	   	header: getLabelFromMap('cmDsTipDomDtlOrdnCmpr',helpMap,'Tipo'),
        tooltip: getToolTipFromMap('cmDsTipDomDtlOrdnCmpr', helpMap,'Columna Tipo'),		           	
       //header: "Tipo",
        dataIndex: 'dsTipDom',
        sortable: true,
        width: 60
        },
        {
	   	header: getLabelFromMap('cmDsDomiciDtlOrdnCmpr',helpMap,'Calle'),
        tooltip: getToolTipFromMap('cmDsDomiciDtlOrdnCmpr', helpMap,'Columna Calle'),		           	
        //header: "Calle",
        dataIndex: 'dsDomici',
        sortable: true,
        width: 120
        },
        {
	   	header: getLabelFromMap('cmNmNumeroDtlOrdnCmpr',helpMap,'N&uacute;mero'),
        tooltip: getToolTipFromMap('cmNmNumeroDtlOrdnCmpr', helpMap,'Columna N&uacute;mero'),		           	
        //header: "Numero",
        dataIndex: 'nmNumero',
        sortable: true,
        width: 50
        },
        {
	   	header: getLabelFromMap('cmDsPaisDomDtlOrdnCmpr',helpMap,'Pa&iacute;s'),
        tooltip: getToolTipFromMap('cmDsPaisDomDtlOrdnCmpr', helpMap,'Columna Pa&iacute;s'),		           	
        //header: "Pais",
        sortable: true,
        dataIndex: 'dsPais',
        width: 50
        },
        {
	   	header: getLabelFromMap('cmDsEdoDomDtlOrdnCmpr',helpMap,'Estado'),
        tooltip: getToolTipFromMap('cmDsEdoDomDtlOrdnCmpr', helpMap,'Columna Estado'),		           	
        //header: "Estado",
        sortable: true,
        dataIndex: 'dsEdo',
        width: 70
        },
        {
	   	header: getLabelFromMap('cmDdsMuniciDtlOrdnCmpr',helpMap,'Munic&iacute;pio'),
        tooltip: getToolTipFromMap('cmDdsMuniciDtlOrdnCmpr', helpMap,'Columna Munic&iacute;pio'),		           	
        //header: 'Municipio',
        sortable: true,
        dataIndex: 'dsMunici',
        width: 120
        },
        {
	   	header: getLabelFromMap('cmDsColoniDtlOrdnCmpr',helpMap,'Colonia'),
        tooltip: getToolTipFromMap('cmDsColoniDtlOrdnCmpr', helpMap,'Columna Colonia'),		           	
        //header: 'Colonia',
        sortable: true,        
        dataIndex: 'dsColoni',
        width: 120
        }
      ]
    );

       grillaDomicilios = new Ext.grid.EditorGridPanel(
         {
             id:'gridDomicilios',
             store:storeGrillaDomicilio(),
             cm: cmDirecciones,
             loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
             frame:true,
             width:590,
             height:150,
             title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Direcci&oacute;n</span>'
        }
        );
};
// FUNCION DE CREAR LA GRILLA DE DOMICILIO
createGridDomicilios();

//##########################################################################
//PAGAR

var instrumentoPago = new Ext.form.TextField(
   {
   	fieldLabel: getLabelFromMap('dsForPag', helpMap,'Instrumento de Pago'), 
	tooltip: getToolTipFromMap('dsForPag', helpMap,'Detalle Instrumento de Pago'),  
	hasHelpIcon:getHelpIconFromMap('dsForPag',helpMap),
	Ayuda: getHelpTextFromMap('dsForPag',helpMap),
    //fieldLabel: 'Instrumento de Pago',
	width:130,
    id: 'dsForPag', 
    name: 'dsForPag',
    allowBlank: true
   }
);

var bancoPago = new Ext.form.TextField(
   {
   	fieldLabel: getLabelFromMap('dsNombre', helpMap,'Banco'), 
	tooltip: getToolTipFromMap('dsNombre', helpMap,'Detalle Orden de Compras Banco'),  
	hasHelpIcon:getHelpIconFromMap('dsNombre',helpMap),
		Ayuda: getHelpTextFromMap('dsNombre',helpMap),
		width:130,
    //fieldLabel: 'Banco', 
    id: 'dsNombre', 
    name: 'dsNombre',
    allowBlank: true
   }
);

//DSTITARJ
var tipoTarjeta = new Ext.form.TextField(
    {
   	fieldLabel: getLabelFromMap('dsTitArg', helpMap,'Tipo Tarjeta'), 
	tooltip: getToolTipFromMap('dsTitArg', helpMap,'Detalle Orden de Compras Tipo Tarjeta'),  			
	hasHelpIcon:getHelpIconFromMap('dsTitArg',helpMap),
		Ayuda: getHelpTextFromMap('dsTitArg',helpMap),
		width:130,
    //fieldLabel: 'Tipo Tarjeta', 
    id: 'dsTitArg', 
    name: 'dsTitArg',
    allowBlank: true
    }
);

var numTarjeta = new Ext.form.TextField(
   {
   	fieldLabel: getLabelFromMap('nmTarj', helpMap,'N&uacute;mero de Tarjeta'), 
	tooltip: getToolTipFromMap('nmTarj', helpMap,'Detalle Orden de Compras N&uacute;mero de Tarjeta'),
	hasHelpIcon:getHelpIconFromMap('nmTarj',helpMap),
		Ayuda: getHelpTextFromMap('nmTarj',helpMap),
		width:130,
        //fieldLabel: 'Numero de Tarjeta', 
        id: 'nmTarj', 
        name: 'nmTarj',
        allowBlank: true
    }
    );


var fechaVencimiento = new Ext.form.TextField(
{
   	fieldLabel: getLabelFromMap('feVence', helpMap,'Fecha de Vencimiento'), 
	tooltip: getToolTipFromMap('feVence', helpMap,'Detalle Orden de Compras Fecha de Vencimiento'),  
	hasHelpIcon:getHelpIconFromMap('feVence',helpMap),
		Ayuda: getHelpTextFromMap('feVence',helpMap),
		width:130,
        //fieldLabel: 'Fecha de Vencimiento', 
        id: 'feVence', 
        name: 'feVence',
        allowBlank: true
    });

var readerPagar = new Ext.data.JsonReader(
		{
			root:'MCarritoComprasManagerListDetOrden',
			totalProperty: 'totalCount',
			successProperty : 'success'
		},
		[
		{name: 'cdBanco',  mapping:'cdBanco',  type:'string'},
		{name: 'dsNombre',  mapping:'dsNombre',  type:'string'},
		{name: 'cdPerson', mapping:'cdPerson', type:'string'},	
		{name: 'dsForPag',   mapping:'dsForPag',   type:'string'},
		{name: 'dsTitArg',   mapping:'dsTitArg',   type:'string'},		
		{name: 'nmTarj',   mapping:'nmTarj',   type:'string'},
		{name: 'feVence',   mapping:'feVence',   type:'string'}
		]);
         
		var dsPagar = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ORDEN_DE_COMPRAS_PAGAR
            }),
            reader: readerPagar,
              baseParams: {
					         cdCarro: codigoCarro.getValue(),
					         cdUsuari:'',
					         cdPerson:''
					      }
        });

pagar=new Ext.Panel(
  {
  layout:'form',
  borderStyle:false,
  items:[ 
          instrumentoPago,
          bancoPago,
          tipoTarjeta,
          numTarjeta,
          fechaVencimiento
        ]
  }
);  
  
var tablePagar = new Ext.Panel({
	//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('pnlPtDtlOrdnCmpr', helpMap,'Pagar')+'</span>',    
    title: '<span style="color:black;font-size:14px;align:center">Pagar</span>',
    layout:'column',
    border :false, 
    frame: true, 
    width: 584,
    defaults: {
        bodyStyle:'padding:5px'
             },
    items: [
            {
                columnWidth: .2 
            },
            {
                columnWidth: .6,
                items:[pagar]
            },
            {
                columnWidth: .2
            }
           ]
});


//##########################################################################
//RECORD DE LA GRILLA DE DETALLE
var recordDetalle=new Ext.data.Record.create([
  {name: 'dsUniEco',  mapping:'dsUniEco',  type: 'string'},
  {name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
  {name: 'dsRazSoc',  mapping:'dsRazSoc',  type: 'string'},
  {name: 'feInicio',  mapping:'feInicio',  type: 'string'},
  {name: 'feFinSus',  mapping:'feFinSus',  type: 'string'},
  {name: 'mnTotalp',  mapping:'mnTotalp',  type: 'string'}                   
]);


////JSONREADER DE DETALLE
var jreaderGrillaDetalle = new Ext.data.JsonReader(
  {
   root:'mcEstructuraList',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
 recordDetalle
);

function storeGrillaDetalle(){
       store = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({
             url: _ACTION_ORDEN_DE_COMPRAS_DETALLE
            }),
       reader:jreaderGrillaDetalle,
       baseParams: {
                    cdCarro:codigoCarro.getValue()
                   }
       });
       return store;
}
var grid2;

//GRILLA DE DETALLE
var cmDetalle = new Ext.grid.ColumnModel(
[
    {
   	header: getLabelFromMap('cmDsUniEcoDtlOrdnCmpr',helpMap,'Aseguradora'),
    tooltip: getToolTipFromMap('cmDsUniEcoDtlOrdnCmpr', helpMap,'Columna Aseguradora'),		           	
    //header: "Aseguradora", 
    dataIndex: 'dsUniEco',
    sortable: true,
    width: 120
    },
    {
   	header: getLabelFromMap('cmDsRamoDtlOrdnCmpr',helpMap,'Producto'),
    tooltip: getToolTipFromMap('cmDsRamoDtlOrdnCmpr', helpMap,'Columna Producto'),		           	
    //header: "Producto",
    dataIndex: 'dsRamo',
    sortable: true,
    width: 100
    },
    {
   	header: getLabelFromMap('cmDsRazSocDtlOrdnCmpr',helpMap,'Raz&oacute;n Social'),
    tooltip: getToolTipFromMap('cmDsRazSocDtlOrdnCmpr', helpMap,'Columna Raz&oacute;n Social'),		           	
    //header: "Raz&oacute;n Social",
    dataIndex: 'dsRazSoc',
    sortable: true,
    width: 80
    },
    {
   	header: getLabelFromMap('cmFeInicioDtlOrdnCmpr',helpMap,'Inicio'),
    tooltip: getToolTipFromMap('cmFeInicioDtlOrdnCmpr', helpMap,'Columna Inicio'),		           	
    //header: "Inicio",
    dataIndex: 'feInicio',
    sortable: true,
    width: 50
    },
    {
   	header: getLabelFromMap('cmFeFinSusDtlOrdnCmpr',helpMap,'Fin'),
    tooltip: getToolTipFromMap('cmFeFinSusDtlOrdnCmpr', helpMap,'Columna Fin'),		           	
    //header: "Fin",
    dataIndex: 'feFinSus',
    sortable: true,
    width: 50
    },
    {
   	header: getLabelFromMap('cmMnTotalpDtlOrdnCmpr',helpMap,'Prima Total'),
    tooltip: getToolTipFromMap('cmMnTotalpDtlOrdnCmpr', helpMap,'Columna Prima Total'),		           	
    //header: "Prima Total",
    dataIndex: 'mnTotalp',
    sortable: true,
    width: 60
    }
]
);

//##########################################################################
//SUBTOTAL DESCUETO TOTALFINAL
var mnTotalp = new Ext.form.TextField(
    {
   	fieldLabel: getLabelFromMap('txtFldMnTotalpDtlOrdnCmpr', helpMap,'Subtotal'), 
	tooltip: getToolTipFromMap('txtFldMnTotalpDtlOrdnCmpr', helpMap,'Detalle Orden de Compras'),  		
	hasHelpIcon:getHelpIconFromMap('txtFldMnTotalpDtlOrdnCmpr',helpMap),
		Ayuda: getHelpTextFromMap('txtFldMnTotalpDtlOrdnCmpr',helpMap),
    //fieldLabel: 'Tipo Tarjeta', 
        //fieldLabel: 'Subtotal',
        labelWidth:10,
        name:'mnTotalp',
        readOnly:true,
        width: 49
    }
);

var nmdsc = new Ext.form.TextField(
    {
   	fieldLabel: getLabelFromMap('txtFldNmDscDtlOrdnCmpr', helpMap,'Dscto'), 
	tooltip: getToolTipFromMap('txtFldNmDscDtlOrdnCmpr', helpMap,'Detalle Orden de Compras'),
	hasHelpIcon:getHelpIconFromMap('txtFldNmDscDtlOrdnCmpr',helpMap),
		Ayuda: getHelpTextFromMap('txtFldNmDscDtlOrdnCmpr',helpMap),
        //fieldLabel: 'Dscto',
        labelWidth:10,
        name:'nmDsc',
        readOnly:true,
        width: 49
    }
);

var nmtotal = new Ext.form.TextField(
    {
   	fieldLabel: getLabelFromMap('txtFldNmtotalDtlOrdnCmpr', helpMap,'Total'), 
	tooltip: getToolTipFromMap('txtFldNmtotalDtlOrdnCmpr', helpMap,'Detalle Orden de Compras'),
	hasHelpIcon:getHelpIconFromMap('txtFldNmtotalDtlOrdnCmpr',helpMap),
		Ayuda: getHelpTextFromMap('txtFldNmtotalDtlOrdnCmpr',helpMap),
	
        //fieldLabel: 'Total',
        labelWidth:10,
        name:'nmtotal',
        readOnly:true,
       width: 49
    }
);

 var readerMontos = new Ext.data.JsonReader(
		{
			root:'MCarritoComprasManagerListDetOrden',
			totalProperty: 'totalCount',
			successProperty : 'success'
		},
		[
		{name: 'cdBanco',  mapping:'cdBanco',  type:'string'},
		{name: 'dsNombre',  mapping:'dsNombre',  type:'string'},
		{name: 'cdPerson', mapping:'cdPerson', type:'string'},	
		{name: 'dsForPag',   mapping:'dsForPag',   type:'string'},
		{name: 'cdTitArg',   mapping:'cdTitArg',   type:'string'},		
		{name: 'nmTarj',   mapping:'nmTarj',   type:'string'},
		{name: 'feVence',   mapping:'feVence',   type:'string'}
		]);
         
		var dsMontos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_MONTOS_CARRITO_COMPRAS
            }),
            reader: readerMontos,
              baseParams: {
					         cdCarro: codigoCarro.getValue(),
					         cdUsuari:'',
					         cdPerson:''
					      }
        });

pie=new Ext.Panel({
  layout:'form',
  borderStyle:true, 
  height:120,
  items:[ 
          mnTotalp,
	      nmdsc,
		  nmtotal
        ]
});

var tableResumen = new Ext.Panel({
	//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('pnlPtDtlOrdnCmpr', helpMap,'Resumen Prima Total')+'</span>',    
    title: '<span style="color:black;font-size:14px;">Resumen Prima Total</span>',
    border :false, 
    frame: true, 
    width: 586,
    layout:'column',
    border :false,
    defaults: {
        bodyStyle:'padding:0px'
    },
    items: [
    	
    		{
                layout:'form',
		        height:80,
                columnWidth: .4 
            },
            {
                layout:'form',
		        height:80,
                columnWidth: .3
            },
            {
                layout:'form',
		        height:80,
                columnWidth: .3,
                items:[pie]
            }
            
            ]
});


function createGrid(){
    grid2= new Ext.grid.GridPanel({
         id: 'grid2',
         store:storeGrillaDetalle(),
         reader:jreaderGrillaDetalle,
         border:true,
         //autoWidth: true,
         cm: cmDetalle,
         clicksToEdit:1,
         successProperty: 'success',         
         width:586,
         frame:true,
         height:150,
		 //title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('grid2DtlOrdnCmpr', helpMap,'Detalle de Compras')+'</span>',
         title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Detalle de Compras</span>',
         sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
         viewConfig: {
                    autoFill: true,
                    forceFit:true
                    }
      });
 
}

createGrid();

botones=new Ext.Panel({
  layout:'column',
  borderStyle:true, 
  bodyStyle:'background: white',
  width: 550,
  height: 50, 
  items:[ 
          {
          buttons:[
               {
					text:getLabelFromMap('pmDtlOrdnCmprButtonRegresar', helpMap,'Regresar'),
					tooltip:getToolTipFromMap('pmDtlOrdnCmprButtonRegresar', helpMap,'Regresa a la Pantalla Anterior'),			        			
                   //text:'Regresar',
                   //tooltip:'Regresa a la Pantalla Anterior',
                   handler: function(){window.location.href = _ACTION_ORDEN_DE_COMPRAS_REGRESAR;}
               },
               {
					text:getLabelFromMap('pmDtlOrdnCmprButtonFinalizar', helpMap,'Finalizar'),
					tooltip:getToolTipFromMap('pmDtlOrdnCmprButtonFinalizar', helpMap,'Finaliza la Orden'),			        			
                   //text:'Finalizar',
                   //tooltip:'Finaliza la Orden ',
                   handler:function(){finalizar(codigoCarro.getValue())}
               }
               ]
              }
        ]
});


//##########################################################################
// mostrar       
var incisosForm = new Ext.FormPanel({
    id: 'incisosForm',
    el:'gridElementos',
	//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formDtlOrdnCmpr', helpMap,'Detalle de Orden Compra')+'</span>',
    title: '<span style="color:black;font-size:14px;">Detalle de Orden Compra</span>',
    iconCls:'logo',
    bodyStyle:'background: white',
    frame:true,   
    url: _ACTION_ORDEN_DE_COMPRAS_DETALLE,
    reader:jreaderGrillaDetalle,
    layout: 'column',
    layoutConfig: {columns: 2, align: 'right'},
    labelAlign:'top',
    width: 600,
    height:810,
    items: [
    		{
        		 border: true,
        		 labelAlign:'right', 
        		 layout: 'form',
              	 items:[ 
              	          tableDatosPersona,
              	          grillaDomicilios,
              	          tablePagar,
    		              grid2,
    		              tableResumen,
    		              botones 
                        ]
        	}
           
            ]              
        
}); 

incisosForm.render();
grid2.store.load();
grillaDomicilios.store.load();

dsEncOrden.load(
    {
     callback:function(record,opt,success)
     {
       if (success) 
       {
       	nmOrden.setValue(dsEncOrden.reader.jsonData.MEstructuraList[0].nmOrden);
       	contratante.setValue(dsEncOrden.reader.jsonData.MEstructuraList[0].contratante);
       }
     }
                         
});
dsPagar.load(
	{
	callback:function(record,opt,success)
	{
         if (success) 
         {
         	instrumentoPago.setValue(dsPagar.reader.jsonData.MCarritoComprasManagerListDetOrden[0].dsForPag);
         	bancoPago.setValue(dsPagar.reader.jsonData.MCarritoComprasManagerListDetOrden[0].dsNombre);
         	tipoTarjeta.setValue(dsPagar.reader.jsonData.MCarritoComprasManagerListDetOrden[0].dsTitArg);
         	numTarjeta.setValue(dsPagar.reader.jsonData.MCarritoComprasManagerListDetOrden[0].nmTarj);
         	fechaVencimiento.setValue(dsPagar.reader.jsonData.MCarritoComprasManagerListDetOrden[0].feVence);
         }
    }                         
});

dsMontos.load(
	{
	 callback:function(record,opt,success)
	 {
        mnTotalp.setValue(dsMontos.reader.jsonData.elSubTotal);
    	nmdsc.setValue(dsMontos.reader.jsonData.elDescuento);
    	nmtotal.setValue(dsMontos.reader.jsonData.elTotalFn);
     }           
});

function finalizar(key) {
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400054', helpMap,'Se Finalizar&aacute; la Transacci&oacute;n'),function(btn)
		//Ext.MessageBox.confirm('Finalizar', 'Se Finalizara la Transaccion',function(btn)
			{
		        if (btn == "yes"){
		        	var _params = {cdCarro: key};
					execConnection(_ACTION_FINALIZAR_ORDEN_DE_COMPRAS, _params, cbkFinalizar);
                }
		    }
		    );
		
};
function cbkFinalizar(_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){});
	}
};
});