Ext.onReady(function(){    

    //_grabarEvento('SEGURIDAD','ACCLOGIN');

    Ext.apply(Ext.form.field.VTypes, {
        
        password: function(val, field) {
            if (field.initialPassField) {
                var pwd = field.up('form').down('#' + field.initialPassField);
                return (val == pwd.getValue());
            }
            return true;
        },

        passwordText: 'Las contrase\u00F1as no coinciden.'
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
        fieldLabel: 'Contrase\u00F1a',
        inputType: 'password',
        name: 'password',
        allowBlank: false,
        blankText:'La contrase\u00F1a es un dato requerido',
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
        fieldLabel: 'Confirme su Contrase\u00F1a',
        inputType: 'password',
        vtype: 'password',
        name: 'passwordConfirm',
        allowBlank: false,
        blankText:'La confirmaci\u00F3n de la contrase\u00F1a es un dato requerido',
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
        title: 'AUTENTICACI\u00D3N DE USUARIO',        
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
                btn.up('form').down('[name=passwordConfirm]').disable();
                btn.up('form').down('[name=passwordConfirm]').hide();
            }
        }]
    });
            
    loginForm.render();
    
    function validarUsuario() {
        if (loginForm.form.isValid()) {
            
            loginForm.form.submit({
                url: _URL_VALIDA_EXISTE_USUARIO,
                waitMsg:'Procesando...',
                failure: function(form, action) {
                    
                    debug('failure', form, action);
                    
                    var msjError = "Error de comunicaci\u00F3n. Consulte a su administrador o intente m\u00E1s tarde."
                    Ext.Msg.show({
                        title: 'Error', 
                        msg: !Ext.isEmpty(action.result.errorMessage) ? action.result.errorMessage : msjError, 
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
                },
                success: function(form, action) {
                    
                    debug('success', form, action);
                    debug('existeUsuarioLDAP=', action.result.params.existeUsuarioLDAP);
                    debug('modoAutenticacionLDAP=', action.result.params.modoAutenticacionLDAP);
                    debug('permiteAgregarUsuariosLdap=', action.result.params.modoAgregarUsuariosLDAP);
                    
                    // Si esta activo el modo autenticacion LDAP y no existe en LDAP:
                    if(action.result.params.modoAutenticacionLDAP == 'true' && action.result.params.existeUsuarioLDAP != 'true') {
                            
                        // Si esta activo el modo de agregar usuario LDAP y
                        // el campo confirmacion de passwd esta oculto:
                        if(action.result.params.modoAgregarUsuariosLDAP == 'true' && loginForm.down('[name=passwordConfirm]').isHidden()) {
                                
                                dsPassword.reset();
                                loginForm.down('[name=passwordConfirm]').enable();
                                loginForm.down('[name=passwordConfirm]').show();
                                Ext.Msg.show({
                                    title   : 'Aviso',
                                    icon    : Ext.Msg.INFO,
                                    msg     : 'Por motivos de seguridad y como \u00FAnica ocasi\u00F3n debe de renovar su contrase\u00F1a.',
                                    buttons : Ext.Msg.OK
                                });
                                
                        } else {
                            validarPasswordYrol();
                        }
                    } else {
                        validarPasswordYrol();
                    }
                }
            });
            
        } else {
            Ext.Msg.show({
                title: 'Aviso',
                msg: 'Complete y Verifique la informaci\u00F3n requerida',
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
                        Ext.Msg.show({title: 'Error', msg: 'Error de comunicaci\u00F3n', buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
                        break;
                    case Ext.form.action.Action.SERVER_INVALID:
                    case Ext.form.action.Action.LOAD_FAILURE:
                        var msgServer = Ext.isEmpty(action.result.errorMessage) ? 'Error interno del servidor, consulte a soporte' : action.result.errorMessage;
                        Ext.Msg.show({title: 'Error', msg: msgServer, buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
                        break;
                }
            },
            success: function(form, action) {
                //_grabarEvento('SEGURIDAD','LOGIN');
                self.location.href = _CONTEXT+'/seleccionaRolCliente.action';
            }
        });
    }
});