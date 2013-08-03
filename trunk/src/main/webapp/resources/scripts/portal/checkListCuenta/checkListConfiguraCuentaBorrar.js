
function borrar(record) {
    			if(record != null)
				{
					var conn = new Ext.data.Connection();
				
					Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
					{
	           	       if (btn == "yes")
	           	        {
	           	        
	           	        conn.request({
					    url: _ACTION_BORRAR_CHECKLIST_CONFIGURA_CUENTA,
					    method: 'POST',
					    params: {"cdConfig": record.get("cdConfig")},
					    waitMsg: 'Espere por favor...',
					    callback: function (options, success, response) {
     							if (Ext.util.JSON.decode(response.responseText).success == false) 
     							{
					    			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
					    		}else{
					    			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),Ext.util.JSON.decode(response.responseText).actionMessages[0]);
					    			reloadGrid(Ext.getCmp('grid2'));
					    		}
					    }		
						});
	                	}
					})
				}else{
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacuten'));
					}
           };
     