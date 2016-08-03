Ext.define('VentanaAviso', {
    extend      : 'Ext.window.Window',
    title       : 'Aviso',
    itemId      : '_c3_instance',
    closeAction : 'destroy',
    modal       : true,
    autoScroll  : true,
    border      : 0,
    mostrar     : function () {
        var me = this;
        centrarVentanaInterna(me.show());
    },
    buttonAlign : 'center',
    buttons     : [
        {
            text    : 'Aceptar',
            icon    : _GLOBAL_DIRECTORIO_ICONOS + 'accept.png',
            handler : function (me) {
                me.up('window').destroy();
            }
        }
    ],
    constructor : function (config) {
        debug('config:', config, '.');
        var me = this;
        Ext.apply(me, {
            html : '<p style="margin : 10px;">' + _NVL(config.aux, '(sin contenido)') + '</p>'
        });
        this.callParent(arguments);
    }
});