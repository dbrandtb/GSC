function agregarEditar(_record,_cdFormatoOrden,_nmCaso,_nmovimiento,_cdOrdenTrabajo,_cdproceso, _dsproceso){

		/*if(_record){
				var _params = "";				
				_params += "&nmcaso="+_nmCaso;
				_params += "&nmovimiento="+_nmovimiento;
				_params += "&cdstatus="+ _record.get('cdStatus');
		
			var connValidar = new Ext.data.Connection();
			connValidar.request({
				url: _ACTION_VALIDAR_INGRESO_ANEXO,
				method: 'POST',
				successProperty : '@success',
				params : params,
	            callback: function (options, success, response) {
	            			var respuesta=Ext.util.JSON.decode(response.responseText).respuesta;
	                       		if(!Ext.isEmpty(respuesta))alert(respuesta); 
	            }
			});
		}*/
var today = new Date();
		var mesCabecera =  today.getMonth() + 1;
		mesCabecera = (mesCabecera<10)?'0'+mesCabecera:mesCabecera+'';
		var minutos = today.getMinutes();
		minutos = (minutos<10)?'0'+minutos:minutos+'';	
		var todayCabecera = today.getDate()+'/'+mesCabecera+'/'+today.getFullYear()+' '+ today.getHours()+':'+minutos;				
	   
		//var todayCabeceraMas1 = today.getDate()+'/'+mesCabecera+'/'+(today.getFullYear()+1) +' '+ today.getHours()+':'+minutos;
			
	var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('codigoId',helpMap,'Codigo'),
        tooltip: getToolTipFromMap('codigoId',helpMap,'Codigo'),
        dataIndex: 'cdUsuario',
        width: 100,
        sortable: true
        },
        {
        header: getLabelFromMap('nombreId',helpMap,'Nombre'),
        tooltip: getToolTipFromMap('nombreId',helpMap,'Nombre'),
        dataIndex: 'desUsuario',
        width: 220,
        sortable: true
        },
        {
        header: getLabelFromMap('rolId',helpMap,'Rol'),
        tooltip: getToolTipFromMap('rolId',helpMap,'Rol'),
        dataIndex: 'desRolmat',
        width: 150,
        sortable: true
        },
        {dataIndex: 'cdRolmat', hidden:true},
        {dataIndex: 'cdMatriz', hidden:true}        
	]);

	var gridUR = new Ext.grid.GridPanel({
       		id: 'gridURId',
            store: storeGridUsrMConsulta,
            border:true,
            cm: cm,
	        successProperty: 'success',            
            width:495,
            frame:true,
            height:200,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeGridUsrMConsulta,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
	//FORMULARIO PRINCIPAL ************************************
	var formPanel = new Ext.FormPanel({			
	        //el:'encabezadoFijo',
	        id: 'acFormPanelId',
	        iconCls:'logo',
	        bodyStyle:{position:'relative',background: 'white'},
	        buttonAlign: "right",
	        labelAlign: 'right',
	        frame:true,
	        width: 578,
	        //autoHeight:true,
	        height:400,
	        autoScroll:true,
	        layout: 'table',
            layoutConfig: { columns: 3, columnWidth: .33},
            //labelWidth: 120,
            //store: _storeEncabezado,
            //reader: _readerEncabezado,
            //url: _ACTION_OBTENER_ENCABEZADO,
            items:[ 
            		{
            		html: '<span class="x-form-item" style="font-weight:bold">DATOS DEL CASO</span>',
            		colspan:3
            		},
            		/*{
            		layout:'table',
            		id:'tableId11',
            		layoutConfig: { columns: 3, columnWidth: .33},
            		colspan:3,
            		items:bloqueDinamico11
            		},
            		{
            		layout:'table',
            		id:'tableId10',
            		layoutConfig: { columns: 3, columnWidth: .33},
            		colspan:3,
            		items:bloqueDinamico10
            		},*/
            		{
            		html: '<br/>',
            		colspan:3
            		},
            		{
            		layout: 'table',
            		id:'dinamico',
            		layoutConfig: {columns: 2, align: 'left'}, 
            		colspan:3,            			
           			items:ATTS_DINAMICOS 
            		}, 
            		{
            		html: '<span class="x-form-item" style="font-weight:bold">DATOS DEL MOVIMIENTO</span>',
            		colspan:3
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 46,       			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'cmbPrioridadId',
	                       fieldLabel: getLabelFromMap('cmbPrioridadId',helpMap,'Prioridad'),
	           			   tooltip:getToolTipFromMap('cmbPrioridadId',helpMap,'Prioridad'),
	           			   hasHelpIcon:getHelpIconFromMap('cmbPrioridadId',helpMap),								 
   						   Ayuda: getHelpTextFromMap('cmbPrioridadId',helpMap),
	                       store: obtenerStoreParaCombo("CATBOPRIOR"),
	                       displayField:'descripcion',
	                       valueField:'codigo',
	                       hiddenName: 'cdPrioridad',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       width: 104,
	                       allowBlank: false,
	                       emptyText:'...',
	                       selectOnFocus:true,
	                       forceSelection:true
					     }
           				]
            		},
            		{            		
           			layout: 'form',
           			labelWidth: 75,           			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: 'txtMovimientoId',
					        fieldLabel: getLabelFromMap('txtMovimientoId',helpMap,'Movimiento'),
					        tooltip:getToolTipFromMap('txtMovimientoId',helpMap,'Movimiento'), 	
					        hasHelpIcon:getHelpIconFromMap('txtMovimientoId',helpMap),								 
   						    Ayuda: getHelpTextFromMap('txtMovimientoId',helpMap),
					        name: 'nmovimiento',
					        disabled: true,
					        width: 75
					     }
           				]
            		},
            		{
            		layout: 'form',
            		labelWidth: 75,
            		items: [
           				{
           					xtype:'textfield',
		            		id: 'txtModuloId',
					        fieldLabel: getLabelFromMap('txtModuloId',helpMap,'Modulo'),
					        tooltip:getToolTipFromMap('txtModuloId',helpMap,'Modulo'), 	 
					        hasHelpIcon:getHelpIconFromMap('txtModuloId',helpMap),								 
   						    Ayuda: getHelpTextFromMap('txtModuloId',helpMap),
					        name: 'cdModulo',
					        //disabled: true,
					        width: 100
					     }
           				]
            		},
            		{            		
           			layout: 'form',  
           			labelWidth: 46,       			            		
           			items: [
           				{
           				   xtype: 'combo',
           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                       id:'cmbEstatusId',
	                       fieldLabel: getLabelFromMap('cmbEstatusId',helpMap,'Estatus'),
	           			   tooltip:getToolTipFromMap('cmbEstatusId',helpMap,'Estatus'),
	           			   hasHelpIcon:getHelpIconFromMap('cmbEstatusId',helpMap),								 
   						   Ayuda: getHelpTextFromMap('cmbEstatusId',helpMap),
	                       store: storeComboStatus,
	                       displayField:'descripcion',
	                       valueField:'codigo',
	                       hiddenName: 'cdStatus',
	                       typeAhead: true,
	                       mode: 'local',
	                       triggerAction: 'all',
	                       forceSelection:true,
	                       width: 104,
	                       allowBlank: false,
	                       emptyText:'...',
	                       selectOnFocus:true
	                      
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 75,          			
           			items: [
           				{
           					xtype:'textfield',
		            		id: 'txtFechaIngresoId',
					        fieldLabel: getLabelFromMap('txtFechaIngresoId',helpMap,'Fecha de Ingreso'),
					        tooltip:getToolTipFromMap('txtFechaIngresoId',helpMap,'Fecha de Ingreso'), 
 							hasHelpIcon:getHelpIconFromMap('txtFechaIngresoId',helpMap),								 
   							Ayuda: getHelpTextFromMap('txtFechaIngresoId',helpMap),     
   						
					        name: 'feRegistro',
					        disabled: true,
					        width: 75,
					        value:todayCabecera 
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 75,          			
           			items: [
           				{
           					xtype:'textfield',
		            		id: 'txtNivelAtencionId',
					        fieldLabel: getLabelFromMap('txtNivelAtencionId',helpMap,'Nivel de Atenci&oacute;n'),
					        tooltip:getToolTipFromMap('txtNivelAtencionId',helpMap,'Nivel de Atenci&oacute;n'), 	
					        hasHelpIcon:getHelpIconFromMap('txtNivelAtencionId',helpMap),								 
   							Ayuda: getHelpTextFromMap('txtNivelAtencionId',helpMap),        
					        name: 'cdNivatn',
					        //disabled: true,
					        width: 100
					     }
           				]
            		},
            		{
		            		layout: 'form',
		            		colspan: 3,
		            		labelWidth: 75,          			
		           			items: [
		           				{
		           					xtype:'textarea',
				            		id: 'txtObservacionId',
							        fieldLabel: getLabelFromMap('txtObservacionId',helpMap,'Observaci&oacute;n'),
							        tooltip:getToolTipFromMap('txtObservacionId',helpMap,'Observaci&oacute;n'), 	    
							        hasHelpIcon:getHelpIconFromMap('txtObservacionId',helpMap),								 
		   							Ayuda: getHelpTextFromMap('txtObservacionId',helpMap), 
							        name: 'observacion',
							        //allowBlank: false,
							        allowBlank:((_record==null)?false:true),
							        width: 390
							     }
		           				]
		            },
		            {
            		layout: 'column',
					colspan: 3,
            		columnWidth: 1.0,
            		items: [
		            		
            				{
		            		layout: 'form', 
		            		labelWidth: 0,
		            		columnWidth:.15,
		            		buttonAlign: "right",
		           			items: [
            		
            					{
			           			xtype: 'textfield',
			           			width:0,
	           					hideLabel: true,
	           					labelSeparator: '',
						        fieldLabel: '',
						        hidden:true
						        
					        	}]
					        },
            				{
		            		layout: 'form',  
		            		columnWidth:.20,
		            		buttonAlign: "right",
		           			items: [
		           			
		           				{	xtype:'button',
		           					text: 'Archivos',
		           					disabled: Ext.isEmpty(_nmovimiento)?true:false,
				                    id:'ArchivosMov',                                       
				                    name: 'ArchivosMov', 
				                    buttonAlign: "right",                        
				                    handler: function(){
				                        //alert("valores _nmcaso: "+_nmCaso+" _cdproceso: "+_cdproceso+" _nmovimiento:"+_nmovimiento);
				                        archivosPorMovimiento(_nmCaso,_dsproceso,_nmovimiento);
				                        
				                    }
				                }
		           				]
			            	},
		            		{
		            		layout: 'form',  
		            		columnWidth:.60,
		            		//labelWidth: 300,          			
		           			items: [
		           				{
		           					xtype:'textfield',
				            		id: 'txtRegistradoPorId',
							        fieldLabel: getLabelFromMap('txtRegistradoPorId',helpMap,'Registrado por'),
							        tooltip:getToolTipFromMap('txtRegistradoPorId',helpMap,'Registrado por'), 	
							        hasHelpIcon:getHelpIconFromMap('txtRegistradoPorId',helpMap),								 
		   							Ayuda: getHelpTextFromMap('txtRegistradoPorId',helpMap), 
							        name: 'desUsuario',
							        disabled: true,
							        width: 165
							     }
		           				]
		            		}
		            	]
		            },            		
            		{
            		layout:'table',
            		id:'tableId3',
            		layoutConfig: { columns: 3, columnWidth: .33},
            		colspan:3,
            		items:[
            			{
	            		html: '<br/><span class="x-form-item" style="font-weight:bold">USUARIOS RESPONSABLES</span>',
	            		colspan:3
	            		},
            			{
	            		layout: 'form',  
	           			labelWidth: 70, 
	           			colspan:3,
	            		items:[gridUR]
            			}
            		]
            		}
            	]
	        
	});
	

	// ************************************************************************************************************************************
	var ventana = new Ext.Window ({
	id: 'windowIngresarMovId',
    title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('96',helpMap,'Consultar o Ingresar Movimiento')+'</span>',
    width: 585,
    modal: true,
    //height: 516,
    //autoHeight:true,
    items: formPanel,
    buttonAlign: "center",
	buttons:[{
		text:getLabelFromMap('btnGuardarId',helpMap,'Guardar'),
		tooltip: getToolTipFromMap('btnGuardarId',helpMap,'Guardar un nuevo movimiento'),
		handler: function() {
   			//if (formaPanel.form.isValid()) {
   			        
                    //guardarDatos(_cdFormatoOrden);
                    //reloadGrid();
                    validaStatusCaso();
                    
			/*} else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
			}*/
		}
		},{
		text:getLabelFromMap('btnRegresarId',helpMap,'Regresar'),
		tooltip: getToolTipFromMap('btnRegresarId',helpMap,'Regresar a la pantalla anterior'),                              
		handler: function() {
			ventana.close();
		}
		}
	]	
	});

	/*if(_cdFormatoOrden==10){
		Ext.getCmp("tableId11").hide();
		Ext.getCmp("tableId10").show();
		Ext.getCmp("10_100_1").setValue(_nmCaso);
		mapearDatos(_cdOrdenTrabajo,_cdFormatoOrden);
		storeProducto.load({
			callback:function(){
				Ext.getCmp('10_100_2').setValue(storeProducto.reader.jsonData.elementosComboBox[0].descripcion);
				storePlanesProducto.load({
				params:{cdRamo: Ext.getCmp('10_100_2').getValue()}
			});
			}
		});		
	}
	else if(_cdFormatoOrden==11){
			Ext.getCmp("tableId11").show();
			Ext.getCmp("tableId10").hide();
			Ext.getCmp("11_101_1").setValue(_nmCaso);
			mapearDatos(_cdOrdenTrabajo,_cdFormatoOrden);
			storeProducto.load({
				callback:function(){
					Ext.getCmp('11_101_2').setValue(storeProducto.reader.jsonData.elementosComboBox[0].descripcion);
					storePlanesProducto.load({
					params:{cdRamo: Ext.getCmp('11_101_2').getValue()}
				});
				}
			});
		}*/
	
	mapearDatos(_cdOrdenTrabajo,_cdFormatoOrden);
		
	var fechaHoy = new Date();	
	//Ext.getCmp('11_101_4').setValue(fechaHoy);
	var fechaDespues = new Date(fechaHoy.setDate(fechaHoy.getDate() + 365));
	//Ext.getCmp('11_101_5').setValue(fechaDespues);
	
	var fechaHoy = new Date();	
	//Ext.getCmp('10_100_4').setValue(fechaHoy);
	var fechaDespues = new Date(fechaHoy.setDate(fechaHoy.getDate() + 365));
	//Ext.getCmp('10_100_5').setValue(fechaDespues);
	
	/*var fecha = new Date();
	var mes = fecha.getMonth() + 1;
	mes = (mes<10)?'0'+mes:mes+'';
	var minutos = fecha.getMinutes();		
	minutos = (minutos<10)?'0'+minutos:minutos+'';	
	var fechaHoraMinuto = fecha.getDate()+'/'+mes+'/'+fecha.getDate()+'  '+fecha.getHours()+':'+minutos;
			
			
	*/
	
	//Ext.getCmp('11_101_6').setValue(fechaHoraMinuto);
	//Ext.getCmp('10_100_6').setValue(fechaHoraMinuto);*/
	
	//
	//Ext.getCmp('txtFechaIngresoId').setValue(fechaHoraMinuto);
	//alert(storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].dsNivatn);
	//Ext.getCmp('cmbPrioridadId').setValue(storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].cdPrioridad);
	
	if(_nmovimiento){
		storeDatosMovimiento.load({
			params:{nmcaso:_nmCaso,nmovimiento:_nmovimiento},
			callback:function(){
				var _desPrioridad = storeDatosMovimiento.reader.jsonData.MListDatosMovimientoCaso[0].desPrioridad;
				var _nmovimiento = storeDatosMovimiento.reader.jsonData.MListDatosMovimientoCaso[0].nmovimiento;
				var _dsModulo = storeDatosMovimiento.reader.jsonData.MListDatosMovimientoCaso[0].desModulo;
				var _desEstatus = storeDatosMovimiento.reader.jsonData.MListDatosMovimientoCaso[0].desStatus;
				var _feRegistro = feFormato(storeDatosMovimiento.reader.jsonData.MListDatosMovimientoCaso[0].feRegistro);
				var _dsNivatn = storeDatosMovimiento.reader.jsonData.MListDatosMovimientoCaso[0].dsNivatn;
				var _observacion = storeDatosMovimiento.reader.jsonData.MListDatosMovimientoCaso[0].observacion;
				var _desUsuario = storeDatosMovimiento.reader.jsonData.MListDatosMovimientoCaso[0].desUsuario;
				
				Ext.getCmp('cmbPrioridadId').setValue(_desPrioridad);
				Ext.getCmp('cmbPrioridadId').setDisabled(true);
				
				Ext.getCmp('txtMovimientoId').setValue(_nmovimiento);
				Ext.getCmp('txtMovimientoId').setDisabled(true);
				
				Ext.getCmp('txtModuloId').setValue(_dsModulo);
				Ext.getCmp('txtModuloId').setDisabled(true);
				
				Ext.getCmp('cmbEstatusId').setValue(_desEstatus);
				Ext.getCmp('cmbEstatusId').setDisabled(true);
				
				Ext.getCmp('txtFechaIngresoId').setValue(_feRegistro);
				Ext.getCmp('txtFechaIngresoId').setDisabled(true);
				
				Ext.getCmp('txtNivelAtencionId').setValue(_dsNivatn);
				Ext.getCmp('txtNivelAtencionId').setDisabled(true);
				
				Ext.getCmp('txtObservacionId').setValue(_observacion);
				Ext.getCmp('txtObservacionId').setDisabled(true);
				Ext.getCmp('txtObservacionId').allowBlank=true;
				
				Ext.getCmp('txtRegistradoPorId').setValue(_desUsuario);
				Ext.getCmp('txtRegistradoPorId').setDisabled(true);
				
				
			}
		});	
	}else{
		storeEncMovimientosCaso.load({
			params:{nmcaso:_nmCaso},
			callback:function(){
				var _nivelAtencion = storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].dsNivatn;
				var _desModulo= storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].desModulo;
				//var _modulo = storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].desModulo;
				//console.log(storeEncMovimientosCaso.reader.jsonData);
				//alert();
	
				Ext.getCmp('txtModuloId').setValue(_desModulo);
				Ext.getCmp('txtModuloId').setDisabled(true);
				
				Ext.getCmp('txtNivelAtencionId').setValue(_nivelAtencion);
				Ext.getCmp('txtNivelAtencionId').setDisabled(true);
			
			}
		});
	}
	
	/*storeGridUsrMCaso.load({
					params:{pv_nmcaso_i: _nmCaso, cdmatriz: storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].cdMatriz}
				});*/
				
	//function reloadGrid(_nmCaso, _cdMatriz){
	/*function reloadGrid2(_nmCaso, _cdMatriz){
		var _params = {pv_nmcaso_i: _nmCaso, cdmatriz:_cdMatriz};		
											
		reloadComponentStore(Ext.getCmp('gridURId'), _params, cbkReload);
	}*/
	
	function reloadGrid2(_nmCaso, _nMovimiento){
		var _params = {pv_nmcaso_i: _nmCaso, pv_nmovimiento_i: _nMovimiento};		
											
		reloadComponentStore(Ext.getCmp('gridURId'), _params, cbkReload);
	}
	
	
	function cbkReload(_r, _options, _success, _store) {
		if (!_success) {
			_store.removeAll();
			//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		}
	}
	
	//reloadGrid2(_nmCaso,storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].cdMatriz);		

	
	reloadGrid2(_nmCaso,_nmovimiento);
	//***************************************************************************************
	ventana.show();	
	
	
	function mapearDatos(_cdOrdenTrabajo,_formatoordentrabajo){
			storeDatosAtributosVariables.load({
					params: {
							 cdordentrabajo: _cdOrdenTrabajo,
							 limit: 999
							 },
					callback:function(){					
						var _atributos = (storeDatosAtributosVariables.reader.jsonData.MListaSeccionesOrden)?storeDatosAtributosVariables.reader.jsonData.MListaSeccionesOrden:0;						
						Ext.each(_atributos, function(valor){
							Ext.getCmp(_formatoordentrabajo+'_'+valor.cdseccion+'_'+valor.cdatribu).setValue(valor.otvalor);
						    
						});						
					}
			});
	}
	
	
	function guardarDatos(codigo){
		
		if (formPanel.form.isValid()){
                var  _params = "";		
				if(_nmovimiento){//GUARDAR CUANDO SE EDITA
					 _params += "&ListMCasoMovVO[0].cdMatriz="+storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].cdMatriz;
					 //La variable CDUSUARIO es temporal
					 _params += "&listMCasoMovVO[0].nmCaso="+_nmCaso;
					 _params += "&listMCasoMovVO[0].cdPrioridad="+storeDatosMovimiento.reader.jsonData.MListDatosMovimientoCaso[0].cdPrioridad;
					 _params += "&listMCasoMovVO[0].cdStatus="+storeDatosMovimiento.reader.jsonData.MListDatosMovimientoCaso[0].cdStatus;
					 _params += "&listMCasoMovVO[0].observacion="+Ext.getCmp("txtObservacionId").getValue();
					 _params += "&listMCasoMovVO[0].cdUsuario="+CDUSUARIO;		
				}else{//GUARDAR CUANDO SE AGREGA
					 _params += "&ListMCasoMovVO[0].cdMatriz="+storeEncMovimientosCaso.reader.jsonData.MListEncMovimientosCaso[0].cdMatriz;
					 //La variable CDUSUARIO es temporal
					 _params += "&listMCasoMovVO[0].nmCaso="+_nmCaso;
					 _params += "&listMCasoMovVO[0].cdPrioridad="+Ext.getCmp("cmbPrioridadId").getValue();
					 _params += "&listMCasoMovVO[0].cdStatus="+Ext.getCmp("cmbEstatusId").getValue();
					 _params += "&listMCasoMovVO[0].observacion="+Ext.getCmp("txtObservacionId").getValue();
					 _params += "&listMCasoMovVO[0].cdUsuario="+CDUSUARIO;			
				}
				_params += "&nmcaso="+_nmCaso;
				_params += "&dsStatus="+Ext.getCmp('cmbEstatusId').getRawValue();
				
				startMask(formPanel.id,"Guardando Datos...");
				 			  
				 
				 
				 
		var connGuardarMov = new Ext.data.Connection();
		connGuardarMov.request({
			url: _ACTION_GUARDAR_NUEVO_MOVIMIENTO,
			method: 'POST',
			successProperty : '@success',
			params : _params,
                          	callback: function (options, success, response) {
                       					//if (CODIGO_PERSONA == "") CODIGO_PERSONA = Ext.util.JSON.decode(response.responseText).codigoPersona; 
                          				if (Ext.util.JSON.decode(response.responseText).success == false) {
                          					var error = Ext.util.JSON.decode(response.responseText).errorMessages[0];
                          					Ext.Msg.alert('Error', error);
                          				} else {
                          					cbkGuardarNuevoCaso(Ext.util.JSON.decode(response.responseText).actionMessages[0],Ext.util.JSON.decode(response.responseText).nmovimiento);
                          				}
                          			}
		});
				 
				//execConnection(_ACTION_GUARDAR_NUEVO_MOVIMIENTO, _params, cbkGuardarNuevoCaso);
		}else{
				Ext.Msg.alert('Aviso', "Complete la información requerida.");
		}
	}
	
	function cbkGuardarNuevoCaso (_message,nmovimientoGenerado) {
		
		try{
	    	endMask();
	    }catch(e){
	    	
	    }
		/*
		//alert('msg2:' +_message);
		//alert(_response);
	    try{
	    	endMask();
	    }catch(e){
	    	
	    }
	    
		if (!_success) {
			if(!Ext.isEmpty(response))Ext.Msg.alert('Error', _message);
			
			
		}else {*/
		
			Ext.Msg.alert('Aviso', _message,function(){reloadGrid2()});
			
			if(Ext.isEmpty(_nmovimiento)){
				Ext.Msg.show({
				   title:'',
				   msg: '¿Desea Anexar archivos a este movimiento?',
				   buttons: Ext.Msg.OKCANCEL,
				   fn: function(btn, text){
						//Recarga el form de búsqueda principal
	               		//recargarDatos(Ext.getCmp('formPanelId'));
					    if (btn == 'ok'){
					    	//ventana.close();
					        //enviarCorreo("", _nmCaso, "M", getUsers())//Se cambio por pedido de Daniel
					        //enviarCorreo("", _nmCaso, TIPOENVIO);
					         archivosPorMovimientoAll(_nmCaso,_cdproceso,_dsproceso,nmovimientoGenerado, mensajeEnviarMail);
					    }else{
					    
					          ventana.close();
					          mensajeEnviarMail(_nmCaso,_cdproceso);
					        
					    }			    
					    
		          },
				  icon: Ext.MessageBox.QUESTION
				    });
			}else {
					mensajeEnviarMail(_nmcaso,_cdproceso);
			}
			
		
		
		/*
			bbar: new Ext.PagingToolbar({
					pageSize:itemsPerPage,
					store: storeGridMovimientos,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    }) 
		*/
	}
	function feFormato(val) {
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
              	return fecha.format('d/m/Y');
              }
         }
         
	function mensajeEnviarMail(numeroCaso, cdProceso){
	
	
		Ext.Msg.show({
				   title:'',
				   msg: '¿Confirme que desea enviar informacion al cliente?',
				   buttons: Ext.Msg.OKCANCEL,
				   fn: function(btn, text){
						//Recarga el form de búsqueda principal
	               		recargarDatos(Ext.getCmp('formPanelId'));
					    if (btn == 'ok'){
					    	if(Ext.getCmp('windowIngresarMovId')) Ext.getCmp('windowIngresarMovId').close();
					        //enviarCorreo("", _nmCaso, "M", getUsers())//Se cambio por pedido de Daniel
					        //enviarCorreo("", _nmCaso, TIPOENVIO);
					        enviarCorreo(numeroCaso, cdProceso);
					    }else{
					    
					          if(Ext.getCmp('windowIngresarMovId')) Ext.getCmp('windowIngresarMovId').close();
					        
					    }			    
					    
		          },
				   //animEl: 'elId',
				   icon: Ext.MessageBox.QUESTION
				    });
			    
	}
//Obtengo todos los usuarios responsables del caso
//Se quito por pedido de daniel
	/*function getUsers(){
		var _params = "";
		
		var strUsuariosSeg = "";
	  	var _listaUsuariosRes = storeGridUsrMConsulta.reader.jsonData.MListResponsablesMCaso;
	  	var i;
	  	if(_listaUsuariosRes){
	  			for(i = 0; i < _listaUsuariosRes.length; i++){
			  		strUsuariosSeg += _listaUsuariosRes[i].email+',';
	  			};
	  	}	 	  
	  return _params += "&strUsuariosSeg=" + strUsuariosSeg;		
	}*/		


// valida status de un caso

         
function validaStatusCaso()
    
{
        var params = {
       
                 nmCaso: _nmCaso
                        
             };
    
        execConnection (_VALIDA_SATUS_CASO, params, cbkValida);

}
function cbkValida (_success, _message) {
   
    //if(_message)alert(_message);
    if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
			guardarDatos(_cdFormatoOrden);
		}

}               
        



}