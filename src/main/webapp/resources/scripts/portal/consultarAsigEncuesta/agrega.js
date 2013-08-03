agregarEncuesta = function(store){

 /***Store que carga el combo de usuarios responsables***/
    
    var dataStoreUsuarios = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_USUARIOS
            }),
            reader: new Ext.data.JsonReader({
                root: 'usuarios',
                id: 'cdUsuario'
            },[
                {name: 'cdUsuario',  type: 'string',  mapping:'cdUsuario'},
                {name: 'dsUsuari',  type: 'string',  mapping:'dsUsuari'}  
            ]),
            remoteSort: true
        });
    dataStoreUsuarios.setDefaultSort('usu', 'desc');
   
	var dsUnieco = new Ext.form.TextField({
			    fieldLabel: getLabelFromMap('dsUniecoAgrId',helpMap,'Aseguradora'),
			    tooltip:getToolTipFromMap('dsUniecoAgrId',helpMap,'Aseguradora'), 
			    hasHelpIcon:getHelpIconFromMap('dsUniecoAgrId',helpMap),
			    Ayuda:getHelpTextFromMap('dsUniecoAgrId',helpMap),
				//fieldLabel:'Aseguradora',
				allowBlank:false,
				name:'dsUnieco',
				//tooltip:'Aseguradora',
				width: 250
		});
		
		
	var dsRamo = new Ext.form.TextField({
			    fieldLabel: getLabelFromMap('dsRamoAgrId',helpMap,'Producto'),
			    tooltip:getToolTipFromMap('dsRamoAgrId',helpMap,'Producto'), 
			    hasHelpIcon:getHelpIconFromMap('dsRamoAgrId',helpMap),
			    Ayuda:getHelpTextFromMap('dsRamoAgrId',helpMap),
				//fieldLabel:'Producto',
				allowBlank:false,
				name:'dsRamo',
				//tooltip:'Producto',
				width: 250
		});	
	
	var estado = new Ext.form.TextField({
			    fieldLabel: getLabelFromMap('estadoAgrId',helpMap,'Estado'),
			    tooltip:getToolTipFromMap('estadoAgrId',helpMap,'Estado'), 
			    hasHelpIcon:getHelpIconFromMap('estadoAgrId',helpMap),
			    Ayuda:getHelpTextFromMap('estadoAgrId',helpMap),
				//fieldLabel:'Estado',
				allowBlank:false,
				name:'estado',
				//tooltip:'Estado',
				width: 250
		});
		
		var nmPoliza = new Ext.form.TextField({
			    fieldLabel: getLabelFromMap('nmPolizaAgrId',helpMap,'P&oacute;liza'),
			    tooltip:getToolTipFromMap('nmPolizaAgrId',helpMap,'P&oacute;liza'), 
			    hasHelpIcon:getHelpIconFromMap('nmPolizaAgrId',helpMap),
			    Ayuda:getHelpTextFromMap('nmPolizaAgrId',helpMap),
				//fieldLabel:'P&oacute;liza',
				allowBlank:false,
				name:'nmPoliza',
				//tooltip:'P&oacute;liza',
				width: 250
		});
		
		var dsPerson = new Ext.form.TextField({
			    fieldLabel: getLabelFromMap('dsPersonAgrId',helpMap,'Cliente'),
			    tooltip:getToolTipFromMap('dsPersonAgrId',helpMap,'Cliente'), 
			    hasHelpIcon:getHelpIconFromMap('dsPersonAgrId',helpMap),
			    Ayuda:getHelpTextFromMap('dsPersonAgrId',helpMap),
				//fieldLabel:'Cliente',
				allowBlank:false,
				name:'dsPerson',
				//tooltip:'Cliente',
				width: 250
		});

var dsUsuari = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{dsUsuari}. {cdUsuario}" class="x-combo-list-item">{dsUsuari}</div></tpl>',
                store: dataStoreUsuarios,
                width: 250,
                mode: 'local',
                name: 'dsUsuari',
                //tooltip:'Usuario Responsable',
                hiddenName: 'dsUsuari',
                typeAhead: true,
                //labelSeparator:'',          
                triggerAction: 'all',           
                displayField:'dsUsuari',
                forceSelection: true,
			    fieldLabel: getLabelFromMap('dsUsuariAgrId',helpMap,'Usuario Responsable'),
			    tooltip:getToolTipFromMap('dsUsuariAgrId',helpMap,'Usuario Responsable'), 
			    hasHelpIcon:getHelpIconFromMap('dsUsuariAgrId',helpMap),
			    Ayuda:getHelpTextFromMap('dsUsuariAgrId',helpMap),
                //fieldLabel: "Usuario Responsable",
                valueField:'cdUsuario', 
                emptyText:'Seleccionar un Usuario...',
                selectOnFocus:true
        });  
	
	var agregarForm = new Ext.form.FormPanel({
				url:'menuusuario/guardarAction.action',
				boder:false,
				frame:true,
				method:'post',            	            
				//fileUpload: true,
        		width: 470,
        		buttonAlign: "center",
				baseCls:'x-plain',
				labelWidth:125,
				labelAlign:'right',
				bodyStyle:'background: white',
				
				items:[
					dsUnieco,
					dsRamo,
					estado,
                    nmPoliza,
					dsPerson,
                    dsUsuari
				]
			});
			
			
			
			/**var _window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('71',helpMap,'Agregar Configuraci&oacute;n de Alertas')+'</span>',
			width: 700,
			autoHeight: true,
			modal:true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: form_nuevo,
            buttons : [ {
                text : getLabelFromMap('btnAddConfAlert',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('btnAddConfAlert',helpMap,'Guardar'),
                disabled : false,
                handler : function(){
                    if (form_nuevo.form.isValid()) {
                    
                    if (form_nuevo.findById('uasuario').getValue()||			// al menos un campo debe no debe ser nulo
                    	form_nuevo.findById('rol').getValue()||
                    	form_nuevo.findById('cliente').getValue()||
                    	form_nuevo.findById('ramo').getValue()||
                    	form_nuevo.findById('aseguradora').getValue()||
                    	form_nuevo.findById('producto').getValue()||
                    	form_nuevo.findById('region').getValue())	
                    	{
						//form_nuevo.findById('feInicio').setValue('20080516');
                        form_nuevo.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_CONFIGURACION_ALERTAS,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0],function(){reloadGrid(Ext.getCmp('grillaId'),Ext.getCmp('el_formId'));});
                                
                               
                                
                                _window.close();                                     
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Guardar: ' + action.result.errorMessages[0]);
                           },
                            waitMsg : getLabelFromMap('400027', helpMap,'guardando datos ...')
                        });
                       }
                       else{
                       		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                       }
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }

                }

            }, {*/
	var window = new Ext.Window({
		title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('wndwCnsltAsigEncAgr',helpMap,'Administrar asignaci&oacute;n de Encuesta')+'</span>',
        //title: 'Administrar asignaci&oacute;n de Encuesta',
        width: 500,
        height:250,
        minWidth: 400,
        minHeight: 250,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: agregarForm,
        buttons: [{
        	text:getLabelFromMap('consltAsigEncAgrButtonBuscar',helpMap,'Guardar'),
        	tooltip: getToolTipFromMap('consltAsigEncEdtButtonBuscar',helpMap,'Guarda administraci&oacute;n de encuesta'),
            //text: 'Guardar', 
 	        //tooltip:'Guarda administraci&oacute;n de encuesta',
            handler: function() {
                if (agregarForm.form.isValid()) {
                	//alert(fileLoad.getValue());
	 		        agregarForm.form.submit({
	 		       		url:_ACTION_GUARDAR_ASIGNACION_ENCUESTA,			      
			            waitMsg:'Procesando...',
			            failure: function(form, action) {
			            	var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Error',mensajeRes);
						    window.close();
						    store.load();
						},
						success: function(form, action) {
							var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Aviso', mensajeRes);
						    window.close();
						    store.load();
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Aviso', 'Debe Seleccionar los campos requeridos.');
				}             
	        }
        },{
        	text:getLabelFromMap('consltAsigEncAgrButtonCancelar',helpMap,'Cancelar'),
        	tooltip: getToolTipFromMap('consltAsigEncEdtButtonBuscar',helpMap,'Cancela administraci&oacute;n de encuesta'),
            //text: 'Cancelar',
  	        //tooltip:'Cancela administraci&oacute;n de encuesta', 	
            handler: function(){window.hide();}
        }]
    });
    window.show();
    dataStoreUsuarios.load();
    
};