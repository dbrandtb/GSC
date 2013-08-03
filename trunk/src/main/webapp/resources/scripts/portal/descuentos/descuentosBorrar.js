borrar = function(record) {
			var conn = new Ext.data.Connection();
		
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
          	       if (btn == "yes")
          	        {
          	        conn.request({
				    url: _ACTION_BORRAR_DESCUENTO,
				    method: 'POST',
				    params: {"cdDscto": record.get("cdDscto")},
				    success: function() {
				    	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Descuento eliminado.');
				    },
			    	failure: function() {
			         Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al eliminar');
			     	}
					});
               	}
			})
         };