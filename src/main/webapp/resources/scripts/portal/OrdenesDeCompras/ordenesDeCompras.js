var helpMap = new Map();

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

var desOrden = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('desOrden', helpMap,'Orden'), 
		tooltip: getToolTipFromMap('desOrden', helpMap,'Texto B&uacute;squeda por Orden'),
		hasHelpIcon:getHelpIconFromMap('desOrden',helpMap),
		Ayuda: getHelpTextFromMap('desOrden',helpMap),
        //fieldLabel: 'Orden', 
        id: 'desOrden', 
        width: 120,
        name: 'desOrden',
        allowBlank: true
    });
    
var fechaRegistroDe = new Ext.form.DateField({
       	fieldLabel: getLabelFromMap('fechaRegistroDe', helpMap,'Fecha De Registro De'), 
		tooltip: getToolTipFromMap('fechaRegistroDe', helpMap,'Texto B&uacute;squeda por Fecha De Registro De'),  	
		hasHelpIcon:getHelpIconFromMap('fechaRegistroDe',helpMap),
		Ayuda: getHelpTextFromMap('fechaRegistroDe',helpMap),
        //fieldLabel: 'Fecha De Registro De', 
        anchor: '95%',
        //width: 100,
        //format: 'd/m/Y',
		altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
        id: 'fechaRegistroDe', 
        name: 'fechaRegistroDe',
        allowBlank: true
    });

var fechaRegistroA = new Ext.form.DateField({
       	fieldLabel: getLabelFromMap('fechaRegistroA', helpMap,'a'), 
		tooltip: getToolTipFromMap('fechaRegistroA', helpMap,'Texto B&uacute;squeda por Fecha hasta'),  			
		hasHelpIcon:getHelpIconFromMap('fechaRegistroA',helpMap),
		Ayuda: getHelpTextFromMap('fechaRegistroA',helpMap),
        //fieldLabel: 'a', 
       // format: 'd/m/Y',
		altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
        //anchor: '95%',
        width: 100,
        id: 'fechaRegistroA', 
        name: 'fechaRegistroA',
        allowBlank: true
    });
       
var incisosForm = new Ext.FormPanel({
		id: 'incisosForm',
        el:'formBusqueda',
  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('135', helpMap,'Orden De Compra')+'</span>',
        iconCls:'logo',
       buttonAlign: "center",
       labelAlign: 'right',
       frame:true,   
       url: _ACTION_BUSCAR_ORDEN_DE_COMPRAS,
       width: 500,
       height:185,
       autoHeight:true,
//html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',

items:[{  
	layout:'form',
	border: false,

	       			
	items:[{
		labelWidth: 100,
        layout: 'form',
		frame:true,
		bodyStyle:'background: white',

        items: [{
		    border:false,		    
		    html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
    		layout:'table',
        	layoutConfig: { columns: 2, columnWidth: .5},
        	 items: [
            		{
            			layout: 'form',
            			colspan: 2,
            			labelWidth:150,
            			items: [desOrden]
            		},
            		{
            			layout: 'form',
            			labelWidth:150,
            			items: [fechaRegistroDe]           			
            			
            		},
            		{
            			layout: 'form',
            			labelWidth:50,
            			items: [fechaRegistroA]
            		}],
      
        		buttonAlign: 'center',
            		buttons: [
            					{
       							text:getLabelFromMap('frmOrdnCmprButtonBuscar', helpMap,'Buscar'),
   								tooltip:getToolTipFromMap('frmOrdnCmprButtonBuscar', helpMap,'Busca &oacute;rdenes de compras'),			        			
            					handler: function () {
											if (incisosForm.form.isValid()) {
                                                   if (grid2!=null) {
                                                         reloadGrid();
                                                   } else {
                                                         createGrid();
                                                   }
                						     } else {
                						     	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										       }
   										}
            					},
            					{
       							text:getLabelFromMap('frmOrdnCmprButtonCancelar', helpMap,'Cancelar'),
   								tooltip:getToolTipFromMap('frmOrdnCmprButtonCancelar', helpMap,'Cancela b&uacute;squeda &oacute;rdenes de compras'),			        			
            					text: 'Cancelar', 
            					handler: function() {
            						incisosForm.form.reset();
            						}
            					}
            				] 
            }]
   }]
   }]    		
      });

// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
	   	header: getLabelFromMap('cmNmOrdenOrdnCmpr',helpMap,'Orden'),
        tooltip: getToolTipFromMap('cmNmOrdenOrdnCmpr', helpMap,'Columna Orden'),		           	
        //header: "Orden",
        dataIndex: 'nmOrden',
        sortable: true,
        width: 150
        },
        {
	   	header: getLabelFromMap('cmFeInicioOrdnCmpr',helpMap,'Fecha Registro'),
        tooltip: getToolTipFromMap('cmFeInicioOrdnCmpr', helpMap,'Columna Fecha Registro'),		           	
        //header: "Fecha Registro",
        dataIndex: 'feInicio',
        sortable: true,
        width: 150
        },
        {
	   	header: getLabelFromMap('cmDsEstadoOrdnCmpr',helpMap,'Estado'),
        tooltip: getToolTipFromMap('cmDsEstadoOrdnCmpr', helpMap,'Columna Estado'),		           	
        //header: "Estado",
        dataIndex: 'dsEstado',
        sortable: true,
        width: 150
        },
        {
        header: "cdCarro",
        dataIndex: 'cdCarro',
        hidden :true
        },
        {
        header: "cdContra",
        dataIndex: 'cdContra',
        hidden :true
        },
        {
        //header: "cdTipoDom",
        dataIndex: 'cdTipDom',
        hidden :true
        },
        {
        //header: "cdClient",
        dataIndex: 'cdUsuari',
        hidden :true
        },
        {
        //header: "cdClient",
        dataIndex: 'cdPerson',
        hidden :true
        }
        
]);

var lasFilas=new Ext.data.Record.create(
  [
  {name: 'cdCarro',  mapping:'cdCarro',  type: 'string'},
  {name: 'feInicio',  mapping:'feInicio',  type: 'string'},
  {name: 'cdContra',  mapping:'cdContra',  type: 'string'},
  {name: 'dsEstado',  mapping:'dsEstado',  type: 'string'},
  {name: 'nmOrden',  mapping:'nmOrden',  type: 'string'},
  {name: 'cdTipDom',  mapping:'cdTipDom',  type: 'string'},
  {name: 'cdUsuari',  mapping:'cdUsuari',  type: 'string'},
  {name: 'cdPerson',  mapping:'cdPerson',  type: 'string'}
  ]
);

var jsonGrilla= new Ext.data.JsonReader(
  {
   root:'mcEstructuraList',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
 lasFilas  
);

function storeGrilla(){
             store = new Ext.data.Store(
             {
             proxy: new Ext.data.HttpProxy(
                 {
                    url: _ACTION_BUSCAR_ORDEN_DE_COMPRAS,
                    waitMsg : getLabelFromMap('400017', helpMap,'Espere por favor ...')
    				//waitMsg: 'Espere por favor....'
    		     }
             ),
             reader:jsonGrilla,
             baseParams: {
							dsCarro: desOrden.getValue(),
							feInicioDe: fechaRegistroDe.getValue(),
							feInicioA: fechaRegistroA.getValue()
						 }
             });
             return store;
}

var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            el:'gridElementos',
            store:storeGrilla(),
            reader:jsonGrilla,
            buttonAlign:'center',
            border:true,
            //autoWidth: true,
            width: 500,
            cm: cm,
            loadMask: {msg: getLabelFromMap('400028',helpMap,'Leyendo datos...'), disabled: false},
            clicksToEdit:1,
	        successProperty: 'success',
            buttons:[
                  {
					text:getLabelFromMap('OrdnCmprButtonDetalle', helpMap,'Detalle'),
					tooltip:getToolTipFromMap('OrdnCmprButtonDetalle', helpMap,'Va a Detalle de Orden'),			        			
                  handler:function(){
                    if (getSelectedKey(grid2, "cdCarro") != "") {
                        configurar(getSelectedKey(grid2, "cdCarro"),getSelectedKey(grid2, "cdContra"),getSelectedKey(grid2, "cdTipDom"));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                  }
                  }/*,
                  {
					text:getLabelFromMap('OrdnCmprButtonRegresar', helpMap,'Regresar'),
					tooltip:getToolTipFromMap('OrdnCmprButtonRegresar', helpMap,'Vueve a la Pantalla Anterior')			        			
                  }*/,
                  {
					text:getLabelFromMap('OrdnCmprButtonRegresar', helpMap,'Finalizar'),
					tooltip:getToolTipFromMap('OrdnCmprButtonRegresar', helpMap,'Finaliza Orden de Compras'),			        			
                    handler:function(){
										if (getSelectedKey(grid2, "cdCarro") != "") {
	                        		finalizar(getSelectedKey(grid2, "cdCarro"));
	                        } else {
	                            Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	                         }                 
	                  }
                   }
                  ],
            width:500,
            frame:true,
            height:320,
            title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: store,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
	                //displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                //emptyMsg: "No hay registros para visualizar"
			    })          
        });
   grid2.render()
}

  
incisosForm.render();
createGrid();


function finalizar(key) {
		if(key != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400054', helpMap,'Se Finalizar&aacute; la Transacci&oacute;n'),function(btn)
			{
		        if (btn == "yes"){
		        	var _params = {cdCarro: key};
					execConnection(_ACTION_FINALIZAR_ORDEN_DE_COMPRAS, _params, cbkFinalizar);
                }
		    });
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
				//Ext.Msg.alert('Advertencia', 'Debe seleccionar una Orden para Finalizar');
		}
};
function cbkFinalizar(_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid();});
	}
};

configurar = function(codcarro,codcontrato,codtipdom,codusuario,codperson)
{
    window.location=_ACTION_IR_DETALLE_ORDENES_DE_COMPRAS+"?carro="+codcarro+"&contrato="+codcontrato+"&tipoDomicilio="+codtipdom;
};

});


function reloadGrid(){
	var _params = {
       		dsCarro: Ext.getCmp('incisosForm').form.findField('desOrden').getValue(),
       		feInicioDe: Ext.getCmp('incisosForm').form.findField('fechaRegistroDe').getRawValue(),
       		feInicioA: Ext.getCmp('incisosForm').form.findField('fechaRegistroA').getRawValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}

function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}