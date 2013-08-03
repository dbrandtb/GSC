borrarConfig = function(store, selectedId, configuraForm) {

   Ext.MessageBox.buttonText.yes = 'Si';
   Ext.MessageBox.confirm('Mensaje','Esta seguro de querer eliminar este elemento?', function(btn) {
           	if(btn == 'yes'){ 
        		configuraForm.form.submit({ 
        			
        			url:'principal/borraPagina.action'+'?id='+ selectedId,
        			waitTitle:'Espere',
        			waitMsg:'Procesando...',
            		failure: function(form, action) {
          				Ext.MessageBox.alert('Estado', 'Error al eliminar');          				
          				
      				},
      				success: function(form, action) {
          				Ext.MessageBox.alert('Estado', 'Elemento eliminado');          
          				store.load();
      				}
          		}); 
         	}
    });
};      