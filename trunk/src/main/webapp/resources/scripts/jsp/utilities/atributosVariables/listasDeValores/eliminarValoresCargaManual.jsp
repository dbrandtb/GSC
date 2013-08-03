<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->

<!-- SOURCE CODE -->
<script type="text/javascript">

//***********
DeleteDemouser = function(store,selectedId, sel,listaValoresForm) {

    // get the selected items
   Ext.MessageBox.confirm('Mensaje','Esta seguro que desea eliminar este valor?', function(btn) {
           if(btn == 'yes'){ 
           
			     
			     listaValoresForm.form.submit({	
			     url:'atributosVariables/EliminaCargaManual.action'+'?valorClave='+selectedId,
			     waitTitle:'<s:text name="ventana.datos.variables.listas.valores.proceso.mensaje.titulo"/>',
			     waitMsg:'<s:text name="ventana.datos.variables.listas.valores.proceso.mensaje"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Error al eliminar');
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Valor eliminado');						    
						    store.load();
						    
						}
        	 }); 
        	 
        	}
       } 
      );
};      
//************

</script>