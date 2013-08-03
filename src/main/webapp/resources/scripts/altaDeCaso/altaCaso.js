Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";				
	
	var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('codigoId',helpMap,'Codigo'),
        tooltip: getToolTipFromMap('codigoId',helpMap,'Codigo'),
        dataIndex: 'cdUsuario',
        width: 150,
        sortable: true
        },
        {
        header: getLabelFromMap('nombreId',helpMap,'Nombre'),
        tooltip: getToolTipFromMap('nombreId',helpMap,'Nombre'),
        dataIndex: 'desUsuario',
        width: 250,
        sortable: true
        },
        {
        header: getLabelFromMap('rolId',helpMap,'Rol'),
        tooltip: getToolTipFromMap('rolId',helpMap,'Rol'),
        dataIndex: 'desRolmat',
        width: 100,
        sortable: true
        },
        {dataIndex: 'cdRolmat', hidden:true},
        {dataIndex: 'cdMatriz', hidden:true}        
	]);

	var gridUR = new Ext.grid.GridPanel({
			renderTo:'grillaResponsables',
       		id: 'gridURId',
       		title:'<br/><span class="x-form-item" style="font-weight:bold">Usuarios Responsables</span>',
            store: storeUResponsables,
            border:true,
            cm: cm,
	        successProperty: 'success',            
            width:630,
          //  loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            frame:true,
            height:200,
            buttonAlign: 'center',
   			labelAlign:'right',
   			buttons: [
   					{
   					id: 'altCsBtnGrdr',
					text: getLabelFromMap('altCsBtnGrdr',helpMap,'Guardar'),
				    tooltip:getToolTipFromMap('altCsBtnGrdr',helpMap,'Guarda Caso'), 
   					//text: 'Guardar',
   					handler:function(){
	   					 		if(Ext.getCmp('acCmbTareaId').getValue()){
	   					 		
	   					 				//guardarDatos(Ext.getCmp("acCmbTareaId").getValue());
	   					 				guardarDatos(Ext.getCmp('formatoOrdenId').getValue());
	   					 		}
	   					 		else{
		                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400075', helpMap,'Debe seleccionar una tarea'));
		                			//disbled:true
	   					 			//Ext.MessageBox.alert('Aviso','Debe seleccionar una tarea');
	   					 		}       					 		       					 		
   					 		}
   					},       					        					
   					{
					text: getLabelFromMap('altCsBtnExprtr',helpMap,'Exportar'),
				    tooltip:getToolTipFromMap('altCsBtnExprtr',helpMap,'Exportar Caso'), 
   					text: 'Exportar'
   					},
   					{
					text: getLabelFromMap('altCsBtnEnvrCrr',helpMap,'Enviar Correo'),
				    tooltip:getToolTipFromMap('altCsBtnEnvrCrr',helpMap,'Enviar Correo'), 
   					//text: 'Enviar Correo',
   					handler:function(){
 	   					 		if (Ext.getCmp('acTFNroCasoId').getValue()!="") 	   					 				
	   					 				//enviarCorreo("Caso Nro: " + Ext.getCmp('acTFNroCasoId').getValue(),Ext.getCmp('acTFNroCasoId').getValue(),TIPOENVIO,getUsers());	   					 					   					 			
	   					 				//enviarCorreo("Caso Nro: " + Ext.getCmp('acTFNroCasoId').getValue(),Ext.getCmp('acTFNroCasoId').getValue(),TIPOENVIO);	   					 				
	   					 				enviarCorreo(Ext.getCmp('acTFNroCasoId').getValue(),Ext.getCmp('cdprocesoHiddenId').getValue());
	   					 			else
		                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400076', helpMap,'Antes debe generar un caso'));	   					 			
   					 		}
   					},
   					{
					text: getLabelFromMap('altCsBtnRgrsr',helpMap,'Regresar'),
				    tooltip:getToolTipFromMap('altCsBtnRgrsr',helpMap,'Regresar Pantalla Anterior'), 
   					//text: 'Regresar',
   					handler:function(){
   								window.location = _ACTION_VOLVER_CONSULTA_CASO+'?cdperson='+CDPERSON;
   							}
   					}
			],
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: storeUResponsables,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
     
     var cdMatriz = new Ext.form.Hidden({
     	id: 'cdmatrizHiddenId',
     	name:'cdMatriz'
     });  
     var cduniecopoliza = new Ext.form.Hidden({
     	id: 'cduniecoHiddenId',
     	name:'cdunieco'
     });
     var cdramopoliza = new Ext.form.Hidden({
     	id: 'cdramoHiddenId',
     	name:'cdramo'
     });
     var estado = new Ext.form.Hidden({
     	id: 'estadoHiddenId',
     	name:'estado'
     });
     var nmsituacion = new Ext.form.Hidden({
     	id: 'nmsituacionHiddenId',
     	name:'nmsituac'
     });
     var nmsituaext = new Ext.form.Hidden({
     	id: 'nmsituaextHiddenId',
     	name:'nmsituaext'
     });
     var nmsbsitext = new Ext.form.Hidden({
     	id: 'nmsbsitextHiddenId',
     	name:'nmsbsitext'
     });
     var nmpoliza = new Ext.form.Hidden({
     	id: 'nmpolizaHiddenId',
     	name:'nmpoliza'
     });
     var nmpoliex = new Ext.form.Hidden({
     	id: 'nmpoliexHiddenId',
     	name:'nmpoliex'
     });
     var proceso = new Ext.form.Hidden({
     	id: 'cdprocesoHiddenId',
     	name:'cdproceso'
     });
     
     var formatoOrden = new Ext.form.Hidden({
     	id: 'formatoOrdenId'
     });
	 
     
	//FORMULARIO PRINCIPAL ************************************
	var formPanel = new Ext.FormPanel({			
	        renderTo:'encabezadoFijo',
	        id: 'acFormPanelId',	        
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('acFormPanelId',helpMap,'Alta daso')+'</span>',
	        //title: '<span style="color:black;font-size:12px;"></span>',
	        iconCls:'logo',
	        store:storePersonaCaso,
	        bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,
	        width: 630,
	        autoHeight:true,
	        layout: 'table',
            layoutConfig: { columns: 3, columnWidth: .33},
            items:[
            		{
            		html: '<span class="x-form-item" style="font-weight:bold">Datos del Cliente</span>',
            		colspan:3
            		},
            		{            		
           			layout: 'form',
           			labelWidth: 70,  
           			width: 300,
           			items: [
           				{
           					xtype: 'textfield',
           					id: 'acTFNomCliId',
					        fieldLabel: getLabelFromMap('acTFNomCliId',helpMap,'Nombre'),
					        tooltip:getToolTipFromMap('acTFNomCliId',helpMap,'Nombre del Cliente'), 	        
		                    hasHelpIcon:getHelpIconFromMap('acTFNomCliId',helpMap),
		 					Ayuda:getHelpTextFromMap('acTFNomCliId',helpMap),
		 					name: 'dsNombre',
					        disabled: true,
					        width: 200
					     }
           				]
            		},
            		{
            		layout: 'form',
            		labelWidth: 120,
            		width: 290,
            		colspan:2,
            		items: [
           				{
           					xtype:'textfield',
		            		id: 'acTFClaveCWId',
					        fieldLabel: getLabelFromMap('acTFClaveCWId',helpMap,'Clave CatWeb'),
					        tooltip:getToolTipFromMap('acTFClaveCWId',helpMap,'Clave CatWeb'), 	        
		                    hasHelpIcon:getHelpIconFromMap('acTFClaveCWId',helpMap),
		 					Ayuda:getHelpTextFromMap('acTFClaveCWId',helpMap),
		 					name: 'cdIdeper',
					        disabled: true,
					        width: 120
					     }
           				]
            		},
					/*{
						layout: 'form'
						
					},*/
            		{
            		layout: 'form',  
            		labelWidth: 70,          			
           			items: [
           				{
           					xtype:'textfield',
		            		id: 'acTFCorpoId',
					        fieldLabel: getLabelFromMap('acTFCorpoId',helpMap,'Corporativo'),
					        tooltip:getToolTipFromMap('acTFCorpoId',helpMap,'Corporativo'), 	        
		                    hasHelpIcon:getHelpIconFromMap('acTFCorpoId',helpMap),
		 					Ayuda:getHelpTextFromMap('acTFCorpoId',helpMap),
					        name: 'corpo',
					        disabled: true,
					        width: 200           					
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 120,          			
           			items: [
           				{
           					xtype:'textfield',
		            		id: 'acTFNroNominaId',
					        fieldLabel: getLabelFromMap('acTFNroNominaId',helpMap,'N&uacute;mero de N&oacute;mina'),
					        tooltip:getToolTipFromMap('acTFNroNominaId',helpMap,'N&uacute;mero de N&oacute;mina'), 	        
		                    hasHelpIcon:getHelpIconFromMap('acTFNroNominaId',helpMap),
		 					Ayuda:getHelpTextFromMap('acTFNroNominaId',helpMap),
					        name: '???',
					        disabled: true,
					        width: 120
					     }
           				]
            		},
            		{
						layout: 'form'
						
					},										
            		{
            		html: '<br/><span class="x-form-item" style="font-weight:bold">Datos del Caso</span>',
            		colspan:3
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 70,         			            		
           			items: [
           					{
           					xtype: 'textfield',
           					id: 'acTFPolizaId',
					        fieldLabel: getLabelFromMap('acTFPolizaId',helpMap,'P&oacute;liza'),
					        tooltip:getToolTipFromMap('acTFPolizaId',helpMap,'P&oacute;liza'), 	        
		                    hasHelpIcon:getHelpIconFromMap('acTFPolizaId',helpMap),
		 					Ayuda:getHelpTextFromMap('acTFPolizaId',helpMap),
					        name: 'nmpoliza',
					        width: 120
					     	}
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 50,          			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: 'acTFIncisoId',
					        fieldLabel: getLabelFromMap('acTFIncisoId',helpMap,'Inciso'),
					        tooltip:getToolTipFromMap('acTFIncisoId',helpMap,'Inciso'), 	        
		                    hasHelpIcon:getHelpIconFromMap('acTFIncisoId',helpMap),
		 					Ayuda:getHelpTextFromMap('acTFIncisoId',helpMap),
					        name: 'nmsbsitext',
					        width: 120
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
   						id: 'acBtnVerPol',
				        text: getLabelFromMap('acBtnVerPol',helpMap,'Ver P&oacute;liza'),
				        tooltip:getToolTipFromMap('acBtnVerPol',helpMap,'Ver P&oacute;liza'),
				        onClick:function(){
				        obtenerPolizas(CDPERSON)}
					     }
           				]												
					},
            		{            		
           			layout: 'form',  
           			labelWidth: 200,  
           			height: 30,
           			items: [
           				{
           					xtype: 'checkbox',
           					id:'acChkbxPolizaId',
		                    fieldLabel: getLabelFromMap('acChkbxPolizaId',helpMap,'P&oacute;liza nueva o No encontrada'),
		      			    tooltip:getLabelFromMap('acChkbxPolizaId',helpMap, 'P&oacute;liza nueva o No encontrada'),
		                    hasHelpIcon:getHelpIconFromMap('acChkbxPolizaId',helpMap),
		 					Ayuda:getHelpTextFromMap('acChkbxPolizaId',helpMap),
					        name:'indpoliza',
					        width: 20,
					        checked:false,
					        onClick:function(){
					        	switch(this.getValue()){
					        		case true: 
					        				Ext.getCmp("acTFPolizaId").setDisabled(true);
					        				Ext.getCmp("acTFIncisoId").setDisabled(true);
					        				Ext.getCmp("acTFPolizaId").setRawValue();
					        				Ext.getCmp("acTFIncisoId").setRawValue();
					        				break;
					        		case false:
					        				Ext.getCmp("acTFPolizaId").setDisabled(true);
					        				Ext.getCmp("acTFIncisoId").setDisabled(true);
					        				Ext.getCmp("acTFPolizaId").setRawValue();
					        				Ext.getCmp("acTFIncisoId").setRawValue();
					        				break;
					        	}					        					
					        }
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 150, 
           			colspan:2,         			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: 'acTFNroCasoId',
					        fieldLabel: getLabelFromMap('acTFNroCasoId',helpMap,'N&uacute;mero de Caso'),
					        tooltip:getToolTipFromMap('acTFNroCasoId',helpMap,'N&uacute;mero de Caso'), 	        
		                    hasHelpIcon:getHelpIconFromMap('acTFNroCasoId',helpMap),
		 					Ayuda:getHelpTextFromMap('acTFNroCasoId',helpMap),
					        name: '???',					        
					        disabled:true,
					        width: 120
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 50,       			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{cdformatoorden}. {cdmatriz}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'acCmbTareaId',
	                       fieldLabel: getLabelFromMap('acCmbTareaId',helpMap,'Tarea'),
	           			   tooltip:getToolTipFromMap('acCmbTareaId',helpMap,'Tarea'),
	                       hasHelpIcon:getHelpIconFromMap('acCmbTareaId',helpMap),
		 				   Ayuda:getHelpTextFromMap('acCmbTareaId',helpMap),
	                       store: storeTareas,
	                       displayField:'descripcion',
	                       valueField:'cdformatoMatriz',
	                       hiddenName: 'cdformatoorden',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 220,
	                       allowBlank: false,
	                       emptyText:'Seleccione Tarea...',
	                       selectOnFocus:true,
	                       forceSelection:true,
	                       onSelect: function(record){
	                       	      _BANDERA_OK = 0;

	                       	    Ext.getCmp('altCsBtnGrdr').setDisabled(false);
	                       	    startMask(document.body,"Cargando datos...");  
	                       	   this.setValue(record.get("cdformatoMatriz"));
		                       this.collapse();
		                                   
		                       Ext.getCmp("cdmatrizHiddenId").setValue(record.get("cdmatriz"));
		                       Ext.getCmp("cdprocesoHiddenId").setValue(record.get("cdproceso"));
		                       Ext.getCmp('cduniecoHiddenId').setValue(record.get('cdunieco'));
		                       Ext.getCmp('cdramoHiddenId').setValue(record.get('cdramo'));		
		                       
		                       Ext.getCmp('formatoOrdenId').setValue(record.get("cdformatoorden"));
		                       					
	                			storePlanesProducto.reload({
										params:{cdRamo: (Ext.getCmp('cdramoHiddenId')!=undefined)?Ext.getCmp('cdramoHiddenId').getValue():""},
										
								       callback: function (){ /*endMask()*/}
								});
								window.frames["atributosVariables"].location.href = _ACTION_OBTENER_ATRIBUTOS_VARIABLES+"?cdformatoorden="+record.get("cdformatoorden")+"&cdseccion="+"&limit=1000";
			        		   
								reloadGrid(record.get("cdproceso"),record.get("cdmatriz"));			        			   
	                            
							   this.setRawValue(record.get("descripcion")); 
	                        
	                       }
	                      
	                       
	                      
					     }
           				]
            		},
            		{            		
           			layout: 'form',
           			colspan:2,  
           			labelWidth: 150,          			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: 'acTFNroOrdenTId',
					        fieldLabel: getLabelFromMap('acTFNroOrdenTId',helpMap,'N&uacute;mero de Orden de Trabajo'),
					        tooltip:getToolTipFromMap('acTFNroOrdenTId',helpMap,'N&uacute;mero de Orden de Trabajo'), 	        
		                    hasHelpIcon:getHelpIconFromMap('acTFNroOrdenTId',helpMap),
		 					Ayuda:getHelpTextFromMap('acTFNroOrdenTId',helpMap),
					        name: '???',
					        disabled:true,
					        width: 120
					     }
           				]
            		},
            		{            		
           			layout: 'form'
            		},
            		{            		
           			layout: 'form',
           			colspan:2,  
           			labelWidth: 150,          			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: 'acTFStatusId',
					        fieldLabel: getLabelFromMap('acTFStatusId',helpMap,'Status'),
					        tooltip:getToolTipFromMap('acTFStatusId',helpMap,'Status'), 	        
		                    hasHelpIcon:getHelpIconFromMap('acTFStatusId',helpMap),
		 					Ayuda:getHelpTextFromMap('acTFStatusId',helpMap),
					        name: 'estado',
					        disabled:true,
					        width: 120
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 50,  
           			colspan:3,        			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'acCmbPrioridadId',
	                       fieldLabel: getLabelFromMap('acCmbPrioridadId',helpMap,'Prioridad'),
	           			   tooltip:getToolTipFromMap('acCmbPrioridadId',helpMap,'Prioridades'),
		                   hasHelpIcon:getHelpIconFromMap('acCmbPrioridadId',helpMap),
		 				   Ayuda:getHelpTextFromMap('acCmbPrioridadId',helpMap),
	                       store: storePrioridades,
	                       displayField:'descripcion',
	                       valueField:'codigo',
	                       hiddenName: 'cdpriord',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 200,
	                       allowBlank: false, 
	         			   //blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
	                       emptyText:'Seleccione Prioridad...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},            		
            		{
            		layout: 'form',  
           			labelAlign: 'top',
           			labelWidth: 100,            			
            		items:[
            			{
            			xtype:'textarea',
	            		id: 'acTAMetodoContactoId',
						fieldLabel: getLabelFromMap('acTAMetodoContactoId',helpMap,'M&eacute;todo de Contacto'),
						tooltip:getToolTipFromMap('acTAMetodoContactoId',helpMap,'M&eacute;todo de Contacto'),
		                hasHelpIcon:getHelpIconFromMap('acTAMetodoContactoId',helpMap),
		 				Ayuda:getHelpTextFromMap('acTAMetodoContactoId',helpMap),
						name: 'dsmetcontac',
						//anchor: '100%'
						width:200
            		}]
            		},
            		{
            		layout: 'form',
            		labelAlign: 'top',  
           			labelWidth: 70, 
           			colspan:2,
            		items:[
            			{
	            		xtype:'textarea',
	            		id: 'acTAMetodoObservacionId',
						fieldLabel: getLabelFromMap('acTAMetodoObservacionId',helpMap,'Observaci&oacute;n'),
						tooltip:getToolTipFromMap('acTAMetodoObservacionId',helpMap,'Observaci&oacute;n'),
		                hasHelpIcon:getHelpIconFromMap('acTAMetodoObservacionId',helpMap),
		 				Ayuda:getHelpTextFromMap('acTAMetodoObservacionId',helpMap),
						name: 'observacion',
						//anchor: '100%'
						width:200
            		}]
            		}
            	]            	
	        
	});

			        
	formPanel.render();
	gridUR.render();
	
		storeTareas.load({
					params:{cdUniEco: Ext.getCmp('cduniecoHiddenId').getValue(),
							cdRamo: Ext.getCmp('cdramoHiddenId').getValue(),
							cdElemento: CDELEMEN
							},
					callback:function(){
							storePrioridades.load();
					}
				});
	
	
	storePersonaCaso.load({
	                         params:{pv_cdperson_i: CDPERSON},
	                         callback:function(r, o, success){
	                         	if (storePersonaCaso.reader.jsonData.success){
									Ext.getCmp('acTFClaveCWId').setValue(storePersonaCaso.reader.jsonData.MPersonaList[0].cdPerson);
								 	Ext.getCmp('acTFNomCliId').setValue(storePersonaCaso.reader.jsonData.MPersonaList[0].dsNombre);
								 	Ext.getCmp('acTFNroNominaId').setValue(storePersonaCaso.reader.jsonData.MPersonaList[0].cdIdeper);
								 	Ext.getCmp('acTFCorpoId').setValue(storePersonaCaso.reader.jsonData.MPersonaList[0].cdElemento);
								 	//Ext.getCmp('acTFCorpoId').setValue(storePersonaCaso.reader.jsonData.MPersonaList[0].corpo);
	                         	}else{
	                         	 //   Ext.Msg.alert("no setea porque viene el success falso");
	                         	}
							 }
	                      });
							
	
	
	
	Ext.getCmp("acTFPolizaId").setDisabled(true);
	Ext.getCmp("acTFIncisoId").setDisabled(true);
	
    if (CDPERSON == ""){
				        Ext.getCmp("acBtnVerPol").setDisabled(true);
				        
				        }	
	var fechaHoy = new Date();
	
	function guardarDatos(_cdfo){		
		var  _params = "";
		
		//Datos del encabezado		
		 _params += "&MCasoListVO[0].cdMatriz=" + ((Ext.getCmp("cdmatrizHiddenId").getValue() != undefined)?Ext.getCmp("cdmatrizHiddenId").getValue():"");
		 //La variable CDUSUARIO es temporal
		 _params += "&MCasoListVO[0].cdUsuario=" + CDUSUARIO;
		 _params += "&MCasoListVO[0].cdUnieco=" + ((Ext.getCmp("cduniecoHiddenId").getValue() != undefined)?Ext.getCmp("cduniecoHiddenId").getValue():"");
		 _params += "&MCasoListVO[0].cdRamo=" + ((Ext.getCmp("cdramoHiddenId").getValue() != undefined)?Ext.getCmp("cdramoHiddenId").getValue():"");
		 _params += "&MCasoListVO[0].estado=" + ((Ext.getCmp("estadoHiddenId").getValue() != undefined)?Ext.getCmp("estadoHiddenId").getValue():"");
		 _params += "&MCasoListVO[0].nmsituac=" + ((Ext.getCmp("nmsituaextHiddenId").getValue() != undefined)?Ext.getCmp("nmsituaextHiddenId").getValue():"");
		 _params += "&MCasoListVO[0].nmsituaext=" + ((Ext.getCmp("nmsituaextHiddenId").getValue() != undefined)?Ext.getCmp("nmsituaextHiddenId").getValue():"");
		 _params += "&MCasoListVO[0].nmsbsitext=" + ((Ext.getCmp("nmsbsitextHiddenId").getValue() != undefined)?Ext.getCmp("nmsbsitextHiddenId").getValue():"");
		 _params += "&MCasoListVO[0].nmpoliza=" + ((Ext.getCmp("nmpolizaHiddenId").getValue() != undefined)?Ext.getCmp("nmpolizaHiddenId").getValue():"");
		 _params += "&MCasoListVO[0].nmpoliex=" + ((Ext.getCmp("nmpoliexHiddenId").getValue() != undefined)?Ext.getCmp("nmpoliexHiddenId").getValue():"");
		 _params += "&MCasoListVO[0].cdPerson=" + CDPERSON;
		 _params += "&MCasoListVO[0].dsmetcontac=" + ((Ext.getCmp("acTAMetodoContactoId").getValue() != undefined)?Ext.getCmp("acTAMetodoContactoId").getValue():"");
		 _params += "&MCasoListVO[0].indPoliza=" + ((Ext.getCmp("acChkbxPolizaId").getValue() != undefined)?Ext.getCmp("acChkbxPolizaId").getValue():"");
		 _params += "&MCasoListVO[0].cdPrioridad=" + ((Ext.getCmp("acCmbPrioridadId").getValue() != undefined)?Ext.getCmp("acCmbPrioridadId").getValue():"");
		 _params += "&MCasoListVO[0].cdProceso=" + ((Ext.getCmp("cdprocesoHiddenId").getValue() != undefined)?Ext.getCmp("cdprocesoHiddenId").getValue():"");
		 _params += "&MCasoListVO[0].dsObservacion=" + ((Ext.getCmp("acTAMetodoObservacionId").getValue() != undefined)?Ext.getCmp("acTAMetodoObservacionId").getValue():"");
		 
		
	  //datos de los usuarios responsables
	  var strUsuariosSeg = "";
	  var _listaUsuariosRes = storeUResponsables.reader.jsonData.MUsuariosResponsablesList;
	  var i;
	  if(_listaUsuariosRes){
	  		for(i = 0; i < _listaUsuariosRes.length; i++){
		  		strUsuariosSeg += _listaUsuariosRes[i].cdUsuario+',';
	  		};
	  }	 	  
	  _params += "&strUsuariosSeg=" + strUsuariosSeg;
	  
	  _params = obtieneStringDatosVariables(_params,_cdfo);
	  	  	 
	 startMask(document.body,"Guardando datos...");
	 execConnection(_ACTION_GUARDAR_NUEVO_CASO, _params, cbkGuardarNuevoCaso);		
	}
	
	function obtieneStringDatosVariables(_params, _cdfo){
		var i = 0;
		var _form = window.frames["atributosVariables"].Ext.getCmp('dinamicFormPanelId');
		Ext.each(_form.getForm().items.items, function(campito){
			 _params += "&listaEmisionVO["+i+"].cdformatoorden="+_cdfo;
			 _params += "&listaEmisionVO["+i+"].cdSeccion="+campito.cdSeccion;
			 _params += "&listaEmisionVO["+i+"].cdAtribu="+campito.cdAtribu;
			 _params += "&listaEmisionVO["+i+"].otValor="+ ((campito.getValue() != undefined && campito.getValue() != null)?campito.getValue():"");
			i++;
		});
		return _params;
	}
	
function cbkGuardarNuevoCaso (_success, _message, _response) {
		endMask();
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			
			if (Ext.util.JSON.decode(_response).codigoMensaje == '')
			{
				Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400096', helpMap,'Caso Creado. N&uacute;mero ') + ' ' + _message + '. ');
			}
			else{
				Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400096', helpMap,'Caso Creado. N&uacute;mero ') + ' ' + _message+'. ' + getLabelFromMap(Ext.util.JSON.decode(_response).codigoMensaje, helpMap, 'No pudo enviarse la notificaci&oacute;n.'));
			}						

			Ext.getCmp('acTFNroCasoId').setValue(Ext.util.JSON.decode(_response).nmcaso);
			Ext.getCmp('acTFNroOrdenTId').setValue(Ext.util.JSON.decode(_response).cdordentrabajo);
			Ext.getCmp('acTFStatusId').setValue("ASIGNADO");
			Ext.getCmp('altCsBtnGrdr').setDisabled('true');
		}
	}	

});
			

function reloadGrid(_cdproceso,_cdmatriz){
		var _params = {cdProceso: _cdproceso,cdmatriz:_cdmatriz};		
											
		reloadComponentStore(Ext.getCmp('gridURId'), _params, cbkReload);
	}
	
function cbkReload(_r, _options, _success, _store) {
		if (!_success) {
			_store.removeAll();
			
			//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
			Ext.getCmp('altCsBtnGrdr').setDisabled(true);
			endMask();
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400092', helpMap,'No existen Usuarios Responsables para darle atención al Caso.'));
			
			
		}else{
			if(_BANDERA_OK == 0){
				//alert('BANDERA_OK = '+_BANDERA_OK);
				validarTiempoMatriz()}
			//Ext.getCmp('altCsBtnGrdr').setDisabled(false);
			}
		

	}
function desabilitar (){Ext.getCmp('altCsBtnGrdr').setDisabled(true);}
	
//function mensaje (){_BANDERA_OK = 1;Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400093', helpMap,'Deben existir campos disponibles en el Formato ligado a la Tarea.'));};

function validarTiempoMatriz(){
		 
	   var conn = new Ext.data.Connection();
	   //comenzarMask(Ext.getBody(), 'Cargando...');
	  // startMask(formPanel.id, getLabelFromMap('400023',helpMap,'Espere ...'));
	   conn.request({
				    	url: _ACTION_VALIDAR_TIEMPOS_MATRIZ,
				    	method: 'POST',
				    	params: {
				    				//nmcaso:formPanel.form.findField('nmcaso').getValue()
				    				cdmatriz: Ext.getCmp("cdmatrizHiddenId").getValue()
				    				
				    			},
	 					//waitMsg : getLabelFromMap('400017', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
                   		     	       // endMask();
										if (Ext.util.JSON.decode(response.responseText).success == false) {
                                		        Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),Ext.util.JSON.decode(response.responseText).errorMessages[0], function () {
                                		       Ext.getCmp('altCsBtnGrdr').setDisabled(true);
                                		        	// formPanel.form.findField('nmcaso').setValue("");
                                		        //formPanel.form.findField('nmcaso').focus();
                                		        });
                                		} else {
                                      //  Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){reloadGrid();});
                                		}
    						}
 	   })
	   }
	   
function finMask(){
	endMask();
}
/*var the_Mask_local = null;
function endMaskLocal(){
	the_Mask_local.hide();
	the_Mask_local = null;
}
function startMaskLocal(ctrlId, text) {
	if (the_Mask_local != null) {
		endMaskLocal();
	}
	the_Mask_local = new Ext.LoadMask(ctrlId, {msg: text, disabled: false});
	the_Mask_local.show();
	
}
*/



//Obtengo todos los usuarios responsables del caso
//Se quito por pedido de daniel
/*function getUsers(){
		var _params = "";
		
		var strUsuariosSeg = "";
	  	var _listaUsuariosRes = storeUResponsables.reader.jsonData.MUsuariosResponsablesList;
	  	var i;
	  	if(_listaUsuariosRes){
	  			for(i = 0; i < _listaUsuariosRes.length; i++){
			  		strUsuariosSeg += _listaUsuariosRes[i].email+',';
	  			};
	  	}	 	  
	  return _params += "&strUsuariosSeg=" + strUsuariosSeg;		
	}*/
	