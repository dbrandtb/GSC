Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {

    Ext.create('Ext.panel.Panel', {
	    border   : 0,
	    renderTo : 'div_usuarios',
	    items    : [
            {
				xtype: 'form',
				//title: 'Agregar usuario del sistema',
				url: _URL_INSERTA_PERSONA,
				border: false,
				margin: '5',
		        defaults :  {
		        	style : 'margin:5px;',
		        	labelWidth : 150
		        },
		        items : [
					{
						xtype      : 'hidden',
						//id       : 'params.accion',
						name       : 'params.accion',
						value      : 'I'
					},{
						xtype      : 'hidden',
						//id       : 'params.esAgente',
						name       : 'params.esAgente'
					},{
						xtype      : 'textfield',
						//id       : 'params.usuario',
						name       : 'params.usuario',
						fieldLabel : 'Usuario/Agente',
						allowBlank : false
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
                        xtype         : 'combo',
                        //id          : 'cdsisrol',
                        name          : 'params.cdsisrol',
                        fieldLabel    : 'Rol',
			    		allowBlank    : false,
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
                        })
                    },{
			        	xtype      : 'textfield',
			    		//id       : 'params.dsnombre',
			    		name       : 'params.dsnombre',
			    		fieldLabel : 'Nombre',
			    		allowBlank : false
			        },{
			        	xtype      : 'textfield',
			    		//id       : 'params.dsnombre1',
			    		name       : 'params.dsnombre1',
			    		fieldLabel : 'Segundo Nombre'
			        },{
			        	xtype      : 'textfield',
			    		//id       : 'params.dsapellido',
			    		name       : 'params.dsapellido',
			    		fieldLabel : 'Apellido paterno',
			    		allowBlank : false
			        },{
			        	xtype      : 'textfield',
			        	//id       : 'params.dsapellido1',
			    		name       : 'params.dsapellido1',
			    		fieldLabel : 'Apellido materno',
			    		allowBlank : false
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
							}
						})
			        },{
						xtype      : 'datefield',
			            //id       : 'params.fenacimi',
			            name       : 'params.fenacimi',
			            fieldLabel : 'Fecha de nacimiento',
			            format     : 'd/m/Y',
			            //value    : '01/01/1970',
			            maxValue   :  new Date()
			        },{
			        	xtype      : 'textfield',
			        	//id       : 'params.cdrfc',
			    		name       : 'params.cdrfc',
			    		fieldLabel : 'RFC'
			        },{
			        	xtype      : 'textfield',
			    		//id       : 'params.curp',
			    		name       : 'params.curp',
			    		fieldLabel : 'CURP'
			        },{
			        	xtype      : 'textfield',
			    		//id       : 'params.dsemail',
			    		name       : 'params.dsemail',
			    		fieldLabel : 'Email'
			        },{
			        	xtype: 'button',
		            	text: 'Guardar',
		            	icon    : _CONTEXT+'/resources/fam3icons/icons/disk.png',
		            	handler: function(btn, e) {
		            		var form = this.up('form').getForm();
		            		console.log(form.getFields());
		            		if (form.isValid()) {
		            			var usuario = form.findField('params.usuario').getValue();
		            			//Asignamos si es agente o no segun el rol elegido:
		            			var rol = form.findField('params.cdsisrol').getValue();
		            			switch(rol) {
	                        		case ROL_AGENTE:
	                        			form.findField('params.esAgente').setValue('1');
	                        			form.findField('params.cdagente').setValue(usuario);
	                        			break;
	                        		default:
	                        			form.findField('params.esAgente').setValue('0');
	                        			form.findField('params.cdusuari').setValue(usuario);
	                        			break;
	                        	}
		            			
		            			var msjeConfirmaGuardadoUsuario = 'Usuario: <strong>' + form.findField('params.usuario').getValue() + '</strong><br/>'+
		            				'Nombre: <strong>' + form.findField('params.dsnombre').getValue() + ' ' + form.findField('params.dsnombre1').getValue() + ' ' +
            						form.findField('params.dsapellido').getValue() + ' ' + form.findField('params.dsapellido1').getValue() + '</strong><br/>' +
            						'Rol: <strong>' + form.findField('params.cdsisrol').getRawValue() + '</strong><br/>'+
            						'¿Desea crearlo?';
		            			
		            			Ext.Msg.show({
	            		            title: 'Confirmar creaci\u00F3n de usuario',
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
		            }
			    ]
	        }
        ]
    });
    
    
});