Ext.onReady(function(){

	Ext.apply(Ext.form.field.VTypes, {
        
        password: function(val, field) {
            if (field.initialPassField) {
                var pwd = field.up('form').down('#' + field.initialPassField);
                return (val == pwd.getValue());
            }
            return true;
        },

        passwordText: 'Las contraseñas no coinciden.'
    });

	var dsUser = new Ext.form.TextField({
		id:'user',
		fieldLabel: 'Usuario',
        name: 'user',
        allowBlank: false,
        blankText:'El nombre del usuario es un dato requerido',
        listeners:{
            scope:this,
            specialkey: function(f,e){
                if(e.getKey()==e.ENTER){                	
                	validarUsuario();
                }
            }
        }
	});
	
	var dsPassword = new Ext.form.TextField({
		id:'password',
    	fieldLabel: 'Contraseña',
    	inputType: 'password',
    	name: 'password',
        allowBlank: false,
        blankText:'La contraseña es un dato requerido',
        listeners:{
            scope:this,
            specialkey: function(f,e){
                if(e.getKey()==e.ENTER){                	
                	validarUsuario();
                }
            },
            change: function(field) {
                var confirmField = field.up('form').down('[name=passwordConfirm]');
                confirmField.validate();
            }
        }
	});

	var confirmPassword = new Ext.form.TextField({
		id:'confirmPassword',
		fieldLabel: 'Confirme su Contraseña',
		inputType: 'password',
		vtype: 'password',
		name: 'passwordConfirm',
		allowBlank: false,
		blankText:'La confirmaci&oacute;n de la contraseña es un dato requerido',
		initialPassField: 'password',
		hidden: true,
		disabled: true,
		listeners:{
			scope:this,
			specialkey: function(f,e){
				if(e.getKey()==e.ENTER){                	
					validarUsuario();
				}
			}
		}
	});
	  
	var loginForm = new Ext.form.FormPanel({
	    el:'formLogin',
	    id: 'loginForm',
	    title: 'AUTENTICACIÓN DE USUARIO',	    
	    labelAlign: 'top',	    
	    frame:true,
	    //height: 200,
        width: 270,
        bodyPadding: 5,
	    items:[
		    dsUser,
		    dsPassword,
		    confirmPassword
        ],
		buttons: [{
			text: 'Entrar',
	        handler: function(btn, e) {
	        	validarUsuario();
			}
		},{
			text: 'Cancelar',
	        handler: function(btn, e) {
	        	btn.up('form').getForm().reset();
			}
		}]
	});
			
	loginForm.render();
	
    function validarUsuario() {
    	if (loginForm.form.isValid()) {
    		if(_MODO_AGREGAR_USUARIOS_A_LDAP && loginForm.down('[name=passwordConfirm]').isHidden()){
    			loginForm.form.submit({
        			url: _URL_VALIDA_EXISTE_USUARIO,
    	        	waitMsg:'Procesando...',
    	        	failure: function(form, action) {
						loginForm.down('[name=passwordConfirm]').enable();
						loginForm.down('[name=passwordConfirm]').show();
						Ext.Msg.show({
							title    : 'Aviso'
							,icon    : Ext.Msg.INFO
							,msg     : 'Por motivos de seguridad y como &uacute;nica ocasi&oacute;n debe de confirmar su contraseña.'
							,buttons : Ext.Msg.OK
						});
					},
    				success: function(form, action) {
						validarPasswordYrol();
    				}
    			});
    			
    		}else {
    			validarPasswordYrol();
    		}
    		
		} else {
			Ext.Msg.show({
                   title: 'Aviso',
                   msg: 'Complete y Verifique la informaci&oacute;n requerida',
                   buttons: Ext.Msg.OK,
                   icon: Ext.Msg.WARNING
               });
		}
    }
    
    function validarPasswordYrol(){
		loginForm.form.submit({
			url: _URL_VALIDA_USUARIO,
        	waitMsg:'Procesando...',
        	failure: function(form, action) {
        		switch (action.failureType) {
                    case Ext.form.action.Action.CONNECT_FAILURE:
                	    Ext.Msg.show({title: 'Error', msg: 'Error de comunicaci&oacute;n', buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
                        break;
                    case Ext.form.action.Action.SERVER_INVALID:
                    case Ext.form.action.Action.LOAD_FAILURE:
                    	 var msgServer = Ext.isEmpty(action.result.errorMessage) ? 'Error interno del servidor, consulte a soporte' : action.result.errorMessage;
                         Ext.Msg.show({title: 'Error', msg: msgServer, buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
                        break;
                }
			},
			success: function(form, action) {
				self.location.href = _CONTEXT+'/seleccionaRolCliente.action';
			}
		});
    }
});