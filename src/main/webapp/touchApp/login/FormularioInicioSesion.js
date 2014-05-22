Ext.define('FormularioInicioSesion',
{
    extend  : 'Ext.form.FormPanel'
    ,config :
    {
        title      : ''
        ,url       : ''
        ,msgError  : ''
        ,msgEspera : ''
        ,items     :
        [
            {
                xtype  : 'fieldset'
                ,items :
                [
                    {
                        xtype       : 'textfield'
                        ,name       : 'user'
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'passwordfield'
                        ,name       : 'password' 
                        ,allowBlank : false
                    }
                ]
            }
            ,{
                xtype   : 'container'
                ,layout :
                {
                    type   : 'vbox'
                    ,pack  : 'center'
                    ,align : 'center'
                }
                ,items  :
                [
                    {
                        xtype : 'button'
                        ,ui   : 'round'
                    }
                ]
            }
        ]
        ,listeners    :
        {
            painted : function()
            {
                debug('>LoginForm painted');
                var me = this;
                me.down('[name=user]').focus();
                debug('<LoginForm painted');
            }
        }
    }
    ,onButtonTap : function(button,event)
    {
        debug('>LoginForm onButtonTap');
        var form=button.up('formpanel');
        debug("form.get('msgError'):",form.get('msgError'));
        debug("form.get('msgEspera'):",form.get('msgEspera'));
        var record = Ext.create('LoginFormModel',form.getValues());
        var message="";
        debug('record:',record);
        var errors=record.validate();
        if(errors.isValid())
        {
            debug('valid');
            form.submit(
            {
                waitMsg  : form.get('msgEspera')
                ,success : function()
                {
                    debug('>success');
                    form.callback();
                    debug('<success');
                }
                ,failure : function(form2,action)
                {
                    debug('>failure');
                    var msgServer = Ext.isEmpty(action.errorMessage) ? form.get('msgError') : action.errorMessage;
                    Ext.Msg.alert('Error', msgServer, function(){});
                    debug('<failure');
                }
            });

        }
        else
        {
            debug('errors.items:',errors.items);
            Ext.each(errors.items,function(rec,i)
            {
                message += rec.config.message+"<br>";
            });
            Ext.Msg.alert("Datos incompletos", message, function(){});
        }
        debug('<LoginForm onButtonTap');
    }
    ,constructor : function(config)
    {
        debug('>LoginForm constructor');
        debug('config:',config);
        this.callParent(arguments);
        var me=this;
        me.setTitle(config.textos.titulo);
        me.setUrl(config.url);
        me.setMsgError(config.textos.msgError);
        me.setMsgEspera(config.textos.msgEspera);
        me.down('[name=user]').setLabel(config.textos.usuario);
        me.down('[name=password]').setLabel(config.textos.contrasena);
        me.down('fieldset').setTitle(config.textos.tituloFieldSet);
        me.down('fieldset').setInstructions(config.textos.instrucciones);
        me.down('button').setText(config.textos.boton);
        me.down('button').setHandler(me.onButtonTap);
        me.callback = config.callback;
        Ext.define('LoginFormModel',
        {
            extend  : 'Ext.data.Model'
            ,config :
            {
                fields       :
                [
                    {name  : 'user'     , type : 'string'}
                    ,{name : 'password' , type : 'password'}
                ]
                ,validations :
                [
                    {type  : 'presence' , name : 'user'     , message : config.textos.usuarioValida}
                    ,{type : 'presence' , name : 'password' , message : config.textos.passwordValida}
                ]
            }
        });
        debug('<LoginForm constructor');
    }
});