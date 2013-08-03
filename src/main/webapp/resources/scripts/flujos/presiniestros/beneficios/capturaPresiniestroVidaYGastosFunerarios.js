
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

var itemsPerPage = 20;
var gridDocs;
	
	if(_UPDATE_MODE){
		if(Ext.isEmpty(BENEFICIO.folio))Ext.MessageBox.alert('Error', 'Error al recuperar los datos. Consulte a su soporte', function () { window.location=_ACTION_IR_CONSULTA_POLIZAS_PRESINIESTROS; });
	}else {
		if(BENEFICIO.folio == null || BENEFICIO.folio == undefined)Ext.MessageBox.alert('Error', 'Error al recuperar los datos. Consulte a su soporte', function () { window.location=_ACTION_IR_CONSULTA_POLIZAS_PRESINIESTROS; });
	}

	var textFolio = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'Folio'),
        tooltip:getToolTipFromMap('',helpMap,'Folio del Pre Siniestro'), 
        width: 100,
        value: BENEFICIO.folio,
        disabled: true
	});
	
	var fecha = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'Fecha'),
        tooltip:getToolTipFromMap('',helpMap,'Fecha del Pre Siniestro'), 
        width: 100,
        value: BENEFICIO.fecha,
        disabled: true
	});
    
    var noSiniestroAseg= new Ext.form.NumberField({
		fieldLabel: getLabelFromMap('',helpMap,'No. Siniestro Aseguradora'),
    	name:'beneficio.noSiniestroAseg',
		allowDecimals : false,
		allowNegative : false,
		width:100,
		maxLength: 20,
		maxLengthText: 'M&aacute;ximo 20 caracteres',
		disabled: _UPDATE_MODE?true:false,
		allowBlank: false
       });
       
       
	var poliza= new Ext.form.TextField({
		fieldLabel: getLabelFromMap('',helpMap,'P&oacute;liza'),
		width:100,
		value: BENEFICIO.poliza,
		disabled: true
       });
       
	var inciso = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'Inciso'),
        tooltip:getToolTipFromMap('',helpMap,'Inciso del Pre Siniestro'), 
        width: 100,
        value: BENEFICIO.inciso,
        disabled: true
	});
	
	var aseguradora = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'Aseguradora'),
        tooltip:getToolTipFromMap('',helpMap,'Aseguradora del Pre Siniestro'), 
        width: 100,
        value: BENEFICIO.aseguradora,
        disabled: true
	});
	
	
	var ramo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'Ramo'),
        tooltip:getToolTipFromMap('',helpMap,'Ramo del Pre Siniestro'), 
        width: 100,
        value: BENEFICIO.dsramo,
        disabled: true
	});
	
	
	var storeTipoTramite = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: _ACTION_COMBO_TIPO_TRAMITE
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaTramites',
            id: 'value'
	        },[
           {name: 'otclave', type: 'string',mapping:'value'},
           {name: 'otvalor', type: 'string',mapping:'label'}
        ]),
		remoteSort: true
    });
	
    var comboTipoTramite =new Ext.form.ComboBox({
    	                    //tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeTipoTramite,
						    displayField:'otvalor',
						    valueField: 'otclave',
						    allowBlank: false,
					    	typeAhead: true,
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione un Tipo...',
					    	selectOnFocus:true,
					    	forceSelection:true,
					    	width:150,
						    fieldLabel: 'Tipo de tr&aacute;mite',
						    disabled: _UPDATE_MODE?true:false,
					    	hiddenName:'beneficio.cdTipoTramite'
	});
	
	
	var fechaPrimerGasto = new Ext.form.DateField({
        name:'beneficio.fechaPrimerGasto',
        disabled: _UPDATE_MODE?true:false,
        fieldLabel:'Fecha Primer Gasto',
        format:'d/m/Y',
        width: 100,
        allowBlank:false
    });
    
	var titular = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'Titular'),
        tooltip:getToolTipFromMap('',helpMap,'Titular del Pre Siniestro'), 
        width: 250,
        value: BENEFICIO.titular,
        disabled: true
	});
	
	var corpo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'Corporativo'),
        tooltip:getToolTipFromMap('',helpMap,'Corporativo del Pre Siniestro'), 
        width: 250,
        value: BENEFICIO.empresa,
        disabled: true
	});
	
	var asegurado = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'Asegurado'),
        tooltip:getToolTipFromMap('',helpMap,'Asegurado del Pre Siniestro'), 
        width: 250,
        value: BENEFICIO.asegurado,
        disabled: true
	});
	
	var reportante = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'Quien report&oacute;'),
        tooltip:getToolTipFromMap('',helpMap,'Nombre de la persona que ha reportado el Pre Siniestro'), 
        name: 'beneficio.reportadoPor',
        disabled: _UPDATE_MODE?true:false,
        allowBlank:false,
        width: 250,
        maxLength: 150,
        maxLengthText: 'M&aacute;ximo 150 caracteres'
	});
	
	var telefono = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'Tel&eacute;fono'),
        tooltip:getToolTipFromMap('',helpMap,'Tel&eacute;fono de la persona que ha reportado el Pre Siniestro'), 
        name: 'beneficio.telefonoRep',
        width: 100,
        maxLength: 20,
        maxLengthText: 'M&aacute;ximo 20 caracteres'
	});
	
	var observaciones = new Ext.form.TextArea({
        fieldLabel: getLabelFromMap('',helpMap,'Observaciones'),
        tooltip:getToolTipFromMap('',helpMap,'Observaciones del Pre Siniestro'), 
        name: 'beneficio.observaciones',
        width: 310,
        height: 80,
        maxLength: 150,
        maxLengthText: 'M&aacute;ximo 500 caracteres'
	});
	
	var montoTotal = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('',helpMap,'<b>Monto Total</b>'),
        id: 'montoTotalId',
        tooltip:getToolTipFromMap('',helpMap,'Monto Total de los Documentos'), 
        name: 'montoTotal',
        width: 140,
        readOnly: true
	});
	
	if(_UPDATE_MODE){
		
		noSiniestroAseg.setValue(BENEFICIO.noSiniestroAseg);
		reportante.setValue(BENEFICIO.reportadoPor);
		telefono.setValue(BENEFICIO.telefonoRep);
		
		var connect = new Ext.data.Connection();
		connect.request ({
			url: _ACTION_OBTIENE_DESC_OBSV,
			callback: function (options, success, response) {				   
				var obs = Ext.util.JSON.decode(response.responseText).mensajeAux; 
				observaciones.setValue(obs);
			}
		});
		
	}
	
	storeTipoTramite.load({callback: function(){ if(_UPDATE_MODE)comboTipoTramite.setValue(BENEFICIO.cdTipoTramite); }});
	
	
	var _cm = new Ext.grid.ColumnModel([
		{
        header: getLabelFromMap('',helpMap,'Tipo de Documento'),
		dataIndex: 'dsTipDoc',
		width:185},
		{
        header: getLabelFromMap('',helpMap,'Descripcion'),
		dataIndex: 'descripcion',
		width:265},
	    {
        header: getLabelFromMap('',helpMap,'Monto'),
	    dataIndex: 'monto',
	    width:145}
	 ]);
		
	var _proxy = new Ext.data.HttpProxy({url: _ACTION_DOCUMENTOS});
	
	var _reader = new Ext.data.JsonReader(
		{root: 'listaDocumentos',totalProperty: 'totalCount',successProperty: '@success'},
		[
			{name: 'cdValoDoc',  type: 'string',  mapping:'cdValoDoc'},
			{name: 'isNew',  type: 'boolean',  mapping:'isNew'},
       		{name: 'cdTipDoc',   type: 'string',  mapping:'cdTipDoc'},
       		{name: 'dsTipDoc',   type: 'string',  mapping:'dsTipDoc'},
			{name: 'cdUnica',  type: 'string',  mapping:'cdUnica'},
       		{name: 'cdMuestra', type: 'string',  mapping:'cdMuestra'},
       		{name: 'descripcion', type: 'string',  mapping:'atr1'},
       		{name: 'monto', type: 'string',  mapping:'atr2'},
       		{name: 'montoTotal', type: 'string',  mapping:'montoTotal'}
       		
		]);
		
	storeGrid = new Ext.data.Store({
		 proxy: _proxy,
		 id: 'cdValoDoc',
		 reader: _reader,
		 waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...'),
		 listeners : {
		 	load : function(str,records,options){
		 			if(str.getTotalCount()>0){
		 				var auxMonto = str.getAt(0).get('montoTotal');
		 				if(!Ext.isEmpty(auxMonto))Ext.getCmp('montoTotalId').setValue(auxMonto);
		 				else Ext.getCmp('montoTotalId').setValue('0.00');
		 			}else{
		 				Ext.getCmp('montoTotalId').setValue('0.00');
		 			}
		 	}
		 }
		});

	gridDocs = new Ext.grid.GridPanel({
			id:'grid',
	        store:storeGrid,
	        buttonAlign:'left',
			loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
			//title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado Documentos</span>',
	        cm: _cm,
			buttons:[
	        		{
					text:getLabelFromMap('',helpMap,'Agregar'),
					tooltip: getToolTipFromMap('',helpMap,'Agrega un nuevo documento al Pre Siniestro'),
            		handler:function(){documentoPresiniestro(null, storeGrid, BENEFICIO.cdElemento, BENEFICIO.cdaseguradora, BENEFICIO.cdramo, _UPDATE_MODE, true);}
	            	},
	            	{
					text:getLabelFromMap('',helpMap,'Editar'),
					tooltip: getToolTipFromMap('',helpMap,'Edita un documento del Pre Siniestro'),
            		handler:function(){
            					if(gridDocs.getSelectionModel().hasSelection()){
	                    			documentoPresiniestro(gridDocs.getSelectionModel().getSelected(), storeGrid, BENEFICIO.cdElemento, BENEFICIO.cdaseguradora, BENEFICIO.cdramo, _UPDATE_MODE, false);
	                    		}
	                    		else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
            				}
	            	},
	            	{
					text:getLabelFromMap('',helpMap,'Eliminar'),
					tooltip: getToolTipFromMap('',helpMap,'Elimina un documento del Pre Siniestro'),
            		handler:function()
            				{
            				if(gridDocs.getSelectionModel().hasSelection()){
            					
            					Ext.MessageBox.confirm('Eliminar', 'Se eliminar&aacute; el registro seleccionado', function(btn) {
				                   if (btn == "yes") {
				                   		var conn = new Ext.data.Connection();
				               			conn.request({
										    url: _ACTION_ELIMINA_DOCUMENTO,
										    method: 'POST',
										    params: {
										    	cdValoDoc: gridDocs.getSelectionModel().getSelected().get('cdValoDoc'),
										    	tipoOperacion: gridDocs.getSelectionModel().getSelected().get('isNew')
										    },			    		 			 
										    callback: function(options, success, response) {
												if (Ext.util.JSON.decode(response.responseText).success != false) {
													Ext.Msg.alert("Aviso", /*Ext.util.JSON.decode(response.responseText).mensajeRespuesta*/'Registro borrado', function (){ storeGrid.load();	});
													
												}else {
													Ext.Msg.alert("Error", /*Ext.util.JSON.decode(response.responseText).mensajeRespuesta*/'Existi&oacute; un problema al eliminar');
												}
											}
										});						
				             		}
				              	});
            				}
                			 else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
	                		}
	            	}],
	    	width:610,
	    	height:200,
	    	frame:true,
			sm: new Ext.grid.RowSelectionModel({
				singleSelect: true
			}),
			
			stripeRows: true/*,
			bbar: new Ext.PagingToolbar({
					pageSize: 20,
					store: storeGrid,
					displayInfo: false,
					displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })*/
			});
	
	storeGrid.load({
		params: {
			mensajeAux: 'PRIMER_CARGA',
			tipoOperacion: _UPDATE_MODE? 'U':'I'
		}
	});
	
	var formPresiniestros = new Ext.form.FormPanel({
		  id:'form',
	      el:'formPresiniestros',
	      url: _ACTION_GUARDA_PRE_VIDA_GASFUN,
	      title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('',helpMap,'Pre Siniestros Vida y Gastos Funerarios')+'</span>',
	      bodyStyle:'background: white',
	      buttonAlign: "center",
	      labelAlign: 'right',
	      autoHeight:true,
	      frame:true,
	      width: 700,
	      items: [
	      		{
	      		layout:'form',
				border: false,
				items:[
					{
	      			//bodyStyle:'background: white',
	        		labelWidth: 90,
	              	layout: 'form',
					frame:true,
			       	baseCls: '',
			       	buttonAlign: "center",
      		        items:[
      		        	{
      		        	layout:'column',
      		        	html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Agregar Pre Siniestro</span><br>',
      		    		items:[
      		    			{
						    	columnWidth:1,
		              			layout: 'form', 
		      		        	items:[noSiniestroAseg]
							},
	      		    		{
						    	columnWidth:.33,
		              			layout: 'form', 
		      		        	items:[textFolio, ramo]
							},
							{
								columnWidth:.33,
		              			layout: 'form',
		              			items:[aseguradora, poliza]
	              			},
							{
								columnWidth:.33,
		              			layout: 'form',
		              			items:[fecha, inciso]
	              			}
	              		 ]
              			},{
      		        	layout:'column',
      		        	html:'<br><br>',
      		    		items:[
      		    			{
						    	columnWidth: .04,
		              			layout: 'fit'
							},{
						    	columnWidth:.60,
		              			layout: 'form', 
		      		        	items:[comboTipoTramite, titular, corpo, 
		      		        			asegurado, reportante, observaciones]
							},
							{
								columnWidth:.36,
		              			layout: 'form',
		              			items:[{layout:'column', html:'<br><br><br><br><br><br><br><br>'},		              			
		              				   telefono]
	              			}
	              		 ]
              			},{
      		        	layout:'column',
      		        	html:'<br>',
      		    		items:[
      		    			{
						    	width: 25,
		              			layout: 'fit'
							},{
						    	columnWidth:1,
		              			layout: 'form', 
		      		        	items:[
			      		        	{          
				                    xtype:'fieldset',
				                    title:'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Documentos</span>',
				                    width:640,
				                    height:250,
				                    items:[gridDocs,
				                    	   {
				                    	   	layout:'column',
					      		    		items:[
					      		    			{
										    		columnWidth: .60,
						              				layout: 'fit'
												},{
											    	columnWidth:.40,
							              			layout: 'form', 
							      		        	items:[montoTotal]
												}
					      		    		]
				                    	   }
				                    ]
				            		}]
							}]
              			}],
			        buttons:[
				      	{
					     text:getLabelFromMap('',helpMap,'Guardar Todo'),
					     tooltip: getToolTipFromMap('',helpMap,'Guarda el Pre Siniestro'),
						 handler: function(){
						 	var parametros;
						 	
							if(_UPDATE_MODE){
								parametros = { 
								 				'beneficio.folio' : BENEFICIO.folio,
								 				'beneficio.fecha' : BENEFICIO.fecha,
								 				'beneficio.poliza' : BENEFICIO.poliza,
								 				'beneficio.polizaExt' : BENEFICIO.polizaExt,
								 				'beneficio.inciso' : BENEFICIO.inciso,
								 				'beneficio.cdAseguradora' : BENEFICIO.cdaseguradora,
								 				'beneficio.dsAseguradora' : BENEFICIO.aseguradora,
								 				'beneficio.cdRamo' : BENEFICIO.cdramo,
								 				'beneficio.dsRamo' : BENEFICIO.dsramo,
								 				'beneficio.asegurado' : BENEFICIO.asegurado,
								 				'beneficio.titular' : BENEFICIO.titular,
								 				'beneficio.empresa' : BENEFICIO.empresa,
								 				'beneficio.cdUnieco': BENEFICIO.cdunieco,
								 				'beneficio.noSiniestroAseg' : BENEFICIO.noSiniestroAseg,
								 				'beneficio.cdTipoTramite' : BENEFICIO.cdTipoTramite,
								 				'beneficio.reportadoPor' : BENEFICIO.reportadoPor,
								 				'tipoOperacion' : 'U'
								 			};
							}else{
								parametros = { 
								 				'beneficio.folio' : BENEFICIO.folio,
								 				'beneficio.fecha' : BENEFICIO.fecha,
								 				'beneficio.poliza' : BENEFICIO.poliza,
								 				'beneficio.polizaExt' : BENEFICIO.polizaExt,
								 				'beneficio.inciso' : BENEFICIO.inciso,
								 				'beneficio.cdAseguradora' : BENEFICIO.cdaseguradora,
								 				'beneficio.dsAseguradora' : BENEFICIO.aseguradora,
								 				'beneficio.cdRamo' : BENEFICIO.cdramo,
								 				'beneficio.dsRamo' : BENEFICIO.dsramo,
								 				'beneficio.asegurado' : BENEFICIO.asegurado,
								 				'beneficio.titular' : BENEFICIO.titular,
								 				'beneficio.empresa' : BENEFICIO.empresa,
								 				'beneficio.cdUnieco': BENEFICIO.cdunieco,
								 				'tipoOperacion' : 'I'
								 			};
							}
						 	if(formPresiniestros.form.isValid()){
							 	formPresiniestros.form.submit({
							 			params: parametros,
						            	waitTitle:'Espere',
						            	waitMsg:'Guardando Pre Siniestro...',
						            	failure: function(form, action) {
						            		var mensajeRespuesta = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						            		Ext.MessageBox.alert('Error', Ext.isEmpty(mensajeRespuesta) ? 'Error al guardar Pre Siniestro' : mensajeRespuesta);
										},
										success: function(form, action) {
									    	var mensajeRespuesta = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
											Ext.MessageBox.alert('Status', Ext.isEmpty(mensajeRespuesta) ? 'El Pre Siniestro se ha guardado con &eacute;xito' : mensajeRespuesta, function () { window.location=_ACTION_IR_CONSULTA_POLIZAS_PRESINIESTROS; });
										}
				        		});
						 	}else{
								Ext.MessageBox.alert('Aviso', 'Favor de llenar campos requeridos.');
							}      
						 }
			      		},
			      		{
					     text:getLabelFromMap('',helpMap,'Cancelar'),
					     tooltip: getToolTipFromMap('',helpMap,'Cancela todos los cambios realizados'),
						 handler: function(){
						 	 window.location=_ACTION_IR_CONSULTA_POLIZAS_PRESINIESTROS; 
						 	}
						 },
			      		{
					     text:getLabelFromMap('',helpMap,'Regresar'),
					     tooltip: getToolTipFromMap('',helpMap,'Regresar a la Consulta de P&oacute;lizas'),
						 handler: function(){
						 	 window.location=_ACTION_IR_CONSULTA_POLIZAS_PRESINIESTROS; 
						 }
			      		}
			      		]
			   	}]
			}]
	});
		

	formPresiniestros.render();

});