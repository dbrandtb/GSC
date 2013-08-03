copiar = function(record)
		{	        			
			var conn = new Ext.data.Connection();
			
			Ext.MessageBox.confirm(getLabelFromMap('400032', helpMap,'Se copiar&aacute; el registro seleccionado'),function(btn)
			{
         	       if (btn == "yes")
         	        {
	         	        conn.request({
					    url: _ACTION_COPIAR_DESCUENTO,
					    method: 'POST',
					    params: {"cdDscto": record.get("cdDscto")},
					    success: function() {
					    			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Registro Copiado');
					    			},
					    failure: function() {
	                              Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
					     		}	
						});
              	   }
			})

		};