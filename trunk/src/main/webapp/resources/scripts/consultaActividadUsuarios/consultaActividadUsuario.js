Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";		
	
	var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmNombreId',helpMap,'Nombre'),
        tooltip: getToolTipFromMap('cmNombreId',helpMap,'Nombre'),
        dataIndex: 'dsnombre',
        width: 75,
        sortable: true
        },
        {
        header: getLabelFromMap('cmNominaId',helpMap,'Nomina'),
        tooltip: getToolTipFromMap('cmNominaId',helpMap,'Nomina'),
        dataIndex: 'cdideper',
        width: 75,
        sortable: true
        },
        {
        header: getLabelFromMap('cmClaveCWId',helpMap,'Clave CatWeb'),
        tooltip: getToolTipFromMap('cmClaveCWId',helpMap,'Clave CatWeb'),
        dataIndex: 'cdperson',
        width: 110,
        sortable: true
        },
		{       
        header: getLabelFromMap('cmEmailCId',helpMap,'Email Casa'),
        tooltip: getToolTipFromMap('cmEmailCId',helpMap,'Email Casa'),
        dataIndex: 'mailpersonal',
        width: 100,
        sortable: true
        },
        {        
        header: getLabelFromMap('cmTelCId',helpMap,'Tel. casa'),
        tooltip: getToolTipFromMap('cmTelCId',helpMap,'Tel. casa'),
        dataIndex: 'telefonocasa',
        width: 95,
        sortable: true
        },
        {        
        header: getLabelFromMap('cmEmailEId',helpMap,'Email Empresa'),
        tooltip: getToolTipFromMap('cmEmailEId',helpMap,'Email Empresa'),
        dataIndex: 'mailoficina',
        width: 120,
        sortable: true
        },
        {        
        header: getLabelFromMap('cmTelEId',helpMap,'Tel. Empresa'),
        tooltip: getToolTipFromMap('cmTelEId',helpMap,'Tel. Empresa'),
        dataIndex: 'telefonoofic',
        width: 120,
        sortable: true
        },
        {
        dataIndex:'cdtipper',hidden:true
        },
        {
        dataIndex:'otfisjur',hidden:true
        },
        {
        dataIndex:'otsexo',hidden:true
        },
        {
        dataIndex:'fenacimi',hidden:true
        },
        {
        dataIndex:'cdrfc',hidden:true
        }
	]);

	var grid = new Ext.grid.GridPanel({
       		id: 'gridId',
       		el:'gridElementos',
            store: storeGrillaActividadUsuario,
            border:true,
            cm: cm,
	        successProperty: 'success', 
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',           
            width:700,
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            buttonAlign:'center',
            frame:true,
            height:320,  
            frame: true,
            buttons:[
                  {
                  text:getLabelFromMap('gridBtn1',helpMap,'Actualizar Datos'),
                  tooltip: getToolTipFromMap('gridBtn1',helpMap,'Actualizar Datos')/*,
                  handler:function(){
						if (getSelectedRecord(grid) != "") {
                        		
               			} else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }}*/
                  }
                  ],          
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeGrillaActividadUsuario,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });          
	
	var cdPerson = new Ext.form.Hidden({id:'cdPersonId', name:'cdPerson'});
	var bandera;
	//FORMULARIO PRINCIPAL ************************************
	var horaIniTemp;
	var horaFinTemp;
	var formPanel = new Ext.FormPanel({			
	        el:'formBusqueda',
	        id: 'acFormPanelId',	        
	        title: '<span style="color:black;font-size:12px;">Actividad de Usuarios</span>',
	        iconCls:'logo',
	        bodyStyle:'background: white',	              
	        frame:true,
	        width: 700,
	        autoHeight:true,
            items:[
            		{            		
	        		layout: 'table',
            		layoutConfig: { columns: 3, columnWidth: .33},
            		labelAlign: 'right',
            		items:[
	            		{
	            		html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
	            		colspan:3
	            		},
	            		{
	            		layout: 'form',
	            		labelWidth: 100,   			
	           			items: [
	           				{
	           				   xtype: 'textfield',
	           				   id:'dsUserId',
		                       fieldLabel: getLabelFromMap('cmbEmpresaId',helpMap,'Nombre'),
	        				   tooltip: getToolTipFromMap('cmbEmpresaId',helpMap,'Nombre'),
		                       hiddenName: 'dsUser',
		                       typeAhead: true,
		                       width: 210
		                       
						     }
	           				]
	            		},
	            		{
	            		layout: 'form',
	            		labelWidth: 100,
	            		items: [
	           				{
	           					
						     }
	           				]
	            		},
						{
							layout: 'form'
							
						},
						{
	            		layout: 'form', 
	            		labelWidth: 100,         			
	           			items: [
	           				{
	           					xtype:'datefield',
			            		id: 'feInicioId',
						        fieldLabel: getLabelFromMap('txtNumNomId',helpMap,'Fecha de Inicio'),
						        tooltip:getToolTipFromMap('txtNumNomId',helpMap,'Fecha de Inicio'), 	        
						        name: 'feInicio',
					            format: 'd/m/Y',
                              // allowBlank: false,
						        width: 210
						     }
	           				]
	            		}, 
	            		/*{
	            		layout: 'form',              		
	            		colspan:3,        			
	           			items: [
	           				{
	           				   xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		                       id:'cmbGrupoId',
		                       fieldLabel: getLabelFromMap('cmbGrupoId',helpMap,'Grupo'),
	        				   tooltip: getToolTipFromMap('cmbGrupoId',helpMap,'Grupo'),
		                       store: storeComboGrupos,
		                       displayField:'descripcion',
		                       valueField:'codigo',
		                       hiddenName: 'cdGrupo',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       width: 150,
		                       allowBlank: false,
		                       emptyText:'Seleccione Grupo...',
		                       selectOnFocus:true,
		                       forceSelection:true
						     }
	           				]
	            		},*/
	            		{
	            		layout: 'form',
	            		labelWidth: 100,          			
	           			items: [
	           				{
	           					xtype:'timefield',
			            		id: 'txtApeMatId',
						        fieldLabel: getLabelFromMap('txtApeMatId',helpMap,'Hora de Inicio'),
						        tooltip:getToolTipFromMap('txtApeMatId',helpMap,'Hora de Inicio'),
						        maxHeight:'100',  	  
						        name: 'horaInicio',
						        width: 200
						     }
	           				]
	            		},            		
	            		{
							layout: 'form'
							
						},
	            		{            		
	           			layout: 'form',
	           			labelWidth: 100,         			            		
	           			items: [
	           				{
	           					xtype: 'datefield',
	           					id: 'cdpersonId',
						        fieldLabel: getLabelFromMap('txtClaveCWClieId',helpMap,'Fecha Final'),
						        tooltip:getToolTipFromMap('txtClaveCWClieId',helpMap,'Fecha Final'), 	        
						        name: 'cdperson',
					            format: 'd/m/Y',
                               // allowBlank: false,
						        width: 210
						     }
	           				]
	            		},         												            		
	            		{
	            		layout: 'form',
	            		labelWidth: 100,          			
	           			items: [
	           				{
	           					xtype:'timefield',
			            		id: 'txtApePatId',
						        fieldLabel: getLabelFromMap('txtApePatId',helpMap,'Hora Final'),
						        tooltip:getToolTipFromMap('txtApePatId',helpMap,'Hora Final'),
						        maxHeight:'100',  	  
						        name: 'dsapellido2',
						        width: 200
						     }
	           				]
	            		}
	            		
            		
            		],
            		 buttonAlign: "center",	
            		buttons:[{
    							text:getLabelFromMap('txtBtnBuscarId',helpMap,'Continuar'),
    							tooltip: getToolTipFromMap('txtBtnBuscarId',helpMap,'Busca clientes'),
    							handler: function() {
               					if (formPanel.form.isValid()) {
                                     //ir a pantalla de activfidad de usuario
               						
               						var fi =Ext.getCmp("feInicioId").getRawValue();
									var ff =Ext.getCmp("cdpersonId").getRawValue();
									var hi =Ext.getCmp("txtApeMatId").getRawValue();
									var hf =Ext.getCmp('txtApePatId').getRawValue();
               						
               						if(validarOk(fi,ff,hi,hf)){
			               							if(Ext.getCmp("txtApeMatId").getRawValue() != ""){
					               							horaIniTemp = Ext.getCmp("txtApeMatId").getRawValue();
					               						}else{horaIniTemp = "12:00 AM"};
					               					if(Ext.getCmp("txtApePatId").getRawValue() != ""){
					               							horaFinTemp = Ext.getCmp("txtApePatId").getRawValue();
					               						}else{horaFinTemp = "11:59 PM"};
					               						 window.location = _ACTION_IR_ACTIVIDAD_USUARIO_RESULTADO + '?dsUser=' + Ext.getCmp("dsUserId").getValue()
					               						 														  + '&feInicial='+ Ext.getCmp("feInicioId").getRawValue()
					               						 														  + '&feFinal='+ Ext.getCmp("cdpersonId").getRawValue()
					               						 														  + '&hrInicial='+ horaIniTemp// Ext.getCmp("txtApeMatId").getValue()
					            																				  + '&hrFinal='	+ horaFinTemp;//Ext.getCmp("txtApePatId").getValue(); 
					            	}
               						
		               						
		               			}else{
			               			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
			               		}
               					}
    						}, {
								"buttonType" : "reset",
								"handler" : function() {
									formPanel.form.reset();
								},
								"id" : "limpiar",
								"text" : "Cancelar",
								"tooltip" : "Cancelar cambios",
								"type" : "reset",
								"xtype" : "button"
							}]
    				}]  
	});  
	
	function reloadGrid(){
		var _params = {
       		cdelemento: Ext.getCmp('cmbEmpresaId').getValue(),
       		//grupo: Ext.getCmp('cmbGrupoId').getValue(),
       		cdideper: Ext.getCmp('txtNumNomId').getValue(),             
       		cdperson: Ext.getCmp('txtClaveCWClieId').getValue(),
       		dsnombre: Ext.getCmp('txtNombreId').getValue(),
       		dsapellido: (Ext.getCmp('txtApeMatId').getValue()!= "")?Ext.getCmp('txtApeMatId').getValue():"12:00 AM",
       		dsapellido2: (Ext.getCmp('txtApePatId').getValue()!= "")?Ext.getCmp('txtApePatId').getValue():"11:59 PM"
       		};
	
		reloadComponentStore(Ext.getCmp('gridId'), _params, cbkReload);
	};
	
	function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		Ext.Msg.alert('Aviso', _store.reader.jsonData.actionErrors[0]);
		//Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		}
	}
	//console.log(formPanel);
	formPanel.render();
	
	
	
	//grid.render();
	

});
 /* No se usa, porque se remplazo creando componentes date 
function timeComp(val){
	var salida = "";
	var am_pm = "";
	var star1 = val.length;
	var star2 = val.length;
	
	var hora = "";
	var horaAux = "";
	var aux = 0;
	var minutos = "";
	
	star2 = star2 - 5;
	hora = val.substring(0,star2 - 1);
	horaAux = val.substr(star2,val.length);
	minutos = horaAux.substring(0,2);
	star1 = star1 - 2;
	am_pm = val.substr(star1,val.length);
	if (am_pm == "PM"){
		if(hora != "12"){
			aux = parseInt(hora) + 12;
			hora = aux.toString();
		}
	}else{
		if(hora == "12"){ 
			hora = "00"
			}
			else{
				if(parseInt(hora)<10){
			 		hora = "0"+ hora
			 		} 
			}
	};
	if(hora != ""){
		salida = hora + ":" + minutos;
	}else salida;
    return salida;
};
function validarFechasPrimero(fi,ff,hi,hf)
{
	alert(3);
		if(fi <= ff){
			if(fi == ff){
					if(hi != "" || hf != ""){
						 if(hi != "" && hf == ""){   
						    //hi = Ext.getCmp("txtApeMatId").getValue();
						    hf = "11:59 PM";
						    if(timeComp(hi) <= timeComp(hf)){
						    	return true;
							}else{ 
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400115', helpMap,'La hora de inicio debe ser menor o igual que la de fin'));
								return false;
								}
					 		}else{
					 			//hi = Ext.getCmp("txtApeMatId").getRawValue();
						    	//hf = Ext.getCmp("txtApePatId").getRawValue();
					 			if(timeComp(hi) <= timeComp(hf)){
						    		return true;
								}else{ 
									Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400115', helpMap,'La hora de inicio debe ser menor o igual que la de fin'));
									return false;
								}	
					 		}		
				}else{
					return true;
				}
				
			}else{
				return true;
			}
		}else{
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400083', helpMap,'La fecha de inicio debe ser menor o igual que la de fin'));
			return false;
		}
};
function validarFechas (fi,ff,hi,hf){
	
		
		if((fi != "") || (ff != "")){
						if ((fi != "") && (ff == "")){
								if(hf == ""){
										ff = "99/99/9999";
										return validarFechasPrimero(fi,ff,hi,hf);
									}else{
										Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400113', helpMap,'Ingrese fecha final'));
										return false;
								}	
						
						}else{
							if((ff != "")&&(fi == "")){
								if(hi == ""){
									return validarFechasPrimero(fi,ff,hi,hf);
								}else{
									Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400114', helpMap,'Ingrese fecha de inicio'));
									return false
									};
									
							}else {return 
									validarFechasPrimero(fi,ff,hi,hf)}
						}
					
		}else{
			if((hi == "") && (hf == "")){
				return true;
			}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400118', helpMap,'Ingrese fecha'));
				return false;
			}
		}		
};*/



function validarOk(_fi,_ff,_hi,_hf){
	
	
	
	if (_fi != "" || _ff != ""){
		if(_fi != "" && _ff == ""){
				if(_hf != ""){
					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400113', helpMap,'Ingrese fecha final'));
					return false
				}else{return true}
			}else{
				if(_fi == "" && _ff != ""){
					if(_hi != ""){
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400114', helpMap,'Ingrese fecha de inicio'));
						return false
					}else{return true}
				}else{
							var diafi = _fi.substring(0,_fi.length - 8);
							var mesfi = _fi.substring(3,_fi.length - 5);
							var aniofi = _fi.substr(_fi.length-4,_fi.length);
			
							var diaff = _ff.substring(0,_ff.length - 8);
							var mesff = _ff.substring(3,_ff.length - 5);
							var anioff = _ff.substr(_ff.length-4,_ff.length);
			
							var di = new Date(mesfi+'/'+diafi+'/'+aniofi+' '+ _hi );
							var df = new Date(mesff+'/'+diaff+'/'+anioff+' '+ _hf );
							
							if(di <= df){
									return true
								}else{
									Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400083', helpMap,'La fecha de inicio debe ser menor o igual que la de fin'));
									return false
									}
				}
			}
	}else{
		if(_hi == "" && _hf == ""){
			return true
			}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400118', helpMap,'Ingrese fecha'));
				return false}
	}
	
	
};



