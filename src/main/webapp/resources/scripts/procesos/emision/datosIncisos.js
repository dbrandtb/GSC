Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


//******************DATOS DE APOYO******************
	var idAseguradora = new Ext.form.Hidden({
	    name:'cdUnieco',
	    value:_cdunieco
	});
	var idProducto = new Ext.form.Hidden({
	    name:'cdRamo',
	    value:_cdramo
	});
	var idPoliza = new Ext.form.Hidden({
	    name:'nmpoliza',
	    value:_nmpoliza
	});
	var idEstado = new Ext.form.Hidden({
	    name:'estado',
	    value:_estado
	});
	var idNmSituac = new Ext.form.Hidden({
	    name:'nmsituac',
	    value:_nmsituac
	});
	var idStaus = new Ext.form.Hidden({
	    name:'status',
	    value:_status
	});
	var nombreAseguradora = new Ext.form.TextField({
	    fieldLabel: 'Aseguradora',
	    name:'dsUnieco',
	    value:_aseguradora,
	    disabled:true,
	    width: 200
	});
	var nombreProducto = new Ext.form.TextField({
	    fieldLabel: 'Producto',
	    name:'dsRamo',
	    value:_producto,
	    disabled:true,
	    width: 200
	});
	var numPoliza = new Ext.form.TextField({
	    fieldLabel: 'Póliza',
	    name:'poliza',
	    value:_poliza,
	    disabled:true,
	    width: 200
	});
	var numInciso = new Ext.form.TextField({
	    fieldLabel: 'Inciso',
	    name:'cdInciso',
	    value:_cdinciso,
	    disabled:true,
	    width: 200
	});
	var nombreDescripcion = new Ext.form.TextField({
	    fieldLabel: 'Descripción',
	    name:'dsDescripcion',
	    value:_dsdescripcion,
	    disabled:true,
	    width: 200
	});
    
    var opcionesForm = new Ext.form.FormPanel({    
    	el:'encabezadoInciso',
        //title: '<span style="color:black;font-size:14px;">Inciso</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',   
        frame:true,                         
        width: 668,
        autoHeight: true,
        items: [{
                layout:'form',
                border: false,
                items:[{
                    //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
                    bodyStyle:'background: white',
                	labelWidth: 100,                	
                	layout: 'form',
                	frame:true,
                	baseCls: '',
                	buttonAlign: "center",
                        items:[{
                            layout:'column',
                            border:false,
                            //columnWidth: 4,
                            items:[{
                                //columnWidth:4,
                                layout: 'form',                     
                                border: false,                      
                                items:[ 
                                		idAseguradora,
                                		idProducto,
                                		idPoliza,
                                        nombreAseguradora,
                                        nombreProducto,
                                        numPoliza
                                ]
                            },
                            {
                                //columnWidth:4,
                                layout: 'form',                     
                                border: false,                      
                                items:[ 
                                		idEstado,
                                		idNmSituac,
                                		idStaus,
                                        numInciso,
                                        nombreDescripcion
                                ]
                            }]
                        }]
                }]
		}]  
	});
	//opcionesForm.render();


//******************Store para grid de Cobreturas******************
function createOptionGrid(){  
    var store = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
            url: 'procesoemision/coberturasIncisos.action?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&nmSituac='+idNmSituac.getValue()
        }),
        reader: new Ext.data.JsonReader({
            root: 'listCoberturasIncisos',
            id: 'listCoberturas'
        },[
                {name: 'cdGarant',        type: 'string',    mapping:'cdGarant'},
                {name: 'dsGarant',        type: 'string',    mapping:'dsGarant'},
               	{name: 'dsSumaAsegurada', type: 'string',    mapping:'dsSumaAsegurada'},
                {name: 'dsDeducible',     type: 'string',    mapping:'dsDeducible'}
        ]),
        remoteSort: true
    });
    store.load();
    return store;
}


//******************Store para grid de Rol de la Póliza******************
function createFnPoliza(){
	var storeGridFnPoliza = new Ext.data.Store({
	    proxy: new Ext.data.HttpProxy({
	        method: 'POST', 
	        url: 'procesoemision/consultaFuncionPoliza.action'+'?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&nmSituac='+idNmSituac.getValue()
	    }),
	    reader: new Ext.data.JsonReader({
	    root: 'fnPoliza'
	    },[
	       {name: 'dsFuncion', type: 'string', mapping:'rol'},
	       {name: 'dsNombre',  type: 'string', mapping:'nombre'},
	       {name: 'cdRol',     type: 'string', mapping:'cdRol'},
	       {name: 'nmSituac',  type: 'string', mapping:'nmSituac'}
	     ]),
	    remoteSort: true
	});
	storeGridFnPoliza.load();
	return storeGridFnPoliza;
}

//******************Store para grid de Accesorios******************
function createGridAccesorios(){
    var storeA = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
            url: 'procesoemision/accesoriosIncisos.action?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&nmSituac='+idNmSituac.getValue()
        }),
        reader: new Ext.data.JsonReader({
            root: 'listAccesoriosIncisos',
            id: 'listAccesorios'
        },[
                {name: 'nmObjeto',     type: 'string', mapping:'nmObjeto'},
                {name: 'dsObjeto',     type: 'string', mapping:'dsObjeto'},
                {name: 'ptObjeto',     type: 'string', mapping:'ptObjeto'},
                {name: 'cdTipoObjeto', type: 'string', mapping:'cdTipoObjeto'}
        ]),
        remoteSort: true
    });
    storeA.load();
    return storeA;
}


//******************Store para grid de Agrupadores******************
function createGridAgrupadores(){
	var storeGridAgrupadores = new Ext.data.Store({
	    proxy: new Ext.data.HttpProxy({
	        method: 'POST', 
	        url: 'procesoemision/consultaAgrupador.action'+'?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&nmSituac='+idNmSituac.getValue()
	    }),
	    reader: new Ext.data.JsonReader({
	    root: 'listMpoliagr'
	    },[
	       {name: 'dsNombre',          type: 'string', mapping:'dsNombre'},
	       {name: 'dsBanco',           type: 'string', mapping:'dsBanco'},
	       {name: 'dsInstrumentoPago', type: 'string', mapping:'cdForpag'},
	       {name: 'dsDomicilio',       type: 'string', mapping:'dsDomicilio'},
	       {name: 'dsForpag',          type: 'string', mapping:'dsForpag'},
	       {name: 'dsSucursal',        type: 'string', mapping:'dsSucursal'},
	       {name: 'dsTipotarj',        type: 'string', mapping:'dsTipotarj'},
	       {name: 'nmcuenta',          type: 'string', mapping:'nmcuenta'},
	       {name: 'fechaUltreg',       type: 'string', mapping:'fechaUltreg'},
	       {name: 'nmDigver',          type: 'string', mapping:'nmDigver'},
	       {name: 'muestraCampos',     type: 'string', mapping:'muestraCampos'}
	     ]),
	    remoteSort: true
	});
	storeGridAgrupadores.load();
	return storeGridAgrupadores;
}

//Variable tipo hidden para evitar la excepcion de la pantalla
//si el pl no trae datos.
var sinDatos = new Ext.form.Hidden({
    id:'sinDatos',
    name:'SinDatos'
});
//Si el Pl no trae datos se mostrara un mensaje y se pintara un hidden.
if (ElementosExt==""){
/*	Ext.Msg.show({
	   title:'Datos Rol',
	   msg: 'No se encontraron datos.',
	   buttons: Ext.Msg.OK,
	   icon: Ext.MessageBox.INFO
	});*/	
    ElementosExt = sinDatos;
}


// *****Funcion que al dar click en el boton del GridPanel muestra un mensaje si no se seleccionó un renglon*****
// @param idBtn id del boton del GridPanel
// @param grid  elemento de tipo Ext.grid.GridPanel (tal como fue declarada la variable)
function muestraMsgEligeRecordDeGrid(idBtn, grid) {
	Ext.getCmp(idBtn).on('click',function(){
		if( !grid.getSelectionModel().hasSelection() ) {
			Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED);
		}
	});
}

//******************DATOS INCISOS******************
    var datosIncisosForm = new Ext.form.FormPanel({    
    	//el:'datosInciso',
        //title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Z</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        labelAlign:'center',     
        //frame:true,
        width: 656,
        autoHeight: true,
        collapsible: true,
        items: [{
                layout:'form',                
                border: false,
                items:[{
                    bodyStyle:'background: white',
                	labelWidth: 200,
                	layout: 'form',
                	frame:true,
                	baseCls: '',
                	buttonAlign: 'center',
                        items:[{
                            layout:'column',
                            border:false,
                            items:[{
                                layout: 'form',                     
                                border: false,                      
                                items: ElementosExt
                            }]
                        }]
                }]
		}]  
	});


//******************DETALLE COBERTURAS******************
function detalleCoberturas(cdGarant,dsGarant,dsSumaAsegurada){

    var conn = new Ext.data.Connection();
    conn.request({
         url: 'procesoemision/datosCoberturas.action?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&nmSituac='+idNmSituac.getValue()+'&cdGarant='+cdGarant+'&status='+idStaus.getValue(),
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if(Ext.util.JSON.decode(response.responseText).success == false){
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         var codeExt = Ext.util.JSON.decode(response.responseText).dext;
                         //if(codeExt != ''){
                      	
							var campoGrant = new Ext.form.TextField({								
								name:'garantia',
								value:dsGarant,
								fieldLabel: 'Cobertura',
								disabled:true,
								width: 200
							});
	                     	var campoSuma = new Ext.form.TextField({	                     	 	
								name:'suma',
								value:dsSumaAsegurada,
								fieldLabel: 'Suma Asegurada',
								disabled:true,
								width: 200
	                     	});
                             var panelDetalleCoberturaEnc = new Ext.FormPanel({
                                    labelAlign:'right',
                                    border: false,
                                    width: 350,
                                    autoHeight: true,
                                    items:[
			                                campoGrant,
			                                campoSuma
			                        ]
                             });
                             var panelDetalleCobertura = new Ext.FormPanel({
                             		labelAlign:'right',
                                    border: false,
                                    width: 350,
                                    autoHeight: true,
                                    items: codeExt
                             });
                             
                             var windowCobertura = new Ext.Window({
                                title: 'Detalles Cobertura',
                                width: 350,
                                autoHeight: true,
                                layout: 'fit',
                                plain:true,
                                modal:true,
                                bodyStyle:'padding:5px;',
                                buttonAlign:'center',
                                items: [panelDetalleCoberturaEnc,panelDetalleCobertura],
                                buttons:[{
                                    text: 'Regresar',
                                    tooltip: 'Cierra la ventana',
                                    handler: function(){
                                        windowCobertura.close();
                                        windowCobertura.destroy();
                                    }//handler
                                }]
                            });
                            windowCobertura.show();
                         //}else{
                           // Ext.Msg.alert('Mensaje', 'No se encontraron datos');
                         //}
                      }//else
                   }//function
    });
}


//******************COBERTURAS******************
	var selectedId;
	var cdGarant;
	var dsGarant;
	var dsSumaAsegurada;
	
	var storeGrid = createOptionGrid();
	var cmc = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
	    {header: "cdGarant",       dataIndex:'cdGarant',        width: 120, sortable:true, hidden:true},
	    {header: "Cobertura",      dataIndex:'dsGarant',        width: 300, sortable:true, renderer:crearHLink},
	  	{header: "Suma Asegurada", dataIndex:'dsSumaAsegurada', width: 160, sortable:true},
	    {header: "Deducible",      dataIndex:'dsDeducible',     width: 160, sortable:true}
	]);
	cmc.defaultSortable = true;

var gridCoberturas = new Ext.grid.GridPanel({
    store:storeGrid,
    border:true,
    buttonAlign: 'center',
    baseCls:' background:white ',
    cm: cmc,
	buttons:[{
				id:'detallesCobertura',
				text:'Detalles',
            	tooltip:'Detalles'
            }],                                 
    width:656,
    frame:true,
    height:200,
    
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
			rowselect: function(sm, row, rec) { 
		        //selectedId = storeGrid.data.items[row].id;
		        cdGarant = rec.get('cdGarant');
		        dsGarant = rec.get('dsGarant');
		        dsSumaAsegurada = rec.get('dsSumaAsegurada');
			}
        }
        
    }),
    //viewConfig: {autoFill: true,forceFit:true}, 
    bbar: new Ext.PagingToolbar({
        pageSize:20,
        store: storeGrid,                  
        displayInfo: true,
		displayMsg: 'Registros mostrados {0} - {1} de {2}',
		emptyMsg: 'No hay registros para mostrar',
		beforePageText: 'P&aacute;gina',
		afterPageText: 'de {0}' 
    })
});

muestraMsgEligeRecordDeGrid('detallesCobertura', gridCoberturas);

gridCoberturas.on('rowdblclick', function() {
	getAyudaWindow(_cdcia,idProducto.getValue(),cdGarant);    
});

Ext.getCmp('detallesCobertura').on('click',function(){
	detalleCoberturas(cdGarant,dsGarant,dsSumaAsegurada);
});

//Funcion para ventana de ayuda del grid de coberturas
    function getAyudaWindow(cdCia,cdRamo,cdGarant){

    var conn = new Ext.data.Connection();
    conn.request({
         url: 'procesoemision/ayudaCobertura.action?cdUnieco='+cdCia+'&cdRamo='+cdRamo+'&cdGarant='+cdGarant,
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         var ayudaVoE = Ext.util.JSON.decode(response.responseText).ayudaCoberturaVo.dsGarant;
                         var ayudaVoL = Ext.util.JSON.decode(response.responseText).ayudaCoberturaVo.dsAyuda;
                         //alert('ayudaVoE :'+ayudaVoE);
                         //alert('ayudaVoL :'+ayudaVoL);
                         if(ayudaVoE!='' && ayudaVoE!=null){
    
                            var windowAyuda= new Ext.Window({
                                title: 'Ayuda Coberturas',
                                html:'<table width=430 ><tr><td align=left bgcolor="#98012e" style="color:white;font-size:11px;"><b>'+ayudaVoE+'</b></td></tr><tr><td style="font-size:11px; ">'+ayudaVoL+'</td></tr></table>',
                                width: 450,
                                height:350,
                                bodyStyle:'background:white',
                                overflow:'auto',
                                autoScroll:true, 
                                buttonAlign:'center',
                                //modal:true,
                                closable:false,
                                buttons: [{
                                   text:'Cerrar',
                                   handler: function() { 
                                   windowAyuda.close();
                                   //windowCoberturas.show(); 
                                   }
                                }]
                            });
                            
                            //var pocisionCobertura = windowCoberturas.getPosition();
                            //alert('pocisionCobertura :'+pocisionCobertura);
                            //alert('pocisionCobertura0 :'+pocisionCobertura[0]);
                            //alert('pocisionCobertura1 :'+pocisionCobertura[1]);
                            //windowCoberturas.hide();
                            //windowCoberturas.toBack();
                            windowAyuda.show();
                            //windowAyuda.toFront();
                            //windowAyuda.setPosition(pocisionCobertura[0]+200,pocisionCobertura[1]+30);

                         }else{
                            Ext.Msg.alert('Mensaje', 'No se encontraron datos');
                         }
                     }//else
        }//function
    });
    }
    
//******************Detalle Rol de la Póliza******************
function datosFnPoliza(cdRol, dsRol, dsNombre){
    var conn = new Ext.data.Connection();
    conn.request({
         url: 'procesoemision/datosRol.action'+'?cdUnieco=' + idAseguradora.getValue() + '&cdRamo=' + idProducto.getValue() + '&estado=' + idEstado.getValue() + '&nmPoliza=' + idPoliza.getValue() + '&cdRol=' + cdRol + '&dsRol=' + dsRol + '&dsNombre=' + dsNombre + '&nmSituac=' + idNmSituac.getValue(),
         method: 'POST',
         successProperty : '@success',
         //params : [idHoja],
         callback: function (options, success, response){
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         var codeExt = Ext.util.JSON.decode(response.responseText).dext;
                         
                         if(codeExt != ''){
                             var panelRol = new Ext.Panel({
                                    border: false,
                                    width: 600,
                                    autoHeight: true,
                                    items:[{
                                        //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la Poliza</span>',
                                        labelWidth: 250, 
                                        labelAlign: 'right',         
                                        layout: 'form',
                                        frame: true,
                                        items:codeExt
                                   }]
                              });
                             
                             var window = new Ext.Window({
                                title: 'Rol del Inciso',
                                width: 500,
                                //height:340,
                                autoHeight: true,
                                minWidth: 300,
                                minHeight: 200,
                                layout: 'fit',
                                plain:true,
                                modal:true,
                                bodyStyle:'padding:5px;',
                                buttonAlign:'center',
                                items: [panelRol],
                                buttons:[{
                                    text: 'Regresar',
                                    tooltip: 'Cierra la ventana',
                                    handler: function(){
                                        window.close();
                                    }//handler
                                }]
                            });
                            
                            window.show();
                         }
                         else{
                            Ext.Msg.alert('Mensaje', 'No se encontraron datos');
                         }
                      }//else
                   }//function
    });
}


//******************Rol de la Póliza******************
var storeGridFnPoliza = createFnPoliza();
var cdRol;
var dsNombre;
var dsRol;

var cmfp = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header:"Rol",  dataIndex:'dsFuncion', width:150, sortable:true},
        {header:"Nombre",   dataIndex:'dsNombre',  width:150, sortable:true},
        {header:"cdRol",    dataIndex:'cdRol',     width:150, sortable:true, hidden:true},
        {header:"nmSituac", dataIndex:'nmSituac',  width:150, sortable:true, hidden:true}
]);

var gridFnPoliza = new Ext.grid.GridPanel({
        store: storeGridFnPoliza,
        border: true,
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cmfp,
        buttons:[{
                    id:'detalle',
                    text:'Detalle',
                    tooltip:'Detalle'                           
                }],                                                     
        width: 656,
        frame: true,
        height: 200,        
        
    	sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                    rowselect: function(sm, row, rec){
                            cdRol = rec.get('cdRol');
                            dsNombre = rec.get('dsNombre');
                            dsRol = rec.get('dsFuncion');
                    }
                }//listeners
        }),
        viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store: storeGridFnPoliza,                                               
            displayInfo: true,
			displayMsg: 'Registros mostrados {0} - {1} de {2}',
			emptyMsg: 'No hay registros para mostrar',
			beforePageText: 'P&aacute;gina',
			afterPageText: 'de {0}'                      
            })                                                                                               
});

muestraMsgEligeRecordDeGrid('detalle', gridFnPoliza);

Ext.getCmp('detalle').on('click',function(){
	datosFnPoliza(cdRol, dsRol, dsNombre);
});

//******************DETALLE ACCESORIOS******************
function detalleAccesorios(tipo, monto, nmObjeto){

    var conn = new Ext.data.Connection();
    conn.request({
         url: 'procesoemision/datosAccesorios.action?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&nmSituac='+idNmSituac.getValue()+'&nmObjeto='+nmObjeto,
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         var codeExt = Ext.util.JSON.decode(response.responseText).dext;
                         if(codeExt != ''){
							var campoTipo = new Ext.form.TextField({
								name:'tipo',
								value:tipo,
								fieldLabel:'Tipo',
								disabled:true,
								width: 200
							});
	                     	var campoMonto = new Ext.form.TextField({
								name:'monto',
								value:monto,
								fieldLabel:'Monto',
								disabled:true,
								width: 200
	                     	});
                            var panelDetalleAccesoriosEnc = new Ext.FormPanel({
						            labelAlign:'right',
						            border: false,
						            width: 350,
						            autoHeight:true,
						            //title:'Encabezado',
                                    items:[
			                             	campoTipo,
			                              	campoMonto
			                        ]
                            });
                            var panelDetalleAccesorios = new Ext.FormPanel({
						            labelAlign:'right',
						            border: false,
						            width: 350,
						            autoHeight:true,
						            //title: 'Datos',
                                    items: codeExt
                            });
                             
                            var windowAccesorios = new Ext.Window({
								title: 'Detalle Equipo Especial',//Antes accesorios
								width: 350,
								autoHeight: true,
								layout: 'fit',
								plain:true,
								modal:true,
								bodyStyle:'padding:5px;',
								buttonAlign:'center',
								items: [panelDetalleAccesoriosEnc,panelDetalleAccesorios],
								buttons:[{
								    text: 'Regresar',
								    tooltip: 'Cierra la ventana',
								    handler: function(){
								        windowAccesorios.close();
								    }//handler
								}]
                            });
                            
                            windowAccesorios.show();
                         }else{
                            Ext.Msg.alert('Mensaje', 'No se encontraron datos');
                         }
                      }//else
                   }//function
    });
}


//******************ACCESORIOS******************
var storeAccesoriosGrid = createGridAccesorios();
var cma = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
    {header: "Tipo",  dataIndex:'dsObjeto', width: 305, sortable:true},
  	{header: "Monto", dataIndex:'ptObjeto', width: 305, sortable:true, renderer:Ext.util.Format.usMoney}
]);

var tipo;
var monto;
var nmObjeto;
var gridAccesorios = new Ext.grid.GridPanel({
    store:storeAccesoriosGrid,
    border:true,
    buttonAlign: 'center',
    baseCls:' background:white ',
    cm: cma,
	buttons:[{
			id:'AccesoriosDetalles',
			text:'Detalles',
           	tooltip:'Detalles'
    }],                                 
    width:656,
    frame:true,
    height:200,
    
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
					rowselect: function(sm, row, rec) { 
                    	//selectedId = storeAccesoriosGrid.data.items[row].id; 
                    	//tipo = rec.get('label');
                    	//monto = rec.get('value');
                    	tipo = rec.get('dsObjeto');
                    	monto = rec.get('ptObjeto');
                    	nmObjeto = rec.get('nmObjeto');
                    }
                   
        }
    }),
    //viewConfig: {autoFill: true,forceFit:true}, 
    bbar: new Ext.PagingToolbar({
        pageSize:20,
        store: storeAccesoriosGrid,                                               
        displayInfo: true,
		displayMsg: 'Registros mostrados {0} - {1} de {2}',
		emptyMsg: 'No hay registros para mostrar',
		beforePageText: 'P&aacute;gina',
		afterPageText: 'de {0}'                       
    })
});




muestraMsgEligeRecordDeGrid('AccesoriosDetalles', gridAccesorios);

Ext.getCmp('AccesoriosDetalles').on('click',function(){

	detalleAccesorios(tipo, monto, nmObjeto);
	//gridAccesorios.getSelectionModel().clearSelections();
});

//******************DETALLE AGRUPADOR******************
function detalleAgrupador(agrupadorNombre,agrupadorBanco,agrupadorDomicilio,agrupadorForpag,agrupadorSuc,agrupadorTipotarj,agrupadorCuenta,agrupadorFecha,agrupadorDigver,DatosTarjeta){

	var Nombre = new Ext.form.TextField({
		name:'nombre',
		value:agrupadorNombre,
		fieldLabel:'Pagado Por',
		labelSeparator: ':',
		disabled:true,
		width: 200
	});
 	var Banco = new Ext.form.TextField({
		name:'banco',
		value:agrupadorBanco,
		fieldLabel:'Banco',
		labelSeparator: ':',
		disabled:true,
		width: 200
 	});
 	var Domicilio = new Ext.form.TextField({
		name:'domicilio',
		value:agrupadorDomicilio,
		fieldLabel:'Domicilio',
		labelSeparator: ':',
		disabled:true,
		width: 200
 	});
 	var Pago = new Ext.form.TextField({
		name:'pago',
		value:agrupadorForpag,
		fieldLabel:'Instrumento de pago',
		labelSeparator: ':',
		disabled:true,
		width: 200
 	});
 	var Sucursal = new Ext.form.TextField({
		name:'sucursal',
		value:agrupadorSuc,
		fieldLabel:'Sucursal',
		labelSeparator: ':',
		disabled:true,
		width: 200
 	});
 	var Tarjeta = new Ext.form.TextField({
		name:'tarjeta',
		value:agrupadorTipotarj,
		fieldLabel:'Tipo tarjeta',
		labelSeparator: ':',
		disabled:true,
		width: 200
 	});
 	var Cuenta = new Ext.form.TextField({
		name:'cuenta',
		value:agrupadorCuenta,
		fieldLabel:'Número de tarjeta',
		labelSeparator: ':',
		disabled:true,
		width: 200
 	});
 	var Fecha = new Ext.form.TextField({
		name:'fecha',
		value:agrupadorFecha,
		fieldLabel:'Fecha de vencimiento',
		labelSeparator: ':',
		disabled:true,
		width: 200
 	});
 	var Digito = new Ext.form.TextField({
		name:'digito',
		value:agrupadorDigver,
		fieldLabel:'Dígito verificador',
		labelSeparator: ':',
		disabled:true,
		width: 200
 	});
    var panelDetalleAgrupador = new Ext.FormPanel({
            id:'formAgrupador',
            labelAlign:'right',
            border: false,
            width: 450,
            autoHeight:true,
            labelWidth:150, 
            items:[
                    Nombre,
                    Domicilio,
                    Pago,
                {
                layout:'form',
                id:'id-form-pago-hide',
                hidden:true,
                border:true,
                items : [
                    Banco,
                    Sucursal,
                    Tarjeta,
                    Cuenta,
                    Fecha,
                    Digito
                    ]
                }
			]
    });
     
    var windowAgrupador = new Ext.Window({
		title: 'Detalles Datos de Cobro',
		width: 460,
		autoHeight: true,
		layout: 'fit',
		plain:true,
		modal:true,
		bodyStyle:'padding:5px;',
		buttonAlign:'center',
		items: [panelDetalleAgrupador],
		buttons:[{
		    text: 'Regresar',
		    tooltip: 'Cierra la ventana',
		    handler: function(){
		        windowAgrupador.close();
		    }//handler
		}]
    });
    
    if(DatosTarjeta=='S'){
        Ext.getCmp('id-form-pago-hide').show();
    }else{
        Ext.getCmp('id-form-pago-hide').hide();
    }
    
    windowAgrupador.show();
}


//******************AGRUPADOR******************
var storeGridAgrupadores = createGridAgrupadores();
var agrupadorNombre;
var agrupadorBanco;
var agrupadorDomicilio;
var agrupadorForpag;
var agrupadorSuc;
var agrupadorTipotarj;
var agrupadorCuenta;
var agrupadorFecha;
var agrupadorDigver;

var cmag = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header: "Nombre",     dataIndex:'dsNombre',    width:150,  sortable:true},
        {header: "Banco",      dataIndex:'dsBanco',     width:200,  sortable:true},
        {header: "Instrumento de Pago",  dataIndex:'dsForpag',      width:250, sortable:true},
        {header: "Domicilio",  dataIndex:'dsDomicilio', width:400,  sortable:true},
        {header: "dsForpag",   dataIndex:'dsForpag',    hidden: true},
        {header: "dsSucursal", dataIndex:'dsSucursal',  hidden: true},
        {header: "dsTipotarj", dataIndex:'dsTipotarj',  hidden: true},
        {header: "nmcuenta",   dataIndex:'nmcuenta',    hidden: true},
        {header: "fechaUltreg",dataIndex:'fechaUltreg', hidden: true},
        {header: "nmDigver",   dataIndex:'nmDigver',    hidden: true},
        {header: "muestraCampos",   dataIndex:'muestraCampos',    hidden: true}
]);

var gridAgrupadores = new Ext.grid.GridPanel({
        store: storeGridAgrupadores,
        border: true,
        //collapsible: true, 
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cmag,
        buttons:[{
                    id:'agrupadorDetalle',
                    text:'Detalle',
                    tooltip:'Detalle'                           
                }],                                                     
        width:656,
        frame:true,
        height:135,      
        //title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la Poliza</span>',
        
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                    rowselect: function(sm, row, rec){ 
                            //selectedId = store.data.items[row].id;
							agrupadorNombre    = rec.get('dsNombre');
							agrupadorBanco     = rec.get('dsBanco');
							agrupadorDomicilio = rec.get('dsDomicilio');
							agrupadorForpag    = rec.get('dsForpag');
							agrupadorSuc       = rec.get('dsSucursal');
							agrupadorTipotarj  = rec.get('dsTipotarj');
							agrupadorCuenta    = rec.get('nmcuenta');
							agrupadorFecha     = rec.get('fechaUltreg');
							agrupadorDigver    = rec.get('nmDigver');
							DatosTarjeta       = rec.get('muestraCampos');
							
                    }
                }//listeners
        
        }),
        //viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store: storeGridAgrupadores,                                               
            displayInfo: true,
			displayMsg: 'Registros mostrados {0} - {1} de {2}',
			emptyMsg: 'No hay registros para mostrar',
			beforePageText: 'P&aacute;gina',
			afterPageText: 'de {0}'
            })
});

muestraMsgEligeRecordDeGrid('agrupadorDetalle', gridAgrupadores);

Ext.getCmp('agrupadorDetalle').on('click',function(){
	detalleAgrupador(agrupadorNombre,agrupadorBanco,agrupadorDomicilio,agrupadorForpag,agrupadorSuc,agrupadorTipotarj,agrupadorCuenta,agrupadorFecha,agrupadorDigver,DatosTarjeta);
});

//******************PANEL DATOS******************
var datosPanel = new Ext.Panel({
    border: false,
    width: 668,
    autoHeight: true,
    autoScroll: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Datos</span>',
        layout:'form',
        collapsible:true,
        frame:true,
        items:[datosIncisosForm]
    }]
});


//******************PANEL GRID COBERTURA******************gridFnPoliza
var coberturaPanelGrid = new Ext.Panel({
    border: false,
    width: 668,
    autoHeight: true,
    autoScroll: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Coberturas</span>',
        layout:'form',
        collapsible:true,
        frame:true,
        items:[gridCoberturas]
    }]
});


//******************PANEL GRID ROL DE LA POLIZA******************
var funcionPolizaPanelGrid = new Ext.Panel({
    border: false,
    width: 668,
    autoHeight: true,
    autoScroll: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol del Riesgo</span>',
        layout:'form',
        collapsible:true,
        frame:true,
        items:[gridFnPoliza]
    }]
});


//******************PANEL GRID ACCESORIOS******************
var accesoriosPanelGrid = new Ext.Panel({
    border: false,
    width: 668,
    autoHeight: true,
    autoScroll: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Equipo especial</span>',
        layout:'form',
        collapsible:true,
        frame:true,
        items:[gridAccesorios]
   }]
});


//******************PANEL GRID AGRUPADORES******************
var agrupadoresPanelGrid = new Ext.Panel({
    border: false,
    width: 668,
    autoHeight: true,
    autoScroll: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Datos de Cobro</span>',
        layout:'form',
        collapsible:true,
        frame:true,
        items:[gridAgrupadores]
   }]
});


//******************PANEL GRID MAIN******************
var mainPanelGrid = new Ext.Panel({
    border: false,
    width: 680,
    autoHeight: true,
    items:[{
        title:'<span style="color:black;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Riesgo</span>',
        layout:'form',
        //collapsible: true,
        frame: true,
        buttonAlign:'center',
        items:[opcionesForm,datosPanel,coberturaPanelGrid,funcionPolizaPanelGrid,agrupadoresPanelGrid,accesoriosPanelGrid],
        buttons:[{
				text:'Regresar',
            	tooltip:'Regresar a detalles poliza',
            	handler: function(){
            		
            		
            		var volverA="";
	                
	                if(_VOLVER1){
	                	volverA="&volver1=true"
	                }else if(_VOLVER2){
		                volverA="&volver2=true";
		            }else if(_VOLVER3){
		                volverA="&volver3=true";
		            }else if(_VOLVER4){
                        volverA="&volver4=true";
                	}
            		
                    window.location.replace('detallePoliza.action?cdUnieco='+idAseguradora.getValue()+'&cdRamo='+idProducto.getValue()+'&estado='+idEstado.getValue()+'&nmPoliza='+idPoliza.getValue()+'&poliza='+numPoliza.getValue()+'&producto='+nombreProducto.getValue()+'&aseguradora='+nombreAseguradora.getValue()+volverA);
                }
        }]
   }]
});
mainPanelGrid.render('pantallaInciso');

	//Función que crea un texto de columna/fila como hiperlink
	function crearHLink (value, metadata, record, rowIndex, colIndex, store) {
			return '<span style="color:blue;font-size:11px; text-decoration:underline; font-family:Arial,Helvetica,sans-serif;">' + value + '</span>';
	}
});