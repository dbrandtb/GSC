Ext.onReady(function(){

	var _URL_VALIDA_USUARIO = _CONTEXT + '/seguridad/autenticaUsuario.action';
	
	var dsUser = new Ext.form.TextField({
		id:'user',
		fieldLabel: 'Usuario',
        name: 'user',
        allowBlank: false,
        blankText:'El nombre del usuario es un dato requerido'
	});
	
	var dsPassword = new Ext.form.TextField({
		id:'password',
    	fieldLabel: 'Contraseña',
    	inputType: 'password',
    	name: 'password',
        allowBlank: false,
        blankText:'La contraseña es un dato requerido'
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
	    //autoHeight: false,
	    //standardSubmit: 'true',
        //type:'json',
	   items:[
		        	dsUser,
		        	dsPassword
		        ]
		,
		buttons: [{
			text: 'Entrar',
	        handler: function() {
	        	if (loginForm.form.isValid()) {
	        		loginForm.form.submit({
			        	waitMsg:'Procesando...',
			        	failure: function(form, action) {
			        		Ext.Msg.alert('Error', action.result.errorMessage);
						},
						success: function(form, action) {
							//Ext.Msg.alert('ENTRA', action.result.errorMessage);
							self.location.href = _CONTEXT+'/seleccionaRolCliente.action';
						}
					});
				} else {
					Ext.MessageBox.alert('Aviso', 'Complete la informaci&oacute;n requerida');
				}
			}
		}]
	});
			
	loginForm.render();
});