Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	
	//********* CAMPOS PARA LOS CRITERIOS DE BÚSQUEDA **************//
	var cuenta_cliente = new Ext.form.TextField({
			     fieldLabel: getLabelFromMap('confNumEndTxtCtaCli',helpMap,'Cuenta/Cliente'),
			     tooltip:getToolTipFromMap('confNumEndTxtCtaCli',helpMap,'Cuenta/Cliente de numeraci&oacute;n de endosos'), 
		         hasHelpIcon:getHelpIconFromMap('confNumEndTxtCtaCli',helpMap),
				 Ayuda:getHelpTextFromMap('confNumEndTxtCtaCli',helpMap),
			     name:'dsElemen',
			     anchor: '94%'
		});
	
	var aseguradora = new Ext.form.TextField({
			     fieldLabel: getLabelFromMap('confNumEndTxtAseg',helpMap,'Aseguradora'),
			     tooltip:getToolTipFromMap('confNumEndTxtAseg',helpMap,'Aseguradora de numeraci&oacute;n de endosos'), 
		         hasHelpIcon:getHelpIconFromMap('confNumEndTxtAseg',helpMap),
				 Ayuda:getHelpTextFromMap('confNumEndTxtAseg',helpMap),
			     name:'dsUniEco',
			     anchor: '94%'
		});
			
	var producto = new Ext.form.TextField({
			     fieldLabel: getLabelFromMap('confNumEndTxtProd',helpMap,'Producto'),
			     tooltip:getToolTipFromMap('confNumEndTxtProd',helpMap,'Producto de numeraci&oacute;n de endosos'), 
		         hasHelpIcon:getHelpIconFromMap('confNumEndTxtProd',helpMap),
				 Ayuda:getHelpTextFromMap('confNumEndTxtProd',helpMap),
			     name:'dsRamo',
			     anchor: '94%'
		});

	var plan = new Ext.form.TextField({
			     fieldLabel: getLabelFromMap('confNumEndTxtPlan',helpMap,'Plan'),
			     tooltip:getToolTipFromMap('confNumEndTxtPlan',helpMap,' Plan de numeraci&oacute;n de endosos'), 
		         hasHelpIcon:getHelpIconFromMap('confNumEndTxtPlan',helpMap),
				 Ayuda:getHelpTextFromMap('confNumEndTxtPlan',helpMap),
			     name:'dsPlan',
			     anchor: '94%'
		});

	var poliza = new Ext.form.TextField({
			     fieldLabel: getLabelFromMap('confNumEndTxtPol',helpMap,'P&oacute;liza'),
			     tooltip:getToolTipFromMap('confNumEndTxtPol',helpMap,'P&oacute;liza de numeraci&oacute;n de endosos'), 
		         hasHelpIcon:getHelpIconFromMap('confNumEndTxtPol',helpMap),
				 Ayuda:getHelpTextFromMap('confNumEndTxtPol',helpMap),
			     name:'nmPoliEx',
			     anchor: '94%'
		});
	
	//********* FORMULARIO CONTENEDOR DE LOS CAMPOS **************//	
	var form = new Ext.form.FormPanel({
		  id:'form',
	      el:'formBusqueda',
		  title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('form',helpMap,'Configurar Numeraci&oacute;n de Endosos')+'</span>',
	      iconCls:'logo',
	      bodyStyle:'background: white',
	      buttonAlign: "center",
	      labelAlign: 'right',
	      autoHeight:true,
	      frame:true,
	      url:_ACTION_OBTENER_NUMERACION_ENDOSOS,
	      width: 500,
	      height:225,
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
	 				    border:false,
	 				    html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
	 				    columnWidth: 1,
	      		    		items:[{
					    		columnWidth:.6,
	              				layout: 'form',
	                			border: false,
	      		        		items:[
	      		        				cuenta_cliente,
	      		        				aseguradora,
	      		        				producto,
	      		        				plan,
	      		        				poliza
	       						]
							},{
							columnWidth:.4,
	              			layout: 'form'
	              				}]
	              			}],
	              			buttons:[
	              					{
								     text: getLabelFromMap('confNumEndBtnSeek',helpMap,'Buscar'),
								     tooltip:getToolTipFromMap('confNumEndBtnSeek',helpMap,'Busca numeraci&oacute;n de endosos'), 
	      							 handler: function() {
					               			if (form.form.isValid()){
		                                        if(grid!=null){reloadGrid();}
		                                        else{createGrid();}
			              					}
			              					else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));}
										}
	      							},
	      							{
								     text: getLabelFromMap('confNumEndBtnCanc',helpMap,'Cancelar'),
								     tooltip:getToolTipFromMap('confNumEndBtnCanc',helpMap,'Cancela operaci&oacute;n de numeraci&oacute;n de endosos'), 
 	      							 handler: function(){form.form.reset()}
	      					}]
      				}]
	      	}]
	});
	
	//********* MODELO DE COLUMNA PARA LA GRILLA **************//	
	var cm = new Ext.grid.ColumnModel([
		{
	        header: getLabelFromMap('confNumEndCmCtaCli',helpMap,'Cuenta/Cliente'),
	        tooltip: getToolTipFromMap('confNumEndCmCtaCli',helpMap,'Columna de Cuenta/Cliente'),
		   	dataIndex: 'dsElemen',
		   	width:120,
	       	align:'left',
		   	sortable: true
		},
		{
	        header: getLabelFromMap('confNumEndCmAseg',helpMap,'Aseguradora'),
	        tooltip: getToolTipFromMap('confNumEndCmAseg',helpMap,'Columna de Aseguradora'),
		   	dataIndex: 'dsUniEco',
		   	width:100,
	       	align:'left',
		   	sortable: true
		},
		{
	        header: getLabelFromMap('confNumEndCmProd',helpMap,'Producto'),
	        tooltip: getToolTipFromMap('confNumEndCmProd',helpMap,'Columna de Producto'),
	       	dataIndex: 'dsRamo',
	       	width:100,
	       	align:'left',
	       	sortable: true
	     },
	     {
	        header: getLabelFromMap('confNumEndCmPlan',helpMap,'Plan'),
	        tooltip: getToolTipFromMap('confNumEndCmPlan',helpMap,'Columna de Plan'),
	       	dataIndex: 'dsPlan',
	       	width:100,
	       	align:'left',
	       	sortable: true
	     },
	     {
	        header: getLabelFromMap('confNumEndCmPol',helpMap,'P&oacute;liza'),
	        tooltip: getToolTipFromMap('confNumEndCmPol',helpMap,'Columna de P&oacute;liza'),
	       	dataIndex: 'nmPoliEx',
	       	width:63,
	       	align:'center',
	       	sortable: true
	     },	     
		 {dataIndex:'cdelemento',hidden:true},
		 {dataIndex:'cdramo',hidden:true},
		 {dataIndex:'cdplan',hidden:true}
	 ]);
	 
	var _proxy = new Ext.data.HttpProxy({url: _ACTION_OBTENER_NUMERACION_ENDOSOS,waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')});
	
	var _reader = new Ext.data.JsonReader({
	            	root:'numEndososEstructuraList',
	            	totalProperty: 'totalCount',
		            successProperty : '@success'
			        },
			        [
			        {name: 'cdElemento',   type: 'string',  mapping:'cdElemento'},
			        {name: 'dsElemen',   type: 'string',  mapping:'dsElemen'},
					{name: 'cdUniEco',  type: 'string',  mapping:'cdUniEco'},
			        {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
			        {name: 'cdRamo', type: 'string',  mapping:'cdRamo'},
			        {name: 'dsRamo', type: 'string',  mapping:'dsRamo'},
			        {name: 'cdPlan', type: 'string',  mapping:'cdPlan'},
			        {name: 'dsPlan', type: 'string',  mapping:'dsPlan'},
			        {name: 'nmPoliEx', type: 'string',  mapping:'nmPoliEx'},
			        {name: 'indCalc', type: 'string',  mapping:'indCalc'},
			        {name: 'nmInicial', type: 'string',  mapping:'nmInicial'},
			        {name: 'nmFinal', type: 'string',  mapping:'nmFinal'},
			        {name: 'nmActual', type: 'string',  mapping:'nmActual'},
			        {name: 'otExpres', type: 'string',  mapping:'otExpres'}			        
					]
			);
	
	var storeGrid = new Ext.data.Store({
	    			proxy: _proxy,
	                reader: _reader,
	                //waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...'),
	            	baseParams: {dsElemen: cuenta_cliente.getValue(),
	            				 dsUniEco: aseguradora.getValue(),
	            				 dsRamo: producto.getValue(),
	            				 dsPlan: plan.getValue(),
	            				 nmPoliEx: poliza.getValue()}
	        });
	
	var grid;
	function createGrid(){
		grid = new Ext.grid.GridPanel({
	        el:'grid',
	        id:'grilla',
	        store:storeGrid,
	        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
			border:true,
			buttonAlign:'center',
			loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
	        cm: cm,
			buttons:[
	        		{
				     text: getLabelFromMap('confNumEndBtnAdd',helpMap,'Agregar'),
				     tooltip:getToolTipFromMap('confNumEndBtnAdd',helpMap,'Agrega numeraci&oacute;n de endosos'), 
            		 handler:function(){
            				guardar(null,'I');
            		}
	            	},
	            	{
				     text: getLabelFromMap('confNumEndBtnEd',helpMap,'Editar'),
				     tooltip:getToolTipFromMap('confNumEndBtnEd',helpMap,'Edita numeraci&oacute;n de endosos'), 
            		 handler:function(){
                			if(getSelectedRecord(grid)!= null){guardar(getSelectedRecord(grid),'U');}
                			else{
                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso') , getLabelFromMap('400011', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                			}
                		}
	            	},
	            	{
				     text: getLabelFromMap('confNumEndBtnDel',helpMap,'Eliminar'),
				     tooltip:getToolTipFromMap('confNumEndBtnDel',helpMap,'Elimina numeraci&oacute;n de endosos'), 
            		 handler:function(){
                			if(getSelectedRecord(grid)!= null){borrar(getSelectedRecord(grid));}
                			else{
                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso') , getLabelFromMap('400011', helpMap, 'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                			}
                		}
	            	},
	            	{
				     text: getLabelFromMap('confNumEndBtnExp',helpMap,'Exportar'),
				     tooltip:getToolTipFromMap('confNumEndBtnExp',helpMap,'Exporta los datos del grid'), 
                     handler:function(){
                        var url = _ACTION_EXPORT_NUMERACION_ENDOSOS + '?dsElemen=' + cuenta_cliente.getValue() + '&dsUniEco=' + aseguradora.getValue() + '&dsRamo=' + producto.getValue() + '&dsPlan=' + plan.getValue() + '&nmPoliEx='+poliza.getValue();
                	 	showExportDialog(url);
                        }
	            	}/*,
	            	{
				     text: getLabelFromMap('confNumEndBtnBack',helpMap,'Regresar'),
				     tooltip:getToolTipFromMap('confNumEndBtnBack',helpMap,' Regresa a la pantalla anterior') 
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
	createGrid()
							
});


function reloadGrid(){
	var params = {dsElemen: Ext.getCmp('form').form.findField('dsElemen').getValue(),
				  dsUniEco: Ext.getCmp('form').form.findField('dsUniEco').getValue(),
				  dsRamo: Ext.getCmp('form').form.findField('dsRamo').getValue(),
				  dsPlan: Ext.getCmp('form').form.findField('dsPlan').getValue(),
				  nmPoliEx: Ext.getCmp('form').form.findField('nmPoliEx').getValue()
				  };
	reloadComponentStore(Ext.getCmp('grilla'), params, myCallback)
};

function myCallback(_rec, _opt, _success, _store) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),getLabelFromMap('400033', helpMap,'No se encontraron registros'));
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll()
	}
};