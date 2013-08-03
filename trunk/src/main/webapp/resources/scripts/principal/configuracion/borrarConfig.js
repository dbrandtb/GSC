borrarConfig = function(store, claveDeConfiguracion, claveDeSeccion, configuraForm) {
	
   Ext.MessageBox.buttonText.yes = 'Si';
   Ext.MessageBox.confirm('Mensaje','Esta seguro de querer eliminar este elemento?', function(btn) {
           	if(btn == 'yes'){
           		//alert(claveDeConfiguracion);
				//alert(claveDeSeccion);
        		configuraForm.form.submit({ 
        			
					
        			url: _ACTION_BORRAR +'?id='+ selectedId +'&claveDeConfiguracion='+claveDeConfiguracion+'&claveDeSeccion='+claveDeSeccion,
        			waitTitle:'Espere',
        			waitMsg:'Procesando...',
            		failure: function(form, action) {
          				Ext.MessageBox.alert('Estado', action.result.actionErrors[0]);          				
          				
      				},
      				success: function(form, action) {
          				Ext.MessageBox.alert('Estado', action.result.actionMessages[0]);
          				store.load();
      				}
          		}); 
         	}
   });
};