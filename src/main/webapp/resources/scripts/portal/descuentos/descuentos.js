Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

 //cantidad de descuentos a mostrar por pagina
// var itemsPerPage=10;

 var cm = new Ext.grid.ColumnModel([
	{
	   	header: getLabelFromMap('cmDsDsctoNombreDescuento',helpMap,'Nombre'),
        tooltip: getToolTipFromMap('cmDsDsctoNombreDescuento', helpMap,'Nombre '),	
        hasHelpIcon:getHelpIconFromMap('cmDsDsctoNombreDescuento',helpMap),
		Ayuda: getHelpTextFromMap('cmDsDsctoNombreDescuento',helpMap,''),
           	
	   	dataIndex: 'dsDscto',
	   	width: 150,
	   	sortable: true
	},{
       	header: getLabelFromMap('cmDsTipoTipoDescuento',helpMap,'Tipo'),
        tooltip: getToolTipFromMap('cmDsTipoTipoDescuento', helpMap,'Tipo de Descuento'),
        hasHelpIcon:getHelpIconFromMap('cmDsDsctoNombreDescuento',helpMap),
		Ayuda: getHelpTextFromMap('cmDsDsctoNombreDescuento',helpMap,''),
        
       	dataIndex: 'dsTipo',
       	width: 90,
	   	sortable: true
     },{
       	header: getLabelFromMap('cmDsNombreClienteDescuento',helpMap,'Nivel'),
        tooltip: getToolTipFromMap('cmDsNombreClienteDescuento', helpMap,'Nivel'),
        hasHelpIcon:getHelpIconFromMap('cmDsNombreClienteDescuento',helpMap),
		Ayuda: getHelpTextFromMap('cmDsNombreClienteDescuento',helpMap,''),
        
       	dataIndex: 'dsNombre',
       	width: 160,
	   	sortable: true
     },{
       	header: getLabelFromMap('cmDsAcumulAcumulaDescuento',helpMap,'Acumula'),
        tooltip: getToolTipFromMap('cmDsAcumulAcumulaDescuento', helpMap,'Acumula Descuento'),
        hasHelpIcon:getHelpIconFromMap('cmDsAcumulAcumulaDescuento',helpMap),
		Ayuda: getHelpTextFromMap('cmDsAcumulAcumulaDescuento',helpMap,''),
        
       	dataIndex: 'dsAcumul',
       	width: 90,
	   	sortable: true
     },{
       	header: getLabelFromMap('cmDsEstadoActivoDescuento',helpMap,'Activo'),
        tooltip: getToolTipFromMap('cmDsEstadoActivoDescuento', helpMap,'Estado'),
        hasHelpIcon:getHelpIconFromMap('cmDsEstadoActivoDescuento',helpMap),
		Ayuda: getHelpTextFromMap('cmDsEstadoActivoDescuento',helpMap,''),
        
       	dataIndex: 'dsEstado',
       	width: 90,
	   	sortable: true
     },{
       	dataIndex: 'cdDscto',
       	hidden: true
     	}
 ]);

//Campos (criterios/filtros) de búsqueda.
var descuento = new Ext.form.TextField({
     fieldLabel: getLabelFromMap('txtFldDsDescuentoNombre', helpMap,'Nombre'), 
     tooltip: getToolTipFromMap('txtFldDsDescuentoNombre', helpMap,'Nombre del descuento'),  
     hasHelpIcon:getHelpIconFromMap('txtFldOtValorDescuentoTipo',helpMap),
	Ayuda: getHelpTextFromMap('txtFldOtValorDescuentoTipo',helpMap,''),
           
     //fieldLabel: 'Nombre',
     name:'dsDescuento',
     anchor:'95%'
 });

var tipo = new Ext.form.TextField({
     fieldLabel: getLabelFromMap('txtFldOtValorDescuentoTipo', helpMap,'Tipo'), 
     tooltip: getToolTipFromMap('txtFldOtValorDescuentoTipo', helpMap,'Tipo de descuento'),    
     hasHelpIcon:getHelpIconFromMap('txtFldOtValorDescuentoTipo',helpMap),
   	Ayuda: getHelpTextFromMap('txtFldOtValorDescuentoTipo',helpMap,''),
       
     //fieldLabel: 'Tipo',
     name:'otValor',
     anchor:'95%'
 });

var cliente = new Ext.form.TextField({
     fieldLabel: getLabelFromMap('txtFldDsClienteDescuentoNivel', helpMap,'Nivel'), 
     tooltip: getToolTipFromMap('txtFldDsClienteDescuentoNivel', helpMap,'Nivel asociado'),   
     hasHelpIcon:getHelpIconFromMap('txtFldDsClienteDescuentoNivel',helpMap),
		Ayuda: getHelpTextFromMap('txtFldDsClienteDescuentoNivel',helpMap,''),
             
     //fieldLabel: 'Cliente',
     name:'dsCliente',
     anchor:'95%'
 });

 function test(){
 			store = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_DESCUENTOS
                }),
                reader: new Ext.data.JsonReader({
            	root:'MDescuentoList',
            	totalProperty: 'totalCount',
            	id: 'cdDscto',
	            successProperty : '@success'
	        },[
	        {name: 'cdDscto',  type: 'string',  mapping:'cdDscto'},
	        {name: 'dsDscto',  type: 'string',  mapping:'dsDscto'},
			{name: 'cdTipo',  type: 'string',  mapping:'cdTipo'},
	        {name: 'dsTipo',  type: 'string',  mapping:'dsTipo'},
	        {name: 'dsNombre',  type: 'string',  mapping:'dsNombre'},
	        {name: 'dsApellido',  type: 'string',  mapping:'dsApellido'},
	        {name: 'dsAcumul',  type: 'string',  mapping:'dsAcumul'},
	        {name: 'dsEstado',  type: 'string',  mapping:'dsEstado'}
			]),
            baseParams: {dsDescuento:descuento.getValue(),
            			 otValor: tipo.getValue(),
            			 dsCliente: cliente.getValue()}
        });
		return store;
 	}

var grid2;

function createGrid(){
	grid2= new Ext.grid.GridPanel({
        el:'gridDescuentos',
        store:test(),
		border:true,
		buttonAlign:'center',
		loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
		title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
        cm: cm,
		buttons:[
        		{
      				text:getLabelFromMap('gridDescuentosButtonAgregarProducto', helpMap,'Agregar por Producto'),
           			tooltip:getToolTipFromMap('gridDescuentosButtonAgregarProducto', helpMap,'Agrega una configuraci&oacute;n de descuento por Producto'),			        			
        			//text:'Agregar',
            		//tooltip:'Agregar un nuevo descuento',
            		handler:function(){
            			agregar("P");
            			//reloadGrid();
            			}
            	},{
      				text:getLabelFromMap('gridDescuentosButtonAgregarVolumen', helpMap,'Agregar por Volumen'),
           			tooltip:getToolTipFromMap('gridDescuentosButtonAgregarVolumen', helpMap,'Agrega una configuraci&oacute;n de descuento por Volumen'),			        			
        			//text:'Agregar',
            		//tooltip:'Agregar un nuevo descuento',
            		handler:function(){
            			agregar("V");
            			//reloadGrid();
            			}
            	},
            	{
      				text:getLabelFromMap('gridDescuentosButtonEditar', helpMap,'Editar'),
           			tooltip:getToolTipFromMap('gridDescuentosButtonEditar', helpMap,'Edita una configuraci&oacute;n de descuento'),           		
            		handler:function(){
                			if(getSelectedRecord()!=null){
                					editar(getSelectedRecord());
                			}
                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
                		}
            	},
            	{
      				text:getLabelFromMap('gridDescuentosButtonBorrar', helpMap,'Eliminar'),
           			tooltip:getToolTipFromMap('gridDescuentosButtonBorrar', helpMap,'Elimina una configuraci&oacute;n de descuento'),           		
                	handler:function(){
                			if(getSelectedRecord()!=null){
                					borrar(getSelectedRecord());
                					reloadGrid();
                			}
                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
                		}
            	},
            	{
      				text:getLabelFromMap('gridDescuentosButtonCopiar', helpMap,'Copiar'),
           			tooltip:getToolTipFromMap('gridDescuentosButtonCopiar', helpMap,'Copia una configuraci&oacute;n de descuento'),           		
                	handler:function(){
                			if(getSelectedRecord()!=null){
                					copiar(getSelectedRecord());
                			}
                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
                		}
            	},
            	{
      				text:getLabelFromMap('gridDescuentosButtonExportar', helpMap,'Exportar'),
           			tooltip:getToolTipFromMap('gridDescuentosButtonExportar', helpMap,'Exporta el contenido del grid'),           		
                    handler:function(){
                        var url = _ACTION_EXPORT_DESCUENTOS + '?dsDescuento=' + descuento.getValue()+ '&otValor=' + tipo.getValue()+ '&dsCliente=' + cliente.getValue();
                	 	showExportDialog( url );
                        }
            	}/*,
            	{
      				text:getLabelFromMap('gridDescuentosButtonRegresar', helpMap,'Regresar'),
           			tooltip:getToolTipFromMap('gridDescuentosButtonBorrar', helpMap)         		
                 }*/
            	],

    	width:600,
    	frame:true,
		height:320,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		stripeRows: true,
    	collapsible: true,
		bbar: new Ext.PagingToolbar({
				pageSize: itemsPerPage,
				store: store,
				displayInfo: true,
				displayMsg:getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
                //displayMsg: 'Mostrando registros {0} - {1} of {2}',
                //emptyMsg: "No hay registros para visualizar"
		    })
		});

    grid2.render()
}

function reloadGrid(){
    var mStore = grid2.store;
    var o = {start: 0};
    mStore.baseParams = mStore.baseParams || {};
    mStore.baseParams['dsDescuento'] = descuento.getValue();
    mStore.baseParams['otValor'] = tipo.getValue();
    mStore.baseParams['dsCliente'] = cliente.getValue();
    mStore.reload(
              {
                  params:{start:0,limit:itemsPerPage},
                  callback : function(r,options,success) {
                      if (!success) {                         
                         //Ext.MessageBox.alert('Error', 'No se encontraron registros');
                         Ext.MessageBox.alert(getLabelFromMap('400000',helpMap,'Aviso'),getLabelFromMap('400033', helpMap,'No se encontraron registros'));
                         mStore.removeAll();
                      }
                  }
              }
            );
}

  var incisosForm = new Ext.form.FormPanel({
      el:'formBusqueda',
	  title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm', helpMap,'Descuentos')+'</span>',
      iconCls:'logo',
      bodyStyle:'background: white',
      buttonAlign: "center",
      labelAlign: 'right',
      autoHeight: true,
      frame:true,
      url:_ACTION_BUSCAR_DESCUENTOS,
      width: 600,
      height:180,
      items: [{
      		layout:'form',
		    border: false,
		    items:[{//bodyStyle:'background: white',
		            labelWidth: 100,
		            layout: 'form',
				    frame:true,
		       	    baseCls: '',
		       	    buttonAlign: "center",
		      		        items: [{
		      		        	html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
		      		        	layout:'column',
		 				        border:false,
		 				        columnWidth: 1,
		      		    		items:[{
						    		columnWidth:.6,
		              				layout: 'form',
		                			border: false,
		      		        		items:[
		      		        				descuento,
		      		        				tipo,
		      		        				cliente   
		       						]
								},{
								columnWidth:.4,
		              				layout: 'form'
		              				}]
		              				
		              			},{
				   				html: '<span class="x-form-item" style="font-weight:bold"></span><br>'
				   				}
		              			],
		              			buttons:[{
		      							text:getLabelFromMap('busquedaDescuentosButtonBuscar', helpMap,'Buscar'),
		           						tooltip:getToolTipFromMap('busquedaDescuentosButtonBuscar', helpMap,'Busca descuentos seg&uacute;n los criterios de b&uacute;squeda'),	
		      							//text:'Buscar',
		      							handler: function() {
				               			if (incisosForm.form.isValid()) {
		                                             if (grid2!=null) {
		                                              reloadGrid();
		                                             } else {
		                                              createGrid();
		                                             }
		              						} else{
											//Ext.MessageBox.alert('Error', 'Por Favor revise los errores!');
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										}
									}
		      							},{
		      							text:getLabelFromMap('busquedaDescuentosButtonCancelar', helpMap,'Cancelar'),
		           						tooltip:getToolTipFromMap('busquedaDescuentosButtonCancelar', helpMap,'Cancela la b&uacute;squeda'),	
		      							//text:'Cancelar',
		      							handler: function() {
		      								incisosForm.form.reset();
		      							}
		      						}]
      					}]
      				}]
});

incisosForm.render();
createGrid();

function toggleDetails(btn, pressed){
 	var view = grid.getView();
 	view.showPreview = pressed;
 	view.refresh();
 }
var store;

function getSelectedRecord(){
    var m = grid2.getSelections();
    if (m.length == 1 ) {
       return m[0];
       }
   }

function agregar(tipo){
    	window.location=_ACTION_IR_AGREGAR_DESCUENTO+"?cdTipo="+tipo;
    }
    
function editar(record){
    	window.location=_ACTION_IR_EDITAR_DESCUENTO+"?cdDscto="+record.get("cdDscto")+"&dsNombre="+record.get("dsDscto")+"&cdTipo="+record.get("cdTipo");
    }

function borrar(record) {
			var conn = new Ext.data.Connection();
		
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
          	       if (btn == "yes")
          	        {
          	        conn.request({
				    url: _ACTION_BORRAR_DESCUENTO,
				    method: 'POST',
				    params: {"cdDscto": record.get("cdDscto")},
				    success: function() {
				    	//Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Descuento eliminado.');
				    	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('200012', helpMap,'Registro eliminado'));

				    	
				    	reloadGrid();
				    },
			    	failure: function() {
			        // Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al eliminar');
			         Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400059', helpMap,'No se borro el registro seleccionado'));
			     	}
					});
               	}
			})
         };   

function copiar(record)
		{	        			
			var conn = new Ext.data.Connection();
			
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400032', helpMap,'Se copiar&aacute; el registro seleccionado'),function(btn)
			{
         	       if (btn == "yes")
         	        {
	         	        conn.request({
					    url: _ACTION_COPIAR_DESCUENTO,
					    method: 'POST',
					    params: {"cdDscto": record.get("cdDscto")},
					    success: function() {
					    			
                                      Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('200009', helpMap,'Registro copiado'), function() {reloadGrid();});
					    			
					    			  //Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Descuento copiado', function() {reloadGrid();});
					    			},
					    failure: function() {
					               Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400060', helpMap,'No se copio el registro seleccionado'));


	                              //Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
	                              
					     		}	
						});
              	   }
			})

		};          

});