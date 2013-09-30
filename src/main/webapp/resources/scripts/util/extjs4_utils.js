/**
 * Funciones comunes
 * @author rbautista
 */


/**
 * Muestra un mensaje emergente
 * @param title   String
 * @param msg     String
 * @param buttons Object/Object[]
 * @param icon    String 
 */
function showMessage(title, msg, buttons, icon){
    Ext.Msg.show({
        title: title,
        msg: msg,
        buttons: buttons,
        icon: icon
    });
}

/**
 * Carga un Store y pagina sus datos de forma local
 * @param _store
 * @param _url
 * @param _root
 * @param _params
 * @param _callback
 */
function cargaStorePaginadoLocal(_store, _url, _root, _params, _callback) {
    Ext.Ajax.request(
    {
        url       : _url,
        params    : _params,
        callback  : _callback,
        success   : function(response)
        {
            var jsonResponse = Ext.decode(response.responseText);
            _store.setProxy(
            {
                type         : 'memory',
                enablePaging : true,
                reader       : 'json',
                data         : jsonResponse[_root]
            });
            _store.load();
        },
        failure   : function()
        {
            Ext.Msg.show({
                title   : 'Error',
                icon    : Ext.Msg.ERROR,
                //msg     : 'Error cargando los datos de ' + _url,
                msg     : 'Error al obener los datos, intente m\u00E1s tarde',
                buttons : Ext.Msg.OK
            });
        }
    });
}