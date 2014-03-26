Ext.onReady(function(){

	var _URL_VALIDA_USUARIO = _CONTEXT + '/seguridad/autenticaUsuario.action';
	
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
            }
        }
	});
	  
	var loginForm = new Ext.form.FormPanel({
	    el:'formLogin',
	    id: 'loginForm',
	    url: _URL_VALIDA_USUARIO,
	    title: 'AUTENTICACIÓN DE USUARIO',	    
	    labelAlign: 'top',	    
	    frame:true,
	    height: 150,
        width: 270,
        bodyPadding: 5,
	    items:[
		    dsUser,
		    dsPassword
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
    		loginForm.form.submit({
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
		} else {
			Ext.Msg.show({
                   title: 'Aviso',
                   msg: 'Complete la informaci&oacute;n requerida',
                   buttons: Ext.Msg.OK,
                   icon: Ext.Msg.WARNING
               });
		}
    }
});