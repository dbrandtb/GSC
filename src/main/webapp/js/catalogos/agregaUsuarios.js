Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {
	
    var panelPersona = Ext.create('Ext.form.Panel', {
    	url: _URL_INSERTA_PERSONA,
		border: false,
	    renderTo : 'div_usuarios',
	    bodyStyle:'padding:5px 0px 0px 40px;',
	    items    : [
					{
						xtype      : 'hidden',
						//id       : 'params.accion',
						name       : 'params.accion',
						value      : editMode? 'U' : 'I'
					},{
						xtype      : 'hidden',
						//id       : 'params.esAgente',
						name       : 'params.esAgente'
					},{
			        	xtype      : 'hidden',
			    		//id       : 'params.cdusuari',
			    		name       : 'params.cdusuari'
			        },{
			        	xtype      : 'hidden',
			    		//id       : 'params.cdagente',
			    		name       : 'params.cdagente',
			    		fieldLabel : 'Agente'
			        },{
						xtype      : 'textfield',
						//id       : 'params.usuario',
						name       : 'params.usuario',
						fieldLabel : 'Usuario/Agente',
						allowBlank : false,
						value      : _parametros.cdusuario,
						readOnly   : editMode
					},{
                        xtype         : 'combo',
                        labelWidth    : 100,
                        name          : 'params.cdsisrol',
                        fieldLabel    : 'Rol',
                        hidden        : editMode,
			    		allowBlank    : editMode,
                        valueField    : 'key',
                        displayField  : 'value',
                        forceSelection: true,
                        queryMode     :'local',
                        store         : Ext.create('Ext.data.Store', {
	                        model     : 'Generic',
	                        autoLoad  : true,
	                        proxy     : {
	                            type        : 'ajax'
	                            ,url        : _URL_CARGA_CATALOGO
	                            ,extraParams: {catalogo:_CAT_ROLES_SISTEMA}
	                            ,reader     :
	                            {
	                                type  : 'json'
	                                ,root : 'lista'
	                            }
	                        }
                        }),
                       listeners: {
                    	   select: function ( combo, records, eOpts ){
                    		   var cdrol = records[0].get('key');
                    		   var fieldPer = panelPersona.down('#fieldAgente');
                    		   if(ROL_AGENTE != cdrol){
                    			   fieldPer.hide();
                    			   panelPersona.getForm().findField('params.otsexo').allowBlank = true;
                    			   panelPersona.getForm().findField('params.dsapellido').allowBlank = true;
                    			   panelPersona.getForm().findField('params.dsapellido1').allowBlank = true;
                    		   }else {
                    			   fieldPer.show();
                    			   panelPersona.getForm().findField('params.otsexo').allowBlank = false;
                    			   panelPersona.getForm().findField('params.dsapellido').allowBlank = false;
                    			   panelPersona.getForm().findField('params.dsapellido1').allowBlank = false;
                    		   }
                    	   }
                       }
                    },{
			        	xtype      : 'textfield',
			    		//id       : 'params.dsnombre',
			    		name       : 'params.dsnombre',
			    		fieldLabel : 'Nombre',
			    		value      : _parametros.nombre,
			    		allowBlank : false
			        },{
						xtype      : 'fieldset',
						itemId     : 'fieldAgente',
						padding    :  0,
						border     : false,
						hidden     : (editMode && _parametros.esAgente == 'N'),
						items      : [{
				        	xtype      : 'textfield',
				    		//id       : 'params.dsnombre1',
				    		name       : 'params.dsnombre1',
				    		fieldLabel : 'Segundo Nombre',
				    		value      : _parametros.snombre
				        },{
				        	xtype      : 'textfield',
				    		//id       : 'params.dsapellido',
				    		name       : 'params.dsapellido',
				    		fieldLabel : 'Apellido paterno',
				    		allowBlank : false,
				    		value      : _parametros.appat
				        },{
				        	xtype      : 'textfield',
				        	//id       : 'params.dsapellido1',
				    		name       : 'params.dsapellido1',
				    		fieldLabel : 'Apellido materno',
				    		allowBlank : false,
				    		value      : _parametros.apmat
				        },{
				        	xtype       : 'combo',
				        	//id        : 'params.otsexo',
				        	name        : 'params.otsexo',
				        	fieldLabel  : 'Sexo',
				        	allowBlank  : false,
				        	valueField  : 'key',
							displayField: 'value',
							forceSelection: true,
	                        queryMode   :'local',
							store       : Ext.create('Ext.data.Store', {
								model : 'Generic',
								autoLoad : true,
								proxy : {
									type : 'ajax',
									url : _URL_CARGA_CATALOGO,
									extraParams : {
										catalogo : _SEXO
									},
									reader : {
										type : 'json',
										root : 'lista'
									}
								},
		                        listeners: {
		                        	load: function (){
		                        		if(editMode){
		                        			panelPersona.getForm().findField('params.otsexo').setValue(_parametros.sexo);
		                        		}
		                        	}
		                        }
							})
				        },{
							xtype      : 'datefield',
				            //id       : 'params.fenacimi',
				            name       : 'params.fenacimi',
				            fieldLabel : 'Fecha de nacimiento',
				            format     : 'd/m/Y',
				            //value    : '01/01/1970',
				            maxValue   :  new Date(),
				            value      : _parametros.fecnac
				        },{
				        	xtype      : 'textfield',
				        	//id       : 'params.cdrfc',
				    		name       : 'params.cdrfc',
				    		fieldLabel : 'RFC',
				    		value      : _parametros.rfc
				        },{
				        	xtype      : 'textfield',
				    		//id       : 'params.curp',
				    		name       : 'params.curp',
				    		fieldLabel : 'CURP',
				    		value      : _parametros.curp
				        },{
				        	xtype      : 'textfield',
				    		//id       : 'params.dsemail',
				    		name       : 'params.dsemail',
				    		fieldLabel : 'Email',
				    		value      : _parametros.email
				        }]
					}
        ],
        buttonAlign: 'center',
	    buttons: [{
        	text: 'Guardar',
        	icon    : _CONTEXT+'/resources/fam3icons/icons/disk.png',
        	handler: function(btn, e) {
        		var form = this.up('form').getForm();
        		
        		if(editMode){
     			   if(_parametros.esAgente == 'N'){
     				  
     				  form.findField('params.otsexo').allowBlank = true;
     				  form.findField('params.dsapellido').allowBlank = true;
     				  form.findField('params.dsapellido1').allowBlank = true;
     			   }
     			}
        		
        		if (form.isValid()) {
        			var usuario = form.findField('params.usuario').getValue();
        			//Asignamos si es agente o no segun el rol elegido:

        			if(editMode){
        				if(_parametros.esAgente == 'S'){
        					
        					form.findField('params.esAgente').setValue('1');
                			form.findField('params.cdagente').setValue(usuario);
        				}else {
        					form.findField('params.esAgente').setValue('0');
        				}
        			}else{
        				var rol = form.findField('params.cdsisrol').getValue();
            			switch(rol) {
                    		case ROL_AGENTE:
                    			form.findField('params.esAgente').setValue('1');
                    			form.findField('params.cdagente').setValue(usuario);
                    			break;
                    		default:
                    			form.findField('params.esAgente').setValue('0');
                    			break;
                    	}
        			}
        			
        			form.findField('params.cdusuari').setValue(usuario);
        			
        			var msjeConfirmaGuardadoUsuario;
        			if(editMode){
        				msjeConfirmaGuardadoUsuario = '&iquest;Esta seguro que desea actualizar este usuario?';
        			}else{
        				msjeConfirmaGuardadoUsuario = '&iquest;Esta seguro que desea crear este usuario?';
        			}
        			
        			Ext.Msg.show({
    		            title: 'Confirmar acci&oacute;n',
    		            msg: msjeConfirmaGuardadoUsuario,
    		            buttons: Ext.Msg.YESNO,
    		            fn: function(buttonId, text, opt) {
    		            	if(buttonId == 'yes') {
    		            		form.submit({
			    		        	waitMsg:'Procesando...',			        	
			    		        	failure: function(form, action) {
			    		        		showMessage('Error', action.result.errorMessage, Ext.Msg.OK, Ext.Msg.ERROR);
			    					},
			    					success: function(form, action) {
			    						recargaGridUsuarios();
			    						windowLoader.close();
			    						showMessage('\u00C9xito', 'El usuario se guard\u00F3 correctamente', Ext.Msg.OK, Ext.Msg.INFO);
			    						form.reset();
			    					}
			    				});
    		            	}
            			},
    		            animateTarget: btn,
    		            icon: Ext.Msg.QUESTION
        			});
    			} else {
    				Ext.Msg.show({
    					title: 'Aviso',
    		            msg: 'Complete la informaci&oacute;n requerida',
    		            buttons: Ext.Msg.OK,
    		            animateTarget: btn,
    		            icon: Ext.Msg.WARNING
    				});
    			}
        	}
        },
        {
            	text: 'Cancelar',
            	icon    : _CONTEXT+'/resources/fam3icons/icons/cancel.png',
            	handler: function(btn, e) {
            		windowLoader.close();
            	}
        }]
    });
    
    
});