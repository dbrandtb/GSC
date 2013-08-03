Ext.onReady(function(){ 
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	
	var descripcionTipoEndoso = new Ext.form.TextField({
		
			    fieldLabel: getLabelFromMap('dsTipSupId',helpMap,'Archivo'),
			    tooltip:getToolTipFromMap('dsTipSupId',helpMap,' Archivo'), 
                hasHelpIcon:getHelpIconFromMap('dsTipSupId',helpMap),								 
                Ayuda: getHelpTextFromMap('dsTipSupId',helpMap),
			    
			    name:'dsTipSup',
			    id: 'dsTipSupId', 
			    anchor: '95%'
			 }); 
	
	var form = new Ext.form.FormPanel({
		  id:'form',
	      el:'formBusqueda',
          title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('form', helpMap,'Administrar Tipos de Archivos')+'</span>',				
	      iconCls:'logo',
	      bodyStyle:'background: white',
	      buttonAlign: "center",
	      labelAlign: 'right',
	      autoHeight: true,
	      frame:true,
	      url:_ACTION_OBTENER_TIPOS_ARCHIVOS,
	      width: 500,
	      height:120,
	      items: [{
	      		layout:'form',
				border: false,
				items:[{
	      			bodyStyle:'background: white',
	        		labelWidth: 100,
	              	layout: 'form',
					frame:true,
			       	baseCls: '',
			       	buttonAlign: "center",
      		        items: [{
      		        	layout:'column',
      		        	html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
	 				    border:false,
	 				    columnWidth: 1,
	      		    		items:[{
					    		columnWidth:.6,
	              				layout: 'form',
	                			border: false,
	      		        		items:[
	      		        				descripcionTipoEndoso
	       						]
							},{
							columnWidth:.4,
	              				layout: 'form'
	              				}]
	              			}],
	              			buttons:[{
				                    text:getLabelFromMap('confTipEndTxtDesc',helpMap,'Buscar'),
				                    tooltip: getToolTipFromMap('confTipEndBtnSeek',helpMap,'Busca Archivos'),
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
				                    tooltip: getToolTipFromMap('confTipEndBtnCanc',helpMap,'Cancela b&uacute;squeda de Archivos'),
	      							handler: function(){form.form.reset();}
	      					}]
      				}]
	      	}]
	});

	var cm = new Ext.grid.ColumnModel([
		
		{
	        header: getLabelFromMap('confTipEndCmDesc',helpMap,'Archivo'),
	        tooltip: getToolTipFromMap('confTipEndCmDesc',helpMap,'Columna de Archivos'),
            hasHelpIcon:getHelpIconFromMap('confTipEndCmDesc',helpMap),								 
            Ayuda: getHelpTextFromMap('confTipEndCmDesc',helpMap),
	        
	       	dataIndex: 'dsTipSup',
	       	width:200,
	       	sortable: true
	     },
	     {
	        header: getLabelFromMap('confTipEndCmCod',helpMap,'Tipo de Archivo'),
	        tooltip: getToolTipFromMap('confTipEndCmCod',helpMap,'Columna Tipo de Archivo'),
            hasHelpIcon:getHelpIconFromMap('confTipEndCmCod',helpMap),								 
            Ayuda: getHelpTextFromMap('confTipEndCmCod',helpMap),
	        
		   	dataIndex: 'indArchivo',		   	
		   	width:250,
	       	align:'center',
		   	sortable: true
		},
		 {dataIndex:'cdTipoar',hidden:true}		 
	 ]); 
	
	var _proxy = new Ext.data.HttpProxy({url: _ACTION_OBTENER_TIPOS_ARCHIVOS});
	
	var _reader = new Ext.data.JsonReader({
	            	root:'estructuraListArchivo',
	            	totalProperty: 'totalCount',
	            	waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...'),
		            successProperty : '@success'
			        },
			        [
			        {name: 'dsTipSup',   type: 'string',  mapping:'dsArchivo'},
			        {name: 'cdTipoar',   type: 'string',  mapping:'cdTipoar'},
			        {name: 'indArchivo',   type: 'string',  mapping:'indArchivo'},
					{name: 'dsIndArchivo',  type: 'string',  mapping:'dsIndArchivo'}
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
                    tooltip: getToolTipFromMap('confTipEndBtnAdd',helpMap,'Agrega tipos de Archivos'),
            		handler:function(){insertarOActualizar(null);}
	            	},
	            	{
                    text:getLabelFromMap('confTipEndBtnEd',helpMap,'Editar'),
                    tooltip: getToolTipFromMap('confTipEndBtnEd',helpMap,'Edita tipos de Archivos'),
            		handler:function(){
                			if(getSelectedRecord()!= null){
                			   //actualizar(getSelectedRecord());
                			    insertarOActualizar(getSelectedRecord());
                			}                                                  
                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
	            				}
	            	},
	            	{
                    text:getLabelFromMap('confTipEndBtnBor',helpMap,'Eliminar'),
                    tooltip: getToolTipFromMap('confTipEndBtnBor',helpMap,'Elimina Archivos'),
            		/*handler:function(){
                			if(getSelectedRecord()!= null){borrar(getSelectedRecord());}
                			else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));}
                			}*/
                	handler:function(){
               			/*if(getSelectedRecord() != null){
               					borrar(getSelectedRecord(getSelectedRecord));*/
                           if (getSelectedKey(grid, "cdTipoar") != "")
                                 {   borrar(getSelectedKey(grid, "cdTipoar"));               					
	                			  }
	                			else{
	                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	                			}
	                		}
	            	},
	            	{
                    text:getLabelFromMap('confTipEndBtnExp',helpMap,'Exportar'),
                    tooltip: getToolTipFromMap('confTipEndBtnExp',helpMap,'Exporta el contenido del grid'),
                    handler:function(){
                        var url = _ACTION_EXPORTAR_TIPOS_ARCHIVOS+'?dsArchivo='+Ext.getCmp('dsTipSupId').getValue();
                	 	showExportDialog(url);
                        }
	            	}
	            	/*
	            	,
	            	{
                    text:getLabelFromMap('confTipEndBtnBack',helpMap,'Regresar'),
                    tooltip: getToolTipFromMap('confTipEndBtnBack',helpMap,'Regresa a la pantalla anterior')
	                }
	                */
	                ,
	                {
                    text:getLabelFromMap('confTipEndBtnField',helpMap,'Campos'),
                    tooltip: getToolTipFromMap('confTipEndBtnField',helpMap,'Campos'),
             	    handler:function(){
                    if (getSelectedRecord()) {
                        window.location=_ACTION_IR_ADMINISTRACION_TIPOS_FAX+"?cdTipoar="+getSelectedRecord().get("cdTipoar")+"&dsArchivo="+getSelectedRecord().get("dsTipSup");
                    } else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
                	}
            		
	            	}
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
	                 // params = {dsTipSup: Ext.getCmp('form').form.findField('dsTipSup').getValue()};
	                  params:{
	                          dsArchivo: Ext.getCmp('form').form.findField('dsTipSup').getValue(),
	                          start:0,limit:itemsPerPage},
	                  waitMsg:getLabelFromMap('400017',helpMap,'Espere por favor...'),	                  
	                  callback : function(r,options,success) {
	                      if (!success) {
	                      	mStore.removeAll();
							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), mStore.reader.jsonData.actionErrors[0]);
	                      }
	                  }
	              }
	            );
	}
	
/*	function borrar(record) {
               Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se borrar&aacute; el registro seleccionado'), function(btn) {
                   if (btn == "yes") {
                   		var conn = new Ext.data.Connection();
               			conn.request({
						    url: _ACTION_BORRAR_TIPO_ARCHIVOS,
						  //  method: 'POST',
						    params: {"cdTipSup": record.get('cdTipSup')},			    		 			 
						    callback: function(options, success, response) {
								if (Ext.util.JSON.decode(response.responseText).success != false) {
									Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), "Borrado exitoso");
									reloadGrid(); 	
								}else {
									Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), "No se pudo borrar la informaci&oacute;n "+ Ext.util.JSON.decode(response.responseText).errorMessages[0]);
								}
							}
						});						
             		}
              });
       }; */
       
	function borrar(Key){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
         		if (btn == "yes"){
         			var _params = {
         					cdTipoar: Key
                		 	//cdatribu: record.get('cdatribu')
         					
         			};
         			execConnection (_ACTION_BORRAR_TIPO_ARCHIVOS, _params, cbkBorrar);
         			reloadGrid();
				}
		})

  	}
	function cbkBorrar (_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
		}
	}
       
});


function reloadGrid(){
	var params = {dsTipSup: Ext.getCmp('form').form.findField('dsTipSup').getValue()};
	reloadComponentStore(Ext.getCmp('grilla'), params, myCallback);
}



function myCallback(_rec, _opt, _success, _store) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll();
	}
}