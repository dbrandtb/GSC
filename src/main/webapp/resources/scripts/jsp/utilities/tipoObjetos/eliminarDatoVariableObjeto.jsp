<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<script type="text/javascript">

//***********
eliminaAtributoVariableObjeto = function(store,selectedId,objetosForm) {

    // get the selected items
   Ext.MessageBox.confirm('Mensaje','Esta seguro que desea eliminar este elemento?', function(btn) {
           if(btn == 'yes'){ 
           
			     objetosForm.form.load({	
			     url:'tipoObjeto/EliminaDatoVariableObjeto.action'+'?descripcion='+selectedId,
			     waitTitle:'<s:text name="ventana.configObjetos.proceso.mensaje.titulo"/>',
				 waitMsg:'<s:text name="ventana.configObjetos.proceso.mensaje"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Elemento eliminado');
						    store.load();
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Elemento eliminado');						    
						    
						    
						}
        	 }); 
        	 
        	}
       } 
      );
};      
//************

</script>