Ext.define('VentanaDocumentos',
{
    extend       : 'Ext.window.Window'
    ,title       : 'Documentos'
    ,itemId      : '_c2_instance'
    ,closeAction : 'destroy'
    ,modal       : true
    ,width       : 700
    ,height      : 400
    ,mostrar     : function()
    {
        var me = this;
        centrarVentanaInterna(me.show());
    }
    ,constructor : function(config)
    {
        var me = this;
        Ext.apply(me,
        {
            loader :
            {
                scripts   : true
                ,autoLoad : true
                ,url      : _GLOBAL_COMP_URL_VENTANA_DOCS
                ,params   :
                {
                    'smap1.cdunieco'  : config.cdunieco
                    ,'smap1.cdramo'   : config.cdramo
                    ,'smap1.estado'   : config.estado
                    ,'smap1.nmpoliza' : config.nmpoliza
                    ,'smap1.nmsuplem' : config.nmsuplem
                    ,'smap1.nmsolici' : config.nmpoliza
                    ,'smap1.ntramite' : config.ntramite
                    ,'smap1.tipomov'  : '0'
                }
            }
        });
        this.callParent(arguments);
    }
});