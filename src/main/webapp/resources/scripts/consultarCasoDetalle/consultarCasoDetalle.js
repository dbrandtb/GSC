
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";			
	
	var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('codigoId',helpMap,'C&oacute;digo'),
        tooltip: getToolTipFromMap('codigoId',helpMap,'C&oacute;digo'),
        hasHelpIcon:getHelpIconFromMap('descripcionId',helpMap),
		Ayuda: getHelpTextFromMap('descripcionId',helpMap,''),  
        dataIndex: 'cdUsuario',
        width: 100,
        sortable: true
        },
        
        {
        header: getLabelFromMap('rolId',helpMap,'Nombre'),
        tooltip: getToolTipFromMap('rolId',helpMap,'Nombre'),
        hasHelpIcon:getHelpIconFromMap('rolId',helpMap),
		Ayuda: getHelpTextFromMap('rolId',helpMap,''),  
        dataIndex: 'desUsuario',
        width: 220,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDsMetEnvNtfcn',helpMap,'Rol'),
        tooltip: getToolTipFromMap('cmDsMetEnvNtfcn',helpMap,'Rol'),
        hasHelpIcon:getHelpIconFromMap('cmDsMetEnvNtfcn',helpMap),
		Ayuda: getHelpTextFromMap('cmDsMetEnvNtfcn',helpMap,''),  
        dataIndex: 'desRolmat',
        width: 150,
        sortable: true
        },
        {dataIndex: 'cdRolmat', hidden:true}        
	]);

	var gridUR = new Ext.grid.GridPanel({
       		id: 'gridURId',
            store: storeCasoUsr,
            border:true,
            cm: cm,
	        successProperty: 'success',            
            width:500,
            frame:true,
            height:200,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeCasoUsr,
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
     var movimiento = new Ext.form.Hidden({
     	id: 'movimientoId',
     	name:'nmovimiento'
     });
	
	var colorImg="";
	
	//FORMULARIO PRINCIPAL ************************************
	var formPanel = new Ext.FormPanel({			
	        el:'encabezadoFijo',
	        id: 'acFormPanelId',	        
	        title: '<span style="color:black;font-size:12px;">Consulta de Caso</span>',
	        iconCls:'logo',
	        bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,
	        width: 790,
	        autoHeight:true,
	        layout: 'table',
            layoutConfig: { columns: 3, columnWidth: .33},
            store: storeEncCasoDetalle,
            reader: readerEncCasoDetalle,
            url: _ACTION_GET_ENC_CASO_DETALLE,
            items:[
            		{xtype:'hidden', name:'cdformatoorden',id:'cdformatoordenId'},
            		{xtype:'hidden', name:'cdnivatn',id:'cdnivatn'},
            		{xtype:'hidden', name:'cdproceso',id:'cdproceso'},
            		//{xtype:'hidden', name:'cdusuario',id:'cdusuario'},
            		{            		
           			layout: 'form',
           			colspan:3,
           			      			            		
           			items: [
           				{xtype:'hidden', name:'cdusuario',id:'cdusuario'
					     }
           				]
            		},
            		{            		
           			layout: 'form',
           			labelWidth: 100,           			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: 'dsproceso',
					        fieldLabel: getLabelFromMap('dsproceso',helpMap,'Tarea'),
					        tooltip:getToolTipFromMap('dsproceso',helpMap,'Tarea'), 	     
					         hasHelpIcon:getHelpIconFromMap('descripcionId',helpMap),
		                    Ayuda: getHelpTextFromMap('descripcionId',helpMap,''),     
					        name: 'dsproceso',
					        disabled: true,
					        width: 220
					     }
           				]
            		},
            		{
            		layout: 'form',
            		labelWidth: 100,
            		items: [
           				{
           					xtype:'textfield',
		            		id: 'txtPrioridadId',
					        fieldLabel: getLabelFromMap('txtPrioridadId',helpMap,'Prioridad'),
					        tooltip:getToolTipFromMap('txtPrioridadId',helpMap,'Prioridad'), 
					        hasHelpIcon:getHelpIconFromMap('txtPrioridadId',helpMap),
		                    Ayuda: getHelpTextFromMap('txtPrioridadId',helpMap,''),     
					        name: 'dspriord',
					        disabled: true,
					        width: 110
					     }
           				]
            		},					
            		{
            		layout: 'form',  
            		labelWidth: 90,          			
           			items: [
           				{
           					xtype:'textfield',
		            		id: 'txtNivelAtencionId',
					        fieldLabel: getLabelFromMap('txtNivelAtencionId',helpMap,'Nivel de Atenci&oacute;n'),
					        tooltip:getToolTipFromMap('txtNivelAtencionId',helpMap,'Nivel de Atenci&oacute;n'), 	
					        hasHelpIcon:getHelpIconFromMap('txtNivelAtencionId',helpMap),
		                    Ayuda: getHelpTextFromMap('txtNivelAtencionId',helpMap,''),          
					        name: 'dsnivatn',
					        disabled: true,
					        width: 110           					
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 100,          			
           			items: [
           				{
           					xtype:'textfield',
		            		id: 'txtNroCasoId',
					        fieldLabel: getLabelFromMap('txtNroCasoId',helpMap,'N&uacute;mero de Caso'),
					        tooltip:getToolTipFromMap('txtNroCasoId',helpMap,'N&uacute;mero de Caso'), 	 
					        hasHelpIcon:getHelpIconFromMap('txtNroCasoId',helpMap),
		                    Ayuda: getHelpTextFromMap('txtNroCasoId',helpMap,''),         
					        name: 'nmcaso',
					        disabled: true,
					        width: 120
					     }
           				]
            		},            		
					{            		
           			layout: 'form',
           			labelWidth: 100,           			            		
           			items: [
           				{
           					
           					xtype: 'textfield',
           					id: 'txtAseguradoraId',
					        fieldLabel: getLabelFromMap('txtAseguradoraId',helpMap,'Aseguradoras'),
					        tooltip:getToolTipFromMap('txtAseguradoraId',helpMap,'Aseguradora'), 	
					        hasHelpIcon:getHelpIconFromMap('txtAseguradoraId',helpMap),
		                    Ayuda: getHelpTextFromMap('txtAseguradoraId',helpMap,''),          
					        name: 'dsunieco',
					        disabled: true,
					        width: 140
					     }
           				]
            		},
            		{
            		layout: 'form',
            		labelWidth: 100,
            		            		items: [
           				{
           					html:'',
           					id: 'txtVigenteId'	
           			
					     }
           				]
            		},					
            		{
            		layout: 'form',  
            		labelWidth: 100,          			
           			items: [
           				{
           					xtype:'textfield',
		            		id: 'txtNroOrdenId',
					        fieldLabel: getLabelFromMap('txtNroOrdenId',helpMap,'N&uacute;mero de Orden'),
					        tooltip:getToolTipFromMap('txtNroOrdenId',helpMap,'N&uacute;mero de Orden'),
					        hasHelpIcon:getHelpIconFromMap('txtNroOrdenId',helpMap),
		                    Ayuda: getHelpTextFromMap('txtNroOrdenId',helpMap,''),   	        
					        name: 'cdordentrabajo',
					        disabled: true,
					        width: 120
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 100,          			
           			items: [
           				{
           					xtype:'textfield',
		            		id: 'txtModuloId',
					        fieldLabel: getLabelFromMap('txtModuloId',helpMap,'M&oacute;dulo'),
					        tooltip:getToolTipFromMap('txtModuloId',helpMap,'M&oacute;dulo'), 	    
					        hasHelpIcon:getHelpIconFromMap('txtModuloId',helpMap),
		                    Ayuda: getHelpTextFromMap('txtModuloId',helpMap,''),      
					        name: 'dsmodulo',
					        disabled: true,
					        width: 110
					     }
           				]
            		},
            		{            		
           			layout: 'form',
           			labelWidth: 90,  
           			colspan:3,         			            		
           			items: [
           				{
           					xtype: 'textfield',
           					id: 'txtEstatusId',
					        fieldLabel: getLabelFromMap('txtEstatusId',helpMap,'Estatus'),
					        tooltip:getToolTipFromMap('txtEstatusId',helpMap,'Estatus'), 	
					        hasHelpIcon:getHelpIconFromMap('txtEstatusId',helpMap),
		                    Ayuda: getHelpTextFromMap('txtEstatusId',helpMap,''),          
					        name: 'dsstatus',
					        disabled: true,
					        width: 110
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 100,          			
           			items: [
           				{
           					xtype:'textfield',
		            		id: 'txtTmpoResAtId',
					        fieldLabel: getLabelFromMap('txtTmpoResAtId',helpMap,'Tiempo Restante para atender'),
					        tooltip:getToolTipFromMap('txtTmpoResAtId',helpMap,'Tiempo Restante para atender'), 	
					        hasHelpIcon:getHelpIconFromMap('txtTmpoResAtId',helpMap),
		                    Ayuda: getHelpTextFromMap('txtTmpoResAtId',helpMap,''),          
					        name: 'tiemporestanteparaatender',
					        disabled: true,
					        width: 150
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		labelWidth: 100,          			
           			items: [
           				{
           					xtype:'textfield',
		            		id: 'txtTmpoResEsId',
					        fieldLabel: getLabelFromMap('txtTmpoResEsId',helpMap,'Tiempo Restante para escalar'),
					        tooltip:getToolTipFromMap('txtTmpoResEsId',helpMap,'Tiempo Restante para escalar'), 
					        hasHelpIcon:getHelpIconFromMap('txtTmpoResEsId',helpMap),
		                    Ayuda: getHelpTextFromMap('txtTmpoResEsId',helpMap,''),  	        
					        name: 'tiemporestanteparaescalarTex',
					        disabled: true,
					        width: 140
					     }
           				]
            		},
            		{
            		layout: 'form',  
            		colspan:2,
            		labelWidth: 90,          			
           			items: [
           				{
           					xtype:'textfield',
		            		id: 'txtHsCompId',
					        fieldLabel: getLabelFromMap('txtHsCompId',helpMap,'Tiempo comprado'),
					        tooltip:getToolTipFromMap('txtHsCompId',helpMap,'Tiempo comprado'), 	     
					        hasHelpIcon:getHelpIconFromMap('txtHsCompId',helpMap),
		                    Ayuda: getHelpTextFromMap('txtHsCompId',helpMap,''),     
					        name: 'nmvecescompra',
					        disabled: true,
					        width: 110
					     }
           				]
            		},
            		{
            		html: '<br/>',
            		colspan:4
            		},
            		{
            		layout: 'table',
            		id:'dinamico',
            		layoutConfig: {columns: 2, align: 'left'}, 
            		colspan:4,            			
           			items:ATTS_DINAMICOS 
            		},
            		{
            		layout:'table',
            		id:'tableId3',
            		layoutConfig: { columns: 4, columnWidth: .25},
            		colspan:3,
            		items:[
            			{
	            		html: '<br/><span class="x-form-item" style="font-weight:bold">Usuarios Responsables</span>',
	            		colspan:4
	            		},
            			{
	            		layout: 'form',  
	           			labelWidth: 70, 
	           			colspan:4,
	            		items:[gridUR]
            			}
            		]
            		}
            	],
            	buttonAlign: 'center',
       			labelAlign:'right',
       			buttons: [
       					{
       					id: 'btnCompTiemp',
       					text:getLabelFromMap('btnCompTiemp',helpMap,'Comprar Tiempo'),
                        tooltip: getToolTipFromMap('btnCompTiemp',helpMap,'Comprar Tiempo'),
       					handler:function(){
       						
       							validarStatusCaso();
       							
       					
       						//comprarTiempo(NMCASO,Ext.getCmp("cdnivatn").getValue(),Ext.getCmp("cdproceso").getValue(),Ext.getCmp("cdusuario").getValue())
       						}
       						},         					        					       					
       					{
       				    text:getLabelFromMap('btnMovimiento',helpMap,'Movimientos'),
                        tooltip: getToolTipFromMap('btnMovimiento',helpMap,'Movimientos'), 
       					handler:function(){clearInterval(intervalo);window.location=_ACTION_IR_CONSULTA_MOVIMIENTO_CASO+'?nmcaso='+NMCASO+'&cdmatriz='+CDMATRIZ+'&cdperson='+CDPERSON+'&cdformatoorden='+Ext.getCmp("cdformatoordenId").getValue()+'&limit=999'+'&edit=0'}},
       				    {
       				    id:'btnREasignar',
       				    text:getLabelFromMap('btnREasignar',helpMap,'Reasignar'),
                        tooltip: getToolTipFromMap('btnREasignar',helpMap,'Reasigna un Caso'), 
       				     handler:function (){
       				     	
       				     	validaStatusCaso();
       				     
       				     
       				     }},
						//{text: 'Reasignar',handler:function (){window.location =_ACTION_IR_REASIGNAR_CASO+'?nmcaso='+ Ext.getCmp('txtNroCasoId').getValue()+'?cdformatoorden=CELESTE'}}, 					
       					{text:getLabelFromMap('btnArchivo',helpMap,'Archivo'),
                         tooltip: getToolTipFromMap('btnArchivo',helpMap,'Archivo'), 
       					 handler:function(){archivosPorMovimiento(NMCASO,Ext.getCmp("dsproceso").getValue(),Ext.getCmp('movimientoId').getValue())}},
       					/*{
       					text:getLabelFromMap('btnEnviarFax',helpMap,'Enviar Fax'),
                        tooltip: getToolTipFromMap('btnEnviarFax',helpMap,'Enviar Fax') ,
                        handler:function(){
                        	
                        window.location = _IR_A_ADMINISTRAR_FAX+'?nmcaso='+ NMCASO;
                        //  window.location = _IR_A_ADMINISTRAR_FAX;
                       
                        }
       					      					    
       					    },*/
       					    
       					 
       					 /* {
       				    id:'btnREasignar',
       				    text:getLabelFromMap('btnREasignar',helpMap,'Reasignar'),
                        tooltip: getToolTipFromMap('btnREasignar',helpMap,'Reasigna un Caso'), 
       				     handler:function (){clearInterval(intervalo);window.location =_ACTION_IR_REASIGNAR_CASO+'?nmcaso='+NMCASO+'&cdmatriz='+CDMATRIZ+'&cdperson='+CDPERSON+'&cdformatoorden='+Ext.getCmp("cdformatoordenId").getValue()+'&limit=999'+'&edit=0'+'&flag=0'}},*/
       					    {
       				    text:getLabelFromMap('btnEnviarFax',helpMap,'Enviar Fax'),
                        tooltip: getToolTipFromMap('btnEnviarFax',helpMap,'Enviar Fax') , 
       					 handler:function (){clearInterval(intervalo);window.location =_IR_A_ADMINISTRAR_FAX+'?nmcaso='+NMCASO+'&cdmatriz='+CDMATRIZ+'&cdperson='+CDPERSON+'&cdformatoorden='+CDFORMATOORDEN+'&limit=999'+'&edit=0'+'&flag='+0}},
       					    
       					{
       					text:getLabelFromMap('btnExportar',helpMap,'Exportar'),
                        tooltip: getToolTipFromMap('btnExportar',helpMap,'Exportar'),
                        handler: function () {
                        	var _url = _ACTION_EXPORTAR + '?nroCaso=' + NMCASO + '&cdMatriz=' + CDMATRIZ;
							//var width, height;
							//width = screen.width;
							//height = screen.height;
                        	//window.open(_url, 'exportaDetalleCaso', config='height=' + height + ', width=' + width + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, directories=no, status=no')
                        	//exportar (_url);
							showExportDialog(_url);
                        }
       					 },
       					{
       					text:getLabelFromMap('btnEnviarCorreo',helpMap,'Enviar Correo'),
                        tooltip: getToolTipFromMap('btnEnviarCorreo',helpMap,'Enviar Correo'), 
       					handler:function(){
   					 				//enviarCorreo('', Ext.getCmp('txtNroCasoId').getValue(),TIPOENVIO,getUsers());   					 		   					 		       					 	
   					 				//enviarCorreo('', Ext.getCmp('txtNroCasoId').getValue(),TIPOENVIO);
   					 				enviarCorreo(Ext.getCmp('txtNroCasoId').getValue(),formPanel.findById("cdproceso").getValue());
   					 		}},
       					/*{
       					 text:getLabelFromMap('btnProcesar',helpMap,'Procesar'),
                         tooltip: getToolTipFromMap('btnProcesar',helpMap,'Procesar') 
       					 },*/
       					{
       					 text:getLabelFromMap('btnRegresa',helpMap,'Regresar'),
                         tooltip: getToolTipFromMap('btnRegresa',helpMap,'Regresar'),
       					 handler:function(){clearInterval(intervalo);window.location = _ACTION_VOLVER_CONSULTA_CASO+'?cdperson='+CDPERSON;}}
				]
	        
	});   
	
	formPanel.render();
	formPanel.load({
		params:{cdmatriz: CDMATRIZ,
				pv_nmcaso_i:NMCASO
				},
		success:function(){
			reloadGrid(NMCASO,CDMATRIZ);			
			mapearDatos(Ext.getCmp('cdformatoordenId').getValue());
			Ext.getCmp('movimientoId').setValue(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].nmovimiento);
			if (storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color == 'red')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp;  &nbsp;  &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; Vigente <img src="../resources/images/red.bmp" alt=""/>';
				//alert(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color);
				}
			if (storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color == 'green')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; Vigente <img src="../resources/images/green.bmp" alt=""/>';
				//alert(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color);
				}
			if (storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color == 'yellow')
				{
				Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp; Vigente <img src="../resources/images/yellow.bmp" alt=""/>';
				
				}	
							
			//Ejecutar aca la funcion del temporizador
			intervalo = setInterval("reloadEnc()",1000*60);
			
			/*if(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].tiemporestanteparaescalar >0 )
		     	{  
		     	 Ext.getCmp('txtTmpoResAtId').setValue(_txtTmpoResAtId);
				 Ext.getCmp('txtTmpoResEsId').setValue(_txtTmpoResEsId2);
		        }				
			else {
			      Ext.getCmp('txtTmpoResAtId').setValue(0);
				  Ext.getCmp('txtTmpoResEsId').setValue(0);
				  Ext.getCmp('btnCompTiemp').setDisabled(true);
				  Ext.getCmp('btnREasignar').setDisabled(true);
			      }*/
			
		}
	});
		
	function mapearDatos(_formatoordentrabajo){
			storeDatosAtributosVariables.load({
					params: {
							 cdordentrabajo: Ext.getCmp('txtNroOrdenId').getValue(),
							 limit:999
							 },
					callback:function(){
						var _atributos = (storeDatosAtributosVariables.reader.jsonData.MListaSeccionesOrden)?storeDatosAtributosVariables.reader.jsonData.MListaSeccionesOrden:0;
						Ext.each(_atributos, function(valor){
							Ext.getCmp(_formatoordentrabajo+'_'+valor.cdseccion+'_'+valor.cdatribu).setValue(valor.otvalor);
						});					
					}
			});
	}
						

	function exportar (action) {
            	var fmExport = new Ext.FormPanel({
            		renderTo: 'formExportar',
            		labelWidth: 120,
            		width: 0,
            		height: 0,
            		onSubmit: Ext.emptyFn,
            		submit: function() {
            			// en la funcion de submit de ExtJS se sobreescribe para hacer un submit sin AJAX 
						this.getEl().dom.setAttribute("action",action);
						this.getEl().dom.target = '_windowExport';
						this.getEl().dom.submit();
						},
					defaultType: 'textfield',
					baseCls: null,
					bodyStyle: {background: 'white', padding: '5px 10px 0'},
					items: [
						{xtype: 'hidden', id:'formato', value: 'PDF'}
					]
				});
				fmExport.render();
				fmExport.getForm().submit(action);
	}

	function validaStatusCaso()
    
{
        var params =  {      
         
                    
                     nmCaso: formPanel.findById('txtNroCasoId').getValue()
                        
             };
    
        execConnection (_VALIDA_SATUS_CASO, params, cbkValida);

}
function cbkValida (_success, _message) {
   
    
    if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
			clearInterval(intervalo);window.location =_ACTION_IR_REASIGNAR_CASO+'?nmcaso='+NMCASO+'&cdmatriz='+CDMATRIZ+'&cdperson='+CDPERSON+'&cdformatoorden='+Ext.getCmp("cdformatoordenId").getValue()+'&limit=999'+'&edit=0'+'&flag=0'
		}

}               



	function validarStatusCaso()
    
{
        var params =  {
       
         
                    
                     nmCaso: formPanel.findById('txtNroCasoId').getValue()
                        
             };
    
        execConnection (VALIDA_SATUS_CASO, params, cbkValidar);

}
function cbkValidar (_success, _message) {
   
    
    if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
			//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
			validaCompraTiempo(NMCASO,Ext.getCmp("cdnivatn").getValue(),Ext.getCmp("cdproceso").getValue())
			//clearInterval(intervalo);window.location =_ACTION_IR_REASIGNAR_CASO+'?nmcaso='+NMCASO+'&cdmatriz='+CDMATRIZ+'&cdperson='+CDPERSON+'&cdformatoorden='+Ext.getCmp("cdformatoordenId").getValue()+'&limit=999'+'&edit=0'+'&flag=0'
		}

}          




});

function reloadEnc(){
		storeEncCasoDetalle.load({
					params:{cdmatriz: CDMATRIZ,pv_nmcaso_i:NMCASO},
					callback:function(){
						reloadGrid(NMCASO,CDMATRIZ);
						//No es necesario llamar a esta función para recargar estos valores
						//mapearDatos(Ext.getCmp(_cdformatoordenId_).getValue());
						Ext.getCmp('movimientoId').setValue(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].nmovimiento);						
					
						var _cdformatoordenId = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].cdformatoorden;
						var _cdnivatn = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].cdnivatn;
						var _cdproceso = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].cdproceso;
						var _cdusuario = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].cdusuario;
						var _dsproceso = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].dsproceso;
						var _txtPrioridadId = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].dspriord;
						var _txtNivelAtencionId = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].dsnivatn;
						var _txtNroCasoId = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].nmcaso;
						var _txtAseguradoraId = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].dsunieco;
						//_txtVigenteId = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color;
						var _txtNroOrdenId = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].cdordentrabajo;
						var _txtModuloId = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].dsmodulo;
						var _txtEstatusId = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].dsstatus;
						
						var  _txtTmpoResAtId = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].tiemporestanteparaatender;
						var _txtTmpoResEsId2 = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].tiemporestanteparaescalar;
							
						 
						
						
						var _txtHsCompId = storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].nmvecescompra;
						
						Ext.getCmp('cdformatoordenId').setValue(_cdformatoordenId);
						Ext.getCmp('cdnivatn').setValue(_cdnivatn);
						Ext.getCmp('cdproceso').setValue(_cdproceso);
						Ext.getCmp('cdusuario').setValue(_cdusuario);
						Ext.getCmp('dsproceso').setValue(_dsproceso);
						Ext.getCmp('txtPrioridadId').setValue(_txtPrioridadId);
						Ext.getCmp('txtNivelAtencionId').setValue(_txtNivelAtencionId);
						Ext.getCmp('txtNroCasoId').setValue(_txtNroCasoId);
						Ext.getCmp('txtAseguradoraId').setValue(_txtAseguradoraId);
						//Ext.getCmp('txtVigenteId').setValue(_txtVigenteId);
						Ext.getCmp('txtNroOrdenId').setValue(_txtNroOrdenId);
						Ext.getCmp('txtModuloId').setValue(_txtModuloId);
						Ext.getCmp('txtEstatusId').setValue(_txtEstatusId);
					//consulta el tiempo Restante para escalar
					/*if(storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].tiemporestanteparaescalar > 0){
						    Ext.getCmp('txtTmpoResAtId').setValue(_txtTmpoResAtId);
							Ext.getCmp('txtTmpoResEsId').setValue(_txtTmpoResEsId2);
								}
							else{
							    Ext.getCmp('txtTmpoResAtId').setValue(0);
							    Ext.getCmp('txtTmpoResEsId').setValue(0);
							    Ext.getCmp('btnCompTiemp').setDisabled(true);
							    Ext.getCmp('btnREasignar').setDisabled(true);
								}
							*/
							
						Ext.getCmp('txtHsCompId').setValue(_txtHsCompId);		
						Ext.getCmp('txtTmpoResAtId').setValue(_txtTmpoResAtId);
						Ext.getCmp('txtTmpoResEsId').setValue(_txtTmpoResEsId2);
						
												
						if (storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color == 'red')
							{
							Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Vigente <img src="../resources/images/red.bmp" alt=""/>';
							}
						if (storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color == 'green')
							{
							Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; Vigente <img src="../resources/images/green.bmp" alt=""/>';
							}
						if (storeEncCasoDetalle.reader.jsonData.MEstructuraCasoList[0].color == 'yellow')
							{
							Ext.getCmp('txtVigenteId').getEl().dom.innerHTML='&nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; Vigente <img src="../resources/images/yellow.bmp" alt=""/>';
							
							}
				}
		});
}
function reloadGrid(_nmcaso,_cdmatriz){
		var _params = {pv_nmcaso_i: _nmcaso, cdmatriz:_cdmatriz};		
											
		reloadComponentStore(Ext.getCmp('gridURId'), _params, cbkReload);
	}
	
	function cbkReload(_r, _options, _success, _store) {
		if (!_success) {
			_store.removeAll();
			//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		}
	}
	
//Obtengo todos los usuarios responsables del caso
//Se quito por pedido de daniel
/*function getUsers(){
		var _params = "";
		
		var strUsuariosSeg = "";
	  	var _listaUsuariosRes = storeCasoUsr.reader.jsonData.MUsuariosResponsablesList;
	  	var i;
	  	if(_listaUsuariosRes){
	  			for(i = 0; i < _listaUsuariosRes.length; i++){
			  		strUsuariosSeg += _listaUsuariosRes[i].email+',';
	  			};
	  	}	 	  
	  return _params += "&strUsuariosSeg=" + strUsuariosSeg;		
	}*/	