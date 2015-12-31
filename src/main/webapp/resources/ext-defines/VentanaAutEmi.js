Ext.define('VentanaAutEmi',
{
    extend       : 'Ext.window.Window'
    ,title       : 'Autorizar'
    ,itemId      : '_c41_instance'
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
        debug('config:',config);
        if(Ext.isEmpty(config))
        {
            throw '#C41 - No se recibieron datos';
        }
        Ext.apply(me,
        {
            items :
            [
                {
                    xtype     : 'form'
                    ,border   : 0
                    ,defaults : { style : 'margin:5px;' }
                    ,items    :
                    [
                        {
                            xtype       : 'textfield'
                            ,name       : 'flujo.ntramite'
                            ,value      : config.ntramite
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textarea'
                            ,fieldLabel : 'Comentarios'
                            ,labelAlign : 'top'
                            ,name       : 'params.dscoment'
                            ,width      : 470
                            ,height     : 220
                        }
                    ]
                }
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
                text     : 'Autorizar'
                ,icon    : _GLOBAL_DIRECTORIO_ICONOS+'key.png'
                ,handler : function(me)
                {
                    var win = me.up('window');
                    var ck = 'Autorizando emisi\u00f3n';
                    try
                    {
                        var form = win.down('form');
                        if(!form.isValid())
                        {
                            throw 'Favor de revisar los datos';
                        }
                        var values = form.getValues();
                        values['params.swagente'] = _fieldById('SWAGENTE',win).getGroupValue();
                        debug('values:',values);
                        
                        _mask(ck);
                        Ext.Ajax.request(
                        {
                            url      : _GLOBAL_COMP_URL_AUTORIZAR_EMISION
                            ,params  : values
                            ,success : function(response)
                            {
                                var ck = 'Decodificando respuesta al autorizar emisi\u00f3n';
                                try
                                {
                                    var json = Ext.decode(response.responseText);
                                    debug('### autorizar:',json);
                                    if(json.success==true)
                                    {
                                        mensajeCorrecto
                                        (
                                            'Emisi\u00f3n correcta'
                                            ,json.message
                                            ,function()
                                            {
                                                _mask('Redireccionando...');
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