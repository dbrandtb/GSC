<script type="text/javascript">

Ext.onReady(function(){ 

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';

var ElementosExt = <s:component template="camposPantallaDatosExt.vm" templateDir="templates" theme="components" ><s:param name="dext" value="dext" /></s:component>;


//Variable tipo hidden para evitar la excepcion de la pantalla
//si el pl no trae datos.
var sinDatos = new Ext.form.Hidden({
    id:'sinDatos',
    name:'SinDatos'
});


//Si el Pl no trae datos se mostrara un mensaje y se pintara un hidden.
if (ElementosExt==""){
/*  Ext.Msg.show({
       title:'Datos Rol',
       msg: 'No se encontraron datos.',
       buttons: Ext.Msg.OK,
       icon: Ext.MessageBox.INFO
    });*/   
    ElementosExt = sinDatos;
}


//******Encabezado******
var cdUnieco = new Ext.form.TextField({
    fieldLabel: '',
    allowBlank: true,
    name: 'cdUnieco',
    //value: '1',
    value:'<s:property value="cdUnieco" />',
    hidden: true
});

var cdRamo = new Ext.form.TextField({
    fieldLabel: '',
    allowBlank: true,
    name: 'cdRamo',
    //value: '1', 
    value:'<s:property value="cdRamo" />',
    hidden: true
});

var nmPoliza = new Ext.form.TextField({
    fieldLabel: '',
    allowBlank: true,
    name: 'nmPoliza',
    //value: '26', 
    value:'<s:property value="nmPoliza" />',
    hidden: true
});

var estado = new Ext.form.TextField({
    fieldLabel: '',
    allowBlank: true,
    name: 'estado',
    //value: 'M', 
    value:'<s:property value="estado" />',
    hidden: true
});
//******** DATOS DE POLIZA **********
var poliza = new Ext.form.TextField({
    fieldLabel: 'Póliza',
    allowBlank: true,
    name: 'poliza',
    width: 150,
    disabled: true,
    value:'<s:property value="poliza" />',
    frame: true 
});

var aseguradora = new Ext.form.TextField({
    fieldLabel: 'Aseguradora',
    allowBlank: true,
    name: 'aseguradora',
    hideLabel: false,
    width: 170,
    disabled: true,
    value:'<s:property value="aseguradora" />',
    frame: true 
});

var producto = new Ext.form.TextField({
    fieldLabel: 'Producto',
    allowBlank: true,
    name: 'producto',
    hideLabel: false,
    width: 270,
    disabled: true,
    value:'<s:property value="producto" />',
    frame: true 
});

//******** DATOS GENERALES DE POLIZA ***********
var vigenciaDesde = new Ext.form.TextField({
    fieldLabel: 'Vigencia Desde',
    allowBlank: true,
    name: 'vigenciaDesde',
    width: 150,
    disabled: true,
    frame: true,
    id: 'vigenciaDesde' 
});

var fechaEfectividad = new Ext.form.TextField({
    fieldLabel: 'Fecha Efectividad',
    allowBlank: true,
    name: 'fechaEfectividad',
    width: 150,
    disabled: true,
    frame: true,
    id: 'fechaEfectividad'
});

/*
var numeroEndoso = new Ext.form.TextField({
    fieldLabel: 'Número Endoso',
    allowBlank: true,
    name: 'numeroEndoso',
    width: 100,
    disabled: true,
    frame: true 
});*/

var vigenciaHasta = new Ext.form.TextField({
    fieldLabel: 'Hasta',
    allowBlank: true,
    name: 'vigenciaHasta',
    width: 150,
    disabled: true,
    frame: true,
    id: 'vigenciaHasta'
});

var fechaRenovacion = new Ext.form.TextField({
    fieldLabel: 'Hora Efecto',
    allowBlank: true,
    name: 'fechaRenovacion',
    width: 150,
    id: 'fechaRenovacion',
    disabled: true,
    frame: true
});

var moneda = new Ext.form.TextField({
    fieldLabel: 'Moneda',
    allowBlank: true,
    name: 'moneda',
    width: 150,
    disabled: true,
    frame: true,
    id: 'moneda'
});

/*
var tipoCoaseguro = new Ext.form.TextField({
    fieldLabel: 'Tipo de Coaseguro',
    allowBlank: true,
    name: 'tipoCoaseguro',
    width: 100,
    disabled: true,
    frame: true 
});

var tipoPoliza = new Ext.form.TextField({
    fieldLabel: 'Tipo de Poliza',
    allowBlank: true,
    name: 'tipoPoliza',
    width: 100,
    disabled: true,
    frame: true 
});*/

var formaPago = new Ext.form.TextField({
    fieldLabel: 'Periocidad',
    allowBlank: true,
    name: 'formaPago',
    width: 150,
    disabled: true,
    frame: true,
    id: 'formaPago'
});

var dataGeneralesPoliza = new Ext.Panel({
    border: false,
    width: 776,
    //labelAlign: 'right', 
    //autoHeight: true,
    //frame: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Datos Generales de Póliza</span>',
        //bodyStyle:'background: white',
        labelWidth: 100,     
        labelAlign: 'right',        
        layout: 'form',
        width: 774,
        //defaults:{anchor: '100%'},
        collapsible: true,
        frame: true,
        //bodyStyle:'padding:5px 5px 0',
        items:[{
                layout: 'column',
                border: false,
                items: [{
                    columnWidth:.4,
                    layout: 'form',
                    border: false,
                    items: [vigenciaDesde, 
                           fechaEfectividad, 
                           formaPago]
                },{
                    columnWidth:.5,
                    layout: 'form',
                    border: false,
                    labelWidth: 130,
                    items: [vigenciaHasta, 
                            fechaRenovacion, 
                            moneda]
                }/*,{
                    columnWidth:.4,
                    layout: 'form',
                    border: false,
                    labelWidth: 110,
                    items: [tipoPoliza, tipoCoaseguro, formaPago]
                }*/]
        }]
    }]
});//dataGeneralesPoliza

var dataAdicionalesPoliza = new Ext.Panel({
    border: false,
    width: 776,
    //height: 1500,
    //autoHeight: true,
    //frame: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Datos Adicionales de Póliza</span>',
        //bodyStyle:'background: white',
        labelWidth: 100,             
        layout: 'form',
        width: 774,
        defaults:{anchor: '55%'},
        //autoHeight: true,
        collapsible: true,
        frame: true,
        labelWidth: 250,
        labelAlign: 'right',
        items:  ElementosExt
/*        <s:component template="builderItemsAdicionalesPoliza.vm" templateDir="templates" theme="components">
              <s:param name="items" value="itemsPantalla" />                
        </s:component>*/
    }]
});

var storeDataAdicionales = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'procesoemision/consultaDatosAdicionalesPoliza.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&estado='+estado.getValue()+'&nmPoliza='+nmPoliza.getValue()
    }),
    reader: new Ext.data.JsonReader({
    root: 'datAdicionales'
    //id: 'value'
    },[
       {name: 'dsNombre',   type: 'string', mapping:'nombreComponente'},
       {name: 'dsEtiqueta', type: 'string', mapping:'etiqueta'},
       {name: 'otValor',    type: 'string', mapping:'valor'},
       {name: 'dsValor',    type: 'string', mapping:'descripcion'}
     ]),
    remoteSort: true
});
/*
storeDataAdicionales.on('load', function(){
    for(var index = 0; index < storeDataAdicionales.getCount(); index++){
        var record = storeDataAdicionales.getAt(index);
        
        //alert(record.get('dsNombre').substring(11, record.get('dsNombre').length));
        if(record.get('dsValor') != ""){
            Ext.getCmp(record.get('dsNombre').substring(11, record.get('dsNombre').length)).setValue(record.get('dsValor'));
         }
         else{
            Ext.getCmp(record.get('dsNombre').substring(11, record.get('dsNombre').length)).setValue(record.get('otValor'));
         }
    }//for
});
*/
storeDataAdicionales.load();

var store = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'procesoemision/consultaDetallePoliza.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&estado='+estado.getValue()+'&nmPoliza='+nmPoliza.getValue()
    }),
    reader: new Ext.data.JsonReader({
    root: 'polizaDet'
    //id: 'value'
    },[
       {name: 'vigenciaHasta',      type: 'string', mapping:'vigenciaHasta'},
       {name: 'vigenciaDesde',      type: 'string', mapping:'vigenciaDesde'},
       {name: 'fechaEfectividad',   type: 'string', mapping:'fechaEfectividad'},
       {name: 'moneda',             type: 'string', mapping:'moneda'},
       {name: 'formaPago',          type: 'string', mapping:'periocidad'},
       {name: 'fechaRenovacion',    type: 'string', mapping:'fechaRenovacion'}
     ]),
    remoteSort: true
});
//store.setDefaultSort('value', 'desc');

store.load({
     callback : function(r, options, success) {
                      if (success) {
	                      	if(r.length>0){
	                          		var record = r[0];
	                          		
								    Ext.getCmp('vigenciaDesde').setValue(record.get('vigenciaDesde'));
								    Ext.getCmp('vigenciaHasta').setValue(record.get('vigenciaHasta'));
								    Ext.getCmp('fechaEfectividad').setValue(record.get('fechaEfectividad'));
								    Ext.getCmp('moneda').setValue(record.get('moneda'));
								    Ext.getCmp('formaPago').setValue(record.get('formaPago'));
								    Ext.getCmp('fechaRenovacion').setValue(record.get('fechaRenovacion'));
							}else{
								Ext.Msg.alert('Aviso','No se encuentran datos para el detalle de la P&oacute;liza');
							}
						}
					}
 });


//*****Funcion que al dar click en el boton del GridPanel muestra un mensaje si no se seleccionó un renglon*****
// @param idBtn id del boton del GridPanel
// @param grid  elemento de tipo Ext.grid.GridPanel (tal como fue declarada la variable)
function muestraMsgEligeRecordDeGrid(idBtn, grid) {
	Ext.getCmp(idBtn).on('click',function(){
		if( !grid.getSelectionModel().hasSelection() ) {
			Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED);
		}
	});
}


//******** GRID OBJETO ASEGURABLE *************
var storeGridObjAsegurable = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'procesoemision/consultaObjetoAsegurable.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&estado='+estado.getValue()+'&nmPoliza='+nmPoliza.getValue()//+'&nmSituac=9'
    }),
    reader: new Ext.data.JsonReader({
    root: 'objAsegurable'
    },[
       {name: 'cdInciso', type: 'string', mapping:'inciso'},
       {name: 'nmsituac', type: 'string', mapping:'nmsituac'},
       {name: 'status',   type: 'string', mapping:'status'},
       {name: 'dsDescripcion', type: 'string', mapping:'descripcion'},
       {name: 'cdcia',    type: 'string', mapping:'cdcia'}
     ]),
    remoteSort: true
});

storeGridObjAsegurable.load();

var cm = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header: "nmsituac",     dataIndex:'nmsituac',      width:20,   hidden: true},
        {header: "status",       dataIndex:'status',        width:20,   hidden: true},
        {header: "cdcia",        dataIndex:'cdcia',         width:20,   hidden: true},
        {header: "Inciso",       dataIndex:'cdInciso',      width:20,   sortable:true},
        {header: "Descripción",  dataIndex:'dsDescripcion', width:100,  sortable:true}
]);

var idNmSituac;
var idInciso;
var dsInciso;
var idStatus;
var cdCia;

var gridObjAsegurable = new Ext.grid.GridPanel({
        store: storeGridObjAsegurable,
        border: true,
        //collapsible: true, 
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cm,
        buttons:[{
                    id:'incisoDetalle',
                    text:'Detalle',
                    tooltip:'Detalle'                           
                }],                                                     
        width: 764,
        //frame: true,
        height: 200,        
        //title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Objeto Asegurable</span>',
        //renderTo:document.body,
        
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
                    rowselect: function(sm, row, rec) {
                            //selectedId = store.data.items[row].id;
                            idNmSituac = rec.get('nmsituac');
                            idInciso = rec.get('cdInciso');
                            dsInciso = rec.get('dsDescripcion');
                            idStatus = rec.get('status');
                            cdCia    = rec.get('cdcia');
                            Ext.getCmp('incisoDetalle').on('click',function(){    
	                                 
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
	                               
	                            window.location.replace('datosIncisos.action?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&estado='+estado.getValue()+'&nmPoliza='+nmPoliza.getValue()+'&nmSituac='+idNmSituac+'&cdInciso='+idInciso+'&cdCia='+cdCia+'&dsDescripcion='+dsInciso+'&aseguradora='+aseguradora.getValue()+'&poliza='+poliza.getValue()+'&producto='+producto.getValue()+'&status='+idStatus+volverA);                                                                       
                            });
                    }
                }//listeners
        
        }),
        viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store: storeGridObjAsegurable,
            displayInfo: true,
            displayMsg: 'Registros mostrados {0} - {1} de {2}',
            emptyMsg: 'No hay registros para mostrar',
    		beforePageText: 'P&aacute;gina',
    		afterPageText: 'de {0}'
            })
        });

muestraMsgEligeRecordDeGrid('incisoDetalle', gridObjAsegurable);

        
var panelObjAsegurable = new Ext.Panel({
    border: false,
    width: 774,
    //autoHeight: true,
    //frame: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Riesgo</span>',
        //bodyStyle:'background: white',
        labelWidth: 100,             
        layout: 'form',
        //autoWidth: true,
        //width: 850,
        //defaults:{anchor: '100%'},
        collapsible: true,
        frame: true,
        //bodyStyle:'padding:5px 5px 0',
        items:[gridObjAsegurable]
   }]
});
        
//******** DATOS ROL ***********
function datosRol(cdRol, dsRol, dsNombre, nmSituac){
    var conn = new Ext.data.Connection();
    conn.request({
         url: 'procesoemision/datosRol.action'+'?cdUnieco=' + cdUnieco.getValue() + '&cdRamo=' + cdRamo.getValue() + '&estado=' + estado.getValue() + '&nmPoliza=' + nmPoliza.getValue() + '&cdRol=' + cdRol + '&dsRol=' + dsRol + '&dsNombre=' + dsNombre + '&nmSituac='+nmSituac,
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
                                        //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol en la Poliza</span>',
                                        labelWidth: 250, 
                                        labelAlign: 'right',         
                                        layout: 'form',
                                        frame: true,
                                        items:codeExt
                                   }]
                              });
                             
                             var window = new Ext.Window({
                                title: 'Rol en Póliza',
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
        
//******** GRID FUNCION EN LA POLIZA *************
var storeGridFnPoliza = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        method: 'POST', 
        url: 'procesoemision/consultaFuncionPoliza.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&estado='+estado.getValue()+'&nmPoliza='+nmPoliza.getValue()+'&nmSituac=0'
    }),
    reader: new Ext.data.JsonReader({
    root: 'fnPoliza'
    },[
       {name: 'dsFuncion', type: 'string', mapping:'rol'},
       {name: 'dsNombre', type: 'string', mapping:'nombre'},
       {name: 'cdRol', type: 'string', mapping:'cdRol'},
       {name: 'nmSituac', type: 'string', mapping:'nmSituac'}
     ]),
    remoteSort: true
});

storeGridFnPoliza.load();

var cm = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {header: "Rol",    dataIndex:'dsFuncion',   width:150,  sortable:true},
        {header: "Nombre",     dataIndex:'dsNombre',    width:150,  sortable:true},
        {header: "cdRol",      dataIndex:'cdRol',       width:150,  sortable:true, hidden: true},
        {header: "nmSituac",   dataIndex:'nmSituac',    width:150,  sortable:true, hidden: true}
]);

var gridFnPoliza = new Ext.grid.GridPanel({
        store: storeGridFnPoliza,
        border: true,
        //collapsible: true, 
        buttonAlign: 'center',
        baseCls:' background:white ',
        cm: cm,
        buttons:[{
                    id:'detalle',
                    text:'Detalle',
                    tooltip:'Detalle'                           
                }],                                                     
        width: 764,
        //autoWidth: true,
        frame: true,
        height: 200,        
        //title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la Póliza</span>',
        
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                    rowselect: function(sm, row, rec){ 
                            //selectedId = store.data.items[row].id;                                                                                                                                    
                            Ext.getCmp('detalle').on('click',function(){
                                var nmSituac = rec.get('nmSituac');
                                var cdRol = rec.get('cdRol');
                                var dsNombre = rec.get('dsNombre');
                                var dsRol = rec.get('dsFuncion');
                                //alert('cdRol:: ' + cdRol + ' nmSituac:: ' + nmSituac + ' dsNombre:: ' + dsNombre + ' dsRol:: ' + dsRol);
                                //window.location.replace('datosRol.action' + '?cdUnieco=' + cdUnieco.getValue() + '&cdRamo=' + cdRamo.getValue() + '&estado=' + estado.getValue() + '&nmPoliza=' + nmPoliza.getValue() + '&cdRol=' + cdRol + '&dsRol=' + dsRol + '&dsNombre=' + dsNombre + '&nmSituac=' + nmSituac + '&aseguradora=' + aseguradora.getValue() + '&poliza=' + poliza.getValue() + '&producto=' + producto.getValue());
                                datosRol(cdRol, dsRol, dsNombre, nmSituac);                                                                            
                            });
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
        
var panelFnPoliza = new Ext.Panel({
    border: false,
    width: 774,
    //autoHeight: true,
    //frame: true,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la Póliza</span>',
        //bodyStyle:'background: white',
        labelWidth: 100,             
        layout: 'form',
        collapsible: true,
        frame: true,
        //bodyStyle:'padding:5px 5px 0',
        items:[gridFnPoliza]
   }]
});

//************ DETALLE RECIBOS ***************
function createDetalleRecibos(nmRecibo){   

 
    if( _POLIZA_CANCELADAS == 1  ){
               // si la poliza proviene de la pantalla de polizas canceladas  se ejecuta este action
           url='procesoemision/polizasCanceladasRecibosDetalle.action?cdUnieco='+cdUnieco.getValue()+'&estado='+estado.getValue()+'&cdRamo='+cdRamo.getValue()+'&nmPoliza='+nmPoliza.getValue()+'&nmRecibo='+nmRecibo;
    }else{
          url='procesoemision/recibosDetalle.action?cdUnieco='+cdUnieco.getValue()+'&estado='+estado.getValue()+'&cdRamo='+cdRamo.getValue()+'&nmPoliza='+nmPoliza.getValue()+'&nmRecibo='+nmRecibo;
    }
    
    storeRecibosDetalle = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
    url: url
    }),
    reader: new Ext.data.JsonReader({
    root:'listRecibos',
    id:'recibosDetalleStore',
    totalProperty: 'totalCount'             
    },[
        {name: 'cdTipoCon',  type: 'string',  mapping:'cdTipoCon'},
        {name: 'dsTipoCon',  type: 'string',  mapping:'dsTipoCon'},
        {name: 'importeCon', type: 'string',  mapping:'importeCon'}
    ]),
    remoteSort: true
    });
    storeRecibosDetalle.setDefaultSort('opcionesStore', 'desc');
    storeRecibosDetalle.load();

//Columnas Grid Recibos
    function toggleDetails(btn, pressed){
    var view = grid.getView();
    view.showPreview = pressed;
    view.refresh();
    }

    var cmRecibosDetalle = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        {header: "Concepto", dataIndex:'cdTipoCon', width: 130, sortable:true},
        {header: "Descripción",    dataIndex:'dsTipoCon', width: 180, sortable:true},
        {header: "Importe",    dataIndex:'importeCon', width: 120, sortable:true}
    ]);
    
    var recibosGrid= new Ext.grid.GridPanel({
    store:storeRecibosDetalle,
    border:true,
    buttonAlign: 'center',
    collapsible: true, 
    baseCls:' background:white ',
    cm: cmRecibosDetalle,
    /*buttons:[{
                text:'Regresar',
                tooltip:'Regresar'
            }],*/                                                        
    width:450,
    frame:true,
    height:160,
    bbar: new Ext.PagingToolbar({
        pageSize:20,
        store: storeRecibosDetalle,
        displayInfo: true,
        displayMsg: 'Registros mostrados {0} - {1} de {2}',
        emptyMsg: 'No hay registros para mostrar',
		beforePageText: 'P&aacute;gina',
		afterPageText: 'de {0}' 
        })
    });
    
    var window = new Ext.Window({
        title: 'Detalle del Recibo ' + nmRecibo,
        width: 500,
        autoHeight: true,
        minWidth: 500,
        minHeight: 160,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: [recibosGrid],
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

//************ RECIBOS ***************
function createOptionGrid(){                                       
        url='procesoemision/recibos.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&nmPoliza='+nmPoliza.getValue();
        storeRecibos = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
        url: url
        }),
        reader: new Ext.data.JsonReader({
        root:'listRecibos',
        id:'opcionesStore',
        totalProperty: 'totalCount'             
        },[
            {name: 'nmreciboexterno',  type: 'string',    mapping:'nmreciboexterno'},
            {name: 'nmrecibo',  	   type: 'string',    mapping:'nmrecibo'},
            {name: 'feinicio',         type: 'string',    mapping:'feinicio'},
            {name: 'fefinal',          type: 'string',    mapping:'fefinal'},
            {name: 'dsestado',         type: 'string',    mapping:'dsestado'},
            {name: 'dsTipoRecibo',     type: 'string',    mapping:'dsTipoRecibo'},
            {name: 'ptimport',         type: 'string',    mapping:'ptimport'}
        ]),
        remoteSort: true
    });
    storeRecibos.setDefaultSort('opcionesStore', 'desc');
    storeRecibos.load();
    return storeRecibos;
}


//Columnas Grid Recibos
function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
        }

        var cmRecibos = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {header: "No. Recibo",      dataIndex:'nmreciboexterno',     width: 120, sortable:true},
            {header: "nmrecibo",        dataIndex:'nmrecibo',     width: 120, sortable:true, hidden:true},
            {header: "Inicio Vigencia", dataIndex:'feinicio',     width: 120, sortable:true},
            {header: "Fin Vigencia",    dataIndex:'fefinal',      width: 120, sortable:true},
            {header: "Estado",          dataIndex:'dsestado',     width: 120, sortable:true},
            {header: "Tipo Recibo",     dataIndex:'dsTipoRecibo', width: 120, sortable:true},
            {header: "Importe",         dataIndex:'ptimport',     width: 120, sortable:true, renderer:Ext.util.Format.usMoney}
        ]);


//Crear Grid Recibos
//function gridRecibos(){
var gridnmRecibo;

var recibosGrid= new Ext.grid.GridPanel({
    store:createOptionGrid(),
    border:true,
    buttonAlign: 'center',
    collapsible: true, 
    baseCls:' background:white ',
    cm: cmRecibos,
    buttons:[{
                id:'recibosGridDetalle',
                text:'Detalle',
                tooltip:'Detalle'
            }],                                                        
    width:764,
    frame:true,
    height:320,
    
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                    rowselect: function(sm, row, rec){
                                gridnmRecibo = rec.get('nmrecibo');
                    }
                }//listeners

    }),
    //title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',      
    //viewConfig: {autoFill: true,forceFit:true},                
    bbar: new Ext.PagingToolbar({
        pageSize:20,
        store: storeRecibos,                                               
        displayInfo: true,
        displayMsg: 'Registros mostrados {0} - {1} de {2}',
        emptyMsg: 'No hay registros para mostrar',
		beforePageText: 'P&aacute;gina',
		afterPageText: 'de {0}'
        })                                                                                               
    });

	Ext.getCmp('recibosGridDetalle').on('click',function(){
	      
	   
	
    	if( !recibosGrid.getSelectionModel().hasSelection() ) {
			Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED);
		} else {
        	createDetalleRecibos(gridnmRecibo);
        }
    });     
    //recibosGrid.render('gridRecibos');
//}
//gridRecibos();

var panelRecibos = new Ext.Panel({
    border: false,
    width: 774,
    items:[{
        title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Recibo</span>',
        labelWidth: 100,             
        layout: 'form',
        collapsible: true,
        frame: true,
        items:[recibosGrid]
   }]
});


//******************DETALLE AGRUPADOR******************
function detalleAgrupador(agrupadorNombre,agrupadorBanco,agrupadorDomicilio,agrupadorForpag,agrupadorSuc,agrupadorTipotarj,agrupadorCuenta,agrupadorFecha,agrupadorDigver,DatosTarjeta){

    var Nombre = new Ext.form.TextField({
        name:'nombre',
        value:agrupadorNombre,
        fieldLabel:'Pagado Por',
        labelSeparator: ':',
        disabled:true,
        width: 300
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
        width: 350
    });
    var Pago = new Ext.form.TextField({
        name:'pago',
        value:agrupadorForpag,
        fieldLabel:'Instrumento de pago',
        labelSeparator: ':',
        disabled:true,
        width: 300
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
            width: 540,
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
        width: 550,
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
				if(_VOLVER1){
                    	window.location=_ACTION_IR_POLIZAS_CANCELADAS + "?idRegresar=S";
                    	//window.close();
                   }else{
                   
                       if(_VOLVER2){
                         window.location=_ACTION_IR_POLIZAS_A_RENOVAR + "?idRegresar=S";
                       }
                   }
                windowAgrupador.close();
        }
        }]//handler
        
    });
    
    if(DatosTarjeta=='S'){
        Ext.getCmp('id-form-pago-hide').show();
    }else{
        Ext.getCmp('id-form-pago-hide').hide();
    }
    
    windowAgrupador.show();
}


//******************AGRUPADOR******************
function createGridAgrupadores(){
    var storeGridAgrupadores = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            method: 'POST', 
            url: 'procesoemision/consultaAgrupador.action'+'?cdUnieco='+cdUnieco.getValue()+'&cdRamo='+cdRamo.getValue()+'&estado='+estado.getValue()+'&nmPoliza='+nmPoliza.getValue()
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
var DatosTarjeta;

var cmag = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),

        {header: "Nombre",          dataIndex:'dsNombre',    width:150,         sortable:true},
        {header: "Banco",           dataIndex:'dsBanco',     width:200,         sortable:true},
        {header: "Instrumento de Pago",  dataIndex:'dsForpag',    width:250,    sortable:true},
        {header: "Domicilio",       dataIndex:'dsDomicilio', width:400,         sortable:true},
        //{header: "dsForpag",        dataIndex:'dsForpag',    hidden: true},
        {header: "dsSucursal",      dataIndex:'dsSucursal',  hidden: true},
        {header: "dsTipotarj",      dataIndex:'dsTipotarj',  hidden: true},
        {header: "nmcuenta",        dataIndex:'nmcuenta',    hidden: true},
        {header: "fechaUltreg",     dataIndex:'fechaUltreg', hidden: true},
        {header: "nmDigver",        dataIndex:'nmDigver',    hidden: true},
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
        width:764,
        frame:true,
        height:135,      
        //title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol en la Poliza</span>',
        
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                    rowselect: function(sm, row, rec){ 
                            //selectedId = store.data.items[row].id;            
                            Ext.getCmp('agrupadorDetalle').on('click',function(){
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
                                    
                                    detalleAgrupador(agrupadorNombre,agrupadorBanco,agrupadorDomicilio,agrupadorForpag,agrupadorSuc,agrupadorTipotarj,agrupadorCuenta,agrupadorFecha,agrupadorDigver,DatosTarjeta);                                                                            
                                });  
                    }
                }//listeners
        
        }),
        //viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize:20,
            store:storeGridAgrupadores,                                               
            displayInfo:true,
            displayMsg: 'Registros mostrados {0} - {1} de {2}',
            emptyMsg: 'No hay registros para mostrar',
    		beforePageText: 'P&aacute;gina',
    		afterPageText: 'de {0}'                       
            })                                                                                               
        });
        
        muestraMsgEligeRecordDeGrid('agrupadorDetalle', gridAgrupadores);
        
        var panelDatosCobro = new Ext.Panel({
            border:false,
            width:774,
            items:[{
                title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Datos de Cobro</span>',
                labelWidth:100,             
                layout:'form',
                collapsible:true,
                frame:true,
                items:[gridAgrupadores]
           }]
        });
        
//************* TABS ***********
var tabs = new Ext.TabPanel({
    activeTab: 0,
    width: 778,
    //height:850,
    //autoHeight: true, 
    autoScroll: false,
    deferredRender:false,
    plain:true,
    frame: true,
    defaults:{autoScroll: true},
    items:[{
            title: 'Datos Generales',
            layout: 'form',                         
            border: false,
            //height: 1000,
            autoHeight: true,
            autoWidth: true,                           
            items: [dataGeneralesPoliza, dataAdicionalesPoliza, panelObjAsegurable, panelFnPoliza, panelDatosCobro]
        },{
            title: 'Recibo',
            layout:'form',                         
            border:false,                           
            items: [panelRecibos]
        }
    ]
});

//***********
  
  
/*************************************************************
** Panel
**************************************************************/ 
  var panelPrincipal = new Ext.Panel({
        //region: 'north',
        buttonAlign: 'center',
        title: 'Consulta de Pólizas',
        //autoHeight : true ,
        //height: 800,
        width: 800,
        //autoWidth: true,
        id:'panelPrincipal',
        bodyStyle:'padding:5px',
        frame: true,
        items: [cdUnieco, cdRamo, estado, nmPoliza,
            {
            layout: 'column',
            border: false,
            bodyStyle:'padding:5px 5px 0',
            width: 850,
            items: [{
                columnWidth: .3,
                layout: 'form',
                border: false,
                labelWidth: 70,
                items: [aseguradora]       
            },{
                columnWidth: .4,
                layout: 'form',
                width: 100,
                border: false,
                labelWidth: 50,
                items: [producto]
            },{
                columnWidth: .3,
                layout: 'form',
                border: false,
                labelWidth: 30,
                items: [poliza]
            }]
        },{ 
            layout: 'form',
            border: false,
            items: [tabs]}/*dataGeneralesPoliza, dataAdicionalesPoliza*/],
        buttons:[{
            id:'regresar',
            text:'Regresar',
            tooltip:'Regresar',
            handler: function(){
            	if(_VOLVER1){
                    	window.location=_ACTION_IR_POLIZAS_CANCELADAS + "?idRegresar=S";
                    	//window.close();
                }else if(_VOLVER2){
                         window.location=_ACTION_IR_POLIZAS_A_RENOVAR + "?idRegresar=S";
                }else if(_VOLVER3){
                         window.location=_ACTION_IR_POLIZAS_RENOVADAS + "?idRegresar=S";
                }else if(_VOLVER4){
                         window.location=_ACTION_IR_REHABILITACION_MASIVA + "?idRegresar=S";
                }else{
                        //window.location.replace("procesoemision/busquedaPoliza.action" + "?idRegresar=S");
                        window.location = _ACTION_BUSQUEDA_POLIZAS + "?idRegresar=S";
                }
        }
        }]//handler
            
            
            
                   
    });

panelPrincipal.render('items');

//Ext.getCmp('iDprueba').setFieldLabel('Prueba');
});

</script>