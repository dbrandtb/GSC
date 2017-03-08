Ext.define('VentanaTurnado',
{
    extend       : 'Ext.window.Window'
    ,title       : 'Turnado'
    ,itemId      : '_c22_instance'
    ,closeAction : 'destroy'
    ,border      : 0
    ,modal       : true
    ,width       : 500
    ,height      : 350
    ,mostrar     : function()
    {
        var me = this;
        centrarVentanaInterna(me.show());
    }
    ,constructor : function(config)
    {
        var me = this;
        if(Ext.isEmpty(config))
        {
            throw '#C22 - No se recibieron datos';
        }
        if(Ext.isEmpty(config.aux))
        {
            throw '#C22 - No se recibi\u00f3 el par\u00e1metro';
        }
        Ext.apply(me,
        {
            items :
            [
                Ext.create('Ext.form.Panel',
                {
                    border    : 0
                    ,defaults : { style : 'margin:5px;' }
                    ,items    :
                    [
                        {
                            xtype       : 'textfield'
                            ,name       : 'CDTIPFLU'
                            ,value      : config.cdtipflu
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textfield'
                            ,name       : 'CDFLUJOMC'
                            ,value      : config.cdflujomc
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textfield'
                            ,name       : 'NTRAMITE'
                            ,value      : config.ntramite
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textfield'
                            ,name       : 'STATUSOLD'
                            ,value      : config.status
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textfield'
                            ,name       : 'STATUSNEW'
                            ,value      : config.aux
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textarea'
                            ,fieldLabel : 'Comentarios'
                            ,labelAlign : 'top'
                            ,name       : 'COMMENTS'
                            ,width      : 470
                            ,height     : 220
                        }
                    ]
                })
                ,{
                    xtype       : 'radiogroup'
                    ,fieldLabel : 'Mostrar al agente'
                    ,columns    : 2
                    ,width      : 250
                    ,style      : 'margin:5px;'
                    ,items      :
                    [
                        {
                            boxLabel    : 'Si'
                            ,itemId     : 'SWAGENTE'
                            ,name       : 'SWAGENTE'
                            ,inputValue : 'S'
                        }
                        ,{
                            boxLabel    : 'No'
                            ,name       : 'SWAGENTE'
                            ,inputValue : 'N'
                            ,checked    : true
                        }
                    ]
                }
            ]
            ,buttonAlign : 'center'
            ,buttons     :
            [{
                text     : 'Turnar'
                ,icon    : _GLOBAL_DIRECTORIO_ICONOS+'user_go.png'
                ,handler : function(me)
                {
                    var win = me.up('window');
                    var ck = 'Turnando tr\u00e1mite';
                    try
                    {
                        var form = win.down('form');
                        if(!form.isValid())
                        {
                            throw 'Favor de revisar los datos';
                        }
                        var values = form.getValues();
                        values.SWAGENTE = _fieldById('SWAGENTE',win).getGroupValue();
                        debug('values:',values);
                        
                        _mask(ck);
                        Ext.Ajax.request(
                        {
                            url     : _GLOBAL_COMP_URL_TURNAR
                            ,params : _formValuesToParams(values)
                            ,success : function(response)
                            {
                                _unmask();
                                var ck = '';
                                try
                                {
                                    var json = Ext.decode(response.responseText);
                                    debug('### turnar:',json);
                                    if(json.success)
                                    {
                                        mensajeCorrecto
                                        (
                                            'Tr\u00e1mite turnado'
                                            ,json.message
                                            ,function()
                                            {
                                                _mask('Redireccionando');
                                                Ext.create('Ext.form.Panel').submit(
                                                {
                                                    url             : _GLOBAL_COMP_URL_MCFLUJO
                                                    ,standardSubmit : true
                                                });
                                            }
                                        );
                                    }
                                    else
                                    {
                                        mensajeError(json.message);
                                    }
                                }
                                catch(e)
                                {
                                    manejaException(e,ck);
                                }
                            }
                            ,failure : function()
                            {
                                _unmask();
                                errorComunicacion(null,'Error al turnar tr\u00e1mite');
                            }
                        });
                    }
                    catch(e)
                    {
                        _unmask();
                        manejaException(e,ck);
                    }
                }
            }]
        });
        this.callParent(arguments);
    }
});