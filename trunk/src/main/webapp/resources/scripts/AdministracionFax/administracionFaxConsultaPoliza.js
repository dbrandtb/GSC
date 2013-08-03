function obtenerPolizas(_cdCaso){	

	var storePolizas = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({url: _ACTION_OBTENER_POLIZAS}),
			reader: new Ext.data.JsonReader({
						root:'MListPolizas',
						totalProperty: 'totalCount',
						successProperty : '@success'
					},
					[
						{name: 'cdunieco',  type: 'string'},
						{name: 'dsunieco',  type: 'string'},
						{name: 'cdramo',  type: 'string'},
						{name: 'dsramo',  type: 'string'},
						{name: 'estado',  type: 'string'},
						{name: 'nmpoliza',  type: 'string'},
						{name: 'nmpoliex',  type: 'string'},
						{name: 'nmsituaext',  type: 'string'},
						{name: 'nmsbsitext',  type: 'string'},
						{name: 'nombrePersona',  type: 'string'},
						{name: 'cdrfc',  type: 'string'},
						{name: 'dsforpag',  type: 'string'},
						{name: 'suma', type: 'string'},
						{name: 'feinival', type:'string'},
						{name: 'fefinval', type:'string'}
					]
			)				
	});


	var storeCaso = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({url: _ACTION_OBTENER_CASO}),
			reader: new Ext.data.JsonReader({
						root:'MListPolizasFaxes',
						totalProperty: 'totalCount',
						successProperty : '@success'
					},
					[
	                    {name: 'cdPerson', type:'string'},
	                    {name: 'cdElemento', type:'string'}
					]
			)				
	});
	
	
	
	var cm = new Ext.grid.ColumnModel([
				{
		           	//header: "P&oacute;liza",
			        header: getLabelFromMap('cmdCnstPlzsNmpoliex',helpMap,'P&oacute;liza'),
			        tooltip: getToolTipFromMap('cmdCnstPlzsNmpoliex',helpMap,'P&oacute;liza'),
		           	dataIndex: 'nmpoliex',
		           	sortable: true,
		           	width: 90
				},
				{
		           	//header: "Inciso",
			        header: getLabelFromMap('cmdCnstPlzsNmsituaext',helpMap,'Inciso'),
			        tooltip: getToolTipFromMap('cmdCnstPlzsNmsituaext',helpMap,'Inciso'),
		           	dataIndex: 'nmsituaext',
		           	sortable: true,
		           	width: 90
	    		},
	    		{
		           	//header: "Subinciso",
			        header: getLabelFromMap('cmdCnstPlzsNmsbsitext',helpMap,'Subinciso'),
			        tooltip: getToolTipFromMap('cmdCnstPlzsNmsbsitext',helpMap,'Subinciso'),
		           	dataIndex: 'nmsbsitext',
		           	sortable: true,
		           	width: 90
	        	},
	    		{
		           	//header: "Nombre",
			        header: getLabelFromMap('cmdCnstPlzsNombrePersona',helpMap,'Nombre'),
			        tooltip: getToolTipFromMap('cmdCnstPlzsNombrePersona',helpMap,'Nombre'),
		           	dataIndex: 'nombrePersona',
		           	sortable: true,
		           	width: 90
	        	},
	    		{
		           	//header: "RFC",
			        header: getLabelFromMap('cmdCnstPlzsCdrfc',helpMap,'RFC'),
			        tooltip: getToolTipFromMap('cmdCnstPlzsCdrfc',helpMap,'RFC'),
		           	dataIndex: 'cdrfc',
		           	sortable: true,
		           	width: 90
	        	},
	    		{
		           	//header: "Vigencia desde",
			        header: getLabelFromMap('cmdCnstPlzsFeinival',helpMap,'Vigencia desde'),
			        tooltip: getToolTipFromMap('cmdCnstPlzsFeinival',helpMap,'Vigencia desde'),
		           	dataIndex: 'feinival',
		           	sortable: true,
		           	width: 90
	        	},
	    		{
		           	//header: "Vigencia hasta",
			        header: getLabelFromMap('cmdCnstPlzsFefinval',helpMap,'Vigencia hasta'),
			        tooltip: getToolTipFromMap('cmdCnstPlzsFefinval',helpMap,'Vigencia hasta'),
		           	dataIndex: 'fefinval',
		           	sortable: true,
		           	width: 90
	        	},
	        	{
		           	//header: "Estado",
			        header: getLabelFromMap('cmdCnstPlzsEstado',helpMap,'Estado'),
			        tooltip: getToolTipFromMap('cmdCnstPlzsEstado',helpMap,'Estado'),
		           	dataIndex: 'estado',
		           	sortable: true,
		           	width: 90
	        	},
	        	{
		           	//header: "Conducto de Cobro",
			        header: getLabelFromMap('cmdCnstPlzsDsforpag',helpMap,'Conducto de Cobro'),
			        tooltip: getToolTipFromMap('cmdCnstPlzsDsforpag',helpMap,'Conducto de Cobro'),
		           	dataIndex: 'dsforpag',
		           	sortable: true,
		           	width: 90
	        	},
	        	{
		           	//header: "Prima Total",
			        header: getLabelFromMap('cmdCnstPlzsSuma',helpMap,'Prima Total'),
			        tooltip: getToolTipFromMap('cmdCnstPlzsSuma',helpMap,'Prima Total'),
		           	dataIndex: 'suma',
		           	sortable: true,
		           	width: 90
	        	},
	        	{hidden:true,dataIndex:'cdunieco'},
	        	{hidden:true,dataIndex:'dsunieco'},
	        	{hidden:true,dataIndex:'cdramo'},
	        	{hidden:true,dataIndex:'dsramo'},
	        	{hidden:true,dataIndex:'nmpoliza'}
				]);
				
	var grid = new Ext.grid.GridPanel({
			id: 'gridId',
	        store: storePolizas,
			loadMask: {msg: getLabelFromMap('400028',helpMap,'Leyendo datos ...'), disabled: false},
	        cm: cm,	        
	        buttonAlign:'center',
			buttons:[
		        		{
							text: getLabelFromMap('cnstPlzsAcptr',helpMap,'Aceptar'),
						    tooltip:getToolTipFromMap('cnstPlzsAcptr',helpMap,'Aceptar'), 
		        			//text:'Aceptar',
		            		handler:function(){
		            			if(getSelectedRecord(grid) != null){
		            				
		            					Ext.getCmp('texPolizaId').setValue(getSelectedRecord(grid).get('nmpoliex'));
		            					ventana.close();
		                		}
		                		else{
		                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		                			//Ext.Msg.alert('Advertencia', 'Debe seleccionar un registro para realizar esta operaci&oacute;n');
		                		}
		                	}
		            	},
		            	{
							text: getLabelFromMap('cnstPlzsDtlls',helpMap,'Detalles'),
						    tooltip:getToolTipFromMap('cnstPlzsDtlls',helpMap,'Detalles'), 
		            		//text:'Detalles'
						    handler:function(){
						    //	var VariableP='hello';
						    	 //window.close()

						    if(getSelectedRecord(grid) != null){
   							//	window.location = _IR_BUSQUEDA_POLIZA +'?_cdperson='+CDPERSON+'&nmpoliex='+VariableP;
   								window.location = _IR_BUSQUEDA_POLIZA +'?_cdperson='+_cdPersona +'&nmpoliex='+getSelectedRecord(grid).get('nmpoliex');
   								
						    	}else{
	                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
						    		} 
   							}
		            	},
		            	{
							text: getLabelFromMap('cnstPlzsRgrsr',helpMap,'Regresar'),
						    tooltip:getToolTipFromMap('cnstPlzsRgrsr',helpMap,'Regresar'), 
		            		//text:'Regresar',
		            		handler:function(){ventana.close()}
		            	}
	            	],
	    	width:700,
	    	frame:true,
			height:230,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: storePolizas,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })  
			
			});			
	
	var formPanel = new Ext.FormPanel({
		    width: 700,
		    store: storePolizas,
		    bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,
	        layout: 'table',
            layoutConfig: { columns: 3, columnWidth: .33},
		    url: _ACTION_OBTENER_POLIZAS,
		    items: [
		    		{            		
           			html: '<br/><span class="x-form-item" style="font-weight:bold"></span>',
            		colspan:3
            		},		    		
		            {            		
           			layout: 'form',
           			labelWidth: 90,   
           			width: 280,
           			items: [
           				{
           					xtype: 'textfield',
				            fieldLabel: getLabelFromMap('dsuniecoId',helpMap,'Aseguradora'),
			                tooltip: getToolTipFromMap('dsuniecoId',helpMap,'Aseguradora'),			          		
		                    hasHelpIcon:getHelpIconFromMap('dsuniecoId',helpMap),
		 					Ayuda:getHelpTextFromMap('dsuniecoId',helpMap),
           					id:'dsuniecoId',
					        //fieldLabel: 'Aseguradora', 	        
					        name: 'dsunieco',
					        disabled: true,
					        width: 150
					     }
           				]
            		},
            		{            		
           			layout: 'form',
           			labelWidth: 90, 
           			width: 280,
           			items: [
           				{
           					xtype: 'textfield',
				            fieldLabel: getLabelFromMap('dsramoId',helpMap,'Producto'),
			                tooltip: getToolTipFromMap('dsramoId',helpMap,'Producto'),			          		
		                    hasHelpIcon:getHelpIconFromMap('dsramoId',helpMap),
		 					Ayuda:getHelpTextFromMap('dsramoId',helpMap),
           					//fieldLabel: 'Producto', 
					        id:'dsramoId',	        
					        name: 'dsramo',
					        disabled: true,
					        width: 150
					     }
           				]
            		},
            		{            		
           			layout: 'form'
            		},
            		{            		
           			html: '<br/><span class="x-form-item" style="font-weight:bold"></span>',
            		colspan:3
            		}/*,
            		{            		
           			layout: 'form',
           			colspan:3,          			            		
           			items: [grid]
            		}*/   
		           ]
		}); 
		
		var ventana = new Ext.Window ({
			title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('wndwCnsltPlzs',helpMap,'Consulta de P&oacute;lizas')+'</span>',
		    //title: '<span style="color:black;font-size:12px;">Consulta de P&oacute;lizas</span>',
		    width: 715,
		    modal: true,
		    autoHeight: true,
		    items: [formPanel,grid]
		});
		
		ventana.show();
	//	reloadGridPoliza();
		
		var _cdPersona;
		var _cdElementos;
		storeCaso.load({ 
			             params:{ nmcaso:_cdCaso},
                         callback:function(r,options,success){
                         reloadGridPoliza();

                         if (success==true){
                           	_cdPersona=r[0].data.cdPerson;
                           	_cdElementos=r[0].data.cdElemento;
                           	
                           	reloadGridPoliza(_cdPersona,_cdElementos);
                           } 
                          
                                     
                          },
                          failure:function(form, action){
                          	Ext.Msg.alert(getLabelFromMap('400010',helpMap,'Error'),action.result.errorMessages[0]);
                          }
              		  });
	}
	
	function valida_tconfigencuesta(_cdunieco, _cdramo){
		var _params = "cdModulo=";
		_params += "&cdUnieco="+_cdunieco;
		_params += "&cdRamo="+_cdramo;
		_params += "&cdElemento=";
		
		startMask(formPanel.id,"Guardando datos...");
		
		execConnection(_ACTION_VALIDA_TCONFIGURAENCUESTA, _params, cbkValidar);
		
	}
	
	function cbkValidar (_success, _message, _response) {
		endMask();
		if (!_success) {
			Ext.Msg.alert('Error', _message);
		}else {
			if(Ext.util.JSON.decode(_response).valida_i==1){
       			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400077', helpMap,'Existen encuestas asocioadas a la p&oacute;liza'));
				//Ext.Msg.alert("Aviso","Existen encuestas asocioadas a la poliza");
			}else{
       			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400078', helpMap,'No existen encuestas asocioadas a la p&oacute;liza'));
				//Ext.Msg.alert("Aviso", "No existen encuestas asocioadas a la poliza");
			}
		}
	}

	function reloadGridPoliza(_cdPer,_cdElemen){
		var _params = {cdPerson:_cdPer,
					   cdElemento:_cdElemen};		
		reloadComponentStore(Ext.getCmp('gridId'), _params, cbkReloadPoliza);
	}
	
	function cbkReloadPoliza(_r, _options, _success, _store) {
		var _dsunieco = (_store.reader.jsonData.MListPolizas!=null)?_store.reader.jsonData.MListPolizas[0].dsunieco:"";
		var _dsramo = (_store.reader.jsonData.MListPolizas!=null)?_store.reader.jsonData.MListPolizas[0].dsramo:""; 
		Ext.getCmp('dsuniecoId').setValue(_dsunieco);
		Ext.getCmp('dsramoId').setValue(_dsramo);
		
		if (!_success) {
			_store.removeAll();
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		}
		

	}

	
	
		