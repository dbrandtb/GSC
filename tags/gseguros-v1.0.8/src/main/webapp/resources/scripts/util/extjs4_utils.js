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
 * Funcion para implementar Paginacion,
 * carga un Store y pagina sus datos de forma local.
 * Se debe de tener un Store con un proxy 
 * de la siguiente forma:
 *
   var store=Ext.create('Ext.data.Store',
   {
       pageSize : 10,
       autoLoad : true,
       model    : 'modelPersonalizado',
       proxy    :
       {
           enablePaging : true,
           reader       : 'json',
           type         : 'memory',
           data         : []
       }
   });
       
 * @param {Ext.data.Store} _store
 * @param {String} _url
 * @param {String} _root
 * @param {Array} _params
 * @param {Function} _callback Function to be called 
 * @param {Ext.grid.Panel} _grid (Optional)
 * @param {Number} _timeout (Optional)
 */
function cargaStorePaginadoLocal(_store, _url, _root, _params, _callback, _grid, _timeout) {
	
	var timeOut = null;
	if(_timeout) {
		timeOut = _timeout;
	}
	
	if(_grid){
		_grid.setLoading(true);
		if(_grid.down('pagingtoolbar')){
			_grid.down('pagingtoolbar').moveFirst();
		}
	}
    Ext.Ajax.request(
    {
        url       : _url,
        params    : _params,
        callback  : _callback,
        timeout   : timeOut != null ? timeOut : Ext.Ajax.timeout, 
        success   : function(response)
        {
        	if(_grid){
        		_grid.setLoading(false);
        	}
        	_store.removeAll();
            var jsonResponse = Ext.decode(response.responseText);
            _store.setProxy({
                type         : 'memory',
                enablePaging : true,
                reader       : 'json',
                data         : jsonResponse[_root]
            });
            _store.load();
        },
        failure   : function()
        {
        	if(_grid){
        		_grid.setLoading(false);
        	}
        	_store.removeAll();
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


/**
 * Maneja los errores en un submit de un formulario e invoca la función callback enviada como parametro
 *
 * @param {Ext.form.Basic} form Formulario que solicitó la acción
 * @param {Ext.form.action.Action} action El objeto Action que ejecutó la operación
 * @param {Function} clbkFn Funcion callback a ejecutar
 */
function manejaErrorSubmit(form, action, clbkFn) {
    var msgServer = Ext.isEmpty(action.result.errorMessage) ? 'Error interno del servidor, consulte a soporte' : action.result.errorMessage;
    try {
    	debug('action=',action);
    	debug('action.failureType=', action.failureType);
        switch (action.failureType) {
        case Ext.form.action.Action.CONNECT_FAILURE:
            Ext.Msg.show({title: 'Error', msg: 'Error de comunicaci&oacute;n', buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
            break;
        case Ext.form.action.Action.SERVER_INVALID:
        case Ext.form.action.Action.LOAD_FAILURE:
            if(clbkFn) {
                clbkFn(form,action);
            } else {
                Ext.Msg.show({title: 'Error', msg: msgServer, buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
            }
            break;
        }
    } catch(err) {
        debug(err);
        Ext.Msg.show({title: 'Error', msg: msgServer, buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
    }
}
