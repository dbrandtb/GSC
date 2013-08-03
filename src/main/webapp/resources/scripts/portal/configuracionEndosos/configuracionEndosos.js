Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	
	var descripcionTipoEndoso = new Ext.form.TextField({
			    fieldLabel: getLabelFromMap('confTipEndTxtDesc',helpMap,'Descripci&oacute;n'),
			    tooltip:getToolTipFromMap('confTipEndTxtDesc',helpMap,'Descripci&oacute;n del tipo de endoso'), 
			    hasHelpIcon:getHelpIconFromMap('confTipEndTxtDesc',helpMap),
				Ayuda: getHelpTextFromMap('confTipEndTxtDesc',helpMap),	
			    name:'dsTipSup',
			    width: 170
			 }); 
	
	var form = new Ext.form.FormPanel({
		  id:'form',
	      el:'formBusqueda',
		  title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('form',helpMap,'Tipos de Endosos')+'</span>',
	      iconCls:'logo',
	      bodyStyle:'background: white',
	      buttonAlign: "center",
	      labelAlign: 'right',
	      autoHeight: true,
	      frame:true,
	      url:_ACTION_OBTENER_TIPOS_ENDOSOS,
	      width: 500,
	      height:120,
	      items: [{
	      		   layout:'form',
				   border: false,
				   items:[{
	      			       bodyStyle:'background: white',
	        		       labelWidth: 100,
	              	       layout: 'column',
					       frame:true,
			       	       baseCls: '',
			       	       buttonAlign: "center",
      		               items: [{
      		        	            layout:'column',
      		        	            html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
	 				                border:false,
	 				                columnWidth: 1,
	      		    		        items:[
	      		    			          {
								           columnWidth:.1,
                				           layout: 'form',
                				           html:'&nbsp;'
                				          },
	      		    			          {
					    		          columnWidth:.6,
	              				          layout: 'form',
	                			          border: false,
	      		        		          items:[
	      		        				         descripcionTipoEndoso
	       						                 ]
							              },
							             {
							              columnWidth:.3,
	              				          layout: 'form'
	              				         }]
	              			      }],
	              			buttons:[{
				                    text:getLabelFromMap('confTipEndBtnBus',helpMap,'Buscar'),
				                    tooltip: getToolTipFromMap('confTipEndBtnBus',helpMap,'Busca tipos de endosos'),
				                    
	      									handler: function() {
				               			if (form.form.isValid()){
	                                        if(grid!=null){
	                                         reloadGrid();
	                                        }
	                                        else{
	                                         createGrid();
	                                        }
		              					}
		              					else{
		              						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										}
									}
	      							},
	      							{
				                    text:getLabelFromMap('confTipEndBtnCanc',helpMap,'Cancelar'),
				                    tooltip: getToolTipFromMap('confTipEndBtnCanc',helpMap,'Cancela b&uacute;squeda de tipos de endosos'),
	      							handler: function(){form.form.reset();}
	      					}]
      				}]
	      	}]
	});

	var cm = new Ext.grid.ColumnModel([
		{
	        header: getLabelFromMap('confTipEndCmCod',helpMap,'C&oacute;digo'),
	        tooltip: getToolTipFromMap('cconTipEndCmCod',helpMap,'Columna de c&oacute;digos de endosos'),
		   	dataIndex: 'cdTipSup',
		   	width:50,
	       	align:'center',
		   	sortable: true
		},
		{
	        header: getLabelFromMap('confTipEndCmDesc',helpMap,'Descripci&oacute;n'),
	        tooltip: getToolTipFromMap('cconTipEndCmDesc',helpMap,'Columna de descripci&oacute;n de endosos'),
	       	dataIndex: 'dsTipSup',
	       	width:250,
	       	sortable: true
	     },
	     {
	        header: getLabelFromMap('confTipEndCmTA',helpMap,'Tarificaci&oacute;n Autom&aacute;tica'),
	        tooltip: getToolTipFromMap('cconTipEndCmTA',helpMap,'Columna de tarificaci&oacute;n autom&aacute;tica de endosos'),
	       	dataIndex: 'swTariFi',
	       	width:150,
	       	align:'center',
	       	sortable: true
	     },	     
		 {dataIndex:'cdTipDoc',hidden:true},
		 {dataIndex:'swSuplem',hidden:true}
	 ]); 
	
	var _proxy = new Ext.data.HttpProxy({url: _ACTION_OBTENER_TIPOS_ENDOSOS});
	
	var _reader = new Ext.data.JsonReader({
	            	root:'endososEstructuraList',
	            	totalProperty: 'totalCount',
	            	waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...'),
		            successProperty : '@success'
			        },
			        [
			        {name: 'cdTipSup',   type: 'string',  mapping:'cdTipSup'},
			        {name: 'dsTipSup',   type: 'string',  mapping:'dsTipSup'},
					{name: 'swSuplem',  type: 'string',  mapping:'swSuplem'},
			        {name: 'swTariFi',  type: 'string',  mapping:'swTariFi'},
			        {name: 'cdTipDoc', type: 'string',  mapping:'cdTipDoc'}
					]);
	
	var storeGrid = new Ext.data.Store({
	    			proxy: _proxy,
	    			waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...'),
	                reader: _reader,
	            	baseParams: {dsTipSup: descripcionTipoEndoso.getValue()}
	        });
	
	var grid;
	function createGrid(){
		grid = new Ext.grid.GridPanel({
	        el:'grid',
	        id:'grilla',
	        store:storeGrid,
			border:true,
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
			buttonAlign:'center',
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
			buttons:[
	        		{
                    text:getLabelFromMap('confTipEndBtnAdd',helpMap,'Agregar'),
                    tooltip: getToolTipFromMap('confTipEndBtnAdd',helpMap,'Agrega tipos de endosos'),
            		handler:function(){insertar();}
	            	},
	            	{
                    text:getLabelFromMap('confTipEndBtnEd',helpMap,'Editar'),
                    tooltip: getToolTipFromMap('confTipEndBtnEd',helpMap,'Edita tipos de endosos'),
            		handler:function(){
                			if(getSelectedRecord()!= null){guardar(getSelectedRecord());}
                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
	            				}
	            	},
	            	{
                    text:getLabelFromMap('confTipEndBtnBor',helpMap,'Eliminar'),
                    tooltip: getToolTipFromMap('confTipEndBtnBor',helpMap,'Elimina tipos de endosos'),
            		handler:function(){
                			if(getSelectedRecord()!= null){borrar(getSelectedRecord());}
                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));}
                			}
	            	},
	            	{
                    text:getLabelFromMap('confTipEndBtnExp',helpMap,'Exportar'),
                    tooltip: getToolTipFromMap('confTipEndBtnExp',helpMap,'Exporta el contenido del grid'),
                    handler:function(){
                        var url = _ACTION_EXPORT_TIPOS_ENDOSOS+'?dsTipSup='+descripcionTipoEndoso.getValue();
                	 	showExportDialog(url);
                        }
	            	}/*,
	            	{
                    text:getLabelFromMap('confTipEndBtnBack',helpMap,'Regresar'),
                    tooltip: getToolTipFromMap('confTipEndBtnBack',helpMap,'Regresa a la pantalla anterior')
	                }*/
	            	],
	    	width:500,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible:true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: storeGrid,
					displayInfo: true,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	    grid.render()
	}
	
	form.render();
	createGrid();
	
	function getSelectedRecord(){
	    var m = grid.getSelections();
	    if (m.length == 1 ) {
	       return m[0];
	       }
    }
    
    function reloadGrid(){
	    var mStore = grid.store;
	    var o = {start: 0};
	    mStore.baseParams = mStore.baseParams || {};
	    mStore.baseParams['dsTipSup'] = (descripcionTipoEndoso.getValue()!=null || descripcionTipoEndoso.getValue()!="")?descripcionTipoEndoso.getValue():null;
	    mStore.reload(
	              {
	                  params:{start:0,limit:itemsPerPage},
	                  waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...'),	                  
	                  callback : function(r,options,success) {
	                      if (!success) {
							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400033', helpMap,'No se encontraron registros'));
	                         mStore.removeAll();
	                      }
	                  }
	              }
	            );
	}
	
	function borrar(record) {
               Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'), function(btn) {
                   if (btn == "yes") {
                   		var conn = new Ext.data.Connection();
               			conn.request({
						    url: _ACTION_BORRAR_TIPO_ENDOSO,
						    method: 'POST',
						    params: {"cdTipSup": record.get('cdTipSup')},			    		 			 
						    callback: function(options, success, response) {
								if (Ext.util.JSON.decode(response.responseText).success != false) {
									//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), "Borrado exitoso");
									Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400081', helpMap,'Los datos se eliminaron con éxito'));
									reloadGrid(); 	
								}else {
									//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), "No se pudo borrar la informaci&oacute;n "+ Ext.util.JSON.decode(response.responseText).errorMessages[0]);
									Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
								}
							}
						});						
             		}
              });
       };
});


function reloadGrid(){
	var params = {dsTipSup: Ext.getCmp('form').form.findField('dsTipSup').getValue()};
	reloadComponentStore(Ext.getCmp('grilla'), params, myCallback);
}

function myCallback(_rec, _opt, _success, _store) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll();
	}
}