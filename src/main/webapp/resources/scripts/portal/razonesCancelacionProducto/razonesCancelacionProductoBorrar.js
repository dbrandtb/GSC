function borrar(record) {
           var conn = new Ext.data.Connection();
               Ext.MessageBox.confirm('Eliminar', 'Desea eliminar la raz&oacute;n del producto?', function(btn) {
                   if (btn == "yes") {
                 conn.request({
				    url: _ACTION_VALIDAR_BORRAR_RAZON_CANCELACION_PRODUCTO,
				    method: 'POST',
				    params: {"cdRazon": record.get('cdRazon'),
				    		 "cdRamo": record.get('cdRamo')},
				    success: function() {
                     			conn.request({
							    url: _ACTION_BORRAR_RAZON_CANCELACION_PRODUCTO,
							    method: 'POST',
							    params: {"cdRazon": record.get('cdRazon'),
				    		 			 "cdRamo": record.get('cdRamo')},
							    success: function() {
							    	Ext.MessageBox.alert('Aviso','Raz&oacute;n eliminada');
							    },
							    failure: function() {
							         Ext.Msg.alert('Aviso','Problemas al eliminar');
							     }
							});
					    },
					 failure: function() {
					         Ext.Msg.alert('Aviso','La razón ya se uso en algún proceso de cancelación');
					     }
					});
              	}
                    });
       };