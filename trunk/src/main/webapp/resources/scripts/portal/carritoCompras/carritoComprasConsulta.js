
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

var cm = new Ext.grid.ColumnModel([ 
        {
        header: "cdCarroD",
        dataIndex: 'cdCarroD',
        hidden :true
        },{
        header: "cdUniEco",
        dataIndex: 'cdUniEco',
        hidden :true
        },{
        header: "cdRamo",
        dataIndex: 'cdRamo',
        hidden :true
        },{
        header: "cdPlan",
        dataIndex: 'cdPlan',
        hidden :true
        },{
        id:'cmDsUniEcoCarritoComprasCosultaProductosId',
        header: getLabelFromMap('cmDsUniEcoCarritoComprasCosultaProductosId',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmDsUniEcoCarritoComprasCosultaProductosId', helpMap,'Aseguradora'),        
        //header: "Aseguradora",
        dataIndex: 'dsUniEco',
        width: 170
        },{
        id:'cmDsRamoCarritoComprasCosultaProductosId',
        header: getLabelFromMap('cmDsRamoCarritoComprasCosultaProductosId',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmDsRamoCarritoComprasCosultaProductosId', helpMap,'Producto'),
        //header: "Producto",
        dataIndex: 'dsRamo',
        width: 150
        },{
        id:'cmDsPlanCarritoComprasCosultaProductosId',
        header: getLabelFromMap('cmDsPlanCarritoComprasCosultaProductosId',helpMap,'Plan'),
        tooltip: getToolTipFromMap('cmDsPlanCarritoComprasCosultaProductosId', helpMap,'Plan'),
        //header: "Plan",
        dataIndex: 'dsPlan',
        width: 140
        },{
        id:'cmFeInicionCarritoComprasCosultaProductosId',
        header: getLabelFromMap('cmFeInicionCarritoComprasCosultaProductosId',helpMap,'Inicio'),
        tooltip: getToolTipFromMap('cmFeInicionCarritoComprasCosultaProductosId', helpMap,'Inicio'),
        //header: "Inicio",
        dataIndex: 'feInicio',
        width: 140
        },{
        id:'cmFeEstadoCarritoComprasCosultaProductosId',
        header: getLabelFromMap('cmFeEstadoCarritoComprasCosultaProductosId',helpMap,'Fin'),
        tooltip: getToolTipFromMap('cmFeEstadoCarritoComprasCosultaProductosId', helpMap,'Fin'),
        //header: "Fin",
        dataIndex: 'feEstado',
        width: 140
        },{
        id:'cmMnTotalpCarritoComprasCosultaProductosId',
        header: getLabelFromMap('cmMnTotalpCarritoComprasCosultaProductosId',helpMap,'Prima Total'),
        tooltip: getToolTipFromMap('cmMnTotalpCarritoComprasCosultaProductosId', helpMap,'Prima Total'),
        //header: "Prima Total",
        dataIndex: 'mnTotalp',
        width: 200
    }]);


 function test(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_OBTENER_PRODUCTOS_CARRITO_COMPRAS,
				waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')
                }),
                reader: new Ext.data.JsonReader({
            	root:'mCarritoComprasManagerList',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
	        {name: 'cdUniEco',  type: 'string',  mapping:'cdUniEco'},
	        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        {name: 'cdPlan',  type: 'string',  mapping:'cdPlan'},
	        {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},
	        {name: 'dsPlan',  type: 'string',  mapping:'dsPlan'},
	        {name: 'feInicio',  type: 'string',  mapping:'feInicio'},
	        {name: 'feEstado',  type: 'string',  mapping:'feEstado'},
	        {name: 'mnTotalp',  type: 'string',  mapping:'mnTotalp'}
			])
			});

       return store;
 	}


 	
//READER DE DATOS DE LAS PERSONAS
var readerFormato = new Ext.data.JsonReader(
		{
			root:'aotEstructuraList',
			totalProperty: 'totalCount',
			successProperty : 'success'
		},
		[
		{name: 'desOrden',  mapping:'nmOrden',  type:'string'},
		{name: 'contratante',  mapping:'nombreContratante',  type:'string'}
		]
	);
	
var readerPersonas = new Ext.data.JsonReader({
            root : 'comboPersonas',
            totalProperty: 'totalCount',
            successProperty : '@success'
		    },
           [{
            	name : 'cdPerson',
            	mapping : 'cdPerson',
            	type : 'string'
            },{
            	name : 'dsNombre1',
            	type : 'string',
            	mapping : 'dsNombre1'
            },{
            	name : 'dsApellido',
            	type : 'string',
            	mapping : 'dsApellido'
            },{
            	name : 'dsApellido1',
            	type : 'string',
            	mapping : 'dsApellido1'
            }]);
        
		var dsPersonas = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PERSONAS
            }),
            reader: readerPersonas
        });
        
//##########################################################################
// DATOS DE LAS PERSONAS
var nombreContratante = new Ext.form.ComboBox({
					tpl: '<tpl for="."><div ext:qtip="{cdPerson}.{dsNombre1}" class="x-combo-list-item">{dsNombre1}</div></tpl>',
                    id:'nombreContratanteComboId',
                    //labelWidth: 50,
                    fieldLabel: getLabelFromMap('nombreContratanteComboId',helpMap,'Contratante'),
                    tooltip: getToolTipFromMap('nombreContratanteComboId',helpMap,'Contratante'),
               		store: dsPersonas, 
               		displayField:'dsNombre1', 
               		valueField: 'cdPerson', 
               		hiddenName: 'cboContratante',
               		typeAhead: true, 
               		triggerAction: 'all', 
               		lazyRender:   true, 
               		emptyText:'Seleccione Nombre...', 
               		selectOnFocus:true,
               		mode: 'local',
               		//labelSeparator:'', 
               		//fieldLabel: 'Contratante',
               		forceSelection:true,
               		width: 200
                                        
/*			                    		onSelect: function (record) {
                    	                             incisosForm.findById(('apPaternoContratante')).setValue(record.get("dsApellido"));
                    	                             incisosForm.findById(('apMaternoContratante')).setValue(record.get("dsApellido1"));
                    	                             this.collapse();
                                                     this.setValue(record.get("cdPerson"));
			                    		}*/
                        })

var nombreAsegurado = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{cdPerson}.{dsNombre1}" class="x-combo-list-item">{dsNombre1}</div></tpl>',
				store: dsPersonas, 
                id:'nombreAseguradoComboId',
                fieldLabel:getLabelFromMap('nombreAseguradoComboId',helpMap,'Asegurado'),
                tooltip:getToolTipFromMap('nombreAseguradoComboId',helpMap,'Asegurado'),
                labelAlign: 'right',
	            //anchor:'95%',
               // labelWidth: 50,
           		width: 200,
           		displayField:'dsNombre1', 
           		valueField: 'cdPerson', 
           		hiddenName: 'cboAsegurado',
           		typeAhead: true, 
           		mode:'local',
           		triggerAction:'all', 
           		lazyRender:true, 
           		//labelWidth:'100',
           		emptyText:'Seleccione Nombre...', 
           		selectOnFocus:true,           		
           		//labelSeparator:'', 
           		//fieldLabel: 'Asegurado',
           		forceSelection:true
           		
                 		
			                    		/*onSelect: function (record) {
                    	                             incisosForm.findById(('apPaternoAsegurado')).setValue(record.get("dsApellido"));
                    	                             incisosForm.findById(('apMaternoAsegurado')).setValue(record.get("dsApellido1"));
                    	                             this.collapse();
                                                     this.setValue(record.get("cdPerson"));
			                    		}*/
                        })	                    		
			                    		
var apMaternoContratante = new Ext.form.TextField({
		id: 'apMaternoContratanteId', 
		fieldLabel: getLabelFromMap('apMaternoContratanteId',helpMap,'Apellido Materno'),
		tooltip: getToolTipFromMap('apMaternoContratanteId',helpMap,'Apellido Materno Contratante'),
		//fieldLabel: 'Apellido Materno', 
        //id: 'apMaternoContratante', 
        name: 'apMaternoContratante',
        allowBlank: true,
        readOnly: true,
        width:200
    });

var apPaternoContratante = new Ext.form.TextField({
		id: 'apPaternoContratanteId', 
		fieldLabel: getLabelFromMap('apPaternoContratanteId',helpMap,'Apellido Paterno'),
		tooltip: getToolTipFromMap('apPaternoContratanteId',helpMap,'Apellido Paterno Contratante'),                    				    
        //fieldLabel: 'Apellido Paterno', 
        //id: 'apPaternoContratante', 
        name: 'apPaternoContratante',
        allowBlank: true,
        readOnly: true,
        width:200
    });
    
var apMaternoAsegurado = new Ext.form.TextField({
		id: 'apMaternoAseguradoId', 
		fieldLabel: getLabelFromMap('apMaternoAseguradoId',helpMap,'Apellido Materno'),
		tooltip: getToolTipFromMap('apMaternoAseguradoId',helpMap,'Apellido Materno Asegurado'),                    				    
        //fieldLabel: 'Apellido Materno', 
        //id: 'apMaternoAsegurado', 
        name: 'apMaternoAsegurado',
        allowBlank: true,
        readOnly: true,
        width:200
    });

var apPaternoAsegurado = new Ext.form.TextField({
		id: 'apPaternoAseguradoId', 
		fieldLabel: getLabelFromMap('apPaternoAseguradoId',helpMap,'Apellido Paterno'),
		tooltip: getToolTipFromMap('apPaternoAseguradoId',helpMap,'Apellido Paterno Asegurado'),                    				    
        //fieldLabel: 'Apellido Paterno', 
        //id: 'apPaternoAsegurado', 
        name: 'apPaternoAsegurado',
        allowBlank: true,
        readOnly: true,
        width:200
    });

   	

var readerEncOrden = new Ext.data.JsonReader({
          root : 'MCarritoComprasManagerListEncOrden',
          totalProperty: 'totalCount',
          successProperty : '@success'
    },
         [{
          	name : 'nmOrden',
          	mapping : 'nmOrden',
          	type : 'string'
          },{
          	name : 'nombreContratante',
          	type : 'string',
          	mapping : 'contratante'
          }]);
      
var dsEncOrden = new Ext.data.Store({
          proxy: new Ext.data.HttpProxy({
              url: _ACTION_OBTENER_ENC_ORDEN
          }),
          reader: readerEncOrden,
            baseParams: {
			         cdCarro:'1'
			      }
});

//la grilla
var lasFilas=new Ext.data.Record.create([
			{name: 'cdCarroD',  type: 'string',  mapping:'cdCarroD'},
            {name: 'cdUniEco',  type: 'string',  mapping:'cdUniEco'},
	        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        {name: 'cdPlan',  type: 'string',  mapping:'cdPlan'},
	        {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},
	        {name: 'dsPlan',  type: 'string',  mapping:'dsPlan'},
	        {name: 'feInicio',  type: 'string',  mapping:'feInicio'},
	        {name: 'feEstado',  type: 'string',  mapping:'feEstado'},
	        {name: 'mnTotalp',  type: 'string',  mapping:'mnTotalp'}               
]);

//el JsonReader de la grilla a mostrar
var jsonGrilla= new Ext.data.JsonReader(
  {
   root:'MCarritoComprasManagerList',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
 lasFilas
);

function storeGrilla(){
       store = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({
             url: _ACTION_OBTENER_PRODUCTOS_CARRITO_COMPRAS,
             waitMsg: 'Espere por favor....'
            }),
       reader:jsonGrilla,
       baseParams: {
					cdCarro:'1',
       	            cdUsuari:'RZ'
					
					}
       });
       return store;
}

var grid2;
 
var cdCarro = new Ext.form.Hidden( 
			{
                 name : 'cdCarro'
             }
             );
    
 var cdUsuari = new Ext.form.Hidden({
 	   name:'cdUsuari'
    });
  
datosPersona=new Ext.Panel({
  layout:'form',
  borderStyle:false,
  labelWidth:70,
  items:[ 
  
         nombreContratante,
         nombreAsegurado
         
         /*apPaternoContratante,
         apMaternoContratante,
         apPaternoAsegurado,
         apMaternoAsegurado*/
        ]
});

    
pie=new Ext.Panel({
  layout:'form',
  borderStyle:false,
 labelAlign: 'right', 
  items:[ 
          mnTotalp,
	      nmdsc,
		  nmtotal
        ]
});


var tableDatosPersona = new Ext.Panel({
	id: 'datosPersonaPanelId',
	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('datosPersonaPanelId', helpMap,'Datos de las personas')+'</span>',
	//title: '<span style="color:black;font-size:14px;">Datos de las personas</span>',
   	layout:'column',
   	border :false,
   	width: 600,
   	labelAlign: 'right',
    defaults: {
        // applied to each contained panel
        bodyStyle:'padding:5px'
    },
 
    items: [{
                columnWidth: .1 
		    },{
		        columnWidth: .8,
		        items:[datosPersona]
		    },{
		        columnWidth: .1
		    }]
}); 



var tableResumen = new Ext.Panel({
   	id: 'datosResumenPrimasTotalPanelId',
   
	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('datosResumenPrimasTotalPanelId', helpMap,'Resumen Prima Total')+'</span>',
   	//title: '<span style="color:black;font-size:14px;">Resumen Prima Total</span>',
	layout:'column',border :false,
	labelAlign: 'left',
    defaults: {
        // applied to each contained panel
        bodyStyle:'padding:0px'
    },
    items: [/*{
                columnWidth: .3 
		    },{
		        columnWidth: .3
		    },*/{
		        //columnWidth: .4,
		        items:[pie]
		    }]
});
    
 
    
function createGrid(){
    grid2= new Ext.grid.GridPanel({
    	 id: 'grid2',
         store:storeGrilla(),
         reader:jsonGrilla,
         border:true,
         autoWidth: true,
         cm: cm,
         clicksToEdit:1,
         //loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
         successProperty: 'success',
          buttonAlign: "center",
         buttons:[
               {
        		   id:'grid2ButtonComprarId',
        		   text:getLabelFromMap('grid2ButtonAgregarId', helpMap,'Comprar'),
            	   tooltip:getToolTipFromMap('grid2ButtonAgregarId', helpMap,'Comprar'),	
	               //text:'Comprar',
	               //tooltip:'Agrega un nuevo Requisito de Cancelacion',
	               handler:function(){agregar()}
               },
               {
        		   id:'grid2ButtonBorrarId',
        		   text:getLabelFromMap('grid2ButtonAgregarId', helpMap,'Eliminar'),
            	   tooltip:getToolTipFromMap('grid2ButtonAgregarId', helpMap,'Eliminar'),	
	               //text:'Borrar',
	               //tooltip:'Borra el Requisito seleccionado',
	               handler:function(){borrar(getSelectedCodigo(grid2))}
               },
               {
        		   id:'grid2ButtonImprimirId',
        		   text:getLabelFromMap('grid2ButtonAgregarId', helpMap,'Imprimir'),
            	   tooltip:getToolTipFromMap('grid2ButtonAgregarId', helpMap,'Imprimir')	
	               //text:'Imprimir',
	               //tooltip:'Borra el Requisito seleccionado',
	              // handler:function(){borrar(getSelectedRecord(grid2))}
               },
               {
        		   id:'grid2ButtonEnviarCorreoId',
        		   text:getLabelFromMap('grid2ButtonEnviarCorreoId', helpMap,'Enviar Correo'),
            	   tooltip:getToolTipFromMap('grid2ButtonEnviarCorreoId', helpMap,'Enviar Correo'),	
	               //text:'Enviar Correo',
	               //tooltip:'Enviar Informacion por E-Mail',
	               handler:function(){enviarCorreo(getSelectedRecord(grid2))}
               },
               {
	               id:'grid2ButtonContinuarComprandoId',
        		   text:getLabelFromMap('grid2ButtonContinuarComprandoId', helpMap,'Continuar Comprando'),
            	   tooltip:getToolTipFromMap('grid2ButtonContinuarComprandoId', helpMap,'Continuar Comprando')	
	               //text:'Continuar Comprando',
	               //tooltip:'Borra el Requisito seleccionado'
               }
               ],
         width: 500,
         frame:true,
         height:375,
         title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Detalle de Compras</span>',
         sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
         viewConfig: {autoFill: true,forceFit:true},
         bbar:tableResumen	    
      });
 
}

createGrid();

//el JsonReader de la parte izquierda
var elJson = new Ext.data.JsonReader(
    {
        root : 'MEstructuraList',
        totalProperty: 'total',
        successProperty : '@success'
    },
    [ 
    {name: 'codRazon',  mapping:'cdRazon',  type: 'string'},
    {name: 'dsRazon',  mapping:'dsRazon',  type: 'string'},  
    {name: 'swReInst',  mapping:'swReInst',  type: 'string'},
    {name: 'swVerPag',  mapping:'swVerPag',  type: 'string'}  
    ]
)

       
var incisosForm = new Ext.FormPanel({
	id: 'incisosForm',
	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm', helpMap,'Carrito de Compras')+'</span>',
    el:'gridProductos',
    //title: '<span style="color:black;font-size:14px;">Carrito de Compras</span>',
    iconCls:'logo',
    bodyStyle:'background: white',
    frame:true,   
    url: _ACTION_OBTENER_PRODUCTOS_CARRITO_COMPRAS,
    store:storeGrilla(),
    reader:jsonGrilla,
    //reader: elJson,
    layout: 'column',
    layoutConfig: {columns: 2, align: 'left'},
    labelAlign:'top',
    width: 600,
    height:500,
    items: [/*{
    		 layout:'form',
    		 columnWidth: .2,
    		 width:300
    		},*/{
        		 layout: 'form',border: false,labelAlign:'left',
              	 items:[ 
              	          cdCarro,
              	          cdUsuari,
              	          //datosPersona,
              	          tableDatosPersona,
              	          grid2
                        ]
        	}]	            
        
});   

  function getSelectedRecord(){
               var m = grid2.getSelections();
               if (m.length == 1 ) {
                  return m[0];
               }
          }

     function getSelectedCodigo(){
              var m = grid2.getSelections();
              var jsonData = "";
              for (var i = 0, len = m.length;i < len; i++) {
                var ss = m[i].get("cdCarroD");
                if (i == 0) {
                jsonData = jsonData + ss;
                } else {
                  jsonData = jsonData + "," + ss;
               }
              }
              return jsonData;
         }
     
    borrar = function(key) {
    			if(key != "")
				{
					var conn = new Ext.data.Connection();
				
					Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminara el registro seleccionado'),function(btn)
					{
	           	       if (btn == "yes")
	           	        {
	           	        var _params = { cdCarro:'1',
	           	                        cdCarroD: key};
	           	        execConnection(_ACTION_BORRAR_REG_CARRITO_COMPRAS, _params, cbkBorrar);
	           	        }
	                	
					})
				}else{
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Seleccionar un registro para realizar esta operaci&oacute;n'));
						//Ext.Msg.alert('Advertencia', 'Debe seleccionar un producto de Carrito de Compras para borrar');
					}
           };
    function cbkBorrar(_success, _messages) {
    	if (_success) {
    		Ext.Msg.alert('Aviso', _messages, function(){
    					reloadGrid ();
    		});
    	}else {
    		Ext.Msg.alert('Error', _messages);
    	}
    } 

function cbkConnection(_success, _message) {
	if (!_success) {
		Ext.Msg.alert('Error', _message);
	}else {
		Ext.Msg.alert('Aviso', _message, function(){reloadGrid();});
	}
}

dsPersonas.load();
incisosForm.render();
grid2.store.load();

dsEncOrden.load({callback:function(record,opt,success){
                        if (success) {
                        	//Ext.getCmp("nmOrden").setValue(dsEncOrden.reader.jsonData.MCarritoComprasManagerListEncOrden[0].nmOrden);
                        	//Ext.getCmp("contratante").setValue(dsEncOrden.reader.jsonData.MCarritoComprasManagerListEncOrden[0].nombreContratante);
                        }
                        }
                         
});
cargarMontos();


            
});


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
					         cdCarro:'1',
					         cdUsuari:'RZ',
					         cdPerson:'1000'
					      }
        });
    
       var mnTotalp = new Ext.form.TextField({
        id: 'mnTotalpMontos', 
		fieldLabel: getLabelFromMap('mnTotalpMontos',helpMap,'Subtotal'),
		tooltip: getToolTipFromMap('mnTotalpMontos',helpMap,'Subtotal'),
 	    //fieldLabel: 'Subtotal',
 	    labelWidth:20,
        name:'mnTotalp',
        readOnly:true,
        width: 100
    });

    var nmdsc = new Ext.form.TextField({
        id: 'nmDscMontos', 
		fieldLabel: getLabelFromMap('mnTotalpMontos',helpMap,'Dscto'),
		tooltip: getToolTipFromMap('mnTotalpMontos',helpMap,'Descuento'),                    				    
        //fieldLabel: 'Dscto',
        labelWidth:20,
        name:'nmDsc',
        readOnly:true,
        width: 100
    });
    
    var nmtotal = new Ext.form.TextField({
         id: 'nmDscMontos', 
		fieldLabel: getLabelFromMap('mnTotalpMontos',helpMap,'Total'),
		tooltip: getToolTipFromMap('mnTotalpMontos',helpMap,'Total'),                    				       	
    	//fieldLabel: 'Total',
    	labelWidth:20,
        name:'nmtotal',
        readOnly:true,
        width: 100
    }); 
        
function cargarMontos(){
dsMontos.load({callback:function(record,opt,success){
                            mnTotalp.setValue(dsMontos.reader.jsonData.elSubTotal);
                        	nmdsc.setValue(dsMontos.reader.jsonData.elDescuento);
                        	nmtotal.setValue(dsMontos.reader.jsonData.elTotalFn);
                        } 
});
}

function reloadGrid(){
	var _params = {
			cdCarro:'1',
       	    cdUsuari:'RZ'
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
	
	cargarMontos();
}


function cbkReload(_r, _options, _success, _store) {
	if (!_success){
		_store.removeAll();
		addGridNewRecord();
	}
}