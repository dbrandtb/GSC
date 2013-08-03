Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";	
	//alert(_NMFAX);		
	
		//FORMULARIO PRINCIPAL ************************************
	
	var storeAdministracionFax = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_ADMNISTRACION_FAX}),
            reader: jsonAdministracionFaxForm
        });
        
	 var jsonAdministracionFaxForm = new Ext.data.JsonReader( {
            root : 'MListConsultaFax',
            totalProperty: 'total',
            successProperty : 'success'
        },
         [ 
            {name: 'cdtipoar', type: 'string',mapping:'cdtipoar'},            			
            {name: 'dsarchivo', type: 'string',mapping:'dsarchivo'},
            {name: 'nmfax', type: 'string',mapping:'nmfax'},
            {name: 'nmpoliex', type: 'string',mapping:'nmpoliex'},
            {name: 'nmcaso', type: 'string',mapping:'nmcaso'},
            {name: 'cdusuario', type: 'string'},
            {name: 'dsusuari', type: 'string',mapping:'dsusuari'},
            {name: 'ferecepcion', type: 'string',mapping:'ferecepcion'}
          ]
        );
        
        
   /*var cdVariable = new Ext.form.Hidden({
     	id: 'cdVariableId',
     	name:'cdVariable'
     });  
 */   
 //  Ext.getCmp('cdVariableId').setValue(1);  
     
	var comboTipoArchivo = new Ext.form.ComboBox({
	
	tpl: '<tpl for="."><div ext:qtip="{cdtipoar}. {dsarchivo}" class="x-combo-list-item">{dsarchivo}</div></tpl>',
	id:'cmbTipoArchivoId',
	fieldLabel: getLabelFromMap('cmbTipoArchivoId',helpMap,'Tipo de Archivo'),
	tooltip:getToolTipFromMap('cmbTipoArchivoId',helpMap,'Tipo de Archivo'),
	hasHelpIcon:getHelpIconFromMap('cmbTipoArchivoId',helpMap),
		Ayuda: getHelpTextFromMap('cmbTipoArchivoId',helpMap),

	store: storeTipoFax,
	value: _CDTIPOAR,
	disabled: true,
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
	forceSelection:true
	
	})
	
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
	
	
	var formPanel = new Ext.FormPanel({			
	        //el:'encabezadoFijo',
			renderTo:'encabezadoFijo',
	        id: 'acFormPanelId',	
	        url: _ACTION_OBTENER_ADMNISTRACION_FAX,
	       // title: '<span style="color:black;font-size:12px;">Administraci&oacute;n de Fax Editar</span>',
	        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('acFormPanelId', helpMap,'Administraci&oacute;n de Fax Editar')+'</span>',
	        iconCls:'logo',
	        store:storeAdministracionFax,
	        reader:jsonAdministracionFaxForm,
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
           			labelWidth: 80,           			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: 'texNmCasoId',
					        fieldLabel: getLabelFromMap('texNmCasoId',helpMap,'N&uacute;mero de Caso'),
					        tooltip:getToolTipFromMap('texNmCasoId',helpMap,'N&uacute;mero de Caso'),
					        hasHelpIcon:getHelpIconFromMap('texNmCasoId',helpMap),
		                    Ayuda: getHelpTextFromMap('texNmCasoId',helpMap),
					        name: 'nmcaso',
					        disabled: true,
					        value: _NMCASO,
					        width: 105					     }
           				]
            		},
            		{
            		layout: 'form',
            		labelWidth: 85,
            		items: [
           				{
           					xtype:'textfield',
		            		id: 'texNmFaxIdTex',
		            		fieldLabel: getLabelFromMap('texNmFaxIdTex',helpMap,'N&uacute;mero de Fax'),
					        tooltip:getToolTipFromMap('texNmFaxIdTex',helpMap,'N&uacute;mero de Fax'),
					         hasHelpIcon:getHelpIconFromMap('texNmFaxIdTex',helpMap),
		                    Ayuda: getHelpTextFromMap('texNmFaxIdTex',helpMap),
					        name: 'nmfax',
					        value: _NMFAX,
					        disabled: true,
					        width: 45
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 120,
            		colspan:4,          			
           			items: [
           			       		comboTipoArchivo
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
            		labelWidth: 80,
            		//colspan:1,          			
           			items: [
           				{
           					xtype:'datefield',
		            		id: 'feingreso',
		            		//value:'10/12/2008',
					        fieldLabel: getLabelFromMap('feingreso',helpMap,'Fecha de Registro'),
					        tooltip:getToolTipFromMap('feingreso',helpMap,'Fecha de Registro'),
					         hasHelpIcon:getHelpIconFromMap('feingreso',helpMap),
		                    Ayuda: getHelpTextFromMap('feingreso',helpMap),
					        name: 'feingreso',
					        disabled: true,
					        format: 'd/m/Y',
					        value: feInicio(),
					        altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
					        width: 90
					   			     
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
					        disabled: true,
					        format: 'd/m/Y',
					        altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
					        width: 90
					   			     
					     }
           				]
            		},{
            		layout: 'form',
            		labelWidth: 75,
            		items: [
           				  {
           					xtype:'textfield',
		            		id: 'texPolizaId',
					        fieldLabel: getLabelFromMap('texPolizaId',helpMap,'P&oacute;liza'),
					        tooltip:getToolTipFromMap('texPolizaId',helpMap,'P&oacute;liza'),
					        hasHelpIcon:getHelpIconFromMap('texPolizaId',helpMap),
		                    Ayuda: getHelpTextFromMap('texPolizaId',helpMap),
					        name: 'nmpoliex',
					        disabled: true
					        //width: 120
					     }
           				]
            		},
            		{
            		layout: 'form',   
            		labelWidth: 70 
            	/*	items: [
           				{
           				xtype: 'button',
   						id: 'btnPoliza',
				        text: getLabelFromMap('btnPoliza',helpMap,'Ver P&oacute;liza'),
				        tooltip:getToolTipFromMap('btnPoliza',helpMap,'Ver P&oacute;liza'),
				        onClick:function(){
				        		obtenerPolizas()
				        }
				        
				       }
           				]		*/										
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
            				width: 150
            			},
            			{            		
           			layout: 'form',
           			labelAlign: 'right',
           			labelWidth: 70,
           			//colspan:1,           			            		
           			items: [
           				{ xtype:'textfield',
           				id: 'numUsuarioId',
           				fieldLabel: getLabelFromMap('numUsuarioId',helpMap,'Usuario'),
					    tooltip:getToolTipFromMap('numUsuarioId',helpMap,'Usuario'),
					    hasHelpIcon:getHelpIconFromMap('numUsuarioId',helpMap),
		                Ayuda: getHelpTextFromMap('numUsuarioId',helpMap),
					    name: 'cdusuario',
					    disabled: true,
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
           					id: 'texUsuarioId',
           					labelSeparator:'',
                            name: 'dsusuari',
					        disabled: true,
					        width: 200
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
            				width: 170
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
            			
            			
            			
            			
            			/* {
            		layout: 'table',
            		layoutConfig: { columns: 2, columnWidth: .50},
            		labelAlign: 'right',
            		items:[			
            		
            		
            		
            		{
            		layout: 'form',
            		labelWidth: 120,
            		items: [
           				{
           					xtype:'textfield',
		            		id: 'texArchivoId',
		            		fieldLabel: getLabelFromMap('texArchivoId',helpMap,'Archivo'),
					        tooltip:getToolTipFromMap('texArchivoId',helpMap,'Archivo'), 	        
					        name: 'nmpoliex',
					       // disabled: true,
					        width: 500
					     }
           				]
           				
            		},
            		{
            			layout: 'form',   
            			labelWidth: 70, 
            			buttonAlign:'left',       			            		
           				items: [
           						{
           						xtype: 'button',
   								id: 'btnExaminar',
				        		text: getLabelFromMap('btnExaminar',helpMap,'Examinar...'),
				        		tooltip:getToolTipFromMap('btnExaminar',helpMap,'Examinar...'),
				        		onClick:function(){
				        			//obtenerPolizas()
				        		}
					     		}
           						]												
					}]
          }       */     	
	    
	    ]//fin item general
	});
	
		
	var formBoton = new Ext.FormPanel({			
	        el:'formBotones',
	        id: 'acFormBotonId',	        
	        //title: '<span style="color:black;font-size:12px;">Administraci&oacute;n de Fax</span>',
	        iconCls:'logo',
	       // store:storeAdministracionFax,
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
                			var _files = panelUpload.store.queryBy(function(r){return 'done' !== r.get('state');});
                	 		if (_files.getCount()) { 
                	 			var  _params = "";
                	 				_params += "cmd=upload&" ;
	                				_params += "&MFaxListVO[0].nmcaso=" + _NMCASO;((Ext.getCmp("texNmCasoId").getValue() != undefined)?Ext.getCmp("texNmCasoId").getValue():"");
									_params += "&MFaxListVO[0].nmfax=" + _NMFAX ;((Ext.getCmp("texNmFaxIdTex").getValue() != undefined)?Ext.getCmp("texNmFaxIdTex").getValue():"");
		 							_params += "&MFaxListVO[0].cdtipoar=" + _CDTIPOAR;Ext.getCmp("cmbTipoArchivoId").getValue(); //((Ext.getCmp("cmbTipoArchivoId").getValue() != undefined)?Ext.getCmp("cmbTipoArchivoId").getValue():"");
		 							_params += "&MFaxListVO[0].feingreso=" + Ext.getCmp("feingreso").getRawValue();//feInicioCambio(Ext.getCmp("feingreso").getValue());// != undefined)?Ext.getCmp("feingreso").getValue():"");
		 							_params += "&MFaxListVO[0].ferecepcion=" + Ext.getCmp("ferecepcion").getRawValue();//feInicioCambio(Ext.getCmp("ferecepcion").getValue());// != undefined)?Ext.getCmp("ferecepcion").getValue():"");
		 							_params += "&MFaxListVO[0].nmpoliex=" + ((Ext.getCmp("texPolizaId").getValue() != undefined)?Ext.getCmp("texPolizaId").getValue():"");
		 							_params += "&MFaxListVO[0].cdusuari=" + ((Ext.getCmp("numUsuarioId").getValue() != undefined)?Ext.getCmp("numUsuarioId").getValue():"");
		 							
                	 		 
		 					
		 					/*"nmcaso=" + formPanel.form.findField('texNmCasoId').getValue() + "&" +
	                					"nmfax=" + formPanel.form.findField('texNmFaxIdTex').getValue() + "&" +
	                					"cdtipoar=" + formPanel.form.findField('cmbTipoArchivoId').getValue() + "&" +
	                					"feingreso=" + formPanel.form.findField('feingreso').getValue() + "&" +
	                					"ferecepcion=" + formPanel.form.findField('ferecepcion').getValue() + "&" +
                	 					"nmpoliex=" + formPanel.form.findField('texPolizaId').getValue() + "&" +
                	 					"cdusuari=" + formPanel.form.findField('funcionCodigoId').getValue();*/
                	 				 _params = obtieneStringDatosVariables(_params,Ext.getCmp("cmbTipoArchivoId").getValue());			
							 
									 panelUpload.setUrl (_ACTION_AGREGAR_ARCHIVO + "?" + _params);
									 startMask(formPanel.id, getLabelFromMap('400022', helpMap,'Guardando Actualizaci&oacute;n de datos...'));
	                	   	     	 panelUpload.uploadCallback = function (_success) {
	                	   	     	 	
	                	   	     	 	//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), uploadCallback );
	                	   	     	 	
	                	   	     	 	//  Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0]);
	                	   	     	 	
									  		  if (_success) {
									  		  	  endMask();
									  		  	// Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400068', helpMap,'Los datos se guardaron con &eacute,xito'));								  		  	
									  		  	 //window.location = _IR_A_CONSULTA_FAX+ '?cdVariable='+1; //Ext.getCmp("cmbTipoArchivoId").getValue();
							    				  }
							    				
							    				window.location = _IR_A_CONSULTA_FAX+ '?cdVariable='+1; //Ext.getCmp("cmbTipoArchivoId").getValue();
							    			
							    				
						      					 }
						      				  
						     				  panelUpload.onUpload();
						     				  
								} else { 
									
									  
									//alert("no hay archivos para subir");
										Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un archivo a cargar para realizar esta operaci&oacute;n'));
							            /* alert (44444);
									       var  _params = "";
		                	 				_params += "cmd=&" ;
			                				_params += "&MFaxListVO[0].nmcaso=" + _NMCASO;((Ext.getCmp("texNmCasoId").getValue() != undefined)?Ext.getCmp("texNmCasoId").getValue():"");
											_params += "&MFaxListVO[0].nmfax=" + _NMFAX ;((Ext.getCmp("texNmFaxIdTex").getValue() != undefined)?Ext.getCmp("texNmFaxIdTex").getValue():"");
				 							_params += "&MFaxListVO[0].cdtipoar=" + _CDTIPOAR;Ext.getCmp("cmbTipoArchivoId").getValue(); //((Ext.getCmp("cmbTipoArchivoId").getValue() != undefined)?Ext.getCmp("cmbTipoArchivoId").getValue():"");
				 							_params += "&MFaxListVO[0].feingreso=" + Ext.getCmp("feingreso").getRawValue();//feInicioCambio(Ext.getCmp("feingreso").getValue());// != undefined)?Ext.getCmp("feingreso").getValue():"");
				 							_params += "&MFaxListVO[0].ferecepcion=" + Ext.getCmp("ferecepcion").getRawValue();//feInicioCambio(Ext.getCmp("ferecepcion").getValue());// != undefined)?Ext.getCmp("ferecepcion").getValue():"");
				 							_params += "&MFaxListVO[0].nmpoliex=" + ((Ext.getCmp("texPolizaId").getValue() != undefined)?Ext.getCmp("texPolizaId").getValue():"");
				 							_params += "&MFaxListVO[0].cdusuari=" + ((Ext.getCmp("numUsuarioId").getValue() != undefined)?Ext.getCmp("numUsuarioId").getValue():"");
				 							
                	 		 
							               _params = obtieneStringDatosVariables(_params,Ext.getCmp("cmbTipoArchivoId").getValue());	*/
							               // panelUpload.setUrl (_ACTION_AGREGAR_ARCHIVO + "?" + _params);
							               //  execConnection(_ACTION_AGREGAR_ARCHIVO, _params, cbkGuardarDatos);		
							               
							              // guardarDatos(_params);
										// _ACTION_AGREGAR_ARCHIVO + "?" + _params;
							           
							        //   Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('', helpMap,'Registro Creado'));
									}
                 
							                                                       
								
                			} else {
                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                  }
                  
                  
                  
                 }
				},{
    				 text:getLabelFromMap('txtBtnExportarId',helpMap,'Exportar'),
    				 tooltip: getToolTipFromMap('txtBtnExportarId',helpMap,'Exporta Faxes'),                              
    				handler: function(){
										
    				 					numDeCaso=Ext.getCmp('texNmCasoId').getValue();
    				 					tipoArchivo= Ext.getCmp('cmbTipoArchivoId').lastSelectionText;
    				 					cdtipoar= Ext.getCmp('cmbTipoArchivoId').getValue();
    				 					/*feRegistro= Ext.getCmp('feingreso').getValue();
    				 					feRecepcion = Ext.getCmp('ferecepcion').getValue();*/
    				 					numPoliza =  Ext.getCmp('texPolizaId').getValue();
    				 					nmfax = Ext.getCmp('texNmFaxIdTex').getValue();
    				 					if (panelUpload.uploader.store.data.items[0]!= undefined ) {
    				 						archivoNombre = panelUpload.uploader.store.data.items[0].data.fileName;
    				 					} else {
    				 						archivoNombre = '';
    				 					}
    				 					/*var url = _ACTION_EXPORTAR_ADMINISTRACION_FAX + '?numDeCaso=' + numDeCaso + '&tipoArchivo=' + tipoArchivo +'&feRegistro=' + feRegistro+'&feRecepcion='+ feRecepcion +'&numPoliza='+numPoliza+'&archivoNombre='+archivoNombre;*/
				     				    var _url = _ACTION_EXPORTAR_ADMINISTRACION_FAX + '?numDeCaso=' + numDeCaso + '&tipoArchivo=' + tipoArchivo +'&numPoliza='+numPoliza+'&archivoNombre='+archivoNombre+'&nmfax='+nmfax+'&cdtipoar='+cdtipoar;
				     				   // showExportDialogVariable( _url );
				     				    showExportDialog(_url);
                     					//var width, height;
      									//width = screen.width;
       									//height = screen.height;
                         					//window.open(_url, 'exportarAdministracionFax', config='height=' + height + ', width=' + width + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, directories=no, status=no')
                     					}
                     },{
    				 text:getLabelFromMap('txtBtnRegresarId',helpMap,'Regresar'),
    				 tooltip: getToolTipFromMap('txtBtnregresarId',helpMap,'Regresar'),
    				 handler : function(){
    				 window.location = _IR_A_CONSULTA_FAX;}                             
    					    				
    				}
    			   ]
            
         });
	

			        
	formPanel.render();
	formBoton.render();
	Ext.getCmp('txtBtnGuardarId').setDisabled(false);
	panelUpload.show();
	
	
	 storeTipoFax.load({
            callback: function (r, o, success) {
                    if (success) {
                        comboTipoArchivo.setValue(_CDTIPOAR);
                       }
            }
        }); 
		
	
	/*function guardarDatosFax(){
	 var conn = new Ext.data.Connection()
	                conn.request({
				    	url: _ACTION_GUARDAR_ADMINISTRACION_FAX,
				    	method: 'POST',
				    	params: {
				    				
				    				nmcaso: formPanel.findById("texNmCasoId").getValue(),
				    				nmfax: formPanel.findById("texNmFaxIdTex").getValue(),
				    				cdtipoar: formPanel.findById("cmbTipoArchivoId").getValue(),
						    		feingreso: formPanel.findById("feingreso").getRawValue(),
                                    ferecepcion: formPanel.findById("ferecepcion").getRawValue(),  
						    		nmpoliex: formPanel.findById("numUsuarioId").getValue(),
						    		cdusuari: formPanel.findById("numUsuarioId").getValue(),
						    		blarchivo: formPanel.findById("texArchivoId").getValue()
						    		
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
                                   if (Ext.util.JSON.decode(response.responseText).success == false) {
                                         Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
                                 }else {
             
                                     Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito',function(){reloadGrid()});
                      window.close();
              function(){reloadGridNivAtencion(cdMatriz);habilitaBotonesTiempo();reloadGridResponsables("",""); _window.close();}
        }
    }
 })
 }*/

        
        
        function guardarDatos(_params){
	//alert(Ext.getCmp("comboTipoArchivoId").getValue()+" - "+Ext.getCmp("descrArchId").getValue()+" - "+Ext.getCmp("cdtipoarId").getValue());		
	 /*var _params = 
	 		{	 			
	 			indarchivo: Ext.getCmp("comboTipoArchivoId").getValue(),
                dsArchivo: Ext.getCmp("descrArchId").getValue(),
                cdTipoar: record?record.get('cdTipoar'):""
            };*/
                
	// startMask(formWindowInsertar.id,"Guardando datos...");
	 execConnection(_ACTION_AGREGAR_ARCHIVO, _params, cbkGuardarDatos);		
	}
	
	function cbkGuardarDatos (_success, _message, _response) {
	//	endMask();
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			alert("Exito");
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){Ext.getCmp('windowId').close();reloadGrid()});
		}
	}
        
        
        
/*	function guardarDatosValorFax(){
	 var conn = new Ext.data.Connection()
	                conn.request({
				    	url: _ACTION_GUARDAR_ADMINISTRACION_FAX,
				    	method: 'POST',
				    	params: {
				    				
				    				nmcaso: formPanel.findById("texNmCasoId").getValue(),
				    				nmfax: formPanel.findById("texNmFaxIdTex").getValue(),
				    				cdtipoar: formPanel.findById("cmbTipoArchivoId").getValue(),
				    				cdatribu:'',//falta hacer la parte dinamica
						    		otvalor:''// falta hacer la parte dinamica
						    		
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
                                   if (Ext.util.JSON.decode(response.responseText).success == false) {
                                         Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
                                 }else {
             
                                     Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito',function(){reloadGrid()});
                    //   window.close();
             // function(){reloadGridNivAtencion(cdMatriz);habilitaBotonesTiempo();reloadGridResponsables("",""); _window.close();}
        }
    }
 })
 }*/

 
 /*formPanel.load({
    params: {
    	      dsarchivo: comboTipoArchivo.getValue(),
			  //pv_nmpoliza_i: record.get('nmPoliza'),
			  nmcaso: _NMCASO,
			  nmfax:  _NMFAX,
			  nmpoliex: _NMPOLIEX
    	},
    waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
    success: function () {formPanel.findById("ferecepcion").setValue(record.get('ferecepcion'))}
});*/
  
 
        function obtieneStringDatosVariables(_params, _cdfo){
		var i = 0;
		
		// alert(campito.nmcaso);
		var _form = window.frames["atributosVariablesFaxEditar"].Ext.getCmp('dinamicFormPanelId');
	
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
        
        
	function feInicio() {
		try{
			var fecha = new Date();
		       return fecha.format(Date.patterns.ShortDate);
               }
              catch(e)
              {
              	return fecha.format('d/m/Y');
              }
         }; 
         
 //storeAdministracionFax.load();
 //alert(formPanel.form.findField('cdusuario').getValue());
 
 formPanel.form.load({
    
    
        params: {
    	      dsarchivo: "",//comboTipoArchivo.getValue(),
			  nmcaso: _NMCASO,
			  nmfax:  _NMFAX,
			  nmpoliex:_NMPOLIEX
    	},
    	//waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
   	    //waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
   	   callbak:function(){
   	   //alert(formPanel.form.findField('cdusuario').getValue());
   	   		endMask();
   	   },
   	    success: function () {
   	    	//alert(formPanel.findById("texNmCasoId").setValue(record.get('nmcaso')));
   	  	 	/*formPanel.findById("texNmCasoId").setValue(record.get('nmcaso'));
   	  	 	formPanel.findById("texNmFaxIdTex").setValue(record.get('nmfax'));
   	 	 	formPanel.findById("cmbTipoArchivoId").setValue(record.get('dsArchivo'));
   		 	formPanel.findById("ferecepcion").setValue(record.get('ferecepcion'));
   		 	formPanel.findById("feingreso").setValue(record.get('feingreso'));
   		 	formPanel.findById("texPolizaId").setValue(record.get('nmpoliex'));
   		 	formPanel.findById("numUsuarioId").setValue(record.get('cdusuario'));
   		 	formPanel.findById("texUsuarioId").setValue(record.get('dsusuari'));*/
			//alert("Usuario="+storeAdministracionFax.reader);//.MListConsultaFax[0].cdusuari);
   	    	//console.log(storeAdministracionFax);
   	    	startMask(formPanel.id,getLabelFromMap('400070',helpMap,'Cargando datos ...'));
   	    	window.frames["atributosVariablesFaxEditar"].location.href = _ACTION_OBTENER_ATRIBUTOS_VARIABLES+"?cdtipoar="+ comboTipoArchivo.getValue() + "&nmcaso=" + _NMCASO /*formPanel.findById("texNmCasoId").getValue*/ + "&nmfax=" + formPanel.findById("texNmFaxIdTex").getValue() ;
     }
    
 })
 
 

})
function finMask(){
	endMask();
}
function habilitarBotonGuarda(){
	Ext.getCmp('txtBtnGuardarId').setDisabled(false);
} 

	
	