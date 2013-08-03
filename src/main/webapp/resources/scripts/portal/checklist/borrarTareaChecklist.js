function borrar(record) {
           var conn = new Ext.data.Connection();
               Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'), function(btn) {
                   if (btn == "yes") {
                 //Debo llamar al validar_borrar
                 conn.request({
				    url: _ACTION_VALIDA_BORRAR_TAREA_CHECKLIST,
				    method: 'POST',
				    params: {"codigoTarea": record.get('cdTarea'),
				    		 "codigoSeccion": record.get('cdSeccion')},
				    callback: function (options, success, response) {
				    			var success = Ext.util.JSON.decode(response.responseText).success;
				    			if (success) {
				    				if(Ext.util.JSON.decode(response.responseText).resultado == 1){				    				 
				    					Ext.Msg.alert(getLabelFromMap('400057', helpMap,'Aviso'), "La tarea no se puede eliminar, se usa en una configuraci&oacute;n de cuenta.");
				    				}
				    				else
				    				{
				    					var _params = {
				    						cveConfig: record.get('cveConfig'),
								    		codigoSeccion: record.get('cdSeccion'),
								    		codigoTarea: record.get('cdTarea')
							    		};
				    					execConnection(_ACTION_BORRA_TAREA_CHECKLIST, _params, cbkBorrar);
				    				}				    								    			
				    			}else {
				    				Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).actionErrors[0]);
				    			}
				    }
					});
              	}
                    });
};
function cbkBorrar (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
	}
}