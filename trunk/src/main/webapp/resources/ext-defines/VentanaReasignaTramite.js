Ext.define('VentanaReasignaTramite',
{
    extend       : 'Ext.window.Window'
    ,title       : 'Reasignar'
    ,itemId      : '_c61_instance'
    ,closeAction : 'destroy'
    ,border      : 0
    ,modal       : true
    ,mostrar     : function()
    {
        var me = this;
        centrarVentanaInterna(me.show());
    }
    ,constructor : function(config)
    {
        var me = this;
        debug('#c61 config:',config);
        if(Ext.isEmpty(config))
        {
            throw '#c61 - No se recibieron datos';
        }
        Ext.apply(me,
        {
            items :
            [
                Ext.create('Ext.grid.Panel',
                {
                    width    : 600
                    ,height  : 300
                    ,columns :
                    [
                        {
                            xtype    : 'actioncolumn'
                            ,icon    : _GLOBAL_DIRECTORIO_ICONOS+'accept.png'
                            ,tooltip : 'Aceptar'
                            ,width   : 30
                            ,handler : me.onAceptar
                        }
                        ,{
                            text       : 'Clave'
                            ,dataIndex : 'CDUSUARI'
                            ,width     : 120
                        }
                        ,{
                            text       : 'Nombre'
                            ,dataIndex : 'NOMBRE'
                            ,flex      : 1
                        }
                        ,{
                            text       : 'Num. tr&aacute;mites asignados'
                            ,dataIndex : 'TOTAL'
                            ,width     : 160
                        }
                    ]
                    ,store : Ext.create('Ext.data.Store',
                    {
                        fields    : [ 'CDUSUARI' , 'NOMBRE' , 'TOTAL' , 'CDSISROL' ]
                        ,autoLoad : true
                        ,proxy    :
                        {
                            type         : 'ajax'
                            ,url         : _GLOBAL_COMP_URL_RECUPERACION_SIMPLE_LISTA
                            ,extraParams :
                            {
                                'smap1.procedimiento' : 'RECUPERAR_USUARIOS_REASIGNACION_TRAMITE'
                                ,'smap1.ntramite'     : config.ntramite
                            }
                            ,reader      :
                            {
                                type : 'json',
                                root : 'slist1'
                            }
                        }
                    })
                })
            ]
            ,buttonAlign : 'center'
            ,buttons     : []
        });
        this.callParent(arguments);
    }
    ,onAceptar : function(v,row,col,item,e,rec)
    {
        var window1  = v.up('window');
        
        debug('window1:',window1);
        
        var cdusuari = rec.get('CDUSUARI');
        var cdsisrol = rec.get('CDSISROL');
        var status   = window1.status;
        debug('cdusuari:',cdusuari,'cdsisrol:',cdsisrol);
        debug('status:',status,'window1:',window1);
        centrarVentanaInterna(Ext.create('Ext.window.Window',
        {
            title        : 'Comentarios'
            ,buttonAlign : 'center'
            ,modal       : true
            ,items       :
            [
                {
                    xtype       : 'textarea'
                    ,width      : 400
                    ,height     : 200
                    ,allowBlank : false
                }
                ,{
                    xtype       : 'radiogroup'
                    ,fieldLabel : 'Mostrar al agente'
                    ,columns    : 2
                    ,width      : 250
                    ,style      : 'margin:5px;'
                    ,hidden     : _GLOBAL_CDSISROL===RolSistema.Agente
                    ,items      :
                    [
                        {
                            boxLabel    : 'Si'
                            ,itemId     : 'SWAGENTE'
                            ,name       : 'SWAGENTE'
                            ,inputValue : 'S'
                            ,checked    : _GLOBAL_CDSISROL===RolSistema.Agente
                        }
                        ,{
                            boxLabel    : 'No'
                            ,name       : 'SWAGENTE'
                            ,inputValue : 'N'
                            ,checked    : _GLOBAL_CDSISROL!==RolSistema.Agente
                        }
                    ]
                }
            ]
            ,buttons :
            [
                {
                    text     : 'Reasignar'
                    ,icon    : _GLOBAL_DIRECTORIO_ICONOS+'accept.png'
                    ,handler : function(me)
                    {
                        var window = me.up('window');
                        var text   = window.down('textarea');
                        debug('window:',window,'text:',text);
                        if(!text.isValid())
                        {
                            datosIncompletos();
                        }
                        else
                        {
                            window.setLoading(true);
                            Ext.Ajax.request(
                            {
                                url     : _GLOBAL_URL_REASIGNAR_TRAMITE_INDV
                                ,params :
                                {
                                    'smap1.ntramite'         : window1.ntramite
                                    ,'smap1.status'          : status
                                    ,'smap1.rol_destino'     : cdsisrol
                                    ,'smap1.usuario_destino' : cdusuari
                                    ,'smap1.comments'        : text.getValue()
                                    ,'smap1.swagente'        : _fieldById('SWAGENTE').getGroupValue()
                                }
                                ,success : function(response)
                                {
                                    window.setLoading(false);
                                    var ck = 'Reasignando tr&aacute;mite';
                                    try
                                    {
                                        var json2 = Ext.decode(response.responseText);
                                        debug('### reasignar:',json2);
                                        if(json2.success)
                                        {
                                            window.close();
                                            window1.close();
                                            Ext.ComponentQuery.query('[xtype=button][text=Buscar]')[0].handler();
                                            mensajeCorrecto('Tr&aacute;mite reasignado'
                                                ,json2.smap1.nombreUsuarioDestino
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
                                            mensajeError('Error al reasignar tr&aacute;mite');
                                        }
                                    }
                                    catch(e)
                                    {
                                        manejaException(e,ck);
                                    }
                                }
                                ,failure : function()
                                {
                                    window.setLoading(false);
                                    errorComunicacion(null,'Error al reasignar tr&aacute;mite');
                                }
                            });
                        }
                    }
                }
            ]
        }).show());
    }
});