function editarEncuestas(record){
	
	var readerForm = new Ext.data.JsonReader({
						root:'mListValorEncuesta',
		            	totalProperty: 'totalCount',
		            	successProperty : '@success'
	        	},
	        	[
		        {name: 'cdPersonEd',  type: 'string',  mapping:'cdPerson'},
		        {name: 'dsPersonEd',  type: 'string',  mapping:'dsPerson'},
		        {name: 'dsUniecoEd',  type: 'string',  mapping:'dsUnieco'},
		        {name: 'dsRamoEd',  type: 'string',  mapping:'dsRamo'},
		        {name: 'cdModuloEd',  type: 'string',  mapping:'cdModulo'},
		        {name: 'dsModuloEd',  type: 'string',  mapping:'dsModulo'},
		        {name: 'cdCampanEd',  type: 'string',  mapping:'cdCampan'},
		        {name: 'dsCampanEd',  type: 'string',  mapping:'dsCampan'},
		        {name: 'dsProcesoEd',  type: 'string',  mapping:'dsProceso'},
		        {name: 'dsEncuestaEd',  type: 'string',  mapping:'dsEncuesta'},
		        {name: 'nmPoliexEd',  type: 'string',  mapping:'nmPoliex'},
		        {name: 'dsElemenEd',  type: 'string',  mapping:'dsElemen'}
		        
				]);
       
	var storeForm  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_BUSCAR_PREGUNTA_ENCUESTA_ENC}),
						//reader:readerForm
						reader:readerForm })
	
	//alert('Si');
/*	var CDUNIECO='52';
	var CDRAMO='500';
	var NMPOLIZA='511';
	var ESTADO='M';
	var CDPERSON='3121';*/
	//var CDPROCESO='3';
	//var CDSECUENCIA='';
	//FORMULARIO PRINCIPAL  con el encabezado solamente************************************
	var formPanelEdit = new Ext.FormPanel({			
	        //el:'encabezado',
	        id: 'encabezadoId',
	        //store: storeForm,
	        //title: '<span style="color:black;font-size:12px;">Ingresar Encuesta</span>',
	        iconCls:'logo',
	        //bodyStyle:'background: white',
	        bodyStyle : 'padding:5px 5px 0',
	        labelAlign: "right",
	        bodyStyle:'background: white',
	        reader: readerForm,
	        successProperty: 'success',
	        frame:true,
	        width: 690,
			layout:'table',
			layoutConfig: {columns:3},
			url:_ACTION_BUSCAR_PREGUNTA_ENCUESTA_ENC,
            defaults: {width:230},	        
	        autoHeight:true,
            items:[
					{
    					html: '<span class="x-form-item" style="font-weight:bold">Editar Encuesta</span>',
    					colspan:3
       				},
       				{            		
   						layout: 'form',
   						labelWidth: 45,
   						//colspan:3,
   						items: [
   								{
		           					xtype: 'textfield',
   									id: 'nombreEncId',
			        				fieldLabel: getLabelFromMap('nombreEncId',helpMap,'Nombre'),
			        				tooltip:getToolTipFromMap('nombreEncId',helpMap,'Nombre Encuestado'),
			        				hasHelpIcon:getHelpIconFromMap('nombreEncId',helpMap),
			       					Ayuda:getHelpTextFromMap('nombreEncId',helpMap),
			        				name: 'dsPersonEd',
			        				disabled:true,
			        				width: 180
			      				}
   								]
    				},
    				{
    					layout: 'form',
    					labelWidth: 80,
    					//colspan:2,
    					items: [
		           				{
		           					xtype: 'textfield',
   									id: 'claveCatWebEncId',
			        				fieldLabel: getLabelFromMap('claveCatWebEncId',helpMap,'Clave Catweb'),
			        				tooltip:getToolTipFromMap('claveCatWebEncId',helpMap,'Clave Catweb'),
			        				hasHelpIcon:getHelpIconFromMap('claveCatWebEncId',helpMap),
			       					Ayuda:getHelpTextFromMap('claveCatWebEncId',helpMap),
			        				name: 'claveCatWeb',
			        				disabled:true,
			        				width: 120
			     				}
   								]
    				},
    				{
    					layout: 'form',
    					labelWidth: 90,
    					//colspan:3,
   						items: [
   								{
		           					xtype: 'textfield',
   									id: 'clienteCuentaEncId',
			        				fieldLabel: getLabelFromMap('clienteCuentaEncId',helpMap,'Cliente Cuenta'),
			        				tooltip:getToolTipFromMap('clienteCuentaEncId',helpMap,'Cliente Cuenta'),
			        				hasHelpIcon:getHelpIconFromMap('clienteCuentaEncId',helpMap),
			       					Ayuda:getHelpTextFromMap('clienteCuentaEncId',helpMap),
			        				name: 'clienteCuenta',
			        				value: CDELEMENTO,
			        				disabled:true,
			        				width: 110
							     }
   								]
    				},	
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 440,
	           			items: [
   								{
   									xtype:'textfield',
            						id: 'dsUniecoEdId',
							        fieldLabel: getLabelFromMap('txtfldaseguradoraId',helpMap,'Aseguradora'),
							        tooltip:getToolTipFromMap('txtfldaseguradoraId',helpMap,'Aseguradora'),
							        hasHelpIcon:getHelpIconFromMap('txtfldaseguradoraId',helpMap),
			       					Ayuda:getHelpTextFromMap('txtfldaseguradoraId',helpMap),
							        name: 'dsUniecoEd',
							        disabled: true,
			        				width: 300
			   		 			}
   								/*{
		           				   xtype: 'combo',
		           				   tpl: '<tpl for="."><div ext:qtip="{cdUnieco}.{dsUnieco}" class="x-combo-list-item">{dsUnieco}</div></tpl>',
			                       id:'aseguradoraEncId',
			                       fieldLabel: getLabelFromMap('aseguradoraEncId',helpMap,'Aseguradora'),
			           			   tooltip:getToolTipFromMap('aseguradoraEncId',helpMap,'Aseguradora'),
			                       //store: storeAseguradoraEnc,
			                       displayField:'dsUnieco',
			                       valueField:'cdUnieco',
			                       hiddenName: 'cdUniecoh',
			                       typeAhead: true,
			                       mode: 'local',
			                       triggerAction: 'all',
			                       width: 200,
			                       emptyText:'Seleccione la Aseguradora...',
			                       selectOnFocus:true,
			                       forceSelection:true
			   		 			}*/
			   		 			]
    				},	
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 440,
	           			items: [
   								{
   									xtype:'textfield',
            						id: 'dsRamoEdId',
							        fieldLabel: getLabelFromMap('txtfldproductoId',helpMap,'Producto'),
							        tooltip:getToolTipFromMap('txtfldproductoId',helpMap,'Producto'),
							        hasHelpIcon:getHelpIconFromMap('txtfldproductoId',helpMap),
			       					Ayuda:getHelpTextFromMap('txtfldproductoId',helpMap),
							        name: 'dsRamoEd',
			        				disabled: true,
			        				width: 300
			   		 			}
   								/*{
		           				   xtype: 'combo',
		           				   tpl: '<tpl for="."><div ext:qtip="{cdRamo}.{dsRamo}" class="x-combo-list-item">{dsRamo}</div></tpl>',
			                       id:'productosEncId',
			                       fieldLabel: getLabelFromMap('productosEncId',helpMap,'Productos'),
			           			   tooltip:getToolTipFromMap('productosEncId',helpMap,'Productos'),
			                       //store: storeProductosEnc,
			                       displayField:'dsRamo',
			                       valueField:'cdRamo',
			                       hiddenName: 'cdRamoh',
			                       typeAhead: true,
			                       mode: 'local',
			                       triggerAction: 'all',
			                       width: 200,
			                       emptyText:'Seleccione el Producto...',
			                       selectOnFocus:true,
			                       forceSelection:true
			   		 			}*/
   								]
    				},	
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 440,
	           			items: [
	           			{
   									xtype:'textfield',
            						id: 'dsModuloEdId',
							        fieldLabel: getLabelFromMap('moduloEncId',helpMap,'M&oacute;dulo'),
							        tooltip:getToolTipFromMap('moduloEncId',helpMap,'M&oacute;dulo'),
							         hasHelpIcon:getHelpIconFromMap('moduloEncId',helpMap),
			       					Ayuda:getHelpTextFromMap('moduloEncId',helpMap),
							        name: 'dsModuloEd',
			        				disabled: true,
			        				width: 300
	           						/*xtype:'textfield',
            						id: 'dsElemenEdId',
							        fieldLabel: getLabelFromMap('dsElemenEdId',helpMap,'Cliente'),
							        tooltip:getToolTipFromMap('dsElemenEdId',helpMap,'Cliente'),
							         hasHelpIcon:getHelpIconFromMap('dsElemenEdId',helpMap),
			       					Ayuda:getHelpTextFromMap('dsElemenEdId',helpMap),
							        name: 'dsElemenEd',
			        				disabled: true,
			        				width: 210*/
			   		 			}
   								/*{
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
			                       triggerAction: 'all',
			                       width: 200,
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
	                                               cdUniEco: '',//CDUNIECOCLI,
	                                               cdRamo: '',//CDRAMOCLI,
	                                               cdModulo: record.get("codModulo")
	                                           }
	                                       }
	
	                                   );
	                                   this.collapse();
                                   }
			   		 			}*/			   		 			
			   		 			
			   		 			
   								]
    				},	
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 440,
	           			items: [
   						{
   									xtype:'textfield',
            						id: 'dsCampanEdId',
							        fieldLabel: getLabelFromMap('campanhaEncId',helpMap,'Campa&ntilde;a'),
							        tooltip:getToolTipFromMap('campanhaEncId',helpMap,'Campa&ntilde;a'),
							        hasHelpIcon:getHelpIconFromMap('campanhaEncId',helpMap),
			       					Ayuda:getHelpTextFromMap('campanhaEncId',helpMap),
							        name: 'dsCampanEd',
			        				disabled: true,
			        				width: 300
			   		 			}		
	           			
	           			/*{
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
			                       width: 200,
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
	                                               cdUniEco: '',//CDUNIECOCLI,
	                                               cdRamo: '',//CDRAMOCLI,
	                                               cdModulo: Ext.getCmp('moduloEncId').getValue(),
	                                               cdCampan: record.get('codCampan')
	                                           }
	                                       }
	
	                                   );
	                                   this.collapse();
                                   }
			   		 			}*/
   								]
    				},
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 440,
	           			items: [
	           			{
   									xtype:'textfield',
            						id: 'dsTemaEdId',
							        fieldLabel: getLabelFromMap('temaEncId',helpMap,'Tema'),
							        tooltip:getToolTipFromMap('temaEncId',helpMap,'Tema'),
							        hasHelpIcon:getHelpIconFromMap('temaEncId',helpMap),
			       					Ayuda:getHelpTextFromMap('temaEncId',helpMap),
							        name: 'dsTemaEd',
			        				disabled: true,
			        				width: 300
			   		 			}
   								/*{
		           				   xtype: 'combo',
		           				   tpl: '<tpl for="."><div ext:qtip="{codTema}.{desTema}" class="x-combo-list-item">{desTema}</div></tpl>',
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
			                       width: 200,
			                       //allowBlank: false,
			                       emptyText:'Seleccione El Tema...',
			                       selectOnFocus:true,
			                       forceSelection:true,
			                       onSelect: function (record) {
	                                   this.setValue(record.get('codTema'));
	                                   Ext.getCmp('encuestaEncId').setValue('');
	                                   storeEncuestaEnc.removeAll();
	                                   storeEncuestaEnc.load({
	                                           params: {
	                                               cdUniEco: '',//CDUNIECOCLI,
	                                               cdRamo: '',//CDRAMOCLI,
	                                               cdModulo: Ext.getCmp('moduloEncId').getValue(),
	                                               cdCampan: Ext.getCmp('campanhaEncId').getValue(),
	                                               cdProceso: CDPROCESO
	                                           }
	                                       }
	
	                                   );
	                                   this.collapse();
                                   }
			   		 			}*/
   								]
    				},
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 440,
	           			items: [
   								{
   									xtype:'textfield',
            						id: 'dsEncuestaEdId',
							        fieldLabel: getLabelFromMap('encuestaEncId',helpMap,'Encuesta'),
							        tooltip:getToolTipFromMap('encuestaEncId',helpMap,'Encuesta'),
							        hasHelpIcon:getHelpIconFromMap('encuestaEncId',helpMap),
			       					Ayuda:getHelpTextFromMap('encuestaEncId',helpMap),
							        name: 'dsEncuestaEd',
			        				disabled: true,
			        				width: 300
			   		 			}
   								/*	
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
			                       width: 200,
			                       //allowBlank: false,
			                       emptyText:'Seleccione La Encuesta...',
			                       selectOnFocus:true,
			                       forceSelection:true,
			                       onSelect: function (record) {
	                                   this.setValue(record.get('codEncuesta'));
	                                   var cdEncuesta=record.get('codEncuesta');
	                                   //alert();
	                                   Ext.getCmp('numConfigId').setValue(record.get('numConfig'));
	                                   this.collapse();
	                                   el_formDatosEncuestas.url=_ACTION_BUSCAR_PREGUNTA_ENCUESTA +'?cdEncuesta='+cdEncuesta+'&cdSecuencia='+'';//CDSECUENCIA;
	                                   el_formDatosEncuestas.load();
	                                   
                                   }
			   		 			}*/
   								]
    				},
    				{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 440,
	           			items: [
   								{
   									xtype:'textfield',
            						id: 'nmPoliexEdId',
							        fieldLabel: getLabelFromMap('nmPolizaEncId',helpMap,'N&uacute;mero de Poliza'),
							        tooltip:getToolTipFromMap('nmPolizaEncId',helpMap,'N&uacute;mero de Poliza'),
							        hasHelpIcon:getHelpIconFromMap('nmPolizaEncId',helpMap),
			       					Ayuda:getHelpTextFromMap('nmPolizaEncId',helpMap),
							        name: 'nmPoliexEd',
			        				disabled: true,
			        				width: 300
			   		 			}
   								]
    				}/*,
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
    				}*/
            	]												
	});
	
	
//FORMULARIO DONDE SE  MOSTRANRAN LOS ELEMENTOS DE LA ENCUESTA DEACUERDO AL COMBO Encuesta
	var el_formDatosEncuestas = new Ext.ux.MetaForm({
    	//el: 'atributosVariables',
    	id: 'el_formDatosEncuestas',
		labelAlign:'right',
        width : 690,
        height: 200,
        //bodyStyle: {position: 'relative', background: 'white'},
        url: _ACTION_BUSCAR_PREGUNTA_ENCUESTA,
        removeComponentsOnLoad: true,
        successProperty: 'success',
        bodyStyle:'background: white',
        labelWidth: 200,
        autoScroll: true,
        defaults: {labelWidth: 120}
        //renderTo: 'formDatosAdicionales'
	});            
	   

		
	/*var formBoton = new Ext.FormPanel({			
	        //renderTo:'formBotones',
	        id: 'frmBotonesId',	        
	        iconCls:'logo',
	        border: false,
	        bodyStyle:'background: white',
	        labelAlign: "right",
	        frame:true,
	        width: 690,
            layoutConfig: { columns: 3, columnWidth: .33},
			items: [
					{
					width: 620,
             		height: 40,
             		buttonAlign: 'center',  
             		border: false,
			 		buttons:[
				 				/*{
	    						text:getLabelFromMap('txtBtnGuardarId',helpMap,'Guardar'),
	    				 		tooltip: getToolTipFromMap('txtBtnGuardarId',helpMap,'Guarda Encuesta'),
	    				  		handler:function(){
		    				  			if (el_formDatosEncuestas.isValid()&&(Ext.getCmp('encuestaEncId').getValue()!="")) {
		            						//guardarPregunta();
						            	}else {
									        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
						            	}
	   					 			}
	    						},*/
	    				/*		{
	    				 		text:getLabelFromMap('txtBtnExportarId',helpMap,'Exportar'),
	    				 		tooltip: getToolTipFromMap('txtBtnExportarId',helpMap,'Exporta Encuesta')                              
	    					
	    						},
	    						{
	    				 		text:getLabelFromMap('txtBtnRegresarId',helpMap,'Regresar'),
	    				 		tooltip: getToolTipFromMap('txtBtnregresarId',helpMap,'Regresar'),
	    				 		handler : function(){
	    				 				
	    				 				win.close();
	    				 				
	    				 				//window.location = _IR_A_CONSULTA_FAX;
	    				 			}                             
	    					   	}
    			  	 		]
					}			
			]            
         });*/
         
         
	
var win = new Ext.Window ({
			id: 'winIngEncuestaEdId',
			//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('95', helpMap,'Editar Encuesta')+'</span>',
			title: getLabelFromMap('winIngEncuestaEdId', helpMap,'Editar Encuesta'),
			width: 710,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	modal: true,
        	items: [formPanelEdit,el_formDatosEncuestas/*,formBoton*/],
        	
        	buttons:[{
				text:getLabelFromMap('txtBtnExportarId',helpMap,'Exportar'),
	    		tooltip: getToolTipFromMap('txtBtnExportarId',helpMap,'Exporta Encuesta'),
				handler: function(){
										
    				 					nmconfig=record.get('nmConfig');
										cdunieco=record.get('cdUnieco');
										cdramo=record.get('cdRamo');
										estado=record.get('estado');
										nmpoliza=record.get('nmPoliza');
										cdperson=record.get('cdPerson');
    				 					
    				 					
    				 					/*numDeCaso=Ext.getCmp('texNmCasoId').getValue();
    				 					tipoArchivo= Ext.getCmp('cmbTipoArchivoId').lastSelectionText;
    				 					cdtipoar= Ext.getCmp('cmbTipoArchivoId').getValue();
    				 					/*feRegistro= Ext.getCmp('feingreso').getValue();
    				 					feRecepcion = Ext.getCmp('ferecepcion').getValue();*/
    				 					/*numPoliza =  Ext.getCmp('texPolizaId').getValue();
    				 					nmfax = Ext.getCmp('texNmFaxIdTex').getValue();
    				 					if (panelUpload.uploader.store.data.items[0]!= undefined ) {
    				 						archivoNombre = panelUpload.uploader.store.data.items[0].data.fileName;
    				 					} else {
    				 						archivoNombre = '';
    				 					}*/
    				 					/*var url = _ACTION_EXPORTAR_ADMINISTRACION_FAX + '?numDeCaso=' + numDeCaso + '&tipoArchivo=' + tipoArchivo +'&feRegistro=' + feRegistro+'&feRecepcion='+ feRecepcion +'&numPoliza='+numPoliza+'&archivoNombre='+archivoNombre;*/
				     				    var _url = _ACTION_EXPORTAR_INGRESAR_ENCUESTAS_EDITAR + '?nmconfig=' + nmconfig + '&cdunieco=' + cdunieco +'&cdramo='+cdramo+'&estado='+estado+'&nmpoliza='+nmpoliza+'&cdperson='+cdperson+'&cdelemento='+CDELEMENTO;
                     					var width, height;
      									width = screen.width;
       									height = screen.height;
                         					window.open(_url, 'exportarValorEncuesta', config='height=' + height + ', width=' + width + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, directories=no, status=no')
                     					}
			},{
	    		text:getLabelFromMap('txtBtnRegresarId',helpMap,'Regresar'),
	    		tooltip: getToolTipFromMap('txtBtnregresarId',helpMap,'Regresar'),
	    		handler : function(){
	    	 				win.close();
	    	 				//window.location = _IR_A_CONSULTA_FAX;
	    	 			}                             
	    	}]
        	
        });
			        
	/*formPanelEdit.load({
		waitTitle : getLabelFromMap('400021', helpMap,'Espere...'),
                waitMsg : getLabelFromMap('400028', helpMap,'Leyendo datos...'),
                    
				params:{
				pv_nmconfig_i:'43',//record.get('nmConfig')
				pv_cdunieco_i:'52',//record.get('cdUnieco')
				pv_cdramo_i:'500',//record.get('cdRamo')
				pv_estado_i:'M',//record.get('estado')
				pv_nmpoliza_i:'516',//record.get('nmPoliza');
				pv_cdperson_i:'714'//record.get('')
				},
				success:function(form, action){alert('succes');console.log(action)},
				failure:function(){alert('failure');console.log(formPanelEdit);/*console.log(storeForm);*//*alert(storeForm.reader.jsonData.mListValorEncuesta[0])},
				callback:function(r,o,success){
					if(success){alert('callback:'+ r)}
					}
				}
	);*/
  	
	storeForm.load({
		params:{pv_nmconfig_i:record.get('nmConfig'),
				pv_cdunieco_i:record.get('cdUnieco'),
				pv_cdramo_i:record.get('cdRamo'),
				pv_estado_i:record.get('estado'),
				pv_nmpoliza_i:record.get('nmPoliza'),
				pv_cdperson_i:record.get('cdPerson')
		},
		
		callback:function(r, o, suc){
		//alert("callback"+storeForm.reader.jsonData);console.log(storeForm.reader.jsonData);
		//alert("uniEco: " + storeForm.reader.jsonData.MListValorEncuesta[0].dsUnieco);
			formPanelEdit.findById("nombreEncId").setValue(storeForm.reader.jsonData.MListValorEncuesta[0].dsPerson);
			formPanelEdit.findById("claveCatWebEncId").setValue(storeForm.reader.jsonData.MListValorEncuesta[0].cdPerson);
			//formPanelEdit.findById("clienteCuentaEncId").setValue(storeForm.reader.jsonData.MListValorEncuesta[0].cdCampan);
			formPanelEdit.findById("dsUniecoEdId").setValue(storeForm.reader.jsonData.MListValorEncuesta[0].dsUnieco);
			formPanelEdit.findById("dsRamoEdId").setValue(storeForm.reader.jsonData.MListValorEncuesta[0].dsRamo);
			formPanelEdit.findById("dsModuloEdId").setValue(storeForm.reader.jsonData.MListValorEncuesta[0].dsModulo);
			//formPanelEdit.findById("dsElemenEdId").setValue(storeForm.reader.jsonData.MListValorEncuesta[0].dsElemen);
			formPanelEdit.findById("dsCampanEdId").setValue(storeForm.reader.jsonData.MListValorEncuesta[0].dsCampan);
			formPanelEdit.findById("dsTemaEdId").setValue(storeForm.reader.jsonData.MListValorEncuesta[0].dsProceso);
			formPanelEdit.findById("dsEncuestaEdId").setValue(storeForm.reader.jsonData.MListValorEncuesta[0].dsEncuesta);
			formPanelEdit.findById("nmPoliexEdId").setValue(storeForm.reader.jsonData.MListValorEncuesta[0].nmPoliex);
					
					
					//alert("success: " + suc);
		
		}
	
	});
	el_formDatosEncuestas.url=_ACTION_BUSCAR_PREGUNTA_ENCUESTA +'?pv_nmconfig_i='+record.get('nmConfig')
   															  +'&pv_cdunieco_i='+record.get('cdUnieco')
   															  +'&pv_cdramo_i='+record.get('cdRamo')
   															  +'&pv_estado_i='+record.get('estado')
   															  +'&pv_nmpoliza_i='+record.get('nmPoliza')
   															  +'&pv_cdperson_i='+record.get('cdPerson');
						//el_formDatosEncuestas.load();
	
 /* formPanelEdit.form.load({
		
		params:{pv_nmconfig_i:'43',//record.get('nmConfig')
				pv_cdunieco_i:'52',//record.get('cdUnieco')
				pv_cdramo_i:'500',//record.get('cdRamo')
				pv_estado_i:'M',//record.get('estado')
				pv_nmpoliza_i:'516',//record.get('nmPoliza');
				pv_cdperson_i:'714'//record.get('')
		},
		//waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
		//waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
		failure: function() {alert('failure');
		},
		success: function(){alert('success')},
		callback: function(r, o, success){
			alert(storeForm.reader);
			console.log(storeForm.reader);
			if(success) {
			
						el_formDatosEncuestas.url=_ACTION_BUSCAR_PREGUNTA_ENCUESTA +'?pv_nmconfig_i='+'43'//record.get('nmConfig')
   															  +'&pv_cdunieco_i='+'52'//record.get('cdUnieco')
   															  +'&pv_cdramo_i='+'500'//record.get('cdRamo')
   															  +'&pv_estado_i='+'M'//record.get('estado')
   															  +'&pv_nmpoliza_i='+'516'//record.get('nmPoliza');
   															  +'&pv_cdperson_i='+'714';//record.get('')
						el_formDatosEncuestas.load();
		}}
	});*/
	//storeForm.load();
	//console.log(formPanel);
	/*formPanel.url = _ACTION_BUSCAR_PREGUNTA_ENCUESTA_ENC +	'?pv_nmconfig_i='+'43'
														 + '&pv_cdunieco_i='+'52'
														 + '&pv_cdramo_i='+'500'
														 + '&pv_estado_i='+'M'
														+	'&pv_nmpoliza_i='+'516'
														+	'&pv_cdperson_i='+'714';*/
														
	//formPanel.load();	
	
	/*el_formDatosEncuestas.url=_ACTION_BUSCAR_PREGUNTA_ENCUESTA +'?pv_nmconfig_i='+'43'//record.get('nmConfig')
   															  +'&pv_cdunieco_i='+'52'//record.get('cdUnieco')
   															  +'&pv_cdramo_i='+'500'//record.get('cdRamo')
   															  +'&pv_estado_i='+'M'//record.get('estado')
   															  +'&pv_nmpoliza_i='+'516'//record.get('nmPoliza');
   															  +'&pv_cdperson_i='+'714';//record.get('')
						el_formDatosEncuestas.load();*/
	//console.log(formPanel);
/*	el_formDatosEncuestas.url=_ACTION_BUSCAR_PREGUNTA_ENCUESTA +'?pv_nmconfig_i='+'43'//record.get('nmConfig')
   															  +'&pv_cdunieco_i='+'52'//record.get('cdUnieco')
   															  +'&pv_cdramo_i='+'500'//record.get('cdRamo')
   															  +'&pv_estado_i='+'M'//record.get('estado')
   															  +'&pv_nmpoliza_i='+'516'//record.get('nmPoliza');
   															  +'&pv_cdperson_i='+'714';//record.get('')
		//	el_formDatosEncuestas.load();*/
	
/*	el_formDatosEncuestas.url=_ACTION_BUSCAR_PREGUNTA_ENCUESTA +'?pv_nmconfig_i='+'43'//record.get('nmConfig')
   															  +'&pv_cdunieco_i='+'52'//record.get('cdUnieco')
   															  +'&pv_cdramo_i='+'500'//record.get('cdRamo')
   															  +'&pv_estado_i='+'M'//record.get('estado')
   															  +'&pv_nmpoliza_i='+'516'//record.get('nmPoliza');
   															  +'&pv_cdperson_i='+'714'//record.get('')
	el_formDatosEncuestas.load();*/
	
	win.show();
	/*storeModuloEnc.load({
			params: {
					cdUniEco: '',//CDUNIECOCLI, 
					cdRamo: ''//CDRAMOCLI
					}
					});*/
					
	

/*	function guardarPregunta(){
		_params= "nmConfig="+Ext.getCmp('numConfigId').getValue()+"&"+
				"cdUniEco="+CDUNIECOCLI+"&"+
				"cdRamo="+CDRAMOCLI+"&"+
				"estado="+CDESTADOCLI+"&"+
				"nmPoliza="+CDNMPOLIZACLI+"&"+
				"cdPerson="+CDPERSONCLI+"&"+
				"cdEncuesta="+Ext.getCmp('encuestaEncId').getValue();
		var i = 0;
		
		Ext.each(el_formDatosEncuestas.items.items, function(campito){
			
			if (campito.name!=undefined){
				//console.log(campito.name);
				_params+="&respuesEnc[" + i + "].id="+campito.name+"&respuesEnc[" + i + "].texto="+campito.getValue();
			i++;
			}
		});
		execConnection(_ACTION_GUARDAR_RESPUESTAS, _params, cbkGuardarPregunta);
	}
	function cbkGuardarPregunta (_success, _message) {
		if (!_success) {
			Ext.Msg.alert('Error', _message);
		}else {
			Ext.Msg.alert('Aviso', _message);
		}
	}*/
}//)
		
	
	
	