Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";	
	//alert(_NMFAX);		
	
		
	var lasFuncionCodigo=new Ext.data.Record.create([
  	{name: 'cdusuari',  mapping:'cdusuari',  type: 'string'}       
	]);
	var readerFuncionCodigo= new Ext.data.JsonReader(
  	{
   		root:'MOperacionCatList',
   		totalProperty: 'totalCount',
   		successProperty : '@success'
  	},
 		lasFuncionCodigo
	);
	

	storeFuncionCodigo = new Ext.data.Store({
   	proxy: new Ext.data.HttpProxy({
         url: _ACTION_OBTENER_FUNCION_CODIGO
        }),
   	reader: readerFuncionCodigo
	});
	
	var lasFuncionNombret=new Ext.data.Record.create([
  	{name: 'funcionNombre',  mapping:'funcionNombre',  type: 'string'}       
	]);
	var readerFuncionNombre= new Ext.data.JsonReader(
  	{
   		root:'MOperacionCatList',
   		totalProperty: 'totalCount',
   		successProperty : '@success'
  	},
 		lasFuncionNombret
	);

	storeFuncionNombre = new Ext.data.Store({
   	proxy: new Ext.data.HttpProxy({
         url: _ACTION_OBTENER_FUNCION_NOMBRE
        }),
   	reader: readerFuncionNombre
	});
	
	var panelUpload = new Ext.ux.UploadPanel({
		//anchor:'30%',
		width: 400,
		autoHeight:true,
		labelAlign: 'center',
		labelButton: 'center',
		//height: 300,
		fieldLabel:'Upload Panel',
		hidden:true,
		frame: true,
		buttonAlign: 'center',
		//url:
		addText:'Agregar un archivo de fax',
		clickRemoveText:'Click para eliminar de la lista',
		clickStopText:'Click para detener',
		emptyText:'Carga de Archivo',
		errorText:'Error',
		fileDoneText:'Archivo <b>{0}</b> ha sido cargado exitosamente',
		fileFailedText:'No se pudo cargar el archivo <b>{0}</b>',
		fileQueuedText:'Esperando a cargar el archivo <b>{0}</b>',
		fileStoppedText:'Archivo <b>{0}</b> detenido por el usuario',
		fileUploadingText:'Cargando archivo <b>{0}</b>',
		removeAllText:'Eliminar todo',
		removeText:'Eliminar',
		stopAllText:'Detener todo',
		uploadText:'Cargar',
		singleUpload: true,
		maxFileCount: 1,
		hideUploadButton: true
	});
	
	
	//FORMULARIO PRINCIPAL ************************************
	var formPanel = new Ext.FormPanel({			
	        renderTo:'encabezadoFijo',
	        //el:'encabezadoFijo',
	        id: 'acFormPanelId',
	        name: 'acFormPanelId',
	        //title: '<span style="color:black;font-size:12px;">Administraci&oacute;n de Fax</span>',
	        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('acFormPanelId', helpMap,'Administraci&oacute;n de Fax Agregar')+'</span>',
	        iconCls:'logo',
	        //store:storePersonaCaso,
	        bodyStyle:'background: white',
	        //buttonAlign: "center",
	        labelAlign: "right",
	        frame:true,
	        width: 700,
	        autoHeight:true,
	        
	        //layout: 'table',
            //layoutConfig: { columns: 3, columnWidth: .33},
            
            items:[
            //{layout:'table',columns: 1, columnWidth: .99,//itmes general
            
            
          	{
            		layout: 'table',
            		layoutConfig: { columns: 3, columnWidth: .33},
            		labelAlign: 'right',
            		items:[
            		{
            		html: '<span class="x-form-item" style="font-weight:bold">Datos del Fax</span>',
            		colspan:4
            		},
            		{            		
           			layout: 'form',
           			labelWidth: 110,           			            		
           			items: [
           				
           				{
           					xtype: 'textfield',
           					id: 'texNmCasoId',
					        fieldLabel: getLabelFromMap('texNmCasoId',helpMap,'N&uacute;mero de Caso'),
					        tooltip:getToolTipFromMap('texNmCasoId',helpMap,'N&uacute;mero de Caso'),
					        hasHelpIcon:getHelpIconFromMap('texNmCasoId',helpMap),
		                    Ayuda: getHelpTextFromMap('texNmCasoId',helpMap),
					        name: 'nmcaso',
					        id:'texNmCasoId',
					        width: 120,
					        listeners: {
					        		blur: function () {
                 						validar();
                 					}
                 			}
					      }
           				]
            		},
            		{
            		layout: 'form',
            		items: [
           				{
           					/*xtype: 'textfield',
           					id: 'texNmFaxIdTex',
					        fieldLabel: getLabelFromMap('texNmCasoId',helpMap,'N&uacute;mero de Fax'),
					        tooltip:getToolTipFromMap('texNmCasoId',helpMap,'N&uacute;mero de Fax'), 	        
					        name: 'nmfax',
					        width: 120*/
           					xtype:'hidden',
		            		id: 'texNmFaxIdTex',
		            		name: 'nmfax'
					        
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 120,
            		colspan:4,          			
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{cdtipoar}. {dsarchivo}" class="x-combo-list-item">{dsarchivo}</div></tpl>',
	                       id:'cmbTipoArchivoId',
	                       fieldLabel: getLabelFromMap('cmbTipoArchivoId',helpMap,'Tipo de Archivo'),
	           			   tooltip:getToolTipFromMap('cmbTipoArchivoId',helpMap,'Tipo de Archivo'),
	           			    hasHelpIcon:getHelpIconFromMap('cmbTipoArchivoId',helpMap),
		                    Ayuda: getHelpTextFromMap('cmbTipoArchivoId',helpMap),
	                       store: storeTipoFax,
	                       displayField:'dsarchivo',
	                       valueField:'cdtipoar',
	                       hiddenName: 'cmbTipoArchivo',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 210,
	                       //allowBlank: false,
	                       emptyText:'Seleccione Tipo Archivo...',
	                       selectOnFocus:true,
	                       forceSelection:true,
	                       onSelect: function(record){
		                       this.setValue(record.get("cdtipoar"));
		                       this.collapse();
		                        //comenzarMask(formPanel.id, getLabelFromMap('400070',helpMap,'Cargando datos ...'));
		                        startMaskLocal(formPanel.id, getLabelFromMap('400070',helpMap,'Cargando datos ...'));
								window.frames["atributosVariablesFax"].location.href = _ACTION_OBTENER_ATRIBUTOS_VARIABLES+"?cdtipoar="+record.get("cdtipoar");//+"&cdseccion="+"&limit=500";
		        			    //console.log(window.frames["atributosVariables"].Ext.getCmp('dinamicFormPanelId')); 
	                       }
					     }
           				]
            		}
            		]},
            		
            		
            		//fin fila uno
            		
                  {
            		layout: 'table',
            		layoutConfig: { columns: 4, columnWidth: .25},
            		labelAlign: 'right',
            		items:[
            		{	
            		
            		layout: 'form',  
            		labelWidth: 110,
            		//colspan:1,          			
           			items: [
           				{
           					xtype:'textfield',
           					//xtype:'datefield',
		            		id: 'feingreso',
		            		//value:'10/12/2008',
					        fieldLabel: getLabelFromMap('feingreso',helpMap,'Fecha de Registro'),
					        tooltip:getToolTipFromMap('feingreso',helpMap,'Fecha de Registro'), 	        
					         hasHelpIcon:getHelpIconFromMap('feingreso',helpMap),
		                    Ayuda: getHelpTextFromMap('feingreso',helpMap),
					        name: 'feingresoNm',
					        disabled: true,
					       // format: 'd/m/Y',
					        value: feInicio(),
					        //date:'d/m/Y',
					        //altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
					        width: 80
					   		 }
           				]
            		},										
            		{
            		layout: 'form',  
            		labelWidth: 100,          			
           			items: [
           				
           					{
           					xtype:'datefield',
		            		id: 'ferecepcion',
					        fieldLabel: getLabelFromMap('ferecepcion',helpMap,'Fecha de Recepci&oacute;n'),
					        tooltip:getToolTipFromMap('ferecepcion',helpMap,'Fecha de Recepci&oacute;n'),
					        hasHelpIcon:getHelpIconFromMap('ferecepcion',helpMap),
		                    Ayuda: getHelpTextFromMap('ferecepcion',helpMap),
					        name: 'ferecepcion',
					        //disabled: true,
					        format: 'd/m/Y',
					        altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
					        width: 100
					   			     
					     }
           				]
            		},{
            		layout: 'form',
            		labelWidth: 60,
            		items: [
           				  {
           					xtype:'textfield',
		            		id: 'texPolizaId',
					        fieldLabel: getLabelFromMap('texPolizaId',helpMap,'P&oacute;liza'),
					        tooltip:getToolTipFromMap('texPolizaId',helpMap,'P&oacute;liza'),
					        hasHelpIcon:getHelpIconFromMap('texPolizaId',helpMap),
		                    Ayuda: getHelpTextFromMap('texPolizaId',helpMap),
					        name: 'nmpoliex',
					       // disabled: true,
					        width: 120
					     }
           				]
            		},
            		{
            		layout: 'form',   
            		labelWidth: 70, 
            		items: [
           				{
           				xtype: 'button',
   						id: 'btnPoliza',
				        text: getLabelFromMap('btnPoliza',helpMap,'Ver P&oacute;liza'),
				        tooltip:getToolTipFromMap('btnPoliza',helpMap,'Ver P&oacute;liza'),
				        onClick:function(){
				        		obtenerPolizas(Ext.getCmp("texNmCasoId").getValue());        		//se agrego parametro
				        }
				        
				       }
           				]												
					}
					]},
			// fin columna dos		
            		
                  {
            		layout: 'table',
            		layoutConfig: { columns: 3, columnWidth: .33},
            		labelAlign: 'right',
            		items:[	
            		{            		
           			layout: 'form',
           			width:120
           			},{
           			layout: 'form',
           			labelAlign: 'right',
           			labelWidth: 50,
           			//colspan:1,           			            		
           			items: [
           				{ xtype:'textfield',
           				id: 'funcionCodigoId',
           				
           				fieldLabel: getLabelFromMap('funcionCodigoId',helpMap,'Usuario'),
					    tooltip:getToolTipFromMap('funcionCodigoId',helpMap,'Usuario'),
					     hasHelpIcon:getHelpIconFromMap('funcionCodigoId',helpMap),
		                    Ayuda: getHelpTextFromMap('funcionCodigoId',helpMap),
					    
					    name: 'cdusuario',
					    disabled: true,
					    //readOnly: true,
					    width: 125
           				}
           				]
           				},
           				{
           				layout: 'form',
           			    labelWidth: 15,
           			    labelAlign: 'left',
           			    colspan:4,           			            		
           			    items: [
           			      {
           					
        				     xtype: 'textfield', 
        				     labelSeparator: '', 
        				     width: 200,
        				     //readOnly: true,
        				     disabled: true,
        				     name: 'funcionNombre',
        				     id:'funcionNombreId'/*, 
        				      value: txt +' - '+ CODIGO_USUARIO*/
        				     
           					
           					/*xtype: 'textfield',
           					id: 'texUsuarioId',
           					labelSeparator:'',
                            name: 'dsusuari',
					       // disabled: true,
					        width: 200*/
					       }
           				  ]
            			}
            			]},
            		
            	// fin columna tres	
           
          {layout: 'table',
            		layoutConfig: { columns: 3, columnWidth: .33},
            		labelAlign: 'center',
            		buttonAlign: 'center',
            		
            		items:[{
            				layout: 'form',
            				width: 120
            			},
            			{
            				layout: 'form',
            				colspan:2,
            				items:[panelUpload]
            			},
            			{
            				layout: 'form'
            			}]
          }          	
	    
	    ]//fin item general
	});

		
	var formBoton = new Ext.FormPanel({			
	        //el:'formBotones',
			renderTo:'formBotones',
	        id: 'acFormBotonId',	        
	        //title: '<span style="color:black;font-size:12px;">Administraci&oacute;n de Fax</span>',
	        iconCls:'logo',
	        //store:storeAdministracionFax,
	        border: false,
	        bodyStyle:'background: white',
	        //buttonAlign: "center",
	        labelAlign: "right",
	        frame:true,
	        width: 700,
	        buttonAlign: "center",
	        autoHeight:true,
	        layout: 'table',
            layoutConfig: { columns: 3, columnWidth: .33},
            items:[{
               xtype: 'hidden',
               name : 'codigo',
               id: 'codigoId'
            }],
            buttonAlign: "center",
            buttons:[{
            		id:'txtBtnGuardarId',
    				 text:getLabelFromMap('txtBtnGuardarId',helpMap,'Guardar'),
    				 tooltip: getToolTipFromMap('txtBtnGuardarId',helpMap,'Guarda Faxes'),
    				 handler: function(){
                		if (formPanel.form.isValid()) {
                			//startMask(Ext.getBody, getLabelFromMap('400027', helpMap,'Guardando datos...'));
                			var _files = panelUpload.store.queryBy(function(r){return 'done' !== r.get('state');});
                	 		if (_files.getCount()) { 
                	 			var  _params = "";
                	 				_params += "cmd=upload&" ;
	                				_params += "&MFaxListVO[0].nmcaso=" + ((Ext.getCmp("texNmCasoId").getValue() != undefined)?Ext.getCmp("texNmCasoId").getValue():"");
									_params += "&MFaxListVO[0].nmfax=" +  ((Ext.getCmp("texNmFaxIdTex").getValue() != undefined)?Ext.getCmp("texNmFaxIdTex").getValue():"");
		 							_params += "&MFaxListVO[0].cdtipoar=" + Ext.getCmp("cmbTipoArchivoId").getValue(); //((Ext.getCmp("cmbTipoArchivoId").getValue() != undefined)?Ext.getCmp("cmbTipoArchivoId").getValue():"");
		 							_params += "&MFaxListVO[0].feingreso=" + Ext.getCmp("feingreso").getRawValue();//feInicioCambio(Ext.getCmp("feingreso").getValue());// != undefined)?Ext.getCmp("feingreso").getValue():"");
		 							_params += "&MFaxListVO[0].ferecepcion=" + Ext.getCmp("ferecepcion").getRawValue();//feInicioCambio(Ext.getCmp("ferecepcion").getValue());// != undefined)?Ext.getCmp("ferecepcion").getValue():"");
		 							_params += "&MFaxListVO[0].nmpoliex=" + ((Ext.getCmp("texPolizaId").getValue() != undefined)?Ext.getCmp("texPolizaId").getValue():"");
		 							_params += "&MFaxListVO[0].cdusuari=" + ((Ext.getCmp("funcionCodigoId").getValue() != undefined)?Ext.getCmp("funcionCodigoId").getValue():"");
		 							
                	 		 
		 					
		 					/*"nmcaso=" + formPanel.form.findField('texNmCasoId').getValue() + "&" +
	                					"nmfax=" + formPanel.form.findField('texNmFaxIdTex').getValue() + "&" +
	                					"cdtipoar=" + formPanel.form.findField('cmbTipoArchivoId').getValue() + "&" +
	                					"feingreso=" + formPanel.form.findField('feingreso').getValue() + "&" +
	                					"ferecepcion=" + formPanel.form.findField('ferecepcion').getValue() + "&" +
                	 					"nmpoliex=" + formPanel.form.findField('texPolizaId').getValue() + "&" +
                	 					"cdusuari=" + formPanel.form.findField('funcionCodigoId').getValue();*/
                	 				 _params = obtieneStringDatosVariables(_params,Ext.getCmp("cmbTipoArchivoId").getValue());			
							 
									 panelUpload.setUrl (_ACTION_AGREGAR_ARCHIVO + "?" + _params);
									 Ext.getCmp('txtBtnGuardarId').setDisabled(true);
									 startMask(formPanel.id, getLabelFromMap('400027', helpMap,'Guardando datos...'));
	                	   	     	 panelUpload.uploadCallback = function (_success) {
									  		  if (_success) {
							           			//  ventana.close();
													endMask();									  		  	
									  		  		Ext.getCmp('txtBtnGuardarId').setDisabled(true);
									  		  		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400068', helpMap,'Los datos se guardaron con &eacute,xito'));
									  		  		//window.location = _IR_A_CONSULTA_FAX;
									  		  		
							    				  }else{Ext.getCmp('txtBtnGuardarId').setDisabled(false);}
						      					 }
						     				  panelUpload.onUpload();
								} else { //alert("no hay archivos para subir");
										Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un archivo a cargar para realizar esta operaci&oacute;n'));
								}
                 
								                                                         
								
                			} else {
                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                  }
                 }
				},/*{
    				 text:getLabelFromMap('txtBtnExportarId',helpMap,'Exportar'),
    				 tooltip: getToolTipFromMap('txtBtnExportarId',helpMap,'Exporta Faxes')                              
    					
    				},*/{
    				/* text:getLabelFromMap('txtBtnRegresarId',helpMap,'Regresar'),
    				 tooltip: getToolTipFromMap('txtBtnRegresarId',helpMap,'Regresar'),
    				 handler : function(){
    				//  if(_NMCASO){
    				 	if (_NMCASO){
    				 		//alert(1);
    				    
    				 		window.location=_ACTION_REGRESAR_A_CONSULTAR_CASO_DETALLE+'?nmcaso='+_NMCASO+'&cdmatriz='+CDMATRIZ+'&cdperson='+CDPERSON+'&cdformatoorden='+CDFORMATOORDEN;
    				      }	
    				 	    				 	
    				      else  { window.location = _IR_A_CONSULTA_FAX;}
    				       }     
    				       
    				       */
    					
    					
    					
    					id:'txtBtnRegresarId',
					text:getLabelFromMap('txtBtnRegresarId',helpMap,'Regresar'),
    				 tooltip: getToolTipFromMap('txtBtnRegresarId',helpMap,'Regresar'),		        			
                   handler:function(){
                   		//alert(FLAG);
                      if (FLAG==0)
                      {
                      	window.location=_ACTION_REGRESAR_A_CONSULTAR_CASO_DETALLE+'?nmcaso='+_NMCASO+'&cdmatriz='+_CDMATRIZ+'&cdperson='+_CDPERSON+'&cdformatoorden='+_CDFORMATOORDEN+'&limit=999'+'&edit=0';
                   	  }
                   	  else 
                   		       {
                   		         window.location=_IR_A_CONSULTA_FAX;
                   		       }
                   }
               }
    					
    				 		
    				
    				
    				 /*{
                  text:getLabelFromMap('gridBtn5',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridBtn5',helpMap,'Regresa a la pantalla de consulta de detalle de caso'),
                  handler:function(){window.location=_ACTION_VOLVER_A_CONSULTA_CASO_DETALLE+'?nmcaso='+NMCASO+'&cdmatriz='+CDMATRIZ+'&cdperson='+CDPERSON+'&cdformatoorden='+CDFORMATOORDEN+'&limit=999'+'&edit=0'}
                  }*/
    				 
    				 
    			   ]
            
    			   
         });
	

			        
	formPanel.render();
	formBoton.render();
	storeTipoFax.load();
	
	function guardarDatosFax(_cdfo){		
		var  _params = "";
		
		//Datos del encabezado	
		//alert(Ext.getCmp("ferecepcion").getValue());	
		alert(feInicioCambio(Ext.getCmp("ferecepcion").getValue()));
		 _params += "&MFaxListVO[0].nmcaso=" + ((Ext.getCmp("texNmCasoId").getValue() != undefined)?Ext.getCmp("texNmCasoId").getValue():"");
		 //La variable CDUSUARIO es temporal
		 _params += "&MFaxListVO[0].nmfax=" +  ((Ext.getCmp("texNmFaxIdTex").getValue() != undefined)?Ext.getCmp("texNmFaxIdTex").getValue():"");
		 _params += "&MFaxListVO[0].cdtipoar=" + _cdfo; //((Ext.getCmp("cmbTipoArchivoId").getValue() != undefined)?Ext.getCmp("cmbTipoArchivoId").getValue():"");
		 _params += "&MFaxListVO[0].feingreso=" + ((Ext.getCmp("feingreso").getValue() != undefined)?Ext.getCmp("feingreso").getValue():"");
		 _params += "&MFaxListVO[0].ferecepcion=" + ((Ext.getCmp("ferecepcion").getValue()!= undefined)?Ext.getCmp("ferecepcion").getValue():"");//feInicioCambio(Ext.getCmp("ferecepcion").getValue());// != undefined)?Ext.getCmp("ferecepcion").getValue():"");
		 _params += "&MFaxListVO[0].nmpoliex=" + ((Ext.getCmp("texPolizaId").getValue() != undefined)?Ext.getCmp("texPolizaId").getValue():"");
		 _params += "&MFaxListVO[0].cdusuari=" + ((Ext.getCmp("funcionCodigoId").getValue() != undefined)?Ext.getCmp("funcionCodigoId").getValue():"");
		 _params += "&MFaxListVO[0].blarchivo=" + ((Ext.getCmp("texArchivoId").getValue() != undefined)?Ext.getCmp("texArchivoId").getValue():"");
		 
    //_params += "&strUsuariosSeg=" + strUsuariosSeg;
	  
	  _params = obtieneStringDatosVariables(_params,_cdfo);
	
	 //startMask(formPanel.id,"Guardando datos...");
	 execConnection(_ACTION_GUARDAR_NUEVO_CASO_FAX, _params, cbkGuardarNuevoCaso);		
	}
	
	function obtieneStringDatosVariables(_params, _cdfo){
		var i = 0;
		
		// alert(campito.nmcaso);
		var _form = window.frames["atributosVariablesFax"].Ext.getCmp('dinamicFormPanelId');
	
	if (_form.getForm().items.items) {
	
		Ext.each(_form.getForm().items.items, function(campito){
		//alert(_cdfo);
		//alert(campito.cdatribu);
			 _params += "&listaFaxesVO["+i+"].nmcaso="+ Ext.getCmp("texNmCasoId").getValue();
			 _params += "&listaFaxesVO["+i+"].nmfax="+ Ext.getCmp("texNmFaxIdTex").getValue();
			 _params += "&listaFaxesVO["+i+"].cdtipoar="+ _cdfo;
			 _params += "&listaFaxesVO["+i+"].cdAtribu="+ campito.cdAtribu;
			 _params += "&listaFaxesVO["+i+"].otvalor="+ ((campito.getValue() != undefined && campito.getValue() != null)?campito.getValue():"");
			i++;
		});
		return _params;
	}
	
	
	}
	
	function cbkGuardarNuevoCaso (_success, _message, _response) {
		//endMask();
		if (!_success) {
			Ext.Msg.alert('Error', _message);
		}else {
			Ext.Msg.alert('Aviso', _message+'. N&uacute;mero '+Ext.util.JSON.decode(_response).nmcaso);
			Ext.getCmp('acTFNroCasoId').setValue(Ext.util.JSON.decode(_response).nmcaso);
			Ext.getCmp('acTFNroOrdenTId').setValue(Ext.util.JSON.decode(_response).cdordentrabajo);
			Ext.getCmp('acTFStatusId').setValue("ASIGNADO");
		}
	}
	
	
	storeFuncionNombre.load(
					 {
					 	callback:function(record,opt,success)
	 					{
        					
        					Ext.getCmp('funcionNombreId').setValue(storeFuncionNombre.reader.jsonData.funcionNombre);
    					//alert(Ext.getCmp('funcionNombreId').getValue());
    					} 
					 
					 });
	panelUpload.show();
	Ext.getCmp('txtBtnGuardarId').setDisabled(false);
	storeFuncionCodigo.load(
					 {
					 	callback:function(record,opt,success)
	 					{
        					
        					Ext.getCmp('funcionCodigoId').setValue(storeFuncionCodigo.reader.jsonData.cdusuari);
    					//alert(Ext.getCmp('funcionCodigoId').getValue());
    					} 
					 
					 }); 
 
	function feInicio() {
		try{
			var fecha = new Date();
		       //alert(fecha.format(Date.patterns.ShortDate));
               return fecha.format(Date.patterns.ShortDate);
               }
              catch(e)
              {
              	return fecha.format('d/m/Y');
              }
         }; 
 
 function feFormato(val) {
		           			  try{
                return Ext.util.Format.date(val,'d/m/Y');
                  }
                 catch(e)
                 { return val;
                 }
                 };

/*function feFormato(val) {
		      try{
		        var fecha = new Date();
		        fecha = Date.parseDate(val, 'Y-m-d H:i:s.g');
		        //alert("Valor: " + val + "\nFecha: " + fecha + "\nformateada : " + fecha.format('d/m/Y'));
               var _val2 = val.format ('Y-m-d H:i:s.g');
              // alert(_val2);
               return _val2.format('d/m/Y');
               }
              catch(e)
              {
              	return val.format('d/m/Y');
              }
         };   */              
                 
 	
 //console.log(panelUpload);
	function validar(){
		 
	   var conn = new Ext.data.Connection();
	   //comenzarMask(Ext.getBody(), 'Cargando...');
	   startMask(formPanel.id, getLabelFromMap('400023',helpMap,'Espere ...'));
	   conn.request({
				    	url: _ACTION_VALIDA_NMCASO_FAX,
				    	method: 'POST',
				    	params: {
				    				nmcaso:formPanel.form.findField('nmcaso').getValue()
				    				
				    			},
	 					//waitMsg : getLabelFromMap('400017', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
                   		     	        endMask();
										if (Ext.util.JSON.decode(response.responseText).success == false) {
                                		        Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0], function () {
                                		        formPanel.form.findField('nmcaso').setValue("");
                                		        formPanel.form.findField('nmcaso').focus();
                                		        });
                                		} else {
                                      //  Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){reloadGrid();});
                                		}
    						}
 	   })
	   }
	   
	   
	   //valida de q pantalla se llama 
if (_NMCASO){
       Ext.getCmp("texNmCasoId").setValue(_NMCASO);
      }	   


})

function mensaje(){
		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400111', helpMap,'El Tipo de Fax debe tener al menos un campo relacionado'));
}
function endMaskLocal(){
	the_Mask_local.hide();
	the_Mask_local = null;
	Ext.getCmp('txtBtnGuardarId').setDisabled(false);
	Ext.getCmp('txtBtnRegresarId').setDisabled(false);
}

function finMask(){
	endMaskLocal();
}
function startMaskLocal(ctrlId, text) {
	if (the_Mask != null) {
		endMaskLocal();
	}
	the_Mask_local = new Ext.LoadMask(ctrlId, {msg: text, disabled: false});
	the_Mask_local.show();
	Ext.getCmp('txtBtnGuardarId').setDisabled(true);
	Ext.getCmp('txtBtnRegresarId').setDisabled(true);
}
var the_Mask_local = null
function endMaskLocal(){
	the_Mask_local.hide();
	the_Mask_local = null;
	//Ext.getCmp('txtBtnGuardarId').setDisabled(false);
	Ext.getCmp('txtBtnRegresarId').setDisabled(false);
}
function deshabilitarBotonGuarda(){
	Ext.getCmp('txtBtnGuardarId').setDisabled(true);
} 

function habilitarBotonGuarda(){
	Ext.getCmp('txtBtnGuardarId').setDisabled(false);
} 
/*  function corte(){
     endMask();  
  };*/
		
	
	
	