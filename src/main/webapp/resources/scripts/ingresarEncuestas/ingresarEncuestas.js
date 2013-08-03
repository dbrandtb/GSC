	function recargarForm (cdPregunta, cdSecuencia, otValor) {
		var cdEncuesta = Ext.getCmp('encuestaEncId').getValue();
		var conn = new Ext.data.Connection ();
		conn.request ({
			url: _ACTION_BUSCAR_PREGUNTA_ENCUESTA_SIG + '?cdEncuesta='+cdEncuesta+'&cdPregunta='+cdPregunta+'&otValor='+otValor,
			method: 'POST',
			//timeout: _timeout,
			//params: _params,
			callback: function (options, success, response) {
							//alert(Ext.util.JSON.decode(response.responseText).success);
							if(Ext.util.JSON.decode(response.responseText).success){
								cdSecuencia=Ext.util.JSON.decode(response.responseText).cdSecuencia;
								Ext.getCmp('el_formDatosEncuestas').url=_ACTION_BUSCAR_PREGUNTA_ENCUESTA +'?cdEncuesta='+cdEncuesta+'&cdSecuencia='+cdSecuencia;
								Ext.getCmp('el_formDatosEncuestas').load();
							}
					}
		});
	}
	
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";	
	
/*	var CDUNIECO='52';
	var CDRAMO='500';
	var NMPOLIZA='511';
	var ESTADO='M';
	var CDPERSON='3121';*/

	var CDSECUENCIA='';
	//FORMULARIO PRINCIPAL  con el encabezado solamente************************************
	var formPanel = new Ext.FormPanel({			
	        el:'encabezado',
	        id: 'encabezadoId',
	        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('827',helpMap,'Ingresar Encuesta')+'</span>',
	        //title: '<span style="color:black;font-size:12px;">Ingresar Encuesta</span>',
	        iconCls:'logo',
	        bodyStyle:'background: white',
	        labelAlign: "right",
	        frame:true,
	        width: 740,
			layout:'table',
			layoutConfig: {columns:3},
            defaults: {width:238},	        
	        autoHeight:true,
            items:[
					{
    					html: '<span class="x-form-item" style="font-weight:bold">Ingresar Encuesta</span>',
    					colspan:3
       				},
       				{            		
   						layout: 'form',
   						width:340,
   						height: 35,
   						items: [
   								{
		           					xtype: 'textfield',
   									id: 'nombreEncId',
			        				fieldLabel: getLabelFromMap('nombreEncId',helpMap,'Nombre'),
			        				tooltip:getToolTipFromMap('nombreEncId',helpMap,'Nombre Encuestado'), 	        
			        				name: 'nombre',
			        				value: NOMBRE,
			        				width: 220,
			        				disabled: true
			      				}
   								]
    				},
    				{
    					layout: 'form',
    					width:200,
    					items: [
		           				{
		           					xtype: 'textfield',
   									id: 'claveCatWebEncId',
			        				fieldLabel: getLabelFromMap('claveCatWebEncId',helpMap,'Clave Catweb'),
			        				tooltip:getToolTipFromMap('claveCatWebEncId',helpMap,'Clave Catweb'), 	        
			        				name: 'claveCatWeb',
			        				value: CDPERSONCLI,
			        				width: 80,
			        				disabled: true
			     				}
   								]
    				},
    				{
    					layout: 'form', 
    					width:200,
   						items: [
   								{
		           					xtype: 'textfield',
   									id: 'clienteCuentaEncId',
			        				fieldLabel: getLabelFromMap('clienteCuentaEncId',helpMap,'Cliente Cuenta'),
			        				tooltip:getToolTipFromMap('clienteCuentaEncId',helpMap,'Cliente Cuenta'), 	        
			        				name: 'clienteCuenta',
			        				value: CDELEMENTO,
			        				width: 80,
			        				disabled: true
							     }
   								]
    				},	
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 460,
	           			items: [
   								{
   									xtype:'textfield',
            						id: 'dsUniecoId',
							        fieldLabel: getLabelFromMap('dsUniecoId',helpMap,'Aseguradora'),
							        tooltip:getToolTipFromMap('dsUniecoId',helpMap,'Aseguradora'), 	        
							        name: 'dsUnieco',
			        				disabled: true,
			        				value: DSUNIECO,
			        				width: 320
			   		 			},
   								{
		           				   xtype: 'combo',
		           				   tpl: '<tpl for="."><div ext:qtip="{codAseguradora}.{desAseguradora}" class="x-combo-list-item">{desAseguradora}</div></tpl>',
			                       id:'aseguradoraEncId',
			                       fieldLabel: getLabelFromMap('aseguradoraEncId',helpMap,'Aseguradora'),
			           			   tooltip:getToolTipFromMap('aseguradoraEncId',helpMap,'Aseguradora'),
			                       store: storeAseguradoraEnc,
			                       displayField:'desAseguradora',
			                       valueField:'codAseguradora',
			                       hiddenName: 'codAseguradorah',
			                       typeAhead: true,
			                       mode: 'local',			                      
			                       triggerAction: 'all',
			                       width: 320,
			                       emptyText:'Seleccione la Aseguradora...',
			                       selectOnFocus:true,
			                       forceSelection:true,
			                       onSelect: function (record) {
	                                   this.setValue(record.get("codAseguradora"));
	                                   storeProductosEnc.removeAll();
	                                   storeProductosEnc.load({
	                                           params: {
	                                               pv_cdunieco_i: record.get("codAseguradora")
	                                           }
	                                       }
	
	                                   );
	                                   this.collapse();
                                   }
			   		 			}
	           						//desAseguradora	
			   		 			]
    				},	
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 460,
	           			items: [
   								{
   									xtype:'textfield',
            						id: 'dsRamoId',
							        fieldLabel: getLabelFromMap('txtfldproductoId',helpMap,'Producto'),
							        tooltip:getToolTipFromMap('txtfldproductoId',helpMap,'Producto'), 	        
							        name: 'dsRamo',
			        				disabled: true,
			        				value: DSRAMO,
			        				width: 320
			   		 			},
   								{
		           				   xtype: 'combo',
		           				   tpl: '<tpl for="."><div ext:qtip="{codProducto}.{desProducto}" class="x-combo-list-item">{desProducto}</div></tpl>',
			                       id:'productosEncId',
			                       fieldLabel: getLabelFromMap('productosEncId',helpMap,'Productos'),
			           			   tooltip:getToolTipFromMap('productosEncId',helpMap,'Productos'),
			                       store: storeProductosEnc,
			                       displayField:'desProducto',
			                       valueField:'codProducto',
			                       hiddenName: 'codProductoh',
			                       typeAhead: true,
			                       mode: 'local',
			                       triggerAction: 'all',
			                       width: 320,
			                       emptyText:'Seleccione el Producto...',
			                       selectOnFocus:true,
			                       forceSelection:true
			   		 			}
   								]
    				},	
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 460,
	            		height: 35,
	           			items: [
   								{
		           				   xtype: 'combo',
		           				   tpl: '<tpl for="."><div ext:qtip="{codModulo}.{desModulo}" class="x-combo-list-item">{desModulo}</div></tpl>',
			                       id:'moduloEncId',
			                       fieldLabel: getLabelFromMap('moduloEncId',helpMap,'Modulo'),
			           			   tooltip:getToolTipFromMap('moduloEncId',helpMap,'Modulo'),
			                       store: storeModuloEnc,
			                       displayField:'desModulo',
			                       valueField:'codModulo',
			                       hiddenName: 'codModuloh',
			                       typeAhead: true,
			                       mode: 'local',
			                       allowBlank: false,
			                       triggerAction: 'all',
			                       width: 320,
			                       emptyText:'Seleccione el Modulo...',
			                       selectOnFocus:true,
			                       forceSelection:true,
			                       onSelect: function (record) {
	                                   this.setValue(record.get("codModulo"));
	                                   Ext.getCmp('campanhaEncId').setValue('');
	                                   storeCampanhaEnc.removeAll();
	                                   Ext.getCmp('temaEncId').setValue('');
	                                   storeTemaEnc.removeAll();
	                                   Ext.getCmp('encuestaEncId').setValue('');	                                   
	                                   storeEncuestaEnc.removeAll();
	                                   storeCampanhaEnc.load({
	                                           params: {
	                                               cdUniEco: CDUNIECOCLI,
	                                               cdRamo: CDRAMOCLI,
	                                               cdModulo: record.get("codModulo")
	                                           }
	                                       }
	
	                                   );
	                                   this.collapse();
                                   }
			   		 			}			   		 			
			   		 			
			   		 			
   								]
    				},	
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 460,
	            		height: 35,
	           			items: [
   								{
		           				   xtype: 'combo',
		           				   tpl: '<tpl for="."><div ext:qtip="{codCampan}.{desCampan}" class="x-combo-list-item">{desCampan}</div></tpl>',
			                       id:'campanhaEncId',
			                       fieldLabel: getLabelFromMap('campanhaEncId',helpMap,'Campa&ntilde;a'),
			           			   tooltip:getToolTipFromMap('campanhaEncId',helpMap,'Campa&ntilde;a'),
			                       store: storeCampanhaEnc,
			                       displayField:'desCampan',
			                       valueField:'codCampan',
			                       hiddenName: 'codCampanh',
			                       typeAhead: true,
			                       mode: 'local',
			                       triggerAction: 'all',
			                       allowBlank: false,
			                       width: 320,
			                       emptyText:'Seleccione la Campaña...',
			                       selectOnFocus:true,
			                       forceSelection:true,
			                       onSelect: function (record) {
	                                   this.setValue(record.get('codCampan'));
	                                   Ext.getCmp('temaEncId').setValue('');
	                                   storeTemaEnc.removeAll();
	                                   Ext.getCmp('encuestaEncId').setValue('');	                                   
	                                   storeEncuestaEnc.removeAll();
	                                   storeTemaEnc.load({
	                                           params: {
	                                               cdUniEco: CDUNIECOCLI,
	                                               cdRamo: CDRAMOCLI,
	                                               cdModulo: Ext.getCmp('moduloEncId').getValue(),
	                                               cdCampan: record.get('codCampan')
	                                           }
	                                       }
	
	                                   );
	                                   this.collapse();
                                   }
			   		 			}
   								]
    				},
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 460,
	            		height: 35,
	           			items: [
   								{
		           				   xtype: 'combo',
		           				   tpl: '<tpl for="."><div ext:qtip="{numConfig}.{codTema}.{desTema}" class="x-combo-list-item">{desTema}</div></tpl>',
			                       id:'temaEncId',
			                       fieldLabel: getLabelFromMap('temaEncId',helpMap,'Tema'),
			           			   tooltip:getToolTipFromMap('temaEncId',helpMap,'Tema'),
			                       store: storeTemaEnc,
			                       displayField:'desTema',
			                       valueField:'codTema',
			                       hiddenName: 'codTemah',
			                       typeAhead: true,
			                       mode: 'local',
			                       triggerAction: 'all',
			                       width: 320,
			                       allowBlank: false,
			                       emptyText:'Seleccione El Tema...',
			                       selectOnFocus:true,
			                       forceSelection:true,
			                       onSelect: function (record) {
	                                   this.setValue(record.get('codTema'));
	                                   Ext.getCmp('numConfigId').setValue(record.get('numConfig'));
	                                   Ext.getCmp('encuestaEncId').setValue('');
	                                   storeEncuestaEnc.removeAll();
	                                   storeEncuestaEnc.load({
	                                           params: {
	                                               cdUniEco: CDUNIECOCLI,
	                                               cdRamo: CDRAMOCLI,
	                                               cdModulo: Ext.getCmp('moduloEncId').getValue(),
	                                               cdCampan: Ext.getCmp('campanhaEncId').getValue(),
	                                               cdProceso: record.get('codTema'),
	                                               nmConfig: Ext.getCmp('numConfigId').getValue()
	                                           }
	                                       }
	
	                                   );
	                                   this.collapse();
                                   }
			   		 			}
   								]
    				},
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 460,
	            		height: 35,
	           			items: [
   								{
		           				   xtype: 'combo',
		           				   tpl: '<tpl for="."><div ext:qtip="{codEncuesta}.{desEncuesta}" class="x-combo-list-item">{desEncuesta}</div></tpl>',
			                       id:'encuestaEncId',
			                       fieldLabel: getLabelFromMap('encuestaEncId',helpMap,'Encuesta'),
			           			   tooltip:getToolTipFromMap('encuestaEncId',helpMap,'Encuesta'),
			                       store: storeEncuestaEnc,
			                       displayField:'desEncuesta',
			                       valueField:'codEncuesta',
			                       hiddenName: 'codEncuestah',
			                       typeAhead: true,
			                       mode: 'local',
			                       triggerAction: 'all',
			                       width: 320,
			                       allowBlank: false,
			                       emptyText:'Seleccione La Encuesta...',
			                       selectOnFocus:true,
			                       forceSelection:true,
			                       onSelect: function (record) {
	                                   this.setValue(record.get('codEncuesta'));
	                                   var cdEncuesta=record.get('codEncuesta');
	                                   //Ext.getCmp('el_formDatosEncuestas').show();
	                                   //alert();
	                                   this.collapse();
	                                   el_formDatosEncuestas.removeAll();
	                                   el_formDatosEncuestas.url=_ACTION_BUSCAR_PREGUNTA_ENCUESTA +'?cdEncuesta='+cdEncuesta+'&cdSecuencia='+CDSECUENCIA;
	                                   el_formDatosEncuestas.load();
	                                   
                                   }
			   		 			}
   								]
    				},
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 460,
	            		height: 35,
	           			items: [
   								{
   									xtype:'textfield',
            						id: 'nmPolizaEncId',
							        fieldLabel: getLabelFromMap('nmPolizaEncId',helpMap,'Numero de Poliza'),
							        tooltip:getToolTipFromMap('nmPolizaEncId',helpMap,'Numero de Poliza'), 	        
							        name: 'nmPoliex',
			        				disabled: true,
			        				value: NMPOLIEX,
			        				width: 320
			   		 			}
   								]
    				},
    				{	
						colspan:3,
    					layout: 'form',  
	            		//labelWidth: 100,
	            		//width: 340,
	           			items: [
   								{
   									xtype:'hidden',
            						id: 'numConfigId',
							        name: '_numConfig'

			   		 			}
   								]
    				}
            	]												
	});
	
	
//FORMULARIO DONDE SE  MOSTRANRAN LOS ELEMENTOS DE LA ENCUESTA DEACUERDO AL COMBO Encuesta
	var el_formDatosEncuestas = new Ext.ux.MetaForm({
    	//el: 'atributosVariables',
    	id: 'el_formDatosEncuestas',
		labelAlign:'right',
        width : 740,
        height: 400,
        //bodyStyle: {position: 'relative', background: 'white'},
        //url: _ACTION_BUSCAR_PREGUNTA_ENCUESTA,
        removeComponentsOnLoad: false,
        successProperty: 'success',
        labelWidth: 200,
        autoScroll: true,
        bodyStyle:'background: white',
        defaults: {labelWidth: 120},
        renderTo: 'formDatosAdicionales',
         onMetaChange: function (form, meta) {
						        if (this.removeComponentsOnLoad == true) this.removeAll();
						        
								var fields = meta.fields;
								
								var doConfig = function(config){
									// handle plugins
						            if(config.plugins){
						            	var plugins = config.plugins;
						            	delete config.plugins;
						            	if(plugins instanceof Array){
						            		config.plugins = [];
						            		Ext.each(plugins, function(plugin){
						            			config.plugins.push( Ext.ComponentMgr.create(plugin) );
						            		});
						            	}else{
						            		config.plugins = Ext.ComponentMgr.create(plugins);
						            	}
						            }	            
									
									// handle regexps
						            if(config.regex) {
						                config.regex = new RegExp(config.regex)
						            }
						            
						            // to avoid checkbox misalignment
						            if('checkbox' === config.xtype) {
						                Ext.apply(config, {
						                     boxLabel:' ',
						                     checked: config.defaultValue
						                });
						            }
						            
						            Ext.apply(config, meta.fieldConfig || {});
									return config;
								};
								
								Ext.each(fields, function(field){		
									var oConfig = doConfig(field);			
									Ext.apply(oConfig, meta.formConfig || {});
									field.valor = field.value;
									field.value = "";
									field.hidden = eval(field.hidden);
									field.permitirBlancos = field.allowBlank;
									//field.allowBlank = field.allowBlank;
						
									if (field.store && field.store != "") {
										field._store = field.store;
										field.store = undefined;
									}
									try {
							        	this.add(field);
							        }catch (e) {
							        }	        
								}, this);
								this.doLayout();
								var _this = this;
								Ext.each(fields, function(campo) {
									if (campo._store && campo._store != "") {
										Ext.getCmp(campo.id).store = eval(campo._store);
									}
									Ext.getCmp(campo.id).setValue(campo.valor);
									if (campo.xtype == "combo") {
										Ext.getCmp(campo.id).on('focus', function() {});
									}
									if(campo.onChange!=null && campo.onChange!= undefined && campo.onChange!="")
									{
										Ext.getCmp(campo.id).on('change', function() {eval(campo.onChange)});
									}
							/*		
								if(campo.onSelect!=null && campo.onSelect!= undefined && campo.onSelect!="")
					{
				//var texto=eval(campo.onChange.substring(1, campo.onChange.length - 1));
						Ext.getCmp(campo.id).on('select', function() {eval(campo.onSelect)});
					}*/
								});
	            					Ext.each(el_formDatosEncuestas.items.items,function (campito) {
      									if (campito.permitirBlancos == "false" || campito.permitirBlancos == "" ||campito.permitirBlancos == "S" || campito.permitirBlancos === "false" || campito.permitirBlancos === false || campito.permitirBlancos === "S") {
      										campito.allowBlank = false;
      									} else {
       										campito.allowBlank = true;
      									}
      									    if (campito.xtype == "combo") {
                  var _onSelect = campito.onSelect;
                  campito.onSelect = function (record) {
                   this.setValue(record.get(campito.valueField));
                   this.collapse();
                   eval(_onSelect);
                  }
                }
      									
     					  			});
	            }
        
	});            
	   

		
	var formBoton = new Ext.FormPanel({			
	        renderTo:'formBotones',
	        id: 'frmBotonesId',	        
	        iconCls:'logo',
	        border: false,
	        bodyStyle:'background: white',
	        labelAlign: "right",
	        frame:true,
	        width: 740,
            layoutConfig: { columns: 3, columnWidth: .33},
			items: [
					{
					width: 720,
             		height: 40,
             		buttonAlign: 'center',  
             		border: false,
			 		buttons:[
				 				{
	    						text:getLabelFromMap('txtBtnGuardarId',helpMap,'Guardar'),
	    				 		tooltip: getToolTipFromMap('txtBtnGuardarId',helpMap,'Guarda Encuesta'),
	    				  		handler:function(){
	    				  				var vl=sonValidos();
		    				  			//alert(vl);
		    				  			//if (Ext.getCmp('moduloEncId').isValid()) {
	    				  			    if (el_formDatosEncuestas.isValid() && (Ext.getCmp('encuestaEncId').getValue()!="") && vl) {
		            						guardarPregunta();
						            	}else {
									        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
						            	}
	   					 			}
	    						},
	    						{
	    				 		text:getLabelFromMap('txtBtnExportarId',helpMap,'Exportar'),
	    				 		tooltip: getToolTipFromMap('txtBtnExportarId',helpMap,'Exporta Encuesta')                              
	    					
	    						},
	    						{
	    						id:'txtBtnRegresarId',
	    				 		text:getLabelFromMap('txtBtnRegresarId',helpMap,'Regresar'),
	    				 		tooltip: getToolTipFromMap('txtBtnregresarId',helpMap,'Regresar'),
	    				 		handler : function(){
	    				 				window.location = _IR_CONSULTA_CLIENTES;
	    				 			}                             
	    					   	}
    			  	 		]
					}			
			]            
         });
	

			        
	formPanel.render();
	formBoton.render();
	if(CDUNIECOCLI!=""){
		Ext.getCmp('dsUniecoId').setVisible(true);
		Ext.getCmp('aseguradoraEncId').setVisible(false);
		Ext.getCmp('aseguradoraEncId').setFieldLabel('');

		Ext.getCmp('dsRamoId').setVisible(true);
		Ext.getCmp('productosEncId').setVisible(false);
		Ext.getCmp('productosEncId').setFieldLabel('');
	
		Ext.getCmp('txtBtnRegresarId').setVisible(true);
		
	}
	else
	{
		Ext.getCmp('dsUniecoId').setVisible(false);
		Ext.getCmp('dsUniecoId').setFieldLabel('');
		Ext.getCmp('aseguradoraEncId').setVisible(true);
		
		
		Ext.getCmp('dsRamoId').setVisible(false);
		Ext.getCmp('dsRamoId').setFieldLabel('');
		Ext.getCmp('productosEncId').setVisible(true);
		
		Ext.getCmp('txtBtnRegresarId').setVisible(false);



		/*
		var desAseguradora = new Ext.form.ComboBox({
			tpl: '<tpl for="."><div ext:qtip="{codAseguradora}.{desAseguradora}" class="x-combo-list-item">{desAseguradora}</div></tpl>',
			id:'aseguradoraEncId',
			fieldLabel: getLabelFromMap('aseguradoraEncId',helpMap,'Aseguradora'),
			tooltip:getToolTipFromMap('aseguradoraEncId',helpMap,'Aseguradora'),
			store: storeAseguradoraEnc,
			displayField:'desAseguradora',
			valueField:'codAseguradora',
			hiddenName: 'codAseguradorah',
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			width: 200,
			emptyText:'Seleccione la Aseguradora...',
			selectOnFocus:true,
			forceSelection:true,
			onSelect: function (record) {
			   this.setValue(record.get("codAseguradora"));
			   storeProductosEnc.removeAll();
			   storeProductosEnc.load({
			           params: {
			               pv_cdunieco_i: record.get("codAseguradora")
			               }
			           }
			
			       );
			       this.collapse();
			   }
		});
		
    	var desProducto = new Ext.form.ComboBox({
			tpl: '<tpl for="."><div ext:qtip="{codProducto}.{desProducto}" class="x-combo-list-item">{desProducto}</div></tpl>',
			id:'productosEncId',
			fieldLabel: getLabelFromMap('productosEncId',helpMap,'Productos'),
			tooltip:getToolTipFromMap('productosEncId',helpMap,'Productos'),
			store: storeProductosEnc,
			displayField:'desProducto',
			valueField:'codProducto',
			hiddenName: 'codProductoh',
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			width: 200,
			emptyText:'Seleccione el Producto...',
			selectOnFocus:true,
			forceSelection:true
    	});
    	
    	
    	
    	
    	
		storeAseguradoraEnc.load({
			callback: function(r, opt, success) {
					if (success) {
						Ext.getCmp('aseguradoraEncId').setValue(CDUNIECOCLI);
						Ext.getCmp('aseguradoraEncId').setDisabled(true);
					}
					storeProductosEnc.load({
							params: {pv_cdunieco_i: CDUNIECOCLI},
							callback: function(r, opt, success) {
								if (success) {
									Ext.getCmp('productosEncId').setValue(CDRAMOCLI);
									Ext.getCmp('productosEncId').setDisabled(true);
								}
							}	
						});
				}
		});*/
		storeAseguradoraEnc.load();
	}
  
	storeModuloEnc.load({
			params: {
					cdUniEco: CDUNIECOCLI, 
					cdRamo: CDRAMOCLI
					}/*,
			callback: function (r, options, success) {
					if (success) {
						Ext.getCmp('el_formDatosEncuestas').hide();
						}
					}*/
	});

	function guardarPregunta(){
		var blanquito=0;
		var cantidad=0;
		var llenito=0;
		var obliga=false;
		_params= "nmConfig="+Ext.getCmp('numConfigId').getValue()+"&"+
				"cdUniEco="+CDUNIECOCLI+"&"+
				"cdRamo="+CDRAMOCLI+"&"+
				"estado="+CDESTADOCLI+"&"+
				"nmPoliza="+CDNMPOLIZACLI+"&"+
				"cdPerson="+CDPERSONCLI+"&"+
				"cdEncuesta="+Ext.getCmp('encuestaEncId').getValue();
		var i = 0;
		
		Ext.each(el_formDatosEncuestas.items.items, function(campito){
			//console.log(campito.allowBlank);
			if (campito.name!=undefined){
				//console.log(campito.name);
				_params+="&respuesEnc[" + i + "].id="+campito.name+"&respuesEnc[" + i + "].texto="+campito.getValue();
			i++;
			}
			if (campito.allowBlank==true){
					blanquito++
				if(campito.getValue()==""){
					llenito++
				}
			}
			else
			{
				obliga=true;
			}
			   
			
		});
		//alert(blanquito);
		//alert(llenito);
		cantidad=blanquito-llenito;
		if ((cantidad == 0)&& (obliga!=true))
		
		//else
		{
			//alert(2);
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('4000110', helpMap,'Debe responder al menos una pregunta relacionada con la Encuesta.'));
		}
		else
		{
			//alert(1);
			execConnection(_ACTION_GUARDAR_RESPUESTAS, _params, cbkGuardarPregunta);
		}
	}
	function cbkGuardarPregunta (_success, _message) {
		if (!_success) {
			Ext.Msg.alert('Error', _message);
		}else {
			Ext.Msg.alert('Aviso', _message);
		}
	}
	
	function sonValidos()
	{
		bnd=true
		bnd=bnd&Ext.getCmp('moduloEncId').isValid();
		bnd=bnd&Ext.getCmp('campanhaEncId').isValid();
		bnd=bnd&Ext.getCmp('temaEncId').isValid();
		bnd=bnd&Ext.getCmp('encuestaEncId').isValid();
		return bnd;
	}
})
		
	
	
	