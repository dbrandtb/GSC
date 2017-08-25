Ext.define('VentanaClaveAuto',
{
    extend       : 'Ext.window.Window'
    ,itemId      : '_c55_instance'
    ,closeAction : 'destroy'
    ,width       : 750
    ,height      : 450
    ,autoScroll  : true
    ,collapsible   : true
    ,titleCollapse : true
    ,cls         : 'VENTANA_DOCUMENTOS_CLASS'
    ,mostrar     : function()
    {
        var me = this;
        return centrarVentanaInterna(me.show());
    }
    ,constructor : function(config)
    {
        debug('config:',config);
        var me = this;
        debug('me:{}',me);
        
        /*
        EL AUXILIAR DEBE SER UN OBJETO JSON
        ACTUALMENTE SE LEEN LAS PROPIEDADES: lista Y aseguradosPrimero
        lista es para el combo de asociación
        usuariosPrimero es para que muestre arriba los docs de usuario
        */
        var auxJson = {};
        if(!Ext.isEmpty(config.aux))
        {
            var ck = 'Decodificando mapa auxiliar de ventana de documentos';
            try
            {
                auxJson = Ext.decode(config.aux);
            }
            catch(e)
            {
                manejaException(e,ck);
            }
        }
        debug('_c2 auxJson:',auxJson,'.');
        
        Ext.apply(me,
        {
            title   : 'Captura Clave Auto del tr\u00e1mite'+(Ext.isEmpty(config.ntramite) ? '' : ' '+config.ntramite)
            ,loader :
            {
                scripts   : true
                ,autoLoad : true
                ,url      : _GLOBAL_COMP_URL_CLAVE_AUTO_FLUJO
                ,params   :
                {
                    'smap1.cdtipflu'  : config.cdtipflu,
					'smap1.cdflujomc' : config.cdflujomc,
					'smap1.tipoent'   : config.tipoent,
					'smap1.claveent'  : 0,//_p28_flujo.claveent,
					'smap1.webid'     : 0,//_p28_flujo.webid,
					'smap1.ntramite'  : config.ntramite,
					'smap1.status'    : config.status,
					'smap1.cdunieco'  : config.cdunieco,
					'smap1.cdramo'    : config.cdramo,
					'smap1.estado'    : config.estado,
					'smap1.nmpoliza'  : config.nmpoliza,
					'smap1.nmsituac'  : config.nmsituac,
					'smap1.nmsuplem'  : config.nmsuplem,
					'smap1.cdusuari'  : config.cdusuari,
					'smap1.aux'       : auxJson
                }
            }
        });
        this.callParent(arguments);
    }
});